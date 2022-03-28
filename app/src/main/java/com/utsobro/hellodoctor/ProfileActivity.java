package com.utsobro.hellodoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.optProfile){
                    Toast.makeText(ProfileActivity.this,"Profile",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optAppointments){
                    Toast.makeText(ProfileActivity.this,"Appointments",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optDoctors){
                    Toast.makeText(ProfileActivity.this,"Doctors",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optEmergency){
                    Toast.makeText(ProfileActivity.this,"Emergency",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optAmbulance){
                    Toast.makeText(ProfileActivity.this,"Ambulance",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optAboutUs){
                    Toast.makeText(ProfileActivity.this,"AboutUs",Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.optLogOut){
                    Toast.makeText(ProfileActivity.this,"LogOut",Toast.LENGTH_SHORT).show();
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
}