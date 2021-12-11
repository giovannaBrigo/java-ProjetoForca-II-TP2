// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados;

// A classe Resultado foi usada como base para fazer essa classe

public class ChuteDePalavra extends Comunicado
{
    private String palavra; // Atributo palavra da classe String

    public ChuteDePalavra (String palavra)
    {
        // Recebemos uma String como parâmetro e armazenamos no atributo palavra da classe
        this.palavra = palavra;
    }

    // Getter
    public String getPalavra ()
    {
        return this.palavra;
    }
}
