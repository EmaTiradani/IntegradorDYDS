package presenter;

import view.SearchView;

public interface CatalogSearchPresenter {

    void start();

    void setView(SearchView view);

    void onEventSearch();

    void onEventChooseOnlyIntro();

    void onEventSaveArticle();

    void onEventLoadArticle();

}
