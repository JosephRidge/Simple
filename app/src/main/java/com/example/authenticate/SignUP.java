package com.example.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUP extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private Button btnSignUp ;
    private ProgressBar progressBar ;
    private FirebaseAuth auth;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        auth = FirebaseAuth.getInstance();// instance of firebaseauth object
        initializeUI();

        btnSignUp.setOnClickListener(this);

    }
    private  void registerUser(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty()){
            inputEmail.setError("Email is required..");
            inputEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Enter a valid email");

        }
        if (password.isEmpty()){
            inputPassword.setError("Password required");
            inputPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            inputPassword.setError("Minimun legnth is 6");
            inputPassword.requestFocus();
            return;
        }
        //operation signup user
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Intent intent = new Intent(SignUP.this,DashBoard.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clears all open activities
                   startActivity(intent);
               }else
               {
                   if (task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(),"Already Registered", Toast.LENGTH_SHORT).show();
                   }
                   else{
                   Toast.makeText(getApplicationContext(),"Error Occured ! ", Toast.LENGTH_SHORT).show();
               }}
            }
        });
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.letssignup:
                registerUser();
                break;



        }
    }


    private void initializeUI() {
        auth = FirebaseAuth.getInstance();
        btnSignUp = (Button) findViewById(R.id.letssignup);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerpassword);
}
}



