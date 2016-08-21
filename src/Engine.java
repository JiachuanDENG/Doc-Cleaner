/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


/**
 *
 * @author Victoria
 */


public class Engine {
    
    
    public static Engine getMD5(File dir) {
        return new Engine(Hashing.md5(), dir);
    }
    
    
    public Engine(HashFunction hashfunc, File dir) {
        this.hashfunc = hashfunc;;
        this.dir = dir;
    }
    
    
    public String findDuplicates(App.Updater updater) throws IOException, 
            FileNotFoundException, NullPointerException {
        updater.setPreparing();
        List<File> files = Util.getAllFile(this.dir);
        Util.getHashStr(files.get(0), this.hashfunc);
        
        updater.setTotal(files.size());
        updater.setScanning();
        for (File file : files) {
            updater.setCurrent(file.getAbsolutePath());
            try
            {
                String hashstr = Util.getHashStr(file, this.hashfunc);
                String dir = file.getAbsolutePath();
                if (!hashmap.containsKey(hashstr)) {
                    hashmap.put(hashstr, dir);
                } else if (!this.map.containsKey(hashstr)) {
                    HashSet<String> hashset = new HashSet<String>();
                    hashset.add(dir);
                    this.map.put(hashstr, hashset);
                } else {
                    this.map.get(hashstr).add(dir);
                }
            }
            catch (FileNotFoundException e) {}
        }
        
        for (String key : this.map.keySet()) {
            this.map.get(key).add(this.hashmap.get(key));
        }
        
        updater.setFinished();
        return Util.listToStringBlock(this.toStringList());
    }
    
    
    private List<String> toStringList() {
        List<String> lst = new Vector<String>();
        for (String key : this.map.keySet()) {
            lst.add(key);
            lst.addAll(this.map.get(key));
            lst.add("\n");
        }
        
        return lst;
    }
    
    
    private File dir;
    private HashFunction hashfunc;
    private HashMap<String, String> hashmap = new HashMap<String, String>();
    private Map<String, Set<String>> map = new HashMap<String, Set<String>>();


}
