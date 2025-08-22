package com.mashinakomplekt.mysystem.dao;

import com.mashinakomplekt.mysystem.models.Document;
import com.mashinakomplekt.mysystem.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    //List<Document> findByPublishedAtGreaterThanEqual(LocalDateTime publishedAt);

    @Query("select d from Document d where d.topic.user.id = ?1 and d.title like %?2%")
    List<Document> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);
}
