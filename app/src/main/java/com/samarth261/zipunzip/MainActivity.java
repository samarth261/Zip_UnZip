package com.samarth261.zipunzip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void someRandomMethod(View view) {
        //************************
        byte b[] = new byte[8092];
        ZipFile zippy = null;
        String folderRootPath = "/sdcard/ASD/";
        try {
            zippy = new ZipFile("/sdcard/ASD.zip");
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        new File(folderRootPath).mkdir();
        Enumeration all = zippy.entries();
        while (all.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) all.nextElement();
            if (entry.isDirectory()) {
                Log.d("create", folderRootPath + entry.getName());
                new File(folderRootPath + entry.getName()).mkdir();
            } else {
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    fos = new FileOutputStream(folderRootPath + entry.getName());
                } catch (Exception e) {
                    Log.e("error", "fos not created" + "\n" + e.getMessage());
                }
                try {
                    is = zippy.getInputStream(entry);
                } catch (Exception e) {
                    Log.e("error", "is not created\n" + e.getMessage());
                }
                int n = 0;
                try {
                    while ((n = is.read(b)) > 0) {
                        fos.write(b, 0, n);
                    }
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                try {
                    is.close();
                    fos.close();
                } catch (Exception e) {
                    Log.e("error", "couldn't close\n" + e.getMessage());
                }
            }
        }

        //***********************
    }
}
