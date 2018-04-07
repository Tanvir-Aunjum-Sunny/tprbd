package com.tpr.andromeda.teenpattireseller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginScreen extends AppCompatActivity {

    private EditText playerId;
    private EditText userPass;
    FirebaseFirestore db;
    MaterialDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        db = FirebaseFirestore.getInstance();

        playerId = findViewById(R.id.etReceiverId);
        userPass = findViewById(R.id.etUserPass);
        Button loginBtn = findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pid = playerId.getText().toString();
                String pass = userPass.getText().toString();

                loginProgressDialog = new MaterialDialog.Builder(LoginScreen.this)
                        .title("Login")
                        .content("Please wait...")
                        .progress(true, 0)
                        .show();

                Query query = db.collection("users").whereEqualTo("playerId", pid).whereEqualTo("user_pass", pass);

                query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    if(task.getResult().size() == 1) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String userId = document.get("userId").toString();

                                            loginProgressDialog.dismiss();

                                            Intent mainActivity = new Intent(LoginScreen.this, MainActivity.class);
                                            mainActivity.putExtra("USERID", userId);
                                            startActivity(mainActivity);
                                            finish();
                                        }
                                    }else{
                                        Toast.makeText(LoginScreen.this, "Wrong player id or password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                loginProgressDialog.dismiss();
                            }
                        });
            }
        });
    }
    public void regbtn(View view) {
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
        finish();
    }
}
