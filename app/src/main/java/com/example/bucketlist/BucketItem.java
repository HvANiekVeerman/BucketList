package com.example.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketItem_table")

public class BucketItem {

    @PrimaryKey(autoGenerate = true)
            private Long id;

    @ColumnInfo(name = "name")
        private String mTitle;
        private String mDescription;

    public BucketItem(String title, String description) {
        this.mTitle = title;
        this.mDescription = description;
    }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public void setmTitle(String title) { this.mTitle = title; }

    public String getTitle() { return mTitle; }

    public void setmDescription(String description) { this.mDescription = description; }

    public String getDescription() { return mDescription; }

    @Override
    public String toString() {
        return "BucketItem{" +
                "id=" + id +
                ", name='" + mTitle + '\'' +
                ", description='" + mDescription + '\'' +
                '}';
    }
}
