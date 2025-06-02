package com.javaee.service;

import com.javaee.po.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    List<User> getAllUsers();
    User getUserById(Integer userId);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Integer userId);
    boolean modifyPassword(Integer userId, String oldPassword, String newPassword);
    User selectUserByUsername(String username);
    void updatepassword(User user);
}
