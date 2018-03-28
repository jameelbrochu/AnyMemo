package org.liberty.android.fantastischmemo.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class MCDaoImpl extends AbstractHelperDaoImpl<MultipleChoiceCard, Integer> implements MCDao {

    private AnyMemoDBOpenHelper helper = null;

    protected MCDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<MultipleChoiceCard> config) throws SQLException {
        super(connectionSource, config);
    }

    public MCDaoImpl(ConnectionSource connectionSource, Class<MultipleChoiceCard> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public AnyMemoDBOpenHelper getHelper() {
        if (helper != null) {
            return helper;
        } else {
            throw new IllegalStateException("Must set the helper in order to use");
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
    public List<MultipleChoiceCard> getAllMCCards() {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        return dbHelper.getAllMultipleChoiceCards();
    }

    @Override
    public MultipleChoiceCard getMCCard(long id) {
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
    public MultipleChoiceCard addMCCard(MultipleChoiceCard mcCard) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        dbHelper.insertMCCard(mcCard, dbHelper.getWritableDatabase());
        if (mcCard != null) {
            return mcCard;
        }
        return null;
    }

    @Override
    public List<MultipleChoiceCard> deleteMCCard(MultipleChoiceCard mcCard) {
        long id;
        AnyMemoDBOpenHelper dbHelper = getHelper();
        id = mcCard.getId();
        dbHelper.deleteMCCard(mcCard);
        for(MultipleChoiceCard card : getAllMCCards()) {
            if (card.getId() == (id + 1)) {
                dbHelper.updateMCId(String.valueOf(id), String.valueOf(card.getId()));
                card.setId(id);
                id++;
            }
        }
        return getAllMCCards();
    }

    @Override
    public MultipleChoiceCard getNextMCCard(MultipleChoiceCard mcCard) {
        long currentCardId;
        long nextCardId;
        currentCardId = mcCard.getId();
        nextCardId = currentCardId + 1;
        MultipleChoiceCard nextCard = getMCCard(nextCardId);
        if (nextCard != null)  {
            return nextCard;
        }
        else {
            return getMCCard(currentCardId);
        }
    }

    @Override
    public MultipleChoiceCard getPrevMCCard(MultipleChoiceCard mcCard) {
        long currentCardId;
        long prevCardId;
        currentCardId = mcCard.getId();
        prevCardId = currentCardId - 1;
        MultipleChoiceCard prevCard = getMCCard(prevCardId);
        if (prevCard != null) {
            return prevCard;
        } else {
            return getMCCard(currentCardId);
        }
    }


}
