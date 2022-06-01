package model;
import java.util.ArrayList;

public interface CatalogModel {

    void addListener(CatalogModelListener listener);

    String getErrorMessage();

    String getSave(String name);

    boolean saveArticleChanges(String title, String body);

    boolean saveArticle(String title, String body);

    boolean deleteArticle(String title);

    String[] getStoredTitles();

    void searchOnWiki(String title);

    String getExtract(SearchResult searchResult);

    String getExtract2();

    void setSearchMode(boolean onlyIntro);

    ArrayList<SearchResult> getPreliminaryResults();
}
