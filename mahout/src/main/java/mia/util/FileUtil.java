package mia.util;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for file operator.
 * @author plz
 */
public final class FileUtil {

    private static final Logger LOG = LoggerFactory
            .getLogger(FileUtil.class);

    private FileUtil() {
        throw new IllegalAccessError("Try initlize FileUtil class.");
    }

    /**
     * 从 classpath 为基础路径获得文件.
     * @param pathUnderClasspath 文件相对于 classpath 的相对路径.
     * @return 文件引用；如有异常发生则返回null.
     */
    public static File getFile(String pathUnderClasspath) {
        if(StringUtils.isBlank(pathUnderClasspath)) {
            return null;
        }
        try {
            File file = new File(
                    Thread.currentThread().getContextClassLoader()
                            .getResource(pathUnderClasspath).toURI());
            return file;
        } catch(URISyntaxException e) {
            LOG.warn("URI transmission error.", e);
            return null;
        } catch(Exception e) {
            LOG.warn("Unknow exception", e);
            return null;
        }
    }

}
