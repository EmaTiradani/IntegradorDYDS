package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.gourmetCatalog.fulllogic.WikipediaPageAPI;
import dyds.gourmetCatalog.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class CatalogModelImpl implements CatalogModel {

    //todo estas 2 variables estan re rancias, hay que ver como volarlas
    String selectedResultTitle = null;
    String lastSearchedText = "";
    private DataBase dataBase;

    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();

    public void setDataBase(DataBase dataBase){
        this.dataBase=dataBase;
    }

    @Override
    public void searchOnWiki(String title){

        WikiSearch searcher = new WikiSearch();
        lastSearchedText = searcher.search(title);
        notifySearchListener();
        //searchOptionsMenu.show(textField1, textField1.getX(), textField1.getY());
        //} catch (IOException e1) { Esta parte eran las excepciones de IO, que el modelo no tiene nada que ver
        //    e1.printStackTrace();
        //}
    }

    @Override
    public String getSave(String title) {
        //comboBox1.addActionListener(actionEvent -> textPane2.setText(textToHtml(DataBase.getExtract(comboBox1.getSelectedItem().toString()))));
        return dataBase.getExtract(title);
    }

    @Override
    public String getSearchResult(){
        return lastSearchedText;
    }

    @Override
    public SearchResult[] getPreliminaryResults() {
        SearchResult dummy = new SearchResult("Hola","1","see");
        SearchResult[] searchResults = {dummy};
        return searchResults;
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        //DataBase.saveInfo(comboBox1.getSelectedItem().toString().replace("'", "`"), textPane2.getText());  //Dont forget the ' sql problem
        DataBase.saveInfo(title.replace("'", "`"), body);//Todo hacer que lo de comillas lo haga la database
        notifySaveListener();
        return true; //Que retorne falso si la database no lo pudo meter o algo asi.
    }

    @Override
    public boolean saveArticle(String title, String body) {
        /*if(text != ""){
            // save to DB  <o/
            dyds.gourmetCatalog.fulllogic.  //Dont forget the ' sql problem
            comboBox1.setModel(new DefaultComboBoxModel<Object>(dyds.gourmetCatalog.fulllogic.DataBase.getTitles().stream().sorted().toArray()));*/
        DataBase.saveInfo(title.replace("'", "`"), body);
        notifySaveListener();
        return true;
    }

    @Override
    public boolean deleteArticle(String title) {
        /*if(comboBox1.getSelectedIndex() > -1){
            DataBase.deleteEntry(comboBox1.getSelectedItem().toString());
            comboBox1.setModel(new DefaultComboBoxModel<Object>(DataBase.getTitles().stream().sorted().toArray()));
            textPane2.setText("");*/
        DataBase.deleteEntry(title);
        notifyDeleteListener();
        return true;
    }

    @Override
    public String[] getStoredTitles() {
        Object[] titles = DataBase.getTitles().stream().sorted().toArray();
        String[] titlesAsStringArray = Arrays.copyOf(titles, titles.length, String[].class);
        return titlesAsStringArray;
    }


    public void addListener(CatalogModelListener listener){
        this.listeners.add(listener);
    }


    private void notifySearchListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSearchOnWiki();
        }
    }
    private void notifySelectSearchListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSelectSearchOption();
        }
    }
    private void notifySaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSaveLocally();
        }
    }
    private void notifySelectSaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSelectSavedSearch();
        }
    }
    private void notifyDeleteListener(){
        for(CatalogModelListener listener: listeners){
            listener.didDeleteSave();
        }
    }






    public static String textToHtml(String text) {

        StringBuilder builder = new StringBuilder();

        builder.append("<font face=\"arial\">");

        String fixedText = text
                .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

        builder.append(fixedText);

        builder.append("</font>");

        return builder.toString();
    }
}
