package org.liberty.android.fantastischmemo.test.ui;

import android.os.Build;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by SnegaT on 2018-03-18.
 */

public class PermissionHelper {

    public static void allowPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector().text("Allow"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    // Timber.e(e, "There is no permissions dialog to interact with ");
                }
            }
        }
    }
}
