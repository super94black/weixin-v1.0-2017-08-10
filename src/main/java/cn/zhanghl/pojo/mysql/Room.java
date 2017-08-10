package cn.zhanghl.pojo.mysql;

import java.util.Map;

public class Room {
    private int id;
    private String openId;//房主id
    private int roomNumber;//房间号
    private int number;//房间人数
    private long expriesTime;//过期时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public long getExpriesTime() {
        return expriesTime;
    }

    public void setExpriesTime(long expriesTime) {
        this.expriesTime = expriesTime;
    }
}
