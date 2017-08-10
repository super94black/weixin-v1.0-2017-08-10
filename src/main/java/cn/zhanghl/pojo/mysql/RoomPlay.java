package cn.zhanghl.pojo.mysql;

/**
 * 房间--玩家关联模型
 */
public class RoomPlay {
    private int id;
    private String playId;
    private int roomId;//注意是room表的Id
    private int identity;//身份 1平民 2卧底
    private int number;//玩家号码
    private String word;//玩家词汇
    private long expriesTime;//过期时间

    public long getExpriesTime() {
        return expriesTime;
    }

    public void setExpriesTime(long expriesTime) {
        this.expriesTime = expriesTime;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
