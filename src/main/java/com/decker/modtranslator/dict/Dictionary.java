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
package com.decker.modtranslator.dict;

import com.decker.modtranslator.Configuration;
import com.decker.modtranslator.Log;
import java.io.File;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.LinkedHashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class Dictionary extends LinkedHashMap<String, String> {

    public void loadFromFile(String dictFilepath) throws IOException {
        this.loadFromFile(FileUtils.getFile(dictFilepath));
    }

    public void loadFromFile(File dictFile) throws IOException {
        String[] content = StringUtils.splitByWholeSeparator(FileUtils.readFileToString(dictFile, "UTF-8"), Configuration.getConfig("LINE_SPITER"));
        for (String line : content) {
            if (line.charAt(0) == '#') {
                continue;
            }
            if (StringUtils.isEmpty(line)) {
                Log.error(String.format("Encountered a error that line seems empty when split line:%s", line));
                continue;
            }
            String[] keyAndValue = StringUtils.splitByWholeSeparator(line, "-->");
            if (keyAndValue.length > 2) {
                Log.error(String.format("Encountered a error when split key and value from line:%s", line));
                continue;
            } else if (keyAndValue.length == 2) {
                this.put(keyAndValue[0], keyAndValue[1]);
            } else if (keyAndValue.length == 1) {
                if (StringUtils.indexOf(line, "-->") == 1) {
                    this.put(keyAndValue[0], "");
                } else {
                    Log.error(String.format("Encountered a error that seems key is empty when split line:%s", line));
                    continue;
                }
            }

        }
    }

    public void writeToFile(File targetFile) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String key : this.keySet()) {
            builder.append(String.format("%s-->%s%s", key, this.get(key), Configuration.getConfig("LINE_SPITER")));
        }
        FileUtils.writeStringToFile(targetFile, builder.toString());
    }

    public void writeToFile(String path) throws IOException {
        this.writeToFile(FileUtils.getFile(path));
    }
}
