package limites;

import controladores.ControleLider;
import java.awt.CardLayout;
import java.util.ArrayList;
import javax.swing.*;
import model.Mensagem;

public class LimitePrincipal extends JFrame
{
    private LimiteSelecaoDepto interfaceInicial;
    private LimiteVisualizacaoChats interfaceComunicacao;
    private String usuario,departamento;
    private ControleLider objCtrl;
    private JPanel central;
    private CardLayout card;
    
    public LimitePrincipal(ControleLider pCtrl)
    {
        //Receber parametros necessarios
        objCtrl = pCtrl;        
        //criar painel central
        //->CRIAR SUBPAINEIS
        interfaceInicial = new LimiteSelecaoDepto(this);
        //->CRIAR PAINEL CENTRAL
        card = new CardLayout();
        central = new JPanel(card);
        //->ADICIONAR SUBPAINEIS AO PAINEL CENTRAL
        card.addLayoutComponent(interfaceInicial, "1");
        central.add(interfaceInicial);
        
        //Adicionar os componentes a JFrame
        super.add(central);
        
        //Setar configurações da JFrame
        super.setTitle("Comunicação seguradora Líder");
        super.setSize(800,600);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }
    
    /**
     * Recebe uma mensagem vinda dos controladores
     * @param msg mensagem completa recebida
     */
    public void receberMensagem(Mensagem msg)
    {
        interfaceComunicacao.receberMensagem(msg);
    }
    
    /**
     * Cria a interface do chat apos saber o usuario e seu departamento
     * @param user usuario do chat, um nickname ou apelido
     * @param Dept departamento do usuario
     */
    public void exibirChat(String user, String Dept)
    {
        //Receber parametros da interfae
        this.usuario = user;
        this.departamento = Dept;
        
        //Setar parametros no controlador
        objCtrl.setarParamentros(usuario, departamento);
        interfaceComunicacao = new LimiteVisualizacaoChats(objCtrl, departamento, usuario);
        objCtrl.atualizarEstado();
        card.addLayoutComponent(interfaceComunicacao, "2");        
        central.add(interfaceComunicacao);
        
        //Atualizar interface
        card.show(central, "2");
    }
    
    public void atualizacaoDeEstado(ArrayList<Mensagem> listaDeEstados)
    {
        interfaceComunicacao.receberEstado(listaDeEstados);
    }
    
    /**
     * MATA A JANELA, UAU
     */
    public void matarAplicacao()
    {
        super.dispose();
    }
}
