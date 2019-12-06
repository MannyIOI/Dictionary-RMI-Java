/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryrmiserver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import api.Word;

/**
 *
 * @author hp
 */
public class Database {
    private final String fileLocation;
    private static Database db;
    private Database(String fileLocation){
        this.fileLocation = fileLocation;
    }
    
    public static Database getInstance(String fileLocation){
        if(db == null) db = new Database(fileLocation);
        return db;
    }
    
    synchronized public Word addWord(Word word) throws JSONException, IOException{
        //TODO
        String text = new String(Files.readAllBytes(Paths.get(this.fileLocation)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);
        
        obj.put(word.getWord(), word.getDefinition());
        
        try(FileWriter file = new FileWriter(this.fileLocation)){
                file.write(obj.toString());
                file.close();
        }
        return new Word(word.getWord(), word.getDefinition());
    }
    
    synchronized public Word getWordDefinition(String word) throws JSONException, IOException{
        //TODO
        String text = new String(Files.readAllBytes(Paths.get(this.fileLocation)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);
        
        JSONArray arr = obj.getJSONArray(word);
        List<String> defs = new ArrayList<>();
        for(int i = 0; i < arr.length(); i++){
            defs.add(arr.getString(i));
        }
        return new Word(word, defs);
    }
    
    synchronized public Word addDefinition(Word word) throws JSONException, IOException{
        //TODO
        String text = new String(Files.readAllBytes(Paths.get(this.fileLocation)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);
        for(int i = 0;i < word.getDefinition().size();i++){
                obj.getJSONArray(word.getWord()).put(word.getDefinition().get(i));
        }
        try(FileWriter file = new FileWriter(this.fileLocation)){
                file.write(obj.toString());
                file.close();
        }
        
        return word;
    }
    
    synchronized public Word removeDefintion(String word) throws JSONException, IOException{
        String text = new String(Files.readAllBytes(Paths.get(this.fileLocation)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);
        
        obj.remove(word);
        try(FileWriter file = new FileWriter(this.fileLocation)){
                file.write(obj.toString());
                file.close();
        }
        return new Word(word);
    }
    
    synchronized public ArrayList<Word> getAllWords() throws JSONException, IOException{
        String text = new String(Files.readAllBytes(Paths.get(this.fileLocation)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(text);
        Iterator<String> keys = obj.keys();
        ArrayList<Word> wordList = new ArrayList<>();
        while(keys.hasNext()) {
            String key = keys.next();
            wordList.add(this.getWordDefinition(key));
        }
        return wordList;
//        return 
    }
    
    
}
