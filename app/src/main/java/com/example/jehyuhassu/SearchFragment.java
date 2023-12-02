package com.example.jehyuhassu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jehyuhassu.databinding.FragmentSearchBinding;
import com.example.jehyuhassu.firebase_model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    //minb: 리얼타임 데이터 베이스 인스턴스
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //minb: 리얼타임 데이터 베이스 인스턴스 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.addButton.setOnClickListener(this);

        // 초기 searchView 하나 추가
        addView();

        return view;
    }

    public void onClick(View v) {
        addView();
    }

    private void addView() {
        final View searchView = getLayoutInflater().inflate(R.layout.row_add_search_menu, null, false);
        ImageButton removeButton = searchView.findViewById(R.id.remove_button);

        String[] regionArray = getResources().getStringArray(R.array.college_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_college = new ArrayAdapter<>(getActivity(), R.layout.dropdown_list_item, regionArray);
        AutoCompleteTextView autoCompleteTextViewCollege = searchView.findViewById(R.id.autoCompleteTextView_college);
        autoCompleteTextViewCollege.setAdapter(arrayAdapter_college);

        String[] peopleNumArray = getResources().getStringArray(R.array.people_num_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_people_num = new ArrayAdapter<>(getActivity(), R.layout.dropdown_list_item, peopleNumArray);
        AutoCompleteTextView autoCompleteTextViewPeopleNum = searchView.findViewById(R.id.autoCompleteTextView_people_num);
        autoCompleteTextViewPeopleNum.setAdapter(arrayAdapter_people_num);

        // 로그 출력하는 부분
        autoCompleteTextViewCollege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCollege = (String) parent.getItemAtPosition(position);
                Log.d("SearchFragment", selectedCollege);
                autoCompleteTextViewCollege.setText(selectedCollege, false);
            }
        });

        autoCompleteTextViewPeopleNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 드롭다운에서 아이템을 골랐을 때 selectedPeopleNum이라는 arraylist에 문자열 저장
                String selectedPeopleNum = (String) parent.getItemAtPosition(position);
                Log.d("SearchFragment", selectedPeopleNum);
                autoCompleteTextViewPeopleNum.setText(selectedPeopleNum, false);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // searchView 삭제
                removeView(searchView);
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer> map = new HashMap<>();
                // layout_list_search 레이아웃 내의 모든 searchView의 단과대와 인원수를 가져와 map에 저장
                for (int i = 0; i < binding.layoutListSearch.getChildCount(); i++) {
                    View view = binding.layoutListSearch.getChildAt(i);
                    AutoCompleteTextView autoCompleteTextViewCollege = view.findViewById(R.id.autoCompleteTextView_college);
                    AutoCompleteTextView autoCompleteTextViewPeopleNum = view.findViewById(R.id.autoCompleteTextView_people_num);


                    String college = autoCompleteTextViewCollege.getText().toString();
                    String peopleNum = autoCompleteTextViewPeopleNum.getText().toString();

                    if (college.length() > 0 && peopleNum.length() > 0) {
                        map.put(college, Integer.parseInt(peopleNum));
                    }
                }

                // map의 키와 값을 로그로 출력
                for (String key : map.keySet()) {
                    Log.d("SearchFragment", "key: " + key + ", value: " + map.get(key));
                }

                // minb: 검색하기 버튼 클릭 시
                // 가게 저장
                int participants=4;
                String storeName="써브웨이";
                String location="위치";
                String startTime="07:00";
                String endTime="23:00";
                String college = "공과대학";
                String startDate="2023.09.01";
                String endDate="2023.12.21";
                String contents="샌드위치 세트 or 샐러드 세트 구매 시 쿠키 1개 증정 (단, 랩 세트, 행사 상품, 단품 제외)";
                String menu1="메뉴1";
                String menu2="메뉴2";
                String menu3="메뉴3";

                saveStoreInfo(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3);
                //findStoreInfoUsingCollege("경영대학");
                //findStoreInfoUsingCollegesAndParticipants(map);
            }
        });

        binding.layoutListSearch.addView(searchView);
    }

    private void findStoreInfoUsingCollegesAndParticipants(Map<String, Integer> inputMap){
        Map<String, Object> storesMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : inputMap.entrySet()) {
            String target_college = entry.getKey();
            Integer target_participants = entry.getValue();

            Log.d("findStoreInfoUsingCollegesAndParticipants","College: " + target_college + ", People Num: " + target_participants);

            // college 필드를 기반으로 데이터 조회
            Query query = mDatabase.child("Stores").orderByChild("college").equalTo(target_college);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                        // 조회된 데이터에 대한 처리
                        int participants = storeSnapshot.child("participants").getValue(Integer.class).intValue();
                        if (participants <= target_participants){
                            String storeName = storeSnapshot.child("storeName").getValue(String.class);
                            String location = storeSnapshot.child("location").getValue(String.class);
                            String startTime = storeSnapshot.child("startTime").getValue(String.class);
                            String endTime = storeSnapshot.child("endTime").getValue(String.class);
                            String college = storeSnapshot.child("college").getValue(String.class);
                            String startDate = storeSnapshot.child("startDate").getValue(String.class);
                            String endDate = storeSnapshot.child("endDate").getValue(String.class);
                            String contents = storeSnapshot.child("contents").getValue(String.class);
                            String menu1 = storeSnapshot.child("menu1").getValue(String.class);
                            String menu2 = storeSnapshot.child("menu2").getValue(String.class);
                            String menu3 = storeSnapshot.child("menu3").getValue(String.class);

                            Log.d("findStoreInfoUsingCollegesAndParticipants", "가게명: " + storeName + ", 위치: " + location +
                                    ", 참가자수: " + participants + ", 운영 시작 시간: " + startTime +
                                    ", 운영 마감 시간: " + endTime + ", 단과대: " + college +
                                    ", 시작 날짜: " + startDate + ", 종료 날짜: " + endDate +
                                    ", 내용: " + contents + ", 메뉴1: " + menu1 +
                                    ", 메뉴2: " + menu2 + ", 메뉴3: " + menu3);

                            // 조회된 데이터를 Map에 저장
                            storesMap.put(storeName, new Store(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3));

                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("findStoreInfoUsingCollegesAndParticipants", "데이터 조회 중 오류: " + error.getMessage());
                }
            });
        }
    }

    private void findStoreInfoUsingCollege(String college){
        // college 필드를 기반으로 데이터 조회
        Query query = mDatabase.child("Stores").orderByChild("college").equalTo(college);
        Map<String, Object> storeMap = new HashMap<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                    // 조회된 데이터에 대한 처리
                    int participants = storeSnapshot.child("participants").getValue(Integer.class).intValue();
                    String storeName = storeSnapshot.child("storeName").getValue(String.class);
                    String location = storeSnapshot.child("location").getValue(String.class);
                    String startTime = storeSnapshot.child("startTime").getValue(String.class);
                    String endTime = storeSnapshot.child("endTime").getValue(String.class);
                    String college = storeSnapshot.child("college").getValue(String.class);  // 수정
                    String startDate = storeSnapshot.child("startDate").getValue(String.class);  // 수정
                    String endDate = storeSnapshot.child("endDate").getValue(String.class);  // 수정
                    String contents = storeSnapshot.child("contents").getValue(String.class);  // 수정
                    String menu1 = storeSnapshot.child("menu1").getValue(String.class);  // 수정
                    String menu2 = storeSnapshot.child("menu2").getValue(String.class);  // 수정
                    String menu3 = storeSnapshot.child("menu3").getValue(String.class);  // 수정

                    Log.d("store", "가게명: " + storeName + ", 위치: " + location +
                            ", 참가자수: " + participants + ", 운영 시작 시간: " + startTime +
                            ", 운영 마감 시간: " + endTime + ", 단과대: " + college +
                            ", 시작 날짜: " + startDate + ", 종료 날짜: " + endDate +
                            ", 내용: " + contents + ", 메뉴1: " + menu1 +
                            ", 메뉴2: " + menu2 + ", 메뉴3: " + menu3);

                    // 조회된 데이터를 Map에 저장
                    storeMap.put(storeName, new Store(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3));
                }

                // 앞서 생성한 storeMap을 사용하여 StoreData를 출력하는 코드
                for (Map.Entry<String, Object> entry : storeMap.entrySet()) {
                    String storeName = entry.getKey();
                    Store storeData = (Store) entry.getValue();

                    // Store 정보 출력
                    Log.d("store", "가게명: " + storeData.getStoreName() + ", 위치: " + storeData.getLocation() +
                            ", 참가자수: " + storeData.getParticipants() + ", 운영 시작 시간: " + storeData.getStartTime() +
                            ", 운영 마감 시간: " + storeData.getEndTime() + ", 단과대: " + storeData.getCollege() +
                            ", 시작 날짜: " + storeData.getStartDate() + ", 종료 날짜: " + storeData.getEndDate() +
                            ", 내용: " + storeData.getContents() + ", 메뉴1: " + storeData.getMenu1() +
                            ", 메뉴2: " + storeData.getMenu2() + ", 메뉴3: " + storeData.getMenu3());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("store", "데이터 조회 중 오류: " + error.getMessage());
            }
        });
    }

    private void saveStoreInfo(int participants, String storeName, String location, String startTime, String endTime, String college, String startDate, String endDate, String contents, String menu1, String menu2, String menu3){
        Store store = new Store(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3);
        String key= storeName+'('+college.substring(0, 2)+')';
        mDatabase.child("Stores").child(key).setValue(store)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // 데이터 저장 성공
                            Log.d("store", "데이터 저장 성공!");

                        } else {
                            // 데이터 저장 실패
                            Log.e("store", "데이터 저장 실패", task.getException());
                        }
                    }
                });
    }

    private void removeView(View view) {
        binding.layoutListSearch.removeView(view);
    }
}