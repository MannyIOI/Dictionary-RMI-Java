/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hp
 */
public class Word implements Serializable{
    
    private String _word;
    private List<String> _definition;

    public Word(String word){
        setWord(word);
    }
    
    public Word(int id, String word){
        setWord(word);
    }
    
    public Word(int id, ArrayList<String> defs){
//        setId(id);
        setDefinition(defs);
    }
    
    public Word(String word, List<String> definition){
        setWord(word);
        setDefinition(definition);
    }
    
    public String getWord() {
        return _word;
    }

    public final void setWord(String _word) {
        this._word = _word;
    }

    public List<String> getDefinition() {
        return _definition;
    }

    public final void setDefinition(List<String> _definition) {
        this._definition = _definition;
    }
    
    public void addDefinition(String definition){
        this._definition.add(definition);
    }

    public String getString(){
        JSONObject jsonResult = new JSONObject();
        try{
            jsonResult.put("word", getWord());
            jsonResult.put("definition", getDefinition());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return jsonResult.toString();
    }
    
    @Override
    public String toString(){
        return getString();
    }
    
    
}
