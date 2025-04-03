package edu.upc.dsa;

import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ManagerImplTest {

    private Manager manager;

    @Before
    public void setUp() {
        this.manager = ManagerImpl.getInstance();


        manager.afegirAvio("A1", "Boeing 747", "Vueling");
        manager.afegirAvio("A2", "Airbus A320", "Ryanair");

        manager.afegirVol("V1", "08:00", "10:00", "Barcelona", "Madrid", "A1");
        manager.afegirVol("V2", "14:00", "16:00", "Madrid", "Valencia", "A2");
    }


    @Test
    public void testAfegirAvio() {
        Avio avio = manager.afegirAvio("A3", "Embraer", "Iberia");
        assertNotNull(avio);
        assertEquals("A3", avio.getId());
        assertEquals("Embraer", avio.getModel());
        assertEquals("Iberia", avio.getCompanyia());
    }

    @Test
    public void testAfegirVol() {
        Vol vol = manager.afegirVol("V3", "09:00", "11:00", "Girona", "Sevilla", "A1");
        assertNotNull(vol);
        assertEquals("V3", vol.getId());
        assertEquals("Girona", vol.getOrigen());
        assertEquals("Sevilla", vol.getDesti());
    }

    @Test
    public void testFacturarMaleta() {
        Maleta m = manager.facturarMaleta("usuari123", "V1");
        assertNotNull(m);
        assertEquals("usuari123", m.getUsuari());
    }

    @Test
    public void testGetMaletesDescarga() {
        manager.facturarMaleta("usuari1", "V1");
        manager.facturarMaleta("usuari2", "V1");
        manager.facturarMaleta("usuari3", "V1");

        List<Maleta> maletes = manager.getMaletesDescarga("V1");
        assertEquals(3, maletes.size());
        assertEquals("usuari3", maletes.get(0).getUsuari()); // últim facturat surt primer
        assertEquals("usuari1", maletes.get(2).getUsuari());
    }
}
