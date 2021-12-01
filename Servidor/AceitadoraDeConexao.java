///////////////////////
// CLASSE MODIFICADA //
///////////////////////

import java.net.*;
import java.util.*;

public class AceitadoraDeConexao extends Thread
{
    private ServerSocket pedido;
    ArrayList<Parceiro> usuarios;
    ArrayList<Socket>   grupo;
    
    public AceitadoraDeConexao
    (String porta, ArrayList usuarios)
    throws Exception
    {
        if (porta==null)
            throw new Exception ("Porta ausente");

        try
        {
            this.pedido =
            new ServerSocket (Integer.parseInt(porta));
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
        }

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");
            
        this.usuarios = usuarios;
        this.grupo    = new ArrayList<Socket>;
    }

    public void run ()
    {
        for(;;)
        {
			Socket conexao=null;
			try
			{
				conexao = this.pedido.accept(); 
				grupo.add(conexao);
			}
			catch (Exception erro)
			{
				continue;
			}
			
			// Depois de criar um grupo de 3
            if (grupo.size() == 3)
            {		
				// Instanciamos um objeto da SupervisoraDeConexao		
				SupervisoraDeConexao supervisoraDeConexao=null;
				try
				{
					supervisoraDeConexao = 
					new SupervisoraDeConexao (grupo, usuarios);
				}
				catch (Exception erro)
				{} // Sei que passei parametros corretos para o construtor
				
				// Startamos a SupervisoraDeConexao
				supervisoraDeConexao.start();
				// Limpamos todas as conexões do grupo 
				// (não sei se isso vai mexer com o grupo da supervisora)
				grupo.clear();
			}
        }
    }
}
