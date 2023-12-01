package com.example.jehyuhassu;

import android.os.Bundle;

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

import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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
            }
        });

        binding.layoutListSearch.addView(searchView);
    }

    private void removeView(View view) {
        binding.layoutListSearch.removeView(view);
    }
}