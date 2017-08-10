package cn.zhanghl.service;

import cn.zhanghl.aes.AesException;
import cn.zhanghl.aes.WXBizMsgCrypt;
import cn.zhanghl.component.JedisClient;
import cn.zhanghl.dao.RoomDao;
import cn.zhanghl.dao.RoomPlayDao;
import cn.zhanghl.dao.WordsDao;
import cn.zhanghl.pojo.message.TextMessage;
import cn.zhanghl.pojo.mysql.Room;
import cn.zhanghl.pojo.mysql.RoomPlay;
import cn.zhanghl.pojo.mysql.Words;
import cn.zhanghl.util.Const;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息业务处理实现类
 */
public class MessageService {
    public static String token = Const.Token;
    public static String encodingAesKey = Const.EncodingAESKey;
    public static String appId = Const.AppId;
    public static final String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
    public static String encrypt = null;
    public static String timeStamp = null;
    public static String nonce = null;
    public JedisClient js = null;
    public RoomDao rd = null;
    /**
     * 解密收到的信息
     * @param request
     * @return
     * @throws IOException
     * @throws AesException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public String decryptMsg(HttpServletRequest request) throws IOException, AesException, SAXException, ParserConfigurationException {
        if(!this.isEncryptMsg(request)){
            request.setCharacterEncoding("UTF-8");
            InputStream inputStream = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            inputStream.close();
            return sb.toString();
        }
        //如果加密直接返回

        //否则进行解密
        //读取密文签名
        String msgSignature = request.getParameter("msg_signature");
        //读取时间戳
        timeStamp = request.getParameter("timestamp");
        //读取随机数
        nonce = request.getParameter("nonce");
        //进行解密
        InputStream inputStream = request.getInputStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(inputStream);

        Element root = document.getDocumentElement();
        NodeList nodeList2 = root.getElementsByTagName("ToUserName");
        String toUserName = nodeList2.item(0).getTextContent();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        String encrypt = nodelist1.item(0).getTextContent();

        String format = "<xml><ToUserName><![CDATA[%s]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
        String postData = String.format(format,toUserName, encrypt);
        //调用解密函数
        WXBizMsgCrypt wx = new WXBizMsgCrypt(token,encodingAesKey,appId);
        String res = wx.decryptMsg(msgSignature,timeStamp,nonce,postData);
        inputStream.close();
        return res;
    }


    /**
     * 加密回复消息
     * @param repMsg
     * @return fromXml
     * @throws AesException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public String EncryptMsg(String repMsg) throws AesException, ParserConfigurationException, IOException, SAXException {
        String timestamp = System.currentTimeMillis() + "";
        String nonce = "zxcvbnm";
        WXBizMsgCrypt wx = new WXBizMsgCrypt(token,encodingAesKey,appId);
        String res = wx.encryptMsg(repMsg, timestamp, nonce);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(res);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
        String encrypt = nodelist1.item(0).getTextContent();

        String fromXML = String.format(format, encrypt);
        return fromXML;
    }

    /**将回复信息转化成XML格式
     * 明文格式
     * @param text
     * @return
     */
    public String formatXml(TextMessage text){
        String xml =
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>";
        String respXml = String.format(xml,text.getToUserName(),text.getFromUserName(),text.getCreateTime(),
                text.getMsgType(),text.getContent());
        return respXml;
    }

    /**
     * 判断是否加密
     * @param request
     * @return
     */
    public boolean isEncryptMsg(HttpServletRequest request){
        encrypt = request.getParameter("encrypt_type");
        System.out.println(encrypt);
        if("aes".equals(encrypt)){
            return true;
        }
        return false;
    }

    public boolean isEncryptMsg(){
        if(encrypt == null || encrypt.equals("raw")){
            return false;
        }
        return true;
    }

