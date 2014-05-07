package com.ecarinfo.auto.generate.persist;

import com.ecarinfo.util.MetaUtil;

public class App 
{
    public static void main( String[] args )
    {    	
    	execute("ec-persist.properties");
    }
    
    static void execute(String configure) {
    	new MetaUtil(configure).execute();
    }
}