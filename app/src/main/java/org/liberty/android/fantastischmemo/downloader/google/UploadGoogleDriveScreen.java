/*
Copyright (C) 2012 Haowen Ning

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package org.liberty.android.fantastischmemo.downloader.google;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.common.base.Strings;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.BaseActivity;
import org.liberty.android.fantastischmemo.ui.FileBrowserFragment;
import org.liberty.android.fantastischmemo.utils.AMGUIUtility;

import java.io.File;

public class UploadGoogleDriveScreen extends BaseActivity {
    private static final String TAG = UploadGoogleDriveScreen.class.getSimpleName();

    private GoogleDriveUploadHelper uploadHelper;

    public static final String EXTRA_AUTH_TOKEN = "authToken";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.upload_google_drive_screen);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            Log.e(TAG, "The extras is not set");
            return;
        }

        String token = extras.getString(EXTRA_AUTH_TOKEN);

        if (Strings.isNullOrEmpty(token)) {
            Log.e(TAG, "The extras does not have token");
            return;
        }

        uploadHelper = new GoogleDriveUploadHelper(this, token);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FileBrowserFragment fragment = new FileBrowserFragment();
        fragment.setOnFileClickListener(fileClickListener);
        ft.add(R.id.file_list, fragment);
        ft.commit();
    }

    private void uploadToGoogleDrive(File file) {
        try {
            uploadHelper.createSpreadsheet(file.getName(), file.getAbsolutePath());
            setResult(Activity.RESULT_OK, new Intent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FileBrowserFragment.OnFileClickListener fileClickListener =
        new FileBrowserFragment.OnFileClickListener() {

            @Override
            public void onClick(File file) {
                showUploadDialog(file);
            }
        };

    private void showUploadDialog(final File file) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.upload_text)
            .setMessage(String.format(getString(R.string.upload_gdrive_message), file.getName()))
            .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface arg0, int arg1){
                    UploadTask task = new UploadTask();
                    task.execute(file);
                }
            })
            .setNegativeButton(R.string.cancel_text, null)
            .show();
    }

    private class UploadTask extends AsyncTask<File, Void, Exception> {

        private ProgressDialog progressDialog;

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UploadGoogleDriveScreen.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(getString(R.string.loading_please_wait));
            progressDialog.setMessage(getString(R.string.upload_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        public Exception doInBackground(File... files) {
            File file = files[0];
            try {
                uploadToGoogleDrive(file);
            } catch (Exception e) {
                Log.e(TAG, "Error uploading ", e);
                return e;
            }
            return null;
        }


        @Override
        public void onPostExecute(Exception e){
            if (e != null) {
                AMGUIUtility.displayException(UploadGoogleDriveScreen.this, getString(R.string.error_text), getString(R.string.error_text), e);
            } else {
                new AlertDialog.Builder(UploadGoogleDriveScreen.this)
                    .setTitle(R.string.successfully_uploaded_text)
                    .setMessage(R.string.gdrive_successfully_uploaded_message)
                    .setPositiveButton(R.string.ok_text, null)
                    .show();
            }
            progressDialog.dismiss();
        }
    }

}
