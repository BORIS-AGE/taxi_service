package com.sergey.taxiservice.ui.fragments.trip.confirmation.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.constants.CarType;
import com.sergey.taxiservice.constants.DateType;
import com.sergey.taxiservice.constants.PayType;
import com.sergey.taxiservice.databinding.FragmentTripConfirmationBinding;
import com.sergey.taxiservice.manager.location.LocationManager;
import com.sergey.taxiservice.manager.location.listener.OnLocationChangeListener;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.dialogs.ChangeCarDialog;
import com.sergey.taxiservice.ui.dialogs.DatePickerFragment;
import com.sergey.taxiservice.ui.dialogs.InputAddressesDialog;
import com.sergey.taxiservice.ui.dialogs.OrderCarDialog;
import com.sergey.taxiservice.ui.dialogs.TimePickerFragment;
import com.sergey.taxiservice.ui.dialogs.TimePickerFragment.OnTimeChooseListener;
import com.sergey.taxiservice.ui.fragments.search.entire.view.AddressEntireFragment;
import com.sergey.taxiservice.ui.fragments.trip.services.view.AdditionalServicesFragment;
import com.sergey.taxiservice.ui.fragments.trip.confirmation.presenter.TripConfirmationPresenter;
import com.sergey.taxiservice.util.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TripConfirmationFragment extends BaseBindingToolbarFragment<TripConfirmationPresenter, FragmentTripConfirmationBinding>
        implements TripConfirmationView, OnLocationChangeListener, InputAddressesDialog.OnDialogActionListener, OrderCarDialog.OnEditedFinishedListener {

    public static final int SINGLE_RIDE = 0;
    public static final int RIDE_WITH_COMPANY = 1;
    public static final String TRIP_TYPE = "trip_type";

    private Date date = new Date();
    private PayType payType = PayType.CACHE;
    private CarType currentCarType = CarType.SEDAN;
    private boolean[] additionalServices = new boolean[]{};
    private ArrayList<AddressModel> addresses = new ArrayList<>();

    private Location currentLocation;
    private OrderCarDialog orderCarDialog;
    private InputAddressesDialog inputAddressesDialog;

    private FrameLayout addCout;
    private ImageView adCout,reCout;
    private TextView txtCout;
    private LinearLayout linerCout;
    private int intTxtCout=0;
    private TextView carTitle;


    @Inject
    LocationManager locationManager;

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_order);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_trip_confirmation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.roadStops.setAddressesOfStops(new String[]{});
        adCout = view.findViewById(R.id.adCout);
        reCout = view.findViewById(R.id.reCout);
        txtCout = view.findViewById(R.id.txtCout);
        linerCout = view.findViewById(R.id.linerCout);
        addCout = view.findViewById(R.id.addCout);


        initPeopleChooser();
        initCarChooser();

        binding.btnAddPoint.setOnClickListener(v -> showInputAddressDialog());
        binding.addServices.setOnClickListener(v -> AdditionalServicesFragment.open(this));
        binding.btnMakeOrder.setOnClickListener(v -> presenter.createOrder(addresses, currentCarType,
                payType, additionalServices, date, getCurrentArguments().getInt(TRIP_TYPE), getPerson()));

        presenter.loadUserData();

        adCout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++intTxtCout;
                carCaut();
            }
        });

        reCout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --intTxtCout;
                carCaut();
            }
        });

    }

    public void carCaut(){
        if(intTxtCout<=0)
            intTxtCout = 0;
        if(intTxtCout>=8)
            intTxtCout = 8;



            if (intTxtCout <= 4) {
                linerCout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                binding.carTitle.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                linerCout.setBackgroundColor(Color.parseColor("#ED5353"));
                binding.carTitle.setTextColor(Color.parseColor("#ED5353"));
                binding.carImage.setBackground(getResources().getDrawable(R.drawable.ic_minibus));
                binding.carTitle.setText("Микроавтобус");
            }

        txtCout.setText(""+intTxtCout);
        }


    @Override
    public void onResume() {
        super.onResume();
        locationManager.setChangeListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(currentLocation == null) {
            currentLocation = location;
            presenter.searchCurrentAddress(currentLocation.getLatitude(), currentLocation.getLongitude());
        }
    }

    @Override
    public void setUserData(String phone, String name) {
        binding.etName.setText(name);
        binding.etPhoneNumber.setText(phone);
    }


    @Override
    public void onAddressFound(List<AddressModel> addressModels) {
        if(addresses.isEmpty()) {
            if(addressModels != null && !addressModels.isEmpty()) {
                addresses.add(addressModels.get(0));
            }

            showInputAddressDialog();
        }
    }

    @Override
    public void onCostChanged(String cost) {
        binding.formMoney.setText(cost);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100) {
            if(resultCode == Activity.RESULT_OK) {
                additionalServices = data.getBooleanArrayExtra(AdditionalServicesFragment.class.getSimpleName());
            }

        } else {
            if(resultCode == Activity.RESULT_OK) {
                AddressModel model = data.getParcelableExtra(AddressEntireFragment.class.getSimpleName());
                inputAddressesDialog.update(model);
            }
        }
    }

    @Override
    public void onInputFormSelected(AddressModel address) {
        AddressEntireFragment.openForResult(this, 1000, address);
    }

    @Override
    public void onTripEdited(ArrayList<AddressModel> addresses) {
        String[] stops = new String[this.addresses.size()];
        for(int i = 0; i < this.addresses.size(); i++) {
            stops[i] = this.addresses.get(i).getTitle();
        }

        binding.roadStops.setAddressesOfStops(stops);
        presenter.routeDataChanged(this.addresses);

        if(orderCarDialog == null) {
            orderCarDialog = OrderCarDialog.newInstance(currentCarType.ordinal(), payType.ordinal(), DateType.NOW.ordinal());
            orderCarDialog.setListener(this);
            orderCarDialog.show(getFragmentManager());
        }
    }

    @Override
    public void onEditedFinished(DateType dateType, CarType carType, PayType payType) {
        setCurrentCarType(carType);
    }

    @Override
    public void dateSelected(DateType dateType) {
        if(dateType == DateType.SELECT_DATE) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.setListener(chosenDate -> {
                this.date = chosenDate;
                showTimePickerDialog();
            });

            datePickerFragment.show(getFragmentManager());
        }
    }


            @SuppressLint("SetTextI18n")
    private void initPeopleChooser() {
        if(getCurrentArguments().getInt(TRIP_TYPE) == RIDE_WITH_COMPANY) {
                addCout.setVisibility(View.VISIBLE);
            binding.number.setText(getString(R.string.number_of_people) + " (" + 1 + ")");
            binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    binding.number.setText(getString(R.string.number_of_people) + " (" + (progress + 1) + ")");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });}
    }

    private void initCarChooser() {
        carCaut();
        setCurrentCarType(currentCarType);
        binding.carLayout.setOnClickListener(v -> {
            binding.carImage.setChecked(true);

            ChangeCarDialog dialog = ChangeCarDialog.newInstance(currentCarType.ordinal());
            dialog.setListener(this::setCurrentCarType);

            if(getFragmentManager() != null) {
                dialog.show(getFragmentManager(), ChangeCarDialog.class.getCanonicalName());
            }
        });
    }

    private void setCurrentCarType(CarType carType) {
        currentCarType = carType;
        binding.carImage.setBackground(getResources().getDrawable(currentCarType.getImage()));
        binding.carTitle.setText(currentCarType.getTitle());
    }

    private void showInputAddressDialog() {
        inputAddressesDialog = InputAddressesDialog.newInstance(addresses);
        inputAddressesDialog.setOnDialogActionListener(this);
        inputAddressesDialog.show(getFragmentManager());
    }

    private void showTimePickerDialog() {
        OnTimeChooseListener listener = new OnTimeChooseListener(date, chosenDate -> {
            binding.formDate.setText(TimeUtils.convertToTripOrderingType(chosenDate));
            this.date = chosenDate;
        });

        new TimePickerFragment(getContext(), listener).show();
    }

    private int getPerson() {
        String number = binding.number.getText().toString()
                .replace(getString(R.string.number_of_people) + " (", "")
                .replace(")", "");

        return Integer.parseInt(number);
    }
}
