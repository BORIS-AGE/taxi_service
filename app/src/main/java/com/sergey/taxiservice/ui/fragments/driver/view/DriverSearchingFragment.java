package com.sergey.taxiservice.ui.fragments.driver.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sergey.taxiservice.R;
import com.sergey.taxiservice.constants.CarType;
import com.sergey.taxiservice.constants.PayType;
import com.sergey.taxiservice.models.GeoAddress;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.databinding.FragmentDriverSearchingBinding;
import com.sergey.taxiservice.models.order.LoadOrder;
import com.sergey.taxiservice.models.ride.Ride;
import com.sergey.taxiservice.ui.activities.MainActivity;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.fragments.car.view.CarFoundedFragment;
import com.sergey.taxiservice.ui.fragments.driver.presenter.DriverSearchingPresenter;
import com.sergey.taxiservice.ui.fragments.map.MapFragment;

import java.util.ArrayList;

public class DriverSearchingFragment extends BaseBindingToolbarFragment<DriverSearchingPresenter, FragmentDriverSearchingBinding> implements DriverSearchingView {

    public static final String ORDER_INFO = "order_info";
    public static final String ORDER_TYPE = "order_type";

    private Ride ride;
    private int orderType;

    public static void open(Activity activity, Ride ride, int orderType) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        Bundle fragmentBundle = new Bundle();

        fragmentBundle.putInt(ORDER_TYPE, orderType);
        fragmentBundle.putParcelable(DriverSearchingFragment.ORDER_INFO, ride);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_CAR_SEARCH);
        intent.putExtra(ToolbarActivity.EXTRA_BUNDLE_DATA, fragmentBundle);

        activity.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_trip_execution);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_driver_searching;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            orderType = getArguments().getInt(ORDER_TYPE);
            ride = getArguments().getParcelable(ORDER_INFO);
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.roadStops.setAddressesOfStops(new String[]{});

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.setPaintLocation(false);

//        ride = new Gson().fromJson("{\"client_id\":24,\"driver_state_id\":\"0\",\"evos_id\":\"ca4003653e624e50a23fc9a2b14117ec\",\"client_name\":\"Yaroslav\",\"client_phone_number\":380933932812,\"ride_data\":{\"dispatching_order_uid\":\"ca4003653e624e50a23fc9a2b14117ec\",\"discount_trip\":false,\"find_car_timeout\":120,\"find_car_delay\":0,\"order_cost\":\"94\",\"currency\":\" \\u0433\\u0440\\u043d.\",\"route_address_from\":{\"name\":\"\\u041f\\u0420\\u041e\\u0412\\u041e\\u0414\\u041d\\u0418\\u0426\\u041a\\u0410\\u042f \\u0423\\u041b., 17\",\"number\":null,\"lat\":50.4312286484564,\"lng\":30.4980916780392},\"route_address_to\":{\"name\":\"\\u041f\\u0420\\u041e\\u0415\\u041a\\u0422\\u041d\\u0410\\u042f \\u0423\\u041b., 7\",\"number\":null,\"lat\":50.4035504877809,\"lng\":30.4225809560799}},\"updated_at\":\"2018-10-09 18:48:44\",\"created_at\":\"2018-10-09 18:48:44\",\"id\":1}", Ride.class);

        if(ride != null) {
            binding.tvCost.setText(Integer.toString(ride.getRideData().getOrderCost()));
            binding.btnCancelOrder.setOnClickListener(v -> presenter.cancelOrder(ride.getId(), orderType));
            binding.car.setText(getString(CarType.values()[ride.getCar_type()].getTitle()));
            binding.pay.setText(getString(PayType.values()[ride.getPay_type()].getTitle()));

            ArrayList<GeoAddress> addresses = new ArrayList<>();
            addresses.add(ride.getRideData().getRouteAddressFrom());
            addresses.add(ride.getRideData().getRouteAddressTo());

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
        presenter.startCheckingDriver(ride.getId(), orderType);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopCheckingDriver();
    }

    @Override
    public void driverFounded(LoadOrder loadOrder) {
        CarFoundedFragment.open(getActivity(), loadOrder, orderType);
        getActivity().finish();
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(getActivity(), "Заказ отменен.", Toast.LENGTH_LONG).show();
//        MainActivity.openWithSection(getActivity(), MainActivity.MainActivitySection.Home, true);
    }

    @Override
    public void showFailureMessage() {
        Toast.makeText(getActivity(), "Произошла ошибка при отмене заказа.", Toast.LENGTH_LONG).show();
    }
}
