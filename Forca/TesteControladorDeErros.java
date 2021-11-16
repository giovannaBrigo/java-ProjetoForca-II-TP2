// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

class TesteControladorDeErros
{
	public static void main (String[] args)
    {
		// TESTES DO CONSTRUTOR
		// teste 1 --> 
		// construtor passando null
		try 
		{
			System.out.println ("TESTE 1");
			ControladorDeErros c1 = new ControladorDeErros(null);
			System.out.println ("Deu certo construir um ControladorDeErros null");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// teste 2 --> 
		// construtor passando < 0
		try 
		{
			System.out.println ("TESTE 2");
			ControladorDeErros c2 = new ControladorDeErros(-2);
            System.out.println ("Deu certo construir um ControladorDeErros < 0 --> ERRADO ");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		} 
		
		// teste 3 --> 
		// construtor passando 0
		try 
		{
			System.out.println ("TESTE 3");
			ControladorDeErros c3 = new ControladorDeErros(0);
            System.out.println ("Deu certo construir um ControladorDeErros passando 0 como parametro --> ERRADO");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// teste 4 --> 
		// passando como parâmetro número > 0 
		ControladorDeErros c4 = null;
		try 
		{
			System.out.println ("TESTE 4");
		    c4 = new ControladorDeErros(3);
            System.out.println ("Deu certo construir um ControladorDeErros passando número positivo como parametro --> CERTO");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		ControladorDeErros c5 = null;
		try 
		{
		    c5 = new ControladorDeErros(3);
            System.out.println ("Deu certo construir um ControladorDeErros passando número positivo como parametro --> CERTO");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		ControladorDeErros c6 = null;
		try 
		{
		    c6 = new ControladorDeErros(3);
            System.out.println ("Deu certo construir um ControladorDeErros passando número positivo como parametro --> CERTO");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTES DO REGISTRE UM ERRO
		
		// teste 5 -->
		// registrando um erro no c4 e c6
		try
		{
			System.out.println("TESTE 5.0");
			c4.registreUmErro();
			System.out.println ("Deu certo registrar um erro.");
			System.out.println ("A quantidade de erros cometidos eh: " + c4.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		try
		{
			System.out.println("TESTE 5.1");
			c6.registreUmErro();
			System.out.println ("Deu certo registrar um erro.");
			System.out.println ("A quantidade de erros cometidos eh: " + c6.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// teste 6 -->
		// registrando 3 erros no c5
		try
		{
			System.out.println ("TESTE 6");
			c5.registreUmErro();
			System.out.println ("Deu certo registrar um erro.");
			System.out.println ("A quantidade de erros cometidos eh: " + c5.toString());
		} 
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// + um erro
		       
		try
		{
			c5.registreUmErro();
			System.out.println ("Deu certo registrar um erro.");
			System.out.println ("A quantidade de erros cometidos eh: " + c5.toString());
		} 
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// + um erro
		// deve aparecer a mensagem "A quantidade máxima de erros já foi atingida!"
		 
		try
		{
			c5.registreUmErro();
			System.out.println ("Deu certo registrar um erro.");
			System.out.println ("A quantidade de erros cometidos eh: " + c5.toString());
		} 
		catch (Exception erro)
		{
			System.err.println (erro.getMessage()); 
		}
		
		// TESTES IS ATINGIDO MAXIMO DE ERROS
		// teste 7 --> 
		// checando se c4 atingiu máximo de erros --> DEVE DAR FALSE
		System.out.println ("TESTE 7"); 
		System.out.println ("Eh " + c4.isAtingidoMaximoDeErros() + " que c4 atingiu o maximo de erros.");
		System.out.println ("O numero de erros de c4 eh: " + c4.toString());
		
		// teste 8 --> 
		// checando se c5 atingiu máximo de erros --> DEVE DAR TRUE
		System.out.println ("TESTE 8"); 
		System.out.println ("Eh " + c5.isAtingidoMaximoDeErros() + " que c5 atingiu o maximo de erros.");
		System.out.println ("O numero de erros de c5 eh: " + c5.toString());
		
		// TESTES DO EQUALS
		// teste 9 --> 
		// checando se c4 é equals de null --> DEVE DAR FALSE
		System.out.println ("TESTE 9"); 
		System.out.println ("Eh " + c4.equals(null) + " que c4 eh equals de null.");
		
		// teste 10 --> 
		// checando se c4 é equals dele mesmo --> DEVE DAR TRUE
		System.out.println ("TESTE 10"); 
		System.out.println ("Eh " + c4.equals(c4) + " que c4 eh equals de c4.");
		
		// teste 11 -->
		// checando se c4 é equals de c5 --> DEVE DAR FALSE
		System.out.println ("TESTE 11"); 
		System.out.println ("Eh " + c4.equals(c5) + " que c4 eh equals de c5.");
		
		// teste 12 -->
		// checando se c4 é equals de c6 --> DEVE DAR TRUE
		System.out.println ("TESTE 12");
		System.out.println ("Eh " + c4.equals(c6) + " que c4 eh equals de c6.");
		
		// teste 13 --> 
		// hashCode com resultado esperado == FALSE
		System.out.println ("TESTE 13");
        System.out.println ("Eh "+(c4.hashCode()==c5.hashCode())+" que o hashCode de c4 eh igual ao hashCode de c5");
        
		// teste 14 --> 
		// hashCode com resultado esperado == TRUE
		System.out.println ("TESTE 14");
		System.out.println ("Eh "+(c4.hashCode()==c4.hashCode())+" que o hashCode de c4 eh igual ao hashCode de c4");
		
		// teste 15 --> 
		// hashCode com resultado esperado == TRUE
		System.out.println ("TESTE 15");
		System.out.println ("Eh "+(c4.hashCode()==c6.hashCode())+" que o hashCode de c4 eh igual ao hashCode de c6");
		
		// TESTES CONSTRUTOR DE CÓPIA
		
		ControladorDeErros copia = null; // objeto que vai receber a cópia
		ControladorDeErros c8 = null;
		
		// teste 16 --> 
		// construtor de cópia recebendo objeto null como parâmetro
		try
		{
			System.out.println ("TESTE 16");
			copia = new ControladorDeErros (c8);
			System.out.println("A copia de c8 foi realizada e c8 eh null!");
			System.out.println ("O objeto c8 esta assim   : " + c8.toString());
			System.out.println ("O objeto copia esta assim: " + copia.toString());		
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// teste 17 --> 
        // construtor de cópia recebendo um objeto instanciado da classe ControladorDeErros como parâmetro
		try 
		{
			System.out.println ("TESTE 17");
			copia = new ControladorDeErros (c4);
			System.out.println("A copia de c4 foi realizada!");	
			System.out.println ("O objeto c4 esta assim   : " + c4.toString());
			System.out.println ("O objeto copia esta assim: " + copia.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE DO CLONE
		System.out.println ("TESTE 18");
		c8 = (ControladorDeErros) c5.clone();
		System.out.println ("O objeto c5 foi clonado!");
		System.out.println ("O objeto c8 esta assim: " + c8.toString());
		System.out.println ("O objeto c5 esta assim: " + c5.toString());
    }
}
