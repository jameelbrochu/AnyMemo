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
package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import java.io.File;

public class OpenTabFragment extends FileBrowserFragment {
    Activity mActivity;

    public OpenTabFragment() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        setOnFileClickListener(fileClickListener);
    }

    private FileBrowserFragment.OnFileClickListener fileClickListener
        = new FileBrowserFragment.OnFileClickListener() {
            public void onClick(File file) {
                String currentDBPath = file.getAbsolutePath();
                String[] split = currentDBPath.split(".db");
                String nameWithoutDB = split[0];

                if (nameWithoutDB.endsWith("_MC")) {
                    DialogFragment df = new OpenActionsMCFragment();
                    Bundle b = new Bundle();
                    b.putString(OpenActionsMCFragment.EXTRA_DBPATH_MC, file.getAbsolutePath());
                    df.setArguments(b);
                    df.show(((FragmentActivity)mActivity).getSupportFragmentManager(), "OpenActionsMC");
                } else {
                    DialogFragment df = new OpenActionsFragment();
                    Bundle b = new Bundle();
                    b.putString(OpenActionsFragment.EXTRA_DBPATH, file.getAbsolutePath());
                    df.setArguments(b);
                    df.show(((FragmentActivity)mActivity).getSupportFragmentManager(), "OpenActions");
                }
            }
        };
}
