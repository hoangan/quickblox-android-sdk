package com.quickblox.chat_v2.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.quickblox.chat_v2.R;
import com.quickblox.chat_v2.core.ChatApplication;
import com.quickblox.chat_v2.interfaces.OnPictureDownloadComplete;
import com.quickblox.chat_v2.widget.TopBar;
import com.quickblox.module.users.model.QBUser;

import java.io.File;

/**
 * Created with IntelliJ IDEA. User: Andrew Dmitrenko Date: 08.04.13 Time: 8:58
 */
public class UserProfileActivity extends Activity implements OnPictureDownloadComplete {

    private ImageView userpic;
    private TextView username;
    private Bitmap userBitmap;

    private ChatApplication app;
    private QBUser friend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        TableRow low = (TableRow) findViewById(R.id.low_table_row);
        low.setVisibility(View.GONE);

        TopBar tb = (TopBar) findViewById(R.id.top_bar);
        tb.setFragmentParams(null, View.INVISIBLE, View.INVISIBLE);

        app = ChatApplication.getInstance();
        app.getQbm().setPictureDownloadComplete(this);

        userpic = (ImageView) findViewById(R.id.profile_userpic);
        username = (TextView) findViewById(R.id.chat_dialog_view_profile);

        //getUserPicture();
    }

    private void getUserPicture() {

        if (friend.getFileId() != null) {
            app.getQbm().setPictureDownloadComplete(UserProfileActivity.this);
            app.getQbm().downloadQBFile(friend);

        }

        username.setText(friend.getFullName() != null ? friend.getFullName() : friend.getLogin());

    }


    @Override
    public void downloadComlete(Bitmap bitmap, File file) {
        userBitmap = bitmap;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                userpic.setImageBitmap(userBitmap);
            }
        });
    }
}