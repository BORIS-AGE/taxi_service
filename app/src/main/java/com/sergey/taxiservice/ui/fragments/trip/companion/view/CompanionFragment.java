package com.sergey.taxiservice.ui.fragments.trip.companion.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FargmentCompanionBinding;
import com.sergey.taxiservice.models.companion.Companion;
import com.sergey.taxiservice.models.companion.CompanionWithInfo;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.trip.companion.childs.CompanionHelpFragment;
import com.sergey.taxiservice.ui.fragments.trip.companion.presenter.CompanionPresenter;
import com.sergey.taxiservice.ui.fragments.details.view.CompanionDetailsFragment;
import com.sergey.taxiservice.ui.fragments.map.MapFragment;
import com.sergey.taxiservice.ui.fragments.user.info.brief.view.UserInfoFragment;

import java.util.List;
import java.util.Random;

import static com.sergey.taxiservice.ui.fragments.trip.companion.childs.CompanionHelpFragment.GENDER;
import static com.sergey.taxiservice.ui.fragments.user.info.brief.view.UserInfoFragment.MY_COMPANION_ID;
import static com.sergey.taxiservice.ui.fragments.user.info.brief.view.UserInfoFragment.USER_COMPANION_ID;
import static com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment.USER_ID;

public class CompanionFragment extends BaseBindingToolbarFragment<CompanionPresenter, FargmentCompanionBinding>
        implements CompanionView, MapFragment.OnMarkerClickListener, CompanionHelpFragment.OnItemSelectedListener {

    public static final String COMPANION_INFO = "companion_info";

    private Companion companion;
    private MapFragment mapFragment;
    private CompanionHelpFragment helpFragment;
    private Bundle bundle = new Bundle();
    private ImageView animationTarget,animationGreen,animationGreen2,animationGreen3;
    private Animation anim,anim2;
    final Random random = new Random();
    private CountDownTimer countDownTimer;
    private RelativeLayout RelativeLayaot;


    @Override
    public String getTitle() {
        return getString(R.string.search_companion);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fargment_companion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null)
            companion = getArguments().getParcelable(COMPANION_INFO);

    }

    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_companion, null);
        //Обекты
        animationTarget = view.findViewById(R.id.radar);
        animationGreen = view.findViewById(R.id.anims);
        animationGreen2 = view.findViewById(R.id.anims2);
        animationGreen3 = view.findViewById(R.id.anims3);
        RelativeLayaot = view.findViewById(R.id.RelativeLayaot);

        //Анимация радар
        anim = AnimationUtils.loadAnimation(getActivity(),R.anim.anim);
        anim2 = AnimationUtils.loadAnimation(getActivity(),R.anim.anim2);
        animationGreen.startAnimation(anim);
        animationGreen2.startAnimation(anim);
        animationGreen3.startAnimation(anim2);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_around_center_point);
        animationTarget.startAnimation(animation);
        countDownTimer = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(1==millisUntilFinished/1000){
                    animationGreen.setY(RelativeLayaot.getHeight()/2+random.nextInt(200));
                    animationGreen.setY(RelativeLayaot.getHeight()/2+random.nextInt(200));}
                if(4==millisUntilFinished/1000){
                    animationGreen2.setY(RelativeLayaot.getHeight()/2-random.nextInt(200));
                    animationGreen2.setX(RelativeLayaot.getWidth()/2-random.nextInt(200));}
                if(7==millisUntilFinished/1000){
                    animationGreen3.setX(RelativeLayaot.getWidth()/2+random.nextInt(200));
                    animationGreen3.setX(RelativeLayaot.getWidth()/2-random.nextInt(200));}
            }
            @Override
            public void onFinish() {
                start();
            }};
        countDownTimer.start();

        return view;
    }


    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle.putInt(GENDER, -1);

        helpFragment = new CompanionHelpFragment();
        helpFragment.setArguments(bundle);
        helpFragment.setListener(this);
        showFragment(helpFragment);

        if(companion != null)
            presenter.loadCompanions(companion.getId());

        mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.setOnMarkerClickListener(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.setOnMarkerClickListener(null);
        mapFragment.setUserMarkers(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_travel, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_show_travel:
                CompanionDetailsFragment.open(getActivity(), companion.getId(), true);
                return true;
        }

        return false;
    }

    @Override
    public void onMarkerClicked(CompanionWithInfo info) {
        if(info == null) {
            helpFragment.setArguments(bundle);
            showFragment(helpFragment);
        } else  {
            UserInfoFragment userInfoFragment = new UserInfoFragment();
            userInfoFragment.addToArguments(USER_ID, info.getClient().getId());
            userInfoFragment.addToArguments(MY_COMPANION_ID, companion.getId());
            userInfoFragment.addToArguments(USER_COMPANION_ID, info.getId());
            showFragment(userInfoFragment);
        }
    }

    @Override
    public void setListOfUsers(List<CompanionWithInfo> users) {
        mapFragment.setUserMarkers(users);
    }

    private void showFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onItemSelected(int type) {
        if(mapFragment != null) {
            bundle.putInt(GENDER, type);
            mapFragment.filterMarkers(type);
        }
    }
}
