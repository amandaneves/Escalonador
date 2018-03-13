package aplicacao;

import javafx.collections.ObservableList;

/**
 * Created by Amanda on 26/06/2016.
 */
public class Escalonador{

    // matrizes
    double[][] mD;
    double[][] mFIFO;
    double[][] mSJF;
    double[][] mPrioridade;
    double[][] mRR;
    //tempo de execução e turnaround de cada algoritmo
    static double[] tEspPrio;
    static double[] tTurPrio;
    static double[] tEspFIFO;
    static double[] tTurFIFO;
    static double[] tEspSJF;
    static double[] tTurSJF;
    static double[] tEspRR;
    static double[] tTurRR;
    // tempo médio
    double tmeFIFO = 0;
    double tmeSJF = 0;
    double tmePrio = 0;
    double tmeRR = 0;
    private ObservableList<ProcessoPrincipal> processoPrincipal;
    private int tempoTotal = 0;
    private int linhas;
    private int quantum;
    private int i;

    public Escalonador(ObservableList<ProcessoPrincipal> processoPrincipal, int quantum) {
        this.processoPrincipal = processoPrincipal;
        this.quantum = quantum;

        linhas = processoPrincipal.size();
        i = 0;
        for(i = 0; i < processoPrincipal.size(); i++){
            tempoTotal += Double.parseDouble(processoPrincipal.get(i).getCpu());
        }
        System.out.println(tempoTotal);

        //inicializa matriz uma para cada algoritmo
        mFIFO = new double[linhas][3];
        mD = new double[linhas][3];
        mSJF = new double[linhas][3];
        mPrioridade = new double[linhas][3];
        mRR = new double[linhas][3];

        //cria matrizes para cada escalonamento e uma geral que não vai ser alterada
        i = 0;
        for (i = 0; i < linhas; i++) {
            //tempo de chegada
            mD[i][0] = Double.parseDouble(processoPrincipal.get(i).getChegada());
            mFIFO[i][0] = Double.parseDouble(processoPrincipal.get(i).getChegada());
            mSJF[i][0] = Double.parseDouble(processoPrincipal.get(i).getChegada());
            mPrioridade[i][0] = Double.parseDouble(processoPrincipal.get(i).getChegada());
            mRR[i][0] = Double.parseDouble(processoPrincipal.get(i).getChegada());

            //tempo de execução
            mD[i][1] = Double.parseDouble(processoPrincipal.get(i).getCpu());
            mFIFO[i][1] = Double.parseDouble(processoPrincipal.get(i).getCpu());
            mSJF[i][1] = Double.parseDouble(processoPrincipal.get(i).getCpu());
            mPrioridade[i][1] = Double.parseDouble(processoPrincipal.get(i).getCpu());
            mRR[i][1] = Double.parseDouble(processoPrincipal.get(i).getCpu());

            //prioridade
            mD[i][2] = Double.parseDouble(processoPrincipal.get(i).getPrioridade());
            mFIFO[i][2] = Double.parseDouble(processoPrincipal.get(i).getPrioridade());
            mSJF[i][2] = Double.parseDouble(processoPrincipal.get(i).getPrioridade());
            mPrioridade[i][2] = Double.parseDouble(processoPrincipal.get(i).getPrioridade());
            mRR[i][2] = Double.parseDouble(processoPrincipal.get(i).getPrioridade());
        }
    }

    public void calculaFIFO() {
        int contFIFO = 0;
        i = 0;
        //representacao do gráfico de Gantt
        int[] escFIFO = new int[tempoTotal];

        //faz FIFO até que o contador alcance o tempo total de execucao
        while (contFIFO < tempoTotal) {
            //passa processo enquanto o tempo de execução for maior que zero
            while (mFIFO[i][1] > 0) {
                //decresse o tempo de execução
                mFIFO[i][1] = mFIFO[i][1] - 1;
                escFIFO[contFIFO] = i;
                contFIFO++;
            }
            i++;//incrementa o número do processo
        }

        //calcula tempo de espera e turnaround
        contFIFO = 0;
        i = 0;
        tEspFIFO = new double[linhas];
        tTurFIFO = new double[linhas];
        int contProcesso = 0;

        //procura a primeira "aparição" do processo
        while (contProcesso < linhas) {
            if (escFIFO[i] == contProcesso) {
                //pega dados da matriz geral já que as outras foram alteradas
                //t. espera = tempo - t.chegada
                tEspFIFO[contProcesso] = i - mD[contProcesso][0];

                //t. turnaround = t. espera + t. execução
                tTurFIFO[contProcesso] = tEspFIFO[contProcesso] + mD[contProcesso][1];
                contProcesso++;
            }
            if (i + 1 >= tempoTotal) {
                i = 0;
            } else {
                i++;
            }
        }
    }

