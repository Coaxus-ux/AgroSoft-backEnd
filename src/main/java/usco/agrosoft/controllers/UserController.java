package usco.agrosoft.controllers;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usco.agrosoft.dao.UserDao;
import usco.agrosoft.models.User;
import usco.agrosoft.utils.TestService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EnableEmailTools
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "api/users",  method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.getUsers();
    }


    @Autowired
    private TestService testService;
    @RequestMapping(value = "api/register", method = RequestMethod.POST)
    public String registerUser(@RequestBody User user) throws UnsupportedEncodingException {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        user.setTokenUser(UUID.randomUUID().toString());
        user.setEnrollmentDate(LocalDateTime.now());
        return userDao.register(user);
    }

    @RequestMapping(value="api/activate/{token}", method = RequestMethod.GET)
    public String activateAccount(@PathVariable String token){
        System.out.println(token);
        return token;
    }

    /*@Autowired
    private TestService testService;

    @PostConstruct
    @RequestMapping(value = "api/mail")
    public String sendMail() throws UnsupportedEncodingException {
        String ola = "kaak";
        testService.sendEmail("ortix01y@gmail.com", "Robinson Zambrano", "Activa tu cuenta", ola);
        return "Enviado 1";
    }*/


}
