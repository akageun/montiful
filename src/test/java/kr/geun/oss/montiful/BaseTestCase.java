package kr.geun.oss.montiful;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * BaseTestCase
 *
 * @author akageun
 * @since 2021-02-14
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class BaseTestCase {
}
