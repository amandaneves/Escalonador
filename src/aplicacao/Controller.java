package aplicacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Controller extends Stage {

    @FXML
    private AnchorPane acpPrincipal;

    @FXML
    private TableView<ProcessoPrincipal> tbvProcessos;

    @FXML
    private TableColumn<ProcessoPrincipal, String> clnProcesso_principal;

    @FXML
    private TableColumn<ProcessoPrincipal, String> clnChegada;

    @FXML
    private TableColumn<ProcessoPrincipal, String> clnCpu_principal;

    @FXML
    private TableColumn<ProcessoPrincipal, String> clnPrioridade;

    @FXML
    private AnchorPane acpDados_processo;

    @FXML
    private TextField txtProcesso;

    @FXML
    private TextField txtChegada;

    @FXML
    private TextField txtCpu;

    @FXML
    private TextField txtPrioridade;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnLimpar;

    @FXML
    private TextField txtQuantum;

    @FXML
    private Button btnCalcular;

    @FXML
    private TableView<Processo> tbvFifo;

    @FXML
    private TableColumn<Processo, String> clnProcesso_fifo;

    @FXML
    private TableColumn<Processo, String> clnEspera_fifo;

    @FXML
    private TableColumn<Processo, String> clnTurnaround_fifo;

    @FXML
    private TextField txtTme_fifo;

    @FXML
    private TextField txtTmt_fifo;

    @FXML
    private TableView<Processo> tbvSjf;

    @FXML
    private TableColumn<Processo, String> clnProcesso_sjf;

    @FXML
    private TableColumn<Processo, String> clnEspera_sjf;

    @FXML
    private TableColumn<Processo, String> clnTurnaround_sjf;

    @FXML
    private TextField txtTme_sjf;

    @FXML
    private TextField txtTmt_sjf;

    @FXML
    private TableView<Processo> tbvPrioridade;

    @FXML
    private TableColumn<Processo, String> clnProcesso_prioridade;

    @FXML
    private TableColumn<Processo, String> clnEspera_prioridade;

    @FXML
    private TableColumn<Processo, String> clnTurnaround_prioridade;

    @FXML
    private TextField txtTme_prioridade;

    @FXML
    private TextField txtTmt_prioridade;

    @FXML
    private TableView<Processo> tbvRr;

    @FXML
    private TableColumn<Processo, String> clnProcesso_rr;

    @FXML
    private TableColumn<Processo, String> clnEspera_rr;

    @FXML
    private TableColumn<Processo, String> clnTurnaround_rr;

    @FXML
    private TextField txtTme_rr;

    @FXML
    private TextField txtTmt_rr;

    private ObservableList<ProcessoPrincipal> processoPrincipal;
    private ObservableList<Processo> fifo;
    private ObservableList<Processo> sjf;
    private ObservableList<Processo> prioridade;
    private ObservableList<Processo> rr;
    private int numeroProcesso = 1;
    private int i;

    public Controller() throws IOException {
        FXMLLoader myfrm = new FXMLLoader(getClass().getResource("Principal.fxml"));
        myfrm.setController(this);
        Scene myscene = new Scene(myfrm.load());
        this.setScene(myscene);
        this.setTitle("Escalonador de Processos");
        this.setResizable(false);

        this.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    txtChegada.requestFocus();
                    txtProcesso.setText("P" + numeroProcesso);
                    txtQuantum.setText("2");

                    clnProcesso_principal.setCellValueFactory(new PropertyValueFactory<ProcessoPrincipal, String>("processo"));
                    clnChegada.setCellValueFactory(new PropertyValueFactory<ProcessoPrincipal, String>("chegada"));
                    clnCpu_principal.setCellValueFactory(new PropertyValueFactory<ProcessoPrincipal, String>("cpu"));
                    clnPrioridade.setCellValueFactory(new PropertyValueFactory<ProcessoPrincipal, String>("prioridade"));

                    clnProcesso_fifo.setCellValueFactory(new PropertyValueFactory<Processo, String>("processo"));
                    clnEspera_fifo.setCellValueFactory(new PropertyValueFactory<Processo, String>("espera"));
                    clnTurnaround_fifo.setCellValueFactory(new PropertyValueFactory<Processo, String>("turnaround"));

                    clnProcesso_sjf.setCellValueFactory(new PropertyValueFactory<Processo, String>("processo"));
                    clnEspera_sjf.setCellValueFactory(new PropertyValueFactory<Processo, String>("espera"));
                    clnTurnaround_sjf.setCellValueFactory(new PropertyValueFactory<Processo, String>("turnaround"));

                    clnProcesso_prioridade.setCellValueFactory(new PropertyValueFactory<Processo, String>("processo"));
                    clnEspera_prioridade.setCellValueFactory(new PropertyValueFactory<Processo, String>("espera"));
                    clnTurnaround_prioridade.setCellValueFactory(new PropertyValueFactory<Processo, String>("turnaround"));

                    clnProcesso_rr.setCellValueFactory(new PropertyValueFactory<Processo, String>("processo"));
                    clnEspera_rr.setCellValueFactory(new PropertyValueFactory<Processo, String>("espera"));
                    clnTurnaround_rr.setCellValueFactory(new PropertyValueFactory<Processo, String>("turnaround"));

                    processoPrincipal = FXCollections.observableArrayList();
                    fifo = FXCollections.observableArrayList();
                    sjf = FXCollections.observableArrayList();
                    prioridade = FXCollections.observableArrayList();
                    rr = FXCollections.observableArrayList();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @FXML
    void btnAdicionar_Click(ActionEvent event) {
        processoPrincipal.add(new ProcessoPrincipal(txtProcesso.getText(), txtChegada.getText(), txtCpu.getText(), txtPrioridade.getText()));
        tbvProcessos.setItems(processoPrincipal);
        txtProcesso.clear();
        txtChegada.clear();
        txtCpu.clear();
        txtPrioridade.clear();
        atualizaNumero();
    }

    @FXML
    void btnCalcular_Click(ActionEvent event) {
        Escalonador escalonador = new Escalonador(processoPrincipal, Integer.parseInt(txtQuantum.getText()));
        escalonador.calculaFIFO();
        escalonador.calculaSJF();
        escalonador.calculaPrioridade();
        escalonador.calculaRR();

        for(i = 0; i < processoPrincipal.size(); i++){
            fifo.add(new Processo("P"+String.valueOf(i+1), escalonador.tEspFIFO[i] + "", escalonador.tTurFIFO[i] + ""));
            sjf.add(new Processo("P"+String.valueOf(i+1), escalonador.tEspSJF[i] + "", escalonador.tTurSJF[i] + ""));
            prioridade.add(new Processo("P"+String.valueOf(i+1), escalonador.tEspPrio[i] + "", escalonador.tTurPrio[i] + ""));
            rr.add(new Processo("P"+String.valueOf(i+1), escalonador.tEspRR[i] + "", escalonador.tTurRR[i] + ""));
        }
        tbvFifo.setItems(fifo);
        tbvSjf.setItems(sjf);
        tbvPrioridade.setItems(prioridade);
        tbvRr.setItems(rr);

        txtTme_fifo.setText(escalonador.getEsperaFIFO() + "");
        txtTmt_fifo.setText(escalonador.getTurnaroundFIFO() + "");
        txtTme_sjf.setText(escalonador.getEsperaSJF() + "");
        txtTmt_sjf.setText(escalonador.getTurnaroundSJF() + "");
        txtTme_prioridade.setText(escalonador.getEsperaPrioridade() + "");
        txtTmt_prioridade.setText(escalonador.getTurnaroundPrioridade() + "");
        txtTme_rr.setText(escalonador.getEsperaRR() + "");
        txtTmt_rr.setText(escalonador.getTurnaroundRR() + "");
    }

    @FXML
    void btnRemover_Click(ActionEvent event) {
        if(tbvProcessos.getSelectionModel().getSelectedItem() != null) {
            processoPrincipal.remove(tbvProcessos.getSelectionModel().getFocusedIndex());
            numeroProcesso -= 1;
            txtProcesso.setText("P"+numeroProcesso);
        }
    }

    @FXML
    void btnLimpar_Click(ActionEvent event) {
        fifo.clear();
        sjf.clear();
        prioridade.clear();
        rr.clear();
        processoPrincipal.clear();
        numeroProcesso = 1;
        txtProcesso.setText("P"+numeroProcesso);
        txtChegada.clear();
        txtCpu.clear();
        txtPrioridade.clear();
        txtQuantum.setText("2");
        txtTme_fifo.clear();
        txtTmt_fifo.clear();
        txtTme_sjf.clear();
        txtTmt_sjf.clear();
        txtTme_prioridade.clear();
        txtTmt_prioridade.clear();
        txtTme_rr.clear();
        txtTmt_rr.clear();
        txtChegada.requestFocus();
    }

    private void atualizaNumero(){
        numeroProcesso += 1;
        txtProcesso.setText("P" + numeroProcesso);
    }
}
