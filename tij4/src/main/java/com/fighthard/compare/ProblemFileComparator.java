package com.fighthard.compare;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * @ClassName ProblemFileComparator
 * @Description 展示 commons.lang3 包中提供的工具类的用法
 * @author piaolinzhi
 * @date 2017年4月18日 下午2:02:02
 */
public class ProblemFileComparator implements Comparator<File> {

    private static final AlphaNumericStringComparator ALPHA_NUMERIC_STRING_COMPARATOR = new AlphaNumericStringComparator();

    public int compare(File a, File b) {
        String aLowerCaseName = a.getName().toLowerCase(Locale.SIMPLIFIED_CHINESE);
        String bLowerCaseName = b.getName().toLowerCase(Locale.SIMPLIFIED_CHINESE);
        // CompareToBuilder 用法
        return new CompareToBuilder()
                .append(a.getParent(), b.getParent(), ALPHA_NUMERIC_STRING_COMPARATOR)
                .append(a.isDirectory(), b.isDirectory())
                .append(!aLowerCaseName.startsWith("demo"), !bLowerCaseName.startsWith("demo"))
                .append(aLowerCaseName, bLowerCaseName, ALPHA_NUMERIC_STRING_COMPARATOR)
                .append(a.getName(), b.getName()).toComparison();
    }

}
