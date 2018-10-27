/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento do programa e pela comunicação entre
 * os dados do usuário e a interface gráfica.
 * @author Ikaro
 */

public class Sistema {
    public static Usuario usuario = new Usuario(); 
    private static float saldo_usuario;
    /**
     * Diretório do arquivo com os dados do usuário.
     */
    private static File user_file;
    
    /**
     * Data atual do sistema.
     */
    public static final Date HOJE = new Date();
    public static final int DIA = toCalendar(HOJE).get(Calendar.DATE);
    public static final int MES = toCalendar(HOJE).get(Calendar.MONTH);
    public static final int ANO = toCalendar(HOJE).get(Calendar.YEAR);
    /**
     * Data em que o usuário fez o login.
     */
    private static Date login_date;
    /**
     * Formato padrão de data do programa.
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String SAVE_DATE_FORMAT = "yyyy/MM/dd";
    /**
     * Formatador de data do sistema.
     */
    public static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat S_SDF = new SimpleDateFormat(SAVE_DATE_FORMAT);
    public static final String FILE_NAME = "user-info.txt";
    private static boolean first_run;
    
    /**
     * Determina o nome do usuário.
     * @param nome Nome do usuário.
     */
    public static void setNome(String nome) {
        usuario.setNome(nome);
    }
    
    /**
     * Determina o faturamento mensal do usuário.
     * @param valor Valor do salário do usuário.
     */
    public static void setRenda(float valor) {
        usuario.setRenda(valor);
    }
    
    /**
     * Determina o saldo atual do usuário.
     * @param valor Valor do saldo do usuário.
     */
    public static void setSaldo(float valor) {
        saldo_usuario = valor;
    }
    
    /**
     * Define a data de login do sistema.
     * @param date Data de login.
     */
    public static void setLoginDate(Date date) {
        login_date = date;
    }
    
    /**
     * Retorna o nome do usuário.
     * @return Nome do usuário.
     */
    public static String getNome() {
        return usuario.getNome();
    }
    
    /**
     * Retorna o saldo atual do usuário.
     * @return Saldo do usuário.
     */
    public static float getSaldo() {
        return saldo_usuario;
    }
    
    /**
     * Retorna a renda do usuário.
     * @return Renda do usuário.
     */
    public static float getRenda() {
        return usuario.getRenda();
    }
    
    /**
     * Retorna a lista de contas normais ou fixas do usuário.
     * @return Lista de contas normais ou fixas do usuário.
     */
    public static List<Conta> getContas() {
        return usuario.getContas();
    }
    
    /**
     * Retorna a lista de contas parceladas do usuário.
     * @return Lista de contas parceladas do usuário.
     */
    public static List<ContaParcelada> getContasParceladas() {
        return usuario.getParceladas();
    }
    
    public static Date getLoginDate() {
        return login_date;
    }
    
    public static boolean isFirstRun() {
        return first_run;
    }
    
    public static void adicionarConta(Conta c) {
        getContas().add(c);
        
        if(checarDataPagamento(c) && !c.isPaga()) {
            pagar(c);
        }
    }
    
    public static void adicionarConta(ContaParcelada cp) {
        getContasParceladas().add(cp);
        
        if(checarDataParcela(cp) && !cp.getParcelaAtual().isPaga()) {
            pagarParcela(cp);
        }
    }

    /**
     * Adiciona uma nova conta normal ou fixa à lista de contas.
     * @param desc Descrição da conta.
     * @param data Data de pagamento da conta.
     * @param val Valor total da conta.
     * @param t  Tipo da conta.
     * @param foi_paga Se a conta foi ou não paga.
     */
    public static void adicionarConta(String desc, Date data, float val, String t, boolean foi_paga){
        Conta nova = new Conta(desc, data, val, t, foi_paga);
        adicionarConta(nova);
    }
    
    /**
     * Adiciona uma nova conta parcelada à lista de contas parceladas.
     * @param desc Descrição da conta.
     * @param data Data de pagamento da conta.
     * @param val Valor total da conta.
     * @param num_parcelas Número de parcelas da conta.
     * @param foi_paga Se a conta foi paga.
     */
    public static void adicionarConta(String desc, Date data, float val, int num_parcelas, boolean foi_paga){
        ContaParcelada nova = new ContaParcelada(desc, data, val, num_parcelas, foi_paga);
        adicionarConta(nova);
    }
    
    /**
     * Remove a conta especificada da lista de contas normais ou fixas, se ela existir.
     * @param conta Conta que será removida.
     */
    public static void removerConta(Conta conta) {
        if(buscarConta(conta) != -1) {
            usuario.removerConta(conta);
        }
    } 
    
    /**
     * Remove a conta especificada da lista de contas parceladas, se ela existir.
     * @param conta conta que será removida
     */
    public static void removerConta(ContaParcelada conta) {
        usuario.removerConta(conta);
    }
    
    public static void removerPeloIndice(int index, Tipo t) {
        if(t.equals(Tipo.P)) {
            ContaParcelada cp = getContasParceladas().get(index);
            removerConta(cp);
        }else {
            Conta c = getContas().get(index);
            removerConta(c);
        }
    }
    
