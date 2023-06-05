package com.ie303.dialuxury.repository;
import com.ie303.dialuxury.model.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends MongoRepository<user, String> {
    user findByEmail(String email);
    user findByUserId(String UserId);

    @Query("{'email': ?0}")
    void resetPassword(String email, String newPassword);
}
