package kr.geun.oss.montiful.core.validation.validator;

import com.google.common.collect.Sets;
import kr.geun.oss.montiful.core.validation.annotation.EmailValid;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 *
 * @author akageun
 */
public class CustomEmailValidator implements ConstraintValidator<EmailValid, String> {

	private static final EmailValidator emailValid = EmailValidator.getInstance();
	private static final Set<String> CACHED_VALID_HOSTNAME = Sets.newLinkedHashSet(Arrays.asList("gmail.com", "naver.com"));

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (emailValid.isValid(email) == false) {
			return false;
		}

		String hostname = StringUtils.substring(email, email.indexOf("@") + 1);

		if (CACHED_VALID_HOSTNAME.contains(StringUtils.lowerCase(hostname))) {
			return true;
		}

		return mxRecordCheck(hostname);
	}

	/**
	 * hostname mx Record Check
	 *
	 * @param hostname
	 * @return
	 */
	private boolean mxRecordCheck(String hostname) {
		try {
			final String mxStr = "MX";
			Hashtable<String, String> hashtable = new Hashtable<>();

			hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			hashtable.put("com.sun.jndi.dns.timeout.initial", "5000");    /* quite short... too short? */
			hashtable.put("com.sun.jndi.dns.timeout.retries", "1");

			DirContext ictx = new InitialDirContext(hashtable);

			Attributes attrs = ictx.getAttributes(hostname, new String[] { mxStr });
			Attribute attr = attrs.get(mxStr);

			if (attr == null || attr.size() == 0) {
				return false; //없음
			}

			NamingEnumeration e = attr.getAll();

			while (e.hasMore()) {
				String mxs = String.valueOf(e.next());
				String[] mx = mxs.split("\\s+");

				for (String mxString : mx) {
					if (StringUtils.endsWith(mxString, ".")) {
						return true;
					}
				}
			}

		} catch (NamingException ne) {
			return false;
		}

		return false;
	}
}
