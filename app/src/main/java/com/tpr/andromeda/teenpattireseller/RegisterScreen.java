package com.tpr.andromeda.teenpattireseller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class RegisterScreen extends AppCompatActivity {


    EditText etUserName, etPlayerId, etCreditAmount, etPhoneNo, etPassword, etConfirmPassword;
    Button registerBtn, loginBtn;
    MaterialDialog registerProgressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_screen);
        db = FirebaseFirestore.getInstance();

        etUserName = findViewById(R.id.etusername);
        etPlayerId = findViewById(R.id.etplayerid);
        etPhoneNo = findViewById(R.id.etphoneno);
        etPassword = findViewById(R.id.etpassword);
        etCreditAmount = findViewById(R.id.etcramount);
        etConfirmPassword = findViewById(R.id.etconfirmpassword);
        registerBtn = findViewById(R.id.btnRegister);
        loginBtn = findViewById(R.id.btnLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void registerUser(){
        String userName = etUserName.getText().toString().trim();
        String playerId = etPlayerId.getText().toString().trim();
        String phoneNo = etPhoneNo.getText().toString().trim();
        String creditAmount = etCreditAmount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if(userName.isEmpty()){
            etUserName.setError("User Name Can't Be Empty");
            etUserName.requestFocus();
            return;
        }
        if (playerId.isEmpty()){
            etPlayerId.setError("Player Id Can't Be Empty");
            etPlayerId.requestFocus();
            return;
        }
        if (phoneNo.isEmpty()){
            etPhoneNo.setError("Phone Number Can't Be Empty");
            etPhoneNo.requestFocus();
            return;
        }
        if (creditAmount.isEmpty()){
            etPhoneNo.setError("CR amount Can't Be Empty");
            etPhoneNo.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password Can't Be Empty");
            etPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            etPassword.setError("Minimum Password Length Is 6");
            etPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()){
            etConfirmPassword.setError("Confirm Password Can't Be Empty");
            etConfirmPassword.requestFocus();
            return;
        }
        registerProgressDialog = new MaterialDialog.Builder(RegisterScreen.this)
                .title("Registration")
                .content("Please wait...")
                .progress(true, 0)
                .show();

        if(password.equals(confirmPassword)) {
            String userId = UUID.randomUUID().toString();
            User user = new User(userId, userName, playerId, phoneNo, Float.parseFloat(creditAmount), password);
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            registerProgressDialog.dismiss();
                            Toast.makeText(RegisterScreen.this, "Registration Successful :)", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registerProgressDialog.dismiss();
                            Toast.makeText(RegisterScreen.this, "Registration Unsuccessful :(", Toast.LENGTH_LONG).show();
                        }
                    });

        }else{
            registerProgressDialog.dismiss();
            Toast.makeText(RegisterScreen.this, "Password did not matched :(", Toast.LENGTH_LONG).show();
        }
    }
}