    /**
     * Busca uma conta e retorna seu índice, se a conta não estiver na lista,
     * retorna -1.
     * @param conta conta que será buscada
     * @return índice da conta
     */
    public static int buscarConta(Conta conta) {

        for(Conta c : getContas()) {
            if (c.equals(conta)) 
                return getContas().indexOf(c);
        }
        
        return -1;
    }
    
    /**
     * Busca uma conta e retorna seu índice, se a conta não estiver na lista,
     * retorna -1.
     * @param conta conta que será buscada
     * @return índice da conta
     */
    public static int buscarConta(ContaParcelada conta) {

        for(Conta c : getContasParceladas()) {
            if (c == conta) 
                return getContasParceladas().indexOf(c);
        }
        
        return -1;
    }
    
    /**
     * Chama a função de ordenação de contas do usuário.
     */
    public static void ordenar() {
        usuario.ordenarPorTipo();
    }
    
    /**
     * Checa se a data de pagamento da conta especificada coincide com a data
     * atual do sistema.
     * @param c Conta a ser verificada.
     * @return true se as datas forem iguais, false se não forem.
     * @see #checarDataParcela(money.ContaParcelada) 
     * @see #checarPagamentoContas() 
     * @see #pagar(money.Conta) 
     */
    public static boolean checarDataPagamento(Conta c) {
        return S_SDF.format(c.getData()).compareTo(S_SDF.format(HOJE)) <= 0;
    }
       
    /**
     * Compara a data de pagamento da parcela atual da conta cp com a data atual do sistema.
     * @param cp Conta a ser verificada.
     * @return true se a data da parcela for menor ou igual a data atual do sistema, false
     * se for maior.
     * @see #checarDataPagamento(money.Conta) 
     * @see #checarPagamentoContas()
     * @see #pagarParcela(money.ContaParcelada) 
     */
    public static boolean checarDataParcela(ContaParcelada cp) {
        return S_SDF.format(cp.getParcelaAtual().getData()).compareTo(S_SDF.format(HOJE)) <= 0;
    }
       
    /**
     * Desconta o valor da conta especificada do saldo do usuário.
     * @param c Conta cujo valor será descontado do saldo.
     * @see #pagarParcela(money.ContaParcelada) 
     */
    public static void pagar(Conta c) {
        saldo_usuario -= c.valor;
        if(c.tipo.equals(Tipo.F)) {
            c.atualizarData();
        }else {
            c.setPaga(true);
        }
    }
    
    /**
     * Desconta o valor da parcela da conta especificada do saldo do usuário.
     * @param cp Conta da qual será paga a atual parcela.
     * @see #pagar(money.Conta) 
     */
    public static void pagarParcela(ContaParcelada cp) {
        saldo_usuario -= cp.getValorParcela();
        cp.pagarParcela();
    }
    
    /**
     * Checa se chegou a data de pagamento das contas fixas e parceladas, se sim, 
     * elas são pagas.
     * @see #checarDataPagamento(money.Conta) 
     * @see #checarDataParcela(money.ContaParcelada) 
     */
    public static void checarPagamentoContas() {
        
        for(Conta c : getContas()) {
            if(c.getTipo().equals(Tipo.F)) {
                if(checarDataPagamento(c) && !c.isPaga()) {
                    pagar(c);
                }
            }
        }
        
        for(ContaParcelada cp : getContasParceladas()) {
            if(checarDataParcela(cp) && !cp.getParcelaAtual().isPaga()) {
                pagarParcela(cp);
            }
        }
    }
    
    /**
     * Verifica a data de pagamento de contas fixas e parceladas, no caso da data
     * coincidir com a data atual do sistema, é efetuado o pagamento.
     */
    public static void atualizarSaldo() {
        setSaldo(getSaldo() + usuario.getRenda());
    }

    /**
     * Checa se o dia da data HOJE é 1.
     * @return true se o dia for igual à 1, false não for.
     */
    public static boolean eDiaPrimeiro() {
        return toCalendar(HOJE).get(Calendar.DATE) == 1;
    }
    
    /**
     * Checa se o mês atual é maior que o mês da data de login.
     * @return true se o mês atual for maior que o mês de login, false se for
     * menor ou igual.
     */
    public static boolean mesAposLogin() {
        int mes_login = toCalendar(login_date).get(Calendar.MONTH);
        
        return MES > mes_login;
    }
    
    /**
     * Inicializa o sistema, abrindo e lendo o arquivo com os dados do usuário.
     * @throws IOException
     * @throws ParseException 
     * @see #carregar() 
     * @see #salvar() 
     */
    public static void inicializar() throws IOException, ParseException {
        abrirArquivo();
        carregar();
        checarPagamentoContas();
        
        if(eDiaPrimeiro() && mesAposLogin()) {
            atualizarSaldo();
        }
    }
    
