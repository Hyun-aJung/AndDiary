package com.app.hyuna.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetAdapter extends ArrayAdapter<CustomWidgetRow>{
    TextView txtTitle, txtMemo;
    ArrayList<CustomWidgetRow> rows = new ArrayList<CustomWidgetRow>();
    Context mContext;

    @Override
    public void add(CustomWidgetRow object) {
        rows.add(object);
    }

    @Override
    public void clear() {
        rows.clear();
    }

    public CustomWidgetAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        mContext = context;
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
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_widget_list_item,parent,false);
        }
        CustomWidgetRow crow = getItem(position);
        txtTitle = (TextView)row.findViewById(R.id.title);
        txtMemo = (TextView)row.findViewById(R.id.memo);

//        //TODO DB에서 읽어와서 뿌려줘야해 흠..아닌가
//        txtTitle.setText("hi title");
//        txtMemo.setText("hi Memo");//요 두줄 db에서 온걸로 바꿔야야
       return row;
    }
}
