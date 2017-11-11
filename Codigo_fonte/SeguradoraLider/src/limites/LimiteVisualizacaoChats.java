package limites;

import controladores.ControleLider;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import model.Mensagem;

public class LimiteVisualizacaoChats extends JPanel {

    private JTextArea chat1TA, chat2TA, todosTA;
    private JToggleButton chat1TB, chat2TB, todosTB;
    private ButtonGroup grupo;
    private JScrollPane chat1SP, chat2SP, todosSP;
    private JTextField entradaMsg;
    private JPanel central;
    private JButton enviar;
    private CardLayout card;
    private String deptUsando = "", usuario = "";
    private ControleLider objCtrl;

    public static final String TI = "TI";
    public static final String ADM = "ADM";
    public static final String PROD = "PROD";
    public static final String GERENCIAL = "GER";

    /**
     * Gera uma classe que e filha de JPanel que exibe os chats
     *
     * @param pCtrl controlador da aplicacao
     * @param dept departamento que esta usando o chat
     * @param usuario nickname do usuario
     */
    public LimiteVisualizacaoChats(ControleLider pCtrl, String dept, String usuario) {
        //Iniciar o departamento que esta usando o chat e o usuario
        this.deptUsando = dept;
        this.usuario = usuario;
        objCtrl = pCtrl;
        
        //criar text areas
        chat1TA = new JTextArea(10, 45);
        chat1TA.setText("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::HISTÓRICO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
        chat1TA.setEditable(false);
        chat2TA = new JTextArea(10, 45);
        chat2TA.setText("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::HISTÓRICO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
        chat2TA.setEditable(false);
        todosTA = new JTextArea(10, 45);
        todosTA.setText("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::HISTÓRICO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
        todosTA.setEditable(false);
        
        //Criar JToggleButtons
        criarBotoes(dept);
        enviar = new JButton("Enviar");
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });

        //criar paineis de rolagem
        chat1SP = new JScrollPane(chat1TA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chat2SP = new JScrollPane(chat2TA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        todosSP = new JScrollPane(todosTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //criar JTextField
        entradaMsg = new JTextField(45);
        entradaMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });

        //Criar paineis
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        central = new JPanel();
        JPanel barraMensagem = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        //adicionar componentes a seus devidos paineis
        //=>painel que fica no topo, com os botoes
        painelTopo.add(chat1TB);
        painelTopo.add(todosTB);
        painelTopo.add(chat2TB);
        //=>painel central, com cardLayout, que possui as caixas de texto
        card = new CardLayout();
        central.setLayout(card);
        criarPainelCentral();
        central.add(chat1SP);
        central.add(chat2SP);
        central.add(todosSP);
        //=>Painel inferior, onde é inserida a mensagem
        barraMensagem.add(entradaMsg);
        barraMensagem.add(enviar);

        //adicionar tudo ao this
        super.setLayout(new BorderLayout());
        super.add(painelTopo, BorderLayout.PAGE_START);
        super.add(central, BorderLayout.CENTER);
        super.add(barraMensagem, BorderLayout.PAGE_END);
    }

    /**
     * Cria os botoes para selecionar qual chat
     *
     * @param dept departamento que esta usando o chat
     */
    private void criarBotoes(String departamento) {
        if (departamento.equals(LimiteVisualizacaoChats.TI)) {
            chat1TB = new JToggleButton("ADM");
            todosTB = new JToggleButton("Grupo Empresa");
            chat2TB = new JToggleButton("PROD");
        } else {
            if (departamento.equals(LimiteVisualizacaoChats.ADM)) {
                chat1TB = new JToggleButton("TI");
                todosTB = new JToggleButton("Grupo Empresa");
                chat2TB = new JToggleButton("PROD");
            } else {
                chat1TB = new JToggleButton("ADM");
                todosTB = new JToggleButton("Grupo Empresa");
                chat2TB = new JToggleButton("TI");
            }
        }

        chat1TB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(central, chat1TB.getText());
                central.revalidate();
            }
        });
        chat2TB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(central, chat2TB.getText());
                central.revalidate();
            }
        });
        todosTB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(central, LimiteVisualizacaoChats.GERENCIAL);
                central.revalidate();
            }
        });

        grupo = new ButtonGroup();
        grupo.add(chat1TB);
        grupo.add(todosTB);
        grupo.add(chat2TB);
        super.revalidate();
        chat1TB.setSelected(true);
    }

    /**
     * Esse método cria o painel central, com cardLayout se baseando no
     * departamento que está usando o chat
     */
    private void criarPainelCentral() {
        if (deptUsando.equals(LimiteVisualizacaoChats.TI)) {
            card.addLayoutComponent(chat1SP, LimiteVisualizacaoChats.ADM);
            card.addLayoutComponent(chat2SP, LimiteVisualizacaoChats.PROD);
        } else {
            if (deptUsando.equals(LimiteVisualizacaoChats.ADM)) {
                card.addLayoutComponent(chat1SP, LimiteVisualizacaoChats.TI);
                card.addLayoutComponent(chat2SP, LimiteVisualizacaoChats.PROD);
            } else {
                card.addLayoutComponent(chat1SP, LimiteVisualizacaoChats.ADM);
                card.addLayoutComponent(chat2SP, LimiteVisualizacaoChats.TI);
            }
        }
        card.addLayoutComponent(todosSP, LimiteVisualizacaoChats.GERENCIAL);
    }

    /**
     * Metodo que aciona o controlador para enviar a mensagem pelo canal de
     * comunicacao. Acionado nos listeners do botao de envio.
     */
    public void enviarMensagem() {
        //Obter parametros necessarios
        String remetente = deptUsando;
        String mensagem = entradaMsg.getText();
        JTextArea adicionarMensagem;

        //Obter qual o destinatario da mensagem e exibi-la na interface
        if (chat1TB.isSelected()) {
            String destinatario = chat1TB.getText();
            adicionarMensagem = chat1TA;
        } else {
            if (chat2TB.isSelected()) {
                String destinatario = chat2TB.getText();
                adicionarMensagem = chat2TA;
            } else {
                String destinatario = LimiteVisualizacaoChats.GERENCIAL;
                adicionarMensagem = todosTA;
            }
        }

        //Verificar se e uma mensagem certa. O QUE UM IDIOTA VAI ENVIAR COM MENOS DE 2 CARACTERES?
        if (mensagem.length() < 2) {
            JOptionPane.showMessageDialog(central, "Componha uma mensagem de forma correta!");
        } else {
            //Limpar a entrada de texto e adicionar mensagem a interface
            entradaMsg.setText("");
            adicionarMensagem.append("\n[VOCE]: " + mensagem);

            //Definir destinatário
            String destino;
            if (chat1TB.isSelected()) {
                destino = chat1TB.getText();
            } else {
                if (chat2TB.isSelected()) {
                    destino = chat2TB.getText();
                } else {
                    destino = LimiteVisualizacaoChats.GERENCIAL;
                }
            }

            //Enviar a mensagem
            Mensagem msg = new Mensagem(mensagem, deptUsando, destino, usuario, new Date());
            objCtrl.enviarMensagem(msg);
        }
    }

    /**
     * Recebe uma mensagem vinda do controlador e imprime para usuario
     *
     * @param msg texto correspondente a mensagem
     */
    public void receberMensagem(Mensagem msg) {
        String texto = "[" + msg.getUsuario() + "/" + msg.getRementente() + "]: " + msg.getMensagem();

        //Seleciona em qual chat exibir de acordo com o remetente
        if (msg.getRementente().equals(chat1TB.getText()) && !msg.getDestinatario().equals(LimiteVisualizacaoChats.GERENCIAL)) {
            chat1TA.append("\n" + texto);
        } else {
            if (msg.getRementente().equals(chat2TB.getText()) && !msg.getDestinatario().equals(LimiteVisualizacaoChats.GERENCIAL)) {
                chat2TA.append("\n" + texto);
            } else {
                todosTA.append("\n" + texto);
            }
        }
    }
    
    public void receberEstado(ArrayList<Mensagem> listaDeEstados)
    {
        for(Mensagem msg : listaDeEstados)
        {
            this.receberMensagem(msg);
        }
    }
}
