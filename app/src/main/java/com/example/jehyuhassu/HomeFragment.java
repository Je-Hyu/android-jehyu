package com.example.jehyuhassu;

import android.content.Intent;
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
import android.widget.ArrayAdapter;

import com.example.jehyuhassu.databinding.CardListItemBinding;
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

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, getResources().getStringArray(R.array.college_dropdown_menu));
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

    private class HomeCardViewHolder extends RecyclerView.ViewHolder {
        private CardListItemBinding binding;

        private HomeCardViewHolder(CardListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(CardListItem item) {
            binding.cardImageView.setImageResource(item.getImage());
            binding.cardTitleTextView.setText(item.getName());
            binding.cardTimeTextView.setText(item.getTime());
            binding.cardCollegeChipView.setText(item.getTags()[0]);
        }

    }

    private class HomeListAdapter extends RecyclerView.Adapter<HomeCardViewHolder> {
        private ArrayList<CardListItem> items;

        public HomeListAdapter(ArrayList<CardListItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public HomeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = binding.homeRecyclerView.getChildAdapterPosition(v);
                    CardListItem item = items.get(position);
                    Log.d("HomeFragment", "item clicked: " + item.getName());
                    // start JehyuDetailActivity
                    Intent intent = new Intent(getActivity(), JehyuDetailActivity.class);
                    startActivity(intent);
                }
            });

            return new HomeCardViewHolder(CardListItemBinding.bind(view));
        }

        @Override
        public void onBindViewHolder(@NonNull HomeCardViewHolder holder, int position) {
            CardListItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}