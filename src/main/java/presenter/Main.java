package presenter;

import dyds.gourmetCatalog.fulllogic.DataBase;
import model.CatalogModel;
import model.CatalogModelImpl;

public class Main {

    public static void main(String[] args) {

        CatalogPresenter presenter = new CatalogPresenterImpl();
        DataBase.loadDatabase();//TODO esto deberia estar en el contstructor del model?
        presenter.start();
    }
}
