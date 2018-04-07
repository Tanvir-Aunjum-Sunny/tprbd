package com.tpr.andromeda.teenpattireseller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        final Intent intent = new Intent(this, LoginScreen.class);
       // LayoutInflater inflater = getLayoutInflater();
       // View alertLayout = inflater.inflate(R.layout.layout_custome_dialogue_giftchip, null);
       // final TextView tVgiftChips = alertLayout.findViewById(R.id.tVgiftChips);
        //final EditText eTgiftChips = alertLayout.findViewById(R.id.eTgiftChips);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout Now");
        // this is set the view from XML inside AlertDialog
        //alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Logout Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(intent);
                Toast.makeText(getBaseContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }
}
