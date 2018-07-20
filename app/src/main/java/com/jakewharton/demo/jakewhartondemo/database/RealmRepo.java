package com.jakewharton.demo.jakewhartondemo.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmRepo extends RealmObject {

    @PrimaryKey
    int id;
    int pageNo;
    long timeStamp;
    String repoData;

    public RealmRepo() {

    }

    public RealmRepo(int id, int pageNo, long timeStamp, String repoData) {
        this.id = id;
        this.pageNo = pageNo;
        this.timeStamp = timeStamp;
        this.repoData = repoData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRepoData() {
        return repoData;
    }

    public void setRepoData(String repoData) {
        this.repoData = repoData;
    }

    @Override
    public String toString() {
        return "RealmRepo{" +
                "id=" + id +
                ", pageNo=" + pageNo +
                ", timeStamp=" + timeStamp +
                ", repoData='" + repoData + '\'' +
                '}';
    }
}
