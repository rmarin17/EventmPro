package unicauca.movil.eventmpro.models;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class Ubicacion {
    long idu;
    String tituloubicacion,direccion;
    Double lat, lng;

    public long getIdu() {
        return idu;
    }

    public void setIdu(long idu) {
        this.idu = idu;
    }

    public String getTituloubicacion() {
        return tituloubicacion;
    }

    public void setTituloubicacion(String tituloubicacion) {
        this.tituloubicacion = tituloubicacion;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
