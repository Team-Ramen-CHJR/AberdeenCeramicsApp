package com.example.aberdeenceramicsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    private static Instant start;
    private static boolean loggedIn;
    private static boolean clockedIn;
    private static boolean admin;

    private static int timeToShow;

    private static String userEmail;

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        MainActivity.userEmail = userEmail;
    }

    public static boolean isClockedIn() {
        return clockedIn;
    }

    public static int getTimeToShow() {
        return timeToShow;
    }

    public static void setTimeToShow(int timeToShow) {
        MainActivity.timeToShow = timeToShow;
    }

    public static void setClockedIn(boolean clockedIn) {
        MainActivity.clockedIn = clockedIn;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        MainActivity.admin = admin;
    }

    BottomNavigationView bottomNavigationView;

    public void setStart(Instant start){
        this.start = start;
    };
    public Instant getStart(){
        return this.start;
    };



    public boolean isLoggedIn() {
        return loggedIn;
    }



    public static void setLoggedIn(boolean loggedInSet) {
        loggedIn = loggedInSet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoggedIn(false);
        setAdmin(false);
        setClockedIn(false);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();

        // this call should be moved to sign in once that is implemented instant start
        //Instant start = startTimer();





        //navstuff

        //define fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        //create nav controller
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.navbarView);
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            //temp vars Can be changed later
            boolean clockedIn = false;

            if(itemId == R.id.profile_menu){
                if(loggedIn){
                    navController.navigate(R.id.profile_fragment);
                } else if (loggedIn == false){
                    navController.navigate(R.id.signIn_fragment);
                }
            } else if (itemId == R.id.clockIn_menu){
                if(clockedIn){
                    navController.navigate(R.id.clockOut_fragment);
                } else if (clockedIn == false) {
                    navController.navigate(R.id.clockIn_fragment);
                }
            } else if (itemId == R.id.safety_menu){
                navController.navigate(R.id.safety_fragment);
            } else if (itemId == R.id.classes_menu){
                navController.navigate(R.id.classes_fragment);
            }

            return true;

        });
    }

    public Instant startTimer(){
        TextView clock = (TextView)findViewById(R.id.userTimer);
        Instant start = Instant.now();
        setStart(start);
        Thread timer;
        timer = new Thread(){
            @Override public void run(){
                for (;;) {
                    try { Thread.sleep(1000L);
                    } catch (InterruptedException ex) {/* ignore */ }

                    long seconds = Duration.between(start, Instant.now()).getSeconds();
                    long absSeconds = Math.abs(seconds);
                    String formattedTime = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60); // https://stackoverflow.com/a/266846 accessed 05/11/2023

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clock.setText(formattedTime);
                        }
                    });

                }}
        };
        timer.setDaemon(true);
        timer.start();
        return start;
    }

    }
