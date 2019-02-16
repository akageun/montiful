package kr.geun.oss.montiful.app.url.models;

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
 * URL Entity
 *
 * @author akageun
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url")
public class UrlEntity {

	@Id
	@Column(name = "url_idx", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long urlIdx;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "url_name", nullable = false)
	private String urlName;

	@Column(name = "memo")
	private String memo;

	@Column(name = "connection_timeout", nullable = false)
	private int connectionTimeout;  //1000 - 10000

	@Column(name = "read_timeout", nullable = false)
	private int readTimeout; //1000 - 10000

	@Column(name = "health_status_cd", nullable = false)
	private String healthStatusCd; //HEALTH, WARNING, ERROR

	@Column(name = "method", nullable = false)
	private String method; //GET, POST

	@Column(name = "status_check_type_cd", nullable = false)
	private String statusCheckTypeCd; //status_200, status-2xx, same_string

	@Column(name = "status_check_value", nullable = true)
	private String statusCheckValue;

	@Column(name = "notify", nullable = false)
	private boolean notify; //알림여부

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
}
