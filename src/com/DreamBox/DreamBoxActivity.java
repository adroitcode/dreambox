package com.DreamBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;


import java.io.IOException;
import java.util.List;


public class DreamBoxActivity extends Activity {

    private static final String appKey = "58l9s2l13lsz9fs";
    private static final String appSecret = "hthwp7pon8kfktx";

    private static final int REQUEST_LINK_TO_DBX = 0;

    private ImageButton mLinkButton;
    private Button mNewButton;


    private static final String TAG = "DreamBox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_dropbox);

        mLinkButton = (ImageButton) findViewById(R.id.link_button);
        mLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLinkToDropbox();
            }
        });
        mNewButton = (Button) findViewById(R.id.new_button);


        DreamBoxApplication.mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), appKey, appSecret);

        if (DreamBoxApplication.mDbxAcctMgr.hasLinkedAccount()) {
            showLinkedView();
        } else {
            showUnlinkedView();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (DreamBoxApplication.mDbxAcctMgr.hasLinkedAccount()) {
            showLinkedView();
        } else {
            showUnlinkedView();
        }
    }

    private void showLinkedView() {
        mLinkButton.setVisibility(View.GONE);
        mNewButton.setVisibility(View.VISIBLE);

    }

    private void showUnlinkedView() {
        mLinkButton.setVisibility(View.VISIBLE);

        //setContentView(R.layout.unauth);
    }

    private void onClickLinkToDropbox() {
        //Check to see if user is already logged in
        if(!DreamBoxApplication.mDbxAcctMgr.hasLinkedAccount()){
            DreamBoxApplication.mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
        }
    }

    public void onClickNewDream(View view) {
        Intent i = new Intent(getBaseContext(), NewDreamActivity.class);
        //i.putExtra("extra", "Hi");
        startActivity(i);
        Log.d(TAG, "New dream activity requested");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"activity result");
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
                //createFile();
                showLinkedView();
            } else {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





}