package com.example.jehyuhassu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jehyuhassu.JehyuDetailActivity;
import com.example.jehyuhassu.databinding.JehyuDetailListItemBinding;
import com.example.jehyuhassu.model.JehyuDetailListItem;

import java.util.ArrayList;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {
    private ArrayList<JehyuDetailListItem> items;

    public DetailListAdapter(ArrayList<JehyuDetailListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.jehyuhassu.R.layout.jehyu_detail_list_item, parent, false);
        return new DetailViewHolder(JehyuDetailListItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        JehyuDetailListItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private JehyuDetailListItemBinding binding;

        private DetailViewHolder(JehyuDetailListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(JehyuDetailListItem item) {
            binding.titleTextView.setText(item.getTitle());
            binding.contentTextView.setText(item.getContent());
        }
    }
}
