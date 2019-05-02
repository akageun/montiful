package kr.geun.oss.montiful.app.user.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User-Authority Mapping Entity
 *
 * @author akageun
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_authority")
@IdClass(UserAuthorityEntity.CompositeKey.class)
public class UserAuthorityEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Id
    @Column(name = "authority_cd", nullable = false)
    private String authorityCd;

    @Column(name = "memo")
    private String memo;

    @Column(name = "created_user_id", nullable = false)
    private String createdUserId;

    @Column(name = "updated_user_id", nullable = false)
    private String updatedUserId;

    /**
     * 생성일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Key Class
     */
    @Getter
    @NoArgsConstructor
    public static class CompositeKey implements Serializable {
        private static final long serialVersionUID = 1L; //TODO : 수정해야함.

        private String userId;
        private String authorityCd;

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
                CompositeKey other = (CompositeKey) obj;
                return new EqualsBuilder().append(userId, other.getUserId()).append(authorityCd, other.getAuthorityCd()).build();
            }
            return false;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(userId).append(authorityCd).build();
        }
    }
}
