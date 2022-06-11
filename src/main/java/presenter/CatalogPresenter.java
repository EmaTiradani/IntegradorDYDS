package presenter;

public interface CatalogPresenter {

    void start();

    void onEventSearch();

    void onEventShowSaved();

    void onEventDeleteArticle();

    void onEventSaveChanges();

    void onEventSaveArticle();

    void onEventLoadArticle();

    void onEventChooseOnlyIntro();
}
