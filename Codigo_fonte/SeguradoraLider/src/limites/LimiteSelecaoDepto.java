package limites;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LimiteSelecaoDepto extends JPanel
{
    private JComboBox escolha;
    private JLabel labelInterf,labelNome;
    private JTextField nomeTF;
    private JButton iniciar,sair;
    private BoxLayout box;
    private JPanel painelCentral,painelBotoes,painelFluxo;
    private LimitePrincipal pai;
    
    /**
     * GERA UM JPANEL QUE TEM O QUE E NECESSARIO PARA SELECIONAR O DEPARTAMENTO E DEFINIR APELIDO DE USUARIO
     * @param pai Inteface principal. O Frame que exibe esse painel
     */
    public LimiteSelecaoDepto(LimitePrincipal pai)
    {
        this.pai = pai;
        
        //criar labels e caixas de texto
        labelInterf =   new JLabel("Selecione seu departamento");
        labelNome =     new JLabel("Informe seu nome:");
        nomeTF = new JTextField(15);
        
        //Criar opcoes
        escolha = new JComboBox();
        escolha.addItem("TI - Tecnologia da informação");
        escolha.addItem("PROD - Produção");
        escolha.addItem("ADM - Administração");
        
        //Criar botoes
        sair = new JButton("Sair do sistema");
        sair.setBackground(new Color(0,0,128));
        sair.setForeground(Color.WHITE);
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               pai.matarAplicacao();
            }
        });
        iniciar = new JButton("Iniciar Comunicação");
        iniciar.setBackground(new Color(0,0,128));
        iniciar.setForeground(Color.WHITE);
        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proximo();
            }
        });
        
        //Criar paineis
        painelCentral = new JPanel(new GridLayout(3, 2, 10, 10));
        painelCentral.setBackground(Color.white);
        painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        painelBotoes.setBackground(Color.white);
        painelFluxo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelFluxo.setBackground(Color.white);
        
        //adicionar componentes aos paineis
        painelBotoes.add(sair);
        painelBotoes.add(iniciar);
        
        painelCentral.add(labelNome);
        painelCentral.add(nomeTF);
        painelCentral.add(labelInterf);
        painelCentral.add(escolha);
        painelCentral.add(Box.createGlue());
        painelCentral.add(painelBotoes);
        
        painelFluxo.add(painelCentral);
        
       //Adicionar componentes ao painel principal
       super.setLayout(new GridLayout(3, 1));
       super.add(Box.createGlue());
       super.add(painelFluxo);
       super.add(Box.createGlue());
       super.setBackground(Color.white);
    }
    
    /**
     * Metodo acionado apos a selecao do departamento. Muda a interface para a visualizacao do chat
     */
    public void proximo()
    {
        String usuario = nomeTF.getText();
        String dept;
        
        if(usuario.length() <=3)
            JOptionPane.showMessageDialog(painelCentral,"Identifique-se de forma correta! Insira um nome adequado!");
        else
        {
            if(escolha.getSelectedIndex() == 0)
            {
                dept = LimiteVisualizacaoChats.TI;
            }
            else
            {
                if(escolha.getSelectedIndex() == 1)
                {
                    dept = LimiteVisualizacaoChats.PROD;
                }
                else
                {
                    dept = LimiteVisualizacaoChats.ADM;
                }
            }
            
            pai.exibirChat(usuario, dept);
        }
    }
}
