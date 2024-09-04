package com.cheatsheet.repository;

import com.cheatsheet.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("SELECT t FROM Tag t WHERE t.isDeleted = false")
    List<Tag> findAllTags();

    @Query("SELECT t FROM Tag t WHERE t.isDeleted = false AND t.id = :id")
    Tag findTagById(@Param("id") Integer id);

    Tag findByName(String name);
}
