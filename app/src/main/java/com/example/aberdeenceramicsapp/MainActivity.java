package com.example.aberdeenceramicsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        // this call should be moved to sign in once that is implemented instant start
        Instant start = startTimer();

        //adding a user
        Map<String, Object> users = new HashMap<>(); //each key/value pair in the map corresponds to a line of JSON. key being the variable name and value being the data

        users.put("email", "newuser@email.com");
        users.put("lastLog", System.currentTimeMillis()); // this is for timestamp. Im going to have a look at formatting it better but for now dont worry about it
        String membership = "pt"; //using a variable instead of just putting pt straight in as I am reusing it later
        users.put("membership", membership);
        users.put("password", "Password2");
        int time = 21600; // some stuff for membership time (6 hours in seconds)
        if(membership == "ft" || membership == "admin"){
            time = 43200; //12 hours
        }
        users.put("time remaining", time);
        //line below is for adding. think the listener isn't needed but it can't hurt
        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("success");
            }
        });
        //stuff for pulling from firebase. whereEqualTo searches the collection for records with the field that matches the specified values
        String input = "newuser@email.com";
        firestore.collection("users").whereEqualTo("email", input).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    System.out.println("success");
                    for(QueryDocumentSnapshot document : task.getResult()){
                        //this will need properly parsed if we need to use any of the data (which we should)
                        System.out.println(document.getId() + " = " + document.getData());
                    }
                }else{
                    System.out.println("didnt work");
                }
            }
        });
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
