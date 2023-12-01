package com.example.jehyuhassu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.jehyuhassu.adapter.DetailListAdapter;
import com.example.jehyuhassu.databinding.ActivityJehyuDetailBinding;
import com.example.jehyuhassu.databinding.JehyuDetailListItemBinding;
import com.example.jehyuhassu.model.JehyuDetailListItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JehyuDetailActivity extends AppCompatActivity {
    private ActivityJehyuDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJehyuDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<JehyuDetailListItem> items = new ArrayList<>();
        items.add(new JehyuDetailListItem("제휴업체", "내가찜한닭 숭실대점"));
        items.add(new JehyuDetailListItem("주소", "서울 동작구 사당로 18-1 2층"));
        items.add(new JehyuDetailListItem("영업시간", "10:00 ~ 21:40"));
        items.add(new JehyuDetailListItem("대표메뉴", "순살안동찜닭, 매콤순살고추장찜닭, 치즈순살고추장찜닭"));
        items.add(new JehyuDetailListItem("제휴기간", "2023.09.11 ~ 2023.12.21"));
        items.add(new JehyuDetailListItem("제휴조건", "IT대학 학생증 모두 제시 시"));
        items.add(new JehyuDetailListItem("제휴혜택", "- 찜닭 소를 주문 시, 테이블 당 캔 음료수 1캔 제공\n" +
                "- 찜닭 중 이상을 주문 시, 테이블 당 사리 추가 1개 제공"));

        binding.jehyuDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.jehyuDetailRecyclerView.setAdapter(new DetailListAdapter(items));
    }
}