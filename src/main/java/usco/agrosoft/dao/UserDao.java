package usco.agrosoft.dao;
import usco.agrosoft.models.User;

import java.io.UnsupportedEncodingException;
import java.util.*;

public interface UserDao {
    List<User> getUsers();
    String register(User user) throws UnsupportedEncodingException;

    User login(User user);
    String activate(String token);
}
