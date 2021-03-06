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
package com.decker.modtranslator;

import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class Configuration {

    private static Configuration instance;

    private static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private HashMap<String, String> config;

    public Configuration() {
        this.config = new HashMap<>();
    }

    public static String getConfig(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        name = name.toUpperCase();
        return getInstance().config.get(name);
    }

    public static void setConfig(String name, String value) {

        if (!StringUtils.isEmpty(value) && !StringUtils.isEmpty(value)) {
            name = name.toUpperCase();
            value = value.toUpperCase();
            getInstance().config.put(name, value);
        }
    }
}
