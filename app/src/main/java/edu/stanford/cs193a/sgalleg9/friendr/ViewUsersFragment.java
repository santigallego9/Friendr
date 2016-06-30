package edu.stanford.cs193a.sgalleg9.friendr;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


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
                        Log.d("Santi", "onCompleted: " + users.size());

                        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.container);

                        for (int i = 0; i < users.size(); i++) {
                            createThumbnails(users.get(i), layout);
                        }
                    }
                });

        Log.d("Santi", "onActivityCreated: " + users.size());


    }

    public void createThumbnails(final String name, LinearLayout parent) {
        View thumbnails = getActivity().getLayoutInflater().inflate(R.layout.thumbnails, null);
        ImageView thumbnail = (ImageView) thumbnails.findViewById(R.id.user_thumbnail);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Picasso.with(getActivity())
                .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                .resize(width / 2, width / 2)
                .into(thumbnail);

        Log.d("Santi", "createThumbnails: " + WEBSITE_HTTP + name.toLowerCase() + ".jpg");

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        parent.addView(thumbnails);
    }
}

