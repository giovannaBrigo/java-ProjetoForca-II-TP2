// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.servidor;

import br.unicamp.cotuca.tp2mat2021.projetofinal.Parceiro;
import br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados.*;
import br.unicamp.cotuca.tp2mat2021.projetofinal.forca.*;

import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private ArrayList<Parceiro>            usuarios;
    private ArrayList<Parceiro>            grupo;
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;
    private Tracinhos                      tracinhos;
    private Palavra                        palavra;

    public SupervisoraDeConexao (ArrayList<Parceiro> usuarios, ArrayList<Parceiro> grupo) throws Exception
    {
        // verificamos se os parâmetros passados são nulos
        if (usuarios == null)
            throw new Exception("Usuarios ausente");

        if (grupo == null)
            throw new Exception("Grupo ausente");

        // this.usuarios recebe usuarios passado como parâmetro
        synchronized (usuarios)
        {
            this.usuarios = usuarios;
        }

        // instanciamos this.grupo com new
        this.grupo = new ArrayList<Parceiro>();
        // passamos todos os elementos do grupo passado como parâmetro para this.grupo
        this.grupo.addAll(grupo);
    }

    public void run ()
    {
        // sorteamos uma palavra da classe BancoDePalavras
        this.palavra = BancoDePalavras.getPalavraSorteada();

        // instanciamos um Tracinhos passando o tamanho da palavra sorteada como parâmetro
        try
        {
            this.tracinhos = new Tracinhos(palavra.getTamanho());
        }
        catch (Exception erro) {}

        // instanciamos um controlador de letras já digitadas
        this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();

        // percorremos o ArrayList this.grupo
        for (int i = 0; i < this.grupo.size(); i++)
        {
            try
            {
                // enviamos comunicados que avisam que o jogo começou para todos os jogadores
                this.grupo.get(i).receba(new ComunicadoInicioDoJogo(this.palavra));
            }
            catch (Exception erro) // caso haja algum erro de transmissão
            {
                synchronized (this.usuarios)
                {
                    // removemos o jogador do array de usuarios
                    this.usuarios.remove(this.grupo.get(i));
                }

                try
                {
                    // encerramos a conexão com ele
                    this.grupo.get(i).adeus();
                }
                catch (Exception erroDeDesconexao) {}

                synchronized (this.grupo)
                {
                    // removemos o jogador do array do grupo
                    this.grupo.remove(this.grupo.get(i));
                }

                // se i é diferente de 2, nós subtraimos 1 de i
                // isso para que todos os jogadores recebam o comunicado,
                // já que o array list foi rearranjado quando um jogador foi excluido
                if (i != 2) i--;

                continue; // voltamos para o for
            }
        }

        try
        {
            // Declaramos e instanciamos um ComunicadoInfosDoJogo, passando os dados do jogo como parâmetro
            ComunicadoInfosDoJogo infosDoJogo =
            new ComunicadoInfosDoJogo(this.controladorDeLetrasJaDigitadas,this.tracinhos);

            // Instanciamos um ComunicadoAtualizacoes
            ComunicadoAtualizacoes atualizacoes = new ComunicadoAtualizacoes();

            // variável que guardará qual a posição do jogador que foi excluido do vetor grupo
            int posicaoDeExclusao = -1;

            // variável que guardará qual a posição do jogador da vez no vetor grupo
            int jogadorAtual = 0;

            // variável que guardará qual a posição, no vetor de ações, da ação realizada pelo jogador da vez
            int acao = -1;

            // variável que guardará qual a posição do resultado, no vetor de resultados, da ação realizada pelo jogador da vez
            int resultado = -1;

            for (;;)
            {
                // definimos o jogador da vez, pegando o parceiro na posição do jogadorAtual no grupo
                Parceiro jogadorDaVez = grupo.get(jogadorAtual);

                // setamos o jogador da vez no comunicado de atualizações, passando o jogadorAtual como parâmetro
                atualizacoes.setJogador(jogadorAtual);

                // o jogador da vez recebe um comunicado com as informações do jogo
                try
                {
                    jogadorDaVez.receba(new ComunicadoInfosDoJogo(infosDoJogo));
                }
                catch (Exception erro) {} // erro de comunicação

                // Jogador da vez recebe um ComunicadoSuaVez
                jogadorDaVez.receba(new ComunicadoSuaVez());

                // Aguardamos um comunicado vindo do jogador da vez
                Comunicado comunicado = jogadorDaVez.envie();

                if (comunicado == null)
                    return;
                // Se o jogador da vez chutar uma letra
                else if (comunicado instanceof ChuteDeLetra)
                {
                    // A ação recebe 0 (chute de letra)
                    acao = 0;

                    // O comunicado vindo do cliente é um comunicado de ChuteDeLetra
                    ChuteDeLetra chuteDeLetra = (ChuteDeLetra) comunicado;

                    // a variável recebe essa letra que foi chutada
                    char letra = chuteDeLetra.getLetra();

                    // Registramos a letra no controlador de letras já digitadas
                    // (já verificamos que a letra não foi digitada antes no cliente)
                    this.controladorDeLetrasJaDigitadas.registre(letra);

                    // Vemos quantas vezes a letra digitadas aparece na palavra
                    int qtd = this.palavra.getQuantidade(letra);

                    // caso não exista na palavra
                    if (qtd == 0)
                    {
                        // O jogador da vez recebe um comunicado de erro
                        jogadorDaVez.receba(new ComunicadoDeErro());
                        // O resultado da ação do jogador é igual a 0, ou seja, errou
                        resultado = 0;
                    }

                    else
                    {
                        // O resultado da ação é igual a 1, ou seja, acertou
                        resultado = 1;

                        // Revelamos a letra em tracinhos
                        for (int c = 0; c < qtd; c++)
                        {
                            int posicao = this.palavra.getPosicaoDaIezimaOcorrencia(c, letra);
                                this.tracinhos.revele(posicao, letra);
                        }

                        // Se tracinhos ainda está com tracinhos
                        if (this.tracinhos.isAindaComTracinhos())
                            // O jogador da vez recebe um comunicado de acerto
                            jogadorDaVez.receba(new ComunicadoDeAcerto());
                        else // Se a palavra foi completada
                        {
                            // Para cada jogador no grupo
                            for (Parceiro jogador : this.grupo)
                            {
                                // Se o jogador for o jogador da vez
                                if (jogador == jogadorDaVez)
                                {
                                    // Recebe um comunicado de vitória
                                    jogador.receba(new ComunicadoDeVitoria());
                                }
                                else // Se não (todos os outros jogadores do grupo)
                                {
                                    // Setamos a ação e o resultado da ação do jogador
                                    atualizacoes.setAcao(acao);
                                    atualizacoes.setResultado(resultado);
                                    // Enviamos uma cópia do comunicado de atualizações
                                    jogador.receba(new ComunicadoAtualizacoes(atualizacoes));

                                    // O jogador recebe um comunicado de derrota
                                    jogador.receba(new ComunicadoDeDerrota());
                                }

                                // Removemos o jogador de usuarios (independentemente de ter ganhado ou perdido)
                                synchronized (this.usuarios)
                                {
                                    this.usuarios.remove(jogador);
                                }

                                // Fechamos a conexão com o jogador (independentemente de ter ganhado ou perdido)
                                try
                                {
                                    jogador.adeus();
                                }
                                catch (Exception erroDeDesconexao) {}
                            }

                            break; // Saimos do for infinito, terminando a execução da supervisora
                        }
                    }
                }
                // Se o jogador da vez chutar uma palavra
                else if (comunicado instanceof ChuteDePalavra)
                {
                    // A ação recebe 1 (chute de palavra)
                    acao = 1;

                    // O comunicado vindo do cliente é um comunicado de ChuteDePalavra
                    ChuteDePalavra chuteDePalavra = (ChuteDePalavra) comunicado;

                    // uma string recebe a palavra digitada pelo cliente
                    String palavraDigitada = chuteDePalavra.getPalavra();

                    // Se a palavra digitada pelo cliente for igual a palavra sorteada
                    if (palavraDigitada.equals(this.palavra.toString()))
                    {
                        // O jogador acertou a palavra

                        // O resultado da ação do jogador é igual a 1, ou seja, acertou
                        resultado = 1;

                        // Para cada parceiro do grupo
                        for (Parceiro jogador : this.grupo)
                        {
                            // Se o parceiro é o jogador da vez
                            if (jogador == jogadorDaVez)
                            {
                                // Recebe um comunicado de vitória
                                jogador.receba(new ComunicadoDeVitoria());
                            }
                            else // Se não (qualquer outro jogador do grupo)
                            {
                                // Setamos a ação e o resultado da ação do jogador da vez
                                atualizacoes.setAcao(acao);
                                atualizacoes.setResultado(resultado);
                                // Enviamos uma cópia do comunicado de atualizações
                                jogador.receba(new ComunicadoAtualizacoes(atualizacoes));

                                // O jogador recebe um comunicado de derrota
                                jogador.receba(new ComunicadoDeDerrota());
                            }

                            // Removemos o jogador dos usuarios (independentemente de ter ganhado ou perdido)
                            synchronized (this.usuarios)
                            {

                                this.usuarios.remove(jogador);
                            }

                            // Fechamos a conexão com ele (independentemente de ter ganhado ou perdido)
                            try
                            {
                                jogador.adeus();
                            }
                            catch (Exception erroDeDesconexao) {}
                        }

                        break; // Saimos do for infinito, terminando a execução da supervisora
                    }
                    else // caso o jogador tenha errado o chute de palavra
                    {
                        // O resultado da sua ação recebe 0, ou seja, errou
                        resultado = 0;

                        // Se o grupo tiver apenas dois jogadores
                        if (grupo.size() == 2)
                        {
                            // Para cada parceiro presente no grupo
                            for (Parceiro jogador : this.grupo)
                            {
                                // Se o parceiro for o jogador da vez
                                if (jogador == jogadorDaVez)
                                {
                                    // Recebe um comunicado de derrota
                                    jogador.receba(new ComunicadoDeDerrota());
                                }
                                else // Se não (é o outro jogador do grupo)
                                {
                                    // Setamos a ação e o resultado dessa ação do jogador da vez
                                    atualizacoes.setAcao(acao);
                                    atualizacoes.setResultado(resultado);
                                    // Enviamos uma cópia do comunicado de atualizações para o jogador
                                    jogador.receba(new ComunicadoAtualizacoes(atualizacoes));

                                    // Enviamos um comunicado de vitória para o jogador
                                    jogador.receba(new ComunicadoDeVitoria());
                                }

                                // Removemos o jogador de usuários, indepentemente de ter ganhado ou perdido
                                synchronized (this.usuarios)
                                {
                                    this.usuarios.remove(jogador);
                                }

                                // Fechamos a conexão com ele, indepentemente dele ter ganhado ou perdido
                                try
                                {
                                    jogador.adeus();
                                }
                                catch (Exception erroDeDesconexao)
                                {}
                            }

                            break; // Saimos do for infinito, terminando a execução da supervisora
                        }
                        else // Se o grupo estiver completo com os três jogadores
                        {
                            // O jogador da vez perde, assim recebendo um comunicado de derrota
                            // e os outros dois jogadores continuam o jogo
                            jogadorDaVez.receba(new ComunicadoDeDerrota());

                            synchronized (this.usuarios)
                            {
                                // removemos o jogador da vez dos usuários
                                this.usuarios.remove(jogadorDaVez);
                            }

                            try
                            {
                                // e fechamos a conexão com ele
                                jogadorDaVez.adeus();
                            }
                            catch (Exception erroDeDesconexao) {}

                            // o removemos também do grupo
                            this.grupo.remove(jogadorDaVez);

                            // A posição de exclusão recebe o indice do jogador atual
                            posicaoDeExclusao = jogadorAtual;
                        }
                    }
                }
                // Se o jogador pediu para sair
                else if (comunicado instanceof PedidoParaSair)
                {
                    // A ação recebe 2 (saiu do jogo)
                    acao = 2;

                    synchronized (this.usuarios)
                    {
                        // removemos o jodador da vez dos usuários
                        this.usuarios.remove(jogadorDaVez);
                    }

                    // o removemos também do grupo
                    this.grupo.remove(jogadorDaVez);

                    try
                    {
                        // e fechamos a conexão com o mesmo
                        jogadorDaVez.adeus();
                    }
                    catch (Exception erroDeDesconexao) {}

                    // caso o tamanho do grupo seja == 1, ou seja, só resta um jogador
                    if (this.grupo.size() == 1)
                    {
                        // Setamos a ação do jogador da vez, e o resultado dessa ação
                        atualizacoes.setAcao(acao);
                        atualizacoes.setResultado(resultado);
                        // Enviamos uma cópia do comunicado de atualizações para o jogador que sobrou
                        this.grupo.get(0).receba(new ComunicadoAtualizacoes(atualizacoes));

                        // Esse jogador recebe o comunicado de vitória
                        this.grupo.get(0).receba(new ComunicadoDeVitoria());

                        synchronized (this.usuarios)
                        {
                            // é retirado do grupo
                            this.usuarios.remove(this.grupo.get(0));
                        }

                        try
                        {
                            // e a conexão com ele é fechada
                            this.grupo.get(0).adeus();
                        }
                        catch (Exception erroDeDesconexao) {}

                        // finalizamos o jogo
                        break;
                    }

                    // Se o jogo não foi finalizado, a posição de exclusão recebe a posição do jogador atual,
                    // indicando que ele foi excluido do grupo
                    posicaoDeExclusao = jogadorAtual;
                }

                // Mandamos um comunicado de atualizações do jogo para o cliente

                atualizacoes.setJogador(jogadorAtual);    // indicamos qual é o jogador da vez
                atualizacoes.setAcao(acao);               // indicamos o que ele fez nessa rodada
                if (resultado != -1)                      // se ele chutou uma palavra/letra
                {
                    atualizacoes.setResultado(resultado); // indicamos qual foi o resultado (errou/acertou)
                    resultado = -1;
                }

                // Mandamos uma cópia do comunicado de atualizacoes para todos os parceiros,
                // menos o jogador atual
                for (Parceiro jogador : this.grupo)
                {
                    if (jogador != jogadorDaVez)
                    {
                        jogador.receba(new ComunicadoAtualizacoes(atualizacoes));
                    }
                }

                // Aqui definimos o próximo jogador

                // Se a posição de exclusão for != 1, quer dizer que um jogador foi excluido do grupo
                // e o array list foi rearranjado
                if (posicaoDeExclusao != -1)
                {
                    // Por isso, o próximo jogador, que estava à direita do jogador que foi excluido
                    // no array list grupo ([],[],[]) passa a ocupar o lugar desse jogador
                    // (todos os jogadores que estavam à direita, vão uma "casinha" para a esquerda)
                    // então, o jogador atual está no indice da posição de exclusão
                    jogadorAtual = posicaoDeExclusao;
                }
                else // nenhum jogador foi excluido
                    jogadorAtual++; // passamos para o próximo jogador

                // se o jogador atual ultrapassou o tamanho do grupo, ele volta a ser o primeiro jogador
                if (jogadorAtual >= grupo.size()) jogadorAtual = 0;
            }
        }
        catch (Exception erro)
        {
            // percorremos o array retirando todos os usuários
            for (int i = 0; i < this.grupo.size(); i++)
            {
                synchronized (this.usuarios)
                {
                    this.usuarios.remove(this.grupo.get(i));
                }
            }

            // e fechando as conexões com todos eles
            try
            {
                for (int i = 0; i < this.grupo.size(); i++)
                {
                    this.grupo.get(i).adeus();
                }
            }
            catch (Exception erroDeDesconexao) {}

            return;
        }
    }
}