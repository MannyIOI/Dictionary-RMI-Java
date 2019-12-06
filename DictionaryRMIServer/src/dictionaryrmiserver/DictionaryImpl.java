/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryrmiserver;

import api.Dictionary;
import api.Word;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.json.JSONException;
/**
 *
 * @author hp
 */
public class DictionaryImpl implements Dictionary{

    Database db = Database.getInstance("E:\\School Files\\4th\\1st Semester\\Distribution System Programming\\Project\\RMI\\DictionaryRMIServer\\src\\dictionaryrmiserver\\database.json");
    
    @Override
    public Word addWord(Word word) throws RemoteException, JSONException, IOException {
        return db.addWord(word);
    }

    @Override
    public Word getWordDefinition(String word) throws RemoteException, JSONException, IOException {
        return db.getWordDefinition(word);
    }

    @Override
    public Word addDefinition(Word word) throws RemoteException, JSONException, IOException {
        return db.addDefinition(word);
    }

    @Override
    public Word removeDefintion(String word) throws RemoteException, JSONException, IOException {
        return db.removeDefintion(word);
    }

    @Override
    public ArrayList<Word> getAllWords() throws RemoteException, JSONException, IOException {
        return db.getAllWords();
    }
    
   
    
}
