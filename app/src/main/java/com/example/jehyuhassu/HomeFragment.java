package com.example.jehyuhassu;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.jehyuhassu.adapter.HomeListAdapter;
import com.example.jehyuhassu.databinding.FragmentHomeBinding;
import com.example.jehyuhassu.firebase_model.Store;
import com.example.jehyuhassu.model.CardListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayAdapter<String> adapter;
    private ArrayList<Store> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_list_item, getResources().getStringArray(R.array.college_dropdown_menu));
        binding.collegeAutoCompleteTextView.setAdapter(adapter);

        binding.collegeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String college = (String) parent.getItemAtPosition(position);
                binding.collegeAutoCompleteTextView.setText(college, false);
                findStoreInfoUsingCollege(college);
            }
        });

        // 초기 데이터 설정
        binding.collegeAutoCompleteTextView.setText("IT대학", false);
        findStoreInfoUsingCollege("IT대학");

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.homeRecyclerView.setAdapter(new HomeListAdapter(items));

        return view;
    }

    private void findStoreInfoUsingCollege(String college) {
        // college 필드를 기반으로 데이터 조회
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Stores")
                .orderByChild("college")
                .equalTo(college);
        Map<String, Object> storeMap = new HashMap<>();
        items.clear();

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
                    String college = storeSnapshot.child("college").getValue(String.class);
                    String startDate = storeSnapshot.child("startDate").getValue(String.class);
                    String endDate = storeSnapshot.child("endDate").getValue(String.class);
                    String contents = storeSnapshot.child("contents").getValue(String.class);
                    String menu1 = storeSnapshot.child("menu1").getValue(String.class);
                    String menu2 = storeSnapshot.child("menu2").getValue(String.class);
                    String menu3 = storeSnapshot.child("menu3").getValue(String.class);
                    String image = storeSnapshot.child("imgPath").getValue(String.class);
                    if (image == null) {
                        image = storeSnapshot.child("image").getValue(String.class);
                    }

                    Store store = new Store(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3, image);
                    items.add(store);
                    // 조회된 데이터를 Map에 저장
                    storeMap.put(storeName, store);
                }

                binding.homeRecyclerView.getAdapter().notifyDataSetChanged();

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
}