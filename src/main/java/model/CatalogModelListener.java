package model;

public interface CatalogModelListener {

    void didSearchOnWiki();//Esto lo uso para el preliminar result

    void didSaveLocally();//tocar el boton de guardar localmente

    void didDeleteSave();

    void didSearchExtract();

    void didThrowException();//Cuando algo lanza una excepcion
}
