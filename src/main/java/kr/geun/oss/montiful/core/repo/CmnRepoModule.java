package kr.geun.oss.montiful.core.repo;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import kr.geun.oss.montiful.core.cd.ISearchTypeCd;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author akageun
 */
public class CmnRepoModule {

	/**
	 * OrderBy
	 *
	 * @param entity
	 * @param sort
	 * @param <E>
	 * @return
	 */
	protected <E extends EntityPathBase> OrderSpecifier[] getOrderBy(E entity, Sort sort) {

		List<OrderSpecifier> orderList = new ArrayList<>();
		for (Sort.Order tmpOrder : sort) {
			orderList.add(
				new OrderSpecifier<>(Order.valueOf(tmpOrder.getDirection().name()), Expressions.stringPath(entity, tmpOrder.getProperty())));
		}

		return orderList.toArray(new OrderSpecifier[0]);
	}

	/**
	 * Where : boolean Like Search
	 *
	 * @param qEntity
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	protected <E> BooleanExpression booleanLikeSearch(EntityPathBase<E> qEntity, ISearchTypeCd searchType, String searchValue) {

		if (searchType.isLongSearch()) {
			NumberPath<Long> numberPath = Expressions.numberPath(Long.class, searchType.getColumnName());

			return numberPath.eq(Long.parseLong(searchValue));
		}

		StringPath tmpStringPath = Expressions.stringPath(qEntity, searchType.getColumnName());

		return tmpStringPath.like(searchValue + "%");
	}
}
