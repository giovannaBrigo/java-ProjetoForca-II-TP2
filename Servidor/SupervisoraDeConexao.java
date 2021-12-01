///////////////////////
// CLASSE MODIFICADA //
///////////////////////

import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    // private double              valor=0; // aqui tem que ver oq vai usar da forca
    // PARA O JOGO DA FORCA:
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private Tracinhos                      tracinhos;
    private Palavra                        palavra;
    /////////////////////////////////////////////////////////////////////////////
    private ArrayList<Socket>              grupo;
    private ArrayList<Parceiro>            usuarios;
    private ArrayList<Parceiro>            parceiros;

    public SupervisoraDeConexao
    (ArrayList<Socket> grupo, ArrayList<Parceiro> usuarios)
    throws Exception
    {
        if (grupo==null)
            throw new Exception ("Grupo ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

		this.usuarios = usuarios;
		
		// Instanciamos this.grupo
		this.grupo = grupo;
		
		this.parceiros = new ArrayList<Parceiro> ();
    }

    public void run ()
    {
		ArrayList<ObjectOutputStream> transmissores;
		for (int i = 0; i < 3; i++)
		{
			ObjectOutputStream transmissor;
			try
			{
				transmissor =
				new ObjectOutputStream(
				this.grupo.get(i).getOutputStream());
			
			}
			catch (Exception erro)
			{
				return;
			}
			
			transmissores.add(transmissor);
        }
        
        ArrayList<ObjectInputStream> receptores;
        for (int i = 0; i < 3; i++)
		{
			ObjectInputStream receptor=null;
			try
			{
				receptor=
				new ObjectInputStream(
				this.grupo.get(i).getInputStream());
			}
			catch (Exception err0)
			{
				try
				{
					// será que ter um for aqui é bom? já que o prof falou que esses
					// comandos as vezes nem tem tempo de de serem executados direito
					// e o for demora mais ainda...
					for (int c = 0; c < 3; c++)
					{
						transmissores.get(c).close();
					}
				}
				catch (Exception falha)
				{} // so tentando fechar antes de acabar a thread -> coment do prof
            
				return;
			}
			
			receptores.add(receptor);
		}
		
		for (int i = 0; i < grupo.size(); i++)
		{
			try
			{
				this.parceiros.add(new Parceiro (this.grupo.get(i),
												 receptores.get(i),
												 transmissores.get(i)));
			}
			catch (Exception erro)
			{} // sei que passei os parametros corretos
		}
		
		// Sorteamos uma palavra
		palavra = BancoDePalavras.getPalavraSorteada(); 
		
		// Instanciamos tracinhos
		tracinhos = null;
		try
		{
			tracinhos = new Tracinhos (palavra.getTamanho());
        }
        catch (Exception erro)
        {}
        
        // Instanciamos um controlador de letras já digitadas
        controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas ();

		// será que a gente faz um controlador de erros? mesmo que n influencie muito no jogo
        
        try
        {
            synchronized (this.usuarios)
            {
                for (int i = 0; i < grupo.size(); i++)
				{
					try
					{
						this.usuarios.add(parceiros.get(i));
					}
					catch (Exception erro)
					{} // sei que passei os parametros corretos
				}
            }


			// COMUNICADOS DO CLIENTE
			/* PEDIDO PARA SAIR
			 * COMUNICADO CHUTE DE LETRA
			 * COMUNICADO CHUTE DE PALAVRA
			 */
			// COMUNICADOS DO SERVIDOR (NESSA TAREFA SUPERVISORA)
			/*
			 * COMUNICADO DE ERRO
			 * COMUNICADO DE ACERTO
			 * COMUNICADO DE VITÓRIA
			 * COMUNICADO DE DERROTA
			 * COMUNICADO TRACINHOS
			 * COMUNICADO LETRAS JA DIGITADAS
			 */
				 
            for(;;)
            {
				for (int i = 0; i < parceiros.size(); i++)
				{
					// o mesmo for????????? :( n sei AAAAAAAAA
					for (int c = 0; c < parceiros.size(); c++)
					{
						// tem que fazer os dois comunicados
						this.parceiros.get(c).receba (new ComunicadoTracinhos(this.tracinhos));
						this.parceiros.get(c).receba (new ComunicadoLetrasJaDigitadas (this.controladorDeLetrasJaDigitadas));
					}
					
					// recebemos um comunicado do usuario, seguindo a ordem de entrada no grupo
					Comunicado comunicado = this.parceiros.get(i).envie ();

					if (comunicado==null)
						return;
					else if (comunicado instanceof ChuteDeLetra)
					{
						// acho que tem que melhorar o nome desse comunicado kkkkkkk
						ChuteDeLetra chuteDeLetra = (ChuteDeLetra)comunicado;
					
						// aqui tem que pegar a letra que o usuário chutou
						// e ver se ela existe na palavra, se não existir
						// passa a vez pro próximo usuário; se existir,
						// atualiza tracinhos
						
					}
					else if (comunicado instanceof ChuteDePalavra)
					{
						// aqui tem que ver se a palvra é igual a palavra chutada
						// pelo usuário, se for, ele ganha o jogo e os outros dois
						// perdem (acabar a conexão com todos e enviar comunicados de
						// vitória e derrotas). Se não for, ele perde o jogo e os 
					    // outros dois continuam (acabar a conexão com o usuário que perdeu)
						
					}
					else if (comunicado instanceof PedidoParaSair)
					{
						// aqui, se o usuário pedir para sair, vamos acabar 
						// a conexão com ele.
						// se ele estiver num grupo de 3, ele sai desse grupo
						// e os outros dois continuam.
						// se ele estiver num grupo de 2, ele sai do grupo
						// e o jogador que restou ganha
						
						synchronized (this.usuarios)
						{
							this.usuarios.remove (this.parceiros.get(i));
						}
						this.parceiros.get(i).adeus();
						
						// n sei se preciso fazer isso
						this.parceiros.remove (this.parceiros.get(i));
						
						if (parceiros.size() == 1)
						{
							parceiros.get(0).receba (new ComunicadoDeVitoria());
						}
					}
				}
            }
        }
        catch (Exception erro) // depois tenho que verificar esse catch
        {
            try
            {
				for (int i = 0; i < parceiros.size(); i++)
				{
					transmissores.get(i).close ();
					receptores.get(i)   .close ();
				}
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
