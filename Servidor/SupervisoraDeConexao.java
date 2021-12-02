///////////////////////
// CLASSE MODIFICADA //
///////////////////////

import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private Tracinhos                      tracinhos;
    private Palavra                        palavra;
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
		for (int c = 0; c < 3; c++)
		{
			ObjectOutputStream transmissor;
			try
			{
				transmissor =
				new ObjectOutputStream(
				this.grupo.get(c).getOutputStream());
			
			}
			catch (Exception erro)
			{
				return;
			}
			
			transmissores.add(transmissor);
        }
        
        ArrayList<ObjectInputStream> receptores;
        for (int c = 0; c < 3; c++)
		{
			ObjectInputStream receptor=null;
			try
			{
				receptor =
				new ObjectInputStream(
				this.grupo.get(c).getInputStream());
			}
			catch (Exception err0)
			{
				try
				{
					// será que ter um for aqui é bom?
					for (int c2 = 0; c2 < 3; c2++)
					{
						transmissores.get(c2).close();
					}
				}
				catch (Exception falha)
				{} // so tentando fechar antes de acabar a thread -> coment do prof
            
				return;
			}
			
			receptores.add(receptor);
		}
		
		for (int c = 0; c < grupo.size(); c++)
		{
			try
			{
				this.parceiros.add(new Parceiro (this.grupo.get(c),
												 receptores.get(c),
												 transmissores.get(c)));
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
                for (int c = 0; c < grupo.size(); c++)
				{
					try
					{
						this.usuarios.add(parceiros.get(c));
					}
					catch (Exception erro)
					{} // sei que passei os parametros corretos
				}
            }


			// COMUNICADOS DO CLIENTE
			/* PEDIDO PARA SAIR               -> FEITO (?) - FALTA TESTAR
			 * COMUNICADO CHUTE DE LETRA      
			 * COMUNICADO CHUTE DE PALAVRA    -> FEITO (?) - FALTA TESTAR
			 */
			// COMUNICADOS DO SERVIDOR (NESSA TAREFA SUPERVISORA)
			/*
			 * COMUNICADO DE ERRO                       
			 * COMUNICADO DE ACERTO            
			 * COMUNICADO DE VITÓRIA          -> FEITO - TEM QUE PROGRAMAR OQ ELE FAZ NO CLIENTE
			 * COMUNICADO DE DERROTA          -> FEITO - TEM QUE PROGRAMAR OQ ELE FAZ NO CLIENTE
			 * COMUNICADO TRACINHOS           -> FEITO - TEM QUE PROGRAMAR OQ ELE FAZ NO CLIENTE
			 * COMUNICADO LETRAS JA DIGITADAS -> FEITO - TEM QUE PROGRAMAR OQ ELE FAZ NO CLIENTE
			 */
				 
            for(;;)
            {
				for (int c = 0; c < parceiros.size(); c++)
				{
					for (int c2 = 0; c2 < parceiros.size(); c2++)
					{
						this.parceiros.get(c2).receba (new ComunicadoTracinhos (this.tracinhos));
						this.parceiros.get(c2).receba (new ComunicadoLetrasJaDigitadas (this.controladorDeLetrasJaDigitadas));
					}
					
					Comunicado comunicado = this.parceiros.get(c).envie ();

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
						ChuteDePalavra chuteDePalavra = (ChuteDePalavra)comunicado;
						
						// aqui tem que ver se a palavra é igual a palavra chutada
						// pelo usuário, se for, ele ganha o jogo e os outros dois
						// perdem (acabar a conexão com todos e enviar comunicados de
						// vitória e derrotas). Se não for, ele perde o jogo e os 
					    // outros dois continuam (acabar a conexão com o usuário que perdeu)
					    
					    // se for fazer assim, o comunicado de chuteDePalavra tem que conter
					    // a palavra que o usuário chutou
					    if (chuteDePalavra.getPalavra().equals(palavra))
					    {
							parceiros.get(c).receba(new ComunicadoDeVitoria());
							
							for (int c3 = parceiros.size()-1; c3 >= 0 ; c3--)
							{
								if (parceiros.get(c3) != parceiros.get(c))
								{
									parceiros.get(c3).receba(new ComunicadoDeDerrota());
								}
								
								synchronized (this.usuarios)
								{								
									this.usuarios.remove (this.parceiros.get(c3));
								}
								
								this.transmissores.remove (this.transmissores.get(c3));
								this.receptores   .remove (this.receptores.get(c3));
								this.parceiros.get(c3).adeus();
								
								this.parceiros.remove (this.parceiros.get(c3));
							}							
						}
						else
						{
							parceiros.get(c).receba(new ComunicadoDeDerrota());
							
							synchronized (this.usuarios)
							{								
								this.usuarios.remove (this.parceiros.get(c));
							}
							
							this.transmissores.remove (this.transmissores.get(c));
							this.receptores   .remove (this.receptores.get(c));
							
							this.parceiros.get(c).adeus();
							this.parceiros.remove (this.parceiros.get(c));
						}
						
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
							this.usuarios.remove (this.parceiros.get(c));
						}
						this.transmissores.remove (this.transmissores.get(c)); 
						this.receptores   .remove (this.receptores.get(c));
						this.parceiros.get(c).adeus();
						
						this.parceiros.remove (this.parceiros.get(c));
						
						if (parceiros.size() == 1)
						{
							parceiros.get(0).receba (new ComunicadoDeVitoria());
						}
					}
				}
            }
        }
        catch (Exception erro)
        {
            try
            {
				for (int c = parceiros.size()-1; c >= 0; c--)
				{
					transmissores.get(c).close ();
					receptores   .get(c).close ();
				}
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
