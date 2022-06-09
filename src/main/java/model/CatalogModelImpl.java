package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class CatalogModelImpl implements CatalogModel {

    ArrayList<SearchResult> results = new ArrayList<>();
    WikiSearch searcher = new WikiSearch();
    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();
    private String errorMessage = "";
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
        try {
            results = searcher.search(title);
        } catch (IOException e) {
            //e.printStackTrace();
            errorMessage = "Failed search";
            notifyError();
        }
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
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public ArrayList<SearchResult> getPreliminaryResults() {
        return results;
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        try {
            DataBase.saveInfo(title, body);
            notifySaveListener();
        } catch (SQLException e) {
            //e.printStackTrace();
            errorMessage = "Error saving changes";
            notifyError();
        }
        return true;
    }

    @Override
    public boolean saveArticle(String title, String body) {
        try {
            DataBase.saveInfo(title, body);
            notifySaveListener();
        } catch (SQLException e) {
            errorMessage = "Error saving article";
            notifyError();
        }
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

    private void notifyDeleteListener(){
        for(CatalogModelListener listener: listeners){
            listener.didDeleteSave();
        }
    }

    private void notifyError(){
        for(CatalogModelListener listener: listeners) {
            listener.didThrowException();
        }

    }

}
