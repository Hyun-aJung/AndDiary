package com.app.hyuna.project1;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by 4강의실 on 2016-07-15.
 */
public class GridViewImg extends BaseAdapter {
    private Context mContext;
    private Integer[] mImg = {R.drawable.default_img,R.drawable.default_img,R.drawable.default_img
                                ,R.drawable.default_img,R.drawable.default_img}; //5개 이미지
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imgView;
        if(view==null){
            imgView = new ImageView(mContext);
            imgView.setLayoutParams(new GridView.LayoutParams(85,85));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setPadding(2,2,2,2);
        }else{
            imgView = (ImageView)view;
        }
        imgView.setImageResource(mImg[i]);
        return imgView;
    }
}
