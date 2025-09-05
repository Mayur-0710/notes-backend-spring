package com.example.notes.controller;

import com.example.notes.dto.NoteDtos;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService svc;
    public NoteController(NoteService svc){ this.svc = svc; }

    private Long uid(Authentication auth){ return Long.parseLong(auth.getName()); }

    @GetMapping
    public List<NoteDtos.NoteRes> list(Authentication auth){
        return svc.list(uid(auth)).stream()
                .map(n -> new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                        n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt()))
                .toList();
    }

    @PostMapping
    public NoteDtos.NoteRes create(Authentication auth, @RequestBody NoteDtos.NoteCreateReq req){
        Note n = svc.create(uid(auth), req);
        return new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @GetMapping("/{id}")
    public NoteDtos.NoteRes get(Authentication auth, @PathVariable Long id){
        Note n = svc.get(uid(auth), id);
        return new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @PatchMapping("/{id}")
    public NoteDtos.NoteRes update(Authentication auth, @PathVariable Long id, @RequestBody NoteDtos.NoteUpdateReq req){
        Note n = svc.update(uid(auth), id, req);
        return new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @PostMapping("/{id}/share")
    public NoteDtos.NoteRes share(Authentication auth, @PathVariable Long id){
        Note n = svc.share(uid(auth), id);
        return new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Authentication auth, @PathVariable Long id){
        svc.delete(uid(auth), id);
        return ResponseEntity.ok().body("{\"ok\":true}");
    }
}
