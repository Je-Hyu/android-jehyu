package com.example.jehyuhassu;

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

import com.example.jehyuhassu.databinding.FragmentProfileBinding;
import com.example.jehyuhassu.databinding.ProfileListItemBinding;
import com.example.jehyuhassu.model.ProfileListItem;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] titles = {"학번", "이름", "대학(원)", "학과(부)"};
        String[] contents = {"20203123", "이중곤", "IT대학", "컴퓨터학부"};
        ArrayList<ProfileListItem> items = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            items.add(new ProfileListItem(titles[i], contents[i]));
        }

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

    private class ProfileViewHolder extends RecyclerView.ViewHolder {
        private ProfileListItemBinding binding;

        public ProfileViewHolder(ProfileListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class ProfileListAdapter extends RecyclerView.Adapter<ProfileViewHolder> {
        private ArrayList<ProfileListItem> items;

        public ProfileListAdapter(ArrayList<ProfileListItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = binding.profileRecyclerView.getChildAdapterPosition(v);
                    ProfileListItem item = items.get(position);
                    Log.d("ProfileFragment", "position=" + position + ", title=" + item.getTitle() + ", content=" + item.getContent());
                }
            });

            return new ProfileViewHolder(ProfileListItemBinding.bind(view));
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
            ProfileListItem item = items.get(position);
            holder.binding.titleTextView.setText(item.getTitle());
            holder.binding.contentTextView.setText(item.getContent());

            if (position == 2 || position == 3) {
                holder.binding.arrowImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}