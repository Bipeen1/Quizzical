package com.example.quizzical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectToBeAdd extends AppCompatActivity {

    Button addSubject;
    EditText subjectName;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_to_be);
        this.setTitle("Add Subject");
        addSubject = (Button) findViewById(R.id.submit_subject);
        subjectName = (EditText) findViewById(R.id.subject_name);

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject = subjectName.getText().toString().trim();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Subjects");
                reference.child(subject).setValue(subject);
                Toast.makeText(SubjectToBeAdd.this, "subject Added", Toast.LENGTH_SHORT).show();
                subjectName.setText("");
                finish();

            }
        });
    }
}