// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.forca;

public class BancoDePalavras
{
    private static String[] palavras =
            {
                    "FELIZMENTE",        "PROSPERIDADE",
                    "ESTRESSE",          "VENTILADOR",
                    "BORBOLETA",         "OPORTUNIDADE",
                    "ALEATORIAMENTE",    "INTENSAMENTE",
                    "ANSIOSAMENTE",      "RECIPROCIDADE",
                    "EMPODERAMENTO",     "TEMPERAMENTAL",
                    "PERSONALIDADE",     "ABSURDAMENTE",
                    "LINDAMENTE",        "MENSAGEM",
                    "PROGRAMADORAS",     "GELADEIRA",
                    "BANHEIRO",          "MONITORIA",
                    "DESENVOLVIMENTO",   "PIZZAIOLO",
                    "AMPULHETA",         "TURISMO",
                    "DESPACHANTE",       "DESFIBRILADOR",
                    "MODERNIDADE",       "REPORTAGEM",
                    "APAIXONANTE"                           };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
                    new Palavra (BancoDePalavras.palavras[
                            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e) {}

        return palavra;
    }
}

