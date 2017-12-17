package com.lalin.test.site.blog.mix.one.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by frzhao on 2017/10/14.
 */
@Entity
public class Probe {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String barCode;
    private String sn;
    private String label;
    private String Description;
    private String Groups;
    private String Status;
    private boolean borrowedNow;



    private String owner;
    private String borrower;
    private Date startTime;
    private Date endTime;
    private String purpose;
    private String location;
    private String note;

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGroups() {
        return Groups;
    }

    public void setGroups(String groups) {
        Groups = groups;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isBorrowedNow() {
        return borrowedNow;
    }

    public void setBorrowedNow(boolean borrowedNow) {
        this.borrowedNow = borrowedNow;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Probe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", sn='" + sn + '\'' +
                ", label='" + label + '\'' +
                ", Description='" + Description + '\'' +
                ", Groups='" + Groups + '\'' +
                ", Status='" + Status + '\'' +
                ", borrowedNow=" + borrowedNow +
                ", owner='" + owner + '\'' +
                ", borrower='" + borrower + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", purpose='" + purpose + '\'' +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
