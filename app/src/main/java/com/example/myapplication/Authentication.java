package com.example.myapplication;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Authentication extends AppCompatActivity {

    private Button continueAsUser;
    private Button continueAsClub;
    private TextView option;
    private TextView optionText;
    private TextView title;
    private LinearLayout signUpCP;
    private boolean signUp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        continueAsUser = findViewById(R.id.student_user);
        continueAsClub = findViewById(R.id.club_user);
        optionText = findViewById(R.id.option_text);
        signUpCP = findViewById(R.id.linearCP);
        title = findViewById(R.id.auth_title);
        option = findViewById(R.id.option);

    }

    public void onloginUser(View view){
        Intent intent = new Intent(Authentication.this, MainActivity.class);
        intent.putExtra("is_user", true);
        startActivity(intent);
    }

    public void onLoginClub(View view){
        Intent intent = new Intent(Authentication.this, MainActivity.class);
        intent.putExtra("is_user", false);
        startActivity(intent);
    }

    public void onOptionClick(View view){
        signUp = !signUp;

        title.setText(signUp ? R.string.signup_title : R.string.login_title);
        continueAsUser.setText(signUp ? R.string.btn_1_signup : R.string.btn_1_login);
        continueAsClub.setText(signUp ? R.string.btn_2_signup : R.string.btn_2_login);
        optionText.setText(signUp ? R.string.yes_account : R.string.no_account);
        option.setText(!signUp ? R.string.option_signup : R.string.option_login);
        signUpCP.setVisibility(signUp ? VISIBLE : GONE);
        option.invalidate();
    }
}