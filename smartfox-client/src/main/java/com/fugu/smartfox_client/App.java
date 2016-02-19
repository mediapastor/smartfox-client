package com.fugu.smartfox_client;

import com.fugu.smartfox_client.Connector;;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        Connector c = new Connector();
        c.start();
    }
}
