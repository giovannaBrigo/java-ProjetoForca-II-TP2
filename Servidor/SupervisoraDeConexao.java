import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
	//valor=0; // aqui tem que ver oq vai usar da forca
	private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private ControladorDeErros             controladorDeErros;
    private Tracinhos                      tracinhos;
    private Palavra                        palavra;
    private ArrayList<Parceiro>            usuarios;
    private ArrayList<ArrayList<Socket>>   grupos;
    private ArrayList<Socket>              grupo;

    public SupervisoraDeConexao
    (ArrayList<ArrayList<Socket>> grupos, 
    ArrayList<Socket> grupo)
    throws Exception
    {
        if (grupo==null)
            throw new Exception ("Grupo ausente");

        if (grupos==null)
            throw new Exception ("Grupos ausentes");

        this.grupos = grupos;
        this.grupo  = grupo;
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
					// comandos as vezes nem tem tempode de serem executados direito
					// e o for demora mais ainda...
					for (int c = 0; c < 3; c++)
					{
						transmissores.get(c).close();
					}
				}
				catch (Exception falha)
				{} // so tentando fechar antes de acabar a thread
            
				return;
			}
			
			receptores.add(receptor);
		}

		for (int i = 0; i < grupo.size(); i++)
		{
			try
			{
				synchronized (this.usuarios)
				{
					this.usuarios.add(new Parceiro (this.grupo.get(i),
													receptores.get(i),
													transmissores.get(i)));
				}
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
        
        // Instanciamos um controlador de erros
		controladorDeErros = null;
		try
		{
			controladorDeErros = new ControladorDeErros ((int)(palavra.getTamanho()*0.6));
		}
		catch (Exception erro)
		{}
				
        try
        {
            while (tracinhos.isAindaComTracinhos() &&
				  !controladorDeErros.isAtingidoMaximoDeErros())
		    {
				// COMUNICADOS DO CLIENTE
				/* PEDIDO DE TRACINHOS
				 * PEDIDO DE ERROS
				 * PEDIDO DE LETRAS JÁ DIGITADAS
				 * PEDIDO PARA SAIR
				 * COMUNICADO CHUTE DE LETRA
				 * COMUNICADO CHUTE DE PALAVRA
				 * 
				 * 
				 */
				// COMUNICADOS DO SERVIDOR
				/*
				 * COMUNICADO DE ERRO
				 * COMUNICADO DE ACERTO
				 * COMUNICADO DE VITÓRIA
				 * COMUNICADO DE DERROTA
				 */
				 
				 // como vamos pegar o parceiro certo?
				 // -> o vetor grupo tem sockets (não parceiros)
				 // -> o vetor usuarios tem parceiros, mas ele é geral (tem todos os usuários do jogo, de todos os grupos)
				for (int i = 0; i < 3; i++)
				{
					Comunicado comunicado = this.grupo.get(i).envie ();
				}
                /*

                if (comunicado==null)
                    return;
                else if (comunicado instanceof PedidoDeTracinhos)
                {
					PedidoDeOperacao pedidoDeOperacao = (PedidoDeOperacao)comunicado;
					
					switch (pedidoDeOperacao.getOperacao())
					{
						case '+':
						    this.valor += pedidoDeOperacao.getValor();
						    break;
						    
						case '-':
						    this.valor -= pedidoDeOperacao.getValor();
						    break;
						    
						case '*':
						    this.valor *= pedidoDeOperacao.getValor();
						    break;
						    
						case '/':
						    this.valor /= pedidoDeOperacao.getValor();
                    }
                }
                else if (comunicado instanceof PedidoDeResultado)
                {
                    this.usuario.receba (new Resultado (this.valor));
                }
                else if (comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
    
}
