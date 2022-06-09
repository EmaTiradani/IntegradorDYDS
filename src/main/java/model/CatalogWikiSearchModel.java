package model;

import java.util.ArrayList;

public interface CatalogWikiSearchModel {

    void addListener(CatalogModelListener listener);

    String getErrorMessage();


    void searchOnWiki(String title);

    String searchExtract(SearchResult searchResult);

    String getExtract();

    void setSearchMode(boolean onlyIntro);

    ArrayList<SearchResult> getPreliminaryResults();
}
