package poliues.eisi.fia.edu.sv.poliues;

/**
 * Created by Rodrigo Daniel on 29/04/2016.
 */
public class Solicitud {
    private int idSolicitud;
    private int actividad;
    private int tarifa;
    private int administrador;
    private String motivoSolicitud;
    private String fechaCreacion;

    public Solicitud(){

    }

    public Solicitud(int idSolicitud, int actividad, int tarifa, int administrador, String motivoSolicitud, String fechaCreacion) {
        this.idSolicitud = idSolicitud;
        this.actividad = actividad;
        this.tarifa = tarifa;
        this.administrador = administrador;
        this.motivoSolicitud = motivoSolicitud;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public int getAdministrador() {
        return administrador;
    }

    public void setAdministrador(int administrador) {
        this.administrador = administrador;
    }

    public String getMotivoSolicitud() {
        return motivoSolicitud;
    }

    public void setMotivoSolicitud(String motivoSolicitud) {
        this.motivoSolicitud = motivoSolicitud;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }




}