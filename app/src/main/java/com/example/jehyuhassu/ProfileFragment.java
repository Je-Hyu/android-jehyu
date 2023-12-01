package com.example.jehyuhassu;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.jehyuhassu.adapter.ProfileListAdapter;
import com.example.jehyuhassu.databinding.FragmentProfileBinding;
import com.example.jehyuhassu.databinding.ProfileListItemBinding;
import com.example.jehyuhassu.model.ProfileListItem;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("UserInfo", getActivity().MODE_PRIVATE);
        ArrayList<ProfileListItem> items = new ArrayList() {{
            add(new ProfileListItem("학번", sharedPreferences.getString("studentNum", "")));
            add(new ProfileListItem("이름", sharedPreferences.getString("name", "")));
            add(new ProfileListItem("대학(원)", sharedPreferences.getString("depart", "")));
            add(new ProfileListItem("학과(부)", sharedPreferences.getString("major", "")));
        }};

        binding.profileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.profileRecyclerView.setAdapter(new ProfileListAdapter(items));

        binding.contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProfileFragment", "contactButton clicked");
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProfileFragment", "logoutButton clicked");
            }
        });

        return view;
    }


}