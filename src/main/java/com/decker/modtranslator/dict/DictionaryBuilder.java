/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decker.modtranslator.dict;

import com.decker.modtranslator.Configuration;
import com.decker.modtranslator.LanguageProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.Regexp;

/**
 *
 * @author Decker
 */
public class DictionaryBuilder extends LanguageProcessor {

    public DictionaryBuilder() {

    }

    public Dictionary buildDictionary(File sourceTransltionFile) throws Exception {
        return this.buildDictionary(FileUtils.readFileToString(sourceTransltionFile,"UTF-8"));
    }

    public Dictionary buildDictionary(String sourceTransltionString) throws Exception {

        Dictionary dictionary = new Dictionary();

        String[] content = StringUtils.splitByWholeSeparator(this.digContent(sourceTransltionString), this.getLineSpliter());

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

}
