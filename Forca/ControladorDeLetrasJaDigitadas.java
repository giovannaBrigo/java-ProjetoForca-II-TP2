// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.forca;
import java.io.Serializable;

public class ControladorDeLetrasJaDigitadas implements Cloneable, Serializable
{
    private String letrasJaDigitadas;

    public ControladorDeLetrasJaDigitadas ()
    {
        // torna this.letrasJaDigitadas igual ao String vazio

        this.letrasJaDigitadas = "";
    }

    public boolean isJaDigitada (char letra)
    {
        // percorrer o String this.letrasJaDigitadas e verificar se ele
        // possui a letra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo

        for(int i = 0; i < this.letrasJaDigitadas.length(); i++)
        {
            if(letrasJaDigitadas.charAt(i) == letra)
            {
                return true;
            }
        }
        return false;
    }

    public void registre (char letra) throws Exception
    {
        // verifica se a letra fornecida ja foi digitada (pode usar
        // o método this.isJaDigitada, para isso), lancando uma exceção
        // em caso afirmativo.
        // concatena a letra fornecida a this.letrasJaDigitadas.

        if (this.isJaDigitada(letra))
            throw new Exception ("Letra já digitada!");

        this.letrasJaDigitadas+=letra;
    }

    @Override
    public String toString ()
    {
        // retorna um String com TODAS as letras presentes em
        // this.letrasJaDigitadas separadas por vírgula (,).

        String strTodas = "";

        if (this.letrasJaDigitadas.length() > 0)
            strTodas = ""+this.letrasJaDigitadas.charAt(0);

        for (int i = 1; i < this.letrasJaDigitadas.length(); i++)
            strTodas += ", " + this.letrasJaDigitadas.charAt(i);

        return strTodas;
    }

    @Override
    public boolean equals (Object obj)
    {
        // verificar se this e obj são iguais

        if (this==obj) return true;

        if (obj==null) return false;

        if (obj.getClass() != ControladorDeLetrasJaDigitadas.class) return false;

        ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = (ControladorDeLetrasJaDigitadas)obj;

        if (!controladorDeLetrasJaDigitadas.letrasJaDigitadas.equals(this.letrasJaDigitadas))
            return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        // calcular e retornar o hashcode de this

        int ret = 1;

        ret = 7 * ret + new String(this.letrasJaDigitadas).hashCode();

        if(ret<0) ret = -ret; // se for negativo, transformamos em positivo

        return ret;
    }

    public ControladorDeLetrasJaDigitadas(
            ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)
            throws Exception // construtor de cópia
    {
        // copiar c.letrasJaDigitadas em this.

        if(controladorDeLetrasJaDigitadas == null)
            throw new Exception("Modelo Ausente");

        this.letrasJaDigitadas = controladorDeLetrasJaDigitadas.letrasJaDigitadas;
    }

    public Object clone ()
    {
        // criar uma cópia do this com o construtor de cópia e retornar

        Object copia=null;

        try
        {
            copia = new ControladorDeLetrasJaDigitadas(this);
        }
        catch (Exception erro) // ignorando exception porque sabemos que não vai ocorrer, já que passamos this como parâmetro do construtor
        // de cópia e this é o objeto chamante do método clone
        {}

        return copia;
    }
}
