package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.ArrayList;
import java.util.List;


public class PreviewEditMCActivity extends Activity {

    private List<MultipleChoiceCard> mcCards = new ArrayList<>();
    private RecyclerView rv;
    private MultipleChoiceCardDao multipleChoiceCardDao;
    private AnyMemoDBOpenHelper dbOpenHelper;
    private FloatingActionButton addCardButton;
    public static String EXTRA_DBPATH_MC = "dbpath";
    String dbPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_edit_mc_activity);
        dbPath = getIntent().getExtras().getString(EXTRA_DBPATH_MC);
        rv=(RecyclerView)findViewById(R.id.rv);
        addCardButton = (FloatingActionButton) findViewById(R.id.addCard);
        addCardButton.setOnClickListener(addButtonListener);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(getApplicationContext(), dbPath);
        multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(dbOpenHelper);
        initializeData();
        initializeAdapter();
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
            RVAdapter adapter = new RVAdapter(mcCards);
            rv.setAdapter(adapter);
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
