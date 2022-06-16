package model.listeners;

public interface CatalogLocalModelListener {
    void didSaveLocally();

    void didDeleteSave();

    void didThrowException();

}
