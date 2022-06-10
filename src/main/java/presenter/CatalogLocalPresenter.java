package presenter;

public interface CatalogLocalPresenter {

    void start();

    void onEventShowSaved();

    void onEventDeleteArticle();

    void onEventSaveChanges();

    void onEventSaveArticle();
}
