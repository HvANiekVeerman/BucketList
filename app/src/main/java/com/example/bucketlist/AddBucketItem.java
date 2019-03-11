package com.example.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddBucketItem extends AppCompatActivity {

    static String mTitle, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bucketitem);

        Button addPortalButton = findViewById(R.id.bAddBucketitem);
        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText etDescription = findViewById(R.id.etDescription);

        addPortalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTitle = etTitle.getText().toString();
                mDescription = etDescription.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("newTitle", mTitle);
                returnIntent.putExtra("newDescription", mDescription);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}