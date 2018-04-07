package com.tpr.andromeda.teenpattireseller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String userId;
    private MaterialDialog userLoading;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        userLoading = new MaterialDialog.Builder(MainActivity.this)
                .title("Loading User")
                .content("Please wait...")
                .progressIndeterminateStyle(true)
                .progress(true, 0)
                .show();

        if(savedInstanceState == null)
        {

            Bundle extras = getIntent().getExtras();
            if(extras != null)
            {
                userId = extras.getString("USERID");
            }
        }
        else {
            userId = String.valueOf(savedInstanceState.getSerializable("USERID"));
        }


        final TextView tvPlayerId = findViewById(R.id.tvPlayerId);
        final TextView tvCurrentCredit = findViewById(R.id.tvCurrentCreditAmount);

        Query query = db.collection("users").whereEqualTo("userId", userId);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult().size() == 1) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String playerId = document.get("playerId").toString();
                                    String userName = document.get("userName").toString();
                                    String phoneNo = document.get("phoneNo").toString();
                                    float creditAmount = Float.parseFloat(document.get("creditAmount").toString());
                                    String user_pass = document.get("user_pass").toString();

                                    currentUser = new User(userId, userName, playerId, phoneNo, creditAmount, user_pass);

                                    tvPlayerId.setText(currentUser.getPlayerId());
                                    String totalCredit = String.valueOf(currentUser.getCreditAmount()) + " cr";
                                    tvCurrentCredit.setText(totalCredit);

                                    userLoading.dismiss();

                                    String welcome = " Welcome, " + currentUser.getUserName();
                                    Toast.makeText(MainActivity.this, welcome, Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        userLoading.dismiss();
                    }
                });

    }

    public void bankHistoryClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custome_dialogue_tabledata, null);
        final TextView tVReceriverId = alertLayout.findViewById(R.id.recevierId);
        final TextView tVchips = alertLayout.findViewById(R.id.Chips);
        final TextView tVtotalPrice = alertLayout.findViewById(R.id.totalPrice);
        final TextView tVdateTime = alertLayout.findViewById(R.id.dateTime);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Bank History");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Done Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void giftChipsbtn(View view) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custome_dialogue_giftchip, null);
        final TextView tVgiftChips = alertLayout.findViewById(R.id.tVgiftChips);
        final EditText eTgiftChips = alertLayout.findViewById(R.id.eTgiftChips);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Gift Chips");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Done Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }
    public void logoutclicked(View view) {

        new MaterialDialog.Builder(MainActivity.this)
                .title("Logout")
                .content("Do you want to logout?")
                .positiveText("Yeah!")
                .negativeText("Nope")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final Intent loginIntent = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(loginIntent);
                        finish();
                    }
                })
                .show();
    }
}
