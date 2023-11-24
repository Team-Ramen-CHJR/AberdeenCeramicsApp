package com.example.aberdeenceramicsapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class signIn_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    FirebaseFirestore firestore;

    public signIn_fragment() {
        // Required empty public constructor
    }

    public static signIn_fragment newInstance(String param1, String param2) {
        signIn_fragment fragment = new signIn_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in_fragment, container, false);
    }

    //function to check if the users time should be reset, lastLogIn should be in the format String timestamp = "23 November 2023 17:40:52";
    public static boolean check(String lastLogIn){
        boolean reset = false;
        Calendar c = Calendar.getInstance();
        Date today = new Date();
        System.out.println(today);

        c.setTime(today);





        String[] parts = lastLogIn.split(" ");
        String dateString = String.format("%s %s %s", parts[0], parts[1], parts[2]);
        String timeString = parts[3];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(dateString + " " + timeString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        c.setTime(date);

        while (!c.getTime().after(today)) {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                return true;
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        return reset;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button clockBtn = view.findViewById(R.id.SignUp);

        clockBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firestore = FirebaseFirestore.getInstance();

                final EditText emailInput = (EditText) v.findViewById(R.id.EmailInput);
                String email = emailInput.getText().toString();
                final EditText passwordInput = (EditText) v.findViewById(R.id.PasswordInput);
                String password = passwordInput.getText().toString();

                firestore.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String dbPass = " ";
                            String lastLogIn = " ";
                            int timeLeft = 0;
                            String membership = " ";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Fetch from database as Map
                                dbPass = (String) document.getData().get("password");
                                lastLogIn = (String) document.getData().get("lastLog");
                                timeLeft = (int) document.getData().get("time remaining");
                                membership = (String) document.getData().get("membership");
                            }

                            if (password.equals(dbPass)) {
                                MainActivity.setLoggedIn(true);
                                if(membership == "admin"){
                                  MainActivity.setAdmin(true);
                                };
                                boolean reset = check(lastLogIn);
                                if (reset) {
                                    if (membership == "pt") {
                                        timeLeft = 21600;
                                    } else if (membership == "ft") {
                                        timeLeft = 43200;
                                    }
                                    final int timeFinal = timeLeft;
                                    firestore.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if (task.isSuccessful() && !task.getResult().isEmpty()) {

                                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                String docID = documentSnapshot.getId();

                                                firestore.collection("users").document(docID).update("time remaining", timeFinal);
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    //this will need properly parsed if we need to use any of the data (which we should)
                                                    System.out.println(document.getId() + " = " + document.getData());
                                                }
                                            } else {
                                                System.out.println("didnt work");
                                            }
                                        }
                                    });

                                }

                            }
                        }
                    }
                });
            }
        });


        //log in data getting code

        //stuff for pulling from firebase. whereEqualTo searches the collection for records with the field that matches the specified values

        //code to be used when the user logs in. lastLogIn is lastLog from fb, timeLeft is time remaining


    }
};