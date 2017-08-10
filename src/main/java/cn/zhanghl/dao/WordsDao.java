package cn.zhanghl.dao;

import cn.zhanghl.pojo.mysql.Token;
import cn.zhanghl.pojo.mysql.Words;
import cn.zhanghl.util.DBUtil;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * 词汇处理Dao
 */
public class WordsDao {

    public void addWors(Words words) {
        Connection conn = DBUtil.getConn();
        String sql = "insert into words (word_one,word_two,create_time,status) values(?,?,?,?)";
        PreparedStatement pst = null;
        // 定义一个list用于接受数据库查询到的内容
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, words.getWordOne());
            pst.setString(2, words.getWordTwo());
            pst.setLong(3, words.getCreateTime());
            pst.setInt(4, words.getStatus());
            pst.executeUpdate();
            DBUtil.close(pst);
            DBUtil.close(conn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机数获取词汇
     * @param id
     * @return
     */
    public Words getWords(int id){
        Connection conn = DBUtil.getConn();
        String sql = "select * from words where id = " + id;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Words wd = new Words();
        // 定义一个list用于接受数据库查询到的内容
        try {
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
               wd.setWordOne(rs.getString("word_one"));
               wd.setWordTwo(rs.getString("word_two"));
            }

            DBUtil.close(rs);
            DBUtil.close(preparedStatement);
            DBUtil.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wd;
    }
}
