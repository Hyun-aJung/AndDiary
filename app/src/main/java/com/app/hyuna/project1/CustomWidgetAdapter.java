package com.app.hyuna.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetAdapter extends BaseAdapter{
    TextView txtTitle, txtMemo;
    ArrayList<CustomWidgetRow> rows = new ArrayList<CustomWidgetRow>();
    Context mContext;
    CustomWidgetRow crow;

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(CustomWidgetRow object) {
        rows.add(object);
    }

    public void clear() {
        rows.clear();
    }

    public CustomWidgetAdapter(Context context){//}, int textViewResourceId){
        //super(context,textViewResourceId);
        mContext = context;
        //rows = new ArrayList<CustomWidgetRow>();
    }
    public int getCount(int index){
        return this.rows.size();
    }
    public CustomWidgetRow getItem(int index){
        return this.rows.get(index);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        if(row==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_widget_list_item,parent,false);
        }

        txtTitle = (TextView)row.findViewById(R.id.title);
        txtMemo = (TextView)row.findViewById(R.id.memo);
        crow = getItem(position);
        if(crow !=null){
            txtTitle.setText(crow.getTitle());
            txtMemo.setText(crow.getMemo());
        }

        return row;
    }
}
