package com.example.jehyuhassu;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jehyuhassu.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private FragmentSearchBinding binding;
    private StringBuilder search_result = new StringBuilder();
    private List<String> selectedColleges = new ArrayList<>();
    private List<String> selectedPeopleNums = new ArrayList<>();
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
        return view;
    }

    public void onClick(View v) {
        addView();
    }
    private void addView() {
        final View searchView = getLayoutInflater().inflate(R.layout.row_add_search_menu, null, false);
        ImageView removeButton = searchView.findViewById(R.id.remove_button);

        String[] regionArray = getResources().getStringArray(R.array.college_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_college = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, regionArray);
        AutoCompleteTextView autoCompleteTextViewCollege = searchView.findViewById(R.id.autoCompleteTextView_college);
        autoCompleteTextViewCollege.setAdapter(arrayAdapter_college);

        String[] peopleNumArray = getResources().getStringArray(R.array.people_num_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_people_num = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, peopleNumArray);
        AutoCompleteTextView autoCompleteTextViewPeopleNum = searchView.findViewById(R.id.autoCompleteTextView_people_num);
        autoCompleteTextViewPeopleNum.setAdapter(arrayAdapter_people_num);

        // 로그 출력하는 부분
        autoCompleteTextViewCollege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 드롭다운에서 아이템을 골랐을 때 selectedColleges라는 arraylist에 문자열 저장
                String selectedCollege = autoCompleteTextViewCollege.getText().toString().trim();
                selectedColleges.add(selectedCollege);
            }
        });

        autoCompleteTextViewPeopleNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 드롭다운에서 아이템을 골랐을 때 selectedPeopleNum이라는 arraylist에 문자열 저장
                String selectedPeopleNum = autoCompleteTextViewPeopleNum.getText().toString().trim();
                selectedPeopleNums.add(selectedPeopleNum);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove_button을 눌렀을 때 가장 마지막에 저장된 selectedColleges, selectedPeopleNum의 String 삭제
                removeView(searchView);
                if (!selectedColleges.isEmpty()) {
                    int lastIndex = selectedColleges.size() - 1;
                    selectedColleges.remove(lastIndex);
                }
                if (!selectedPeopleNums.isEmpty()) {
                    int lastIndex = selectedPeopleNums.size() - 1;
                    selectedPeopleNums.remove(lastIndex);
                }
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectedColleges와 selectedPeopleNums의 저장된 문자열의 개수중 작은 값을 size로 설정
                //드롭다운 단과대와 인원수 2개중에 1개의 값만 선택했을 때는 검색에 반영 안되도록 하기 위함.
                int size = Math.min(selectedColleges.size(), selectedPeopleNums.size());
                //리스트를 저장해줄 StringBuilder
                StringBuilder logBuilder = new StringBuilder();
                //logBuilder에 값 저장
                for (int i = 0; i < size; i++) {
                    String college = selectedColleges.get(i);
                    String peopleNum = selectedPeopleNums.get(i);
                    if (i < size - 1) {
                        logBuilder.append("{\"college\": \"").append(college)
                                .append("\", \"participants\": ").append(peopleNum).append("\"}, ");
                    } else {
                        logBuilder.append("{\"college\": \"").append(college)
                                .append("\", \"participants\": ").append(peopleNum).append("\"}");
                    }
                }
                //size의 개수와 리스트 출력
                Log.d("SearchFragment", " " + size);
                Log.d("SearchFragment", logBuilder.toString());
            }
        });

        binding.layoutListSearch.addView(searchView);
    }

    private void removeView(View view){
        binding.layoutListSearch.removeView(view);
    }
}