package com.example.quizzical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzical.util.Constants;
import com.example.quizzical.util.PreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.prefs.PreferenceChangeListener;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    EditText email, password;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailTextInput, passwordTextInput;
    //private FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        login = (Button) findViewById(R.id.loginButton);
        register = (TextView) findViewById(R.id.register);
        emailTextInput = (TextInputLayout) findViewById(R.id.email_TextInput);
        passwordTextInput = (TextInputLayout) findViewById(R.id.password_TextInput);


        //  auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void init() {

    }


    public void validation() {
//
        // Check for a valid email address.
        if (email.getText().toString().trim().equals("")) {
            emailTextInput.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
            emailTextInput.setErrorIconDrawable(0);

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailTextInput.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
            emailTextInput.setErrorIconDrawable(0);
            emailTextInput.setError(null);
        } else {
            isEmailValid = true;
        }


        // Check for a valid password.
        if (password.getText().toString().trim().equals("")) {
            passwordTextInput.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
            passwordTextInput.setErrorIconDrawable(0);

        } else if (password.getText().length() < 6) {
            passwordTextInput.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
            passwordTextInput.setErrorIconDrawable(0);
            passwordTextInput.setError(null);
        } else {
            isPasswordValid = true;
        }

        if (isEmailValid && isPasswordValid) {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();

            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("Users");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        if (dataSnapshot1.getValue() != null && dataSnapshot1.child("email").getValue().toString().equalsIgnoreCase(emailStr)
                                && dataSnapshot1.child("password").getValue().toString().equals(passwordStr)) {

                            baseActivityPreferenceHelper.putBoolean(Constants.PREF_LOGIN, true);
                            baseActivityPreferenceHelper.putString(Constants.PREF_EMAIL, emailStr);
                            baseActivityPreferenceHelper.putString(Constants.PREF_PASSWORD, passwordStr);

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Email or Password is Incorrect!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });

            //authUser(email,password);
//            preferences = getSharedPreferences("Userinfo", MODE_PRIVATE);
//            String prefEmail = preferences.getString("email", "yadavbipin096@gmail.com");
//            String prefPassword = preferences.getString("password", "123456");
//
//            if (email.getText().toString().trim().equals(prefEmail) && password.getText().toString().trim().equals(prefPassword)) {
//                Toast.makeText(this, "successfully logged in", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, "Incorrect email and Password", Toast.LENGTH_SHORT).show();
//            }
        }
    }

//    private void authUser(EditText email,EditText password){
//        String emailStr=email.getText().toString().trim();
//        String passwordStr=password.getText().toString().trim();
//
//        auth.signInWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    createUserLogin(email,password);
//                    Toast.makeText(LoginActivity.this, "Sucessfully logged in", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, " email and Password does not match", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


    private void createUserLogin(EditText email, EditText password) {
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        baseActivityPreferenceHelper.putString(Constants.PREF_PASSWORD, passwordStr);
        baseActivityPreferenceHelper.putString(Constants.PREF_EMAIL, emailStr);
        baseActivityPreferenceHelper.putBoolean(Constants.PREF_LOGIN, true);

    }


}