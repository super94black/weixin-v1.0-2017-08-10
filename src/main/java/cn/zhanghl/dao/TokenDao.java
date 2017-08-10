package cn.zhanghl.dao;

import cn.zhanghl.pojo.mysql.Token;
import cn.zhanghl.util.DBUtil;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;


/**
 * token Dao层
 */
public class TokenDao {
    public Token getAccessToken(){
        Connection conn = DBUtil.getConn();
        String sql = "select * from token where status = '"  + "1 ' and id = (select max(id) from token)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Token token = new Token();
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                token.setId(rs.getInt("id"));
                token.setAccessToken(rs.getString("access_token"));
                token.setCreateTime(rs.getLong("create_time"));
                token.setExpiresIn(rs.getInt("expires_in"));
                token.setStatus(rs.getInt("status"));
            }

            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 更新access_token
     * @param token
     */
    public void insertToken(Token token){
        Connection conn = DBUtil.getConn();
        String sql = "insert into token (access_token,expires_in,create_time,status) values(?,?,?,?)";
        PreparedStatement pst = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1,token.getAccessToken());
            pst.setInt(2,token.getExpiresIn());
            pst.setLong(3,token.getCreateTime());
            pst.setInt(4,token.getStatus());
            pst.executeUpdate();
            DBUtil.close(pst);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
