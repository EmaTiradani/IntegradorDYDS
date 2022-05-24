package presenter;

import model.CatalogModel;
import model.CatalogModelImpl;

public class Main {

    public static void main(String[] args) {

        CatalogModel model = new CatalogModelImpl();
        //model.setDateManager(new CurrentDateManager());
        //model.setNotesRepository(new NonPersistentNotesRepository());todo aca podria asignarle la databse tal vez

        //NotesListerPresenterImpl lister = new NotesListerPresenterImpl(model);
        CatalogPresenter presenter = new CatalogPresenterImpl();
        presenter.start();//todo esto no esta ni implementado jeje
        //lister.start();
    }
}
