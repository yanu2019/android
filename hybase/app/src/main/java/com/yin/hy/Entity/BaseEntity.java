package com.yin.hy.Entity;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;

public class BaseEntity extends LitePalSupport {
    protected int id;
    protected String operateTime;

    @Override
    public boolean save() {
        this.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()).toString());
        return super.save();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
