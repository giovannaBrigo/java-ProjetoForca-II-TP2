// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

public class TesteTracinhos
{
    public static void main (String[] args)
    {        
        System.out.println ("TESTES DA CLASSE TRACINHOS");

        // TESTES CONSTRUTOR        		
		// TESTE 1 -> DEVE LANÇAR EXCEÇÃO
		try 
        {
			System.out.println ("TESTE 1");
			Tracinhos t1 = new Tracinhos (-1);
			System.out.println ("Deu certo construir tracinhos com numero de posicoes negativa!");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
	    // TESTE 2 -> DEVE LANÇAR EXCEÇÃO
		try 
        {
			System.out.println ("TESTE 2");
			Tracinhos t2 = new Tracinhos (0);
			System.out.println ("Deu certo construir tracinhos com qtd = 0!");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 3
		Tracinhos t3 = null;
		try 
        {
			System.out.println ("TESTE 3");
			t3 = new Tracinhos (5);
			System.out.println ("Deu certo construir t3 com qtd = 5!");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTES REVELE E TO STRING
		// TESTE 4 -> REVELE DEVE LANÇAR EXCEÇÃO
		try
		{
			System.out.println ("TESTE 4");
			t3.revele(-1,'C');
			System.out.println ("Deu certo revelar o caractere 'C' na posicao  -1;");
			System.out.println ("A string de t3 esta desse jeito: " + t3.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 5 -> REVELE DEVE LANÇAR EXCEÇÃO
		try
		{
			System.out.println ("TESTE 5");
			t3.revele(6,'A');
			System.out.println ("Deu certo revelar o caractere 'A' na posicao 6;");
			System.out.println ("A string de t3 esta desse jeito: " + t3.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 6 -> REVELE DEVE LANÇAR EXCEÇÃO
		try
		{
			System.out.println ("TESTE 6");
			t3.revele(5,'A');
			System.out.println ("Deu certo revelar o caractere 'A' na posicao 5;");
			System.out.println ("A string de tracinhos esta desse jeito: " + t3.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 7
		try
		{
			System.out.println ("TESTE 7");
			t3.revele(2,'I');
			System.out.println ("Deu certo revelar o caractere 'I' na posicao 2;");
			System.out.println ("A string de tracinhos esta desse jeito: " + t3.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTES IS AINDA COM TRACINHOS
		// TESTE 8 -> DEVE DAR TRUE
		System.out.println ("TESTE 8");
		System.out.println ("Eh " + t3.isAindaComTracinhos() + " que t3 ainda esta com tracinhos.");
		
		// PREENCHENDO O RESTO DE t3 COM LETRAS
		try
		{
			System.out.println ("\nPREENCHENDO O RESTO DE t3 COM LETRAS");
			t3.revele(0,'C');
			t3.revele(1,'O');
			t3.revele(3,'S');
			t3.revele(4,'A');
			System.out.println ("O vetor texto de t3 esta assim: "+ t3.toString() +"\n");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 9 -> DEVE DAR FALSE
		System.out.println ("TESTE 9");
		System.out.println ("Eh " + t3.isAindaComTracinhos() + " que t3 ainda esta com tracinhos.");
		System.out.println ("A string de t3 esta desse jeito: " + t3.toString());
		
		// TESTES DO EQUALS
		// INSTANCIANDO E PREENCHENDO T4 - TEXTO = C O I S A
		Tracinhos t4 = null;
		try 
        {
			System.out.println ("\nINSTANCIANDO E PREENCHENDO T4 COM LETRAS");
			t4 = new Tracinhos (5);
			System.out.println ("Deu certo construir tracinhos com qtd = 5!");
			t4.revele(0,'C');
			t4.revele(1,'O');
			t4.revele(2,'I');
			t4.revele(3,'S');
			t4.revele(4,'A');
			System.out.println ("O vetor texto de t4 esta assim: "+ t4.toString() +"\n");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// INSTANCIANDO E PREENCHENDO T5 - TEXTO = T E S T E
		Tracinhos t5 = null;
		try 
        {
			System.out.println ("\nINSTANCIANDO E PREENCHENDO T5");
			t5 = new Tracinhos (5);
			System.out.println ("Deu certo construir tracinhos com qtd = 5!");
			t5.revele(0,'T');
			t5.revele(1,'E');
			t5.revele(2,'S');
			t5.revele(3,'T');
			t5.revele(4,'E');
			System.out.println ("O vetor texto de t5 esta assim: "+ t5.toString() +"\n");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 10 - EQUALS T3 E T4 -> DEVE DAR TRUE
		System.out.println ("TESTE 10");
		System.out.println ("Eh " + t3.equals(t4) + " que t3 eh equals de t4.");
		System.out.println ("O texto de t3 eh: " + t3.toString());
		System.out.println ("O texto de t4 eh: " + t4.toString());
		
		// TESTE 11 - EQUALS T3 E T5 -> DEVE DAR FALSE
		System.out.println ("TESTE 11");
		System.out.println ("Eh " + t3.equals(t5) + " que t3 eh equals de t5.");
		System.out.println ("O texto de t3 eh: " + t3.toString());
		System.out.println ("O texto de t5 eh: " + t5.toString());
		
		// TESTE 12 - EQUALS T3 COM STRING "COISA" -> DEVE DAR FALSE
		System.out.println ("TESTE 12");
		System.out.println ("Eh " + t3.equals("COISA") + " que t3 eh equals de String 'COISA'.");
		
		// TESTE 13 - EQUALS T3 COM NULL -> DEVE DAR FALSE
		System.out.println ("TESTE 13");
		System.out.println ("Eh " + t3.equals(null) + " que t3 eh equals de null.");
		
		// TESTE 14 - EQUALS T3 COM T3 -> DEVE DAR TRUE
		System.out.println ("TESTE 14");
		System.out.println ("Eh " + t3.equals(t3) + " que t3 eh equals dele mesmo.");
		
		// TESTES DE HASHCODE
		// TESTE 15 - HASHCODE DE T3 É EQUALS DE HASHCODE DE T4? -> DEVE DAR TRUE
		System.out.println ("TESTE 15");
		System.out.println ("Eh " + (t3.hashCode()==t4.hashCode()) + " que o hashCode de t3 eh igual ao de t4.");
		
		// TESTE 16 - HASHCODE DE T3 É EQUALS DE HASHCODE DE T5? -> DEVE DAR FALSE
		System.out.println ("TESTE 16");
		System.out.println ("Eh " + (t3.hashCode()==t5.hashCode()) + " que o hashCode de t3 eh igual ao de t5.");
		
		// TESTES DE CONSTRUTOR DE CÓPIA
		// TESTE 17 - DEVE LANÇAR EXCEÇÃO
		Tracinhos t6 = null; // recebe a cópia
		Tracinhos t7 = null;
		try 
        {
			System.out.println("TESTE 17");
			t6 = new Tracinhos (t7);
			System.out.println("A copia de t7 foi realizada e t7 eh null!");
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE 18 - CONSTRUTOR DE CÓPIA QUE TEM COMO PARÂMETRO UM OBJETO 
		//            INSTANCIADO DA CLASSE TRACINHOS
		System.out.println("TESTE 18");
		Tracinhos t8 = null;
		try 
        {
			t8 = new Tracinhos (4);
			System.out.println("Deu certo criar tracinhos com vetor de qtd = 4.");
			t8.revele(0,'J');
			t8.revele(1,'A');
			t8.revele(2,'V');
			t8.revele(3,'A');
			System.out.println("O vetor de t8 esta assim: " + t8.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		try 
        {
			t6 = new Tracinhos (t8);
			System.out.println("A copia (t6) de t8 foi realizada!");
			System.out.println("O vetor de t6 esta assim: " + t6.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}
		
		// TESTE DE CLONE
		// TESTE 19
		System.out.println("TESTE 19");
		Tracinhos t9 = null;
		t9 = (Tracinhos) t3.clone();
		System.out.println("O vetor de t3 esta assim: " + t3.toString());
		System.out.println("O vetor de t9 esta assim: " + t9.toString());
    }
}
