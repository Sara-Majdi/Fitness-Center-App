package com.example.testing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class AboutUsFragment extends Fragment {

    ImageButton fbIcon, igIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        fbIcon = view.findViewById(R.id.fbIcon);
        igIcon = view.findViewById(R.id.igIcon);

        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://www.facebook.com/arlongsaw.joah");
            }
        });

        igIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToURL("https://www.instagram.com/joah_00/");
            }
        });

        return view;
    }

    public void goToURL(String s){
        try{
            Uri uri = Uri.parse(s);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}