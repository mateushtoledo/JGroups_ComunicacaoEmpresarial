package controladores;

import java.util.ArrayList;
import limites.LimitePrincipal;
import limites.LimiteVisualizacaoChats;
import model.Mensagem;

public class ControleLider {

    private ArrayList<Mensagem> listaDeMensagens;
    private LimitePrincipal interfGrafica;
    private Comunicador canal;
    private String usuario;
    private String dept;

    public ControleLider() {
        //Setar IPv4 como padrão da JRE
        System.setProperty("java.net.preferIPv4Stack", "true");

        //Criar arraylist
        listaDeMensagens = new ArrayList<>();
        interfGrafica = new LimitePrincipal(this);
    }

    /**
     * Salva o usuario e o seu departamento vindos da interface. Apos isso sao
     * iniciados os canais de comunicacao.
     *
     * @param pUser usuario, nome de quem esta usando
     * @param pDepto departamento do funcionario que esta usando o chat
     */
    public void setarParamentros(String pUser, String pDepto) {
        //Salvar usuario e seu departamento
        usuario = pUser;
        dept = LimiteVisualizacaoChats.GERENCIAL;

        //Iniciar canais de comunicacao
        canal = new Comunicador(listaDeMensagens, this);

        //Cada canal e o caminho de um departamento. O canal 4 e o grupo com todos os DEPTs
        try {
            canal.start("SEGURADORA");
        } catch (Exception e) {
            System.err.println("[ERRO]: Falha ao iniciar canal! | [CAUSA]:" + e.getMessage());
        }
    }

    /**
     * Adiciona mensagem ao array do controlador e define por qual canal de
     * comunicacao deve ser enviada a mensagem
     *
     * @param msg Mensagem composta para ser enviada
     */
    public void enviarMensagem(Mensagem msg) {
        canal.enviarMsg(msg);
    }

    /**
     * Recebe uma mensagem de algum canal (O canal aciona esse metodo)
     *
     * @param msg Mensagem recebida e construida pelo canal de comunicacao
     */
    public void receberMensagem(Mensagem msg) {
        if (!(msg.getUsuario().equals(this.usuario) && msg.getRementente().equals(this.dept))) {
            //Exibir mensagem na interface
            interfGrafica.receberMensagem(msg);

            //Adicionar mensagem a lista de mensagens
            listaDeMensagens.add(msg);
        }
    }
    
    public void atualizarEstado()
    {
        interfGrafica.atualizacaoDeEstado(listaDeMensagens);
    }
}
