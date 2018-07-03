package com.example.demo.repository;

import com.example.demo.config.Neo4jConfigTest;
import com.example.demo.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.session.Utils;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.neo4j.ogm.testutil.TestUtils;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@DirtiesContext
public class Neo4jIntegrationTest extends MultiDriverTestClass{

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeClassSetUp() {
        sessionFactory = new SessionFactory(driver, "com.example.demo.domain");
        Session initialSession = sessionFactory.openSession();
        initialSession.query(TestUtils.readCQLFile("data/user_program_test_data.cql").toString(), Utils.map());
    }

    @Before
    public void init() throws IOException {
        session = sessionFactory.openSession();
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
