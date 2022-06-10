package model;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogWikiSearchModelImpl implements CatalogWikiSearchModel{

    WikiSearch searcher = new WikiSearch();
    ArrayList<SearchResult> results = new ArrayList<>();
    private String extract;
    private ArrayList<CatalogWikiSearchModelListener> listeners = new ArrayList<>();
    private String errorMessage = "";

    @Override
    public void addListener(CatalogWikiSearchModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getExtract2() {
        return extract;
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
    public String searchExtract(SearchResult searchResult) {
        extract = searcher.getExtract(searchResult);
        notifySelectSearchListener();
        return extract;
    }

    @Override
    public String getExtract(SearchResult result){
        extract = searcher.getExtract(result);
        notifySelectSearchListener();
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
