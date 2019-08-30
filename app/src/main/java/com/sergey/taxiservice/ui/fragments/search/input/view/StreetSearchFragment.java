package com.sergey.taxiservice.ui.fragments.search.input.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.databinding.FragmentStreetSearchBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.fragments.search.input.adapter.StreetSearchAdapter;
import com.sergey.taxiservice.ui.fragments.search.input.presenter.StreetSearchPresenter;
import com.sergey.taxiservice.ui.fragments.search.input.adapter.StreetSearchClickListener;

import java.util.List;

public class StreetSearchFragment extends BaseBindingFragment<StreetSearchPresenter, FragmentStreetSearchBinding> implements StreetSearchView, StreetSearchClickListener {

    public static void openForResult(Fragment fragment, String streetName, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_STREET_SEARCH);
        intent.putExtra(ToolbarActivity.EXTRA_TITLE, streetName);
        fragment.startActivityForResult(intent, requestCode);
    }

    private EditText searchView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_street_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null) {
            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerView.setAdapter(new StreetSearchAdapter(this));
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if(actionBar != null) {
                actionBar.setCustomView(R.layout.actionbar_edittext_view);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowCustomEnabled(true);

                searchView = actionBar.getCustomView().findViewById(R.id.et_actionbar);
                presenter.setSearchTextListening(searchView);
                searchView.setHint(R.string.street_name);
            }
        }

        if(getArguments() != null && getArguments().getString(ToolbarActivity.EXTRA_TITLE) != null) {
            searchView.setText(getArguments().getString(ToolbarActivity.EXTRA_TITLE));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                clearActionBar();
                return true;
        }
        return false;
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onSearchSuccess(List<AddressModel> results) {
        ((StreetSearchAdapter) binding.recyclerView.getAdapter()).setData(results);
    }

    @Override
    public void onItemClicked(AddressModel addressModel) {
        Activity activity = getActivity();
        if(activity != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(StreetSearchFragment.class.getSimpleName(), addressModel);

            activity.setResult(Activity.RESULT_OK, resultIntent);
            activity.finish();
        }
    }

    private void clearActionBar() {
        if (searchView != null) {
            searchView.setText("");
        }
    }
}
