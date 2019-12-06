/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryrmiclient;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import api.Dictionary;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import api.Word;
/**
 * FXML Controller class
 *
 * @author hp
 */
public class WordPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String currentWord = "";
    
    @FXML
    private ListView<String> word_list;
    
    @FXML
    private TextField search_bar;
    
    @FXML
    private TextArea definition;
    
    @FXML
    private ProgressIndicator progressListWords;
    
    Dictionary dictionary;
    Registry registry;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            registry = LocateRegistry.getRegistry("localhost", 6789);
            new GetList().start();
        } catch (RemoteException ex) {
            Logger.getLogger(WordPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    @FXML
    public void onAddWordClicked(ActionEvent event) throws IOException{
        Parent addWordParent = FXMLLoader.load(getClass().getResource("AddWord.fxml"));
        Scene addWordScene = new Scene(addWordParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(addWordScene);
        window.show();
    }
    
    @FXML
    public void onItemSelected(MouseEvent ev){
        currentWord = word_list.getSelectionModel().getSelectedItem();
        new GetDefinition(currentWord).start();
    }
    
    @FXML
    public void onRefreshClicked(ActionEvent ev) throws IOException{
        System.out.println("Refreshing");
        new GetList().start();
    }
    
    @FXML
    public void onSearch(KeyEvent ev) throws IOException{
        if(ev.getCharacter().hashCode() == 13){
              // WHEN ENTER CLICKED
              currentWord = search_bar.getText();
              new GetDefinition(currentWord).start();
          }
    }
    
    @FXML
    public void onDeleteWordClicked(ActionEvent ev){
        if(!currentWord.equals("")){
            new RemoveWord(currentWord).start();
        }
    }
    
    class GetList extends Thread{
        @Override
        synchronized public void run() {
            try{
                
                dictionary = (Dictionary) registry.lookup("dictionary");
                progressListWords.setVisible(true);
                progressListWords.setProgress(0.4);
                ArrayList<Word> res = dictionary.getAllWords();
                System.out.println(res);
                progressListWords.setProgress(0.8);
                
                ArrayList<String> wordList = new ArrayList<>();
                for(int i = 0;i < res.size();i++){
                    wordList.add(res.get(i).getWord());
                }
                
                progressListWords.setProgress(0.9);
                ObservableList<String> items = FXCollections.observableArrayList(wordList);
                word_list.setItems(items);
                word_list.refresh();
                progressListWords.setVisible(false);
                
                dictionary.getAllWords();
            }
            catch(RemoteException | JSONException | NotBoundException ex){
                ex.printStackTrace();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    class GetDefinition extends Thread{
        String word;
        Dictionary dictionary;
        public GetDefinition(String word){
            this.word = word;
        }

        @Override
        public void run() {
            try{
                dictionary = (Dictionary) registry.lookup("dictionary");
                Word res = dictionary.getWordDefinition(word);
                String displayString = "Defintion\n\n"+this.word+"\n\t";
                for(int i = 0;i < res.getDefinition().size();i++){
                        int j = i + 1;
                        displayString += j + ".\t" + res.getDefinition().get(i) + "\n\n\t";
                }
                definition.setText(displayString);
            }
            catch(RemoteException | JSONException | NotBoundException ex){
                ex.printStackTrace();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    class RemoveWord extends Thread{
        String word;
        Dictionary dictionary;
        public RemoveWord(String word){
                this.word = word;
        }

        @Override
        public void run() {
            try{
                
                dictionary = (Dictionary) registry.lookup("dictionary");
                dictionary.removeDefintion(word);
                new GetList().start();
            }
            catch(RemoteException | JSONException | NotBoundException ex){
                ex.printStackTrace();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
}
