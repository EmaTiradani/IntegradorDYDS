package presenter;

import model.CatalogLocalModelImpl;
import model.CatalogWikiSearchModelImpl;
import model.DataBase;
import view.MainWindow;
import view.MainWindowImpl;

public class Main {

    public static void main(String[] args) {

        CatalogPresenter presenter = new CatalogPresenterImpl(new CatalogLocalModelImpl(), new CatalogWikiSearchModelImpl());
        MainWindow view = new MainWindowImpl(presenter);
        presenter.setView(view);

        DataBase.loadDatabase();
        presenter.start();
    }
}
