package presenter;

import model.*;
import view.*;

public class Main {

    public static void main(String[] args) {
        DataBase.loadDatabase();



        CatalogLocalModel localModel = new CatalogLocalModelImpl();
        CatalogWikiSearchModel searchModel = new CatalogWikiSearchModelImpl();

        searchModel.setLocalModel(localModel);

        CatalogLocalPresenter localPresenter = new CatalogLocalPresenterImpl(localModel);
        LocalView localView = new LocalViewImpl(localPresenter);
        localPresenter.setView(localView);

        CatalogSearchPresenter searchPresenter = new CatalogSearchPresenterImpl(searchModel);
        SearchView searchView = new SearchViewImpl(searchPresenter);
        searchPresenter.setView(searchView);

        MainFrame frame = new MainFrameImpl();
        frame.setLocalPanel(localView.getPanel());
        frame.setSearchPanel(searchView.getPanel());
        frame.showView();

        localView.start();
        searchPresenter.start();
        localPresenter.start();
    }
}
