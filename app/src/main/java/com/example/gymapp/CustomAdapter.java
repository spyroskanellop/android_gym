package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
//    private ArrayList<Athlete> list;
    private ArrayList idList, firstList, lastList, phoneList, descList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.athletes_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewAthleteId.setText(String.valueOf(idList.get(position)));
        holder.textViewFirstName.setText(String.valueOf(firstList.get(position)));
        holder.textViewLastName.setText(String.valueOf(lastList.get(position)));
        holder.textViewPhone.setText(String.valueOf(phoneList.get(position)));

//        holder.textViewAthleteId.setText(String.valueOf(list.get(position).getId()));
//        holder.textViewFirstName.setText("spyros");
//        holder.textViewLastName.setText(String.valueOf(list.get(position).getLastName()));
//        holder.textViewDescription.setText(String.valueOf(list.get(position+4).getDescription()));
//        holder.textViewPhone.setText(String.valueOf(list.get(position).getPhone()));

    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewFirstName, textViewLastName, textViewPhone, textViewAthleteId;
//        private TextView textViewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAthleteId = itemView.findViewById(R.id.textViewAthleteId);
            textViewFirstName = itemView.findViewById(R.id.textViewAthleteFirstName);
            textViewLastName = itemView.findViewById(R.id.textViewAthleteLastName);
//            textViewDescription = itemView.findViewById(R.id.editTextDescription);
            textViewPhone = itemView.findViewById(R.id.textViewAthletePhone);
        }
    }
}
