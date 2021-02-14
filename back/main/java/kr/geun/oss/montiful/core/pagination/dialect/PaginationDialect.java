package kr.geun.oss.montiful.core.pagination.dialect;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Pagination Dialect
 *
 * @author akageun
 */
public class PaginationDialect extends AbstractProcessorDialect {

	public PaginationDialect() {
		super(
			//@formatter:off
			"Paging Module",    // Dialect name
			"paging",            // Dialect prefix (hello:*)
			1000 // Dialect precedence
			//@formatter:on
		);
	}

	@Override
	public Set<IProcessor> getProcessors(final String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<>();
		processors.add(new PaginationAttributeTagProcessor(dialectPrefix));
		return processors;
	}

}
