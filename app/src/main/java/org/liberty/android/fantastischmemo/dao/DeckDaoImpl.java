package org.liberty.android.fantastischmemo.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import org.liberty.android.fantastischmemo.entity.Card;
import org.liberty.android.fantastischmemo.entity.Category;
import org.liberty.android.fantastischmemo.entity.Deck;

import java.sql.SQLException;
import java.util.Random;

public class DeckDaoImpl extends BaseDaoImpl<Deck, Integer> implements DeckDao {
    public DeckDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Deck> tableConfig)
        throws SQLException {
        super(connectionSource, Deck.class);
    }
    public DeckDaoImpl(ConnectionSource connectionSource, Class<Deck> clazz)
        throws SQLException {
        super(connectionSource, clazz);
    }

    public void shuffleCards(String name) throws SQLException {

        QueryBuilder<Deck, Integer> qb = queryBuilder();
        PreparedQuery<Deck> pq = qb.where().eq("name", name).prepare();
        Deck deck = queryForFirst(pq);



        Card[] cards = new Card[10];
        Random randomNum = new Random();
        Card temp;
        int newNum;
        int cardsInDeck = cards.length;

        for(int i=0; i<cards.length; i++){

            //pick a random number between 0 and cardsInDeck - 1
            newNum = randomNum.nextInt(cardsInDeck);

            //swap cards[i] and cards[newIndex]
            temp = cards[i];
            cards[i] = cards[newNum];
            cards[newNum] = temp;
        }
    }

}

