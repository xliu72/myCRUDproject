package com.xingyu.mywebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public void saveUser(User user){
        repo.save(user);

    }

    public User editUser(Integer id) throws UserNotFoundException {

        Optional<User> byId = repo.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new UserNotFoundException(" User not found with the given id: "+id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if(count ==null | count==0){
            throw new UserNotFoundException("User not found when attempt to delete: "+id);
        }
        repo.deleteById(id);

    }
}
