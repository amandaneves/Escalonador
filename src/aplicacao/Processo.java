package aplicacao;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Amanda on 20/06/2016.
 */
public class Processo {
    private SimpleStringProperty processo;
    private SimpleStringProperty espera;
    private SimpleStringProperty turnaround;

    public Processo(String processo, String espera, String turnaround){
        this.processo = new SimpleStringProperty(processo);
        this.espera = new SimpleStringProperty(espera);
        this.turnaround = new SimpleStringProperty(turnaround);
    }

    public String getProcesso() {
        return processo.get();
    }

    public SimpleStringProperty processoProperty() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo.set(processo);
    }

    public String getEspera() {
        return espera.get();
    }

    public SimpleStringProperty esperaProperty() {
        return espera;
    }

    public void setEspera(String espera) {
        this.espera.set(espera);
    }

    public String getTurnaround() {
        return turnaround.get();
    }

    public SimpleStringProperty turnaroundProperty() {
        return turnaround;
    }

    public void setTurnaround(String turnaround) {
        this.turnaround.set(turnaround);
    }
}
