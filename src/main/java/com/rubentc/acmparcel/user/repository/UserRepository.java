package com.rubentc.acmparcel.user.repository;

import com.rubentc.acmparcel.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        LEFT JOIN FETCH u.employee e
        LEFT JOIN FETCH e.roles r
        LEFT JOIN FETCH r.permissions p
        WHERE u.email = :email
    """)
    Optional<User> findByEmailWithAuthorities(
            @Param("email") String email
    );
}
