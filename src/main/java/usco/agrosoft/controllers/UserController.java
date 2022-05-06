package usco.agrosoft.controllers;
import usco.agrosoft.models.User;
import usco.agrosoft.dao.UserDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import usco.agrosoft.utils.JWTUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "api/users",  method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public String registerUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        user.setTokenUser(UUID.randomUUID().toString());
        user.setEnrollmentDate(LocalDateTime.now());
        return userDao.register(user);
    }
}
