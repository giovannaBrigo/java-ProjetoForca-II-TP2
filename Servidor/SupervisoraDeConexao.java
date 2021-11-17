import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
 // private double              valor=0; // aqui tem que ver oq vai usar da forca
    private ArrayList<ArrayList<Socket>> grupos;
    private ArrayList<Socket>            grupo;
    private ArrayList<Parceiro>          usuarios;

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

/*
		for (int i = 0; i < grupo.size(); i++)
		{
			try
			{
				this.usuarios.add(new Parceiro (this.grupo.get(i),
												receptores.get(i),
												transmissores.get(i)));
			}
			catch (Exception erro)
			{} // sei que passei os parametros corretos
		}
		
        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }


            for(;;)
            {
                Comunicado comunicado = this.usuario.envie ();

                if (comunicado==null)
                    return;
                else if (comunicado instanceof PedidoDeOperacao)
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
*/
    }
    
}
