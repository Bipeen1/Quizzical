package com.example.quizzical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quizzical.util.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectListActivity extends BaseActivity {
    ArrayList<String> myArrayList = new ArrayList<>();
    ListView myListView;
    DatabaseReference subjectRef;
    static String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        myListView = findViewById(R.id.listview2);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(SubjectListActivity.this, android.R.layout.simple_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);

        // show the list of value from firebase
        subjectRef = FirebaseDatabase.getInstance().getReference(Constants.questionBank).child(Constants.subjects);
        subjectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    myArrayList.add(snap.getKey().toString());
                }
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(SubjectListActivity.this, "You have selected " + selectedItem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubjectListActivity.this, QuestionToBeAdded.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {

    }
}