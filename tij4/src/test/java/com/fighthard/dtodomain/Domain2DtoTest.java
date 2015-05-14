package com.fighthard.dtodomain;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fighthard.dtodomain.annotation.ModelDataObjectUtil;
import com.fighthard.dtodomain.dto.UserDto;
import com.fighthard.dtodomain.model.User;

public class Domain2DtoTest {
    
    private User user;
    private UserDto userDto;
    
    @Before
    public void setUp(){
        user=new User();
        user.setId(10l);
        user.setName("测试用户");
    }
    
    @Test
    public void translateTest(){
        userDto=ModelDataObjectUtil.do2model(user, UserDto.class);
        assertNotNull(userDto);
        assertNotNull(userDto.getUid());
        String uid=userDto.getUid();
        System.out.println(uid);
        
        
        
        User userTransBack=ModelDataObjectUtil.model2do(userDto, User.class);
        assertNotNull(userTransBack);
        assertNotNull(userTransBack.getId());
        assertTrue(user.equals(userTransBack));
        
    }
    
    

}
