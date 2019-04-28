package model;

import com.github.collinalpert.java2db.annotations.TableName;
import com.github.collinalpert.java2db.entities.BaseEntity;

@TableName("score")
public class Score extends BaseEntity {
    private int word_id;
    private int length;
    private int tries;
    private boolean win;

    public Score(int word_id, int length, int tries, boolean win) {
        this.word_id = word_id;
        this.length = length;
        this.tries = tries;
        this.win = win;
    }

    public Score() {
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
