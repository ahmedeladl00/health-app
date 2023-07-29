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


    public TopBarHelper(Activity activity) {
        CircularImageView profileImageView = activity.findViewById(R.id.profile_image);
        userNameTextView = activity.findViewById(R.id.user_name);
        burgerIcon = activity.findViewById(R.id.burger_icon);
        notificationIcon = activity.findViewById(R.id.notification_icon);

        User user = User.getInstance();
        user.loadUserData(activity);
        String userName = user.getName();

        setUpTopBar(activity, userName);
    }

    private void setUpTopBar(final Activity activity, String userName) {

        userNameTextView.setText(userName);


        burgerIcon.setOnClickListener(v -> {
            // Handle burger icon click here
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
        });

        notificationIcon.setOnClickListener(v -> {
            // Handle burger icon click here
            Intent intent = new Intent(activity, NotificationActivity.class);
            activity.startActivity(intent);

        });
    }

}

