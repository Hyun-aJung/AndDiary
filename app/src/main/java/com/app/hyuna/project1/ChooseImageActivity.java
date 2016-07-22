package com.app.hyuna.project1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-18.
 */
public class ChooseImageActivity extends Activity{
    ProgressDialog mLoagindDialog;
    GridView mGvImageList;
    ImageAdapter mListAdapter;
    ArrayList<GalleryItemActivity> mThumbImageInfoList;
    ///////
    Button btnOk, btnCancel;
    GridView gridView;
    String userId;
    int listNum;
    final int MEMO=0,DRAW=1,POST=2,SET=3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_list);
        setTitle("Choose Images");


        Intent inIntent = getIntent();
        userId = inIntent.getStringExtra("userId");
        //listNum = inIntent.getExtra("list");


        mThumbImageInfoList = new ArrayList<GalleryItemActivity>();
        mGvImageList = (GridView) findViewById(R.id.listView);
        new DoFindImageList().execute();
    }


    private long findThumbList() {
        long returnValue = 0;

        // Select 하고자 하는 컬럼
        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };

        // 쿼리 수행
        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " desc ");
        //Cursor imageCursor = managedQuery(Uri.parse("/data/data/"), projection, null, null, MediaStore.Images.Media.DATE_ADDED + " desc ");

        if (imageCursor != null && imageCursor.getCount() > 0) {
            // 컬럼 인덱스
            int imageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int imageDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);

            // 커서에서 이미지의 ID와 경로명을 가져와서 ThumbImageInfo 모델 클래스를 생성해서
            // 리스트에 더해준다.
            while (imageCursor.moveToNext()) {
                GalleryItemActivity thumbInfo = new GalleryItemActivity();

                thumbInfo.setId(imageCursor.getString(imageIDCol));
                thumbInfo.setData(imageCursor.getString(imageDataCol));

                String path = imageCursor.getString(imageDataCol);

                BitmapFactory.Options option = new BitmapFactory.Options();

                if (new File(path).length() > 100000)
                    option.inSampleSize = 2;//10
                else
                    option.inSampleSize = 2;//불러온사진 해상도 결정 숫자 클수록 흐림

                Bitmap bmp = BitmapFactory.decodeFile(path, option);
                thumbInfo.setBitmap(bmp);

                thumbInfo.setCheckedState(false);

                mThumbImageInfoList.add(thumbInfo);
                returnValue++;
            }
        }
        imageCursor.close();
        return returnValue;
    }

    // 화면에 이미지들을 뿌려준다.
    private void updateUI() {
        mListAdapter = new ImageAdapter(this, R.layout.activity_gallery_cell, mThumbImageInfoList);
        mGvImageList.setAdapter(mListAdapter);
    }

    // *****************************************************************************************
    // //
    // Image Adapter Class
    // *****************************************************************************************
    // //
    static class ImageViewHolder {
        ImageView ivImage;
        CheckBox chkImage;
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int mCellLayout;
        private LayoutInflater mLiInflater;
        private ArrayList<GalleryItemActivity> mArrData;

        public ImageAdapter(Context c, int cellLayout,
                            ArrayList<GalleryItemActivity> thumbImageInfoList) {
            mContext = c;
            mCellLayout = cellLayout;
            mArrData = thumbImageInfoList;

            mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return mArrData.size();
        }

        public Object getItem(int position) {
            return mArrData.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLiInflater.inflate(mCellLayout, parent, false);
                ImageViewHolder holder = new ImageViewHolder();

                holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
                holder.chkImage = (CheckBox) convertView.findViewById(R.id.chkImage);

                convertView.setTag(holder);
            }

            final ImageViewHolder holder = (ImageViewHolder) convertView.getTag();

            if (((GalleryItemActivity) mArrData.get(position)).getCheckedState())
                holder.chkImage.setChecked(true);
            else
                holder.chkImage.setChecked(false);

            holder.ivImage.setImageBitmap(mArrData.get(position).getBitmap());

            setProgressBarIndeterminateVisibility(false);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mArrData.get(position).getCheckedState() == true) {
                        mArrData.get(position).setCheckedState(false);
                    } else {
                        mArrData.get(position).setCheckedState(true);
                        Toast.makeText(getApplicationContext(),mArrData.get(position).getData(),Toast.LENGTH_SHORT).show();
                    }
                    mListAdapter.notifyDataSetChanged();
                }
            });

            btnOk = (Button)findViewById(R.id.btnOK);
            btnCancel = (Button)findViewById(R.id.btnCancel);

            btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(getApplicationContext(),WritePostActivity.class);
                int count=0;
                for(int i=0;i<mArrData.size();i++) {
                    if(count>4){
                        break;
                    }
                    if (mArrData.get(i).getCheckedState()) {

                        String temp = "pic"+count;
                        mintent.putExtra(temp, mArrData.get(i).getData());
                        Log.d("??????????",temp);
                        count++;
                    }
                }
                if(count<4){
                    for(int j=count; j<5; j++) {
                        String temp = "pic"+count;
                        mintent.putExtra(temp, "0");
                        count++;
                    }
                }

                setResult(RESULT_OK,mintent);
                finish();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(getApplicationContext(),WritePostActivity.class);
                    setResult(RESULT_CANCELED,mIntent);
                    finish();
                }
            });
            return convertView;
        }
    }

    // *****************************************************************************************
    // //
    // Image Adapter Class End
    // *****************************************************************************************
    // //

    // *****************************************************************************************
    // //
    // AsyncTask Class
    // *****************************************************************************************
    // //
    private class DoFindImageList extends AsyncTask<String, Integer, Long> {
        @Override
        protected void onPreExecute() {
            mLoagindDialog = ProgressDialog.show(ChooseImageActivity.this, null, "로딩중...", true, true);
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(String... arg0) {
            long returnValue = 0;
            returnValue = findThumbList();
            return returnValue;
        }

        @Override
        protected void onPostExecute(Long result) {
            updateUI();
            mLoagindDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    // *****************************************************************************************
    // //
    // AsyncTask Class End
    // *****************************************************************************************
    // //
}