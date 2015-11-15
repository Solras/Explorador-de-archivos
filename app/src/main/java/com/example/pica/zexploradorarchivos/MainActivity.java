package com.example.pica.zexploradorarchivos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private android.widget.ListView listView;
    private android.widget.TextView parentPath;
    private Adaptador ad;
    private ArrayList<String> lastPaths;
    private String actualRoot="/storage/sdcard0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_search: {
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Buscar");
                LayoutInflater inflater= LayoutInflater.from(this);
                alert.setView(inflater.inflate(R.layout.dialog_search, null));

                DialogInterface.OnClickListener listenerSearch = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ArrayList<File> values = ((Adaptador) listView.getAdapter()).getValues();
                        EditText et=(EditText) findViewById(R.id.et_search);
                        ArrayList<File> newValues = new ArrayList();
                        for (File file : values) {
                            if(file.getName().contains(et.getText().toString())){
                                newValues.add(file);
                            }
                        }
                        listView.setAdapter(new Adaptador(MainActivity.this,R.layout.elemento_lista,newValues));
                    }
                };
                alert.setPositiveButton("Buscar", listenerSearch);
                alert.setNegativeButton("Cancelar", null);
                alert.show();
                return true;
            }
            case R.id.sdcard0:{
                actualRoot="/storage/sdcard0";
                File f = new File(actualRoot);
                parentPath.setText(f.getPath());
                lastPaths.add(f.getPath());
                ArrayList<File> files = foldersFirst(f.listFiles());
                listView.setAdapter(new Adaptador(this, R.layout.elemento_lista, files));
                return true;
            }
            case R.id.sdcard1:{
                actualRoot="/storage/sdcard1";
                File f = new File(actualRoot);
                parentPath.setText(f.getPath());
                lastPaths.add(f.getPath());
                ArrayList<File> files = foldersFirst(f.listFiles());
                listView.setAdapter(new Adaptador(this, R.layout.elemento_lista, files));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!lastPaths.isEmpty()) {
            File f = new File(lastPaths.get(lastPaths.size() - 1));
            lastPaths.remove(lastPaths.get(lastPaths.size() - 1));
            parentPath.setText(f.getPath());
            ArrayList<File> files = foldersFirst(f.listFiles());
            if (!f.getPath().equals(actualRoot))
                files.add(0, new File(f.getParent()));
            listView.setAdapter(new Adaptador(this, R.layout.elemento_lista, files));
        }
        else{
            super.onBackPressed();
        }
    }

    public void iniciar() {
        this.listView = (ListView) findViewById(R.id.listView);
        this.parentPath = (TextView) findViewById(R.id.parentPath);
        lastPaths = new ArrayList();

        File f = new File(actualRoot);
        try {
            FileInputStream inputStream = openFileInput(f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAllFiles(f);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File f = ((Adaptador) listView.getAdapter()).getFile(position);
                        if (f.isDirectory()) {
                            parentPath.setText(f.getPath());
                            lastPaths.add(f.getPath());
                            ArrayList<File> files = foldersFirst(f.listFiles());
                            if(!f.getPath().equals(actualRoot))
                                files.add(0, new File(f.getParent()));
                            listView.setAdapter(new Adaptador(MainActivity.this, R.layout.elemento_lista, files));
                        }
                    }
                }
        );
    }

    public void getAllFiles(File f) {
        parentPath.setText(f.getPath());
        ArrayList<File> files = foldersFirst(f.listFiles());
        if (!f.getPath().equals(actualRoot))
            files.add(0, new File(f.getParent()));
        listView.setAdapter(new Adaptador(this, R.layout.elemento_lista, files));
    }

    private ArrayList<File> foldersFirst(File[] subfiles){
        ArrayList<File> newfiles = new ArrayList();
        ArrayList<File> folders = new ArrayList();
        ArrayList<File> files = new ArrayList();
        for (File f : subfiles) {
            if(f.isDirectory()){
                folders.add(f);
            }
            if(f.isFile()){
                files.add(f);
            }
        }
        Collections.sort(folders);
        Collections.sort(files);
        for (File f : folders) {
            newfiles.add(f);
        }
        for (File f : files) {
            newfiles.add(f);
        }
        return newfiles;
    }
}
