package com.example.e_fir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView tvHeading;
    EditText etMobile, etOTP;
    CardView cvOTP;
    String mobile, verificationCodeByUser, verificationCodeBySystem;
    private static final String TAG = "MainActivity";

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
                    mobile = etMobile.getText().toString().trim();
                    sendOTP();
                }
            }
        });
    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,           //Phone number to verify
                60,                       //Timeout duration
                TimeUnit.SECONDS,
                MainActivity.this,                //Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInUser(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationID, forceResendingToken);

                        verificationCodeBySystem = verificationID;
                        Toast.makeText(MainActivity.this, "OTP Successfully Sent", Toast.LENGTH_SHORT).show();
                        showDialog();
                    }
                });                 //OnVerificationStateChangedCallbacks
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
                verificationCodeByUser = etOTP.getText().toString().trim();

                if(verificationCodeByUser.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCodeByUser);
                    signInUser(credential);
                }
            }
        })
                .setNegativeButton("Resend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendOTP();
                    }
                });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Verified Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, registerFIR.class);
                            intent.putExtra("mobile", mobile);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Verification Not Successful", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onComplete: " + task.getException().getLocalizedMessage());
                        }
                    }
                });
    }
}