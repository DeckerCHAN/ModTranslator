/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decker.modtranslator;

import com.decker.modtranslator.dict.Dictionary;
import com.decker.modtranslator.dict.DictionaryBuilder;
import com.decker.modtranslator.translate.Translator;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Decker
 */
public class Start {

    public static void main(String[] args) throws Exception {

        Options options = new Options();

        options.addOption(new Option("g", false, "use dictionary mode"));
        options.addOption(new Option("s", "source", true, "source file"));
        options.addOption(new Option("t", "target", true, "target file"));
        options.addOption(new Option("l", "spilt", true, "line spliter(defalult is system line spilte)"));
        options.addOption(new Option("k", "spliter", true, "the spliter for key and value"));
        options.addOption(new Option("f", "content", true, "a filter to specify language file content"));
        options.addOption(new Option("d", "dictionaries", true, "specify your dictonaries for translation(using format {dict1,dict2})"));

        options.addOption(new Option("g", false, "dictionary generate mode"));

        options.addOption("h", "help", false, "print help for the usage");

        CommandLine cli;
        try {

            cli = new BasicParser().parse(options, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("Mod translator", options, true);
            return;
        }
        if (cli.hasOption("h")) {
            new HelpFormatter().printHelp("Mod translator", options, true);
            return;
        } else if (!(cli.hasOption('t') && cli.hasOption('s'))) {
            System.out.println("You have to specify source file and target file at least!");
            new HelpFormatter().printHelp("Mod translator", options, true);
            return;
        }
        //Set configurations
        Configuration.setConfig("DICT_KV_SPITER", "=>");
        Configuration.setConfig("CONTENT_KV_SPITER", cli.getOptionValue('k'));
        Configuration.setConfig("LINE_SPITER", StringUtils.isEmpty(cli.getOptionValue('l')) ? System.lineSeparator() : cli.getOptionValue('l'));
        Configuration.setConfig("CONTENT_MATCHER", cli.getOptionValue('f'));

        if (cli.hasOption('g')) {
            //Generate dictionary
            Log.info("Dictionary Generation Task");
            Log.info(String.format("Source file:%s  Target file:%s", cli.getOptionValue('s'), cli.getOptionValue('t')));

            DictionaryBuilder builder = new DictionaryBuilder();
            Dictionary dictionary = builder.buildDictionary(
                    FileUtils.getFile(
                            cli.getOptionValue('s')
                    )
            );
            Log.info(String.format("Dictionary generated! Items: %s", dictionary.keySet().size()));
            dictionary.writeToFile(cli.getOptionValue('t'));
            Log.info(String.format("Wroted to file %s!", cli.getOptionValue('t')));
            return;
        } else if (!cli.hasOption('g') || cli.hasOption('d')) {

            Translator translator = new Translator();
            for (String dictFilePath : StringUtils.split(StringUtils.substring(cli.getOptionValue('d'), 1, -1), ',')) {
                Dictionary dict = new Dictionary();
                dict.loadFromFile(dictFilePath);
                translator.loadDictionary(dict);
            }
            String translated = translator.translate(
                    FileUtils.readFileToString(
                            FileUtils.getFile(
                                    cli.getOptionValue('s')
                            ), "UTF-8"
                    )
            );
            //Write result to target file 
            FileUtils.writeStringToFile(
                    FileUtils.getFile(cli.getOptionValue('t')),
                    translated,"UTF-8"
            );
        }

    }
}
