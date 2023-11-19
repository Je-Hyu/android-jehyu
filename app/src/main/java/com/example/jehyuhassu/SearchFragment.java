package com.example.jehyuhassu;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jehyuhassu.databinding.FragmentSearchBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //단과대 종류 드롭다운 구현
        String[] regionArray = getResources().getStringArray(R.array.college_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_college = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, regionArray);
        binding.autoCompleteTextViewCollege.setAdapter(arrayAdapter_college);

        //인원수 드롭다운 구현
        String[] peopleNumArray = getResources().getStringArray(R.array.people_num_dropdown_menu);
        ArrayAdapter<String> arrayAdapter_people_num = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, peopleNumArray);
        binding.autoCompleteTextViewPeopleNum.setAdapter(arrayAdapter_people_num);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collegeItem = binding.autoCompleteTextViewCollege.getText().toString().trim();
                String participantsItem = binding.autoCompleteTextViewPeopleNum.getText().toString().trim();
                String selectedItem = ("{\"college\":\""+ collegeItem + "\", \"participants\":\""+participantsItem+"\"}");
                showToast(selectedItem);
            }
        });
/*
        binding.addSearchCardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 카드뷰를 동적으로 생성하고 설정합니다.
                CardView cardView = createCardView();

                // 생성한 카드뷰를 레이아웃에 추가합니다.
                binding.searchLayout.addView(cardView);
            }
        });*/
        return view;
    }
/*
    private CardView createCardView() {
        // 새로운 카드뷰 생성
        CardView cardView = new CardView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16); // 마진 설정
        cardView.setLayoutParams(layoutParams);

        // 카드뷰에 내용을 추가 (예시로 TextView를 추가)
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardContent = inflater.inflate(R.layout.search_cardview_content, null);
        cardView.addView(cardContent);

        return cardView;
    }
    */
    public void showToast(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}