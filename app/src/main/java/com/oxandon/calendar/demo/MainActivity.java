package com.oxandon.calendar.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSingle(View v) {
        startActivity(new Intent(this, SingleMonthActivity.class));
    }

    public void onClickMulti(View v) {
        startActivity(new Intent(this, MultiMonthActivity.class));
    }
}
