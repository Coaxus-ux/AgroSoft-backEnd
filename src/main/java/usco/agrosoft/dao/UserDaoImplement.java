package usco.agrosoft.dao;

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
    public void register(User user) {
        entityManager.merge(user);
    }
}
