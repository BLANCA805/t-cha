package com.tcha.tag.repository;

import com.tcha.tag.entity.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    void deleteByName(String name);

    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:name%")
    Optional<List<Tag>> findByNameContaining(String name);
}
