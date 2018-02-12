package org.liberty.android.fantastischmemo.dao;

import org.liberty.android.fantastischmemo.entity.Card;
import org.liberty.android.fantastischmemo.entity.Category;
import org.liberty.android.fantastischmemo.entity.Deck;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

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

}

