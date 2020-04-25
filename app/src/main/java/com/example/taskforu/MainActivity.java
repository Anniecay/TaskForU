package com.example.taskforu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<String> titleArr =new ArrayList<>();
    private ArrayList<String> descArr =new ArrayList<>();
    private ArrayList<String> catArray = new ArrayList<>();
    private ArrayList<String> dateArray = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private View parentView;
    private Dialog dialog;
    private EditText taskTitle, taskDesc;
    private FloatingActionButton floatingActionButton;
    private Button confirm ;
    private DatePicker calendar;
    private Spinner spinner;
    private  DBhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate:started.");
        recyclerView = findViewById(R.id.recyclerview);
        parentView = findViewById(R.id.parent);
        floatingActionButton = findViewById(R.id.floatButton);
        dialog  =new Dialog(this, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.menu);
        confirm = dialog.findViewById(R.id.confirmBtn);
        taskTitle = dialog.findViewById(R.id.TaskDesc);
        taskDesc = dialog.findViewById(R.id.TaskName);
        calendar = dialog.findViewById(R.id.calendar);
        spinner = dialog.findViewById(R.id.chooser);
        helper = new DBhelper(this);

        init();

        ArrayAdapter<CharSequence> spinnerAdapter= ArrayAdapter.createFromResource(this,R.array.array,
                android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerAdapter);



        //on click listeners

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = calendar.getDayOfMonth();
                int month = calendar.getMonth();
                int year = calendar.getYear();


                String spinnerItem = spinner.getSelectedItem().toString();
                add(convertDate(day,month,year),spinnerItem);
                init();
                dialog.cancel();
            }
        });
        }


        public String convertDate(int day, int month, int year){
                StringBuilder builder = new StringBuilder();
                builder.append(day);
                builder.append("/");
                builder.append(month+1);
                builder.append("/");
                builder.append(year);

                return builder.toString();

        }
        public void add(String date, String category){

        if(taskTitle.getText().length()>0){
            String title = taskTitle.getText().toString();
            String description = taskDesc.getText().toString();

            boolean insert = helper.insertTask(title, description, category,date);
            if(insert){

                Snackbar.make(parentView, "Task was added successfully", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }else{

                Snackbar.make(parentView, "Error blabla", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }

        }

        }
     public void init(){
        titleArr = new ArrayList<>();
        descArr = new ArrayList<>();
        catArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        Cursor cursor = helper.SelectAll();

        while(cursor.moveToNext()){
            titleArr.add(cursor.getString(1));
            descArr.add(cursor.getString(2));
            catArray.add(cursor.getString(3));
            dateArray.add(cursor.getString(4));
        }

        adapter = new RecyclerViewAdapter(titleArr,descArr,catArray,dateArray,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String item = titleArr.get(viewHolder.getAdapterPosition());
                Cursor cursor = helper.getDataID(item);

                int id = -1;

                while(cursor.moveToNext()){
                    id = cursor.getInt(0);
                }
                if(id>-1){

                    helper.deleteTask(id,item);
                }

                titleArr.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                init();



            }
        };



    }
