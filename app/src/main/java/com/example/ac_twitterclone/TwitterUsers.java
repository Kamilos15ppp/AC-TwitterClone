package com.example.ac_twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView userListview;
    private ArrayList<String> tUsers;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);

        FancyToast.makeText(TwitterUsers.this,
                "Welcome " + ParseUser.getCurrentUser().getUsername(),
                Toast.LENGTH_SHORT, FancyToast.INFO,
                false).show();

        userListview = findViewById(R.id.userListview);
        tUsers = new ArrayList();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, tUsers);
        userListview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        userListview.setOnItemClickListener(this);

        try {

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseUser user : objects) {

                                tUsers.add(user.getUsername());

                            }

                            userListview.setAdapter(arrayAdapter);

                            for (String user : tUsers) {

                                if (ParseUser.getCurrentUser().getList("fanOf") != null) {

                                    if (ParseUser.getCurrentUser().getList("fanOf").contains(user)) {

                                        userListview.setItemChecked(tUsers.indexOf(user), true);

                                    }
                                }
                            }
                        }

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:

                ParseUser.getCurrentUser().logOut();
                finish();

                break;

            case R.id.send:

                goToSignupActivity();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()) {

            FancyToast.makeText(TwitterUsers.this,
                    tUsers.get(position) + " is now followed!",
                    Toast.LENGTH_SHORT, FancyToast.INFO,
                    false).show();

            ParseUser.getCurrentUser().add("fanOf", tUsers.get(position));

        } else {

            FancyToast.makeText(TwitterUsers.this,
                    tUsers.get(position) + " is now unfollowed!",
                    Toast.LENGTH_SHORT, FancyToast.INFO,
                    false).show();

            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf", currentUserFanOfList);

        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    FancyToast.makeText(TwitterUsers.this,
                            "Saved",
                            Toast.LENGTH_SHORT, FancyToast.INFO,
                            false).show();

                }

            }
        });

    }

    private void goToSignupActivity() {

        Intent intent = new Intent(TwitterUsers.this, SendTweet.class);
        startActivity(intent);

    }
}