package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.SQLDataException;

public class TrainerNavigationPage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Account account;
    private DatabaseManager dbManager;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_navigation_page);

        Toolbar toolbar = findViewById(R.id.toolbarTrainer);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_trainer);
        NavigationView navigationView = findViewById(R.id.nav_view_trainer);
        dbManager = new DatabaseManager(this);
        account = new Account();
        page = 0;

        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();
        page = intent.getIntExtra("pagekey", 0);
        account = (Account) getIntent().getSerializableExtra("account");
        account.setProfile(dbManager);

        if (!account.isHavingProfile()){
            Toast.makeText(getApplicationContext(), "Profile is not complete!", Toast.LENGTH_LONG).show();
        }

        if (page == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homeTrainerSideNav);
        } else if (page == 1){
            Bundle bundle = new Bundle();
            bundle.putSerializable("account", account);
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            myProfileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myProfileFragment).commit();
            navigationView.setCheckedItem(R.id.myProfileSideNav);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("account", account);

                if (item.getItemId() == R.id.homeTrainerSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
                else if (item.getItemId() == R.id.viewClassSideNav){
                    ViewClassFragment viewClassFragment = new ViewClassFragment();
                    viewClassFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewClassFragment).commit();
                }
                else if (item.getItemId() == R.id.aboutUsTrainerSideNav){
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutUsFragment).commit();
                }
                else if (item.getItemId() == R.id.myProfileSideNav) {
                    MyProfileFragment myProfileFragment = new MyProfileFragment();
                    myProfileFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myProfileFragment).commit();
                }
                else if (item.getItemId() == R.id.logoutSideNav) {
                    Intent i = new Intent(TrainerNavigationPage.this, Login.class);
                    startActivity(i);
                }
                dbManager.close();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}