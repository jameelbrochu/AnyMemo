package org.liberty.android.fantastischmemo.dao;

import org.liberty.android.fantastischmemo.entity.History;

import java.util.List;

public interface HistoryDao extends HelperDao<History, Integer> {

    public List<History> getHistoryForDB(String dbpath);

    public History insertHistory(History history);

    public void deleteHistory(History history);

    public int count(String dbPath);
}
