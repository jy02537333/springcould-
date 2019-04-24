package pri.zxw.spring_base.controller;

import org.springframework.web.bind.annotation.*;
import pri.zxw.spring_base.feign.MFeignClient;
import pri.zxw.spring_base.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api("springcloud consumer user 控制器")
@RequestMapping("user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MFeignClient feignClient;

    /**
     * @param id
     * @return
     */
    @ApiOperation(value = "根据用户id查询用户信息", httpMethod = "GET", produces = "application/json")
    @ApiResponse(code = 200, message = "success", response = User.class)
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public User getUser(@ApiParam(name = "id",   required = true, value = "用户Id") @PathVariable String id) {
        User user= feignClient.getUser(id);
        return user;
    }


    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<User> users(HttpServletRequest request) {
        try {
            logger.info("calling from user provider");
            List<User> users = feignClient.getUsers();
            return users == null ? new ArrayList<User>() : users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}