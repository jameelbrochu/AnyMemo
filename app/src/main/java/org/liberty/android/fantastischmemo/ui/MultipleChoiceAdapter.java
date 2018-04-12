package org.liberty.android.fantastischmemo.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.MultipleChoiceHolder> {

    private Context mContext;
    private List<MultipleChoiceCard> mcCards;
    private MultipleChoiceCardDao multipleChoiceCardDao;
    private AnyMemoDBOpenHelper dbOpenHelper;
    private String dbPath;

    public MultipleChoiceAdapter(Context mContext, List<MultipleChoiceCard> mCards) {
        this.mContext = mContext;
        this.mcCards = mCards;
        this.dbPath = dbPath;
    }

    @Override
    public MultipleChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mcInflater = LayoutInflater.from(mContext);
        view = mcInflater.inflate(R.layout.cardview_item_mc, parent, false);
        return new MultipleChoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(final MultipleChoiceHolder multipleChoiceHolder, final int position) {
        final MultipleChoiceCard card = mcCards.get(position);

        multipleChoiceHolder.questionText.setText(mcCards.get(position).getQuestion());
        multipleChoiceHolder.option1Text.setText("Option 1: " + card.getOption1());
        multipleChoiceHolder.option2Text.setText("Option 2: " + card.getOption2());
        multipleChoiceHolder.option3Text.setText("Option 3: " + card.getOption3());
        multipleChoiceHolder.option4Text.setText("Option 4: " + card.getOption4());
        multipleChoiceHolder.answerText.setText("Answer: " + card.getAnswer());

        dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(mContext, dbPath);
        multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(dbOpenHelper);

        multipleChoiceHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItemPopup(view, card, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcCards.size();
    }

    private void showListItemPopup(final View childView, final MultipleChoiceCard card, final int position) {
        View view = childView.findViewById(R.id.rv_mc_question);
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mc_card_list_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent myIntent = new Intent();
                switch (menuItem.getItemId()) {
                    case R.id.delete_mc:
                        multipleChoiceCardDao.deleteMultipleChoiceCard(card);
                        mcCards.remove(card);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mcCards.size());
                        break;
                    case R.id.edit_mc:
                        myIntent.setClass(mContext, CardMCEditor.class);
                        myIntent.putExtra(CardMCEditor.EXTRA_DBPATH_MC, dbPath);
                        myIntent.putExtra(CardMCEditor.EXTRA_MC, card);
                        mContext.startActivity(myIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public static class MultipleChoiceHolder extends RecyclerView.ViewHolder {

        TextView questionText;
        TextView option1Text;
        TextView option2Text;
        TextView option3Text;
        TextView option4Text;
        TextView answerText;
        CardView cardView;

        public MultipleChoiceHolder(View itemView) {
            super(itemView);

            questionText = (TextView) itemView.findViewById(R.id.rv_mc_question);
            option1Text = (TextView) itemView.findViewById(R.id.rv_mc_option1);
            option2Text = (TextView) itemView.findViewById(R.id.rv_mc_option2);
            option3Text = (TextView) itemView.findViewById(R.id.rv_mc_option3);
            option4Text = (TextView) itemView.findViewById(R.id.rv_mc_option4);
            answerText = (TextView) itemView.findViewById(R.id.rv_mc_answer);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
