package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweet extends AppCompatActivity implements View.OnClickListener{

    private EditText edtSendTweet;
    private Button btnSendTweet, btnViewTweets;
    private ListView viewTweetsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        edtSendTweet = findViewById(R.id.edtSendTweet);
        btnSendTweet = findViewById(R.id.btnSendTweet);
        btnViewTweets = findViewById(R.id.btnViewTweets);
        viewTweetsListView = findViewById(R.id.listView);
        viewTweetsListView.setVisibility(View.GONE);

        btnSendTweet.setOnClickListener(this);
        btnViewTweets.setOnClickListener(this);

    }

    public void sendTweet() {

        ParseObject object = new ParseObject("MyTweet");
        object.put("tweet", edtSendTweet.getText().toString());
        object.put("user", ParseUser.getCurrentUser().getUsername());
        Context context = this;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    FancyToast.makeText(SendTweet.this,
                            ParseUser.getCurrentUser().getUsername() + "'s tweet" + "(" + edtSendTweet.getText().toString() + ")" + " is saved!",
                            Toast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false).show();

                } else {

                    FancyToast.makeText(SendTweet.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false).show();

                }

                progressDialog.dismiss();

            }
        });

    }

    public void viewTweets() {

        final ArrayList<HashMap<String, String>> tweetList = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(SendTweet.this,
                tweetList, android.R.layout.simple_list_item_2,
                new String[]{"tweetUsername", "tweetValue"},
                new int[]{android.R.id.text1, android.R.id.text2});
        try {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("MyTweet");
            query.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanOf"));
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (objects.size() > 0 && e == null) {

                        for (ParseObject tweetObject : objects) {

                            HashMap<String, String> userTweet = new HashMap<>();
                            userTweet.put("tweetUsername", tweetObject.getString("user"));
                            userTweet.put("tweetValue", tweetObject.getString("tweet"));
                            tweetList.add(userTweet);

                        }

                        viewTweetsListView.setAdapter(adapter);
                        viewTweetsListView.setVisibility(View.VISIBLE);

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSendTweet:

                sendTweet();

                break;

            case R.id.btnViewTweets:

                viewTweets();

                break;

        }

    }
}