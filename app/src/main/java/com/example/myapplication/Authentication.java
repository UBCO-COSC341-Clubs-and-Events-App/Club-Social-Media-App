package com.example.myapplication;

import static android.content.ContentValues.TAG;import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jspecify.annotations.NonNull;

import java.util.regex.Pattern;

public class Authentication extends AppCompatActivity {

    private Button continueAsUser;
    private Button continueAsClub;
    private TextView option;
    private TextView optionText;
    private TextView title;
    private LinearLayout signUpCP;
    private LinearLayout usernameWrapper;
    private boolean signUp = false;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private Intent intent;
    private ProgressBar progressBar;
    private EditText email_text;
    private EditText password_text;
    private EditText confirm_password_text;
    private EditText username_text;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent loggedin = new Intent(Authentication.this, MainActivity.class);
            startActivity(loggedin);
        }
    }

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
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        intent = new Intent(Authentication.this, MainActivity.class);
        email_text = findViewById(R.id.user_email);
        password_text = findViewById(R.id.user_password);
        confirm_password_text = findViewById(R.id.user_confirm_password);
        username_text = findViewById(R.id.username);
        usernameWrapper = findViewById(R.id.username_wrapper);
    }

    public void onloginUser(View view){
        email = email_text.getText().toString();
        try {
            String[] tempEmail =email.split("@");
            if (charChecker("[^a-zA-Z0-9\\\\s._-]", tempEmail[0])) {
                Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!tempEmail[1].contains(".") || charChecker("[^a-zA-Z0-9\\\\s.]", tempEmail[1])) {
                Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
            return;
        }
        password = password_text.getText().toString();
        if (password.isBlank()) {
            Toast.makeText(getApplicationContext(), R.string.err1, Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8){
            Toast.makeText(getApplicationContext(), R.string.err2, Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(VISIBLE);
        intent.putExtra("is_user", true);
        // --- FIX START ---
        // Swapped the logic. If signUp is true, create a user. Otherwise, sign in.
        if (signUp) {
            if (!password.equals(confirm_password_text.getText().toString())) {
                Toast.makeText(getApplicationContext(), R.string.err3, Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(INVISIBLE);
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                // You might want to navigate to another screen here or show a success message
                                // For example, go to MainActivity after successful sign-up
                                Intent intent = new Intent(Authentication.this, MainActivity.class);
                                intent.putExtra("is_user", true);
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("uid", user.getUid());
                                startActivity(intent);
                                finish(); // Finish this activity so the user can't go back
                            } else {
                                progressBar.setVisibility(INVISIBLE);
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else { // This is for Login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(INVISIBLE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    // The onStart() method will handle the redirect to MainActivity
                                    startActivity(new Intent(Authentication.this, MainActivity.class));
                                    finish(); // Finish this activity
                                }
                            } else {
                                progressBar.setVisibility(INVISIBLE);
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        // --- FIX END ---
    }

    public void onLoginClub(View view){
        email = email_text.getText().toString();
        try {
            String[] tempEmail =email.split("@");
            if (charChecker("[^a-zA-Z0-9\\\\s._-]", tempEmail[0])) {
                Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!tempEmail[1].contains(".") || charChecker("[^a-zA-Z0-9\\\\s.]", tempEmail[1])) {
                Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.err0, Toast.LENGTH_SHORT).show();
            return;
        }
        password = password_text.getText().toString();
        progressBar.setVisibility(VISIBLE);
        intent.putExtra("is_user", false);
        // --- FIX START ---
        // Swapped the logic. If signUp is true, create a user. Otherwise, sign in.
        if (signUp) { // This is for Sign Up
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(INVISIBLE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                // After sign-up, you should likely navigate to the main activity
                                startActivity(new Intent(Authentication.this, MainActivity.class));
                                finish(); // Finish current activity
                            } else {
                                progressBar.setVisibility(INVISIBLE);
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else { // This is for Login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(INVISIBLE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user != null){
                                    // onStart() handles redirect, so just start the activity
                                    startActivity(new Intent(Authentication.this, MainActivity.class));
                                    finish(); // Finish current activity
                                }
                            } else {
                                progressBar.setVisibility(INVISIBLE);
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        // --- FIX END ---
    }

    protected boolean charChecker(String regex, String toCheck) {
        Pattern pattern = Pattern.compile(regex);
        if(toCheck.isEmpty()) {
            return false;
        }
        return pattern.matcher(toCheck).find();
    }

    public void onOptionClick(View view){
        signUp = !signUp;

        title.setText(signUp ? R.string.signup_title : R.string.login_title);
        continueAsUser.setText(signUp ? R.string.btn_1_signup : R.string.btn_1_login);
        continueAsClub.setText(signUp ? R.string.btn_2_signup : R.string.btn_2_login);
        optionText.setText(signUp ? R.string.yes_account : R.string.no_account);
        option.setText(!signUp ? R.string.option_signup : R.string.option_login);
        signUpCP.setVisibility(signUp ? VISIBLE : GONE);
        usernameWrapper.setVisibility(signUp ? VISIBLE : GONE);
        option.invalidate();
    }
}
