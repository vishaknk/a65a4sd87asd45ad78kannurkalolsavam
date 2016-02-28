package com.kannur.kalolsavam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ExcelActivity extends AppCompatActivity {

    private static String TAG = "ExcelLog";
    private static String FILE_NAME = "collegelist.xls";
    private ListView mListView;
    String filepath = "";
    ArrayList<ExcelModel> mExcelModel = new ArrayList<ExcelModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Colleges");
        setContentView(R.layout.activity_excel_list);
        mListView = (ListView) findViewById(R.id.listView);
        readExcelFile(this);
    }

    private void readExcelFile(Context context) {
        try{
            // Creating Input Stream
            InputStream myInput;
            //  Don't forget to Change to your assets folder excel sheet
            myInput = getAssets().open(FILE_NAME);
            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();

            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                ExcelModel excelModel = new ExcelModel();
                int mCount = 0;
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.d(TAG, "Cell Value: " + myCell.toString());
                    switch (mCount){
                        case 0:
                            excelModel.setmId(myCell.toString());
                            break;
                        case 1:
                            excelModel.setmCollegeName(myCell.toString());
                            break;
                        case 2:
                            excelModel.setmPassword(myCell.toString());
                            break;
                    }
                    mCount ++;
                }
                mExcelModel.add(excelModel);
            }
            ExcelAdapter mExcelAdapter = new ExcelAdapter(this, mExcelModel);
            mListView.setAdapter(mExcelAdapter);
        }catch (Exception e){e.printStackTrace(); }

        return;
    }


    //adapter for populating the ListView
    private class ExcelAdapter extends BaseAdapter {

        private List<ExcelModel> originalList;
        private List<ExcelModel> dataList;   // Values to be displayed

        public ExcelAdapter(Context context, ArrayList<ExcelModel> list) {
            super();
            this.dataList = new ArrayList<ExcelModel>();
            this.dataList.addAll(list);
            this.originalList = new ArrayList<ExcelModel>();
            this.originalList.addAll(list);
        }

        private class ViewHolder {
            TextView tvName;
            TextView tvId,slno;
            TextView tvPassword;
            LinearLayout mMainLayout;
        }

        @Override
        public int getCount() {
            return originalList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item_excel_data, null);

                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
                holder.slno = (TextView) convertView.findViewById(R.id.slno);
                holder.tvPassword = (TextView) convertView.findViewById(R.id.tv_password);
                holder.mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
                convertView.setTag(holder);

            } else
                holder = (ViewHolder) convertView.getTag();
            if(position% 2 == 0)
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_outer_grey));
            else
                holder.mMainLayout.setBackgroundColor(getResources().getColor(R.color.bg_leading_dist));


            holder.tvName.setText("" + originalList.get(position).getmCollegeName());
            holder.tvId.setText("" + originalList.get(position).getmId());
            holder.slno.setText(String.valueOf(position + 1));
            holder.tvPassword.setText("" + originalList.get(position).getmPassword());
            holder.tvPassword.setVisibility(View.GONE);
            //display the values

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
