package com.example.quizzical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.quizzical.util.Constants;
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


public class RegisterActivity extends BaseActivity {

    EditText name, email, password, confirmPassword;
    Button register;
    TextView login;
    TextInputLayout nameTextInput, email_TextInput, password_TextInput, confirm_TextInput;

    final String role="user";
    final String role1="Admin";

    //private FirebaseAuth auth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
        confirmPassword = (EditText) findViewById(R.id.confirm_Password);
        nameTextInput = (TextInputLayout) findViewById(R.id.name_TextInput);
        email_TextInput = (TextInputLayout) findViewById(R.id.email_Text_Input);
        password_TextInput = (TextInputLayout) findViewById(R.id.password_Text_Input);
        confirm_TextInput = (TextInputLayout) findViewById(R.id.Confirm_TextInput);

        //get firebase auth instance
//        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {

    }

    public void validation() {
        if (!name.getText().toString().trim().equals("")) {
            if (!email.getText().toString().trim().equals("")) {
                nameTextInput.setError(null);
                if (!password.getText().toString().trim().equals("")) {
                    email_TextInput.setError(null);
                    if (!confirmPassword.getText().toString().trim().equals("")) {
                        password_TextInput.setError(null);
                        if (confirmPassword.getText().toString().trim().equals(password.getText().toString().trim())) {
                            confirm_TextInput.setError(null);
                            createUserLogin(email, password);
                            valueRegisterInDatabase(name,email,password,confirmPassword);
                           // createUserEmailAndPasswordAuth(email, password);
                        } else {
                            confirm_TextInput.setError(getString(R.string.password_confirm));
                        }
                    } else {
                        confirm_TextInput.setError(getString(R.string.confirm_passwod_error));
                        confirm_TextInput.setErrorIconDrawable(0);
                    }
                } else {
                    password_TextInput.setError(getResources().getString(R.string.password_error));
                    password_TextInput.setErrorIconDrawable(0);
                }
            } else {
                email_TextInput.setError(getResources().getString(R.string.email_error));
                email_TextInput.setErrorIconDrawable(0);
            }
        } else {
            nameTextInput.setError(getResources().getString(R.string.name_error));
            nameTextInput.setErrorIconDrawable(0);
        }
    }


    //Save the value in shared prefrences
    private void createUserLogin(EditText email, EditText password) {
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString();

        baseActivityPreferenceHelper.putString(Constants.PREF_EMAIL,emailStr);
        baseActivityPreferenceHelper.putString(Constants.PREF_PASSWORD,passwordStr);
        baseActivityPreferenceHelper.putBoolean(Constants.PREF_LOGIN,true);

    }

    // value gets register in real time  database firebase
    public void valueRegisterInDatabase(EditText name,EditText email,EditText password,EditText confirmPassword)
    {
        String userName=name.getText().toString().trim();
        String userEmail=email.getText().toString().trim();
        String userPassword=password.getText().toString().trim();
        String userConfirmPassword=confirmPassword.getText().toString().trim();


        rootNode =FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Users").child(userEmail.substring(0, userEmail.indexOf("@")));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null && snapshot.child("email").getValue().toString().equalsIgnoreCase(userEmail)){
                    Toast.makeText(RegisterActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
                    email.setText("");
                }
                else{
                    if(userEmail.contains("bipin@gmail.com"))
                    {
                        UserInfo info=new UserInfo(userName,userEmail,userPassword,userConfirmPassword,role1);
                        reference.setValue(info);
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        UserInfo info=new UserInfo(userName,userEmail,userPassword,userConfirmPassword,role);
                        reference.setValue(info);
                        Toast.makeText(RegisterActivity.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    //save the data in the firebase
//    private void createUserEmailAndPasswordAuth(EditText email, EditText password) {
//        String emailStr = email.getText().toString().trim();
//        String passwordStr = password.getText().toString().trim();
//
//        // create user
//        auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(RegisterActivity.this, " Registration Done Now loggin", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//
//                }
//                else{
//                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

}