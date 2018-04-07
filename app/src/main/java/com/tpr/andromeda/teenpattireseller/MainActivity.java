package com.tpr.andromeda.teenpattireseller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String userId;
    private MaterialDialog userLoading;
    User currentUser;
    String userDocumentId;

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

                                    userDocumentId = document.getId();

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
        final ListView historyListView = alertLayout.findViewById(R.id.lvBankHistory);

        ViewGroup listViewHeader = (ViewGroup)inflater.inflate(R.layout.layout_bank_history_item, historyListView, false);

        historyListView.addHeaderView(listViewHeader);

        final ArrayList<BankHistory> histories = new ArrayList<>();

        db.collection("histories").whereEqualTo("playerId", currentUser.getPlayerId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                String playerId = document.get("playerId").toString();
                                String reciverId = document.get("reciverId").toString();
                                float chips = Float.parseFloat(document.get("chips").toString());
                                String type = document.get("type").toString();
                                String time = document.get("time").toString();


                                histories.add(new BankHistory(playerId, reciverId, chips, type, time));
                            }
                            historyListView.setAdapter(new BankHistoryAdapter(MainActivity.this, histories));
                        }
                    }
                });


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Bank History");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void giftChipsbtn(View view) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custome_dialogue_giftchip, null);

        final EditText etTotalGiftChips = alertLayout.findViewById(R.id.etTotalGiftChips);
        final EditText etReciverId = alertLayout.findViewById(R.id.etReceiverId);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Gift Chips");

        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        alert.setPositiveButton("Gift", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLoading.setTitle("Processing");
                userLoading.show();

                String receiverId = etReciverId.getText().toString();
                final float numberOfChips = Float.parseFloat(etTotalGiftChips.getText().toString());

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String currentDate = dateFormatter.format(new Date());

                BankHistory bankHistory = new BankHistory(currentUser.getPlayerId(), receiverId, numberOfChips, "Gift", currentDate);

                db.collection("histories")
                        .add(bankHistory)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String documentId = documentReference.getId();
                                updateCurrentCredit(numberOfChips, documentId);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                userLoading.dismiss();
                                Toast.makeText(MainActivity.this, "Gift can not processed. Something went wrong! :(", Toast.LENGTH_LONG).show();
                            }
                        });
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

    private void updateCurrentCredit(float spent, final String spentDocumentId)
    {
        float currentUserCreditAmount = currentUser.getCreditAmount();
        float updatedAmount = currentUserCreditAmount - spent;
        currentUser.setCreditAmount(updatedAmount);
        db.collection("users").document(userDocumentId)
                .set(currentUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userLoading.dismiss();
                        Toast.makeText(MainActivity.this, "Gift Sent :)", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        userLoading.dismiss();
                        Toast.makeText(MainActivity.this, "Gift can not processed. Something went wrong! :(", Toast.LENGTH_LONG).show();
                        db.collection("histories").document(spentDocumentId).delete();
                    }
                });
    }
}
