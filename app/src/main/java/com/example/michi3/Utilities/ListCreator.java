package com.example.michi3.Utilities;

import com.example.michi3.Spinners.SpinnerACItem;
import com.example.michi3.Spinners.SpinnerIconItem;
import com.example.michi3.R;
import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class ListCreator {
    ArrayList<SpinnerIconItem> listIcon = new ArrayList<>();
    ArrayList<SpinnerACItem> listAccounts = new ArrayList<>();
    ArrayList<SpinnerACItem> listCategories = new ArrayList<>();


        public ArrayList<SpinnerIconItem> getAccountIcons(){
            listIcon.add(new SpinnerIconItem(R.drawable.placeholder_account_wallett_24dp));
            listIcon.add(new SpinnerIconItem(R.drawable.placeholder_account_bank_24dp));
            listIcon.add(new SpinnerIconItem(R.drawable.placeholder_account_card_24dp));
            listIcon.add(new SpinnerIconItem(R.drawable.placeholder_account_money_24dp));
            return listIcon;
        }

    public ArrayList<SpinnerIconItem> getCategoryIcons(){
        listIcon.add(new SpinnerIconItem(R.drawable.placeholder_category_amazon_24dp));
        listIcon.add(new SpinnerIconItem(R.drawable.placeholder_category_food_24dp));
        listIcon.add(new SpinnerIconItem(R.drawable.placeholder_category_shopping_24dp));
        listIcon.add(new SpinnerIconItem(R.drawable.placeholder_category_transport_24dp));
        return listIcon;
    }

    public ArrayList<SpinnerACItem> getAccounts(List<Account> accounts){
            listAccounts.clear();
        for (Account account: accounts) {
            listAccounts.add(new SpinnerACItem(account.getAccountName(), account.getIcon(), account.getId()));
        }
        return listAccounts;
    }

    public ArrayList<SpinnerACItem> getCategories(List<Category> categories){
        listCategories.clear();
        for (Category category: categories) {
            listCategories.add(new SpinnerACItem(category.getCategoryName(), category.getIcon(), category.getId()));
        }
        return listCategories;
    }

}
