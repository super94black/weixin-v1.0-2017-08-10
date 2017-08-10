package cn.zhanghl.service;

import cn.zhanghl.dao.TokenDao;
import cn.zhanghl.pojo.mysql.Token;
import cn.zhanghl.util.Const;
import cn.zhanghl.util.CurlUtil;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

/**
 * 调用微信接口服务类
 */
public class GetInterfaceService {

    public static String accessToken = null;
    public static long lastTime = 0;
    public static long nowTime = 0;
    /**
     * 调用微信接口获得AccessToken
     * @return
     */
    public Map<String,Object> curlAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("secret",Const.AppSecret);
        params.put("appid", Const.AppId);

        String json = CurlUtil.getContent(url,params,"GET");
        JSONObject jsonObject = new JSONObject(json);
        Map<String,Object> map = new HashMap<>();
        String accessToken = jsonObject.getString("access_token");
        int expiresIn = jsonObject.getInt("expires_in");
        map.put("accessToken",accessToken);
        map.put("expiresIn",expiresIn);
        return map;
    }

     public String getAccessToken(){
        TokenDao dao = new TokenDao();
        Token token = dao.getAccessToken();
        Map<String,Object> map = new HashMap<>();
        //判断token是否为空
        if(token.getAccessToken() == null || token.getAccessToken().equals("")){
            map = this.curlAccessToken();
            //把内容设置到Token实体
            token = this.RsetToken(map,token);
            //插入数据库字段
            dao.insertToken(token);
            accessToken = token.getAccessToken();
            System.out.println(accessToken);
            return accessToken;
        }else{
            //判断token是否过期
            nowTime = System.currentTimeMillis();
            lastTime = token.getCreateTime();
            //token过期就重新获取
            synchronized(this){
                if(nowTime - lastTime >= token.getExpiresIn() * 1000){
                //重新新获取token
                map = this.curlAccessToken();
                token = this.RsetToken(map,token);
                dao.insertToken(token);
                return  token.getAccessToken();
            }else{
                return dao.getAccessToken().getAccessToken();
                }
            }
        }


    }

    public Token RsetToken(Map<String,Object> map,Token token){
        String accessToken = (String) map.get("accessToken");
        int expiresIn = (int) map.get("expiresIn");
        //执行更新操作
        token.setAccessToken(accessToken);
        token.setExpiresIn(expiresIn);
        token.setCreateTime(System.currentTimeMillis());
        token.setStatus(1);
        return token;
    }
}
