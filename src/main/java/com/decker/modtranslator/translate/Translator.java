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
package com.decker.modtranslator.translate;

import com.decker.modtranslator.LanguageProcessor;
import com.decker.modtranslator.Log;
import com.decker.modtranslator.dict.Dictionary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class Translator extends LanguageProcessor {

    ArrayList<Dictionary> dictionaries;

    public Translator() {

        this.dictionaries = new ArrayList<>();
    }

    public void loadDictionary(Dictionary dict) {
        this.dictionaries.add(dict);
    }

    /**
     * Translate lang file according dictionaries
     *
     * @param source source lang file wating for translate
     * @return lang file translated
     */
    public String translate(String source) throws Exception {

        String sourceContent = this.digContent(source);
        String[] content = StringUtils.splitByWholeSeparator(sourceContent, this.getLineSpliter());

        LinkedHashMap<String, String> sourceHashMap = new LinkedHashMap<>();
        for (String line : content) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            try {
                String key = line.substring(0, line.indexOf(this.getKeyValueSpliter()));
                String value = line.substring(line.indexOf(this.getKeyValueSpliter()) + 1, line.length());
                sourceHashMap.put(key, value);
            } catch (Exception e) {
                Log.error("Cant split source keys and values to hashmap", e);
            }

        }

        ArrayList<Dictionary> reverseDictionaries = this.dictionaries;
        Collections.reverse(reverseDictionaries);

        LinkedHashMap<String, String> translated = new LinkedHashMap<>();

        for (Dictionary dictionary : reverseDictionaries) {
            ArrayList<String> reverseKeySet = new ArrayList<String>(dictionary.keySet());
            Collections.reverse(reverseKeySet);
            for (String word : reverseKeySet) {
                for (String key : sourceHashMap.keySet()) {
                    String value = sourceHashMap.get(key);
                    //If that value already been put to the "translated" then replace the string in the translated
                    if (!translated.containsKey(key)) {
                        value = value.replaceAll(word, dictionary.get(word));
                    } else {
                        Matcher m = Pattern.compile(word).matcher(value);
                        if (!m.find()) {
                            continue;
                        } else {
                            value = translated.get(key).replaceAll(m.group(), dictionary.get(word));
                        }

                    }
                    translated.put(key, value);

                }
            }
        }

        StringBuilder translatedContent = new StringBuilder();
        for (String key : translated.keySet()) {
            translatedContent.append(
                    String.format(
                            "%s%s%s%s",
                            key, this.getKeyValueSpliter(),
                            translated.get(key),
                            this.getLineSpliter()
                    )
            );
        }
        return source.replace(sourceContent, translatedContent.toString());
    }

}
