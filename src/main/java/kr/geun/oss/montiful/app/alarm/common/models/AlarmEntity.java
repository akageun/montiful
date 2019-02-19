package kr.geun.oss.montiful.app.alarm.common.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Alarm Entity
 *
 * @author akageun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "alarm")
public class AlarmEntity {

	@Id
	@Column(name = "alarm_idx", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long alarmIdx;

	@Column(name = "alarm_name", nullable = false)
	private String alarmName;

	@Column(name = "alarm_channel", nullable = false)
	private String alarmChannel; //notificationChannelCd

	@Column(name = "alaram_value", nullable = false)
	private String alarmValue; //Json

	@Column(name = "memo")
	private String memo;

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
}
