package com.example.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    GestureDetector gestureDetector;
    private BucketItemRoomDatabase db;
    ArrayList<BucketItem> mBucketItems = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = BucketItemRoomDatabase.getDatabase(this);

        // Set up the RecyclerView
        mRecyclerView = findViewById(R.id.rvBucketList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapter(this, mBucketItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        FloatingActionButton addButton = findViewById(R.id.fabNewBucketitem);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddBucketItem.class), 1);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = mRecyclerView.getChildAdapterPosition(child);
                    deleteBucketItem(mBucketItems.get(adapterPosition));
                }
            }
        });
        mRecyclerView.addOnItemTouchListener(this);
        getAllBucketItems();
    }

    public void insertBucketItem(final BucketItem bucketItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketItemDao().insert(bucketItem);
                getAllBucketItems();
            }
        });
    }

    private void deleteBucketItem(final BucketItem bucketItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketItemDao().delete(bucketItem);
                getAllBucketItems();
            }
        });
    }

    private void deleteAllBucketItems(final List<BucketItem> bucketItems) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketItemDao().delete(bucketItems);
                getAllBucketItems();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Checking the item id of our menu item.
        if (item.getItemId() == R.id.action_delete_item) {
            // Deleting all items and notifying our list adapter of the changes.
            deleteAllBucketItems(mBucketItems);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllBucketItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<BucketItem> mBucketItems = db.bucketItemDao().getAllBucketItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(mBucketItems);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String mTitle = data.getStringExtra("newTitle");
                String mDescription = data.getStringExtra("newDescription");

                BucketItem bucketItem = new BucketItem(mTitle,mDescription);
                insertBucketItem(bucketItem);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void updateUI(List<BucketItem> bucketItems) {
        mBucketItems.clear();
        mBucketItems.addAll(bucketItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void onItemClick(View view, int position) {
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}