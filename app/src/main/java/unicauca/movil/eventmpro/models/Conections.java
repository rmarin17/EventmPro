package unicauca.movil.eventmpro.models;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class Conections {
    long id;
    String dias, ponentes, ubicacion, beacons;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getPonentes() {
        return ponentes;
    }

    public void setPonentes(String ponentes) {
        this.ponentes = ponentes;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getBeacons() {
        return beacons;
    }

    public void setBeacons(String beacons) {
        this.beacons = beacons;
    }
}
