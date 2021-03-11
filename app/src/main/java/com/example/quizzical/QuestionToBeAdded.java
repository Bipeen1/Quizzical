package com.example.quizzical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionToBeAdded extends AppCompatActivity {
    Button clear, submit;
    RadioGroup radioGroup;
    DatabaseReference questionReference;
    EditText questionEditText, option1EditText, option2EditText, option3EditText, option4EditText;
    String correctValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_to_be_added);

        clear = findViewById(R.id.clear_everthing);
        submit = findViewById(R.id.submit_Question);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        questionEditText = (EditText) findViewById(R.id.questions_edittext);
        option1EditText = (EditText) findViewById(R.id.edittext_option1);
        option2EditText = (EditText) findViewById(R.id.edittext_option2);
        option3EditText = (EditText) findViewById(R.id.edittext_option3);
        option4EditText = (EditText) findViewById(R.id.edittext_option4);
       // AddSubjectFragment asf=new AddSubjectFragment();

        String selectedSubject = getIntent().getStringExtra("SUBJECT_SELECTED");
        System.out.println("Selected Subject: EUstion Add Screen Get: " + selectedSubject);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                correctValue = findRadioButton(checkedId);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuestionToBeAdded.this, "Please select the correct option", Toast.LENGTH_SHORT).show();
                } else {
                    questionReference = FirebaseDatabase.getInstance().getReference("QuestionBank").child("Subjects").child(selectedSubject);
                    questionReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String questionValue = questionEditText.getText().toString().trim();
                            String option1 = option1EditText.getText().toString().trim();
                            String option2 = option2EditText.getText().toString().trim();
                            String option3 = option3EditText.getText().toString().trim();
                            String option4 = option4EditText.getText().toString().trim();

                            Questions question = new Questions(questionValue, option1, option2, option3, option4, correctValue);
                            questionReference.child(questionValue).setValue(question);
                            Toast.makeText(QuestionToBeAdded.this, "Value added to database", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QuestionToBeAdded.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
            }
        });

    }

    public String findRadioButton(int checked) {
        String value = "value not selected";
        switch (checked) {
            case R.id.radioButton1:
                value = option1EditText.getText().toString().trim();
                break;
            case R.id.radioButton2:
                value = option2EditText.getText().toString().trim();
                break;
            case R.id.radioButton3:
                value = option3EditText.getText().toString().trim();
                break;
            case R.id.radioButton4:
                value = option4EditText.getText().toString().trim();
                break;
        }
        return value;
    }
}