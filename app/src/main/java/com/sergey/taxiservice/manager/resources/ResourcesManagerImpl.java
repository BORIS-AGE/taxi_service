package com.sergey.taxiservice.manager.resources;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.views.FeedbackView;

import java.util.ArrayList;
import java.util.List;

public class ResourcesManagerImpl implements ResourcesManager {

    private Resources resources;

    public ResourcesManagerImpl(Context context) {
        this.resources = context.getResources();
    }

    @Override
    public List<FeedbackView.FeedbackParams> getFeedbackResources() {
        int[] colors = resources.getIntArray(R.array.view_array_feedback_points_color);
        int[] maxGrades = resources.getIntArray(R.array.view_array_feedback_grades_max);
        int[] initialGrades = resources.getIntArray(R.array.view_array_feedback_initial_grades);
        String[] titles = resources.getStringArray(R.array.view_array_feedback_points);
        TypedArray drawables = resources.obtainTypedArray(R.array.view_array_feedback_drawable);

        List<FeedbackView.FeedbackParams> feedbackParams = new ArrayList<>();
        for(int i = 0; i < titles.length; i++) {
            feedbackParams.add(new FeedbackView.FeedbackParams(
                    colors[i],
                    maxGrades[i],
                    initialGrades[i],
                    titles[i],
                    drawables.getResourceId(i, -1)
            ));
        }

        drawables.recycle();

        return feedbackParams;
    }
}
