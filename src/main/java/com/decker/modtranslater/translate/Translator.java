/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decker.modtranslater.translate;

import com.decker.modtranslater.dict.Dictionary;
import java.util.ArrayList;

/**
 *
 * @author Decker
 */
public class Translator {

    ArrayList<Dictionary> dictionaries;

    public Translator() {
        this.dictionaries = new ArrayList<>();
    }

    public void loadDictionary(Dictionary dict) {
        this.dictionaries.add(dict);
    }

    /**
     * Translate lang file according dictionaries
     * @param source source lang file wating for translate
     * @return lang file translated
     */
    public String translate(String source) {
        
        
        
        return null;
    }

}
