package org.liberty.android.fantastischmemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.liberty.android.fantastischmemo.dao.HistoryDaoImpl;

@DatabaseTable(tableName = "History", daoClass = HistoryDaoImpl.class)
public class History implements Parcelable {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(defaultValue = "", width = 8192)
    private String dbPath;

    @DatabaseField(defaultValue = "0", width = 8192)
    private double mark;

    @DatabaseField(defaultValue = "0", width = 8192)
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

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    protected History(Parcel in) {
        dbPath = in.readString();
        mark = in.readDouble();
        timeStamp = in.readLong();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getdbPath());
        parcel.writeDouble(this.getMark());
        parcel.writeLong(this.getTimeStamp());
    }
}
