public class Cita {
    String fecha = "";
    String motivo = "";
    String doctorCita="";
    String pacienteCita="";

    public String getPacienteCita() {
        return pacienteCita;
    }

    public void setPacienteCita(String pacienteCita) {
        this.pacienteCita = pacienteCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDoctorCita() {
        return doctorCita;
    }

    public void setDoctorCita(String doctorCita) {
        this.doctorCita = doctorCita;
    }
}