    /**
     * 文本消息处理函数
     * @param content
     * @param openId
     * @return
     */
    public String processTextMsg(String content,String openId){
        //对发来的内容做判断
        js = new JedisClient();
       //先验证是不是数字信息
        if (this.isAllNum(content)){
            //如果是纯数字信息 先验证时4-13
            int num  = Integer.parseInt(content);
            if(num >= 4 && num <= 13){
                //验证是不是房主
                if(js.get(openId) != null){
                    //如果是房主则验证是不是上次的申请创建完毕了
                    if(!this.isReCreateRoom(openId)){
                        //如果在创建完成后的房间输入的 4-13 即已经输入4-13建房成功后再次输入
                        //判断是不是输入的6
                        if(num == 6){
                            //回复惩罚信息
                            return "惩罚信息";
                        }else{
                            //否则回复游戏规则
                            return this.getGameRule();
                        }
                    }
                    else{
                        //此时说明房主已经点击申请新房间了 然后才输入的4-13
                        //执行创建房间逻辑
                        return this.createRoom(num,openId);
                    }
                }
                else{
                    //不是房主的话 回复游戏规则
                    return this.getGameRule();
                }
            }else if(num >= 4000 && num <= 5000){
                //如果是房主 无论是不是已经建房完毕 直接判断是不是自己的房间
                if(js.get(openId) != null){
                    if(this.isMyselfRoom(num,openId)){
                        //如果是自己创建的就回复 masterInfo
                        return js.get(openId+num);
                    }else{
                        //如果这个房间不是自己创建的就执行加入房间逻辑
                        if(this.isExsistRoom(num)){
                            //该用户房主身份取消
                            js.decr(openId);
                            return this.joinRoom(num,openId);
                        }else{
                            return this.roomExpriesTimeOver();
                        }

                    }
                }else{
                    //如果不是房主 就看有没有这个房间
                    if(this.isExsistRoom(num)){
                        //执行加入房间逻辑
                        return this.joinRoom(num,openId);
                    }else{
                        //回复房间时间过期
                        return this.roomExpriesTimeOver();
                    }

                }
            }
        }else{
            //如果输入的是文字信息就确定是不是 换。改
            if("换".equals(content) || "改".equals(content)){
                //如果是不是房主身份
                if(js.get(openId) != null){
                   //看有没有已经重新创建房间
                    if(this.isReCreateRoom(openId)){
                        //如果已经申请了重建房间 但是还没有输入4-13 就是非法输入
                        return this.crateRoomIllegalInput();
                    }else{
                        //此时说明该房主需要重新玩一局
                        return this.reStartGame(openId);
                    }
                }else{
                    //不是房主的话就是非法输入
                    return this.roomExpriesTimeOver();
                }
            }else{
                //判断是不是房主
                if(js.get(openId) != null){
                    //判断是不是刚刚新申请创建了房间
                    if(this.isReCreateRoom(openId)){
                        //如果刚刚新申请创建房间 就回复必须是4-13的数字
                        return this.crateRoomIllegalInput();
                    }else{
                        //回复游戏规则
                        return this.getGameRule();
                        
                    }
                }else{
                    //如果不是房主直接回复游戏规
                    return this.getGameRule();
                }
            }
        }
        return "非法输入";
    }

    /**
     * 创建房间
     * @param number
     * @param openId
     * @return
     */
    public String createRoom(int number,String openId){
        js = new JedisClient();
        //获取该房主已经创建好的房间id
        int id = Integer.parseInt(js.get(openId));
        //随机数获得房间号
        //随机数和数据库房间号做比对 如果已经存在 就跳过在获取
        int roomNuber = this.getRandomRoomNumber();
        //随机获取关键词
        //如果不存在就直接存入数据库
        Room room = new Room();
        room.setId(id);
        room.setOpenId(openId);
        //设置房间人数
        room.setNumber(number);
        //设置房间号
        room.setRoomNumber(roomNuber);
        //更新room表
        rd = new RoomDao();
        rd.update(room);
        //随机得出卧底号码
        //向room_play表插入number条记录
        Words words = this.getWords();
        Map<Integer,Integer> map = this.insertRoomPlay(number,id,words,openId);
        //得出卧底有几个人 以及卧底分别是多少号
        //把房主记录play表记录房主当前房间号

       String content = this.getReplyMsg(roomNuber,number,words,openId,map);
        return content;
    }

