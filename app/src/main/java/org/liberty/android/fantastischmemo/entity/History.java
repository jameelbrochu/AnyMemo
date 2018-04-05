package org.liberty.android.fantastischmemo.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.liberty.android.fantastischmemo.dao.HistoryDaoImpl;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceDaoImpl;

import java.sql.Timestamp;

/**
 * Created by SnegaT on 2018-04-05.
 */

@DatabaseTable(tableName = "history", daoClass = HistoryDaoImpl.class)
public class History {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(defaultValue = "", width = 8192)
    private String dbPath;

    @DatabaseField(defaultValue = "", width = 8192)
    private double mark;

    @DatabaseField(defaultValue = "", width = 8192)
    private long timeStamp;

    public History() {}

    public History(String dbPath, double mark, long timeStamp) {
        this.dbPath = dbPath;
        this.mark = mark;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getdbPath() {
        if (dbPath == null) {
            return "";
        }
        return dbPath;
    }

    public void setdbPath(String dbPath) {
        if (dbPath == null) {
            this.dbPath = "";
        } else {
            this.dbPath = dbPath;
        }
    }

    public double getMark() { return mark; }

    public void setMark(double mark) { this.mark = mark; }

    public long getTimeStamp() { return timeStamp; }

    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }

}
