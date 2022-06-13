package presenter;

import model.WikiSearch;
import view.MainWindow;

public interface CatalogPresenter {

    void start();

    void setView(MainWindow view);

    void onEventSearch();

    void onEventShowSaved();

    void onEventDeleteArticle();

    void onEventSaveChanges();

    void onEventSaveArticle();

    void onEventLoadArticle();

    void onEventChooseOnlyIntro();
}
