package usco.agrosoft.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import usco.agrosoft.models.User;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class UserDaoImplement implements UserDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<User> getUsers() {
        String query = "FROM User u";
        List<User> result = entityManager.createQuery(query).getResultList();
        System.out.println(result.size());
        return result;
    }

    @Override
    public String register(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> list = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();
        System.out.println(list.size());
        System.out.println(list);
        if (list.isEmpty()) {
            entityManager.merge(user);
            return "User registered successfully";
        }
        return "User already exists";
    }
    @Override
    public User login(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> list = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();
        if (list.isEmpty()) {
            return null;
        }
        String passwordHashed = list.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, user.getPassword())) {
            return list.get(0);
        }
        return null;
    }
}
