package pri.zxw.spring_base.service.impl;

import pri.zxw.spring_base.base.BaseService;
import pri.zxw.spring_base.base.DAOInterface;
import pri.zxw.spring_base.model.User;
import pri.zxw.spring_base.repository.UserRepository;
import pri.zxw.spring_base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Administrator on 8/14 0014.
 */
@Transactional
@Service
public class UserServiceImpl  extends BaseService<User> implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected DAOInterface<User> getDAOInterface() {
        return userRepository;
    }
}
