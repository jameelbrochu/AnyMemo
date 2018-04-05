package org.liberty.android.fantastischmemo.dao;

import org.liberty.android.fantastischmemo.entity.History;

import java.util.List;

/**
 * Created by SnegaT on 2018-04-05.
 */

public interface HistoryDao extends HelperDao<History, Integer> {

    List<History> getHistoryForDB(String dbpath);

    History insertHistory(History history);

    void deleteHistory(History history);




}
