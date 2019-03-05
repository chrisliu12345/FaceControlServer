package com.gd.util;

import java.io.File;

public class testSpeak {
    public static void main(String[] args){
        TextToSpeak textToSpeak=new TextToSpeak();
        File file=new File("E://123");
        if(!file.exists()){
            file.mkdirs();
        }
       // textToSpeak.textSpeak("123","E://123//liu2ng.mp3");
    }
}
