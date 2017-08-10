package cn.zhanghl.pojo.menu;

/**
 * 子菜单公共属性封装
 * 子菜单包括两项 一种是一级菜单但不包含二级菜单  一种是二级菜单
 *   "type":"click",
     "name":"今日歌曲",
     "key":"V1001_TODAY_MUSIC"
     },
 *   "name":"菜单",
     "sub_button":[
     {
     "type":"view",
     "name":"搜索",
     "url":"http://www.soso.com/"
 },
 */

public class CommonButton extends Button{
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
