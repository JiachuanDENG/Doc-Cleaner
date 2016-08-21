/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.google.common.hash.HashFunction;
import com.google.common.io.Files;


/**
 *
 * @author Victoria
 */


public class Util {
    

    private Util() {
        
    }
    
    
    public static List<File> getAllFile(File dir) {
        List<File> all = new Vector<File>();
        getAllFile(dir, all);
        return all;
    }
    
    
    private static void getAllFile(File dir, List<File> files) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                getAllFile(file, files);
            } 
        } else {
            files.add(dir);
        }
    }
    
    
    public static String getHashStr(File file, HashFunction hashfunc) 
            throws IOException {
        return Files.hash(file, hashfunc).toString();
    }
    
    
    public static String listToStringBlock(List<String> lst) {
        if (lst.size() == 0) {
            return "";
        }
        
        int size = 0;
        for (String str : lst) {
            size += str.length() + 1;
        }
        
        StringBuilder sb = new StringBuilder(size);
        for (String str : lst) {
            sb.append(str);
            sb.append('\n');
        }
        
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    } 
    
    
}
