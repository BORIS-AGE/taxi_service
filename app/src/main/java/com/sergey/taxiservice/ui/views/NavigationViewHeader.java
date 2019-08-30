package com.sergey.taxiservice.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.util.ImageLoader;

public class NavigationViewHeader extends RelativeLayout {

    private ImageView userAva;

    public NavigationViewHeader(Context context) {
        super(context);
        init();
    }

    public NavigationViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nav_header, NavigationViewHeader.this);
        userAva = findViewById(R.id.imageView);
    }

    public void setUser(UserModel user) {
        if(user != null && user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            ImageLoader.load(user.getAvatar(), userAva, R.drawable.default_avatar, null);
        } else ImageLoader.load(R.drawable.default_avatar, userAva, null);
    }
}
