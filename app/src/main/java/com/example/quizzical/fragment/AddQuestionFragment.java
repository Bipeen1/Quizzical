  package com.example.quizzical.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizzical.QuestionToBeAdded;
import com.example.quizzical.R;
import com.example.quizzical.SubjectListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddQuestionFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.add_question_fragment,container,false);
        floatingActionButton=view.findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SubjectListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
