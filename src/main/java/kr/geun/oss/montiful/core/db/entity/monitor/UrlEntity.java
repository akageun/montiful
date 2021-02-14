package kr.geun.oss.montiful.core.db.entity.monitor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.geun.oss.montiful.app.monitor.type.UrlCheckType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * UrlEntity
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "url")
public class UrlEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "memo")
    private String memo;

    @Column(name = "use", nullable = false)
    private boolean use; //사용여부

    @Column(name = "notify", nullable = false)
    private boolean notify; //알림여부

    @Column(name = "check_type")
    @JsonSerialize(using = UrlCheckType.Serializer.class)
    @JsonDeserialize(using = UrlCheckType.Deserializer.class)
    private UrlCheckType checkType;

    @Column(name = "check_json")
    private String checkJson;

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
    public UrlEntity(
        Long id,
        String url,
        String memo,
        boolean use,
        boolean notify,
        UrlCheckType checkType,
        String checkJson,
        Long createdUserId,
        Long updatedUserId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.url = url;
        this.memo = memo;
        this.use = use;
        this.notify = notify;
        this.checkType = checkType;
        this.checkJson = checkJson;
        this.createdUserId = createdUserId;
        this.updatedUserId = updatedUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
