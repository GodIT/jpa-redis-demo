package com.kim.jparedisdemo.dao;

import com.kim.jparedisdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * 请补充描述信息
 *
 * @author <a href="mailto:yu.peng@vtradex.com">彭宇</a>
 * @version v1.0
 * @since :
 **/

@Component
public class MyEntityManager {

    @Autowired
    EntityManager entityManager;
    @Transactional
    public User saveYourEntity(User yourEntity) {
        if (yourEntity.getId() == null) { // 假设 getId() 返回实体的主键
             entityManager.persist(yourEntity); // 用于保存新实体
            return yourEntity;
        } else {
            return entityManager.merge(yourEntity); // 用于更新现有实体
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
