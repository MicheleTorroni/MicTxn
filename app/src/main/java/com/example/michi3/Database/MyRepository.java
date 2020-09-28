package com.example.michi3.Database;

import android.app.Application;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;
import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;
import com.example.michi3.transactions.Transaction;

public class MyRepository {
    private MyDAO myDAO;
    private LiveData<List<Account>> accounts;
    private LiveData<List<Category>> categories;
    private LiveData<List<Transaction>> transactions;

    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        myDAO = db.accountDAO();
        accounts = myDAO.getAccounts();
        categories = myDAO.getCategories();
        transactions = myDAO.getTransactions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Account>> getAccounts(){
        return accounts;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addAccount(final Account account) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.addAccount(account);
            }
        });
    }

    public Account findAccountByID(final int id){
        Future<Account> future;
        Account AccountSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Account>() {
                @Override
                public Account call() {
                    return myDAO.findAccountByID(id);
                }
            });
            AccountSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return AccountSelected;
    }

    public void decreaseBalanceAccount(final int id, final double money){
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.decreaseAccountBalance(id, money);
            }
        });
    }

    public Account findAccountByName(final String name){
        Future<Account> future;
        Account AccountSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Account>() {
                @Override
                public Account call() {
                    return myDAO.findAccountByName(name);
                }
            });
            AccountSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return AccountSelected;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void removeAccount(final Account account) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.removeAccount(account);
            }
        });
    }

    public Account getAccount(final Account account){
        Future<Account> future;
        Account AccountSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Account>() {
                @Override
                public Account call() {
                    return myDAO.findAccountByID(account.getId());
                }
            });
            AccountSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return AccountSelected;
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Category>> getCategories(){
        return categories;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addCategory(final Category category) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.addCategory(category);
            }
        });
    }

    public Category findCategoryByID(final int id){
        Future<Category> future;
        Category CategorySelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Category>() {
                @Override
                public Category call() {
                    return myDAO.findCategoryByID(id);
                }
            });
            CategorySelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return CategorySelected;
    }

    public void increaseCategoryBalance(final int id, final double money) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.increaseCategoryBalance(id, money);
            }
        });
    }

    public Category findCategoryByName(final String name){
        Future<Category> future;
        Category CategorySelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Category>() {
                @Override
                public Category call() {
                    return myDAO.findCategoryByName(name);
                }
            });
            CategorySelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return CategorySelected;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void removeCategory(final Category category) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.removeCategory(category);
            }
        });
    }

    public Category getCategory(final Category category){
        Future<Category> future;
        Category CategorySelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Category>() {
                @Override
                public Category call() {
                    return myDAO.findCategoryByID(category.getId());
                }
            });
            CategorySelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return CategorySelected;
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Transaction>> getTransactions(){
        return transactions;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addTransaction(final Transaction transaction) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.addTransaction(transaction);
            }
        });
    }

    public Transaction findTransactionById(final int id){
        Future<Transaction> future;
        Transaction TransactionSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Transaction>() {
                @Override
                public Transaction call() {
                    return myDAO.findTransactionById(id);
                }
            });
            TransactionSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return TransactionSelected;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void removeTransaction(final Transaction transaction) {
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDAO.removeTransaction(transaction);
            }
        });
    }

    public void removeTransactionByTitleAndDate(final String transactionTitle, final String transactionDate){
        Future<Transaction> future;
        Transaction TransactionSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Transaction>() {
                @Override
                public Transaction call() {
                    return myDAO.findTransactionByTitleAndDate(transactionTitle, transactionDate);
                }
            });
            TransactionSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        removeTransaction(TransactionSelected);
    }

    public Transaction getTransaction(final Transaction transaction){
        Future<Transaction> future;
        Transaction TransactionSelected = null;
        try {
            future = MyRoomDatabase.databaseWriteExecutor.submit(new Callable<Transaction>() {
                @Override
                public Transaction call() {
                    return myDAO.findTransactionById(transaction.getId());
                }
            });
            TransactionSelected = future.get();
        } catch (Exception e){
            Log.d("LAB", e.toString());
        }
        return TransactionSelected;
    }
}
