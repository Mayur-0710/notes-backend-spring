package com.example.notes.service;

import com.example.notes.dto.NoteDtos;
import com.example.notes.model.Note;
import com.example.notes.model.User;
import com.example.notes.repo.NoteRepository;
import com.example.notes.repo.UserRepository;
import com.example.notes.util.ShareToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository notes;
    private final UserRepository users;

    public NoteService(NoteRepository notes, UserRepository users){
        this.notes = notes; this.users = users;
    }

    private User userById(Long id){
        return users.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Note> list(Long userId){
        return notes.findByUserOrderByUpdatedAtDesc(userById(userId));
    }

    public Note create(Long userId, NoteDtos.NoteCreateReq req){
        Note n = new Note();
        n.setUser(userById(userId));
        n.setTitle(req.title);
        n.setContent(req.content);
        return notes.save(n);
    }

    public Note get(Long userId, Long noteId){
        return notes.findByIdAndUser(noteId, userById(userId))
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Note update(Long userId, Long noteId, NoteDtos.NoteUpdateReq req){
        Note n = get(userId, noteId);
        if (req.title != null) n.setTitle(req.title);
        if (req.content != null) n.setContent(req.content);
        if (req.is_public != null) {
            boolean desired = req.is_public;
            n.setPublic(desired);
            if (desired && (n.getShareToken() == null || n.getShareToken().isEmpty()))
                n.setShareToken(ShareToken.generate());
            if (!desired) n.setShareToken(null);
        }
        return notes.save(n);
    }

    public void delete(Long userId, Long noteId){
        notes.delete(get(userId, noteId));
    }

    public Note getByShareToken(String token){
        return notes.findByShareTokenAndIsPublic(token, true)
                .orElseThrow(() -> new RuntimeException("Invalid or private share link"));
    }
}
