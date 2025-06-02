package com.javaee.service.impl;

import com.javaee.mapper.UserMapper;
import com.javaee.po.User;
import com.javaee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired  
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.listAllUsers();
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public boolean addUser(User user) {
        userMapper.insertUser(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        userMapper.updateUser(user);
        return true;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
        return true;
    }

    @Override
    public boolean modifyPassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.getUserById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userMapper.updateUserPassword(user);
            return true;
        }
        return false;
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public void updatepassword(User user) {
        userMapper.updatePassword(user);
    }
}