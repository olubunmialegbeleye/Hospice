package com.example.hospice;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientListRecyclerAdapter extends RecyclerView.Adapter<PatientListRecyclerAdapter.ViewHolder>{
    private final Context context;
    private final LayoutInflater layoutInflater;
    List<Patient> patientList;

    public PatientListRecyclerAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.record_list, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.textName.setText(patient.getPatientName());
        holder.textAge.setText(patient.getPatientAge() + "");
        holder.textGender.setText(patient.getPatientGender());
        holder.textBP.setText(patient.getPatientBP());
        holder.currentPosition = position;
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textAge;
        TextView textGender;
        TextView textBP;
        int currentPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.patientName);
            textAge = (TextView) itemView.findViewById(R.id.age_value);
            textGender = (TextView) itemView.findViewById(R.id.sex_value);
            textBP = (TextView) itemView.findViewById(R.id.bp_value);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent patientDetailsIntent = new Intent(context, ViewRecordActivity.class);
                    patientDetailsIntent.putExtra("patient object", patientList.get(currentPosition));
                    context.startActivity(patientDetailsIntent);
                }
            });
        }
    }
}