    /**
     * 获取房间创建完成后的各种信息
     * @param roomNuber
     * @param number
     * @param words
     * @param openId
     * @param map
     * @return
     */
    public String getReplyMsg(int roomNuber,int number,Words words,String openId, Map<Integer,Integer> map){
        String underConverNum = "";
        int count = 0;
        for (Integer i: map.keySet()) {
            underConverNum += i + "号 ";
            count++;
        }

        String playInfo = "配置: " + count + "个卧底，" + (number - count) + "个平民";


        String content = "建房成功! 您是法官，请让参与游戏的玩家对我回复[" + roomNuber +"]进入房间。\n" +
                "房间：" + roomNuber  + "\n" +
                playInfo +"\n" +
                "卧底词: " + words.getWordTwo() + "\n" +
                "平民词: " + words.getWordOne() + "\n" +
                "卧底: " + underConverNum;


        String masterInfo = "您是法官，请让参与游戏的玩家对我回复[" + roomNuber +"]进入房间。\n" +
                "房间：" + roomNuber  + "\n" +
                playInfo + " \n" +
                "卧底词: " + words.getWordTwo() + "\n" +
                "平民词: " + words.getWordOne() + "\n" +
                "卧底: " + underConverNum;

        String key = openId + roomNuber;

        js.set(key,masterInfo);//房主建房成功时的信息
        js.expire(key,Const.EXPRIES_TIME);

        String peizhiInfo = roomNuber + "peizhiInfo";
        js.set(peizhiInfo,playInfo);//玩家配置信息
        js.expire(peizhiInfo,Const.EXPRIES_TIME);


        return content;
    }
    /**
     * 随机出房间号
     * @return number
     */
    public int getRandomRoomNumber(){
        int max=5000;
        int min=4000;
        Random random = new Random();
        int number = random.nextInt(max)%(max-min+1) + min;
        RoomDao rd = new RoomDao();
        List<Room> list = rd.getRoomNumber();
        boolean flag = true;
        while(flag){
            int count = 0;
            for (Room room: list) {
                if(room.getNumber() == number){
                    count++;
                    break;
                }
            }
            if(count == 0){
                flag = false;
            }else{
                number = random.nextInt(max)%(max-min+1) + min;
            }
        }
        return number;
    }

    /**
     * 随机获取词汇
     * @return
     */
    public Words getWords(){
        int max=216;
        int min=1;
        Random random = new Random();
        int id = random.nextInt(max)%(max-min+1) + min;
        WordsDao wd = new WordsDao();
        Words words = wd.getWords(id);
        return words;
    }

    /**
     * 创建房间基本信息
     * 返回卧底号码
     * @param number
     * @param roomId
     * @param words
     * @return map
     */
    public Map<Integer, Integer> insertRoomPlay(int number,int roomId,Words words,String openId){
        //按照1/3的卧底概率
        int underCoverCount = number / 3;
        int max = number;
        int min = 1;
        Date date = new Date();
        long timeMill = date.getTime();
        Random random = new Random(timeMill);
        Map<Integer,Integer> map = new HashMap<>();
        //得到卧底的号码
        for(int i = 0; i < underCoverCount; i++){
            int num = random.nextInt(max)%(max-min+1) + min;
            map.put(num,num);
        }
        //获取该房间的过期时间
        long expriesTime = this.getRoomExpriesTime(openId);
        //向room_play插入记录
        RoomPlayDao roomPlayDao = new RoomPlayDao();
        for (int j = 1; j <= number; j++){
            boolean flag = true;
            RoomPlay rp = new RoomPlay();
            rp.setRoomId(roomId);
            rp.setNumber(j);
            rp.setExpriesTime(expriesTime);
            //如果是卧底号
            for (Integer i : map.keySet()){
                if(i == j){
                    //身份标识 1 平民 2 卧底
                    //设置身份标识为2
                    rp.setIdentity(2);
                    rp.setWord(words.getWordTwo());
                    roomPlayDao.insertRoomPlay(rp);
                    flag = false;
                    break;
                }else{
                    continue;
                }
            }
           if(flag){
               rp.setIdentity(1);
               rp.setWord(words.getWordOne());
               //向room_play表插入记录
               roomPlayDao.insertRoomPlay(rp);
           }
        }
        return map;
    }

