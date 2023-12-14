package com.example.pract17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {


    private ImageButton backButton, editButton, deleteButton;
    private TextView nameTextView, emailTextView;
    private ImageView profileImageView;

    int id, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        profileImageView = findViewById(R.id.profileImageView);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            position = intent.getIntExtra("id", 0);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<User> userList = dbHelper.getUserArray();
        User user = userList.get(position);

        id = user.getId();

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        Picasso.get().load(user.getProfileImageUrl()).placeholder(R.drawable.placeholder_image).into(profileImageView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditUserActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteUser(user.getId());
                Toast.makeText(DetailActivity.this,"User Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
