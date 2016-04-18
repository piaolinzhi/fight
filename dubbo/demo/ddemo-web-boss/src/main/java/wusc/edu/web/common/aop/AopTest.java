package wusc.edu.web.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AopTest {

    private static final Logger LOG = LoggerFactory
            .getLogger(AopTest.class);

    public void before() {
        LOG.error("Aop before succeed");
    }

    public void afterThrow(Exception e) {
        LOG.error(e.getMessage(), e);
    }

}
