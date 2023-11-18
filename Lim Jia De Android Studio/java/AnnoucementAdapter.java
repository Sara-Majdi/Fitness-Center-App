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

public class AnnoucementAdapter extends RecyclerView.Adapter<AnnoucementAdapter.AnnouncementViewHolder>{

    private ArrayList<Announcement> announcements;

    public AnnoucementAdapter(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    @NonNull
    @Override
    public AnnoucementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnoucementAdapter.AnnouncementViewHolder holder, int position) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        holder.announcementTitle.setText(announcements.get(position).getAnnouncementTitle());
        holder.announcementDescription.setText(announcements.get(position).getDescription());

        Date time = announcements.get(position).getCreationTime();
        Date date = announcements.get(position).getCreationDate();

        if (time != null) {
            holder.time.setText("Time: " + timeFormat.format(time));
        } else {
            holder.time.setText("Time: N/A");
        }

        if (date != null) {
            holder.date.setText("Date: " + dateFormat.format(date));
        } else {
            holder.date.setText("Time: N/A");
        }

    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        private TextView announcementTitle, announcementDescription, date, time;
        public AnnouncementViewHolder (@NonNull View itemView){
            super(itemView);
            announcementTitle = itemView.findViewById(R.id.announcementTitle);
            announcementDescription = itemView.findViewById(R.id.announcementDescription);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);

        }
    }
}
