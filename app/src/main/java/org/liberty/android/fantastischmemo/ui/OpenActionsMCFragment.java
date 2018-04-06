package org.liberty.android.fantastischmemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AMPrefKeys;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.common.BaseActivity;
import org.liberty.android.fantastischmemo.common.BaseDialogFragment;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;
import org.liberty.android.fantastischmemo.utils.AMFileUtil;
import org.liberty.android.fantastischmemo.utils.AMPrefUtil;
import org.liberty.android.fantastischmemo.utils.RecentListUtil;
import org.liberty.android.fantastischmemo.utils.ShareUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OpenActionsMCFragment extends BaseDialogFragment{

    public static final String EXTRA_DBPATH_MC = "dbpath";
    private BaseActivity mActivity;

    private String dbPath;

    private View studyItem;
    private View editItem;
    private View deleteItem;

    MultipleChoiceCardDao multipleChoiceCardDao;
    AnyMemoDBOpenHelper dbOpenHelper;
    List<MultipleChoiceCard> multipleChoiceCards = new ArrayList<>();

    @Inject AMFileUtil amFileUtil;

    @Inject RecentListUtil recentListUtil;

    @Inject
    AMPrefUtil amPrefUtil;

    public OpenActionsMCFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fragmentComponents().inject(this);
        Bundle args = this.getArguments();
        dbPath = args.getString(EXTRA_DBPATH_MC);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        View v = inflater.inflate(R.layout.open_actions_mc_layout, container, false);

        studyItem = v.findViewById(R.id.study);
        studyItem.setOnClickListener(buttonClickListener);

        editItem = v.findViewById(R.id.edit);
        editItem.setOnClickListener(buttonClickListener);

        deleteItem = v.findViewById(R.id.delete);
        deleteItem.setOnClickListener(buttonClickListener);

        return v;
    }
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v == studyItem) {
                Intent myIntent = new Intent();
                myIntent.setClass(mActivity, MCStudyActivity.class);
                myIntent.putExtra(MCStudyActivity.EXTRA_DBPATH_MC, dbPath);
                startActivity(myIntent);
                recentListUtil.addToRecentList(dbPath);
            }

            if (v == editItem) {
                Intent myIntent = new Intent();
                myIntent.setClass(mActivity, PreviewEditMCActivity.class);
                myIntent.putExtra(PreviewEditActivity.EXTRA_DBPATH, dbPath);
                int startId = amPrefUtil.getSavedInt(AMPrefKeys.PREVIEW_EDIT_START_ID_PREFIX, dbPath, 1);
                myIntent.putExtra(PreviewEditActivity.EXTRA_CARD_ID, startId);
                startActivity(myIntent);
                recentListUtil.addToRecentList(dbPath);
            }

            if (v == deleteItem) {
                new AlertDialog.Builder(mActivity)
                        .setTitle(getString(R.string.delete_text))
                        .setMessage(getString(R.string.fb_delete_message))
                        .setPositiveButton(getString(R.string.delete_text), new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which ) {
                                dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(mActivity, dbPath);
                                multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
                                multipleChoiceCardDao.setHelper(dbOpenHelper);
                                multipleChoiceCards = multipleChoiceCardDao.getAllMultipleChoiceCards();

                                for (MultipleChoiceCard card : multipleChoiceCards) {
                                    multipleChoiceCardDao.deleteMultipleChoiceCard(card);
                                }

                                amFileUtil.deleteDbSafe(dbPath);
                                recentListUtil.deleteFromRecentList(dbPath);

                            /* Refresh the list */
                                mActivity.restartActivity();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel_text), null)
                        .create()
                        .show();
            }
            dismiss();
        }
    };

}
