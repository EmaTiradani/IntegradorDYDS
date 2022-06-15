package model;

import model.listeners.CatalogLocalModelListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CatalogLocalModelImpl implements CatalogLocalModel{

    private ArrayList<CatalogLocalModelListener> listeners = new ArrayList<>();
    private String message = "";


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public String getSave(String title) {
        return DataBase.getExtract(title);
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        try {
            DataBase.saveInfo(title, body);
            notifySaveListener();
        } catch (SQLException e) {
            message = "Error saving changes";
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
            message = "Error saving article";
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

    @Override
    public void addListener(CatalogLocalModelListener listener) {
        listeners.add(listener);
    }

    private void notifySaveListener(){
        for(CatalogLocalModelListener listener: listeners){
            listener.didSaveLocally();
        }
    }

    private void notifyDeleteListener(){
        for(CatalogLocalModelListener listener: listeners){
            listener.didDeleteSave();
        }
    }

    private void notifyFoundSave(){
        for(CatalogLocalModelListener listener: listeners){
            listener.foundSave();
        }
    }

    private void notifyError(){
        for(CatalogLocalModelListener listener: listeners) {
            listener.didThrowException();
        }
    }
}
