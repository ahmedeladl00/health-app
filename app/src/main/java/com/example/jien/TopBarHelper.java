/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
            Intent intent = new Intent(activity, DigitalSpanActivity.class);
            activity.startActivity(intent);

        });

    }

}

