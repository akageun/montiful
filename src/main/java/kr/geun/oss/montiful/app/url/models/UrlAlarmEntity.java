package kr.geun.oss.montiful.app.url.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
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
 *
 *
 * @author akageun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "url_alarm")
@IdClass(UrlAlarmEntity.CompositeKey.class)
public class UrlAlarmEntity {

	@Id
	@Column(name = "url_idx", nullable = false)
	private Long urlIdx;

	@Id
	@Column(name = "alarm_idx", nullable = false)
	private Long alarmIdx;

	@Column(name = "created_user_id", nullable = false, updatable = false)
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

	@Getter
	@NoArgsConstructor
	public static class CompositeKey implements Serializable {
		private static final long serialVersionUID = 1L; //TODO : 수정해야함.

		private Long urlIdx;
		private Long alarmIdx;

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
				CompositeKey other = (CompositeKey) obj;
				return new EqualsBuilder().append(urlIdx, other.getUrlIdx()).append(alarmIdx, other.alarmIdx).build();
			}
			return false;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(urlIdx).append(alarmIdx).build();
		}
	}
}
