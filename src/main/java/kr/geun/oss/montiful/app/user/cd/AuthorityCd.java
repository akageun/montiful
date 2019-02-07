package kr.geun.oss.montiful.app.user.cd;

/**
 *
 *
 * @author akageun
 */
public enum AuthorityCd {
	NORMAL, MANAGER, SUPER_ADMIN,

	;

	public String roleCd() {
		return "ROLE_" + this.name();
	}
}
