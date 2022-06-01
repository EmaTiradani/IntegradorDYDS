package model;

import java.util.ArrayList;

public interface CatalogWikiSearchModel {

    void addListener(CatalogModelListener listener);

    String getErrorMessage();


    void searchOnWiki(String title);

    String getExtract(SearchResult searchResult);

    String getExtract2();

    void setSearchMode(boolean onlyIntro);

    ArrayList<SearchResult> getPreliminaryResults();
}
