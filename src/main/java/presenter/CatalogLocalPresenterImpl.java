package presenter;

import model.CatalogLocalModel;
import model.listeners.CatalogLocalModelListener;
import view.LocalView;
import view.SearchView;

public class CatalogLocalPresenterImpl implements CatalogLocalPresenter{

    private LocalView view;
    private CatalogLocalModel localModel;

    public CatalogLocalPresenterImpl(CatalogLocalModel localModel){
        this.localModel = localModel;
        initListeners();
    }

    @Override
    public void start() {//TODO cambiar al constructor o cambiar el nombre al metodo como loadTitlesList()
        this.view.setStoredList(localModel.getStoredTitles());
    }

    public void setView(LocalView view){
        this.view = view;
    }

    @Override
    public void onEventShowSaved() {//TODO es necesario cambiar? El modelo no hace nada, solamente retorna algo
        String body = localModel.getSave(view.getSavesSelection());
        view.setStoredContent(body);
    }

    @Override
    public void onEventDeleteArticle() {
        localModel.deleteArticle(view.getSavesSelection());
    }

    @Override
    public void onEventSaveChanges() {
        localModel.saveArticleChanges(view.getSavesSelection(),view.getDisplayedArticle());
    }

    private void initListeners(){
        localModel.addListener(new CatalogLocalModelListener() {

            @Override
            public void didSaveLocally() {
                String[] titles = localModel.getStoredTitles();
                view.setStoredList(titles);
                view.displayMessage("Saved succesfully!");
            }

            @Override
            public void didThrowException() {
                view.displayMessage(localModel.getErrorMessage());
            }

            @Override
            public void foundSave() {

            }

            @Override
            public void didDeleteSave() {
                view.setStoredContent("");
                view.setStoredList(localModel.getStoredTitles());
                view.displayMessage("Deleted succesfully!");
            }
        });
    }
}
