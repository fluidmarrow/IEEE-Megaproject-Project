package com.example.e_fir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tvHeading;
    EditText etMobile, etOTP;
    CardView cvOTP;
    int otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHeading = findViewById(R.id.tvHeading);
        etMobile = findViewById(R.id.etMobile);
        cvOTP = findViewById(R.id.cvOTP);

        tvHeading.setTranslationY(-500f);
        etMobile.setTranslationY(900f);
        cvOTP.setTranslationY(1500f);
        tvHeading.animate().translationYBy(500f);
        etMobile.animate().translationYBy(-900f);
        cvOTP.animate().translationYBy(-1500f);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        cvOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                }
                else{

                    try {
                        String api="AolHa8PNKw7ZGRWpDJYSUCz3FQ4Eq5ue1v2cfO9BgdbysjitxTbSL9Vy32KF7ZQXINDH4Ocahj8mnfGC";
                        otp = new Random().nextInt(8998) ;
//                        HttpResponse response = Unirest.post("https://www.fast2sms.com/dev/bulk")
//                                .header("authorization", "YOUR_API_KEY")
//                                .header("cache-control", "no-cache")
//                                .header("content-type", "application/x-www-form-urlencoded")
//                                .body("sender_id=FSTSMS&language=english&route=qt&numbers=etMobile.getText().toString()&message=39599&variables={#AA#}&variables_values=otp")
//                                .asString();
                        Toast.makeText(MainActivity.this, "OTP Successfully Sent", Toast.LENGTH_SHORT).show();
                        showDialog();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, "Error Occurred" , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Really exit?")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showDialog()
    {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View otp_view = inflater.inflate(R.layout.otp_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setView(otp_view);

        etOTP = otp_view.findViewById(R.id.etOTP);

        dialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int OTP = Integer.parseInt(etOTP.getText().toString().trim());
                if(otp==OTP){
                    Intent intent = new Intent(MainActivity.this, registerFIR.class);
                    intent.putExtra("mobile", etMobile.getText().toString().trim());
                    startActivity(intent);}
            }
        })
                .setNegativeButton("Resend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            // Construct data
                            otp = new Random().nextInt(8998) ;
//                            HttpResponse response = Unirest.post("https://www.fast2sms.com/dev/bulk")
//                                    .header("authorization", "YOUR_API_KEY")
//                                    .header("cache-control", "no-cache")
//                                    .header("content-type", "application/x-www-form-urlencoded")
//                                    .body("sender_id=FSTSMS&language=english&route=qt&numbers=etMobile.getText().toString()&message=39599&variables={#AA#}&variables_values=otp")
//                                    .asString();
                            Toast.makeText(MainActivity.this, "OTP Successfully Sent", Toast.LENGTH_SHORT).show();
                            showDialog();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}