package com.kim.jparedisdemo.service.impl;

import com.kim.jparedisdemo.dao.MyEntityManager;
import com.kim.jparedisdemo.dao.MyHibernateDaoSupport;
import com.kim.jparedisdemo.dao.UserRepository;
import com.kim.jparedisdemo.model.User;
import com.kim.jparedisdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository ;


//    @Autowired
//    private MyHibernateDaoSupport myHibernateDaoSupport;


    @Autowired
    MyEntityManager myEntityManager;

    @Override
    public User save(User user) {
        log.info(" save User , {}" ,user);

        return null == user ? null :myEntityManager.saveYourEntity(user);
//        return null == user ? null : userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        log.info(" get User , {}", id);

//        return  (User)myEntityManager.getEntityManager().createQuery("from kim_user t where t.id = :id").setParameter("id",id).getResultList().get(0);

//        return userRepository.findById(id).orElse(null);

        return  (User)myEntityManager.getEntityManager().find(User.class,id);

    }


}
