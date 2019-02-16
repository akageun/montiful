package kr.geun.oss.montiful.app.user.cd;

/**
 * Authority Code Enum
 *
 * @author akageun
 */
public enum AuthorityCd {
	//@formatter:off
	NORMAL,
	MANAGER,
	SUPER_ADMIN,
	//@formatter:on
	;

	public String roleCd() {
		return "ROLE_" + this.name();
	}
}
