package unicauca.movil.eventmpro.models;

/**
 * Created by RicardoM on 28/11/2017.
 */

public class Evento {
    long ide;
    String eventonombre, eventoimg, objetivo, lugarevento, descripcion, fecha;
    int numerodias;

    public long getIde() {
        return ide;
    }

    public void setIde(long ide) {
        this.ide = ide;
    }

    public String getEventonombre() {
        return eventonombre;
    }

    public void setEventonombre(String eventonombre) {
        this.eventonombre = eventonombre;
    }

    public String getEventoimg() {
        return eventoimg;
    }

    public void setEventoimg(String eventoimg) {
        this.eventoimg = eventoimg;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getLugarevento() {
        return lugarevento;
    }

    public void setLugarevento(String lugarevento) {
        this.lugarevento = lugarevento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumerodias() {
        return numerodias;
    }

    public void setNumerodias(int numerodias) {
        this.numerodias = numerodias;
    }
}
