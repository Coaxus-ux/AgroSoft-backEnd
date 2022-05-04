package usco.agrosoft.dao;
import usco.agrosoft.models.User;
import java.util.*;

public interface UserDao {
    List<User> getUsers();
    void register(User user);

}
