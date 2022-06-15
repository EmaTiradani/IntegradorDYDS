package model;

import model.listeners.CatalogLocalModelListener;
import model.listeners.CatalogWikiSearchModelListener;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogWikiSearchModelImpl implements CatalogWikiSearchModel{

    WikiSearch searcher = new WikiSearch();
    ArrayList<SearchResult> results = new ArrayList<>();
    private String extract;
    private ArrayList<CatalogWikiSearchModelListener> listeners = new ArrayList<>();
    private String errorMessage = "";
    private CatalogLocalModel localModel;

    @Override
    public void setSearchModel(CatalogLocalModel localModel){
        this.localModel = localModel;
    }

    @Override
    public void addListener(CatalogWikiSearchModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void searchOnWiki(String title) {
        try {
            results = searcher.search(title);
        } catch (IOException e) {
            //e.printStackTrace();
            errorMessage = "Error searching on Wikipedia";
            notifyError();
        }
        notifySearchListener();
    }

    @Override
    public void setSearchEngine(WikiSearch searcher) {
        this.searcher = searcher;
    }

    @Override
    public String getExtract() {
        return extract;
    }

    @Override
    public String searchExtract(SearchResult result){
        extract = searcher.getExtract(result);
        notifySelectSearchListener();
        return extract;
    }

    @Override
    public void setSearchMode(boolean onlyIntro) {
        searcher.toggleFullArticle(onlyIntro);
    }

    @Override
    public boolean getSearchMode() {
        return searcher.getArticleMode();
    }

    @Override
    public ArrayList<SearchResult> getPreliminaryResults() {
        return results;
    }

    @Override
    public boolean saveArticle(String title, String body) {
        localModel.saveArticle(title, body);
        return true;
    }

    private void notifyError(){
        for(CatalogWikiSearchModelListener listener: listeners) {
            listener.didThrowException();
        }
    }
    private void notifySelectSearchListener(){
        for(CatalogWikiSearchModelListener listener: listeners){
            listener.didSearchExtract();
        }
    }
    private void notifySearchListener() {
        for(CatalogWikiSearchModelListener listener: listeners){
            listener.didSearchOnWiki();
        }
    }

}
