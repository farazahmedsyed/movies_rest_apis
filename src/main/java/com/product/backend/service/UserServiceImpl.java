package com.product.backend.service;

import com.product.backend.model.User;
import com.product.backend.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRespository;
    @Autowired
    private UserService selfReference;

    @Override
    public Map<String, User> getManagedEntities(Set<User> users) {
        Map<String, User> userMap = new HashMap<>();
        for (User user : users) {
            try {
                userMap.put(user.getName(), selfReference.saveAndCommit(user));
            } catch (DataIntegrityViolationException e) {
                userMap.put(user.getName(), userRespository.findFirstByName(user.getName()));
            }
        }
        return userMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User saveAndCommit(User user) {
        return userRespository.save(user);
    }
}
