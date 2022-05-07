package usco.agrosoft.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.beans.factory.annotation.Autowired;
import usco.agrosoft.models.User;
import org.springframework.stereotype.Repository;
import usco.agrosoft.utils.TestService;

import java.io.UnsupportedEncodingException;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;

@EnableEmailTools
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

    @Autowired
    private TestService testService;
    @Override
    public String register(User user) throws UnsupportedEncodingException {
        String query = "FROM User WHERE email = :email";
        List<User> list = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();
        if (list.isEmpty()) {
            entityManager.merge(user);
            String body = "Activa tu cuenta dando click aqui: https://smcac-usco.herokuapp.com/api/activate/" + user.getTokenUser();
            testService.sendEmail(user.getEmail(), user.getName() + user.getLastName(), "Activa tu cuenta Agrosoft", body);
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
    @Override
    public String activate(String token){
        String query = "FROM User WHERE token_user = :token";
        List<User> list = entityManager.createQuery(query)
                .setParameter("token", token)
                .getResultList();
        return "Cuenta activada con exito";
    }
}
