package model;

import com.github.collinalpert.java2db.annotations.TableName;
import com.github.collinalpert.java2db.entities.BaseEntity;

@TableName("word")
public class Word extends BaseEntity {
    private String name;

    public Word(String name) {
        this.name = name;
    }

    public Word() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
