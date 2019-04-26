/**
 * 
 */
package pri.zxw.spring_base.repository;

import pri.zxw.spring_base.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
    User findByUsername(String username);
}
