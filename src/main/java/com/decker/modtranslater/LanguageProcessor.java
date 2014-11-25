/*
 * The MIT License
 *
 * Copyright 2014 Decker.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.decker.modtranslater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class LanguageProcessor {

    protected final String defalutContentMatcher;
    protected final String defalutLineSpliter;
    protected final String defaultKeyValueSpliter;

    public LanguageProcessor() {
        this.defalutContentMatcher = "(?<=languagefile \\{)([\\s\\S]*)*(?=\\})";
        this.defalutLineSpliter = System.lineSeparator();
        this.defaultKeyValueSpliter = "=";
    }

    /**
     * @return the contentMatcher
     */
    public String getContentMatcher() {
        if (StringUtils.isEmpty(Configuration.getConfig("CONTENT_MATCHER"))) {
            return this.defalutContentMatcher;
        } else {
            return Configuration.getConfig("CONTENT_MATCHER");
        }
    }

    /**
     * @return the lineSpliter
     */
    public String getLineSpliter() {
        if (StringUtils.isEmpty(Configuration.getConfig("LINE_SPITER"))) {
            return defalutLineSpliter;
        } else {
            return Configuration.getConfig("LINE_SPITER");
        }

    }

    /**
     * @return the keyValueSpliter
     */
    public String getKeyValueSpliter() {
        if (StringUtils.isEmpty(Configuration.getConfig("CONTENT_KV_SPITER"))) {
            return defaultKeyValueSpliter;
        } else {
            return Configuration.getConfig("CONTENT_KV_SPITER");
        }
    }

    protected String digContent(String source) throws Exception {
        Matcher matcher = Pattern.compile(this.getContentMatcher()).matcher(source);
        if (!matcher.find()) {
            throw new Exception("Cant match any content");
        }
        return matcher.group();
    }
}
