package com.youye.model.user;

/**
 * **********************************************
 * <p/>
 * Date: 2018-09-14 09:16
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 用户基本信息修改页面的实体类
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class UserModifyVO {

    private Long userId;
    private String nickname;
    private Integer age;
    private Integer height;
    private Integer sex;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
