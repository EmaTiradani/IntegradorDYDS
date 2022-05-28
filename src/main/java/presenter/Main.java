package presenter;

import model.CatalogModel;
import model.CatalogModelImpl;

public class Main {

    public static void main(String[] args) {

        CatalogPresenter presenter = new CatalogPresenterImpl();
        presenter.start();
    }
}
