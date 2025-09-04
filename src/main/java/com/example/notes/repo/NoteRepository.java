package com.example.notes.repo;

import com.example.notes.model.Note;
import com.example.notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserOrderByUpdatedAtDesc(User user);
    Optional<Note> findByIdAndUser(Long id, User user);
    Optional<Note> findByShareTokenAndIsPublic(String token, boolean isPublic);
}
