package com.example.notes.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notes", indexes = {
        @Index(name = "idx_notes_user_id", columnList = "user_id"),
        @Index(name = "idx_notes_share_token", columnList = "shareToken")
})
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_note_user"))
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    // IMPORTANT: use TEXT instead of @Lob to avoid PostgreSQL Large Object
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean isPublic = false;

    @Column(unique = true)
    private String shareToken;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }

    // getters & setters

    public Long getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public boolean isPublic() { return isPublic; }

    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    public String getShareToken() { return shareToken; }

    public void setShareToken(String shareToken) { this.shareToken = shareToken; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
