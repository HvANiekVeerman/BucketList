package com.example.bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface BucketItemDao {

    @Insert
    void insert(BucketItem bucketItem);

    @Delete
    void delete(BucketItem bucketItem);

    @Delete
    void delete(List<BucketItem> bucketItems);

    @Query("SELECT * from bucketItem_table")
    List<BucketItem> getAllBucketItems();
}
