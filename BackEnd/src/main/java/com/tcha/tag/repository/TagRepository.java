package com.tcha.tag.repository;

import com.tcha.tag.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    void deleteByName(String name);
}
