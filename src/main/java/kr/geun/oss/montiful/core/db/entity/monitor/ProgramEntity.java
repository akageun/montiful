package kr.geun.oss.montiful.core.db.entity.monitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ProgramEntity
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "program")
public class ProgramEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String memo;

    @Column(name = "use", nullable = false)
    private boolean use; //사용여부

    @Column(name = "created_user_id", nullable = false, updatable = false)
    private Long createdUserId;

    @Column(name = "updated_user_id", nullable = false)
    private Long updatedUserId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public ProgramEntity(
        Long id, String name, String memo, boolean use, Long createdUserId, Long updatedUserId, LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.use = use;
        this.createdUserId = createdUserId;
        this.updatedUserId = updatedUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
