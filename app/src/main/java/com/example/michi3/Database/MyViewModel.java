package com.example.michi3.Database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;
import com.example.michi3.transactions.Transaction;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 *
 * The data stored by ViewModel is not for long term. (Until activity is destroyed)
 */
public class MyViewModel extends AndroidViewModel {

    private MyRepository repository;

    private LiveData<List<Account>> accounts;
    private LiveData<List<Category>> categories;
    private LiveData<List<Transaction>> transactions;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
        accounts = repository.getAccounts();
        categories = repository.getCategories();
        transactions = repository.getTransactions();
    }

    public LiveData<List<Account>> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        repository.addAccount(account);
    }

    public void removeAccount(Account account){
        repository.removeAccount(account);
    }

    public Account findAccountByID(int id){
        return repository.findAccountByID(id);
    }

    public void decreaseBalanceAccount(final int id, final double money) {
        repository.decreaseBalanceAccount(id,money);
    }

    public Account findAccountByName(String name){
        return repository.findAccountByName(name);
    }

    public Account getAccount(Account account){
        return repository.getAccount(account);
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public void addCategory(Category category){
        repository.addCategory(category);
    }

    public void removeCategory(Category category){
        repository.removeCategory(category);
    }

    public Category findCategoryByID(int id){
        return repository.findCategoryByID(id);
    }

    public void increaseBalanceCategory(final int id, final double money) {
        repository.increaseCategoryBalance(id, money);
    }

    public Category findCategoryByName(String name){
        return repository.findCategoryByName(name);
    }

    public Category getCategory(Category category){
        return repository.getCategory(category);
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        repository.addTransaction(transaction);
    }

    public void removeTransaction(Transaction transaction){
        repository.removeTransaction(transaction);
    }

    public void removeTransactionByTitleAndDate(String transactionTitle,String transactionDate){
        repository.removeTransactionByTitleAndDate(transactionTitle, transactionDate);
    }

    public Transaction findTransactionByID(int id){
        return repository.findTransactionById(id);
    }

    public Transaction getTransaction(Transaction transaction){
        return repository.getTransaction(transaction);
    }
}
