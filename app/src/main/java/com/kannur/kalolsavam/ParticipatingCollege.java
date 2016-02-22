package com.kannur.kalolsavam;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Priyesh on 14-02-2016.
 */
public class ParticipatingCollege extends AppCompatActivity {
    private ListView listView;
    private TreeMap<String,String>colleges;
    String url = "";
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Participating Colleges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_standings);
        listView = (ListView) findViewById(R.id.listview);
        colleges = new TreeMap<>();
        listView.setEmptyView(findViewById(android.R.id.empty));

        String coll = readComplexExcelFile(this, "College");
        Log.v("coll","-coll"+coll);
    }

    private File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File("file");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<String> read(String key) throws IOException {
        List<String> resultSet = new ArrayList<String>();

        File inputWorkbook = new File("file");
        if(inputWorkbook.exists()){
            Workbook w;
            try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                // Loop over column and lines
                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    if(cell.getContents().equalsIgnoreCase(key)){
                        for (int i = 0; i < sheet.getColumns(); i++) {
                            Cell cel = sheet.getCell(i, j);
                            resultSet.add(cel.getContents());
                        }
                    }
                    continue;
                }
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            resultSet.add("File not found..!");
        }
        if(resultSet.size()==0){
            resultSet.add("Data not found..!");
        }
        return resultSet;
    }

    public String readComplexExcelFile(Context context, String userInput){
        String requiredContents = "";
        try{

            try {
                AssetManager assetManager = getResources().getAssets();
                InputStream inputStream = null;
                inputStream = assetManager.open("collegelist.xlsx");
                file = createFileFromInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Workbook w;

            // Create a workbook using the File System
            w = Workbook.getWorkbook(file);

            // Get the first sheet from workbook
            Sheet sheet = w.getSheet(0);

            /** We now need something to iterate through the cells.**/
            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell cell = sheet.getCell(j, i);
                    if(cell.getContents().equalsIgnoreCase(userInput)){
                        Cell cellCorrespond = sheet.getCell(j+1, i);
                        requiredContents = cellCorrespond.getContents();
                        break;
                    }

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return requiredContents;
    }
}
