/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryrmiclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import api.Dictionary;
import api.Word;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AddWordController implements Initializable {

    @FXML
    public TextField wordTextField;
    
    @FXML
    public TextArea definitionTextArea;
    
    @FXML
    public ProgressIndicator progress;
    
    /**
     * Initializes the controller class.
     */
    Registry registry;
    Dictionary dictionary;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            
            registry = LocateRegistry.getRegistry("localhost", 6789);
            
        } catch (RemoteException ex) {
            Logger.getLogger(WordPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        progress.setVisible(false);
    }   
    
    
    @FXML
    public void onAddWordClicked(ActionEvent event) throws IOException{
        
        String[] defList = definitionTextArea.getText().split("#");
        new AddWord(wordTextField.getText(), definitionTextArea.getText().split("#")).start();
        
    }
    
    
    @FXML
    public void onBackClicked(ActionEvent event) throws IOException{
        Parent addWordParent = FXMLLoader.load(getClass().getResource("WordPage.fxml"));
        Scene addWordScene = new Scene(addWordParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(addWordScene);
        window.show();
    }
    
    class AddWord extends Thread{
        String word;
        String[] defList;
        public AddWord(String word, String[] defList){
            this.word = word;
            this.defList = defList;
        }
        
        @Override
        public void run() {
            try{
                progress.setVisible(true);
                progress.setProgress(0.1);
                dictionary = (Dictionary) registry.lookup("dictionary");
                progress.setProgress(0.3);
                List<String> defListFin = new ArrayList<String>();
                 progress.setProgress(0.4);
                for(String def: this.defList){
                    defListFin.add(def);
                }
                progress.setProgress(1);
                dictionary.addWord(new Word(word, defListFin));
            }
            catch(Exception ex){
                
            }
        }
    }
    
}
