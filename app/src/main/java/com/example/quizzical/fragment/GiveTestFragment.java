package com.example.quizzical.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizzical.DialogueActivity;
import com.example.quizzical.R;
import com.example.quizzical.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiveTestFragment extends Fragment {
    Spinner spinner;
    DatabaseReference subjectRef;
    List<String> myArrayList = new ArrayList<>();
    Button submitSubject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.give_test_fragment, container, false);
        spinner = view.findViewById(R.id.spinner);
        submitSubject = view.findViewById(R.id.submit_button);


        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, myArrayList);
        spinner.setAdapter(myArrayAdapter);


        subjectRef = FirebaseDatabase.getInstance().getReference(Constants.questionBank).child(Constants.subjects);

        subjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myArrayList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {

                    myArrayList.add(snap.getKey());
                }

                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogue();
            }
        });

        return view;
    }

    public void openDialogue() {
        DialogueActivity dialogueActivity = new DialogueActivity();
        dialogueActivity.show(getFragmentManager(), "Dialogue alert");
    }

}
