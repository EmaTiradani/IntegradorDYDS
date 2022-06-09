package model;

public interface CatalogLocalModelListener {
    void didSaveLocally();//tocar el boton de guardar localmente

    void didDeleteSave();

    void didThrowException();//Cuando algo lanza una excepcion

}
