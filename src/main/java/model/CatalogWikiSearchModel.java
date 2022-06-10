package model;

import java.util.ArrayList;

public interface CatalogWikiSearchModel {

    void addListener(CatalogWikiSearchModelListener listener);

    String getErrorMessage();

    String getExtract2();

    void searchOnWiki(String title);

    String searchExtract(SearchResult searchResult);

    String getExtract(SearchResult searchResult);

    void setSearchMode(boolean onlyIntro);

    ArrayList<SearchResult> getPreliminaryResults();
}
