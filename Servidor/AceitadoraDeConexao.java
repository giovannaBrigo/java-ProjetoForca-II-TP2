import java.net.*;
import java.util.*;

public class AceitadoraDeConexao extends Thread
{
    private ServerSocket pedido;
    // será que pra instanciar eu tenho que tirar o Grupo<Parceiro>? pq isso parece
    // classe genérica
    // assim:
    private ArrayList<ArrayList<Socket>> grupos;  // arraylist que vai guardar os grupos
    // ou assim:
    // private ArrayList<<Grupo<Parceiro>> grupos; // arraylist que vai guardar os grupos
    // ou assim:
    // private ArrayList grupos;
    
    // será que posso instanciar aqui?
 // private ArrayList<Socket> grupo = new ArrayList(); // arraylist que guarda sockets 
	private ArrayList<Socket> grupo;

    public AceitadoraDeConexao
    (String porta, ArrayList grupos)
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

        if (grupos==null)
            throw new Exception ("Grupos ausentes");

        this.grupos = grupos;
        this.grupo  = new ArrayList<Socket> ();
        
        // no fim desse método teremos um arraylist usuários
        // e um server socket (servidor?) -> aceita conexões
        // e retorna um socket
    }

    public void run ()
    {
        for(;;)
        {
			Socket conexao=null;
			try
			{
				conexao = this.pedido.accept(); // espera UMA conexão ser aceita,
												// quando é, cria o socket conexao.
				grupo.add(conexao);             // quando uma conexão é aceita, 
												// adicionamos ela no grupo.
			}
			catch (Exception erro)
			{
				continue;
			}
			
			// depois de criar um grupo de 3
            if (grupo.size() == 3)
            {
				grupos.add(grupo); // adicionamos um grupo ao ArrayList grupo
				
				SupervisoraDeConexao supervisoraDeConexao=null;
				try
				{
					supervisoraDeConexao = 
					new SupervisoraDeConexao (grupo, grupos);
				}
				catch (Exception erro)
				{} // sei que passei parametros corretos para o construtor
				
				supervisoraDeConexao.start();
			}
        }
    }
}
