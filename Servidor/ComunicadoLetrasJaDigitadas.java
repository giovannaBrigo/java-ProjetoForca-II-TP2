///////////////////////
// CLASSE MODIFICADA //
///////////////////////

// A classe Resultado foi usada como base para fazer essa classe

public class ComunicadoLetrasJaDigitadas extends Comunicado 
{
	private ControladorDeLetrasJaDigitadas letrasJaDigitadas;
	
	public ComunicadoLetrasJaDigitadas 
	(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)
	{
		this.letrasJaDigitadas = 
		new ControladorDeLetrasJaDigitadas (controladorDeLetrasJaDigitadas);
	}
	
	// n acho que precise desse método
	public ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas ()
    {
        return this.letrasJaDigitadas;
    }
	
	private String toString ()
	{
		return ("" + this.letrasJaDigitadas);
	}
}
