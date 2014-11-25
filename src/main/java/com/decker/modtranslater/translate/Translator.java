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
package com.decker.modtranslater.translate;

import com.decker.modtranslater.LanguageProcessor;
import com.decker.modtranslater.dict.Dictionary;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        String[] content = StringUtils.split(sourceContent, this.getLineSpliter());
        LinkedHashMap<String, String> translated = new LinkedHashMap<>();
        for (Dictionary dictionary : this.dictionaries) {
            for (String word : dictionary.keySet()) {
                for (int i = 0; i < content.length; i++) {
                    String key = content[i].substring(0, content[i].indexOf(this.getKeyValueSpliter()));
                    String value = content[i].substring(content[i].indexOf(this.getKeyValueSpliter()) + 1, content[i].length());
                    //If that value already been put to the "translated" then replace the string in the translated
                    if (translated.containsKey(key)) {
                        value = translated.get(key).replaceAll(word, dictionary.get(word));
                    } else {
                        value = value.replaceAll(word, dictionary.get(word));
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
