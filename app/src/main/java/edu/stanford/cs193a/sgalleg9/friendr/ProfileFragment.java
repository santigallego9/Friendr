package edu.stanford.cs193a.sgalleg9.friendr;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    //@BindView(R.id.user_view) ImageView userPic;
    private static final String WEBSITE_HTTP = "http://www.martystepp.com/friendr/friends/";

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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra("name");

        ImageView userPic = (ImageView) getActivity().findViewById(R.id.user_view);
        TextView nameView = (TextView) getActivity().findViewById(R.id.name);

        nameView.setText(name);

        Log.d("Santi2", "onActivityCreated: " + name);

        Picasso.with(getActivity())
                .load(WEBSITE_HTTP + name.toLowerCase() + ".jpg")
                .resize(width,width)
                .into(userPic);
    }
}
