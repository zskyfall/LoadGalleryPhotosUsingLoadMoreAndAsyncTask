package com.example.ginz.loadgalleryphotosusingasynctask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRcvAdapter;
    private List<File> mPhotos;
    private GridLayoutManager mLayoutManager;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final String PATH_CAMERA = PATH + "/DCIM/Facebook";
    private static final String TAG = "CHECK_LOAD_IMAGE";
    private static final String MESSAGE_PERMISSION_GRANTED = "Permission Granted";
    private static final String MESSAGE_PERMISSION_REVOKED = "Permission Revoked";
    private static final int SPAN_COUNT = 2;
    private static final int REQUEST_READ_STORAGE_PERMISSION_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mPhotos = new ArrayList<>();

        if(isReadStoragePermissionGranted()){
            File[] images = getAllImage();
            for (File i : images){
                mPhotos.add(i);
                Log.d(TAG, i.toString());
            }
        }

        mLayoutManager = new GridLayoutManager(getApplicationContext(),SPAN_COUNT);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRcvAdapter = new RecyclerViewAdapter(getApplicationContext(), mPhotos);
        mRecyclerView.setAdapter(mRcvAdapter);
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,MESSAGE_PERMISSION_GRANTED);
                return true;
            } else {

                Log.v(TAG,MESSAGE_PERMISSION_REVOKED);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_PERMISSION_CODE);
                return false;
            }
        }
        else {
            Log.v(TAG,MESSAGE_PERMISSION_GRANTED);
            return true;
        }
    }

    private File[] getAllImage(){
        File file = new File(PATH_CAMERA);
        File[] images = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getAbsolutePath().endsWith(".png")
                        || file.getAbsolutePath().endsWith(".jpg")
                        || file.getAbsolutePath().endsWith("jpeg");
            }
        });

        return images;
    }

}
