package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.whatsappclone.Adapters.FragmentAdapter;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       auth=FirebaseAuth.getInstance();


       binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
       binding.tabLayout.setupWithViewPager(binding.viewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.setting:
                Intent intent= new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

                break;

            case R.id.logout:

                auth.signOut();
                Intent intentt= new Intent(MainActivity.this, SignIn_Activity.class);
                startActivity(intentt);
                break;

            case R.id.groupChat:

                Intent intent1 = new Intent(MainActivity.this,GroupChatActivity.class);

                startActivity(intent1);
                break;

        }

        return true;
    }
}