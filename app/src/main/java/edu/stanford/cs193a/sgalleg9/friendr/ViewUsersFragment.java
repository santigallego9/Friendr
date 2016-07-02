package edu.stanford.cs193a.sgalleg9.friendr;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import stanford.androidlib.SimpleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUsersFragment extends Fragment {

    private static final String WEBSITE_HTTP = "http://www.martystepp.com/friendr/friends/";

    public ViewUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());

        final ArrayList<String> users = new ArrayList<>();

        Ion.with(getActivity())
                .load("http://www.martystepp.com/friendr/friends/list")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        String[] items = result.split("\n");
                        for (String item : items) {
                            users.add(item);
                        }

                        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.container);

                        for (int i = 0; i < users.size(); i++) {
                            createThumbnails(users.get(i), layout);
                        }
                    }
                });
    }

    public void createThumbnails(final String name, LinearLayout parent) {
        View thumbnails = getActivity().getLayoutInflater().inflate(R.layout.thumbnails, null);
        ImageView thumbnail = (ImageView) thumbnails.findViewById(R.id.user_thumbnail);
        TextView nameView = (TextView) thumbnails.findViewById(R.id.name_view);
        TextView tagView = (TextView) thumbnails.findViewById(R.id.tag_view);
        TextView bioView = (TextView) thumbnails.findViewById(R.id.bio_view);

        final String tag = tagGenerator(name);

        nameView.setText(name);
        tagView.setText(tag);
        bioView.setText(bioGenerator());

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if(isPortriat()) {
            Picasso.with(getActivity())
                    .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                    .resize(width / 6, width / 6)
                    .transform(new CircleTransform())
                    .into(thumbnail);
        } else {
            Picasso.with(getActivity())
                    .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                    .resize(height / 6, height / 6)
                    .transform(new CircleTransform())
                    .into(thumbnail);
        }

        Log.d("Santi", "createThumbnails: " + WEBSITE_HTTP + name.toLowerCase() + ".jpg");

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPortriat()) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("tag", tag);
                    startActivity(intent);
                } else {
                    ProfileFragment fragment = (ProfileFragment)
                            getActivity().getFragmentManager().findFragmentById(R.id.profile_fragment);

                    fragment.setProfileDetails(name);
                }
            }
        });

        parent.addView(thumbnails);
    }

    public String tagGenerator(String name) {
        String[] additions = {"Vancity", "9", "Awesome", "11", "_", "Champ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Arabica", "CoffeeLover", "Careless"};
        int size = additions.length;

        Random r = new Random();
        int i = r.nextInt(size);

        name = "@" + name + additions[i];

        return name;
    }

    public void updateProfileFragment() {
    }

    public String bioGenerator() {
        String bio_default = "This is the default bio. I am however going to write a bunch of stuff here so I can see";

        return bio_default;
    }

    public boolean isPortriat() {
        return getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;
    }

    public boolean isLandscape() {
        return getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
    }
}

