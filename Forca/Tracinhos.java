// Danyelle Nogueira Fran�a 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

public class Tracinhos implements Cloneable
{
    private char texto [];

    public Tracinhos (int qtd) throws Exception
    {
		// verifica se qtd n�o � positiva, lan�ando uma exce��o.
		// instancia this.texto com um vetor com tamanho igual qtd.
		// preenche this.texto com underlines (_).
		
		if (qtd <= 0)
		    throw new Exception ("Quantidade de tracinhos inv�lida!");
		    
		this.texto = new char[qtd];
		
		for (int i = 0; i < qtd; i++)
			this.texto[i] = '_';
    }

    public void revele (int posicao, char letra) throws Exception
    {
		// verifica se posicao � negativa ou ent�o igual ou maior
		// do que this.texto.length, lan�ando uma exce��o.
		// armazena a letra fornecida na posicao tambem fornecida
		// do vetor this.texto
		
		if ((posicao < 0) || (posicao >= this.texto.length))
		    throw new Exception ("Posi��o inv�lida!");
		    
	    this.texto[posicao] = letra;
    }

    public boolean isAindaComTracinhos ()
    {
        // percorre o vetor de char this.texto e verifica
        // se o mesmo ainda contem algum underline ou se ja
        // foram todos substituidos por letras; retornar true
        // caso ainda reste algum underline, ou false caso
        // contrario
        
        for (int i = 0; i < this.texto.length; i++)
        {
			if (this.texto[i] == '_')
				return true;
		}
		
		return false;
    }

    @Override
    public String toString ()
    {
        // retorna um String com TODOS os caracteres que h�
        // no vetor this.texto, intercalados com espa�os em
        // branco
        
        String strTodos = "";
        
        for (int i = 0; i < this.texto.length; i++)
			strTodos += this.texto[i] + " ";
        
        return strTodos;
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte�do, retornando
        // true no caso afirmativo ou false no caso negativo
        
		if (obj==null) return false;
		
		if (this==obj) return true;

        if (obj.getClass() != Tracinhos.class) return false;

        Tracinhos tracinhos = (Tracinhos)obj;
        
        if (this.texto.length != tracinhos.texto.length) return false;

		for (int i = 0; i < this.texto.length; i++)
		{
			if (tracinhos.texto[i] != (this.texto[i]))
				return false;
		}
		
        return true;
    }
    
    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        
        int ret = 5;
        
        ret = 5*ret + new String(this.texto).hashCode();
        
        if (ret < 0) ret = - ret;

        return ret;
    }

    public Tracinhos (Tracinhos t) throws Exception // construtor de c�pia
    {
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copiar o conte�do de t.texto para this.texto
        
        if (t == null)
			throw new Exception ("O objeto passado como par�metro n�o foi instanciado.");
        
        this.texto = new char[t.texto.length];
        
        for (int i = 0; i < t.texto.length; i++)
        {
			this.texto[i] = t.texto[i];
		}
    }

    public Object clone ()
    {
        // retornar uma copia de this
        
        Object copia=null;
        
        try
        {
			copia = new Tracinhos(this);
		}
		catch (Exception erro)
		{} // ignoramos Exception porque sabemos que n�o ocorrer�
		   // j� que passamos this como par�metro do construtor de c�pia
		   // e this � o objeto chamante do m�todo clone, logo, n�o � nulo
        
        return copia;
    }
}
