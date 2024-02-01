package org.pokergame.utils;

public class PathUtil {
    private static final String BASE_PATH = "/org/pokergame/";

    public String getImage(String filename){
        return (BASE_PATH + "images/" + filename);
    }


    public String getSound(String filename){
        return (BASE_PATH + "sounds/" + filename);
    }
}
