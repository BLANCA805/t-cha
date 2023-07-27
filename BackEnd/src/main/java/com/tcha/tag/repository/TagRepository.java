package com.tcha.tag.repository;

import com.tcha.tag.entity.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    boolean existsByName(String tag);

    Optional<Tag> findByName(String name);
}
