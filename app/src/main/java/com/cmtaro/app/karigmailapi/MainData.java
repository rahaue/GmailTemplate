package com.cmtaro.app.karigmailapi;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_name")
public class MainData {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "subject")
    private String subject;

    @ColumnInfo(name = "text")
    private String text;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) { this.text = text; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}
