package com.attracttest.attractgroup.attracttest;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SuperheroFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;
    String name;
    String desc;
    String imgURL;
    SuperheroProfile sp;

    static SuperheroFragment newInstance(int page, SuperheroProfile profile, int id) {
        SuperheroFragment superheroFragment = new SuperheroFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("profiles", profile);
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt("id", id);
        superheroFragment.setArguments(arguments);
        return superheroFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        sp = (SuperheroProfile) getArguments().getSerializable("profiles");
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.superhero_fragment_profile, null);

        TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(sp.getName());

        TextView descView = (TextView) view.findViewById(R.id.description);
        descView.setText(sp.getDescription());


        TextView lolView = (TextView) view.findViewById(R.id.lol);
        lolView.setText("Page " + pageNumber);
        lolView.setBackgroundColor(backColor);

        return view;
    }
}