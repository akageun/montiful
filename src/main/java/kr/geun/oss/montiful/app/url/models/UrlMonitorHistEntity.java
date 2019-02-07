package kr.geun.oss.montiful.app.url.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * URL Monitor Hist
 *
 * @author akageun
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url_monitor_hist")
public class UrlMonitorHistEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "monitor_hist_idx")
	private Long monitorHistIdx;

	@Column(name = "url_idx", nullable = false)
	private Long urlIdx;

	@Column(name = "health_status_cd", nullable = false)
	private String healthStatusCd; //HEALTH, WARNING, ERROR

	@Column(name = "pre_health_status_cd", nullable = false)
	private String preHealthStatusCheckCd;

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
}
