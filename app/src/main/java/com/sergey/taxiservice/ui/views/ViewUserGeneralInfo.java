package com.sergey.taxiservice.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.util.ImageLoader;

public class ViewUserGeneralInfo extends RelativeLayout {

    private View additionalInfo;
    private ImageView avatar;
    private ImageView gender;
    private TextView genderText;
    private TextView fullName;
    private TextView about;

    public ViewUserGeneralInfo(Context context) {
        super(context);
        init();
    }

    public ViewUserGeneralInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewUserGeneralInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_user_general_info, ViewUserGeneralInfo.this);

        additionalInfo = findViewById(R.id.additionalInfo);
        avatar = findViewById(R.id.avatar);
        gender = findViewById(R.id.genderImage);
        genderText = findViewById(R.id.genderText);
        fullName = findViewById(R.id.fullName);
        about = findViewById(R.id.about);
    }

    @SuppressLint("SetTextI18n")
    public void setGeneralUserInfo(UserGeneralInfo userInfo) {
        if(!userInfo.getFullName().isEmpty()) {
            fullName.setText(userInfo.getFullName());
            fullName.setVisibility(VISIBLE);
        }

        if(!userInfo.getAbout().isEmpty()) {
            about.setText(userInfo.getAbout());
            about.setVisibility(VISIBLE);
        }

        if(!userInfo.getAvaUrl().isEmpty()) {
            ImageLoader.load(userInfo.getAvaUrl(), avatar, R.drawable.default_avatar, null);
        } else ImageLoader.load(R.drawable.default_avatar, avatar, null);

        if(userInfo.getAge() != 0 || userInfo.getGender() != -1) {
            additionalInfo.setVisibility(VISIBLE);

            if(userInfo.getGender() != -1) {
                if(userInfo.getGender() == 0) {
                    ImageLoader.load(R.drawable.ic_user_male, gender, null);
                    genderText.setText(R.string.man);

                } else {
                    ImageLoader.load(R.drawable.ic_user_female, gender, null);
                    genderText.setText(R.string.woman);
                }
            }

            if(userInfo.getAge() != 0) {
                genderText.setText(genderText.getText() + ", " + userInfo.getAge() + " лет");
            }
        }
    }

    public void hideAbout() {
        about.setVisibility(GONE);
    }

    public static class UserGeneralInfo {

        private String fullName;
        private String avaUrl;
        private String about;
        private int gender;
        private int age;

        public UserGeneralInfo(String fullName, String avaUrl, String about, int gender, int age) {
            this.fullName = fullName;
            this.avaUrl = avaUrl;
            this.about = about;
            this.gender = gender;
            this.age = age;
        }

        String getFullName() {
            return fullName;
        }

        String getAbout() {
            return about;
        }

        String getAvaUrl() {
            return avaUrl;
        }

        public int getGender() {
            return gender;
        }

        int getAge() {
            return age;
        }
    }
}
