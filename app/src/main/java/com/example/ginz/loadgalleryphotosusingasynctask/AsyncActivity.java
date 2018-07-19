package com.example.ginz.loadgalleryphotosusingasynctask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AsyncActivity extends AppCompatActivity {
    private Button mButtonStart;
    private MyAsyncTask mMyAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        mButtonStart = findViewById(R.id.button_start);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyAsyncTask = new MyAsyncTask(AsyncActivity.this);
                mMyAsyncTask.execute();
            }
        });
    }
}
