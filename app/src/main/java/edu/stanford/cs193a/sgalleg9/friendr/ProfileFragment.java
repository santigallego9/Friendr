package edu.stanford.cs193a.sgalleg9.friendr;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.PrintStream;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    //@BindView(R.id.user_view) ImageView userPic;
    private static final String WEBSITE_HTTP = "http://www.martystepp.com/friendr/friends/";
    @BindView(R.id.user_rating)
    RatingBar ratingBar;
    private String name;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        name = intent.getStringExtra("name");

        ImageView userPic = (ImageView) getActivity().findViewById(R.id.user_view);
        TextView nameView = (TextView) getActivity().findViewById(R.id.name);

        Log.d("Santi2", "onActivityCreated: " + name);

        if (isPortrait()) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            Picasso.with(getActivity())
                    .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                    .resize(width, width)
                    .transform(new CircleTransform())
                    .into(userPic);

            nameView.setText(name);
        } else {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            Picasso.with(getActivity())
                    .load(WEBSITE_HTTP + "chandler" + ".jpg")
                    .resize(height / 2, height / 2)
                    .transform(new CircleTransform())
                    .into(userPic);

            nameView.setText("Chandler");
        }


    }

    @OnClick(R.id.user_rating)
    public void Submit(String name) {
        double rating = getUserRating();

        // TODO: Use SQLite to save the rating of these users
        FeedReaderContract.FeedEntry.FeedReaderDbHelper mDbHelper = new FeedReaderContract.FeedEntry.FeedReaderDbHelper(getContext());


        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

    }

    public void setProfileDetails(String name) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        ImageView userPic = (ImageView) getActivity().findViewById(R.id.user_view);
        TextView nameView = (TextView) getActivity().findViewById(R.id.name);

        nameView.setText(name);

        Picasso.with(getActivity())
                .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                .resize(height / 2, height / 2)
                .transform(new CircleTransform())
                .into(userPic);

    }

    public void setUserRating(double rating) {
        ratingBar.setRating((float) rating);
    }

    public double getUserRating() {
        return (double) ratingBar.getRating();
    }

    public boolean isPortrait() {
        return getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;
    }

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
    }
}
