package com.example.sanjiv.awarenessapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    String userRole;
    FirebaseDatabase mDatabase;
    FirebaseUser user;
    Menu navigationMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = mAuth.getCurrentUser();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_settings).setVisible(false);
        navigationMenu.findItem(R.id.nav_map).setVisible(false);

        retrieveUserRole();

        //Als er op de notificatie gedrukt is wordt er doorverwezen naar de lampnotificatie fragment
        fragmentManager = getSupportFragmentManager();
        String menuFragment = getIntent().getStringExtra("menuFragment");
        Fragment fragment = null;
        Class fragmentClass = null;
        if (menuFragment != null) {
            if (menuFragment.equals("nav_notificaties")) {
                fragmentClass = LampNotificaties.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                getSupportActionBar().setTitle("Lampnotificaties");
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();

        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_lamp) {
            fragmentClass = Lampen.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            getSupportActionBar().setTitle("Lampen Overzicht");

        } else if (id == R.id.nav_pis) {
            fragmentClass = Pi.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            getSupportActionBar().setTitle("Raspberry Pi's");

        } else if (id == R.id.nav_statistics) {
            fragmentClass = Statistics.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            getSupportActionBar().setTitle("Statistieken");

        } else if (id == R.id.nav_notificaties) {
            fragmentClass = LampNotificaties.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            getSupportActionBar().setTitle("Lampnotificaties");

        } else if (id == R.id.nav_map) {
            finish();
            startActivity(new Intent(MainActivity.this, MapActivity.class));

        }else if (id == R.id.nav_settings) {
            fragmentClass = Preferences.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            getSupportActionBar().setTitle("Instellingen");

        } else if (id == R.id.logout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }

    public void onResume() {
        super.onResume();
        int minutes = 5;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        alarmManager.cancel(pendingIntent);
        if (minutes > 0) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + minutes * 60 * 1000, minutes * 60 * 1000, pendingIntent);
        }
    }

    public void retrieveUserRole() {
        DatabaseReference ref_userRole = mDatabase.getReference().child("users").child(user.getUid()).child("rollen");

        ref_userRole.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userM = dataSnapshot.getValue(UserModel.class);
                userRole = userM.getUserRole();
                Log.d("MainActivity", "Value from database: " + userRole);
                updateDrawer();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("MainActivity", "credentialsUserRoleGet:failure", databaseError.toException());
            }
        });

    }

    private void updateDrawer() {
        if(userRole.equalsIgnoreCase("guest")){
            navigationMenu.findItem(R.id.nav_lamp).setVisible(false);
            navigationMenu.findItem(R.id.nav_pis).setVisible(false);
            navigationMenu.findItem(R.id.nav_notificaties).setVisible(false);
            navigationMenu.findItem(R.id.nav_statistics).setVisible(false);
            navigationMenu.findItem(R.id.nav_map).setVisible(true);

        }
    }

}
