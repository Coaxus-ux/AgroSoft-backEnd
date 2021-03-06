package usco.agrosoft.dao;
import usco.agrosoft.models.User;
import java.util.*;

public interface UserDao {
    List<User> getUsers();
    String register(User user);

    User login(User user);
}
