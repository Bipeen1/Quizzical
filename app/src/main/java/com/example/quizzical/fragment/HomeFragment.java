package com.example.quizzical.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quizzical.R;

public class HomeFragment extends Fragment {
    Button addSubject, addQuestion, giveTest;

    //Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen_fragment, container, false);
        addSubject = view.findViewById(R.id.add_subject);
        addQuestion = view.findViewById(R.id.add_question);
        giveTest = view.findViewById(R.id.give_test);
        //toolbar=view.findViewById(R.id.toolbar);

        addSubject.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AddSubjectFragment addSubjectFragment = new AddSubjectFragment();
                Bundle bundle=new Bundle();
                bundle.putString("FROM_SCREEN", "ADD_SUBJECT");
                //set Fragmentclass Arguments
                addSubjectFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addSubjectFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AddSubjectFragment addSubjectFragment = new AddSubjectFragment();
                Bundle bundle=new Bundle();
                bundle.putString("FROM_SCREEN", "ADD_QUESTION");
                //set Fragmentclass Arguments
                addSubjectFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addSubjectFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                toolbar.setTitle("Add Question");
            }
        });

        giveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveTestFragment giveTestFragment = new GiveTestFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, giveTestFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
