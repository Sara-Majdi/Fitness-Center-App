package com.example.testing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ClassDetailsViewHolder>{

    private final ClassDetailsInterface classDetailsInterface;
    private ArrayList<MyClass> classes;

    public ClassDetailsAdapter(ArrayList<MyClass> classes, ClassDetailsInterface classDetailsInterface) {
        this.classes = classes;
        this.classDetailsInterface = classDetailsInterface;
    }

    @NonNull
    @Override
    public ClassDetailsAdapter.ClassDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        return new ClassDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassDetailsAdapter.ClassDetailsViewHolder holder, int position) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        holder.classDescription.setText(classes.get(position).getClassDescription());
        holder.trainerName.setText(classes.get(position).getTrainerDetails().getName());
        holder.venue.setText("Venue: " + classes.get(position).getClassVenue());
        holder.duration.setText("Duration: " + classes.get(position).getClassDuration() + " mins");

        Date startTime = classes.get(position).getClassStartTime();
        Date endTime = classes.get(position).getClassEndTime();
        Date classDate = classes.get(position).getClassDate();

        if (startTime != null) {
            holder.startTime.setText("Time: " + timeFormat.format(startTime));
        } else {
            holder.startTime.setText("Time: N/A");
        }

        if (endTime != null) {
            holder.endTime.setText(timeFormat.format(endTime));
        } else {
            holder.endTime.setText("Time: N/A");
        }

        if (classDate != null) {
            holder.classDate.setText("Date: " + dateFormat.format(classDate));
        } else {
            holder.classDate.setText("Date: N/A");
        }
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    class ClassDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView classDescription, trainerName, startTime, endTime, venue, classDate,
                        duration;
        public ClassDetailsViewHolder (@NonNull View itemView){
            super(itemView);
            classDescription = itemView.findViewById(R.id.announcementTitle);
            trainerName = itemView.findViewById(R.id.announcementDescription);
            startTime = itemView.findViewById(R.id.time);
            endTime = itemView.findViewById(R.id.endTime);
            venue = itemView.findViewById(R.id.venue);
            classDate = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if (classDetailsInterface != null){
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            classDetailsInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
