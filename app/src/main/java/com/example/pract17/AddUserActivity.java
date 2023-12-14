package com.example.pract17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    Button addPersonButton;
    EditText nameEditText, emailEditText, profileImageUrlEditText;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        dbHelper = new DatabaseHelper(this);


        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        profileImageUrlEditText = findViewById(R.id.editTextProfileImageUrl);
        addPersonButton = findViewById(R.id.buttonSaveUser);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String profileImageUrl = profileImageUrlEditText.getText().toString();

                User newUser = new User(1, name, email, profileImageUrl);

                dbHelper.addUser(newUser);

                nameEditText.setText("");
                emailEditText.setText("");
                profileImageUrlEditText.setText("");

                Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
