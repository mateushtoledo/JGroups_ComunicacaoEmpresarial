package model;

import java.io.Serializable;
import java.util.Date;

public class Mensagem implements Serializable,Comparable
{
    private String mensagem,rementente,destinatario,usuario;
    private Date data;
    
    /**
     * Cria uma mensagem no formato adequado para o dominio da aplicacao
     * @param mensagem texto que deve ser enviado/recebido
     * @param rementente identificador do canal emissor, do departamento que gerou a mensagem
     * @param destinatario identificador do canal receptor, do departamento que recebe a mensagem
     * @param usuario apelido de quem esta usando o chat
     * @param data data que serve como criterio de ordenacao da mensagem
     */
    public Mensagem(String mensagem, String rementente, String destinatario,String usuario, Date data) {
        this.mensagem = mensagem;
        this.rementente = rementente;
        this.usuario = usuario;
        this.destinatario = destinatario;
        this.data = data;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getRementente() {
        return rementente;
    }

    public void setRementente(String rementente) {
        this.rementente = rementente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public void setUsuario(String pUsuario)
    {
        this.usuario = pUsuario;
    }
    
    public String getUsuario()
    {
        return this.usuario;
    }
    
    /**
     * Compara duas datas para definir a ordenacao
     * @param o Mensagem que sera comparada com a atual
     * @return 0 caso sejam iguais, -1 caso a data dessa mensagem seja anterior e 1 caso seja mais recente
     */
    @Override
    public int compareTo(Object o){
        Mensagem outra = (Mensagem) o;
        //igual retorna 0
        if(outra.getData() == this.getData())
            return 0;
        else
        {
            //data anterior retorna -1
            if(this.getData().compareTo(outra.getData()) < 0)
                return -1;
            else
            {
                //Se for apos a data recebida, retorno 1
                return 1;
            }
        }
    }
    
}
