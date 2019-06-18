package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ListView mResultsListView = findViewById(R.id.results_view);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
            R.layout.activity_list_view, RemoteServer.getRecordings());

        mResultsListView.setAdapter(adapter);
    }
}
