/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import main_system.Sistema;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import main_system.Conta;
import main_system.ContaParcelada;
import main_system.Tipo;

/**
 *
 * @author ikaro
 */
public class MainFrame extends javax.swing.JFrame { 
    private final DefaultTableModel model;
    private final NumberFormat nf;
    private final SimpleDateFormat sdf;
    private final List<Integer> indices;
    private final Calendar min_data;

    /**
     * Creates new form MainFrame
     * @throws java.io.IOException
     */
    public MainFrame() throws IOException {
        initComponents();
        setLookAndFeel("Windows");       
        setLocation();
        setTableSelectionColor();
        setSaveOnClose();
        setMaximized();
        setIcon();
        
        this.model = (DefaultTableModel) table.getModel();
        this.nf = NumberFormat.getNumberInstance();
        this.sdf = new SimpleDateFormat(Sistema.DATE_FORMAT);
        this.indices = new ArrayList();
        this.min_data = Calendar.getInstance();
        this.min_data.set(Sistema.ANO, Sistema.MES, 1);
        loadTableContent();
        setSaldoLabel();
    }
    
    /**
     * Define que o frame deve ser aberto maximizado.
     */
    private void setMaximized() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    /**
     * Define o valor mostrado na label do saldo.
     */
    public void setSaldoLabel() {
        user_balance_label.setText(nf.format(Sistema.getSaldo()));
    }
    
    private void setIcon() throws IOException {
        setIconImage(Toolkit.getDefaultToolkit().getImage("res\\16x16.png"));
    }
    
    private void setLocation() {
        this.setLocationRelativeTo(null);
    }
    
    private void setTableSelectionColor() {
        table.setSelectionBackground(java.awt.Color.LIGHT_GRAY);
    }

