package model.listeners;

public interface CatalogWikiSearchModelListener {

    void didSearchOnWiki();//Esto lo uso para el preliminar result

    void didSearchExtract();

    void didThrowException();//Cuando algo lanza una excepcion

}
