package com.example.michi3.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michi3.Interfaces.OnItemListener;
import com.example.michi3.R;
import com.example.michi3.accounts.AccountAdapter;

import java.util.List;

//"costruttore" della recycler view
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Context context;
    private OnItemListener listener;

    public CategoryAdapter(List<Category> categoryList, Context context, OnItemListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_categories, parent, false);
        return new CategoryViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        Category category = getCategoryAdapter(position);
        holder.categoryNameTextView.setText(category.getCategoryName());
        holder.categoryBalanceTextView.setText(String.valueOf(category.getCategoryBalance()));
        holder.icon.setImageResource(category.getIcon());
    }

    public Category getCategoryAdapter(int position){
        return categoryList.get(position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView categoryNameTextView;
        public TextView categoryBalanceTextView;
        public ImageView icon;
        OnItemListener itemListener;

        public CategoryViewHolder(@NonNull View itemView, OnItemListener listener) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryBalanceTextView = itemView.findViewById(R.id.categoryBalanceTextView);
            icon = itemView.findViewById(R.id.categoryIcon);
            itemListener = listener;
            itemView.setOnClickListener(this);
        }

        //called when an entry is cliccked and sent to (e.g. Fragment_Accounts)
        @Override
        public void onClick(View v) {
            itemListener.onItemClick(getAdapterPosition());
        }
    }

}

