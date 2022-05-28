package presenter;

import model.CatalogModel;

public interface CatalogPresenter {

    void start();

    void onEventSearch();

    void onEventShowSaved();

    void onEventDeleteArticle();

    void onEventSaveChanges();

    void onEventSaveArticle();


}
