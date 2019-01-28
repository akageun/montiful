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
 * Program Url Entity
 *
 * @author akageun
 */
//@formatter:off
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "program_url",
	indexes = {
		@Index (
			name = "IDX_program_url",
			columnList = "program_idx, url_idx",
			unique = true
			)
		}
	)
//@formatter:on
public class ProgramUrlEntity {

	@Id
	@Column(name = "program_url_idx", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long programUrlIdx;

	@Column(name = "program_idx", nullable = false)
	private Long programIdx;

	@Column(name = "url_idx", nullable = false)
	private Long urlIdx;

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
