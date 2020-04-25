package com.example.taskforu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskDetails extends AppCompatActivity {

    TextView titleTV, catgegoryTV, dateTV;
    Button close;
    EditText desc;
    DBhelper helper;
    FloatingActionButton pigsave;
    int id;
    ImageView point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        helper = new DBhelper(this);
        Intent recievedIntent = getIntent();
        point = findViewById(R.id.green2);
        pigsave = findViewById(R.id.pigsave);

        titleTV = findViewById(R.id.titleTask);
        close = findViewById(R.id.closeBtn);
        desc = findViewById(R.id.detail);
        catgegoryTV = findViewById(R.id.textView2);
        dateTV = findViewById(R.id.time);

         id = recievedIntent.getIntExtra("id", -1);
        String title =  recievedIntent.getStringExtra("title");


        //color change

        String c = fetchCategory(title,id);

        if(c.equals("ToDo")){
            catgegoryTV.setTextColor(getResources().getColor(R.color.green));
            point.setImageResource(R.drawable.point);

        }
        if(c.equals("Email")){
            catgegoryTV.setTextColor(getResources().getColor(R.color.yellow));
            point.setImageResource(R.drawable.pointyellow);

        }
        if(c.equals("Phone")){
            catgegoryTV.setTextColor(getResources().getColor(R.color.orange));
            point.setImageResource(R.drawable.pointorange);
        }
        if(c.equals("Meeting")){
            catgegoryTV.setTextColor(getResources().getColor(R.color.pink));
            point.setImageResource(R.drawable.pointpint);

        }
        desc.setText(fetchData(title,id));
        catgegoryTV.setText(fetchCategory(title,id));
        dateTV.setText(fetchDate(title,id));

        titleTV.setText(title);

        pigsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetails.this, MainActivity.class);
                helper.updateDescription(desc.getText().toString(),id);
                startActivity(intent);
            }
        });
    }

    public String fetchDate(String name, int id){
        Cursor data = helper.getDate(name, id);

        if(data.getCount()==0){
            return "error getting date. Have you set it? ";
        }else{
            if(data.moveToFirst()){
                return data.getString(data.getColumnIndex("date"));
            }
            data.close();
        }
        return "ERRRRROOOOOR";

    }

    public String fetchCategory(String name, int id){
        Cursor data = helper.getCategory(name, id);

        if(data.getCount()==0){
            return "error getting category. Have you set it? ";
        }else{
            if(data.moveToFirst()){
                return data.getString(data.getColumnIndex("category"));
            }
            data.close();
        }
        return "ERRRRROOOOOR";

    }
    public String fetchData(String name, int id){

        Cursor data = helper.getDescription(name,id);

        if(data.getCount() == 0){
            return "no description is set";
        }
        else {
            if(data.moveToFirst()){
                return data.getString(data.getColumnIndex("description"));
            }
            data.close();
        }

        return "error ";
    }
}