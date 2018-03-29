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
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;


public class PreviewEditMCActivity extends Activity {

    private List<MultipleChoiceCard> mcCards;
    private RecyclerView rv;
    private MultipleChoiceCardDao multipleChoiceCardDao;
    private FloatingActionButton addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_edit_mc_activity);

        rv=(RecyclerView)findViewById(R.id.rv);
        addCardButton = (FloatingActionButton) findViewById(R.id.addCard);
        addCardButton.setOnClickListener(addButtonListener);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        mcCards = multipleChoiceCardDao.getAllMultipleChoiceCards();

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        mcCards =  multipleChoiceCardDao.getAllMultipleChoiceCards();
        /*mcCards = new ArrayList<>();
        mcCards.add(new MultipleChoiceCard("Who are you?", "god","cat","dog", "Regina", "Regina"));
        mcCards.add(new MultipleChoiceCard("Who are am I?", "tiger","birdy","dog", "lion", "Prab"));
        mcCards.add(new MultipleChoiceCard("Who are am Ilalalala?", "tiger","birdy","dog", "lion", "Prab"));*/

    }

    private void initializeAdapter(){
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
                    //myIntent.setClass(mActivity, CardMCEditor.class);
                   // myIntent.putExtra(CardMCEditor.EXTRA_DBPATH, dbPath);
                    startActivity(myIntent);
                }
                };

}
