package cn.zhanghl.service;

import cn.zhanghl.aes.AesException;
import cn.zhanghl.pojo.message.TextMessage;
import cn.zhanghl.util.MessageUtil;
import cn.zhanghl.util.XMLUtil;
import org.xml.sax.SAXException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * 核心业务处理service
 */
public class CoreService {
    public static String receviceMsg = null;
    public static void ProcessRequset(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        //回复的消息XML
        String respXml = null;
        //回复的content
        String content = null;
        try{
            //判断是明文模式还是密文模式
            cn.zhanghl.service.MessageService ms = new cn.zhanghl.service.MessageService();
            //判断是否加密 如果加密得到的是解密后的明文 如果没有加密得到的就是明文
            String res = ms.decryptMsg(request);

            //将XML转化为Map
            Map<String,String> map = XMLUtil.getXMLToMap(res);
            String toUserName = map.get("FromUserName");
            String FromUserName = map.get("ToUserName");
            //获得用户的消息类型 是消息类型
            String msgType = map.get("MsgType");

            //将回复文本信息封装到实体类
            TextMessage text = new TextMessage();
            text.setToUserName(toUserName);
            text.setFromUserName(FromUserName);
            text.setCreateTime(map.get("CreateTime"));
            text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            //对微信服务器发来的请求做判断 继而进行不同的业务逻辑
            //先对消息类型做判断
            //文本消息
            if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
                //调用回复消息内容函数 获取回复Content
               content = ms.processTextMsg(map.get("Content"),map.get("FromUserName"));

            }
            //图片消息
            else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
                content ="tupian";
            }
            //视频消息 图片消息。。。。。

            //事件推送
            else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
                MenuService menuService = new MenuService();
                //先判断是哪种事件类型
                String eventType = map.get("Event");
                //如果是自定义菜单点击事件
                if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
                    String key = map.get("EventKey");
                    //调用MenuService的processClickEvent方法
                    content = menuService.processClickEvent(key,map.get("FromUserName"));
                }

            }
            //设置回复内容
            text.setContent(content);
            //将信息添加到xml中去
            respXml = ms.formatXml(text);
            //判断是否加密
            if(ms.isEncryptMsg()){
                //加密的话就进行解密
                respXml =  ms.EncryptMsg(respXml);
            }
            System.out.println(respXml);

            //回复微信服务器
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(respXml);
            response.getWriter().flush();


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (AesException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
