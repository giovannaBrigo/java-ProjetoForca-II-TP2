///////////////////////
// CLASSE MODIFICADA //
///////////////////////

// A classe Resultado foi usada como base para fazer essa classe

public class ChuteDePalavra extends Comunicado
{
    private Palavra palavra;

    public ChuteDePalavra (String palavra)
    {
        this.palavra = new Palavra (palavra);
    }

    public Palavra getPalavra ()
    {
        return this.palavra;
    }
    
    public String toString ()
    {
		return (""+this.palavra);
	}
}
