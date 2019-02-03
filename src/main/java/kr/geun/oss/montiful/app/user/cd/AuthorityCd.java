package kr.geun.oss.montiful.app.user.cd;

/**
 *
 *
 * @author 김형근
 */
public enum AuthorityCd {
	NORMAL, MANAGER, SUPER_ADMIN,

	;

	public String roleCd() {
		return "ROLE_" + this.name();
	}
}
