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
package com.decker.modtranslater.dict;

import com.decker.modtranslater.Configuration;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Decker
 */
public class Dictionary extends LinkedHashMap<String, String> {
    
    public void writeToFile(File targetFile) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String key : this.keySet()) {
            builder.append(String.format("%s=>%s%s", key, this.get(key), Configuration.getConfig("LINE_SPITER")));
        }
        FileUtils.writeStringToFile(targetFile, builder.toString());
    }
    
    public void writeToFile(String path) throws IOException {
        this.writeToFile(FileUtils.getFile(path));
    }
}
