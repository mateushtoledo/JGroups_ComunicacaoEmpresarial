package controladores;

import java.util.ArrayList;
import java.util.Collections;
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
        dept = pDepto;

        //Iniciar canais de comunicacao
        canal = new Comunicador(listaDeMensagens, this);

        //Cada canal e o caminho de um departamento. O canal 4 e o grupo com todos os DEPTs
        try {
            canal.start("SEGURADORA");
        } catch (Exception e) {
            System.err.println("[ERRO]: Falha ao iniciar canal! =" + e.getMessage());
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

    /**
     * Retorna todas as mensagens recebidas no chat
     *
     * @param pDepto departamento que vieram as mensagens
     * @return lista com as mensagens do chat que envolve o departamento de TI
     */
    public ArrayList<Mensagem> obterListaDeMensagens(String pDepto) {
        ArrayList<Mensagem> msgsGrupo = new ArrayList<>();
        
        if(pDepto.equals(dept))
            return msgsGrupo;
        
        for(Mensagem msg: listaDeMensagens)
        {
            if( (msg.getRementente().equals(dept)&&msg.getDestinatario().equals(pDepto)&&msg.getUsuario().equalsIgnoreCase("Voce")) || (msg.getRementente().equals(pDepto)&&msg.getDestinatario().equals(dept)))
                msgsGrupo.add(msg);
        }
        
        //Ordenar lista de mensagens por data
        System.out.println("Total de mensagens de "+dept+"/"+pDepto+" = "+msgsGrupo.size());
        return ordenacaoData(msgsGrupo);
    }

    /**
     * Retorna todas as mensagens recebidas no chat com todos os departamentos
     * @return lista com as mensagens do chat que envolve o departamento de TI
     */
    public ArrayList<Mensagem> obterListaMsgGrupo() {
        ArrayList<Mensagem> msgsGrupo = new ArrayList<>();

        listaDeMensagens.stream().filter((msg) -> (msg.getDestinatario().equals(LimiteVisualizacaoChats.GERENCIAL) || msg.getRementente().equals(LimiteVisualizacaoChats.GERENCIAL))).forEachOrdered((msg) -> {
            msgsGrupo.add(msg);
        });

        //Ordenar lista de mensagens por data
        System.out.println("Total de mensagens do grupo: "+msgsGrupo.size());
        return ordenacaoData(msgsGrupo);
    }

    /**
     * Ordena as mensagens por data e horário
     *
     * @param listaMsg lista das mensagens que devem ser ordenadas
     * @return lista ordenada por data e horário
     */
    public ArrayList<Mensagem> ordenacaoData(ArrayList<Mensagem> listaMsg) {
        Collections.sort(listaMsg);
        return listaMsg;
    }
}
