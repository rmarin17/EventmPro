package unicauca.movil.eventmpro.models;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class Beacons {
    long id;
    String btitulo;
    int major, minor;
    Double blat,blng;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBtitulo() {
        return btitulo;
    }

    public void setBtitulo(String btitulo) {
        this.btitulo = btitulo;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public Double getBlat() {
        return blat;
    }

    public void setBlat(Double blat) {
        this.blat = blat;
    }

    public Double getBlong() {
        return blng;
    }

    public void setBlong(Double blng) {
        this.blng = blng;
    }
}
