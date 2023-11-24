package com.example.aberdeenceramicsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link classes_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class classes_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public classes_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment classes_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static classes_fragment newInstance(String param1, String param2) {
        classes_fragment fragment = new classes_fragment();
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
        View rootView = inflater.inflate(R.layout.fragment_classes_fragment, container, false);
        //array of button ids
        int[] buttonIds = {R.id.C1_Book, R.id.c2_Book, R.id.c3_Book, R.id.C4_Book, R.id.C5_Book, R.id.C6_Book, R.id.C7_Book};

        //Hashmap of buttons to urls
        HashMap<Integer, String> buttonUrlMap = new HashMap<>();
        buttonUrlMap.put(R.id.C1_Book, "https://www.aberdeenceramicsstudio.com/service-page/handbuilding-tuesdays?referral=service_list_widget");
        buttonUrlMap.put(R.id.c2_Book, "https://www.aberdeenceramicsstudio.com/service-page/throwing-tuesdays?referral=service_list_widget");
        buttonUrlMap.put(R.id.c3_Book, "https://www.aberdeenceramicsstudio.com/service-page/throwing-thursdays?referral=service_list_widget");
        buttonUrlMap.put(R.id.C4_Book, "https://www.aberdeenceramicsstudio.com/service-page/mug-making-workshop?referral=service_list_widget");
        buttonUrlMap.put(R.id.C5_Book, "https://www.aberdeenceramicsstudio.com/service-page/four-week-pottery-wheel-course-3?referral=service_list_widget");
        buttonUrlMap.put(R.id.C6_Book, "https://www.aberdeenceramicsstudio.com/service-page/kid-s-clay-creature-class-1?referral=service_list_widget");
        buttonUrlMap.put(R.id.C7_Book, "https://www.aberdeenceramicsstudio.com/service-page/handbuild-your-own-tea-light-holder?referral=service_list_widget");

        //on click listener
        View.OnClickListener bookClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buttonUrlMap.get(v.getId());
                if(url != null){
                    linkToClassOnSite(url);
                }
            }
        };

        for (int buttonId : buttonIds){
            rootView.findViewById(buttonId).setOnClickListener(bookClickListener);
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public void linkToClassOnSite(String url){
        Intent intentBook = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intentBook);

    }
}