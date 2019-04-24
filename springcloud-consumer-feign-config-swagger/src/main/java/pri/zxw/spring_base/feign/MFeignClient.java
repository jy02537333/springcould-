package pri.zxw.spring_base.feign;

import pri.zxw.spring_base.hrstrix.HystrixFeignFallback;
import pri.zxw.spring_base.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "springcloud-provider-config",fallback = HystrixFeignFallback.class, configuration = MFeignConfig.class)
public interface MFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    User getUser(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/user/list")
    List<User> getUsers();
}