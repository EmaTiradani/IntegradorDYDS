package model;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogWikiSearchModelImpl implements CatalogWikiSearchModel{

    WikiSearch searcher = new WikiSearch();
    ArrayList<SearchResult> results = new ArrayList<>();
    private String extract;
    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();
    private String errorMessage = "";

    @Override
    public void addListener(CatalogModelListener listener) {
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
            errorMessage = "ERROR AAAAAAAAAAAAAAAA";
            notifyError();
        }
        notifySearchListener();
    }

    @Override
    public String searchExtract(SearchResult searchResult) {
        extract = searcher.getExtract(searchResult);
        notifySelectSearchListener();
        return extract;
    }

    @Override
    public String getExtract() {
        return extract;
    }

    @Override
    public void setSearchMode(boolean onlyIntro) {
        searcher.toggleFullArticle(onlyIntro);
    }

    @Override
    public ArrayList<SearchResult> getPreliminaryResults() {
        return results;
    }

    private void notifyError(){
        for(CatalogModelListener listener: listeners) {
            listener.didThrowException();
        }
    }
    private void notifySelectSearchListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSearchExtract();
        }
    }
    private void notifySearchListener() {
        for(CatalogModelListener listener: listeners){
            listener.didSearchOnWiki();
        }
    }
}
