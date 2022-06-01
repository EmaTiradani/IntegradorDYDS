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

    ArrayList<SearchResult> results = new ArrayList<>();
    WikiSearch searcher = new WikiSearch();

    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();

    private String extract;

    public String getExtract2(){
        return extract;
    }

    @Override
    public void setSearchMode(boolean onlyIntro) {
        searcher.toggleFullArticle(onlyIntro);
    }

    @Override
    public void searchOnWiki(String title){
        results = searcher.search(title);
        notifySearchListener();
    }

    @Override
    public String getExtract(SearchResult result){
        extract = searcher.getExtract(result);
        notifySelectSearchListener();
        return extract;
    }

    @Override
    public String getSave(String title) {
        return DataBase.getExtract(title);
    }

    @Override
    public ArrayList<SearchResult> getPreliminaryResults() {
        return results;
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        DataBase.saveInfo(title, body);
        notifySaveListener();
        return true; //Que retorne falso si la database no lo pudo meter o algo asi.
    }

    @Override
    public boolean saveArticle(String title, String body) {
        DataBase.saveInfo(title, body);
        notifySaveListener();
        return true;
    }

    @Override
    public boolean deleteArticle(String title) {
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
            listener.didSearchExtract();
        }
    }
    private void notifySaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSaveLocally();
        }
    }
    /*private void notifySelectSaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSelectSavedSearch();
        }
    }*/
    private void notifyDeleteListener(){
        for(CatalogModelListener listener: listeners){
            listener.didDeleteSave();
        }
    }

}
