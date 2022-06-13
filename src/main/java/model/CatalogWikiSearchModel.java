package model;

import model.listeners.CatalogWikiSearchModelListener;

import java.util.ArrayList;

public interface CatalogWikiSearchModel {

    void addListener(CatalogWikiSearchModelListener listener);

    String getErrorMessage();

    String getExtract();

    void searchOnWiki(String title);

    //String searchExtract(SearchResult searchResult);

    void setSearchEngine(WikiSearch searcher);

    String searchExtract(SearchResult searchResult);

    void setSearchMode(boolean onlyIntro);

    boolean getSearchMode();

    ArrayList<SearchResult> getPreliminaryResults();
}
