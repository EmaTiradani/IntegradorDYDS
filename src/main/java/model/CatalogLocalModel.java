package model;

public interface CatalogLocalModel {

    void addListener(CatalogLocalModelListener listener);

    String getMessage();

    String getErrorMessage();

    String getSave(String name);

    boolean saveArticleChanges(String title, String body);

    boolean saveArticle(String title, String body);

    boolean deleteArticle(String title);

    String[] getStoredTitles();
}
