package com.sergey.taxiservice.ui.fragments.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentCompanionDetailsBinding;
import com.sergey.taxiservice.models.client.Client;
import com.sergey.taxiservice.models.companion.CompanionInfo;
import com.sergey.taxiservice.models.companion.RideGeneralInfo;
import com.sergey.taxiservice.models.companion.Route;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.models.order.CreateOrder;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.share.chat.view.ChatFragment;
import com.sergey.taxiservice.ui.fragments.details.presenter.CompanionDetailsPresenter;
import com.sergey.taxiservice.ui.fragments.driver.view.DriverSearchingFragment;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class CompanionDetailsFragment extends BaseBindingToolbarFragment<CompanionDetailsPresenter, FragmentCompanionDetailsBinding> implements CompanionDetailsView {

    private final static String COMPANION_ID = "companion_id";
    private final static String IS_HOST = "is_host";

    public static void open(Activity activity, int id, boolean isHost) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        Bundle fragmentBundle = new Bundle();

        fragmentBundle.putInt(COMPANION_ID, id);
        fragmentBundle.putBoolean(IS_HOST, isHost);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_COMPANION_DETAILS);
        intent.putExtra(ToolbarActivity.EXTRA_BUNDLE_DATA, fragmentBundle);

        activity.startActivity(intent);
    }

    private boolean isHost = false;
    private int companionId = 0;

    private String name = null;

    @Override
    public String getTitle() {
        return getString(R.string.travel_info);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_companion_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        name.isEmpty();

        if(getArguments() != null) {
            companionId = getArguments().getInt(COMPANION_ID);
            isHost = getArguments().getBoolean(IS_HOST);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnMakeOrder.setVisibility(isHost ? View.VISIBLE : View.GONE);
        presenter.loadCompanionInfo(companionId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                ChatFragment.open(getActivity(), companionId);
                return true;
        }

        return false;
    }

    @Override
    public void setInfo(RideGeneralInfo info) {
        binding.tvCost.setText(String.valueOf(info.getRidePrice()));
        binding.tvTime.setText(getSimpleDateString(info.getRideDuration()));
        binding.btnMakeOrder.setOnClickListener(view -> presenter.findCar(companionId));

        for (Route route : info.getRoutes()) {
            View start = getLayoutInflater().inflate(R.layout.item_route_point_from, binding.rotes, false);
            ((TextView) start.findViewById(R.id.text1)).setText(route.getFromAddress());

            View finish = getLayoutInflater().inflate(R.layout.item_route_point_to, binding.rotes, false);
            ((TextView) finish.findViewById(R.id.text1)).setText(route.getTargetAddress());

            binding.rotes.addView(start);
            binding.rotes.addView(finish);
        }

        // add owner to the top of companions list
        addOwnerToCompanionsList(info.getClient(), info.getPersons());

        for (CompanionInfo cInfo : info.getCompanions()) {
            Client client = cInfo.getRequest().getClient();
            View user = getLayoutInflater().inflate(R.layout.item_user, binding.followers, false);

            if(client != null) {
                ImageView avatar = user.findViewById(R.id.avatar);
                if(client.getAvatar() != null && !client.getAvatar().isEmpty()) {
                    Picasso.get().load(client.getAvatar()).into(avatar);
                } else Picasso.get().load(R.drawable.default_avatar).into(avatar);

                StringBuilder builder = new StringBuilder();
                builder.append(client.getFirstName() != null ? client.getFirstName() : "")
                        .append(" ")
                        .append(client.getLastName() != null ? client.getLastName() : "")
                        .append(" ")
                        .append(client.getPatronymic() != null ? client.getPatronymic() : "")
                        .append(" (")
                        .append(cInfo.getRequest().getPersons())
                        .append(" чел.)");

                ((TextView) user.findViewById(R.id.fullName)).setText(builder.toString());

                ImageView accept = user.findViewById(R.id.accept);
                ImageView delete = user.findViewById(R.id.delete);

                if(isHost) {
                    accept.setOnClickListener(view -> presenter.acceptRide(cInfo.getRequest_id(), cInfo.getClient_id()));
                    delete.setOnClickListener(view -> presenter.deleteRide(cInfo.getRequest_id(), cInfo.getClient_id()));

                    switch (cInfo.getState()) {
                        case 1:
                            disableButton(accept);
                            hideButton(delete);
                            break;

                        case 2:
                            disableButton(delete);
                            hideButton(accept);
                            break;

                        default:
                            break;
                    }

                } else {

                    switch (cInfo.getState()) {
                        case 1:
                            disableButton(accept);
                            hideButton(delete);
                            break;

                        case 2:
                            disableButton(delete);
                            hideButton(accept);
                            break;

                        default:
                            disableButton(accept);
                            disableButton(delete);
                            break;
                    }
                }

                binding.followers.addView(user);
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reloadData() {
        binding.rotes.removeAllViews();
        binding.followers.removeAllViews();
        presenter.loadCompanionInfo(companionId);
    }

    @Override
    public void showDriverSearchingScreen(CreateOrder order) {
        DriverSearchingFragment.open(getActivity(), order.getRide(), OrderType.COMPANION);
        getActivity().finish();
    }

    private void addOwnerToCompanionsList(Client client, int persons) {
        View user = getLayoutInflater().inflate(R.layout.item_user, binding.followers, false);

        if(client != null) {
            ImageView avatar = user.findViewById(R.id.avatar);
            if(client.getAvatar() != null && !client.getAvatar().isEmpty()) {
                Picasso.get().load(client.getAvatar()).into(avatar);
            } else Picasso.get().load(R.drawable.default_avatar).into(avatar);

            StringBuilder builder = new StringBuilder();
            builder.append(client.getFirstName() != null ? client.getFirstName() : "")
                    .append(" ")
                    .append(client.getLastName() != null ? client.getLastName() : "")
                    .append(" ")
                    .append(client.getPatronymic() != null ? client.getPatronymic() : "")
                    .append("(Владелец, ")
                    .append(persons)
                    .append(" чел.)");

            ((TextView) user.findViewById(R.id.fullName)).setText(builder.toString());

            user.findViewById(R.id.accept).setVisibility(View.INVISIBLE);
            user.findViewById(R.id.delete).setVisibility(View.INVISIBLE);

            binding.followers.addView(user);
        }
    }

    public static String getSimpleDateString(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;

        if(hours > 0) {
            return String.format(Locale.getDefault(), "%02d час %02d минуты", hours, minutes);
        } else return String.format(Locale.getDefault(), "%02d минуты", minutes);
    }

    private void disableButton(ImageView imageView) {
        imageView.setAlpha(.5f);
        imageView.setClickable(false);
        imageView.setEnabled(false);
    }

    private void hideButton(ImageView imageView) {
        imageView.setVisibility(View.GONE);
    }
}
