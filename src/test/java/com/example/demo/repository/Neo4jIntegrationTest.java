package com.example.demo.repository;

import com.example.demo.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4jIntegrationTest{

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void init() throws IOException {
        loadData();
    }

    public  void loadData() {

        StringBuilder cypherStatements = new StringBuilder();
        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("data/user_program_test_data.cql"))) {
            scanner.useDelimiter(System.getProperty("line.separator"));
            while (scanner.hasNext()) {
                cypherStatements.append(scanner.next()).append(' ');
            }
        }

        sessionFactory.openSession().query(cypherStatements.toString(), new HashMap<String, Object>());
    }

    @After
    public void teardown() {
        sessionFactory.openSession().purgeDatabase();
    }

    @Test
    public void shouldLoadUsers() {

        Collection<User> users = sessionFactory.openSession().loadAll(User.class);
        if (!users.isEmpty()) {
            assertThat(users).hasSize(1);

            for (User user : users) {
                assertThat(user.getUserRefId()).isEqualTo(user.getUserRefId());
            }
        } else {
            fail("Satellite Integration Tests not run: Is there a database?");
        }
    }

}
