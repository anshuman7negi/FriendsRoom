package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Model.Users;
import com.example.whatsappclone.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {

    ActivitySignupBinding binding;

    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       getSupportActionBar().hide();

        progressDialog =new ProgressDialog(Signup_Activity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating account");


        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.grayBackground));
        }


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                auth.createUserWithEmailAndPassword
                        (binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Signup_Activity.this, "Register successfully", Toast.LENGTH_SHORT).show();

                            Users user=new Users(binding.etUserName.getText().toString(),binding.etEmail.getText().toString(),
                                    binding.etPassword.getText().toString());


                            String id= task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                        }
                      else
                        {
                            Toast.makeText(Signup_Activity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        

                    }
                });
            }
        });

        binding.haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup_Activity.this,SignIn_Activity.class);
                startActivity(intent);
                finish();
            }
        });






    }


}