package com.example.ac_twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailSignup, edtUsernameSignup, edtPasswordSignup;
    private Button btnSignup, btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtEmailSignup = findViewById(R.id.edtEmailSignup);
        edtUsernameSignup = findViewById(R.id.edtUsernameSignup);
        edtPasswordSignup = findViewById(R.id.edtPasswordSignup);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);


        btnSignup.setOnClickListener(this);
        btnGoLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

            //ParseUser.getCurrentUser().logOut();
            goToSocialMediaActivity();

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSignup:

                if (edtEmailSignup.getText().toString().equals("") ||
                        edtUsernameSignup.getText().toString().equals("") ||
                        edtPasswordSignup.getText().toString().equals("")) {

                    FancyToast.makeText(SignupActivity.this,
                            "Email, Username and Password is required!",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                            false).show();

                } else {

                    final ParseUser user = new ParseUser();
                    user.setEmail(edtEmailSignup.getText().toString());
                    user.setUsername(edtUsernameSignup.getText().toString());
                    user.setPassword(edtPasswordSignup.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                FancyToast.makeText(SignupActivity.this,
                                        user.getUsername() + " is signed up",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS,
                                        false).show();
                                goToSocialMediaActivity();

                            } else {

                                FancyToast.makeText(SignupActivity.this,
                                        "There was an error: " + e.getMessage(),
                                        Toast.LENGTH_LONG, FancyToast.ERROR,
                                        false).show();

                            }

                        }
                    });

                }

                break;

            case R.id.btnGoLogin:

                goToLoginActivity();

                break;

        }

    }

    private void goToSocialMediaActivity() {

        Intent intent = new Intent(SignupActivity.this, TwitterUsers.class);
        startActivity(intent);

    }

    public void goToLoginActivity() {

        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);

    }

}