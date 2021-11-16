// Danyelle Nogueira Fran�a 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

public class ControladorDeErros implements Cloneable
{
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
		// verifica se qtdMax fornecida n�o � positiva, lan�ando
		// uma exce��o.
		// armazena qtdMax fornecida em this.qtdMax.
		
		if (qtdMax <= 0)
			throw new Exception ("Quantidade inv�lida!");
		
		this.qtdMax = qtdMax;
    }

    public void registreUmErro () throws Exception
    {
        // verifica se this.qtdErr ja � igual a this.qtdMax,
        // lan�ando excecao em caso positivo ou
        // incrementando this.qtdErr em caso negativo
        
        if (this.qtdErr == this.qtdMax)
			throw new Exception ("A quantidade m�xima de erros j� foi atingida!");
		
		this.qtdErr++;
    }

    public boolean isAtingidoMaximoDeErros  ()
    {
        // retorna true se this.qtdErr for igual a this.qtdMax,
        // ou ent�o false, caso contrario.
        
        if (this.qtdErr == this.qtdMax)
			return true;
		
		return false;
    }

    public String toString ()
    {
        return this.qtdErr + "/" + this.qtdMax;
    }

	@Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conte�do, retornando
        // true no caso afirmativo ou false no caso negativo
        
        if (obj == null) return false;
			
		if (this == obj) return true;
		
		if (obj.getClass() != ControladorDeErros.class) return false;
        
        // revela��o, pois temos certeza de que obj � um ControladorDeErros
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
        
		int ret = 5;
		
        ret = 5 * ret + Integer.valueOf(this.qtdMax).hashCode();
        ret = 5 * ret + Integer.valueOf(this.qtdErr).hashCode();

        if(ret<0) ret = -ret; // se for negativo, transformamos em positivo

        return ret;
    }

    public ControladorDeErros (ControladorDeErros c) throws Exception // construtor de c�pia
    {
        // copiar c.qtdMax e c.qtdErr, respectivamente em, this.qtdMax e this.qtdErr
        
        if (c==null)
			throw new Exception ("O objeto passado como par�metro � nulo!");
			
		this.qtdMax = c.qtdMax;
		this.qtdErr = c.qtdErr;        
    }

    public Object clone ()
    {
        // retornar uma c�pia de this
        
        ControladorDeErros c=null;
        
        try
        {
			c = new ControladorDeErros(this);
		}
        catch (Exception erro)
        {} // ignorando exception porque sabemos que n�o vai ocorrer, j� que passamos 
		   // this como par�metro do construtor de c�pia e this � o objeto chamante do 
		   // m�todo clone, logo, n�o � nulo
        
        return c;
    }
}
