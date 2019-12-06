/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryrmiserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import api.Dictionary;
import java.rmi.RemoteException;

/**
 *
 * @author hp
 */
public class DictionaryRMIServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(6789);
        
        DictionaryImpl dictionaryImpl = new DictionaryImpl();
        
        Dictionary skeleton = (Dictionary) UnicastRemoteObject.exportObject(dictionaryImpl, 0);
        
        registry.rebind("dictionary", skeleton);
        
        System.out.println("Server is running");
    }
    
}
