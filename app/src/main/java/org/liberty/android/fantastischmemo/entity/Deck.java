package org.liberty.android.fantastischmemo.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.*;

import org.liberty.android.fantastischmemo.dao.DeckDaoImpl;

import java.util.Date;

@DatabaseTable(tableName = "decks", daoClass = DeckDaoImpl.class)
public class Deck {
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(defaultValue = "", width = 8192)
    private String name;

    @DatabaseField(defaultValue = "", width = 8192)
    private String description;

    @DatabaseField(format="yyyy-MM-dd HH:mm:ss.SSSSSS", dataType=DataType.DATE_STRING)
    private Date creationDate;

    @DatabaseField(version = true, format="yyyy-MM-dd HH:mm:ss.SSSSSS", dataType=DataType.DATE_STRING)
    private Date updateDate;

    //An array of cards
    private Card[] cards;

    public Deck() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void shuffleCards(){
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
