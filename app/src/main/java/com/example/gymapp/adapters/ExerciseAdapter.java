package com.example.gymapp.adapters;

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

import com.example.gymapp.DBHelper;
import com.example.gymapp.R;
import com.example.gymapp.activities.UpdateExerciseActivity;
import com.example.gymapp.entities.Exercise;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private Context context;
    private ArrayList<Exercise> list;
    private Activity activity;

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.exercise_view, parent, false);
        return new ExerciseAdapter.ExerciseViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.textViewExerciseId.setText(String.valueOf(list.get(position).getId()));
        holder.textViewExName.setText(String.valueOf(list.get(position).getExName()));
        holder.textViewRepeats.setText(String.valueOf(list.get(position).getRepeats()));
        holder.textViewSets.setText(String.valueOf(list.get(position).getSets()));

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateExerciseActivity.class);
                intent.putExtra("id", String.valueOf(list.get(position).getId()));
                intent.putExtra("exName", String.valueOf(list.get(position).getExName()));
                intent.putExtra("repeats", String.valueOf(list.get(position).getRepeats()));
                intent.putExtra("sets", String.valueOf(list.get(position).getSets()));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.exerciseLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmDialog(String.valueOf(list.get(position).getId()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();    }


    public class ExerciseViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewExerciseId, textViewExName, textViewRepeats, textViewSets;
        private LinearLayout exerciseLayout;


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseId = itemView.findViewById(R.id.textViewExerciseId);
            textViewExName = itemView.findViewById(R.id.textViewExName);
            textViewRepeats = itemView.findViewById(R.id.textViewRepeats);
            textViewSets = itemView.findViewById(R.id.textViewSets);
            exerciseLayout = itemView.findViewById(R.id.exerciseLayout);
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
                dbHelper.deleteExercise(id);
                Intent intent = new Intent(context, ViewExercise.class);
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

