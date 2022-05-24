package presenter;

import model.CatalogModel;
import view.MainWindow;

public class CatalogPresenterImpl implements CatalogPresenter{

    private CatalogModel model;
    private MainWindow view;


    @Override
    public void start() {
        view.showView();
    }

    @Override
    public void onEventSearch() {

    }

    public void onEvent(){}

    public void initListeners(){
        //todo aca tengo que meterle los listeners del modelo
        @Override public void didUpdateNote() {

        }
    }
}
