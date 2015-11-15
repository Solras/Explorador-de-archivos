package com.example.pica.zexploradorarchivos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class SearchResultsActivity extends AppCompatActivity {

    private android.widget.ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);
        this.listView = (ListView) findViewById(R.id.listView);
    }


}