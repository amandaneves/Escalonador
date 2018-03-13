package aplicacao;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by AMANDA on 20/06/2016.
 */
public class ProcessoPrincipal {
    private SimpleStringProperty processo;
    private SimpleStringProperty chegada;
    private SimpleStringProperty cpu;
    private SimpleStringProperty prioridade;

    public ProcessoPrincipal(String processo, String chegada, String cpu, String prioridade ){
        this.processo = new SimpleStringProperty(processo);
        this.chegada = new SimpleStringProperty(chegada);
        this.cpu = new SimpleStringProperty(cpu);
        this.prioridade = new SimpleStringProperty(prioridade);
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

    public String getChegada() {
        return chegada.get();
    }

    public SimpleStringProperty chegadaProperty() {
        return chegada;
    }

    public void setChegada(String chegada) {
        this.chegada.set(chegada);
    }

    public String getCpu() {
        return cpu.get();
    }

    public SimpleStringProperty cpuProperty() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
    }

    public String getPrioridade() {
        return prioridade.get();
    }

    public SimpleStringProperty prioridadeProperty() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade.set(prioridade);
    }
}
