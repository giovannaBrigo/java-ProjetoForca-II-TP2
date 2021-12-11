// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados;

public class ComunicadoAtualizacoes extends Comunicado
{
    private String[] nomesDosJogadores;        // vetor nomeDosJogadores
    private String[] acoes;                    // vetor acoes
    private String[] resultados;               // vetor resultados
    private String   jogador, acao, resultado; // strings que armazenam o jogador,
                                               // a ação realizada por ele e
                                               // o resultado dessa ação

    public ComunicadoAtualizacoes ()
    {
        // Instanciamos um vetor de strings com as ações possíveis
        String acoes[] = {"chutou uma letra", "chutou uma palavra", "saiu do jogo"};
        this.acoes = acoes; // this.acoes recebe o vetor de acoes

        // Instanciamos um vetor de strings com os resultados possíveis
        String resultados[] = {"errou", "acertou"};
        this.resultados = resultados; // this.resultados recebe o vetor de resultados

        // Instanciamos um vetor de strings com os nomes dos jogadores
        String nomesDosJogadores[] = {"jogador 1", "jogador 2", "jogador 3"};
        this.nomesDosJogadores = nomesDosJogadores; // this.nomesDosJogadores recebe o vetor de nomes
    }

    // Getters

    public String getJogador()
    {
        return this.jogador;
    }

    public String getAcao()
    {
        return this.acao;
    }

    public String getResultado()
    {
        return this.resultado;
    }

    // Setters

    public void setJogador (int jogador) throws Exception
    {
        if (jogador < 0 || jogador > 2)
            throw new Exception("Jogador inexistente!");

        this.jogador = nomesDosJogadores[jogador];
    }

    public void setAcao (int acao) throws Exception
    {
        if (acao < 0 || acao > 2)
            throw new Exception("Acao inexistente!");

        this.acao = acoes[acao];
    }

    public void setResultado (int resultado) throws Exception
    {
        if (resultado < 0 || resultado > 2)
            throw new Exception("Resultado inexistente!");

        this.resultado = resultados[resultado];
    }

    // Construtor de cópia
    public ComunicadoAtualizacoes (ComunicadoAtualizacoes c) throws Exception
    {
        if (c == null)
            throw new Exception("Controlador de letras já digitadas ausente!");

        this.nomesDosJogadores = c.nomesDosJogadores;
        this.acoes             = c.acoes;
        this.resultados        = c.resultados;
        this.jogador           = c.jogador;
        this.acao              = c.acao;
        this.resultado         = c.resultado;
    }
}
