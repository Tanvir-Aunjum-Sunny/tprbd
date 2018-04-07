package com.tpr.andromeda.teenpattireseller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {


    EditText eTuserName,eTplayerId,eTcramount,eTphoneNo,eTPassword,eTconfirmPassword;
    Button registerBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference userDatabase,userdatabaseChild1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_screen);
        userDatabase = FirebaseDatabase.getInstance().getReference("User");

        eTuserName = (EditText) findViewById(R.id.etusername);
        eTplayerId = (EditText) findViewById(R.id.etplayerid);
        eTphoneNo = (EditText) findViewById(R.id.etphoneno);
        eTPassword = (EditText) findViewById(R.id.etpassword);
        eTcramount = (EditText) findViewById(R.id.etcramount);
        eTconfirmPassword = (EditText) findViewById(R.id.etconfirmpassword);
        registerBtn = (Button) findViewById(R.id.registerbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    public void regbtn(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    public void logbtn(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    private void registerUser(){
        String userName = eTuserName.getText().toString().trim();
        String playerId = eTplayerId.getText().toString().trim();
        String phoneNo = eTphoneNo.getText().toString().trim();
        String cramount = eTcramount.getText().toString().trim();
        String password = eTPassword.getText().toString().trim();
        String confirmPassword = eTconfirmPassword.getText().toString().trim();

        if(userName.isEmpty()){
            eTuserName.setError("User Name Can't Be Empty");
            eTuserName.requestFocus();
            return;
        }
        if (playerId.isEmpty()){
            eTplayerId.setError("Player Id Can't Be Empty");
            eTplayerId.requestFocus();
            return;
        }
        if (phoneNo.isEmpty()){
            eTphoneNo.setError("Phone Number Can't Be Empty");
            eTphoneNo.requestFocus();
            return;
        }
        if (cramount.isEmpty()){
            eTphoneNo.setError("CR amount Can't Be Empty");
            eTphoneNo.requestFocus();
            return;
        }
        if (password.isEmpty()){
            eTPassword.setError("Password Can't Be Empty");
            eTPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            eTPassword.setError("Minimum Password Length Is 6");
            eTPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()){
            eTconfirmPassword.setError("Confirm Password Can't Be Empty");
            eTconfirmPassword.requestFocus();
            return;
        }
        if(!userName.isEmpty()||!playerId.isEmpty() ||!phoneNo.isEmpty()||!cramount.isEmpty()|| !password.isEmpty()||!confirmPassword.isEmpty()){
            //String key="player";
            String id = userDatabase.push().getKey();
            User user = new User(id,userName,playerId,phoneNo,cramount,password);
            progressBar.setVisibility(View.VISIBLE);
            userDatabase.child(id).setValue(user);
            Toast.makeText(this,"Registered Successfully",Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);




    }


}
