package com.example.jehyuhassu.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jehyuhassu.JehyuDetailActivity;
import com.example.jehyuhassu.R;
import com.example.jehyuhassu.databinding.CardListItemBinding;
import com.example.jehyuhassu.model.CardListItem;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeCardViewHolder> {
    private ArrayList<CardListItem> items;

    public HomeListAdapter(ArrayList<CardListItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HomeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new HomeCardViewHolder(CardListItemBinding.bind(view));
    }


    @Override
    public void onBindViewHolder(@NonNull HomeCardViewHolder holder, int position) {
        CardListItem item = items.get(position);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("[HomeListAdapter]", "onClick: " + item.getName());
                Intent intent = new Intent(holder.itemView.getContext(), JehyuDetailActivity.class);
                holder.binding.getRoot().getContext().startActivity(intent);
            }
        });

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HomeCardViewHolder extends RecyclerView.ViewHolder {
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
}
