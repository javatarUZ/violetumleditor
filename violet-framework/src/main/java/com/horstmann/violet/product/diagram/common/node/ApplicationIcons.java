package com.horstmann.violet.product.diagram.common.node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksander Orchowski comodsuda@gmail.com
 * LAST TOUCH : Aleksander Orchowski
 */
public enum ApplicationIcons
{
    DATABASE, API;

    
    /**
     * @return path to icon resource
     */
    public String getIconPath()
    {
        return ICON_FOLDER + ICONS.get(super.toString()).getKey();
    }
    
    /**
     * @return translationKey
     */
    public String getIconTranslationKey()
    {
        return ICONS.get(super.toString()).getValue();
    }


    private final String ICON_FOLDER = "/icons/";
    private final Map<String, Pair<String,String>> ICONS= new HashMap<String, Pair<String,String>>()
    {
        {
            put("DATABASE", new Pair<String,String>("128x128/database.jpg","icon.database"));
            put("API", new Pair<String,String>("128x128/api.jpg","icon.api"));
        }
    };
}

class Pair<K,V>{
    
    public K getKey()
    {
        return key;
    }
    public V getValue()
    {
        return value;
    }
    
    public Pair(K key, V value)
    {
        super();
        this.key = key;
        this.value = value;
    }
    private K key;
    private V value;
} 
