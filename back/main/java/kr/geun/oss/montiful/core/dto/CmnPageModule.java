package kr.geun.oss.montiful.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common Page Module
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CmnPageModule {
	private int pn; //pageNumber

	@Setter
	private int ez; //elementSize

	@Setter
	private String sot; //sortType

	@Setter
	private String sod; //Sort.Direction
}
