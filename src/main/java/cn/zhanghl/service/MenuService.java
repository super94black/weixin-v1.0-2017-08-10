package cn.zhanghl.service;

import cn.zhanghl.pojo.menu.Button;
import cn.zhanghl.pojo.menu.CommonButton;
import cn.zhanghl.pojo.menu.ComplexButton;
import cn.zhanghl.pojo.menu.Menu;
import cn.zhanghl.util.Const;
import net.sf.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
/**
 * 菜单服务类
 *
 */
public class MenuService {
    public static void main(String args[]){
        GetInterfaceService gs = new GetInterfaceService();
        String accessToken = gs.getAccessToken();
        try {
            createMenu(getMenu(),accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 组装菜单数据
     * @return
     */
    private static Menu getMenu(){
        CommonButton btn11 = new CommonButton();
        btn11.setName("狼人杀");
        btn11.setType("click");
        btn11.setKey("11");

        CommonButton btn12 = new CommonButton();
        btn12.setName("谁是卧底");
        btn12.setType("click");
        btn12.setKey("12");

        //设置第一个一级菜单
        ComplexButton complexButton = new ComplexButton();
        complexButton.setName("游戏");
        complexButton.setSub_button(new CommonButton[]{btn11,btn12});

        //设置第二个一级菜单的子菜单
        CommonButton btn21 = new CommonButton();
        btn21.setName("张瀚林");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22 = new CommonButton();
        btn22.setName("是");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23 = new CommonButton();
        btn23.setName("大帅比");
        btn23.setType("click");
        btn23.setKey("23");

        ComplexButton complexButton1 = new ComplexButton();
        complexButton1.setName("点我");
        complexButton1.setSub_button(new CommonButton[] {btn21,btn22,btn23});

        CommonButton btn3 = new CommonButton();
        btn3.setName("测试");
        btn3.setType("click");
        btn3.setKey("3");

        //封装整个菜单
        Menu menu = new Menu();
        menu.setButton(new Button[] {complexButton,complexButton1,btn3});
        return menu;
    }
    public static void createMenu(Menu menu,String accessToken) throws IOException {
        String strJson = JSONObject.fromObject(menu).toString();
        String path =  "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;

        URL url = new URL(path);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
        writer.write(strJson);
        writer.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        writer.close();
        System.out.println(sb.toString());

    }

    public String processClickEvent(String key,String openId){
        //如果是第一个按钮的第一个子按钮
        MenuServiceVo vo = new MenuServiceVo();
        String content = "";
        if(Const.BUTTON_ONE_TWO.equals(key)){
            //调用谁是卧底的业务逻辑
            content = vo.processWhoIsCaunche(openId);
        }else{
            content = "该功能尚未完成,敬请期待!";
        }
        return content;
    }
}