    /**
     * Cria, se já não existir, e tenta abrir o arquivo com os dados do usuário.
     * @throws IOException
     */
    public static void abrirArquivo() throws IOException {
        //cria (se já não exisir) o diretório data
        File dir = new File("data");
        dir.mkdir();
        
        //cria (se já não existir) o arquivo user-info.txt na pasta data.
        //guardando seu caminho na variável user_file.
        user_file = new File(dir + "\\" + FILE_NAME);
        user_file.createNewFile();
    }

    /**
     * Lê o arquivo com as informações salvas do usuário, armazenando tais informações
     * em suas devidas variáveis.
     * @throws IOException
     * @throws ParseException
     */
    public static void carregar() throws IOException, ParseException {  
        //tenta-se criar um novo BufferedReader, que será usado para ler o arquivo com os dados salvos
        try (BufferedReader leitor = new BufferedReader(new FileReader(user_file.getAbsolutePath()))) {
            String linha = leitor.readLine();
            
            if(linha == null) {
                first_run = true;
                setSaldo(getRenda());
                return;
            }
            
            while(linha != null) { 
                
                switch(linha) {  
                    case "DATA_LOGIN":
                        linha = leitor.readLine();
                        setLoginDate(SDF.parse(linha));
                        break;
                        
                    case "NOME":
                        linha = leitor.readLine();
                        usuario.setNome(linha);
                        break;
                    
                    case "RENDA":
                        linha = leitor.readLine();
                        usuario.setRenda(Float.parseFloat(linha));
                        break;
                        
                    case "SALDO":
                        linha = leitor.readLine();
                        saldo_usuario = Float.parseFloat(linha);
                        break;

                    case "CONTA":
                        linha = leitor.readLine();
                        
                        //guarda em um array de strings cada parte da linha que está separada por hífen
                        String[] info = linha.split("-", 5);
                        boolean foi_paga = info[4].equals("s");
                        
                        //se a conta for normal ou fixa
                        if(info[0].equals(Tipo.N.getText()) || info[0].equals(Tipo.F.getText())) {
                            /*
                            info[0] = tipo da conta, info[1] = descrição da conta
                            info[2] = data de pagamento da conta, info[3] = valor total da conta 
                            info[4] = se a conta foi paga*/
                            
                            //adiciona a conta à lista de contas normais e fixas
                            adicionarConta(
                                info[1], SDF.parse(info[2]), Float.parseFloat(info[3]), info[0], foi_paga
                            );
                        }
                        else {
                            /*
                            info[0] = descrição da conta, info[1] = data da primeira parcela.
                            info[2] = valor total da conta, info[3] = número de parcelas da conta.
                            info[4] = se a conta foi paga.
                            p_info[0] = data de pagamento da parcela.
                            p_info[1] = se a parcela foi paga.
                            p_info[2] = valor da parcela*/
                            List<Parcela> parcelas = new ArrayList();
                            
                            do {
                                switch(linha) {
                                    case "PARCELA":
                                        linha = leitor.readLine();
                                        String[] p_info = linha.split("-", 3);
                                        boolean p_paga = p_info[1].equals("s");
                                        parcelas.add(
                                            new Parcela(
                                                SDF.parse(p_info[0]), p_paga, Float.parseFloat(p_info[2])
                                            )
                                        );
                                        break;
                                        
                                    default:
                                        linha = leitor.readLine();
                                        break;
                                }
                                
                            }while(!linha.equals("FIMPARCELAS"));
                            
                            boolean paga = info[4].equals("s");
                            Date primeira = parcelas.get(0).getData();
                            Date ultima = parcelas.get(parcelas.size()-1).getData();
                            ContaParcelada nova = new ContaParcelada(
                                info[0], primeira, Float.parseFloat(info[2]), Integer.parseInt(info[3]), paga
                            );
                            
                            //todas as parcelas lidas do arquivo são adicionadas à lista de parcelas da conta
                            nova.getParcelas().addAll(0, parcelas);
                            //redefine a data de pagamento da conta, tornando-a igual a data de pagamento da última parcela.
                            nova.setData(ultima);
                            //é definida a parcela atual da conta
                            nova.setParcelaAtual();
                            //a conta é adicionada a lista de contas do usuário.
                            adicionarConta(nova);
                        }
                        break;
                        
                    default:
                        linha = leitor.readLine();
                        break;                    
                }
            }
        }
        
        first_run = false;
    }

    /**
     * Salva as informações do usuário.
     * @throws IOException
     */
    public static void salvar() throws IOException {
        
        try (Writer escritor = new BufferedWriter(new FileWriter(user_file.getAbsolutePath()))) {
            
            escritor.append(String.format("DATA_LOGIN\n%s\n", SDF.format(getLoginDate())));
            escritor.append(String.format("NOME\n%s\n", getNome()));
            escritor.append(String.format("RENDA\n%s\n", getRenda()));
            escritor.append(String.format("SALDO\n%s\n", getSaldo()));
            
            for(Conta c : getContas()) {
                escritor.append(c.toString());
            }
            
            for(ContaParcelada c : getContasParceladas()) {
                escritor.append(c.toString());
            }
            
            escritor.close();
        }     
    }
    
    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}