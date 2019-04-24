package pri.zxw.spring_base.hrstrix;

import pri.zxw.spring_base.feign.MFeignClient;
import pri.zxw.spring_base.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HystrixFeignFallback implements MFeignClient {
    @Override
    public User getUser(String id) {
        User user = new User();
        user.setId("233");
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>();
    }
}