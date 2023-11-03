package com.example.aberdeenceramicsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    //define the nav bar
    BottomNavigationView bottomNavbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavbarView = findViewById(R.id.navbarView);
        bottomNavbarView.setOnItemSelectedListener(this);
        bottomNavbarView.setSelectedItemId(R.id.profile_nb);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;


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
}