package com.example.quizzical.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizzical.QuestionToBeAdded;
import com.example.quizzical.R;
import com.example.quizzical.SubjectToBeAdd;
import com.example.quizzical.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddSubjectFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    ListView myListView;
    DatabaseReference subjectRef;
    ArrayList<String> myArrayList = new ArrayList<>();

    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_subject_fragment, container, false);

        context = getActivity();
        floatingActionButton = view.findViewById(R.id.fab);
        myListView = view.findViewById(R.id.listview1);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);


        String sourceScreen = getArguments().getString("FROM_SCREEN");
        if (sourceScreen.equals("ADD_QUESTION")) {
            //set fab button hide
            floatingActionButton.setVisibility(View.GONE);
            myListView.setClickable(true);
            myListView.setEnabled(true);

        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
            myListView.setClickable(false);
            myListView.setEnabled(false);
        }

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(context, "You have selected " + selectedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionToBeAdded.class);
                intent.putExtra("SUBJECT_SELECTED", selectedItem);
                startActivity(intent);
            }
        });


        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int which_item = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Are you sure ?");
                builder.setMessage("Do you want to delete this subject ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String arr = myArrayList.remove(which_item);
                        subjectRef = FirebaseDatabase.getInstance().getReference("QuestionBank").child("Subjects").child(arr);
                        subjectRef.removeValue();
                        myArrayAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), arr + " Subject deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
            }
        });


//        subjectRef = FirebaseDatabase.getInstance().getReference("QuestionBank").child("Subjects");
//        subjectRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String value = snapshot.getValue(String.class);
//                myArrayList.add(value);
//                myArrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                myArrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                myArrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        subjectRef = FirebaseDatabase.getInstance().getReference(Constants.questionBank).child(Constants.subjects);
        subjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myArrayList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    myArrayList.add(snap.getKey().toString());
                }
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        subjectRef = FirebaseDatabase.getInstance().getReference(Constants.questionBank).child(Constants.subjects);
//        subjectRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    myArrayList.add(snap.getValue().toString());
//                }
//                myArrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SubjectToBeAdd.class);
                startActivity(intent);
            }
        });
        return view;
    }


}

