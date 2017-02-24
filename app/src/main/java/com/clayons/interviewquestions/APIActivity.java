package com.clayons.interviewquestions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Problem 2.
 * Consult http://jsonplaceholder.typicode.com/posts and code up a single activity to show a list of all items.
 * It should query this server and retrieve and populate data
 *
 */
public class APIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
    }
}
