package com.example.quizzical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzical.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    DatabaseReference questionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        myListView = findViewById(R.id.listview3);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(QuestionList.this, android.R.layout.simple_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);

        String selectedSubject = getIntent().getStringExtra("SUBJECT_SELECTED");

        questionRef = FirebaseDatabase.getInstance().getReference(Constants.questionBank).child(Constants.subjects).child(selectedSubject);
        questionRef.addValueEventListener(new ValueEventListener() {
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


        floatingActionButton = findViewById(R.id.fab2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionList.this, QuestionToBeAdded.class);
                intent.putExtra("SUBJECT_SELECTED", selectedSubject);
                startActivity(intent);
                finish();
            }
        });
    }
}