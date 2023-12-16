package com.example.jehyuhassu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.jehyuhassu.adapter.HomeListAdapter;
import com.example.jehyuhassu.databinding.ActivitySearchResultBinding;
import com.example.jehyuhassu.firebase_model.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {
    ActivitySearchResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Map<String, Integer> searchConditions = (Map<String, Integer>) intent.getSerializableExtra("searchConditions");

        findStoreInfoUsingCollege("공과대학");
    }

    private void findStoreInfoUsingCollege(String college) {
        // college 필드를 기반으로 데이터 조회
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Stores")
                .orderByChild("college")
                .equalTo(college);
        Map<String, Object> storeMap = new HashMap<>();
        ArrayList<Store> items = new ArrayList<>();
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
                    String image = storeSnapshot.child("image").getValue(String.class);

                    Store store = new Store(participants, storeName, location, startTime, endTime, college, startDate, endDate, contents, menu1, menu2, menu3, image);
                    items.add(store);
                    // 조회된 데이터를 Map에 저장
                    storeMap.put(storeName, store);
                }

                binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.searchRecyclerView.setAdapter(new HomeListAdapter(items));

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