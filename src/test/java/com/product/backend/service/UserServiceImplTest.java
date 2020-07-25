package com.product.backend.service;

import com.product.backend.BaseTest;
import com.product.backend.model.User;
import com.product.backend.repository.UserRespository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest extends BaseTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRespository userRespository;
    @Mock
    private UserService selfReference;

    @Test
    public void unitTest_getManagedEntities_allUnique_success() {
        User user1 = createDirector1();
        User user2 = createDirector2();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        when(selfReference.saveAndCommit(user1)).thenReturn(user1);
        when(selfReference.saveAndCommit(user2)).thenReturn(user2);
        Map<String, User> map = userService.getManagedEntities(users);
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals(user1, map.get(user1.getName()));
        assertEquals(user2, map.get(user2.getName()));
        verify(selfReference, times(2)).saveAndCommit(any());
        verify(userRespository, times(0)).findFirstByName(any());
    }

    @Test
    public void unitTest_getManagedEntities_oneUnique_success() {
        User user1 = createDirector1();
        User user2 = createDirector2();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        when(selfReference.saveAndCommit(user1)).thenReturn(user1);
        when(selfReference.saveAndCommit(user2)).thenThrow(new DataIntegrityViolationException(""));
        when(userRespository.findFirstByName(user2.getName())).thenReturn(user2);
        Map<String, User> map = userService.getManagedEntities(users);
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals(user1, map.get(user1.getName()));
        assertEquals(user2, map.get(user2.getName()));
        verify(selfReference, times(2)).saveAndCommit(any());
        verify(userRespository, times(1)).findFirstByName(any());
    }

    @Test
    public void unitTest_getManagedEntities_NoneUnique_success() {
        User user1 = createDirector1();
        User user2 = createDirector2();
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        when(selfReference.saveAndCommit(any())).thenThrow(new DataIntegrityViolationException(""));
        when(userRespository.findFirstByName(user1.getName())).thenReturn(user1);
        when(userRespository.findFirstByName(user2.getName())).thenReturn(user2);
        Map<String, User> map = userService.getManagedEntities(users);
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals(user1, map.get(user1.getName()));
        assertEquals(user2, map.get(user2.getName()));
        verify(selfReference, times(2)).saveAndCommit(any());
        verify(userRespository, times(2)).findFirstByName(any());
    }

    @Test
    public void unitTest_saveAndCommit_valid_success(){
        long userId =1L;
        User user = createActor1();
        user.setId(null);
        doAnswer(invocationOnMock -> {
            assertEquals(user, invocationOnMock.getArgument(0));
            User user1 = new User();
            user1.setName(user.getName());
            user1.setId(userId);
            return user1;
        }).when(userRespository).save(any());

        User user1 = userService.saveAndCommit(user);
        assertNotNull(user1);
        assertEquals(userId, user1.getId());
        assertEquals(user.getName(), user1.getName());
    }
}
