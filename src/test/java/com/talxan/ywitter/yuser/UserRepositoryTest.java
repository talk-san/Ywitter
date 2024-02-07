package com.talxan.ywitter.yuser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByEmail() {

        // Given
        User user = User.builder()
                .email("tariel@gmail.com")
                .build();
        underTest.save(user);

        // When
        Optional<User> expected = underTest.findByEmail("tariel@gmail.com");

        // Then
        assertThat(expected).isPresent().contains(user);

    }

}
