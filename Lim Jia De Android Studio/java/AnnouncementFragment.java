package com.example.testing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class AnnouncementFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Announcement> announcements;
    private DatabaseManager dbManager;
    private Announcement announcement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_annoucement, container, false);
        announcement = new Announcement();
        announcements = new ArrayList<>();
        dbManager = new DatabaseManager(requireActivity());
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        announcements = dbManager.getAllAnnouncement();
        dbManager.close();

        recyclerView.setAdapter(new AnnoucementAdapter(announcements));


        return view;
    }
}