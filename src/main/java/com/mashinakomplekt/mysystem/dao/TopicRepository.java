package com.mashinakomplekt.mysystem.dao;

import com.mashinakomplekt.mysystem.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findById(Long id);
}
