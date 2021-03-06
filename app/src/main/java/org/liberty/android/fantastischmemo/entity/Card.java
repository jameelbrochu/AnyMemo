package org.liberty.android.fantastischmemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.liberty.android.fantastischmemo.dao.CardDaoImpl;

import java.util.Date;

@DatabaseTable(tableName = "cards", daoClass = CardDaoImpl.class)
public class Card implements VersionableDomainObject, Parcelable {
    @DatabaseField(generatedId = true)
    private Integer id;

    /* The actual card ordinal in a deck */
    @DatabaseField(index = true)
    private Integer ordinal;

    @DatabaseField(defaultValue = "", width = 8192)
    private String question = "";

    @DatabaseField(defaultValue = "", width = 8192)
    private String answer = "";

    @DatabaseField(defaultValue = "", width = 8192)
    private String note = "";

    /* Category = 1 should be uncategorized */
    @DatabaseField(foreign = true, index = true)
    private Category category;

    @DatabaseField(foreign = true)
    private LearningData learningData;

    @DatabaseField(defaultValue = "0")
    private Integer cardType = 0;

    @DatabaseField(format="yyyy-MM-dd HH:mm:ss.SSSSSS", dataType=DataType.DATE_STRING)
    private Date creationDate;

    @DatabaseField(format="yyyy-MM-dd HH:mm:ss.SSSSSS", dataType=DataType.DATE_STRING)
    private Date updateDate;

    @DatabaseField(defaultValue = "N/A", width = 8192)
    private String hint;

    @DatabaseField(defaultValue = "false", width = 8192)
    private boolean favourite;

    private boolean result;

    public Card() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getQuestion() {
        if (question == null) {
            return "";
        }

        return question;
    }

    public void setQuestion(String question) {
        if (question == null) {
            this.question = "";
        } else{
            this.question = question;
        }
    }

    public String getAnswer() {
        if (answer == null ) {
            return "";
        }
        return answer;
    }

    public void setAnswer(String answer) {
        if (answer == null) {
            this.answer = "";
        } else {
            this.answer = answer;
        }
    }

    public String getNote() {
        if (note == null) {
            return "";
        } else {
            return note;
        }
    }

    public void setNote(String note) {
        if (note == null) {
            this.note = "";
        } else {
            this.note = note;
        }
    }

    public void setFavourite(boolean fav){
        this.favourite = fav;
    }

    public boolean getFavourite() {
        return this.favourite;
    }

    public void setResult(boolean result) { this.result = result; }

    public boolean getResult() { return this.result; }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public LearningData getLearningData() {
		return learningData;
	}

	public void setLearningData(LearningData learningData) {
		this.learningData = learningData;
	}

	public String getHint() {
        return hint;
    }

    public void setHint(String hint){
        this.hint = hint;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("id", id)
            .add("question", question)
            .add("answer", answer)
            .add("hint", hint)
            .toString();
    }

    @Override
    public boolean equals(Object c) {
        Card card2 = (Card)c;
        return this.getId().equals(card2.getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getQuestion());
        parcel.writeString(this.getAnswer());
        parcel.writeString(this.getHint());
        parcel.writeInt(this.getId());
    }

    public static final Parcelable.Creator<Card> CREATOR
            = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    private Card(Parcel in) {
        question = in.readString();
        answer = in.readString();
        hint = in.readString();
        id = in.readInt();
    }

}
