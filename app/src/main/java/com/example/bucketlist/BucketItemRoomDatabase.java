package com.example.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BucketItem.class}, version = 1, exportSchema = false)

public abstract class BucketItemRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "bucketItem_database";
    public abstract BucketItemDao bucketItemDao();
    private static volatile BucketItemRoomDatabase INSTANCE;

    public static BucketItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BucketItemRoomDatabase.class) {
                if (INSTANCE == null) {

                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BucketItemRoomDatabase.class, NAME_DATABASE).build();
                }
            }
        }
        return INSTANCE;
    }

}