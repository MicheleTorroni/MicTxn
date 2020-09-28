package com.example.michi3.transactions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michi3.Activity_add;
import com.example.michi3.Activity_modify;
import com.example.michi3.Database.MyViewModel;
import com.example.michi3.Interfaces.OnItemListener;
import com.example.michi3.Interfaces.OnLongItemListener;
import com.example.michi3.R;
import com.example.michi3.Spinners.SpinnerACAdapter;
import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;
import com.example.michi3.categories.CategoryAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SuppressLint("ClickableViewAccessibility")
public class Fragment_Transactions extends Fragment implements OnItemListener, OnLongItemListener {
    //FIELDS
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    MyViewModel transactionViewModel;
    ViewGroup container;
    private ImageButton fab;
    private ImageButton searchButton;
    private EditText searchEditText;
    private Activity activity;
    private List<Account> accountList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<Transaction> transactionList = new ArrayList<>();
    private View tutorial;
    //CONSTRUCTOR
    public Fragment_Transactions() {
    }

    //ONCREATE
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        transactionViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        this.container = container;
        activity = getActivity();
        bindGraphic(view);

        onClickFab(fab, activity, accountList, categoryList);
        onClickSearchButton(searchButton, searchEditText, activity);

        if(transactionViewModel.getTransactions().getValue() != null) {
            adapter = new TransactionAdapter(transactionViewModel.getTransactions().getValue(), getContext(), this, this, getActivity());
            recyclerView.setAdapter(adapter);
        }


        transactionViewModel.getTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                List<Transaction> list = new ArrayList<>();
                for (Transaction transaction : transactions) {
                    list.add(transaction);
                }
                if(list.isEmpty()){
                    tutorial.setVisibility(View.VISIBLE);
                } else {
                    transactionList.clear();
                    transactionList.addAll(list);
                    tutorial.setVisibility(View.INVISIBLE);
                    updateRecyclerView();
                }

            }
        });
        transactionViewModel.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                accountList.clear();
                List<Account> list = new ArrayList<>();
                for (Account account : accounts) {
                    list.add(account);
                    accountList.add(account);
                }
                onClickFab(fab, activity, accountList, categoryList);
            }
        });
        transactionViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                List<Category> list = new ArrayList<>();
                for (Category category : categories) {
                    list.add(category);
                    categoryList.add(category);
                }
                onClickFab(fab, activity, accountList, categoryList);
            }
        });







        return view;
    }

    private void updateRecyclerView(){
        adapter = new TransactionAdapter(transactionViewModel.getTransactions().getValue(), getContext(),this, this, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void onClickFab(final ImageButton fab, final Activity activity, final List<Account> accounts, final List<Category> categories){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //CAMBIO COLORE
                fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_transactions_outline_24dp)); //placeholder icon
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_transactions_add_24dp));
                        fab.setBackgroundTintList(null);
                        fab.setBackgroundResource(R.drawable.rounded_tl);
                    }
                }, 50);
                //FUNZIONAMENTO EFFETTIVO
                if (!accountList.isEmpty() && !categoryList.isEmpty()) {
                    Intent addIntent = new Intent(activity, Activity_add.class);
                    String addTransactions = "addTransactions";
                    addIntent.putExtra("fragment",addTransactions);
                    startActivity(addIntent);
                } else {
                    Toast.makeText(getContext(), "please create an account and a category before", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onClickSearchButton(final ImageButton searchButton, final EditText searchEditText, final Activity activity){
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!transactionList.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (searchEditText.getVisibility() == View.GONE) {
                        searchButton.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.placeholder_cancel_24dp)); //placeholder icon
                        searchEditText.setVisibility(View.VISIBLE);
                        searchEditText.requestFocus();
                        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                        search(searchEditText);
                    } else {
                        searchButton.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.placeholder_search_24dp)); //placeholder icon
                        searchEditText.setVisibility(View.GONE);
                        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                        adapter.getFilter().filter(null);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "you have no transactions :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void search(final EditText searchEditText){
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    adapter.getFilter().filter(searchEditText.getText().toString());
                    recyclerView.setAdapter(adapter);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int adapterPosition) {
        final View view = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(adapterPosition)).itemView;
        final ConstraintLayout cardView = view.findViewById(R.id.single_card);
        final View expandableView = cardView.findViewById(R.id.expandableView);
        TransitionManager.beginDelayedTransition(cardView, new Slide(Gravity.TOP));
        expandableView.setVisibility(expandableView.getVisibility()== View.GONE ?
                View.VISIBLE : View.GONE );
    }

    @Override
    public void onLongItemClick(int adapterPosition) {
        final View view = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(adapterPosition)).itemView;
        final ConstraintLayout cardView = view.findViewById(R.id.single_card);
        final View expandableView = cardView.findViewById(R.id.expandableView);
        double balance = Double.valueOf(((TextView)cardView.findViewById(R.id.transactionsBalanceTextView)).getText().toString());
        String accountName = ((TextView)expandableView.findViewById(R.id.transactionAccountTextView)).getText().toString();
        String categoryName = ((TextView)expandableView.findViewById(R.id.transactionCategoryTextView)).getText().toString();
        String transactionTitle = ((TextView)cardView.findViewById(R.id.transactionTitleTextView)).getText().toString();
        String transactionDate =  ((TextView)expandableView.findViewById(R.id.transactionsDateTextView)).getText().toString();
        openDeleteDialog(transactionTitle, transactionDate, accountName, categoryName, balance);
    }

    private void bindGraphic(View view){
        fab = view.findViewById(R.id.imageButtonTransactionsAdd);
        tutorial = view.findViewById(R.id.tutorial_transaction);
        searchButton = view.findViewById(R.id.floatingActionButtonSearch);
        searchEditText = view.findViewById(R.id.searchEditText);
        recyclerView = view.findViewById(R.id.recycler_view_transactions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public  void openDeleteDialog(final String transactionTitle, final String transactionDate,final String accountName, final String categoryName, final double balance){
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        View myView = getLayoutInflater().inflate(R.layout.z_delete_dialog, null);
        ((TextView)myView.findViewById(R.id.deleteObject)).setText(transactionTitle);
        myBuilder.setView(myView);
        final AlertDialog dialog = myBuilder.create();
        ((Button)myView.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account tmpAccount = transactionViewModel.findAccountByName(accountName);
                if(tmpAccount != null) {
                    transactionViewModel.decreaseBalanceAccount(tmpAccount.getId(), -balance);
                }
                Category tmpCategory = transactionViewModel.findCategoryByName(categoryName);
                if (tmpCategory != null) {
                    transactionViewModel.increaseBalanceCategory(tmpCategory.getId(), -balance);
                }

                transactionViewModel.removeTransactionByTitleAndDate(transactionTitle, transactionDate);
                dialog.dismiss();
            }
        });

        ((Button)myView.findViewById(R.id.keepButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

