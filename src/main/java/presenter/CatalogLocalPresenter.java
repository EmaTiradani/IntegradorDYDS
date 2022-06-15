package presenter;

import view.LocalView;

public interface CatalogLocalPresenter {

    void start();

    void setView(LocalView view);

    void onEventShowSaved();

    void onEventDeleteArticle();

    void onEventSaveChanges();


}
