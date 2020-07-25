package com.product.backend.service;

import com.product.backend.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

public interface UserService {
    Map<String, User> getManagedEntities(Set<User> users);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    User saveAndCommit(User user);
}
