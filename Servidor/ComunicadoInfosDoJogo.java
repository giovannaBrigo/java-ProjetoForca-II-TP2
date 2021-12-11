// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados;

import br.unicamp.cotuca.tp2mat2021.projetofinal.forca.ControladorDeLetrasJaDigitadas;
import br.unicamp.cotuca.tp2mat2021.projetofinal.forca.Tracinhos;

// A classe Resultado foi usada como base para fazer essa classe

public class ComunicadoInfosDoJogo extends Comunicado
{
    private ControladorDeLetrasJaDigitadas letrasJaDigitadas; // atributo letras ja digitadas
    private Tracinhos                      tracinhos;         // atributo tracinhos

    public ComunicadoInfosDoJogo (ControladorDeLetrasJaDigitadas c, Tracinhos t) throws Exception
    {
        // Verificamos se o controlador de letras já digitadas, passado como parâmetro, é nulo
        if (c == null)
            // Se for, lançamos uma exceção
            throw new Exception("Controlador de letras já digitadas ausente!");

        // Verificamos se o tracinhos, passado como parâmetro, é nulo
        if (t == null)
            // Se for, lançamos uma exceção
            throw new Exception("Tracinhos ausente!");

        // this.letrasJaDigitadas recebe o controlador de letras já digitadas passado como parâmetro
        this.letrasJaDigitadas = c;
        // this.tracinhos recebe o tracinhos passado como parâmetro
        this.tracinhos         = t;
    }

    // Getters

    public Tracinhos getTracinhos ()
    {
        return this.tracinhos;
    }

    public ControladorDeLetrasJaDigitadas getLetrasJaDigitadas ()
    {
        return this.letrasJaDigitadas;
    }

    // Construtor de cópia
    public ComunicadoInfosDoJogo (ComunicadoInfosDoJogo c) throws Exception
    {
        if (c == null)
            throw new Exception("Controlador de letras já digitadas ausente!");

        this.letrasJaDigitadas = (ControladorDeLetrasJaDigitadas) c.letrasJaDigitadas.clone();
        this.tracinhos         = (Tracinhos) c.tracinhos.clone();
    }
}
