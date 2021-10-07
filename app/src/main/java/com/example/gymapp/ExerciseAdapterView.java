package com.example.gymapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ExerciseAdapterView extends RecyclerView.Adapter<ExerciseAdapterView.ExerciseViewHolder>{
    private Context context;
    private ArrayList<Exercise> list;
    private Activity activity;

    private static boolean isPressed = false;
    private static int old_pos= -1;
    private static ArrayList<String> selected = new ArrayList<>();
    @NonNull
    @Override
    public ExerciseAdapterView.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.exercise_view, parent, false);

        return new ExerciseAdapterView.ExerciseViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapterView.ExerciseViewHolder holder, int position) {
        holder.textViewExerciseId.setText(String.valueOf(list.get(position).getId()));
        holder.textViewExName.setText(String.valueOf(list.get(position).getExName()));

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Touch me", Toast.LENGTH_SHORT).show();
                int pos = holder.getLayoutPosition();
                if(old_pos != pos && !isPressed){
                    selected.add(String.valueOf(list.get(position).getId()));
                    holder.itemView.setBackgroundColor(Color.BLUE);
                    old_pos = pos;
                } else {
                    if(!isPressed){
                        Toast.makeText(context, "DONT TOUCH ME", Toast.LENGTH_SHORT).show();
//                        selected.remove(String.valueOf(list.get(position).getId()));
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                        isPressed = !isPressed;
                    } else {
                        Toast.makeText(context, "Touch me", Toast.LENGTH_SHORT).show();
                        selected.add(String.valueOf(list.get(position).getId()));
                        holder.itemView.setBackgroundColor(Color.BLUE);
                        isPressed = !isPressed;
                    }
                    // Create an intent to pass the list
//                    Intent intent = new Intent("exerciseList");
//                    intent.putStringArrayListExtra("list", (ArrayList<String>) selected);
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }

//                if(isPressed == 0){
//                    holder.itemView.getId();
////                    holder.textViewExName.setTextColor(Color.BLUE);
////                    holder.exerciseLayout.setBackgroundColor(Color.BLUE);
//                    Toast.makeText(context, "Touch me", Toast.LENGTH_SHORT).show();
//                    selected.add(String.valueOf(list.get(position).getId()));
//                    holder.itemView.setBackgroundColor(Color.BLUE);
//                    isPressed = 1;
//                } else{
////                    holder.textViewExName.setTextColor(Color.GRAY);
////                    holder.exerciseLayout.setBackgroundColor(Color.TRANSPARENT);
//                    Toast.makeText(context, "DONT TOUCH ME", Toast.LENGTH_SHORT).show();
//                    selected.remove(String.valueOf(list.get(position).getId()));
//                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//                    isPressed = false;
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();    }

    public ArrayList<String> getList(){
        return selected;
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewExerciseId, textViewExName, textViewRepeats, textViewSets, textViewRepeatsText, textViewSetsText;
        private LinearLayout exerciseLayout;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseId = itemView.findViewById(R.id.textViewExerciseId);
            textViewExName = itemView.findViewById(R.id.textViewExName);
            textViewRepeats = itemView.findViewById(R.id.textViewRepeats);
            textViewRepeatsText = itemView.findViewById(R.id.textViewRepeatsText);
            textViewSets = itemView.findViewById(R.id.textViewSets);
            textViewSetsText = itemView.findViewById(R.id.textViewSetsText);
            exerciseLayout = itemView.findViewById(R.id.exerciseLayout);


            textViewSets.setVisibility(View.GONE);
            textViewRepeats.setVisibility(View.GONE);
            textViewSetsText.setVisibility(View.GONE);
            textViewRepeatsText.setVisibility(View.GONE);

        }
    }
}
