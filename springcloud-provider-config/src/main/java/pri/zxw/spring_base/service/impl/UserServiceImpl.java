package pri.zxw.spring_base.service.impl;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pri.zxw.comutil.PageInfo;
import pri.zxw.comutil.entity.ErrorReturn;
import pri.zxw.spring_base.dao.UserMapper;
import pri.zxw.spring_base.model.PageinfoaParam;
import pri.zxw.spring_base.model.User;
import pri.zxw.spring_base.model.UserExample;
import pri.zxw.spring_base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 8/14 0014.
 */
@Service
public class UserServiceImpl  implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    UserMapper dataApiMapper;

    /**
     * 根据条件查询列表
     *
     * @param id 主键查询
     */
    @Override
    public User selectByPrimaryKey(String id) {
        User retEntity = dataApiMapper.selectByPrimaryKey(id);
        logger.info("api数据表  selectByPrimaryKey--------------------------" + retEntity);
        return retEntity;
    }

    /**
     * 根据条件查询列表
     *
     * @param pageInfo 自定义的分页page参数
     */
    @Override
    public com.github.pagehelper.PageInfo selectByExample(PageInfo pageInfo) {
        UserExample example = new UserExample();
        if (pageInfo.getName() != null && pageInfo.getName().trim().length() > 0) {
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+pageInfo.getName()+"%");
        }
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        List<User> list = dataApiMapper.selectByExample(example);
        com.github.pagehelper.PageInfo<User> sPageInfo = new com.github.pagehelper.PageInfo(list);
        logger.info("api数据表  selectByExample--------------------------" + sPageInfo);
        return sPageInfo;
    }

    /**
     * 根据条件查询列表
     *
     * @param pageInfo 自定义的分页page参数
     */
    @Override
    public com.github.pagehelper.PageInfo selectByExample(PageinfoaParam pageInfo) {
        UserExample example = new UserExample();
        if (pageInfo.getName() != null && pageInfo.getName().trim().length() > 0) {
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+pageInfo.getName()+"%");
        }
        example.setOrderByClause("create_time desc");
		PageHelper.startPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        List<User> list = dataApiMapper.selectByExample(example);
        com.github.pagehelper.PageInfo<User> sPageInfo = new com.github.pagehelper.PageInfo(list);
        logger.info("api数据表  selectByExample--------------------------" + sPageInfo);
        return sPageInfo;
    }

    @Override
    public ErrorReturn insert(DataApiEntity record) {
        record.setId(UUID.randomUUID().toString());
        int ret = -1;
        try {
            ret = dataApiMapper.insert(record);
            logger.info("api数据表  insert--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 批量添加
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn inserts(List<DataApiEntity> list) {
        for (DataApiEntity item : list) {
            item.setId(UUID.randomUUID().toString());
        }
        int ret = -1;
        try {
            ret = dataApiMapper.inserts(list);
            logger.info("api数据表  inserts--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret >= 1) {
            return new ErrorReturn(1, "");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 插入特定条件值
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn insertSelective(DataApiEntity record) {
        record.setId(UUID.randomUUID().toString());
        int ret = -1;
        try {
            ret = dataApiMapper.insertSelective(record);
            logger.info("api数据表  insertSelective--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 删除
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn deleteByPrimaryKey(String id) {
        int ret = -1;
        try {
            ret = dataApiMapper.deleteByPrimaryKey(id);
            logger.info("api数据表  deleteByPrimaryKey--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 软删除
     *
     * @param id
     */
    @Override
    public ErrorReturn updateStatusToDelByDataId(String id) {
        int ret = -1;
        try {
//			ret = dataApiMapper.updateStatusToDel(id);
            ret = dataApiMapper.updateStatusToDelByDataId(id);
            logger.info("api数据表  updateStatusToDel--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 软删除
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn updateStatusToDel(String id) {
        int ret = -1;
        try {
            ret = dataApiMapper.updateStatusToDel(id);
            logger.info("api数据表  updateStatusToDel--------------------------" + ret);
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }


    /**
     * 根据主键按照条件更新
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn updateByPrimaryKeySelective(DataApiEntity record) {
        int ret = -1;
        try {
            ret = dataApiMapper.updateByPrimaryKeySelective(record);
            logger.info("api数据表 updateByPrimaryKeySelective--------------------------" + ret);
            if (ret != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

    /**
     * 根据主键全部信息更新
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public ErrorReturn updateByPrimaryKey(DataApiEntity record) {
        int ret = -1;
        try {
            ret = dataApiMapper.updateByPrimaryKey(record);
            logger.info("api数据表 updateByPrimaryKey--------------------------" + ret);
            if (ret != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (DuplicateKeyException e) {
            return MysqlCapture.capture(e, logger);
        } catch (DataIntegrityViolationException e) {
            return MysqlCapture.capture(e, logger);
        } catch (Exception e) {
            return MysqlCapture.capture(e, logger);
        }
        if (ret != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return new ErrorReturn(ret, "");
    }

}
