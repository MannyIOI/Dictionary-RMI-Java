/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.json.JSONException;

/**
 *
 * @author hp
 */
public interface Dictionary extends Remote{
    
    public Word addWord(Word word) throws RemoteException, JSONException, IOException;
    
    public Word getWordDefinition(String word) throws RemoteException, JSONException, IOException;
    
    public Word addDefinition(Word word) throws RemoteException, JSONException, IOException;
     
    public Word removeDefintion(String word) throws RemoteException, JSONException, IOException;
     
    public ArrayList<Word> getAllWords() throws RemoteException, JSONException, IOException;

}
