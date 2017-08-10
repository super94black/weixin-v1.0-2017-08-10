package cn.zhanghl.dao;

import cn.zhanghl.pojo.mysql.RoomPlay;
import cn.zhanghl.pojo.mysql.Token;
import cn.zhanghl.util.DBUtil;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * room_play表 DAO层
 */
public class RoomPlayDao {
    public void insertRoomPlay(RoomPlay roomPlay){
        Connection conn = DBUtil.getConn();
        String sql = "insert into room_play (room_id,identify,word,number,expries_time) values(?,?,?,?,?)";
        PreparedStatement pst = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,roomPlay.getRoomId());
            pst.setInt(2,roomPlay.getIdentity());
            pst.setString(3,roomPlay.getWord());
            pst.setInt(4,roomPlay.getNumber());
            pst.setLong(5,roomPlay.getExpriesTime());
            pst.executeUpdate();
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RoomPlay> getPlayIdIsNullByRoomId(int roomId){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room_play where room_id = ? and play_id is NULL and expries_time > " + System.currentTimeMillis();
        PreparedStatement pst = null;
        List<RoomPlay> list = new ArrayList<>();
        ResultSet rs = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,roomId);
            rs = pst.executeQuery();
            while (rs.next()){
                RoomPlay rp = new RoomPlay();
                rp.setId(rs.getInt("id"));
                rp.setRoomId(rs.getInt("room_id"));
                rp.setWord(rs.getString("word"));
                rp.setIdentity(rs.getInt("identify"));
                rp.setNumber(rs.getInt("number"));
                rp.setExpriesTime(rs.getLong("expries_time"));
                list.add(rp);
            }
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 更新play_id
     * @param playId
     * @param id
     */
    public void updatePlayIdById(String playId ,int id){
        Connection conn = DBUtil.getConn();
        String sql = "update room_play set play_id = ? where id = ?";
        PreparedStatement pst = null;
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,playId);
            pst.setInt(2,id);
            pst.executeUpdate();
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 作废当前房间
     * @param id
     */
    public void updateExpriesTime(int id) {
        Connection conn = DBUtil.getConn();
        String sql = "update room_play set expries_time = ? where id = ?";
        PreparedStatement pst = null;
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setLong(1,System.currentTimeMillis() - 1800 * 1000);
            pst.setInt(2,id);
            pst.executeUpdate();
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RoomPlay> getRoomPlay(int roomId){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room_play where room_id = ? and expries_time > " + System.currentTimeMillis();
        PreparedStatement pst = null;
        List<RoomPlay> list = new ArrayList<>();
        ResultSet rs = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setInt(1,roomId);
            rs = pst.executeQuery();
            while (rs.next()){
                RoomPlay rp = new RoomPlay();
                rp.setId(rs.getInt("id"));
                rp.setExpriesTime(rs.getLong("expries_time"));
                list.add(rp);
            }
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
