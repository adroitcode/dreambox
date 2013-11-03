package com.DreamBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.dropbox.sync.android.*;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 3/19/13
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewDreamActivity extends Activity {
    private TextView mTestOutput;
    public EditText titleInput;
    public EditText dreamInput;
    private static final String TAG = "DreamBox";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_dream);

        titleInput = (EditText) findViewById(R.id.dreamTitleInput);
        dreamInput = (EditText) findViewById(R.id.dreamTextInput);
    }



    public void createFile(String title, String body){
        try{
            DbxFileSystem dbxFs = DbxFileSystem.forAccount(DreamBoxApplication.mDbxAcctMgr.getLinkedAccount());

            DbxFile testFile = dbxFs.create(new DbxPath(title + ".txt"));
            try {
                testFile.writeString(body);
            } finally {
                testFile.close();
            }
        }   catch(Exception e){
            Log.d(TAG,"Error creating file");
        }

    }

    public void saveDream(View view) {
        if(!titleInput.getText().toString().equals("") && !dreamInput.getText().toString().equals("")){
            createFile(titleInput.getText().toString(),dreamInput.getText().toString());
            titleInput.setText("");
            dreamInput.setText("");
            Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Please enter a title and body",Toast.LENGTH_LONG).show();
        }

    }
}
