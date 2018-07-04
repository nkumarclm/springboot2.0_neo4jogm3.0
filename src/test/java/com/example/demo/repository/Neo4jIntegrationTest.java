package com.example.demo.repository;

import com.example.demo.config.Neo4jConfigTest;
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
import org.neo4j.ogm.session.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@DirtiesContext
public class Neo4jIntegrationTest{

    private static SessionFactory sessionFactory;

    private Session session;

    static Configuration getNeo4jConfiguration() {
        org.neo4j.ogm.config.Configuration configuration =
                new org.neo4j.ogm.config.Configuration.Builder(new ClasspathConfigurationSource("ogm.properties"))
                .build();

        return configuration;
    }

    @BeforeClass
    public static void beforeClassSetUp() {
        sessionFactory = new SessionFactory(getNeo4jConfiguration(), "com.example.demo.domain");
    }

    @Before
    public void init() throws IOException {
        session = sessionFactory.openSession();
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

        session.query(cypherStatements.toString(), new HashMap<String, Object>());
    }

    @After
    public void teardown() {
        session.purgeDatabase();
    }

    @Test
    public void shouldLoadUsers() {

        Collection<User> users = session.loadAll(User.class);
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
