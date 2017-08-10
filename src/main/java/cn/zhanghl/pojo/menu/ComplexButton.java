package cn.zhanghl.pojo.menu;

/**
 * 对包含有二级菜单的一级菜单的封装
 * 该类菜单有两个属性 name sub_button
 * 其中sub_button 就以数组形式展现
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
