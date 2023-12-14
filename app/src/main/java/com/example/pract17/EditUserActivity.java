package com.example.pract17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditUserActivity extends AppCompatActivity {

    Button editPersonButton;
    EditText nameEditText, emailEditText, profileImageUrlEditText;

    int id;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        dbHelper = new DatabaseHelper(this);


        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        profileImageUrlEditText = findViewById(R.id.editTextProfileImageUrl);
        editPersonButton = findViewById(R.id.buttonSaveUser);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<User> userList = dbHelper.getUserArray();
        User user = userList.get(id);

        id = user.getId();
        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        profileImageUrlEditText.setText(user.getProfileImageUrl());

        editPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String profileImageUrl = profileImageUrlEditText.getText().toString();

                User newUser = new User(id, name, email, profileImageUrl);

                dbHelper.updateUser(newUser);

                Toast.makeText(getApplicationContext(), "User Edited", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditUserActivity.this, DetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}