package com.utsobro.hellodoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        //toolbar setup

        setSupportActionBar(toolbar);

        //action bar toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ProfileActivity.this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //default flag
        loadFragment(new ProfileFragment(),0);
        //default fragment

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.optProfile){
                    loadFragment(new ProfileFragment(),1);
                }
                else if(id == R.id.optAppointments){
                    loadFragment(new AppointmentsFragment(),1);
                }
                else if(id == R.id.optDoctors){
                    loadFragment(new DoctorsFragment(),1);
                }
                else if(id == R.id.optEmergency){
                    loadFragment(new EmergencyFragment(),1);
                }
                else if(id == R.id.optAmbulance){
                    loadFragment(new AmbulanceFragment(),1);
                }
                else if(id == R.id.optAboutUs){
                    loadFragment(new AboutFragment(),1);
                }
                else if(id == R.id.optLogOut){
                    //logout fragment implementation
                    loadFragment(new LogoutFragment(),1);
                }
                drawerLayout.closeDrawer(GravityCompat.START);



                return true;
            }
        });
    }

    //on back press


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            //app closed
            super.onBackPressed();
        }
    }
    private void loadFragment(Fragment fragment,int flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(flag == 0) fragmentTransaction.add(R.id.container,fragment);
        else fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();

    }
}