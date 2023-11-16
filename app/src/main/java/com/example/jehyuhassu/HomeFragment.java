package com.example.jehyuhassu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.jehyuhassu.databinding.FragmentHomeBinding;

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

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, getResources().getStringArray(R.array.dropdown_menu));
        binding.collegeAutoCompleteTextView.setAdapter(adapter);

        binding.collegeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String college = (String) parent.getItemAtPosition(position);
                binding.collegeAutoCompleteTextView.setText(college, false);
            }
        });

        return view;
    }
}