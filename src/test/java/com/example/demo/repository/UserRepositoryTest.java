package com.example.demo.repository;

import com.example.demo.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Import({UserRepository.class})
public class UserRepositoryTest extends Neo4jIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testUserTest() throws Exception {

        Pageable pageable = PageRequest.of(0, 1);
        Page<User> userPage = userRepository.findAll(pageable);
        Assert.assertNotNull(userPage);
        Assert.assertEquals(1, userPage.getTotalPages());
    }

}