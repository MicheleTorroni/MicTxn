package com.example.michi3.Database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;
import com.example.michi3.transactions.Transaction;

/**
 * A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 */
@Dao
public interface MyDAO {
    // The selected on conflict strategy ignores a new person
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addAccount(Account account);

    @Delete
    void removeAccount(Account account);

    @Query("SELECT * from accounts_saved ORDER BY accounts_id DESC")
    LiveData<List<Account>> getAccounts();


    @Query("SELECT * FROM accounts_saved WHERE accounts_id LIKE :id")
    Account findAccountByID(int id);

    @Query("SELECT * FROM accounts_saved WHERE accountName LIKE :name")
    Account findAccountByName(String name);

    @Query("UPDATE accounts_saved SET realAccountBalance = realAccountBalance - :money WHERE accounts_id LIKE :id")
    void decreaseAccountBalance(int id, double money);

    // The selected on conflict strategy ignores a new person
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCategory(Category category);

    @Delete
    void removeCategory(Category category);

    @Query("SELECT * from categories_saved ORDER BY categories_id DESC")
    LiveData<List<Category>> getCategories();


    @Query("SELECT * FROM categories_saved WHERE categories_id LIKE :id")
    Category findCategoryByID(int id);

    @Query("SELECT * FROM categories_saved WHERE categoryName LIKE :name")
    Category findCategoryByName(String name);

    @Query("UPDATE categories_saved SET realCategoryBalance = realCategoryBalance + :money WHERE categories_id LIKE :id")
    void increaseCategoryBalance(int id, double money);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTransaction(Transaction transaction);

    @Delete
    void removeTransaction(Transaction transaction);

    @Query("SELECT * from transactions_saved ORDER BY transactions_id DESC")
    LiveData<List<Transaction>> getTransactions();

    @Query("SELECT * FROM transactions_saved WHERE transactions_id LIKE :id")
    Transaction findTransactionById(int id);

    @Query("SELECT * FROM transactions_saved WHERE transactions_title LIKE :transactionTitle AND transaction_date LIKE :transactionDate")
    Transaction findTransactionByTitleAndDate(String transactionTitle,String transactionDate);

}

