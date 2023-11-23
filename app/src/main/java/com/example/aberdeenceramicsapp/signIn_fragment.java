package com.example.aberdeenceramicsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signIn_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signIn_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signIn_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signIn_fragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in_fragment, container, false);
    }



   //log in data getting code



    //code to be used when the user logs in. lastLogIn is lastLog from fb, timeLeft is time remaining

//    boolean reset = check(lastLogIn);
//        if(reset){
//        if(membership=="pt"){
//            timeLeft = 21600;
//        }
//        else if(membership == "ft"){
//            timeLeft = 43200;
//        }
//    }

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
}