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

public class ViewClassFragment extends Fragment implements ClassDetailsInterface{

    private RecyclerView recyclerView;
    private ArrayList<MyClass> classes;
    private DatabaseManager dbManager;
    private Account account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_class, container, false);
        dbManager = new DatabaseManager(requireActivity());
        account = new Account();

        Bundle args = getArguments();
        if (args != null && args.containsKey("account")) {
            account = (Account) args.getSerializable("account");
        }

        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        recyclerView = view.findViewById(R.id.recyclerViewClass);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        classes = new ArrayList<>();

        classes = dbManager.getTrainerClass(account.getAccountDBID());
        dbManager.close();

        recyclerView.setAdapter(new ClassDetailsAdapter(classes, this::onItemClick));

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }
}