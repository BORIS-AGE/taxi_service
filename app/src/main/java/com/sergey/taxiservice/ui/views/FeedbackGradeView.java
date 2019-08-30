package com.sergey.taxiservice.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sergey.taxiservice.R;

import java.util.List;

public class FeedbackGradeView extends LinearLayout {

    private LinearLayout gradesContainer;

    public FeedbackGradeView(Context context) {
        super(context);
        init();
    }

    public FeedbackGradeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeedbackGradeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_feedback_grade, this);
        gradesContainer = findViewById(R.id.gradesContainer);
    }

    public void setFeedbackGrades(List<Float> grades, List<FeedbackView.FeedbackParams> params) {
        if(grades.size() == params.size()) {
            for(int i = 0; i < params.size(); i++) {
                initGradeItem(params.get(i).getTitle(), params.get(i).getColor(), grades.get(i));
            }
        }
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    private void initGradeItem(String title, int color, float grade) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            View itemView = inflater.inflate(R.layout.item_feedback_grade, null);
            TextView titleView = itemView.findViewById(R.id.title);
            TextView gradeView = itemView.findViewById(R.id.grade);
            FrameLayout gradeSize = itemView.findViewById(R.id.gradeSize);

            titleView.setText(title);
            titleView.setTextColor(color);
            gradeView.setText(Float.toString(grade));
            gradeSize.setBackgroundColor(color);
            gradeSize.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, grade));

            gradesContainer.addView(itemView);
        }
    }
}
