package com.onlineShop.repository;

import com.onlineShop.models.Users.Staff.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, UUID> {

    Optional<Moderator> findById(String id);

    Optional<Moderator> findByEmail(String email);

    Optional<Moderator> findByLogin(String login);

    Optional<Moderator> findByPhoneNumber(String phoneNumber);

    List<Moderator> getAllByAdminId(String id);

    boolean existsModeratorByEmail(String email);

    boolean existsModeratorByPhoneNumber(String phoneNumber);

    boolean existsModeratorByLogin(String login);

    void deleteById(String id);

}
