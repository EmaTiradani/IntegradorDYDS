package model;

import model.listeners.CatalogWikiSearchModelListener;

import java.util.ArrayList;

public interface CatalogWikiSearchModel {

    void addListener(CatalogWikiSearchModelListener listener);

    String getErrorMessage();

    String getExtract();

    void searchOnWiki(String title);

    void setSearchEngine(WikipediaSearch searcher);

    String searchExtract(SearchResult searchResult);

    void setSearchMode(boolean onlyIntro);

    ArrayList<SearchResult> getPreliminaryResults();

    boolean saveArticle(String title, String body);

    void setLocalModel(CatalogLocalModel localModel);
}
