package com.example.michi3.transactions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.michi3.Interfaces.OnItemListener;
import com.example.michi3.Interfaces.OnLongItemListener;
import com.example.michi3.R;
import com.example.michi3.Utilities.PhotoUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//"costruttore" della recycler view
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>  implements Filterable {

    private List<Transaction> transactionList;
    private List<Transaction> transactionListFull = new ArrayList<>();
    private Context context;
    private OnItemListener listener;
    private OnLongItemListener longItemListener;
    private Activity activity;

    public TransactionAdapter(List<Transaction> transactionList, Context context, OnItemListener listener, OnLongItemListener longItemListener, Activity activity) {
        this.transactionList = transactionList;
        this.context = context;
        this.listener = listener;
        this.activity = activity;
        this.longItemListener = longItemListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transactions, parent, false);
        return new TransactionViewHolder(v, listener, longItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, final int position) {
        Transaction transaction = getTransactionAdapter(position);
        String imagePath = transaction.getImageResource();

        holder.balance.setText(String.valueOf(transaction.getBalance()));
        holder.title.setText(transaction.getTitle());
        holder.account.setText(transaction.getAccount());
        holder.accountIcon.setImageResource(transaction.getAccountIcon());
        holder.category.setText(transaction.getCategory());
        holder.categoryIcon.setImageResource(transaction.getCategoryIcon());
        holder.date.setText(transaction.getDate());
        holder.place.setText(transaction.getPlace());
        imagePath = transaction.getImageResource();
        if (imagePath.contains("ic_")) {
            Drawable drawable = activity.getDrawable(activity.getResources()
                    .getIdentifier(transaction.getImageResource(), "drawable",
                            activity.getPackageName()));
            holder.yourImage.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = PhotoUtility.getImageBitmap(activity, Uri.parse(imagePath));
            if (bitmap != null) {
                holder.yourImage.setImageBitmap(bitmap);
            }
        }
    }

    public Transaction getTransactionAdapter(int position){
        return transactionList.get(position);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        /**
         * Called to filter the data according to the constraint
         * @param constraint constraint used to filtered the data
         * @return the result of the filtering
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transaction> filteredList = new ArrayList<>();

            //if you have no constraint --> return the full list
            if (constraint == null || constraint.length() == 0) {
                if(!transactionListFull.isEmpty()){
                    filteredList.addAll(transactionListFull);
                } else {
                    filteredList.addAll(transactionList);
                }
            } else {
                //else apply the filter and return a filtered list
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Transaction item : transactionList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) ||
                            item.getAccount().toLowerCase().contains(filterPattern) ||
                            item.getCategory().toLowerCase().contains(filterPattern) ||
                            item.getDate().toLowerCase().contains(filterPattern) ||
                            item.getPlace().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /**
         * Called to publish the filtering results in the user interface
         * @param constraint constraint used to filter the data
         * @param results the result of the filtering
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(transactionListFull.isEmpty()){
                transactionListFull.addAll(transactionList);
            }
            transactionList.clear();
            //cardItemList.addAll((List) results.values);
            List<?> result = (List<?>) results.values;
            for (Object object : result) {
                if (object instanceof Transaction) {
                    transactionList.add((Transaction) object);
                }
            }
            //warn the adapter that the dare are changed after the filtering
            notifyDataSetChanged();
        }
    };

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView balance;
        public TextView title;
        public TextView account;
        public ImageView accountIcon;
        public TextView category;
        public ImageView categoryIcon;
        public TextView date;
        public TextView place;
        public ImageView yourImage;
        OnItemListener itemListener;
        OnLongItemListener longItemListener;

        public TransactionViewHolder(@NonNull final View itemView, OnItemListener listener, OnLongItemListener longItemListener) {
            super(itemView);
            balance = itemView.findViewById(R.id.transactionsBalanceTextView);
            title = itemView.findViewById(R.id.transactionTitleTextView);
            account = itemView.findViewById(R.id.transactionAccountTextView);
            accountIcon = itemView.findViewById(R.id.logo_account);
            category = itemView.findViewById(R.id.transactionCategoryTextView);
            categoryIcon = itemView.findViewById(R.id.logo_category);
            date = itemView.findViewById(R.id.transactionsDateTextView);
            place = itemView.findViewById(R.id.transactionsPlaceTextView);
            yourImage = itemView.findViewById(R.id.transactionsImage);
            itemListener = listener;
            this.longItemListener = longItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //called when an entry is cliccked and sent to (e.g. Fragment_Accounts)
        @Override
        public void onClick(View v) {
            itemListener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v){
            longItemListener.onLongItemClick(getAdapterPosition());
            return true;
        }
    }

}

