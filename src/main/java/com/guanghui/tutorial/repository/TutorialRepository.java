package com.guanghui.tutorial.repository;

import com.guanghui.tutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(Boolean published);

    List<Tutorial> findByTitleContainingIgnoreCase(String title);
}
