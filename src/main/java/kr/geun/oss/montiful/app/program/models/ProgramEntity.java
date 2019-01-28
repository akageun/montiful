package kr.geun.oss.montiful.app.program.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Program Entity
 *
 * @author akageun
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "program", indexes = {@Index(name = "IDX_programName", columnList = "program_name")})
public class ProgramEntity {

    @Id
    @Column(name = "program_idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long programIdx;

    @Column(name = "program_name", nullable = false)
    private String programName;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
