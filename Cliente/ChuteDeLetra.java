// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados;

// A classe Resultado foi usada como base para fazer essa classe

public class ChuteDeLetra extends Comunicado
{
    private char letra; // Atributo letra do tipo char

    public ChuteDeLetra (char letra)
    {
        // Recebemos um char como parâmetro e armazenamos no atributo letra da classe
        this.letra = letra; 
    }

    // Getter
    public char getLetra ()
    {
        return this.letra;
    }

    // Método toString
    public String toString ()
    {
        return (""+this.letra);
    }
}
