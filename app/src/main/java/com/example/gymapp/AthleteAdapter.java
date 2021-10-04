package com.example.gymapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Athlete> list;
    private Activity activity;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.athletes_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textViewAthleteId.setText(String.valueOf(list.get(position).getId()));
        holder.textViewFirstName.setText(String.valueOf(list.get(position).getFirstName()));
        holder.textViewLastName.setText(String.valueOf(list.get(position).getLastName()));
//        holder.textViewDescription.setText(String.valueOf(list.get(position).getDescription()));
        holder.textViewPhone.setText(String.valueOf(list.get(position).getPhone()));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAthlete.class);
                intent.putExtra("id", String.valueOf(list.get(position).getId()));
                intent.putExtra("firstName", String.valueOf(list.get(position).getFirstName()));
                intent.putExtra("lastName", String.valueOf(list.get(position).getLastName()));
                intent.putExtra("phone", String.valueOf(list.get(position).getPhone()));
                intent.putExtra("description", String.valueOf(list.get(position).getDescription()));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmDialog(String.valueOf(list.get(position).getId()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewFirstName, textViewLastName, textViewPhone, textViewAthleteId;
//        private TextView textViewDescription;
        private LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAthleteId = itemView.findViewById(R.id.textViewAthleteId);
            textViewFirstName = itemView.findViewById(R.id.textViewAthleteFirstName);
            textViewLastName = itemView.findViewById(R.id.textViewAthleteLastName);
//            textViewDescription = itemView.findViewById(R.id.editTextDescription);
            textViewPhone = itemView.findViewById(R.id.textViewAthletePhone);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    private void confirmDialog(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete athlete");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteAthlete(id);
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
