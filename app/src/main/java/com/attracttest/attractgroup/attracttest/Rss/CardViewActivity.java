package com.attracttest.attractgroup.attracttest.Rss;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.attracttest.attractgroup.attracttest.R;

public class CardViewActivity extends Activity {
    TextView personName;
    TextView personAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardview_rss_activity);
        personName = findViewById(R.id.rss_title);
        personAge = findViewById(R.id.rss_description);


        personName.setText("Emma Wilson");
        personAge.setText("23 years old");



    }
}
