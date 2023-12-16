package com.example.jehyuhassu.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jehyuhassu.ProfileFragment;
import com.example.jehyuhassu.R;
import com.example.jehyuhassu.databinding.ProfileListItemBinding;
import com.example.jehyuhassu.model.ProfileListItem;

import java.util.ArrayList;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder> {
    private ArrayList<ProfileListItem> items;

    public ProfileListAdapter(ArrayList<ProfileListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
        return new ProfileViewHolder(ProfileListItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        ProfileListItem item = items.get(position);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("[ProfileListAdapter]", "onClick: " + item.getTitle());
            }
        });

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        private ProfileListItemBinding binding;

        public ProfileViewHolder(ProfileListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(ProfileListItem item) {
            binding.titleTextView.setText(item.getTitle());
            binding.contentTextView.setText(item.getContent());
        }
    }

}

