package edu.stanford.cs193a.sgalleg9.friendr;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.LineNumberReader;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView button = (ImageView) findViewById(R.id.image_button_main);

        Picasso.with(this)
                .load("http://www.martystepp.com/friendr/friends/heckles.jpg")
                .resize(500, 500)
                .into(button);
    }

    public void ViewUsersClicked(View view) {
        /*Ion.with(this)
                .load("http://www.martystepp.com/friendr/friends/list")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Santi", "onCompleted: " + result);
                    }
                });*/


        Intent intent = new Intent(this, ViewUsersActivity.class);
        startActivity(intent);

    }

    public void SwipeClicked(View view) {
        Intent intent = new Intent(this, SwipeProfilesActivity.class);
        startActivity(intent);
    }
}
