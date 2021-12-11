// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados;

import br.unicamp.cotuca.tp2mat2021.projetofinal.forca.Palavra;

public class ComunicadoInicioDoJogo extends Comunicado
{
    private Palavra palavra; // atributo palavra da classe Palavra

    public ComunicadoInicioDoJogo (Palavra p) throws Exception
    {
        // Verificamos se a Palavra passada como parâmetro é nula
        if (p == null)
            // Se for, lançamos uma exceção
            throw new Exception("Palavra ausente!");

        // this.palavra recebe a Palavra passada como parâmetro
        this.palavra = p;
    }

    // Getter

    public Palavra getPalavra ()
    {
        return this.palavra;
    }
}
