package pri.zxw.spring_base.config;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    /**
     * 轮询
     * @return
     */
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

//    /**
//     * 随机
//     * @return
//     */
//    @Bean
//    public IRule ribbonRule() {
//        return new RandomRule();
//    }
//
//    /**
//     * 随机
//     * @return
//     */
//    @Bean
//    public ILoadBalancer ribbonLoadBalancer() {
//        return new ILoadBalancer();
//    }
}
