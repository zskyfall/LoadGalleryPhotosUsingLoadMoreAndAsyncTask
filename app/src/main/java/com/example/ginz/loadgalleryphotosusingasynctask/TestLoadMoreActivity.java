package com.example.ginz.loadgalleryphotosusingasynctask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ginz.loadgalleryphotosusingasynctask.adapter.MyRecyclerViewAdapter;
import com.example.ginz.loadgalleryphotosusingasynctask.helper.EndlessRecyclerViewScrollListener;
import com.example.ginz.loadgalleryphotosusingasynctask.model.Item;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestLoadMoreActivity extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener, MyRecyclerViewAdapter.RetryLoadMoreListener {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Item> mPhotos;

    private int currentPage;
    private static final String TAG = "CHECK_LOAD_IMAGE";
    private static final String MESSAGE_PERMISSION_GRANTED = "Permission Granted";
    private static final String MESSAGE_PERMISSION_REVOKED = "Permission Revoked";
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final String PATH_CAMERA = PATH + "/DCIM/Facebook";
    private static final int REQUEST_READ_STORAGE_PERMISSION_CODE = 3;
    private static final int SPAN_COUNT = 2;
    private boolean emulatorLoadMoreFaild = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_load_more);

        getSupportActionBar().setTitle("Recycler With Loadmore");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mPhotos = new ArrayList<>();

        if(isReadStoragePermissionGranted()){
//            File[] images = getAllImage();
//            for (File i : images){
//                Item item = new Item(i.toString());
//                mPhotos.add(item);
//                Log.d(TAG, i.toString());
//            }
            try {
                List<File> listFiles = new LoadImageAsyncTask().execute().get();
                for(File i : listFiles){
                    Item item = new Item(i.toString());
                    mPhotos.add(item);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // set up the RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, this, this);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager){
            @Override
            public void onLoadMore(final int page) {
                Log.i("TAG", "load more "+page);
                currentPage = page;
                loadMore(page);
            }
        });

        adapter.add(mPhotos);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRetryLoadMore() {
        loadMore(currentPage);
    }

    /**
     * The logic is only for testing
     */
    private void loadMore(final int page){
        adapter.startLoadMore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(page == 2 && emulatorLoadMoreFaild){
                    adapter.onLoadMoreFailed();
                    emulatorLoadMoreFaild = false;
                    return;
                }
                if(page == 3){
                    adapter.onReachEnd();
                    return;
                }
                adapter.add(mPhotos);
            }
        }, 500);
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