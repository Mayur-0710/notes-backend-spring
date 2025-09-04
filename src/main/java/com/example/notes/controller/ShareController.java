package com.example.notes.controller;

import com.example.notes.dto.NoteDtos;
import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share")
public class ShareController {
    private final NoteService svc;
    public ShareController(NoteService svc){ this.svc = svc; }

    @GetMapping("/{token}")
    public NoteDtos.NoteRes view(@PathVariable String token){
        Note n = svc.getByShareToken(token);
        return new NoteDtos.NoteRes(n.getId(), n.getTitle(), n.getContent(),
                n.isPublic(), n.getShareToken(), n.getCreatedAt(), n.getUpdatedAt());
    }
}
