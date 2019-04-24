package pri.zxw.spring_base.controller;

import pri.zxw.spring_base.model.User;
import pri.zxw.spring_base.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public User getuser(@PathVariable String id) {
        User user = null;
        try {
            System.out.println(id);
            user=new User();
            user.setId(id);
            user.setNickName("faegaew");
            user.setPassword("aewgawgaewg");
            user.setUsername("gewgaewg噶为噶为");
//            user = userService.find(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * @return
     */
    @GetMapping("list")
    public List<User> users() {
        try {
            logger.info("calling trace demo backend");
            List<User> users =new ArrayList<>();
            User user19=new User();
            user19.setId("1111");
            user19.setNickName("faegaew");
            user19.setPassword("aewgawgaewg");
            user19.setUsername("gewgaewg噶为噶为");
            users.add(user19);
            User user18=new User();
            user18.setId("2222");
            user18.setNickName("faegaew");
            user18.setPassword("aewgawgaewg");
            user18.setUsername("嘎嘎二五噶为噶为");
            users.add(user18);
            User user17=new User();
            user17.setId("2222");
            user17.setNickName("faegaew");
            user17.setPassword("aewgawgaewg");
            user17.setUsername("v埃尔哇嘎哇嘎个挖嘎尔为");
            users.add(user19);
             // userService.findAll();
            if (users != null && users.size() != 0) {
                return users;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}