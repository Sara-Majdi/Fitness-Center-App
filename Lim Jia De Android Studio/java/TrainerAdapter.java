package com.example.testing;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder>{
    private ArrayList<User> trainers;
    private Context context;
    private final TrainersAchievementsInterface trainersAchievementsInterface;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    public TrainerAdapter(Context context, ArrayList<User> trainers, TrainersAchievementsInterface trainersAchievementsInterface){
        this.trainers = trainers;
        this.context = context;
        this.trainersAchievementsInterface = trainersAchievementsInterface;
    }

    @NonNull
    @Override
    public TrainerAdapter.TrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_trainers, parent, false);
        return new TrainerAdapter.TrainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerAdapter.TrainerViewHolder holder, int position) {
        holder.trainerName.setText(trainers.get(position).getName());
        holder.achievements.setText(trainers.get(position).getAchievements());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            );
        } else {
            // Permission already granted, load the image
            String imagePath = trainers.get(position).getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                Uri imageUri = Uri.parse(imagePath);
                holder.profilePicture.setImageURI(imageUri);
            }
        }
    }

    @Override
    public int getItemCount() {
        return trainers.size();
    }

    class TrainerViewHolder extends RecyclerView.ViewHolder {

        private TextView trainerName, achievements;
        private ImageView profilePicture;

        public TrainerViewHolder (@NonNull View itemView){
            super(itemView);
            trainerName = itemView.findViewById(R.id.trainerName);
            achievements = itemView.findViewById(R.id.achievements);
            profilePicture = itemView.findViewById(R.id.profilePicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if (trainersAchievementsInterface != null){
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            trainersAchievementsInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }

}
