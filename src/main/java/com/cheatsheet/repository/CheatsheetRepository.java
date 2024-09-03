package com.cheatsheet.repository;

import com.cheatsheet.dto.CheatsheetDTO;
import com.cheatsheet.entity.Cheatsheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CheatsheetRepository extends JpaRepository<Cheatsheet, Integer> {
    @Procedure(procedureName = "GetCheatsheetsBySection")
    List<Cheatsheet> findCheatsheetBySection(@Param("section_id") Integer sectionId);

    @Query("SELECT c FROM Cheatsheet c WHERE c.isDeleted = false")
    List<Cheatsheet> findAllCheatsheets();

    @Query("SELECT c FROM Cheatsheet c WHERE c.isDeleted = false AND c.id = :id")
    Cheatsheet findCheatsheetById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Cheatsheet c SET c.isDeleted = true WHERE c.id = :id")
    int deleteCheatsheetById(@Param("id") Integer id);
}
