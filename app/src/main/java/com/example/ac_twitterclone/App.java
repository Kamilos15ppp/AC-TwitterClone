package com.example.ac_twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("fRimPzKRY8YZo0LBjLl37JtTF9kir83tsQ2KL8Oh")
                // if defined
                .clientKey("ff0n2cUVZIiuaNpuOslAr9EpCG4U96EZEFKlwFV9")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
