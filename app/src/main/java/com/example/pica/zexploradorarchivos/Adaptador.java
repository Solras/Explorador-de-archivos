package com.example.pica.zexploradorarchivos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

public class Adaptador extends ArrayAdapter<File> {

    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private ArrayList<File> values;

    static class ViewHolder {
        TextView tvn,tvp;
        ImageView iv;
    }

    public Adaptador(Context context, int resource, ArrayList<File> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.res = resource;
        this.lInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();

        if (convertView == null) {
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_nombre);
            vh.tvn = tv;
            tv = (TextView) convertView.findViewById(R.id.tv_path);
            vh.tvp = tv;
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_icono);
            vh.iv = iv;
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

//        if(position==0){
//            if(!values.get(position).getParent().equals("/storage/sdcard0") && !values.get(position).getParent().equals("/storage/sdcard1")) {
//                vh.tvn.setText("../");
//                vh.tvp.setText("");
//            }
//            else {
//                vh.tvn.setText(values.get(position).getName());
//                String lastModified = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(values.get(position).lastModified()));
//                vh.tvp.setText(lastModified);
//            }
//        }
//        else {
            vh.tvn.setText(values.get(position).getName());
            String lastModified = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(values.get(position).lastModified()));
            vh.tvp.setText(lastModified);
//        }

        if(values.get(position).isDirectory()) {
            vh.iv.setImageResource(R.drawable.folder);
        }
        if(values.get(position).isFile()){
            vh.iv.setImageResource(R.drawable.file);
        }


        return convertView;
    }

    public File getFile(int position) {
        return values.get(position);
    }

    public ArrayList<File> getValues() {
        return values;
    }
}
