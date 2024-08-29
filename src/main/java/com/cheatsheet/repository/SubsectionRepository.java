package com.cheatsheet.repository;

import com.cheatsheet.entity.Section;
import com.cheatsheet.entity.Subsection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubsectionRepository extends JpaRepository<Subsection, Integer> {
    @Query("SELECT ss FROM Subsection ss WHERE ss.isDeleted = false")
    public List<Subsection> findAllSubsections();

    @Query("SELECT ss FROM Subsection ss WHERE ss.isDeleted = false AND ss.id = :id")
    public Optional<Subsection> findSubsectionById(int id);
}
