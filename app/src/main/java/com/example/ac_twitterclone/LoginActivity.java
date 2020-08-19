package com.example.ac_twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLogin, btnGoSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);

        btnLogin.setOnClickListener(this);
        btnGoSignup.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

            ParseUser.getCurrentUser().logOut();

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLogin:

                if (edtEmailLogin.getText().toString().equals("") ||
                        edtPasswordLogin.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,
                            "Email and Password is required!",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                            false).show();

                } else {

                    ParseUser.logInInBackground(edtEmailLogin.getText().toString(),
                            edtPasswordLogin.getText().toString(),
                            new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null && e == null) {

                                FancyToast.makeText(LoginActivity.this,
                                        user.getUsername() + " is logged in successfylly",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        false).show();
                               goToSocialMediaActivity();

                            }

                        }
                    });

                }

                break;

            case R.id.btnGoSignup:

                goToSignupActivity();

                break;

        }

    }

    private void goToSocialMediaActivity() {

        Intent intent = new Intent(LoginActivity.this, TwitterUsers.class);
        startActivity(intent);

    }

    private void goToSignupActivity() {

        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);

    }
}