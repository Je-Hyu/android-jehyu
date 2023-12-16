package com.example.jehyuhassu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jehyuhassu.adapter.DetailListAdapter;
import com.example.jehyuhassu.databinding.ActivityJehyuDetailBinding;
import com.example.jehyuhassu.databinding.JehyuDetailListItemBinding;
import com.example.jehyuhassu.firebase_model.Store;
import com.example.jehyuhassu.model.JehyuDetailListItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        Store store = (Store) getIntent().getSerializableExtra("Store");

        // Get a default Storage bucket
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a reference to a file from a Google Cloud Storage URI
        StorageReference gsReference = storage.getReferenceFromUrl(store.getImgPath());
        Glide.with(binding.getRoot().getContext())
                .load(gsReference)
                .into(binding.jehyuImageView);

        ArrayList<JehyuDetailListItem> items = new ArrayList<>();
        items.add(new JehyuDetailListItem("제휴업체", store.getStoreName()));
        items.add(new JehyuDetailListItem("주소", store.getLocation()));
        items.add(new JehyuDetailListItem("영업시간", store.getStartTime() + " ~ " + store.getEndTime()));
        items.add(new JehyuDetailListItem("대표메뉴", store.getMenu1() + ", " + store.getMenu2() + ", " + store.getMenu3()));
        items.add(new JehyuDetailListItem("제휴기간", store.getStartDate() + " ~ " + store.getEndDate()));
        items.add(new JehyuDetailListItem("제휴혜택", store.getContents().replace("\\n", "\n")));

        binding.jehyuDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.jehyuDetailRecyclerView.setAdapter(new DetailListAdapter(items));
    }
}