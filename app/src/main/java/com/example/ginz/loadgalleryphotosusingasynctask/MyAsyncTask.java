package com.example.ginz.loadgalleryphotosusingasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Activity mActivity;
    private  TextView textView;

    public MyAsyncTask(Activity activity){
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mActivity, "Pre Execute", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i=0;i<=100;i++)
        {
            //nghỉ 100 milisecond thì tiến hành update UI
            SystemClock.sleep(100);
            //khi gọi hàm này thì onProgressUpdate sẽ thực thi
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressBar = mActivity.findViewById(R.id.progress_bar_demo);
        int number = values[0];
        progressBar.setProgress(number);

       textView = mActivity.findViewById(R.id.text_status);
        textView.setText(number + " %");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Toast.makeText(mActivity, "on Post Execute", Toast.LENGTH_SHORT).show();
        textView.setText("DA LOAD XONG");
    }
}
