/**
 * 
 */
package pri.zxw.spring_base.repository;

import pri.zxw.spring_base.base.DAOInterface;
import pri.zxw.spring_base.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DAOInterface<User>{
    User findByUsername(String username);
}