    //calcula SJF
    public void calculaSJF() {
        int i = 0;
        int pmt = 0;
        int contSJF = 0;
        //representacao do gráfico de Gantt
        int[] escSJF = new int[(int) tempoTotal];

        //faz SJF até que o contador alcance o tempo total
        while (contSJF < tempoTotal) {
            //busca menor tempo de execução
            int minTime = 9999;

            for (i = 0; i < linhas; i++) {
                //se o processo for o menor tempo de execução e já ter chegado
                if (mSJF[i][1] < minTime && mSJF[i][1] > 0 && mSJF[i][0] <= contSJF) {
                    minTime = (int) mSJF[i][1];
                    pmt = i;
                }
            }

            //faz SJF
            //decressse tempo de execução
            mSJF[pmt][1] = mSJF[pmt][1] - 1;
            escSJF[contSJF] = pmt;
            contSJF++;
        }

        //calcula tempo de espera e turnround
        contSJF = 0;
        i = 0;
        tEspSJF = new double[(int) linhas];
        tTurSJF = new double[(int) linhas];
        int contProcesso = 0;

        //procura a primeira "aparição" do processo
        while (contProcesso < linhas) {

            if (escSJF[i] == contProcesso) {
                //pega dados da matriz geral já que as outras foram alteradas
                //t. espera = tempo - t.chegada
                tEspSJF[contProcesso] = i - mD[contProcesso][0];

                //t. turnaround = t. espera + t. execução
                tTurSJF[contProcesso] = tEspSJF[contProcesso] + mD[contProcesso][1];
                contProcesso++;
            }
            //no caso de não ter encontrado um processo e não ter "terminado o tempo"
            //ele zera o i
            if (i + 1 >= tempoTotal) {
                i = 0;
            } else {
                i++;
            }
        }
    }

    //calcula Prioridade
    public void calculaPrioridade() {
        int i = 0;

        int pmp = 0;
        int contPrio = 0;
        //representa o gráfico de Gantt
        int[] escPrio = new int[(int) tempoTotal];

        //faz Priotidade até que o contador alcance o tempo total
        while (contPrio < tempoTotal) {
            //busca menor prioridade
            int minPrio = 9999;
            for (i = 0; i < linhas; i++) {
                if (mPrioridade[i][2] < minPrio && mPrioridade[i][1] > 0 && mPrioridade[i][0] <= contPrio) {
                    minPrio = (int) mPrioridade[i][2];
                    pmp = i;
                }
            }

            //faz prioridade
            //decressse tempo de execução
            mPrioridade[pmp][1] = mPrioridade[pmp][1] - 1;
            escPrio[contPrio] = pmp;
            contPrio++;
        }

        //calcula tempo de espera e turnround
        contPrio = 0;
        i = 0;
        tEspPrio = new double[(int) linhas];
        tTurPrio = new double[(int) linhas];
        int contProcesso = 0;
        //procura a primeira "aparição" do processo
        while (contProcesso < linhas) {
            if (escPrio[i] == contProcesso) {
                //pega dados da matriz geral já que as outras foram alteradas
                //t. espera = tempo - t.chegada
                tEspPrio[contProcesso] = i - mD[contProcesso][0];
                tTurPrio[contProcesso] = tEspPrio[contProcesso] + mD[contProcesso][1];
                contProcesso++;
            }
            //no caso de não ter encontado um processo e não ter "terminado o tempo"
            //ele zera o i
            if (i + 1 >= tempoTotal) {
                i = 0;
            } else {
                i++;
            }
        }

    }

