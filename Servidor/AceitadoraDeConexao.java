// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.servidor;

import br.unicamp.cotuca.tp2mat2021.projetofinal.Parceiro;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class AceitadoraDeConexao extends Thread
{
    private ServerSocket        pedido;
    private ArrayList<Parceiro> usuarios;
    private ArrayList<Parceiro> grupo;    // ArrayList de parceiros chamado grupo

    public AceitadoraDeConexao (String porta, ArrayList<Parceiro> usuarios) throws Exception
    {
        if (porta==null)
            throw new Exception ("Porta ausente");

        try
        {
            this.pedido = new ServerSocket (Integer.parseInt(porta));
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
        }

        if (usuarios == null)
            throw new Exception("Usuarios ausente");

        this.usuarios = usuarios;

        // Instanciamos o ArrayList grupo
        grupo = new ArrayList<Parceiro>();
    }

    public void run ()
    {
        for (;;) // for infinito
        {
            Socket conexao = null;
            try
            {
                conexao = this.pedido.accept(); // Aceitamos uma conexão

                // Declaramos e instanciamos um transmissor para essa conexão
                ObjectOutputStream transmissor;
                try
                {
                    transmissor = new ObjectOutputStream (conexao.getOutputStream());
                }
                catch (Exception erro)
                {
                    continue;
                }

                // Declaramos e instanciamos um receptor para essa conexão
                ObjectInputStream receptor=null;
                try
                {
                    receptor = new ObjectInputStream (conexao.getInputStream());
                }
                catch (Exception erro)
                {
                    try
                    {
                        transmissor.close();
                    }
                    catch (Exception falha)
                    {}

                    continue;
                }

                // Declaramos e instanciamos um parceiro jogador, com a conexão, receptor e transmissor
                Parceiro jogador = null;
                try
                {
                    jogador =  new Parceiro (conexao,receptor,transmissor);
                }
                catch (Exception erro)
                {} // sei que passei os parametros corretos

                grupo.add(jogador); // Adicionamos o parceiro no grupo

                // Adicionamos o parceiro no ArrayList usuarios
                synchronized (this.usuarios)
                {
                    usuarios.add(jogador);
                }
            }
            catch (Exception erro)
            {
                continue; // Voltamos para o início do for
            }

            // Se o grupo tiver 3 pessoas
            if (grupo.size() == 3)
            {
                // Declaramos e instanciamos uma supervisora para o grupo
                SupervisoraDeConexao supervisoraDeConexao = null;
                try
                {
                    supervisoraDeConexao = new SupervisoraDeConexao(usuarios, grupo);
                }
                catch (Exception erro)
                {}

                // Startamos a supervisora
                supervisoraDeConexao.start();

                // Removemos todos os parceiros do grupo
                // (vamos começar a formar outro grupo na próxima repetição do for)
                grupo.clear();
            }
        }
    }
}