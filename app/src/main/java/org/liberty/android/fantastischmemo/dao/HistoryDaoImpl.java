package org.liberty.android.fantastischmemo.dao;


import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.entity.History;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by SnegaT on 2018-04-05.
 */

public class HistoryDaoImpl extends AbstractHelperDaoImpl<History, Integer> implements HistoryDao {

    private AnyMemoDBOpenHelper helper = null;

    protected HistoryDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<History> config) throws SQLException {
        super(connectionSource, config);
    }

    public HistoryDaoImpl(ConnectionSource connectionSource, Class<History> clazz) throws SQLException {
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


    public int create(History history) {
        int res = super.create(history);
        return res;
    }

    public int delete(History history) {
        if (history != null) {
            int res = super.delete(history);
        }
        return 0;
    }

    @Override
    public List<History> getHistoryForDB(String dbPath) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        return dbHelper.getHistoryForDB(dbPath);
    }

    @Override
    public History insertHistory(History hist) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        dbHelper.insertHistory(hist, dbHelper.getWritableDatabase());
        if (hist != null) {
            return hist;
        }
        return null;
    }

    @Override
    public void deleteHistory(History history) {

        AnyMemoDBOpenHelper dbHelper = getHelper();
        long id = history.getId();
        dbHelper.deleteHistory(history);

    }

    @Override
    public int count(String dbPath) {
        AnyMemoDBOpenHelper dbHelper = getHelper();
        return dbHelper.getCountHistoryforDB(dbPath);
    }


}
