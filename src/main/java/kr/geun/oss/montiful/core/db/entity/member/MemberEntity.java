package kr.geun.oss.montiful.core.db.entity.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * MemberEntity
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwd;

    @Column(nullable = false)
    private String name;

    //status

    //last login

    //last passwd change

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
