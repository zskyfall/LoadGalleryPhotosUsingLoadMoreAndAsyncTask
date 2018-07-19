package com.example.ginz.loadgalleryphotosusingasynctask;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.ginz.loadgalleryphotosusingasynctask.model.Item;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class LoadImageAsyncTask extends AsyncTask<Void, Void, List<File>> {

    private static final String TAG = "CHECK_LOAD_IMAGE";
    private static final String PATH = Environment.getExternalStorageDirectory().getPath().toString();
    private static final String PATH_CAMERA = PATH + "/DCIM/Facebook";
    private List<Item> mPhotos;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mPhotos = new ArrayList<>();
    }

    @Override
    protected List<File> doInBackground(Void... voids) {
        List<File> images = getAllImage();
        for (File i : images){
            Item item = new Item(i.toString());
            mPhotos.add(item);
            Log.d(TAG, i.toString());
        }
        return images;
    }

    @Override
    protected void onPostExecute(List<File> files) {
        super.onPostExecute(files);
    }

    private List<File> getAllImage(){
        List<File> listFiles = new ArrayList<>();
        File file = new File(PATH_CAMERA);
        File[] images = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getAbsolutePath().endsWith(".png")
                        || file.getAbsolutePath().endsWith(".jpg")
                        || file.getAbsolutePath().endsWith("jpeg");
            }
        });

        for(File f : images){
            listFiles.add(f);
        }

        return listFiles;
    }
}
