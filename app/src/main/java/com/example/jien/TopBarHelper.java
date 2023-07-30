package com.example.jien;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class TopBarHelper {
    private final TextView userNameTextView;
    private final ImageView burgerIcon;
    private final ImageView notificationIcon;

    private final CircularImageView userPic;

    private final ImageView noteBtn;

    public TopBarHelper(Activity activity) {
        CircularImageView profileImageView = activity.findViewById(R.id.profile_image);
        userPic =activity.findViewById(R.id.user_pic);
        userNameTextView = activity.findViewById(R.id.user_name);
        burgerIcon = activity.findViewById(R.id.burger_icon);
        notificationIcon = activity.findViewById(R.id.notification_icon);
        noteBtn = activity.findViewById(R.id.noteBtn);

        User user = User.getInstance();
        user.loadUserData(activity);
        String userName = user.getName();

        setUpTopBar(activity, userName);
    }

    private void setUpTopBar(final Activity activity, String userName) {

        userNameTextView.setText(userName);


        userPic.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
        });

        burgerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(intent);
        });

        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, NotificationActivity.class);
            activity.startActivity(intent);

        });

        noteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(activity, WriteNotizenActivity.class);
            activity.startActivity(intent);

        });

    }

}

