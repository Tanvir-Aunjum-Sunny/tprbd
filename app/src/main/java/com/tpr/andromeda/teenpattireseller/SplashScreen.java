package com.tpr.andromeda.teenpattireseller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageViewLogo = findViewById(R.id.imageView_logo);
        TextView textViewAppMessage = findViewById(R.id.textView_app_message);

        Animation loadAnimation = AnimationUtils.loadAnimation(this,R.anim.alpha_transation);
        imageViewLogo.startAnimation(loadAnimation);
        textViewAppMessage.startAnimation(loadAnimation);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                    startActivity(new Intent(getApplicationContext(),RegisterScreen.class));
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
