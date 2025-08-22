package com.mashinakomplekt.mysystem.dao;

import com.mashinakomplekt.mysystem.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("select t from Topic t where t.user.id = ?1")
    List<Topic> findByUserId(Long userId);

    Optional<Topic> findById(Long id);
}
