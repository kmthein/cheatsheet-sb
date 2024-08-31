package com.cheatsheet.repository;

import com.cheatsheet.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    @Query("SELECT b FROM Block b WHERE b.cheatsheet.id = :cheatsheetId")
    List<Block> getBlocksByCheatsheet(@Param("cheatsheetId") Integer cheatsheetId);
}
