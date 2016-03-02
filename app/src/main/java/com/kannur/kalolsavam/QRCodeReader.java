package com.kannur.kalolsavam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by msi1364 on 02-03-2016.
 */
public class QRCodeReader extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String TAG = "QR Reader";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        mScannerView.setFocusable(true);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
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
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Intent intent = new Intent(QRCodeReader.this, QRData.class);
        intent.putExtra("Data",rawResult.getText());
        startActivity(intent);
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}