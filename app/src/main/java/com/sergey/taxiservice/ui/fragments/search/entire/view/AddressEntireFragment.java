package com.sergey.taxiservice.ui.fragments.search.entire.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.databinding.FragmentEntireAddressBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.fragments.search.entire.presenter.AddressEntirePresenter;
import com.sergey.taxiservice.ui.fragments.search.input.view.StreetSearchFragment;

import static com.sergey.taxiservice.ui.activities.ToolbarActivity.EXTRA_BUNDLE_DATA;
import static com.sergey.taxiservice.ui.activities.ToolbarActivity.EXTRA_OPEN_WITH;
import static com.sergey.taxiservice.ui.activities.ToolbarActivity.OPEN_WITH_ADDRESS_ENTIRE;

public class AddressEntireFragment extends BaseBindingFragment<AddressEntirePresenter, FragmentEntireAddressBinding> implements AddressEntireView {

    private static final int REQUEST_CODE_STREET = 1;
    private static final String MODEL = "model";

    private String title = "";
    private String building = "";
    private String entrance = "";

    public static void openForResult(Fragment fragment, int requestCode, AddressModel addressModel){
        Intent intent = new Intent(fragment.getActivity(), ToolbarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MODEL, addressModel);

        intent.putExtra(EXTRA_OPEN_WITH, OPEN_WITH_ADDRESS_ENTIRE);
        intent.putExtra(EXTRA_BUNDLE_DATA, bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_entire_address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null) {
            AddressModel model = getArguments().getParcelable(MODEL);

            if (model != null) {
                if(model.getData() != null) {
                    title = model.getData();
                }

                if(model.getAdditionalData() != null) {
                    String data = model.getAdditionalData();
                    if(!data.contains(", " + getString(R.string.entrance) + " ")) {
                        building = data;
                    } else {
                        String[] numbers = data.split(", " + getString(R.string.entrance) + " ");
                        if(numbers.length > 1) {
                            building = numbers[0];
                            entrance = numbers[1];
                        }
                    }
                }
            }

        }

        binding.etStreet.setInputType(InputType.TYPE_NULL);
        binding.etStreet.setOnClickListener(v -> StreetSearchFragment.openForResult(this, title, REQUEST_CODE_STREET));
        binding.etStreet.setOnFocusChangeListener((v, focus) -> {
            if(focus && title.isEmpty()){
                StreetSearchFragment.openForResult(this, title, REQUEST_CODE_STREET);
            }
        });

        binding.etEntrance.setText(entrance);
        binding.etHouseNumber.setText(building);
        binding.etStreet.setText(title);

        Activity activity = getActivity();
        if(activity != null) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle("Поиск улицы");
            }
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_check, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_check:
                onCheckAction();
                return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_STREET: {
                if(resultCode == Activity.RESULT_OK) {
                    AddressModel geoModel = data.getParcelableExtra(StreetSearchFragment.class.getSimpleName());

                    if(geoModel.getAdditionalData() == null || geoModel.getAdditionalData().isEmpty())
                        presenter.processGeoObject(geoModel.getData());
                    else binding.etStreet.setText(geoModel.getData());

                } else if(TextUtils.isEmpty(binding.etStreet.getText()) && getActivity() != null) {
                    getActivity().finish();
                }
                break;
            }
        }
    }

    @Override
    public void onAddressReady(AddressModel addressModel) {
        Activity activity = getActivity();
        if(activity != null) {
            Intent intent = new Intent();
            intent.putExtra(AddressEntireFragment.class.getSimpleName(), addressModel);

            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }

    private void onCheckAction(){
        boolean success = true;
        if(TextUtils.isEmpty(binding.etStreet.getText())){
            success = false;
            binding.etStreet.setError(getString(R.string.field_error_cannot_be_empty));
        }
        if(TextUtils.isEmpty(binding.etHouseNumber.getText())){
            success = false;
            binding.etHouseNumber.setError(getString(R.string.field_error_cannot_be_empty));
        }
        if(success){
            presenter.processGeoStreet(binding.etStreet.getText().toString(),
                    binding.etHouseNumber.getText().toString(),
                    TextUtils.isEmpty(binding.etEntrance.getText()) ? "" : getString(R.string.entrance) + " " + binding.etEntrance.getText().toString());
        }
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}
}
