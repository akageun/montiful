package kr.geun.oss.montiful.core.pagination.dialect;

import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Pagination Attribute Tag
 *
 * @author akageun
 */
@Slf4j
public class PaginationAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "pagination";
	private static final int PRECEDENCE = 10000;

	private static final String JS_FUNC_NAME_ATTRIBUTE_NAME = "jsFuncName";
	private static final String NO_EMPTY_ATTRIBUTE_NAME = "noEmpty";

	public PaginationAttributeTagProcessor(final String dialectPrefix) {
		super(
			//@formatter:off
			TemplateMode.HTML, // This processor will apply only to HTML mode
			dialectPrefix,     // Prefix to be applied to name for matching
			null,              // No tag name: match any tag name
			false,             // No prefix to be applied to tag name
			ATTR_NAME,         // Name of the attribute that will be matched
			true,              // Apply dialect prefix to attribute name
			PRECEDENCE,        // Precedence (inside dialect's precedence)
			true // Remove the matched attribute afterwards
			//@formatter:on
		);
	}

	@Override
	protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String s,
		IElementTagStructureHandler iElementTagStructureHandler) {

		final IEngineConfiguration configuration = iTemplateContext.getConfiguration();

		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		final IStandardExpression expression = parser.parseExpression(iTemplateContext, s);

		final PaginationInfo paginationInfo = (PaginationInfo)expression.execute(iTemplateContext);

		if (paginationInfo == null) {
			return;
		}

		String jsFuncName = "movePage";
		if (iProcessableElementTag.hasAttribute(JS_FUNC_NAME_ATTRIBUTE_NAME)) {
			jsFuncName = iProcessableElementTag.getAttribute(JS_FUNC_NAME_ATTRIBUTE_NAME).getValue();
		}

		StringBuffer sb = new StringBuffer();
		if (paginationInfo.getPageNumber() > paginationInfo.getPageBlockSize()) {
			sb.append("<li class='page-item'><a class='page-link'  onclick='" + jsFuncName + "(" + paginationInfo.getFirstPageNo()
				+ ");'>&laquo;</a></li>");
			sb.append("<li class='page-item'><a class='page-link'  onclick='" + jsFuncName + "(" + paginationInfo.getPreBlockPageNo()
				+ ");'>&lt;</a></li>");
		}

		for (int i = paginationInfo.getFirstBlockPageNo(); i < paginationInfo.getLastBlockPageNo(); i++) {
			if (i == paginationInfo.getPageNumber()) {
				sb.append("<li class='page-item active'><a class='page-link'>" + i + "</a></li>");
			} else {
				sb.append("<li class='page-item'><a class='page-link'  onclick='" + jsFuncName + "(" + i + ");'>" + i + "</a></li>");
			}

		}

		if (paginationInfo.getTotalPages() > paginationInfo.getNextBlockPageNo()) {
			sb.append("<li class='page-item'><a class='page-link'  onclick='" + jsFuncName + "(" + (paginationInfo.getNextBlockPageNo() + 1)
				+ ");'>&gt;</a></li>");
			sb.append(
				"<li class='page-item'><a class='page-link'  onclick='" + jsFuncName + "(" + paginationInfo.getLastPageNo() + ");'>&raquo;</a></li>");
		}

		if (StringUtils.isBlank(sb) && iProcessableElementTag.hasAttribute(NO_EMPTY_ATTRIBUTE_NAME) && Boolean.parseBoolean(
			iProcessableElementTag.getAttribute(NO_EMPTY_ATTRIBUTE_NAME).getValue())) {

			sb.append("<li class='page-item active'><a class='page-link'>1</a></li>");
		}

		iElementTagStructureHandler.setBody(sb.toString(), false);

	}
}
