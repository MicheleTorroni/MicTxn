package com.example.michi3.Database;

import android.content.Context;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;
import com.example.michi3.transactions.Transaction;

@Database(entities = {Account.class, Category.class, Transaction.class}, version = 1, exportSchema = false)
abstract class MyRoomDatabase extends RoomDatabase {

    abstract MyDAO accountDAO();

    //Singleton instance
    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //ExecutorService with a fixed thread pool that you will use to run database operations
    // asynchronously on a background thread.
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * It'll create the database the first time it's accessed, using Room's database
     * @param context the context of the Application
     * @return the singleton
     */
    static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

