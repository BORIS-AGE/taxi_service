package com.sergey.taxiservice.ui.fragments.trip.companion.childs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.fragments.trip.companion.view.CompanionFragment;

public class CompanionHelpFragment extends Fragment implements View.OnClickListener {

    public static final String GENDER = "gender";

    private TextView manButton;
    private TextView womanButton;
    private TextView allButton;

    private CompanionFragment CompanionFragment = new CompanionFragment();

    private RelativeLayout animRelative;
    private ConstraintLayout animConstraint;

    private OnItemSelectedListener listener;

    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companion_help, null);
        manButton = view.findViewById(R.id.man);
        manButton.setOnClickListener(this);
        womanButton = view.findViewById(R.id.woman);
        womanButton.setOnClickListener(this);



        allButton = view.findViewById(R.id.all);
        allButton.setOnClickListener(this);

        if(getArguments() != null) {
            switch (getArguments().getInt(GENDER)) {
                case -1:
                    allButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    break;

                case 1:
                    womanButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    break;

                case 0:
                    manButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                    break;
            }
        } else allButton.setTextColor(getResources().getColor(R.color.textColorPrimary));

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        animConstraint = (ConstraintLayout)getActivity().findViewById(R.id.animConstraint);
        animRelative = (RelativeLayout)getActivity().findViewById(R.id.animRelative);
    }

    public void onStartRadar(){
        animConstraint.setVisibility(View.VISIBLE);
        animRelative.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.man:
                manButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                womanButton.setTextColor(Color.WHITE);
                allButton.setTextColor(Color.WHITE);
                onStartRadar();
                notifyClick(0);
                break;

            case R.id.woman:
                manButton.setTextColor(Color.WHITE);
                womanButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                allButton.setTextColor(Color.WHITE);
                onStartRadar();
                notifyClick(1);
                break;

            case R.id.all:
                manButton.setTextColor(Color.WHITE);
                womanButton.setTextColor(Color.WHITE);
                allButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
                onStartRadar();
                notifyClick(-1);
                break;
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }




    private void notifyClick(int type) {
        if(listener != null) {
            listener.onItemSelected(type);
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(int type);
    }
}
