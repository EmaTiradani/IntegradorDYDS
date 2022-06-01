package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CatalogLocalModelImpl implements CatalogLocalModel{

    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();
    private String errorMessage = "";


    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getSave(String title) {
        return DataBase.getExtract(title);
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        try {
            DataBase.saveInfo(title, body);
            errorMessage = "Saved changes";
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

    @Override
    public void addListener(CatalogModelListener listener) {
        listeners.add(listener);
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
