package com.sergey.taxiservice.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseActivity;
import com.sergey.taxiservice.ui.fragments.car.view.CarFoundedFragment;
import com.sergey.taxiservice.ui.fragments.share.action.view.ActionFragment;
import com.sergey.taxiservice.ui.fragments.share.approve.view.ApproveFragment;
import com.sergey.taxiservice.ui.fragments.share.chat.view.ChatFragment;
import com.sergey.taxiservice.ui.fragments.details.view.CompanionDetailsFragment;
import com.sergey.taxiservice.ui.fragments.driver.view.DriverSearchingFragment;
import com.sergey.taxiservice.ui.fragments.search.entire.view.AddressEntireFragment;
import com.sergey.taxiservice.ui.fragments.search.input.view.StreetSearchFragment;
import com.sergey.taxiservice.ui.fragments.trip.invite.view.InvitePersonFragment;
import com.sergey.taxiservice.ui.fragments.trip.services.view.AdditionalServicesFragment;
import com.sergey.taxiservice.ui.fragments.trip.completion.view.TripCompletionFragment;
import com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment;

import static com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment.USER_ID;

public class ToolbarActivity extends BaseActivity {

    public static String EXTRA_TITLE = "title";
    public static String EXTRA_OPEN_WITH = "open_with";
    public static String EXTRA_BUNDLE_DATA = "bundle_data";

    public static String OPEN_WITH_CAR_SEARCH = "car_search";
    public static String OPEN_WITH_CAR_FOUNDED = "car_founded";
    public static String OPEN_WITH_STREET_SEARCH = "street_search";
    public static String OPEN_WITH_ADDRESS_ENTIRE = "address_entire";
    public static String OPEN_WITH_COMPANION_DETAILS = "companion_details";
    public static String OPEN_WITH_CHAT = "chat";
    public static String OPEN_WITH_ADDITIONAL_SERVICES = "additional_services";
    public static String OPEN_WITH_COMPLETION = "completion";
    public static String OPEN_WITH_USER_INFO = "user_info";
    public static String OPEN_WITH_INVITE_PERSON = "invite_person";
    public static String OPEN_WITH_SHARE_ACTION = "share_action";
    public static String OPEN_WITH_APPROVE_ACTION = "approve_action";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Intent intent = getIntent();
        setupActionBar();

        if(intent.hasExtra(EXTRA_OPEN_WITH)) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_STREET_SEARCH)) {
                if(!(currentFragment instanceof StreetSearchFragment)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_TITLE, intent.getStringExtra(EXTRA_TITLE));
                    intent.removeExtra(EXTRA_TITLE);

                    Fragment fragment = new StreetSearchFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_ADDRESS_ENTIRE)) {
                if(!(currentFragment instanceof AddressEntireFragment)) {
                    Fragment fragment = new AddressEntireFragment();
                    fragment.setArguments(intent.getBundleExtra(EXTRA_BUNDLE_DATA));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_CAR_SEARCH)) {
                if(!(currentFragment instanceof DriverSearchingFragment)) {
                    Fragment fragment = new DriverSearchingFragment();
                    fragment.setArguments(intent.getBundleExtra(EXTRA_BUNDLE_DATA));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_CAR_FOUNDED)) {
                if(!(currentFragment instanceof CarFoundedFragment)) {
                    Fragment fragment = new CarFoundedFragment();
                    fragment.setArguments(intent.getBundleExtra(EXTRA_BUNDLE_DATA));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            }  else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_COMPANION_DETAILS)) {
                if(!(currentFragment instanceof CompanionDetailsFragment)) {
                    Fragment fragment = new CompanionDetailsFragment();
                    fragment.setArguments(intent.getBundleExtra(EXTRA_BUNDLE_DATA));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_CHAT)) {
                if(!(currentFragment instanceof ChatFragment)) {
                    Fragment fragment = new ChatFragment();
                    fragment.setArguments(intent.getBundleExtra(EXTRA_BUNDLE_DATA));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_ADDITIONAL_SERVICES)) {
                if(!(currentFragment instanceof AdditionalServicesFragment)) {
                    Fragment fragment = new AdditionalServicesFragment();
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_COMPLETION)) {
                if(!(currentFragment instanceof TripCompletionFragment)) {
                    Fragment fragment = new TripCompletionFragment();
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_USER_INFO)) {
                if(!(currentFragment instanceof UserFullInfoFragment)) {
                    UserFullInfoFragment fragment = new UserFullInfoFragment();
                    fragment.addToArguments(USER_ID, intent.getIntExtra(USER_ID, 0));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_INVITE_PERSON)) {
                if(!(currentFragment instanceof InvitePersonFragment)) {
                    InvitePersonFragment fragment = new InvitePersonFragment();
                    fragment.addToArguments(InvitePersonFragment.USER_ID, intent.getIntExtra(InvitePersonFragment.USER_ID, 0));
                    fragment.addToArguments(InvitePersonFragment.INVITE_ID, intent.getIntExtra(InvitePersonFragment.INVITE_ID, 0));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_SHARE_ACTION)) {
                if(!(currentFragment instanceof ActionFragment)) {
                    ActionFragment fragment = new ActionFragment();
                    fragment.addToArguments(ActionFragment.USER_ID, intent.getIntExtra(ActionFragment.USER_ID, 0));
                    fragment.addToArguments(ActionFragment.ACTION_TYPE, intent.getIntExtra(ActionFragment.ACTION_TYPE, 0));
                    replaceFragment(R.id.fragment_container, fragment);
                }

            } else if(intent.getStringExtra(EXTRA_OPEN_WITH).equals(OPEN_WITH_APPROVE_ACTION)) {
                if(!(currentFragment instanceof ApproveFragment)) {
                    ApproveFragment fragment = new ApproveFragment();
                    replaceFragment(R.id.fragment_container, fragment);
                }
            }

            intent.removeExtra(EXTRA_OPEN_WITH);

        } else if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            finish();
        }

        if(intent.hasExtra(EXTRA_TITLE) && getSupportActionBar() != null)
            getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));
    }

    private void setupActionBar(){
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
