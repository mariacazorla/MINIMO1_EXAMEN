package edu.upc.dsa;

import edu.upc.dsa.models.*;

import java.util.List;

public interface Manager {

    //Afegir avio
    Avio afegirAvio(String idAvio, String model, String companyia);

    //Afegir vol
    Vol afegirVol(String idVOL, String origen, String desti, String horaSortida, String horaArribada, String idAvio);

    // Facturar una maleta
    Maleta facturarMaleta(String idUsuario, String idVuelo);

    // Maletes facturades en un vol
    List<Maleta> getMaletesDescarga(String idVuelo);

  int size();
}

