// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

public class ControladorDeErros implements Cloneable
{
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
		// verifica se qtdMax fornecida não é positiva, lançando
		// uma exceção.
		// armazena qtdMax fornecida em this.qtdMax.
		
		if (qtdMax <= 0)
			throw new Exception ("Quantidade invalida!");
		
		this.qtdMax = qtdMax;
    }

    public void registreUmErro () throws Exception
    {
        // verifica se this.qtdErr ja é igual a this.qtdMax,
        // lançando excecao em caso positivo ou
        // incrementando this.qtdErr em caso negativo
        
        if (this.qtdErr == this.qtdMax)
			throw new Exception ("A quantidade maxima de erros já foi atingida!");
		
		this.qtdErr++;
    }

    public boolean isAtingidoMaximoDeErros  ()
    {
        // returna true se this.qtdErr for igual a this.qtdMax,
        // ou então false, caso contrario.
        
        if (this.qtdErr == this.qtdMax)
			return true;
		
		return false;
    }

    @Override
    public String toString ()
    {
        return this.qtdErr + "/" + this.qtdMax;
    }

	@Override
    public boolean equals (Object obj) 
    {
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo
        
        if (obj == null) return false;
			
		if (this == obj) return true;
		
		if (obj.getClass() != ControladorDeErros.class) return false;
        
        ControladorDeErros c = (ControladorDeErros) obj; 
        
        if (c.qtdMax != this.qtdMax)
			return false;
			
		if (c.qtdErr != this.qtdErr)
			return false;
			
		return true;
    }

	@Override
    public int hashCode () 
    {
        // calcular e retornar o hashcode de this
        
		int ret = super.hashCode();
		
        ret = 5 * ret + Integer.valueOf(this.qtdMax).hashCode(); // ele pega um tipo primitivo int ou uma string e retorna um objeto do tipo Integer.
        ret = 5 * ret + Integer.valueOf(this.qtdErr).hashCode();

        if(ret<0) ret = -ret; // se for negativo, transformamos em positivo

        return ret;
    }

    public ControladorDeErros (ControladorDeErros c) throws Exception // construtor de cópia
    {
        // copiar c.qtdMax e c.qtdErr, respectivamente em, this.qtdMax e this.qtdErr
        
        if (c==null)
			throw new Exception ("O objeto passado como parametro eh nulo!");
			
		this.qtdMax = c.qtdMax;
		this.qtdErr = c.qtdErr;        
    }

    public Object clone ()
    {
        // retornar uma cópia de this
        
        ControladorDeErros c=null;
        
        try
        {
			c = new ControladorDeErros(this);
		}
        catch (Exception erro)
        {}
        
        return c;
    }
}
