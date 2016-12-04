package com.fighthard.dtodomain.dto;

/**
 * @author plz
 * @ClassName UserDto
 * @Description DTO of User.
 * @date 2015年5月19日 下午3:55:24
 */
public class UserDto {

    private String uid;
    private String name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
