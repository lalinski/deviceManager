package com.lalin.test.site.blog.mix.one.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by frzhao on 2017/10/16.
 */
@Entity
public class DeviceBorrowList {
    @Id
    @GeneratedValue
    private Integer id;
    private String deviceType;
    private String deviceName;
    private String deviceLabel;
    private String deviceId;
    private String lender;
    private String borrower;
    private Date startTime;
    private Date endTime;
    private Date returnTime;
    private String purpose;
    private String status;
    private Integer duration;
    private Integer overdays;

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getOverdays() {
        return overdays;
    }

    public void setOverdays(Integer overdays) {
        this.overdays = overdays;
    }

    @Override
    public String toString() {
        return "DeviceBorrowList{" +
                "id=" + id +
                ", deviceType='" + deviceType + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceLabel='" + deviceLabel + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", lender='" + lender + '\'' +
                ", borrower='" + borrower + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", returnTime=" + returnTime +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", duration=" + duration +
                ", overdays=" + overdays +
                '}';
    }
}
