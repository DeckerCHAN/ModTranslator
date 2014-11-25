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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class Log {

    private static Log instance;

    private static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public static void info(String log) {
        getInstance().writeToLog("INFO:" + log);
    }

    public static void error(String log) {
        getInstance().writeToLog("ERROR:" + log);
    }

    public static void error(String log, Throwable throwable) {
        getInstance().writeToLog(
                String.format(
                        "ERROR:%s Detail:%s",
                        log,
                        throwable.toString()
                )
        );
    }

    public static void crash(String log, Throwable throwable) {
        getInstance().writeToLog(
                String.format(
                        "CRUSHED:%s Detail:%s",
                        log,
                        throwable.toString()
                )
        );
    }

    StringBuilder logContent;

    public Log() {
        this.logContent = new StringBuilder();
        this.logContent.append(
                String.format(
                        "Log Date:%s %s%s",
                        new Date().toString(),
                        System.lineSeparator(),
                        System.lineSeparator()
                )
        );
    }

    private void writeToLog(String log) {
        if (!StringUtils.isEmpty(log)) {
            System.out.println(log);
            this.logContent.append(
                    String.format(
                            "[%s]:%s%s",
                            new Date().toString(),
                            log, System.lineSeparator()
                    )
            );
        }
    }

    public void writeLogToFile(String filepath) throws IOException {
        File logFile=FileUtils.getFile(filepath);
        if(logFile.exists())
        {
            FileUtils.deleteQuietly(logFile);
        }
        FileUtils.writeStringToFile(logFile, this.logContent.toString());
    }

    public void writeLogToFile() throws IOException {
        this.writeLogToFile(
                String.format(
                        "%s.log",
                        new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(
                                new Date()
                        )
                )
        );
    }

}
