package com.cheatsheet.repository;

import com.cheatsheet.dto.TagCountDTO;
import com.cheatsheet.entity.Cheatsheet;
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

//    SELECT count(t.name) as total_count, t.id, t.name FROM tag t JOIN cheatsheet_has_tag cht ON t.id = cht.tag_id JOIN cheatsheet c ON cht.cheatsheet_id = c.id WHERE c.section_id = 1 GROUP BY t.id LIMIT 5;
    @Query("SELECT new com.cheatsheet.dto.TagCountDTO(t.id, t.name, COUNT(t)) " +
        "FROM Tag t " +
        "JOIN t.cheatsheets c " +
        "WHERE c.section.name = :sectionName " +
        "GROUP BY t.id, t.name")
    List<TagCountDTO> getTopTenTags(@Param("sectionName") String sectionName);

    @Query("SELECT c FROM Cheatsheet c JOIN c.tags t WHERE t.name = :tagName")
    List<Cheatsheet> getCheatsheetsByTag(@Param("tagName") String tagName);

    @Query("SELECT t FROM Tag t INNER JOIN t.cheatsheets c WHERE t.isDeleted = false AND c.id = :cheatsheetId")
    List<Tag> findByCheatsheetId(@Param("cheatsheetId") Integer cheatsheetId);

    @Query("SELECT t FROM Tag t WHERE t.isDeleted = false AND t.id = :id")
    Tag findTagById(@Param("id") Integer id);

    Tag findByName(String name);
}
