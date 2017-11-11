package controladores;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Mensagem;
import org.jgroups.Address;

public class Comunicador extends ReceiverAdapter
{
    private JChannel canal;
    private ArrayList<Mensagem> listaDeMensagens;
    private ControleLider objCtrl;
    
    //Metodo construtor
    public Comunicador(ArrayList<Mensagem> lista,ControleLider pCtrl)
    {
        listaDeMensagens = lista;
        objCtrl = pCtrl;
    }
    
    //Metodo ao criar a view
    @Override
    public void viewAccepted(View new_view) {
        System.out.println("-->Integrantes do chat: " + new_view);
    }
    
    //AO receber a mensagem
    @Override
    public void receive(Message msg) {
        Mensagem recebida = (Mensagem) msg.getObject();
        objCtrl.receberMensagem(recebida);
    }
    
    //Obter estado
    public void getState(OutputStream output) throws Exception {
        synchronized (listaDeMensagens) {
            Util.objectToStream(listaDeMensagens, new DataOutputStream(output));
        }
    }
    
    //Definir estado. Utilizado quando a pessoa entra em um canal ja aberto
    @SuppressWarnings("unchecked")
    @Override
    public void setState(InputStream input) throws Exception {
        this.listaDeMensagens = (ArrayList<Mensagem>) Util.objectFromStream(new DataInputStream(input));
        System.out.println("Estado recebido. A lista possui total de " + listaDeMensagens.size() + " mensagens...");
        objCtrl.atualizarEstado();
    }
    
    //Iniciar comunicacao
    public void start(String nomecanal) throws Exception {
        canal = new JChannel("udp.xml");
        canal.setReceiver(this);
        canal.connect(nomecanal);

        View vi = canal.getView();
        List<Address> end = vi.getMembers();
        for (int i = 0; i < end.size(); i++) {
            System.out.println(end.get(i));
        }
        
        canal.getState(null, 10000);
    }
    
    //Enviar mensagem
    public void enviarMsg(Mensagem envio)
    {
        Message msg = new Message(null ,null, envio);
        try {
            canal.send(msg);
        } catch (Exception e) {
            System.err.println("[ERRO]: Falha ao enviar mensagem!");
        }
    }
    
    //Fechar canal de comunicacao
    public void fechaCanal() {
        canal.close();
    }
}