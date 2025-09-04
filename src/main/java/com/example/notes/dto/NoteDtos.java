package com.example.notes.dto;

import java.time.Instant;

public class NoteDtos {

    public static class NoteCreateReq {
        public String title;
        public String content;
    }

    public static class NoteUpdateReq {
        public String title;
        public String content;
        public Boolean is_public;
    }

    public static class NoteRes {
        public Long id;
        public String title;
        public String content;
        public boolean is_public;
        public String share_token;
        public Instant created_at;
        public Instant updated_at;

        public NoteRes(Long id, String title, String content, boolean isPublic,
                       String shareToken, Instant createdAt, Instant updatedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.is_public = isPublic;
            this.share_token = shareToken;
            this.created_at = createdAt;
            this.updated_at = updatedAt;
        }
    }
}
