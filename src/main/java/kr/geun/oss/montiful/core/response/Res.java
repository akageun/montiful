package kr.geun.oss.montiful.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response Class
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public class Res<T> {
	private Boolean result;

	private T data;
	private String msg;

	public static Res of(String msg) {
		return new Res<>(msg);
	}

	public static Res of(Boolean result, String msg) {
		return new Res<>(result, msg);
	}

	public static <T> Res of(Boolean result, String msg, T data) {
		return new Res<>(result, msg, data);
	}

	private Res(String msg) {
		this.msg = msg;
	}

	private Res(Boolean result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	private Res(Boolean result, String msg, T data) {
		this.result = result;
		this.msg = msg;
		this.data = data;
	}
}
