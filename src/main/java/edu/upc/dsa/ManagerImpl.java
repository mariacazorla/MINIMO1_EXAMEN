package edu.upc.dsa;

import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vol;
import org.apache.log4j.Logger;

import java.util.*;

public class ManagerImpl implements Manager {

    private static Manager instance;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    private List<Avio> avions;
    private List<Vol> vols;
    private List<Maleta> maletes;

    private ManagerImpl() {
        this.avions = new ArrayList<>();
        this.vols = new ArrayList<>();
        this.maletes = new ArrayList<>();
    }

    public static Manager getInstance() {
        if (instance == null) instance = new ManagerImpl();
        return instance;
    }

    // Afegim un  avió i comprovem id
    @Override
    public Avio afegirAvio(String idAvio, String model, String companyia) {

        // Comprovar si ja existeix un avió amb aquest ID
        for (Avio a : this.avions) {
            if (a.getId().equals(idAvio)) {
                logger.error("Error: Ja existeix un avió amb ID: " + idAvio);
                return null; // No es pot afegir un duplicat
            }
        }

        // Crear i afegir el nou avió
        Avio nou = new Avio(idAvio, model, companyia);
        this.avions.add(nou);
        logger.info("✅ Avió afegit correctament amb ID: " + idAvio);
        return nou;
    }


    // Afegir un vol i comprovem id
    @Override
    public Vol afegirVol(String idVol, String horaSortida, String horaArribada, String origen, String desti, String idAvio) {


        // Comprovem si l'ID de vol ja existeix
        for (Vol v : this.vols) {
            if (v.getId().equals(idVol)) {
                logger.error("Error: Ja existeix un vol amb ID: " + idVol);
                return null; // No es pot afegir un vol amb el mateix ID
            }
        }

        // Buscar l'avió corresponent
        Avio avio = this.avions.stream().filter(a -> a.getId().equals(idAvio)).findFirst().orElse(null);
        if (avio == null) {
            logger.error("L'avió amb id: " + idAvio + "no existeix");
            return null;
        }

        // Crear i afegir el nou vol
        Vol nouVol = new Vol(idVol, horaSortida, horaArribada, origen, desti, avio);
        this.vols.add(nouVol);
        logger.info("Vol amb ID: " + idVol + "afegit correctament");
        return nouVol;
    }



    // Facturar una maleta
    @Override
    public Maleta facturarMaleta(String idUsuari, String idVol) {
        logger.info("Facturant maleta per usuari: " + idUsuari + " al vol: " + idVol);
        Vol vol = this.vols.stream().filter(v -> v.getId().equals(idVol)).findFirst().orElse(null);
        if (vol == null) {
            logger.error("Vol no trobat");
            return null;
        }

        String idMaleta = UUID.randomUUID().toString();
        Maleta nova = new Maleta(idMaleta, idUsuari);
        vol.afegirMaleta(nova);
        this.maletes.add(nova);
        logger.info("Maleta facturada correctament");
        return nova;
    }

    // Obtenir les maletes d’un vol en ordre de descarrega
    @Override
    public List<Maleta> getMaletesDescarga(String idVol) {
        logger.info("Obtenint maletes del vol: " + idVol);
        Vol vol = this.vols.stream().filter(v -> v.getId().equals(idVol)).findFirst().orElse(null);
        if (vol == null) {
            logger.error("Vol no trobat");
            return Collections.emptyList();
        }

        Stack<Maleta> pila = vol.getMaletes();
        List<Maleta> descarrega = new ArrayList<>();

        Stack<Maleta> copia = (Stack<Maleta>) pila.clone();
        while (!copia.isEmpty()) {
            descarrega.add(copia.pop());
        }

        logger.info("Llista de maletes retornada en ordre de descarrega:");
        return descarrega;
    }

    @Override
    public int size() {
        int total = avions.size() + vols.size() + maletes.size();
        logger.info("Total d'elements: " + total);
        return total;
    }


}
