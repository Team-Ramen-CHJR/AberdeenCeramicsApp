package com.example.aberdeenceramicsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addNewsletter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addNewsletter extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore firestore;


    public addNewsletter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addNewsletter.
     */
    // TODO: Rename and change types and number of parameters
    public static addNewsletter newInstance(String param1, String param2) {
        addNewsletter fragment = new addNewsletter();
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
        return inflater.inflate(R.layout.fragment_add_newsletter, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addBtn = view.findViewById(R.id.Add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firestore = FirebaseFirestore.getInstance();

                final EditText titleInput = (EditText) v.findViewById(R.id.titleInput);
                String title = titleInput.getText().toString();
                final EditText textInput = (EditText) v.findViewById(R.id.textInput);
                String text = textInput.getText().toString();

                Map<String, Object> news = new HashMap<>(); //each key/value pair in the map corresponds to a line of JSON. key being the variable name and value being the data

                news.put("title", title);
                news.put("description", text); // this is for timestamp. Im going to have a look at formatting it better but for now dont worry about it
                news.put("time", System.currentTimeMillis());
                //line below is for adding. think the listener isn't needed but it can't hurt
                firestore.collection("newsletters").add(news).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("success");
                    }
                });


            }
        });

    }


}