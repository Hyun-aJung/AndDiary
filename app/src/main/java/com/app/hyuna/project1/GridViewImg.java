package com.app.hyuna.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by 4강의실 on 2016-07-15.
 */
public class GridViewImg extends BaseAdapter {
    private Context mContext;
    private Integer[] mImg = {R.drawable.default_img,R.drawable.default_img,R.drawable.default_img
                                ,R.drawable.default_img,R.drawable.default_img}; //5개 이미지
    private Bitmap[] bitImg = new Bitmap[5];
    int x,y;

    public GridViewImg(Context c){
        mContext = c;
    }
    @Override
    public int getCount() {
        return mImg.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void setDisplaySize(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Integer[] setImg(Integer[] img){  //이미지를 받아오기 위해
        mImg = img;
        return mImg;
    }
    public void setBitImg(String[] img){
        for(int i=0; i<img.length;i++){
            if(img[i].equals("0")){//이미지가 5개 미만으로 선택 되었을 때
                bitImg[i] = null;
            }
            else {
                bitImg[i] = BitmapFactory.decodeFile(img[i]);
            }
        }
    }
    public void setBitImgStream(InputStream[] img){ //서버에 있는 이미지 불러올 때 사용
        for(int i=0; i<img.length;i++){
            if(img[i]==null){//이미지가 5개 미만으로 선택 되었을 때
                bitImg[i] = null;
            }
            else {
                bitImg[i] = BitmapFactory.decodeStream(img[i]);
            }
        }
    }
    public Bitmap getBitImg(int i){
        return bitImg[i];
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imgView;
        if(view==null){
            imgView = new ImageView(mContext);
            //Display display = getWindowManager().getDefaultDisplay();
            imgView.setLayoutParams(new GridView.LayoutParams(x/6,y/6));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setPadding(0,0,0,0);
            /*imgView.setLayoutParams(new GridView.LayoutParams(85,85));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setPadding(2,2,2,2);*/
        }else{
            imgView = (ImageView)view;
        }
        if(bitImg[i]==null){ //이미지가 5개 미만으로 선택됬을 떄
            imgView.setImageResource(mImg[i]);}
        else{
            imgView.setImageBitmap(getBitImg(i));
        }
        return imgView;
    }
}
