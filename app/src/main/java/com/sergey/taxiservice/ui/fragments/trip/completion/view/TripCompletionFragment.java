package com.sergey.taxiservice.ui.fragments.trip.completion.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentTripCompetionBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.trip.completion.presenter.TripCompletionPresenter;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.OnFeedbackSelectedListener;

import java.util.List;

public class TripCompletionFragment extends BaseBindingToolbarFragment<TripCompletionPresenter, FragmentTripCompetionBinding> implements TripCompletionView, OnFeedbackSelectedListener {

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_COMPLETION);
        activity.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_trip_completion);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_trip_competion;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnTripCompletion.setOnClickListener(view1 -> {
            presenter.onTripCompletionClicked();
//            MainActivity.openWithSection(getActivity(), MainActivity.MainActivitySection.Home, true);
        });

        presenter.loadResources();
        presenter.loadDriverInfo();
    }

    @Override
    public void onDriverInfoLoaded(String fullName, String genderWithAge) {
//        binding.driverName.setText(fullName);
//        binding.genderText.setText(genderWithAge);
    }

    @Override
    public void onFeedbackParamsLoaded(List<FeedbackView.FeedbackParams> feedbackParams) {
        for(FeedbackView.FeedbackParams params : feedbackParams) {
            FeedbackView feedbackView = new FeedbackView(getContext());
            feedbackView.setListener(this);
            feedbackView.setFeedbackParams(params);
            binding.feedbackContainer.addView(feedbackView);
        }
    }

    @Override
    public void feedbackSelected(String title, float grade) {
        String[] points = getResources().getStringArray(R.array.view_array_feedback_points);
        if(title.equals(points[0])) {
            presenter.onPunctualitySelected(grade);
        } else if(title.equals(points[1])) {
            presenter.onSociabilityScelected(grade);
        } else if(title.equals(points[2])) {
            presenter.onRecommendedSelected(grade);
        } else if(title.equals(points[3])) {
            presenter.onNotRecommendedSelected(grade);
        }
    }
}
