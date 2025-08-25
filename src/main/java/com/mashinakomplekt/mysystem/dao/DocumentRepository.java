package com.mashinakomplekt.mysystem.dao;

import com.mashinakomplekt.mysystem.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    //List<Document> findByPublishedAtGreaterThanEqual(LocalDateTime publishedAt);

    @Query("select d from Document d left join d.topic.users u where u.id = ?1 and d.title like %?2%")
    List<Document> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

    Optional<Document> findById(Long id);
}
