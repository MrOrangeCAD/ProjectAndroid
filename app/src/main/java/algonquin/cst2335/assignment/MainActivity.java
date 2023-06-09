package algonquin.cst2335.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.assignment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.NYTButton.setOnClickListener( btn -> {
            Intent nextPage = new Intent(MainActivity.this, NYTActivity.class);
            startActivity(nextPage);
        });

    }
}