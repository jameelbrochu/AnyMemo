package org.liberty.android.fantastischmemo.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class MultipleChoiceDaoImpl extends AbstractHelperDaoImpl<MultipleChoiceCard, Integer> implements MultipleChoiceCardDao {

    private AnyMemoDBOpenHelper helper = null;

    protected MultipleChoiceDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<MultipleChoiceCard> config) throws SQLException {
        super(connectionSource, config);
    }

    public MultipleChoiceDaoImpl(ConnectionSource connectionSource, Class<MultipleChoiceCard> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public AnyMemoDBOpenHelper getHelper() {
        if (helper != null) {
            return helper;
        } else {
            throw new IllegalStateException("Must set the helper in order to use.");
        }
    }

    @Override
    public void setHelper(AnyMemoDBOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public int create(MultipleChoiceCard mcCard) {
            int res = super.create(mcCard);
            return res;
    }

    @Override
    public int delete(MultipleChoiceCard mcCard) {
        if(mcCard != null) {
            int res = super.delete(mcCard);
        }
        return 0;
    }

    @Override
    public List<MultipleChoiceCard> getAllMultipleChoiceCards() {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        return dbHelper.getAllMultipleChoiceCards();
    }

    @Override
    public MultipleChoiceCard getMultipleChoiceCard(long id) {
        List<MultipleChoiceCard> mcCards;
        AnyMemoDBOpenHelper dbHelper = getHelper();
        mcCards = dbHelper.getAllMultipleChoiceCards();
        for(MultipleChoiceCard card : mcCards) {
            if(Objects.equals(card.getId(), id)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public MultipleChoiceCard addMultipleChoiceCard(MultipleChoiceCard mcCard) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        dbHelper.insertMultipleChoiceCard(mcCard, dbHelper.getWritableDatabase());
        if (mcCard != null) {
            return mcCard;
        }
        return null;
    }

    @Override
    public void deleteMultipleChoiceCard(MultipleChoiceCard mcCard) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        long id = mcCard.getId();
        dbHelper.deleteMultipleChoiceCard(mcCard);
        for(MultipleChoiceCard card : getAllMultipleChoiceCards()) {
            if (card.getId() == (id + 1)) {
                dbHelper.updateMultipleChoiceId(String.valueOf(id), String.valueOf(card.getId()));
                card.setId(id);
                id++;
            }
        }
    }

    @Override
    public MultipleChoiceCard getNextMultipleChoiceCard(MultipleChoiceCard mcCard) {
        long currentCardId = mcCard.getId();
        long nextCardId = currentCardId + 1;
        MultipleChoiceCard nextCard = getMultipleChoiceCard(nextCardId);
        if (nextCard != null)  {
            return nextCard;
        } else {
            return getMultipleChoiceCard(currentCardId);
        }
    }

    @Override
    public MultipleChoiceCard getPrevMultipleChoiceCard(MultipleChoiceCard mcCard) {
        long currentCardId = mcCard.getId();
        long prevCardId = currentCardId - 1;
        MultipleChoiceCard prevCard = getMultipleChoiceCard(prevCardId);
        if (prevCard != null) {
            return prevCard;
        } else {
            return getMultipleChoiceCard(currentCardId);
        }
    }


}
