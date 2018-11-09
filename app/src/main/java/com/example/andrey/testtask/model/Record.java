package com.example.andrey.testtask.model;

public class Record {
    private String mContext;
    private long mDate;//current date record in milliseconds
    private String mUserId;

    public Record() {

    }

    public Record(String pContext, long pDate, String pUserId) {
        mContext = pContext;
        mDate = pDate;
        mUserId = pUserId;
    }

    public String getContext() {
        return mContext;
    }

    public void setContext(String pContext) {
        mContext = pContext;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long pDate) {
        mDate = pDate;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String pUserId) {
        mUserId = pUserId;
    }
}
