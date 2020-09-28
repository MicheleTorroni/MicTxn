package com.example.michi3.accounts;

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

import java.util.List;

//"costruttore" della recycler view
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accountList;
    private Context context;
    private OnItemListener listener;

    public AccountAdapter(List<Account> accountList, Context context, OnItemListener listener) {
        this.accountList = accountList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_accounts, parent, false);
        return new AccountViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, final int position) {
        Account account = getAccountAdapter(position);
        holder.accountNameTextView.setText(account.getAccountName());
        holder.accountBalanceTextView.setText(String.valueOf(account.getAccountBalance()));
        holder.icon.setImageResource(account.getIcon());
    }

    public Account getAccountAdapter(int position){
        return accountList.get(position);
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView accountNameTextView;
        public TextView accountBalanceTextView;
        public ImageView icon;
        OnItemListener itemListener;

        public AccountViewHolder(@NonNull View itemView, OnItemListener listener) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.accountNameTextView);
            accountBalanceTextView = itemView.findViewById(R.id.accountBalanceTextView);
            icon = itemView.findViewById(R.id.accountIcon);
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
