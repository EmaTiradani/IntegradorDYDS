package presenter;

import dyds.gourmetCatalog.fulllogic.DataBase;
import model.CatalogLocalModelImpl;
import model.CatalogWikiSearchModelImpl;
import view.MainWindow;
import view.MainWindowImpl;

public class Main {

    public static void main(String[] args) {

        /*this.view = new MainWindowImpl(this);
        this.searchModel = new CatalogWikiSearchModelImpl();
        this.localModel = new CatalogLocalModelImpl();
        */


        CatalogPresenter presenter = new CatalogPresenterImpl(new CatalogLocalModelImpl(), new CatalogWikiSearchModelImpl());
        MainWindow view = new MainWindowImpl(presenter);
        presenter.setView(view);

        DataBase.loadDatabase();//TODO esto deberia estar en el contstructor del model?
        presenter.start();
    }
}
