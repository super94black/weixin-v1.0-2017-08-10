package cn.zhanghl.dao;

import cn.zhanghl.pojo.mysql.Room;
import cn.zhanghl.util.DBUtil;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 房间Dao层
 */
public class RoomDao {

        public List<Room> getList(String openId){
            Connection conn = DBUtil.getConn();
            String sql = "select * from room where expries_time >" + System.currentTimeMillis() + " and open_id = \'"+ openId + "\'";
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;
            List<Room> list = null;
            // 定义一个list用于接受数据库查询到的内容
            try {
                preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
                rs = preparedStatement.executeQuery();
                list = new ArrayList<>();
                while(rs.next()){
                    Room room = new Room();
                    room.setId(rs.getInt("id"));
                    room.setOpenId(rs.getString("open_id"));
                    room.setRoomNumber(rs.getInt("room_number"));
                    list.add(room);
                }

                DBUtil.close(rs);
                DBUtil.close(preparedStatement);
                DBUtil.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

    public List<Room> getAllRoom(){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room where expries_time >" + System.currentTimeMillis();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Room> list = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while(rs.next()){
                Room room = new Room();
                room.setId(rs.getInt("id"));
                room.setOpenId(rs.getString("open_id"));
                room.setRoomNumber(rs.getInt("room_number"));
                list.add(room);
            }

            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 记录用户openid标记将要创建房间
     * @param openId
     */
    public int insertRoomMaster(String openId){
        Connection conn = DBUtil.getConn();
        String sql = "insert into room (open_id,expries_time) values(?,?)";
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        //原子操作 解决并发
        try {
            synchronized (this){
                pst = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pst.setString(1,openId);
                pst.setLong(2,System.currentTimeMillis() + 1800*1000);
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();

                if(rs.next()){
                    id = rs.getInt(1);
                }
                DBUtil.close(rs);
                DBUtil.close(pst);
                DBUtil.close(conn);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<Room> getRoomNumber(){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room where expries_time >" + System.currentTimeMillis();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Room> rooms = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            rooms = new ArrayList<>();
            while(rs.next()){
                Room room = new Room();
                room.setRoomNumber(rs.getInt("room_number"));
                rooms.add(room);
            }

            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void update(Room room) {
        Connection conn = DBUtil.getConn();
        String sql = "update room set room_number = ? , number = ? where id = ?";
        PreparedStatement preparedStatement = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            preparedStatement.setInt(1,room.getRoomNumber());
            preparedStatement.setInt(2,room.getNumber());
            preparedStatement.setInt(3,room.getId());
            preparedStatement.executeUpdate();
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateExpriesTime(int id) {
        Connection conn = DBUtil.getConn();
        String sql = "update room set expries_time = ? where id = ?";
        PreparedStatement preparedStatement = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            preparedStatement.setLong(1,System.currentTimeMillis() + 1800 * 1000);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRoomIdByNumber(int num){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room where expries_time >" + System.currentTimeMillis() + " and room_number = " + num;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Room room = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            room = new Room();
            if (rs.next()){
                room.setId(rs.getInt("id"));
            }

            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room.getId();
    }

    public Room getRoomById(int id){
        Connection conn = DBUtil.getConn();
        String sql = "select * from room where expries_time >" + System.currentTimeMillis() + " and id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Room room = new Room();
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                room.setId(id);
                room.setRoomNumber(rs.getInt("room_number"));
                room.setOpenId(rs.getString("open_id"));
                room.setNumber(rs.getInt("number"));
                room.setExpriesTime(rs.getLong("expries_time"));
            }
            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }
}

