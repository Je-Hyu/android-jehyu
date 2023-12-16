package com.example.jehyuhassu.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jehyuhassu.JehyuDetailActivity;
import com.example.jehyuhassu.MyAppGlideModule;
import com.example.jehyuhassu.R;
import com.example.jehyuhassu.databinding.CardListItemBinding;
import com.example.jehyuhassu.firebase_model.Store;
import com.example.jehyuhassu.model.CardListItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchCardViewHolder> {
    private ArrayList<Store> items;

    public SearchListAdapter(ArrayList<Store> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SearchCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item, parent, false);
        return new SearchCardViewHolder(CardListItemBinding.bind(view));
    }


    @Override
    public void onBindViewHolder(@NonNull SearchCardViewHolder holder, int position) {
        Store store = items.get(position);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("[HomeListAdapter]", "onClick: " + store.getStoreName());
                Intent intent = new Intent(holder.itemView.getContext(), JehyuDetailActivity.class);
                intent.putExtra("Store", store);
                holder.binding.getRoot().getContext().startActivity(intent);
            }
        });

        holder.bind(store);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SearchCardViewHolder extends RecyclerView.ViewHolder {
        private CardListItemBinding binding;

        private SearchCardViewHolder(CardListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(Store store) {
            // Get a default Storage bucket
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a reference to a file from a Google Cloud Storage URI
            StorageReference gsReference = storage.getReferenceFromUrl(store.getImgPath());

            Glide.with(binding.getRoot().getContext())
                    .load(gsReference)
                    .into(binding.cardImageView);
            binding.cardTitleTextView.setText(store.getStoreName());
            binding.cardTimeTextView.setText(store.getStartTime() + " ~ " + store.getEndTime());
            binding.cardCollegeChipView.setText(store.getCollege());
        }

    }
}
