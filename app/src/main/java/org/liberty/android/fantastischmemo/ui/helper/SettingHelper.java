package org.liberty.android.fantastischmemo.ui.helper;

import android.app.ProgressDialog;
import android.os.Bundle;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AMEnv;
import org.liberty.android.fantastischmemo.entity.Setting;
import org.liberty.android.fantastischmemo.ui.FileBrowserFragment;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.Global.getString;

/**
 * Created by SnegaT on 2018-04-12.
 */

public class SettingHelper {

    public SettingHelper(){}

    public static List setColors(Setting setting) {
        List<Integer> colors = new ArrayList<Integer>(5);
        colors.add(setting.getQuestionTextColor());
        colors.add(setting.getHintTextColor());
        colors.add(setting.getAnswerTextColor());
        colors.add(setting.getQuestionBackgroundColor());
        colors.add(setting.getHintBackgroundColor());
        colors.add(setting.getAnswerBackgroundColor());
        colors.add(setting.getSeparatorColor());

        return colors;
    }

    public static Setting setValues(Setting setting, List<Integer> colors) {
        setting.setQuestionTextColor(colors.get(0));
        setting.setHintTextColor(colors.get(1));
        setting.setAnswerTextColor(colors.get(2));
        setting.setQuestionBackgroundColor(colors.get(3));
        setting.setHintBackgroundColor(colors.get(4));
        setting.setAnswerBackgroundColor(colors.get(5));
        setting.setSeparatorColor(colors.get(6));

        return setting;
    }

    public static Bundle setQuestionBundle(Bundle b) {
        b.putString(FileBrowserFragment.EXTRA_FILE_EXTENSIONS, ".ttf");
        b.putString(FileBrowserFragment.EXTRA_DEFAULT_ROOT, AMEnv.DEFAULT_ROOT_PATH);
        b.putBoolean(FileBrowserFragment.EXTRA_DISMISS_ON_SELECT, true);

        return b;
    }

    public static Bundle setAnswerBundle(Bundle b) {
        b.putString(FileBrowserFragment.EXTRA_FILE_EXTENSIONS, ".ttf");
        b.putString(FileBrowserFragment.EXTRA_DEFAULT_ROOT, AMEnv.DEFAULT_ROOT_PATH);
        b.putBoolean(FileBrowserFragment.EXTRA_DISMISS_ON_SELECT, true);

        return b;
    }

    public static ProgressDialog setProgressDialog(ProgressDialog progressDialog, String title, String message) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);

        return progressDialog;
    }


}
