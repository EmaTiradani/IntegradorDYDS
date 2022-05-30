package model;

public interface CatalogModelListener {

    void didSearchOnWiki();//Esto lo uso para el preliminar result

    void didSelectSearchOption();//cuando buscas, te aparecen opciones y tocas una

    void didSaveLocally();//tocar el boton de guardar localmente

    void didSelectSavedSearch();//cuando elegis algo del combobox

    void didDeleteSave();

}
