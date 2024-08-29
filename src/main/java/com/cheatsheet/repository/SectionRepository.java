package com.cheatsheet.repository;

import com.cheatsheet.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    @Query("SELECT s FROM Section s WHERE s.isDeleted = false")
    public List<Section> getAllSections();

    @Query("SELECT s FROM Section s WHERE s.isDeleted = false AND s.id = :id")
    public Optional<Section> findSectionById(int id);

    @Query("SELECT s FROM Section s WHERE s.name = :name")
    public Optional<Section> findByName(String name);
}
