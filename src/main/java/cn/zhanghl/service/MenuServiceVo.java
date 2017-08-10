package cn.zhanghl.service;

import cn.zhanghl.component.JedisClient;
import cn.zhanghl.dao.RoomDao;
import cn.zhanghl.pojo.mysql.Room;
import cn.zhanghl.util.Const;

import java.util.List;

/**
 * 对菜单的各个按钮的业务逻辑处理类
 */
public class MenuServiceVo {

    /**
     * 处理用户创建房间
     * @param openId
     * @return
     */
    public String processWhoIsCaunche(String openId){
        String content = "";
        RoomDao rd = new RoomDao();
        //先判断是不是该用户已经被标记要创建房间了
        List<Room> list = rd.getList(openId);
        //判断用户是不是有有标记过 但是没有输入数字进行创建房间 但仍然点击创建房间的按钮操作
        int id = 0;
        //如果用户没有有效时间内的标记 就直接标记上
        if(list.isEmpty()){
            //把openid存入room表 先做标记表示此Id需要创建房间
            id = rd.insertRoomMaster(openId);
        }else {
            //如果用户有 有效时间的标记 就看在有效标记的list中存不存在roomid为空的
            // 存在就不为他添加新的记录 最多让法官保证有一个空房间
            int emptyRoom = 0;
            Room r = new Room();
            for (Room room: list) {
                if(room.getRoomNumber() == 0){
                    r = room;
                    emptyRoom++;
                }
            }
            if(emptyRoom == 0){
                id = rd.insertRoomMaster(openId);
            }else{

                //获取id
                id = r.getId();
                //更新过期时间
                rd.updateExpriesTime(id);
            }
        }
        JedisClient js = new JedisClient();
        js.set(openId, String.valueOf(id));
        js.expire(openId,Const.EXPRIES_TIME);
        content = "正在创建谁是卧底,请输入游戏人数(4-13之间，不包括法官哦)";
        return content;
    }
}
