package com.lalin.test.site.blog.mix.one.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by frzhao on 2017/12/13.
 */
@Entity
public class BorrowingProbe {
    @Id
    @GeneratedValue
    Integer id;
    int borrowListId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBorrowListId() {
        return borrowListId;
    }

    public void setBorrowListId(int borrowListId) {
        this.borrowListId = borrowListId;
    }
}
