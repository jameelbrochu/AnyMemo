package org.liberty.android.fantastischmemo.ui.multipleChoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.ArrayList;
import java.util.List;


public class PreviewEditMCActivity extends AppCompatActivity {

    private List<MultipleChoiceCard> mcCards = new ArrayList<>();
    private RecyclerView mcRV;
    private MultipleChoiceCardDao multipleChoiceCardDao;
    private AnyMemoDBOpenHelper dbOpenHelper;
    private FloatingActionButton addCardButton;
    public static final String EXTRA_DBPATH_MC = "dbpath";
    String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_edit_mc_activity);
        dbPath = getIntent().getExtras().getString(EXTRA_DBPATH_MC);
        mcRV = (RecyclerView) findViewById(R.id.recyclerview_mc);
        addCardButton = (FloatingActionButton) findViewById(R.id.addCard);
        addCardButton.setOnClickListener(addButtonListener);
        setDbOpenHelper();
        initializeData();
        initializeAdapter();
    }

    private void setDbOpenHelper() {
        dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(getApplicationContext(), dbPath);
        multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(dbOpenHelper);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        initializeData();
        initializeAdapter();
    }

    private void initializeData() {
        mcCards =  multipleChoiceCardDao.getAllMultipleChoiceCards();
    }

    private void initializeAdapter() {
        if (mcCards == null) {
            new AlertDialog.Builder(PreviewEditMCActivity.this)
                    .setTitle("No items in deck")
                    .setCancelable(false)
                    .show();
        } else {
            MultipleChoiceAdapter mcAdapter = new MultipleChoiceAdapter(this, mcCards, dbPath);
            mcRV.setLayoutManager(new GridLayoutManager(this, 1));
            mcRV.setAdapter(mcAdapter);
        }
    }

    private View.OnClickListener addButtonListener =
            new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent();
                    myIntent.setClass(getApplicationContext(), CardMCEditor.class);
                    myIntent.putExtra(CardMCEditor.EXTRA_DBPATH_MC, dbPath);

                    startActivity(myIntent);
                }
                };
}
