package com.kannur.kalolsavam;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;
import com.kannur.kalolsavam.app.Utilities;

import java.io.File;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by msi1364 on 02-03-2016.
 */
public class QRCodeReader extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    private static final int CAMERA_REQUEST = 1338;
    String TAG = "QR Reader";
    // camera permission
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA,

    };

    // to set the permission for camera
    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        mScannerView.setFocusable(true);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (canAccessCamera()) {
                    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                    mScannerView.startCamera();
                }else{
                    Utilities.showToast(this,"Permission denied");
                    finish();
                    return;
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!canAccessCamera()) {
                Log.v("no permis", "camera");
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            } else {
                mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();          // Start camera on resume

            }
        } else {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)/
       /* if(!rawResult.getText().toString().contains("STAGE"))
            return;*/

        Intent intent = new Intent(QRCodeReader.this, QRData.class);
        intent.putExtra("Data",rawResult.getText());
        startActivity(intent);
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}