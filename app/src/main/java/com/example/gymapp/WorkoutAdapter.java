package com.example.gymapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private static String workoutId;
    private static ExerciseAdapterView adapterView;
    private static ArrayList<Exercise> exList;
    public static boolean isClickable = true;
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
        workoutId = String.valueOf(workoutList.get(position).getId());
        holder.textViewWorkoutId.setText(workoutId);
        holder.textViewWorkoutName.setText(String.valueOf(workoutList.get(position).getWorkoutName()));
        holder.workoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClickable){
                    confirmView(position);
                }
            }
        });

        holder.workoutLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(isClickable){
                    confirmDialog(String.valueOf(workoutList.get(position).getId()));
                }
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
        builder.setTitle("Delete Workout");
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

    private void confirmView(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("View Workout");
        builder.setMessage("Edit Workout?");
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_exercise, null);
        exList = getExList(workoutList.get(position).getId());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.addButton2);
        button.setVisibility(View.GONE);

        adapterView = new ExerciseAdapterView(context, exList, activity);
        adapterView.isClickable=false;
        recyclerView.setAdapter(adapterView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        builder.setView(view);
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, UpdateWorkout.class);
                intent.putExtra("id", String.valueOf(workoutList.get(position).getId()));
                intent.putExtra("workoutName", String.valueOf(workoutList.get(position).getWorkoutName()));
                activity.startActivityForResult(intent, 1);
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.getWindow().setLayout(1500, 1000); //Controlling width and height.
    }

    private ArrayList<Exercise> getExList(int workoutId){
        DBHelper dbHelper = new DBHelper(context);
        exList = dbHelper.findExercises(String.valueOf(workoutId));
        return exList;
    }
}