    //calcula RR
    public void calculaRR() {
        int i = 0;
        //contador do quantum
        int cQ = 0;
        int contRR = 0;

        //representa o gráfico de Gantt
        int[] escRR = new int[(int) tempoTotal];

        //faz o "ciclo" do RR enquanto o tempo não acabar
        while (contRR < tempoTotal) {
            //faz ciclo ,se o processo tiver acabado ele passo para o próximo
            if (mRR[i][1] > 0 && mRR[i][0] <= contRR) {
                //decrementa tempo de execução
                mRR[i][1] = mRR[i][1] - 1;
                escRR[contRR] = i;
                contRR++;
                cQ++;
            } else {
                //zera quantum
                cQ = 1;
                //se completou o cilo ele o reinicia se não passa o para o próximo
                if (i + 1 >= linhas) {
                    i = 0;
                } else {
                    i++;
                }
            }
            //verifica se o quantum terminou
            if (cQ > quantum) {
                cQ = 1;
                //se completou o cilo ele o reinicia se não passa o para o próximo
                if (i + 1 >= linhas) {
                    i = 0;
                } else {
                    i++;
                }
            }
            //se completou o cilo ele o reinicia
            if (i + 1 > linhas) {
                i = 0;
            }

        }


        //calcula tempo de espera e turnround
        contRR = 0;
        i = 0;
        tEspRR = new double[(int) linhas];
        tTurRR = new double[(int) linhas];
        int contProcesso = 0;
        //procura a primeira "aparição" do processo
        while (contProcesso < linhas) {
            if (escRR[i] == contProcesso) {
                //pega dados da matriz geral já que as outras foram alteradas
                //t. espera = tempo - t.chegada
                tEspRR[contProcesso] = i - mD[contProcesso][0];
                tTurRR[contProcesso] = tEspRR[contProcesso] + mD[contProcesso][1];
                contProcesso++;
            }
            //no caso de não ter encontado um processo e não ter "terminado o tempo"
            //ele zera o i
            if (i + 1 >= tempoTotal) {
                i = 0;
            } else {
                i++;
            }
        }

    }

    //calcula o melhor algoritmo e retorna
    public int getMelhor() {
        //retorna 1=fifo 2=sjf 3=prio 4=rr
        int c = 0;
        if (tmeFIFO < tmeSJF && tmeFIFO < tmePrio && tmeFIFO < tmeRR) {
            c = 1;
        }
        if (tmeSJF < tmeFIFO && tmeSJF < tmePrio && tmeSJF < tmeRR) {
            c = 2;
        }
        if (tmePrio < tmeFIFO && tmePrio < tmeSJF && tmePrio < tmeRR) {
            c = 3;
        }
        if (tmeRR < tmeFIFO && tmeRR < tmeSJF && tmeRR < tmePrio) {
            c = 4;
        }
        return c;

    }

    //calcula tempo médio de espera de FIFO e o retorna
    public double getEsperaFIFO() {

        for (int i = 0; i < tEspFIFO.length; i++) {
            tmeFIFO = tEspFIFO[i] + tmeFIFO;
        }
        return tmeFIFO / linhas;
    }

    //calcula tempo médio de espera de SJF e retorna
    public double getEsperaSJF() {

        for (int i = 0; i < tEspSJF.length; i++) {
            tmeSJF = tEspSJF[i] + tmeSJF;
        }
        return tmeSJF / linhas;
    }

    //calcula tempo médio de espera de Prioridade e o retorna
    public double getEsperaPrioridade() {

        for (int i = 0; i < tEspPrio.length; i++) {
            tmePrio = tEspPrio[i] + tmePrio;
        }
        return tmePrio / linhas;
    }

    //calcula tempo médio de espera de RR e o retorna
    public double getEsperaRR() {
        for (int i = 0; i < tEspRR.length; i++) {
            tmeRR = tEspRR[i] + tmeRR;
        }
        return tmeRR / linhas;
    }
    //calcula turnaround  médio de FIFO e o retorna

    public double getTurnaroundFIFO() {
        return (tmeFIFO + tempoTotal) / linhas;

    }

    //calcula turnaround médio de SJF e o retorna
    public double getTurnaroundSJF() {
        return (tmeSJF + tempoTotal) / linhas;
    }

    //calcula turnaround médio de Prioridade e o retorna
    public double getTurnaroundPrioridade() {
        return (tmePrio + tempoTotal) / linhas;
    }

    //calcula turnaround médio de RR e o retorna
    public double getTurnaroundRR() {
        return (tmeRR + tempoTotal) / linhas;
    }
}
