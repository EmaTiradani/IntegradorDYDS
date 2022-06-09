package model;

public interface CatalogLocalModel {

    void addListener(CatalogModelListener listener);

    String getMessage();


    String getSave(String name);

    boolean saveArticleChanges(String title, String body);

    boolean saveArticle(String title, String body);

    boolean deleteArticle(String title);

    String[] getStoredTitles();
}
