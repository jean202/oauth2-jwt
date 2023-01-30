package com.exercise.oauth2jwt.repository;

import com.exercise.oauth2jwt.model.Users;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private Map<String,Object> users = new HashMap<String, Object>();

    public Users findByUsername(String username){
        if(users.containsKey(username)){
            return (Users)users.get(username);
        }
        return null;
    }
    public void register(Users user){
        if(users.containsKey(user.getUsername())){
            return;
        }
        users.put(user.getUsername(),user);
    }
}