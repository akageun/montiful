package kr.geun.oss.montiful.app.program.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Program Url Entity
 *
 * @author akageun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "program_url")
@IdClass(ProgramUrlEntity.CompositeKey.class)
public class ProgramUrlEntity {

	@Id
	@Column(name = "program_idx", nullable = false)
	private Long programIdx;

	@Id
	@Column(name = "url_idx", nullable = false)
	private Long urlIdx;

	@Column(name = "created_user_id", nullable = false, updatable = false)
	private String createdUserId;

	/**
	 * 생성일시
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Getter
	@NoArgsConstructor
	public static class CompositeKey implements Serializable {
		private static final long serialVersionUID = 1L; //TODO : 수정해야함.

		private Long programIdx;
		private Long urlIdx;

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
				CompositeKey other = (CompositeKey) obj;
				return new EqualsBuilder().append(programIdx, other.programIdx).append(urlIdx, other.urlIdx).build();
			}
			return false;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(programIdx).append(urlIdx).build();
		}
	}

}
