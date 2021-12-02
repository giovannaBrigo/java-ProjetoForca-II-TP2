///////////////////////
// CLASSE MODIFICADA //
///////////////////////

// A classe Resultado foi usada como base para fazer essa classe

public class ComunicadoTracinhos extends Comunicado
{
	private Tracinhos tracinhos;
	
	public ComunicadoTracinhos (Tracinhos tracinhos)
	{
		this.tracinhos = new Tracinhos (tracinhos);
	}
	
	// n acho que precise desse método
	public Tracinhos getTracinhos ()
    {
        return this.tracinhos;
    }
	
	private String toString ()
	{
		return ("" + this.tracinhos);
	}
}
