/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decker.modtranslater.dict;

import com.decker.modtranslater.Configuration;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.Regexp;

/**
 *
 * @author Decker
 */
public class DictionaryBuilder {

    private final String defalutContentMatcher;
    private final String defalutLineSpliter;
    private final String defaultKeyValueSpliter;

    public DictionaryBuilder() {
        this.defalutContentMatcher = "(?<=languagefile \\{)([\\s\\S]*)*(?=\\})";
        this.defalutLineSpliter = System.lineSeparator();
        this.defaultKeyValueSpliter = "=";
    }

    public Dictionary buildDictionary(File sourceTransltionFile) throws Exception {
        return this.buildDictionary(FileUtils.readFileToString(sourceTransltionFile));
    }

    public Dictionary buildDictionary(String sourceTransltionString) throws Exception {

        Dictionary dictionary = new Dictionary();

        Matcher matcher = Pattern.compile(this.getContentMatcher()).matcher(sourceTransltionString);
        if (!matcher.find()) {
            throw new Exception("Cant match any content");
        }
        String[] content = StringUtils.split(matcher.group(), this.getLineSpliter());

        for (String line : content) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String key = line.substring(0, line.indexOf(this.getKeyValueSpliter()));
            String value = line.substring(line.indexOf(this.getKeyValueSpliter()) + 1, line.length());

            if (!StringUtils.isEmpty(value)) {
                dictionary.put(value, value);
            }
        }

        return dictionary;
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

}
