package com.sergey.taxiservice.ui.fragments.car.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.GeoAddress;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.databinding.FragmentCarFoundedBinding;
import com.sergey.taxiservice.models.order.LoadOrder;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.fragments.car.presenter.CarFoundedPresenter;
import com.sergey.taxiservice.ui.fragments.trip.completion.view.TripCompletionFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CarFoundedFragment extends BaseBindingToolbarFragment<CarFoundedPresenter, FragmentCarFoundedBinding> implements CarFoundedView {

    public static final String LOAD_ORDER_INFO = "order_info";
    public static final String LOAD_ORDER_TYPE = "load_order_info";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private LoadOrder order;
    private int orderType,intTxtCout;

    private ImageView adCout,reCout;
    private TextView txtCout;

    public static void open(Activity activity, LoadOrder order, int orderType) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        Bundle fragmentBundle = new Bundle();

        fragmentBundle.putParcelable(LOAD_ORDER_INFO, order);
        fragmentBundle.putInt(LOAD_ORDER_TYPE, orderType);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_CAR_FOUNDED);
        intent.putExtra(ToolbarActivity.EXTRA_BUNDLE_DATA, fragmentBundle);

        activity.startActivity(intent);
    }



    @Override
    public String getTitle() {
        return getString(R.string.car_founded);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_car_founded;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            orderType = getArguments().getInt(LOAD_ORDER_TYPE);
            order = getArguments().getParcelable(LOAD_ORDER_INFO);
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.roadStops.setAddressesOfStops(new String[]{});

        if(order != null) {
            binding.formMoney.setText(Integer.toString(order.getRide().getRideData().getOrderCost()));
            binding.carInfo.setText(order.getEvos().getOrderCarInfo());
            binding.time.setText(getSimpleDateString(order.getEvos().getRequiredTime()));
            binding.btnCancelOrder.setOnClickListener(v -> presenter.cancelOrder(order.getRide().getId(), orderType));

            ArrayList<GeoAddress> addresses = new ArrayList<>();
            addresses.add(order.getRide().getRideData().getRouteAddressFrom());
            addresses.add(order.getRide().getRideData().getRouteAddressTo());

            String[] stops = new String[addresses.size()];
            for(int i = 0; i < addresses.size(); i++) {
                stops[i] = addresses.get(i).getName();
            }

            binding.roadStops.setAddressesOfStops(stops);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.startCheckingDriver(order.getRide().getId(), orderType);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopCheckingDriver();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    @Override
    public void showSuccessMessage() {
        Toast.makeText(getActivity(), "Заказ отменен.", Toast.LENGTH_LONG).show();
//        MainActivity.openWithSection(getActivity(), MainActivity.MainActivitySection.Home, true);
    }

    @Override
    public void showFailureMessage() {
        Toast.makeText(getActivity(), "Произошла ошибка при отмене заказа.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void isArchived() {
        TripCompletionFragment.open(getActivity());
        getActivity().finish();
    }

    public static String getSimpleDateString(String apiFormattedDatedStr) {
        try {
            Date date = apiDateFormat.parse(apiFormattedDatedStr.replace("T", " "));
            return simpleDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
