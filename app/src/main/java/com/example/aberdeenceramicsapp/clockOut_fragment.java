package com.example.aberdeenceramicsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.time.Instant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clockOut_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clockOut_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore firestore;
    public clockOut_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment clockOut_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static clockOut_fragment newInstance(String param1, String param2) {
        clockOut_fragment fragment = new clockOut_fragment();
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
        String email = "test@email.com"; // have this passed from log in
        Button clockBtn = (Button) getView().findViewById(R.id.clockBtn);
        firestore = FirebaseFirestore.getInstance();
        clockBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firestore.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            System.out.println("success");
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String docID = documentSnapshot.getId();
                            Long oldTime = documentSnapshot.getLong("Time remaining");
                            MainActivity mainActivity = (MainActivity) getActivity();
                            int newTime = (int)(oldTime - Duration.between(mainActivity.getStart(), Instant.now()).getSeconds());
                            firestore.collection("users").document(docID).update("time remaining", newTime);
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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clock_out_fragment, container, false);
    }
}