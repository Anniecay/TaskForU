package com.example.taskforu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> titleArr=new ArrayList<>();
    private ArrayList<String> descArr =new ArrayList<>();
    private ArrayList<String> catArray = new ArrayList<>();
    private ArrayList<String> dateArr = new ArrayList<>();
    private Context mContext;
    private CardView cardView;
    private DBhelper helper;
    public RecyclerViewAdapter(ArrayList<String> titleArr,ArrayList<String> descArr,ArrayList<String> catArray,ArrayList<String> dateArr, Context mContext){
       this.titleArr=titleArr;
       this.descArr=descArr;
       this.mContext=mContext;
       this.catArray = catArray;
       this.dateArr = dateArr;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lisitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        helper = new DBhelper(mContext);



        switch (catArray.get(position)){

            case "ToDo" : holder.point.setImageResource(R.drawable.point);
                        holder.tasktype.setTextColor(mContext.getColor(R.color.green));
            break;
            case "Email" : holder.point.setImageResource(R.drawable.pointyellow);
                 holder.tasktype.setTextColor(mContext.getColor(R.color.yellow));

                break;
            case "Phone": holder.point.setImageResource(R.drawable.pointorange);
                holder.tasktype.setTextColor(mContext.getColor(R.color.orange));


                break;
            case "Meeting": holder.point.setImageResource(R.drawable.pointpint);
                holder.tasktype.setTextColor(mContext.getColor(R.color.pink));

                break;

        }
        holder.tasktype.setText(titleArr.get(position));
        holder.dueDate.setText(dateArr.get(position));


        holder.cardView.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                String name = titleArr.get(position);
                Cursor cursor = helper.getDataID(name);
                int id = -1;
                while (cursor.moveToNext()) {
                    //get ID value from first column
                    id = cursor.getInt(0);

                    Intent intent = new Intent(mContext, TaskDetails.class);
                    intent.putExtra("id",id);
                    intent.putExtra("title",name);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView point;
        TextView tasktype;
        TextView dueDate;
        RelativeLayout parent_layout;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            point=itemView.findViewById(R.id.green);
            tasktype=itemView.findViewById(R.id.todo);
            dueDate = itemView.findViewById(R.id.duedate);
            parent_layout=itemView.findViewById(R.id.parent_layout);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}