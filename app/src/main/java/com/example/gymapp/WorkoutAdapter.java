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
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>{
    private Context context;
    private ArrayList<Workout> workoutList;
    private Activity activity;

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.workout_view, parent, false);
        return new WorkoutAdapter.WorkoutViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.textViewWorkoutId.setText(String.valueOf(workoutList.get(position).getId()));
        holder.textViewWorkoutName.setText(String.valueOf(workoutList.get(position).getWorkoutName()));
        holder.workoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateWorkout.class);
                intent.putExtra("id", String.valueOf(workoutList.get(position).getId()));
                intent.putExtra("workoutName", String.valueOf(workoutList.get(position).getWorkoutName()));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.workoutLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmDialog(String.valueOf(workoutList.get(position).getId()));
                return true;
            }
        });
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewWorkoutName, textViewWorkoutId;
        private LinearLayout workoutLayout;


        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWorkoutId = itemView.findViewById(R.id.textViewWorkoutId);
            textViewWorkoutName = itemView.findViewById(R.id.textViewWorkoutName);
            workoutLayout = itemView.findViewById(R.id.workoutLayout);
        }
    }

    private void confirmDialog(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete exercise");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteWorkout(id);
                Intent intent = new Intent(context, ViewWorkout.class);
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