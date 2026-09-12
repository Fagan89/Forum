package ru.fagan.forumservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.fagan.forumservice.enam.Role;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "forum_users",
        indexes = {
                @Index(name = "idx_forum_user_username", columnList = "username"),
                @Index(name = "idx_forum_user_role", columnList = "role")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_forum_user_auth_id", columnNames = "auth_user_id"),
                @UniqueConstraint(name = "uk_forum_user_profile_id", columnNames = "profile_user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "username", "role"})
public class ForumUser {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "auth_user_id", nullable = false, updatable = false)
    private UUID authUserId;

    @Column(name = "profile_user_id", nullable = false, updatable = false, unique = true)
    private UUID profileUserId;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "banned_until")
    private Instant bannedUntil;

    @Column(name = "ban_reason", columnDefinition = "TEXT")
    private String banReason;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}