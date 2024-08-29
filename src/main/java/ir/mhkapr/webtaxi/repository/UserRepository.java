package ir.mhkapr.webtaxi.repository;

import ir.mhkapr.webtaxi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
        public Optional<User> findByPhoneNumber(String phoneNumber);
}