    /**
     * 判断该房主是否已经创建房间完毕
     * 创建完毕则返回false
     * 未创建完毕则返回true
     * @param openId
     * @return
     */
    public boolean isReCreateRoom(String openId){
        rd = new RoomDao();
        List<Room> list = rd.getList(openId);
        for (Room room: list) {
            if(room.getRoomNumber() == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前有效的房间
     * @return
     */
    public List<Room> getAllRoom(){
        rd = new RoomDao();
        List<Room> list = rd.getAllRoom();
        if(list.isEmpty() || list.size() < 1){
            return null;
        }
        return list;
    }

    /**
     *
     * 判断是房主的房间吗
     * @param number
     * @param
     * @return
     */
    public boolean isMyselfRoom(int number,String openId){
        rd = new RoomDao();
        List<Room> list = rd.getList(openId);
        for (Room room : list) {
            if(room.getRoomNumber() == number){
                return true;
            }
            else{
                continue;
            }
        }
        return false;
    }

    /**
     * 判断是不是纯数字
     * @param content
     * @return
     */
    public boolean isAllNum(String content){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(content);
        if(isNum.matches()){
            return true;
        }
        return false;
    }

    /**
     * 判断某个房间是不是存在
     * @param number
     * @return
     */
    public boolean isExsistRoom(int number){
        List<Room> list = this.getAllRoom();
        for (Room room : list) {
            if(room.getRoomNumber() == number){
                return true;
            }
            else{
                continue;
            }
        }
        return false;
    }

    /**
     * 判断房间是否可以加入
     * 可以的话就加入
     * 不可以就返回相应信息
     * @param num
     * @param openId
     * @return
     */

    public String joinRoom(int num,String openId){
        //根据房间号获得room_id
        rd = new RoomDao();
        int roomId = rd.getRoomIdByNumber(num);
        //获取room—_play表中 room_id = id的数据
        RoomPlayDao dao = new RoomPlayDao();
        List<RoomPlay> list = dao.getPlayIdIsNullByRoomId(roomId);
        //如果房间满了返回空值
        if(list.isEmpty() || list.size() == 0){
            return this.roomPlayCountOver();
        }
        RoomPlay playInfo = list.get(0);
        //更新play_id字段
        dao.updatePlayIdById(openId,playInfo.getId());
        return this.replyPlayMsg(num,playInfo);
    }

    /**
     * 获取回复玩家的信息
     * @param num
     * @param roomPlay
     * @return
     */
    public String replyPlayMsg(int num,RoomPlay roomPlay){
        String content = "房间: " + num + "\n" +
                         "词语: " + roomPlay.getWord() + "\n" +
                         "您是: " + roomPlay.getNumber() + "\n" +
                         js.get(String.valueOf(num)) + "\n" +
                         "输了要有惩罚的哦,回复6查看大冒险惩罚!";
        return content;
    }

    /**
     * 房间过期的回复
     * @return
     */
    public String roomExpriesTimeOver(){
        String content = "该房间过期了,请重新寻找房间,或者重新创建一个房间";
        return content;
    }

    /**
     * 创建房间的不合法输入回复
     * @return
     */
    public String crateRoomIllegalInput(){
        String content = "输入的必须是4-13之间的数字哦，请重新输入:";
        return content;
    }

    /**
     * 房间人数满了的回复
     * @return
     */
    public String roomPlayCountOver(){
        String content = "房间满了,请耐心等下一局吧,或者再去找一个房间号";
        return content;
    }

    /**
     * 谁是卧底游戏规则
     * @return
     */
    public String getGameRule(){
        String rule =   "谁是卧底游戏规则: \n" +
                        "游戏有卧底和平民2种身份。\n" +
                        "游戏根据在场人数大部分玩家拿到同一词语，其余玩家拿到与之相关的另一词语。\n" +
                        "每人每轮用一句话描述自己拿到的词语，既不能让卧底察觉，也要给同伴以暗示。\n" +
                        "每轮描述完毕，所有在场的人投票选出怀疑谁是卧底，得票最多的人出局。\n" +
                        "若卧底全部出局，则游戏结束。若卧底未全部出局，游戏继续。并反复此流程。\n" +
                        "若卧底撑到最后一轮（剩余总人数小于卧底初始人数的二倍时），则卧底获胜，反之，则平民胜利。";
        return rule;
    }

    /**
     * 重新开始一局游戏
     * @param openId
     * @return
     */
    public String reStartGame(String openId){
        int id = Integer.parseInt(js.get(openId));//room表中的id
        rd = new RoomDao();
        //把room表里的时间更新
        rd.updateExpriesTime(id);
        //吧redis的时间更新
        js.expire(openId,Const.EXPRIES_TIME);
        //把原来的room_play表里的该房间的时间全部过期
        RoomPlayDao roomPlayDao = new RoomPlayDao();
        List<RoomPlay> list = roomPlayDao.getRoomPlay(id);
        for (RoomPlay roomPlay : list) {
            roomPlayDao.updateExpriesTime(roomPlay.getId());
        }
        //获取当前的房间信息
        Room room = rd.getRoomById(id);

        //执行插入room_play表的逻辑(以插入代表更新)
        //重新获得词汇
        Words words = this.getWords();

        //重新获得卧底的号码
        //将信息插入到room_play表
        Map<Integer,Integer> map = this.insertRoomPlay(room.getNumber(),id,words,openId);

        return this.getReplyMsg(room.getRoomNumber(),room.getNumber(),words,openId,map);
    }

    /**
     * 根据openId获取房间过期时间
     * @param openId
     * @return
     */
    public long getRoomExpriesTime(String openId){
        int id = Integer.parseInt(js.get(openId));
        rd = new RoomDao();
        Room room = rd.getRoomById(id);
        long expriesTime = room.getExpriesTime();
        return expriesTime;
    }

}

