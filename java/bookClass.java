package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class bookClass extends AppCompatActivity {

    private Account account;
    private MyClass myClass;
    private TextView classDescriptionBooking, trainerNameBooking, timeBooking,
                    dateBooking, durationBooking, venueBooking;
    private Button bookBtn;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

        classDescriptionBooking = findViewById(R.id.trainerAchievements);
        trainerNameBooking = findViewById(R.id.displayAchievements);
        timeBooking = findViewById(R.id.timeBooking);
        dateBooking = findViewById(R.id.dateBooking);
        durationBooking = findViewById(R.id.durationBooking);
        venueBooking = findViewById(R.id.venueBooking);
        bookBtn = findViewById(R.id.bookBtn);
        account = (Account) getIntent().getSerializableExtra("account");
        myClass = (MyClass) getIntent().getSerializableExtra("myClass");

        dbManager = new DatabaseManager(this);

        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }

        classDescriptionBooking.setText(myClass.getClassDescription());
        trainerNameBooking.setText("Trainer: " + myClass.getTrainerDetails().getName());
        timeBooking.setText("Time: " + timeFormat.format(myClass.getClassStartTime()) + " - "
                + timeFormat.format(myClass.getClassEndTime()));
        dateBooking.setText("Date: " + dateFormat.format(myClass.getClassDate()));
        durationBooking.setText("Duration: " + Integer.toString(myClass.getClassDuration()) + " mins");
        venueBooking.setText("Venue: " + myClass.getClassVenue());

        bookBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (dbManager.bookClass(account.getAccountDBID(), myClass.getClassDBID())){
                    Toast.makeText(getApplicationContext(), "Class booked successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to book class, something went wrong!", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(bookClass.this, NavigationPage.class);
                i.putExtra("account", account);
                i.putExtra("pagekey", 2);
                startActivity(i);
            }
        });

    }
}