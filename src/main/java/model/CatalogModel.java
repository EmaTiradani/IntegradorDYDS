package model;

public interface CatalogModel {

    void addListener(CatalogModelListener listener);

    String getSearchResult();

    String getSave(String name);

    boolean saveArticleChanges(String title, String body);

    boolean saveArticle(String title, String body);
}
