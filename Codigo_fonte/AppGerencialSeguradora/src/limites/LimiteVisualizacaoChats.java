package limites;

import controladores.ControleLider;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import model.Mensagem;

public class LimiteVisualizacaoChats extends JPanel {

    private JTextArea todosTA;
    private JScrollPane todosSP;
    private JTextField entradaMsg;
    private JPanel central;
    private JButton enviar;
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

        todosTA = new JTextArea(10, 45);
        todosTA.setEditable(false);

        //Criar botoes
        enviar = new JButton("Enviar");
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        });

        //criar paineis de rolagem
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
        central = new JPanel();
        JPanel barraMensagem = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //adicionar componentes a seus devidos paineis
        //=>painel do topo, que contem um Label
        top.add(new JLabel("Olá "+usuario+", as mensagens que você enviar serão exibidas a todos os funcionários utilizando o grupo da empresa."));
        //=>painel central, com cardLayout, que possui as caixas de texto
        central.setLayout(new BorderLayout());
        central.add(todosSP, BorderLayout.CENTER);
        //=>Painel inferior, onde é inserida a mensagem
        barraMensagem.add(entradaMsg);
        barraMensagem.add(enviar);

        //adicionar tudo ao this
        super.setLayout(new BorderLayout());
        super.add(top,BorderLayout.PAGE_START);
        super.add(central, BorderLayout.CENTER);
        super.add(barraMensagem, BorderLayout.PAGE_END);
    }

    /**
     * Metodo que aciona o controlador para enviar a mensagem pelo canal de
     * comunicacao. Acionado nos listeners do botao de envio.
     */
    public void enviarMensagem() {
        //Obter parametros necessarios
        String remetente = LimiteVisualizacaoChats.GERENCIAL;
        String destinatario = LimiteVisualizacaoChats.GERENCIAL;
        String mensagem = entradaMsg.getText();

        //Verificar se e uma mensagem certa. O QUE UM IDIOTA VAI ENVIAR COM MENOS DE 2 CARACTERES?
        if (mensagem.length() < 2) {
            JOptionPane.showMessageDialog(central, "Componha uma mensagem de forma correta!");
        } else {
            //Limpar a entrada de texto e adicionar mensagem a interface
            entradaMsg.setText("");
            todosTA.append("\n[VOCE]: " + mensagem);

            //Enviar a mensagem
            Mensagem msg = new Mensagem(mensagem.toUpperCase()+"\n", deptUsando, destinatario, usuario, new Date());
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
        todosTA.append("\n" + texto);
    }

    public void receberEstado(ArrayList<Mensagem> listaDeEstados) {
        for (Mensagem msg : listaDeEstados) {
            this.receberMensagem(msg);
        }
        todosTA.append("\n------------------------------------------- AS MENSAGENS ACIMA FORAM ENVIADAS ANTES DE VOCE ENTRAR NO CHAT-------------------------------------------\n");
    }
}
