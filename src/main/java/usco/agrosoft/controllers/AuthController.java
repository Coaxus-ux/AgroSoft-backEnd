package usco.agrosoft.controllers;
import usco.agrosoft.utils.JWTUtil;
import usco.agrosoft.models.User;
import usco.agrosoft.dao.UserDao;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class AuthController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.GET)
    public String login(@RequestBody User user) {
        User userLogged = userDao.login(user);
        if (userLogged != null) {
            return jwtUtil.create(String.valueOf(userLogged.getIdUser()), userLogged.getEmail());
        }
        return "User not found";
    }
}