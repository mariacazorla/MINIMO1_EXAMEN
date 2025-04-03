package edu.upc.dsa.models;
import java.util.Stack;

public class Vol {
    private String id;
    private String horaSortida;
    private String horaArribada;
    private String origen;
    private String desti;
    private Avio avio;
    private Stack<Maleta> maletes;

    public Vol(String id, String horaSortida, String horaArribada, String origen, String desti, Avio avio) {
        this.id = id;
        this.horaSortida = horaSortida;
        this.horaArribada = horaArribada;
        this.origen = origen;
        this.desti = desti;
        this.avio = avio;
        this.maletes = new Stack<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHoraSortida() { return horaSortida; }
    public void setHoraSortida(String horaSortida) { this.horaSortida = horaSortida; }

    public String getHoraArribada() { return horaArribada; }
    public void setHoraArribada(String horaArribada) { this.horaArribada = horaArribada; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDesti() { return desti; }
    public void setDesti(String desti) { this.desti = desti; }

    public Avio getAvio() { return avio; }
    public void setAvio(Avio avio) { this.avio = avio; }

    public Stack<Maleta> getMaletes() { return maletes; }
    public void setMaletes(Stack<Maleta> maletes) { this.maletes = maletes; }

    public void afegirMaleta(Maleta m) { this.maletes.push(m); }
    public Maleta treureMaleta() { return this.maletes.isEmpty() ? null : this.maletes.pop(); }

    @Override
    public String toString() {
        return "Vol {id='" + id + "', origen='" + origen + "', desti='" + desti + "', sortida='" + horaSortida +
                "', arribada='" + horaArribada + "', avio=" + avio + ", maletes=" + maletes.size() + "}";
    }
}


