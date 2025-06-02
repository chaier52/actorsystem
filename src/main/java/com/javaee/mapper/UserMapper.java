package com.javaee.mapper;

import com.javaee.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> listAllUsers();
    
    User getUserById(@Param("userId") Integer userId);
    
    void insertUser(User user);
    
    void updateUser(User user);
    
    void updateUserPassword(User user);
    
    void deleteUser(@Param("userId") Integer userId);
    
    void updatePassword(User user);
    
    User selectUserByUsername(@Param("username") String username);
}