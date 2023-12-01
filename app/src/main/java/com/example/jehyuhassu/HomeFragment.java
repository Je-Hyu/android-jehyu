package com.example.jehyuhassu;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.jehyuhassu.adapter.HomeListAdapter;
import com.example.jehyuhassu.databinding.FragmentHomeBinding;
import com.example.jehyuhassu.model.CardListItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayAdapter<String> adapter;

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
            }
        });

        ArrayList<CardListItem> items = new ArrayList<>();
        items.add(new CardListItem(R.drawable.dak, "내가찜한닭", "10:00 ~ 21:40", new String[]{"IT대학"}));
        items.add(new CardListItem(R.drawable.real_fry, "리얼후라이", "16:00 ~ 01:00", new String[]{"IT대학"}));
        items.add(new CardListItem(R.drawable.chwihyang, "취향", "10:30 ~ 20:30", new String[]{"IT대학"}));

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.homeRecyclerView.setAdapter(new HomeListAdapter(items));

        return view;
    }
}