package com.example.aberdeenceramicsapp;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this call should be moved to sign in once that is implemented
        Instant start = startTimer();

    }

    public Instant startTimer(){
        TextView clock = (TextView)findViewById(R.id.userTimer);
        Instant start = Instant.now();
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
    /*
    public void databaseStuff(){

        some random example code from tools>firbase> cloud firestore
        URL url = new URL("https://teamramen-c8713-default-rtdb.europe-west1.firebasedatabase.app//") // this should be the url but I might be wrong. it will need to be passed when using getInstance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
Map<String, Object> user = new HashMap<>();
user.put("first", "Ada");
user.put("last", "Lovelace");
user.put("born", 1815);

// Add a new document with a generated ID
db.collection("users")
        .add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

        // Create a new user with a first, middle, and last name
Map<String, Object> user = new HashMap<>();
user.put("first", "Alan");
user.put("middle", "Mathison");
user.put("last", "Turing");
user.put("born", 1912);

// Add a new document with a generated ID
db.collection("users")
        .add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

}

         */
