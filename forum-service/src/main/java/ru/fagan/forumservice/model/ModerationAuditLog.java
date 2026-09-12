package ru.fagan.forumservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.fagan.forumservice.enam.ModerationAction;
import ru.fagan.forumservice.enam.ModerationTargetType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "moderation_audit_logs",
        indexes = {
                @Index(name = "idx_audit_moderator", columnList = "moderator_id"),
                @Index(name = "idx_audit_target", columnList = "target_type, target_id")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "action", "targetType"})
public class ModerationAuditLog {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", nullable = false, foreignKey = @ForeignKey(name = "fk_audit_moderator"))
    private ForumUser moderator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", foreignKey = @ForeignKey(name = "fk_audit_target_user"))
    private ForumUser targetUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 20)
    private ModerationTargetType targetType;

    @Column(name = "target_id")
    private UUID targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 20)
    private ModerationAction action;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