    //configura o look and feel do form
    private void setLookAndFeel(String name){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Faz com que este frame chame a função salvar() do gerenciador ao ser fechado.
     */
    private void setSaveOnClose(){
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    Sistema.salvar();
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void addRow(Object[] data) {
        model.addRow(data);
    }
    
    public void addIndice(int val) {
        this.indices.add(val);
    }
    
    public JTable getTable() {
        return this.table;
    }
    
    public List<Integer> getIndices() {
        return this.indices;
    }
    
    public Date getMinData() {
        return this.min_data.getTime();
    }
    
    /**
     * Carrega todas as contas do usuário nas linhas da tabela.
     */
    private void loadTableContent() throws NullPointerException{
        List<Conta> contas = Sistema.getContas();
        List<ContaParcelada> parceladas = Sistema.getContasParceladas();
        int count = 0;  //quantidade de contas adicionadas à tabela
        
        
        /*
         * criar o objeto que guarda as informações da conta.
         * adicionar o índice da conta à lista de índices.
         * adicionar uma nova linha à tabela com as informações do objeto. 
         */
        for(Conta c : contas) {
            Object[] row = new Object[] {
                ++count, c.getDescricao(), sdf.format(c.getData()),
                c.getValor(), c.getTipo().getText(), 0
            };
            addIndice(contas.indexOf(c));
            addRow(row);
        }
        
        for(ContaParcelada cp : parceladas) {
            Object[] row = new Object[] {
                ++count, cp.getDescricao(), sdf.format(cp.getData()),
                cp.getValor(), cp.getTipo().getText(), cp.getNumParcelas()
            };
            addIndice(parceladas.indexOf(cp));  
            addRow(row);
        }
        System.out.println(indices);
        remove.setEnabled(model.getRowCount() > 0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        header_panel = new javax.swing.JPanel();
        nome_label = new javax.swing.JLabel();
        user_name_label = new javax.swing.JLabel();
        balance_label = new javax.swing.JLabel();
        user_balance_label = new javax.swing.JLabel();
        body_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        bottom_panel = new javax.swing.JPanel();
        add = new javax.swing.JButton();
        remove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Money Manager");
        setBackground(new java.awt.Color(55, 68, 138));
        setExtendedState(MAXIMIZED_BOTH);
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 600));

        header_panel.setBackground(getBackground());
        header_panel.setForeground(new java.awt.Color(255, 255, 255));
        header_panel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        header_panel.setRequestFocusEnabled(false);
        java.awt.GridBagLayout header_panelLayout = new java.awt.GridBagLayout();
        header_panelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        header_panelLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        header_panel.setLayout(header_panelLayout);

        nome_label.setFont(header_panel.getFont().deriveFont(Font.BOLD));
        nome_label.setForeground(header_panel.getForeground());
        nome_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nome_label.setText("Nome:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        header_panel.add(nome_label, gridBagConstraints);

        user_name_label.setFont(header_panel.getFont().deriveFont(Font.BOLD));
        user_name_label.setForeground(header_panel.getForeground());
        user_name_label.setText(main_system.Sistema.getNome());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 80);
        header_panel.add(user_name_label, gridBagConstraints);

        balance_label.setFont(header_panel.getFont().deriveFont(Font.BOLD));
        balance_label.setForeground(header_panel.getForeground());
        balance_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        balance_label.setText("Saldo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 80, 0, 0);
        header_panel.add(balance_label, gridBagConstraints);

        user_balance_label.setFont(header_panel.getFont().deriveFont(Font.BOLD));
        user_balance_label.setForeground(header_panel.getForeground());
        user_balance_label.setText(String.format("%.2f", main_system.Sistema.getRenda()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        header_panel.add(user_balance_label, gridBagConstraints);

        getContentPane().add(header_panel, java.awt.BorderLayout.PAGE_START);

        body_panel.setBackground(getBackground());
        body_panel.setForeground(new java.awt.Color(255, 255, 255));
        body_panel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        body_panel.setLayout(new java.awt.BorderLayout(0, 10));

        table.setAutoCreateRowSorter(true);
        table.setFont(body_panel.getFont());
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Descrição", "Data de Pagamento", "Valor", "Tipo", "Parcelas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight((int)body_panel.getFont().getSize2D() + 4);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        table.setSelectionForeground(new java.awt.Color(0, 0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

        body_panel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(body_panel, java.awt.BorderLayout.CENTER);

        bottom_panel.setBackground(getBackground());
        bottom_panel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bottom_panel.setRequestFocusEnabled(false);

        add.setFont(bottom_panel.getFont());
        add.setText("Adicionar conta");
        add.setMaximumSize(new java.awt.Dimension(127, 35));
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        remove.setFont(bottom_panel.getFont());
        remove.setText("Remover conta");
        remove.setEnabled(false);
        remove.setMaximumSize(new java.awt.Dimension(127, 35));
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottom_panelLayout = new javax.swing.GroupLayout(bottom_panel);
        bottom_panel.setLayout(bottom_panelLayout);
        bottom_panelLayout.setHorizontalGroup(
            bottom_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottom_panelLayout.createSequentialGroup()
                .addContainerGap(187, Short.MAX_VALUE)
                .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottom_panelLayout.setVerticalGroup(
            bottom_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottom_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bottom_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(bottom_panel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        try { 
            new NovaConta(this).setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        remove.setEnabled(true);
    }//GEN-LAST:event_addActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        //guarda o índice da linha selecionada
        int row_index = table.getSelectedRow();
        //não executa nada se o índice for menor que 0
        if(row_index < 0) return; 
        //guarda a quantidade de linhas antes da linha selecionada ser removida
        int row_count;
        
        try{
            row_count = model.getRowCount(); 
            String tipo = model.getValueAt(row_index, 4).toString();
            //pega o índice da conta, que está armazenado na lista de índices
            int index = indices.get(row_index);
            
            //se o índice da linha selecionada for o último da tabela, a linha anterior
            //à ela será selecionada, caso contrário, se houver ao menos duas linhas na tabela
            //a linha posterior à linha selecionada, será selecionada, 
            //senão, o botão de remover contas é desativado.
            if(row_index+1 == row_count && row_index > 0) {
                table.setRowSelectionInterval(row_index-1, row_index-1);
            }else if(row_count > 1){
                table.setRowSelectionInterval(row_index, row_index); 
            }
            else {
                remove.setEnabled(false);
            }
            
            Sistema.removerPeloIndice(index, Tipo.fromString(tipo));    //remove a conta da lista de contas
            model.removeRow(row_index); //remove a linha escolhida pelo usuário.
        }catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Índice inválido.");
        }          
    }//GEN-LAST:event_removeActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JLabel balance_label;
    private javax.swing.JPanel body_panel;
    private javax.swing.JPanel bottom_panel;
    private javax.swing.JPanel header_panel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nome_label;
    private javax.swing.JButton remove;
    private javax.swing.JTable table;
    private javax.swing.JLabel user_balance_label;
    private javax.swing.JLabel user_name_label;
    // End of variables declaration//GEN-END:variables
}
