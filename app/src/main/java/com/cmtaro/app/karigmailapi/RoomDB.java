package com.cmtaro.app.karigmailapi;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Add database entities
@Database(entities = {MainData.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    // abstract 抽象class instanse
    private static RoomDB database;
    private static String DATABASE_NAME = "data_base";

    public synchronized  static RoomDB getInstance(Context context){
        //check condition
        if (database == null) {
            // database is null
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        // return database
        return database;
    };
    // Create Dao
    public abstract MainDao mainDao();

}
