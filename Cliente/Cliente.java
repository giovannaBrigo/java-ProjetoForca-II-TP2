// Danyelle Nogueira França 21232
// Julia Flausino da Silva 21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.cliente;

import br.unicamp.cotuca.tp2mat2021.projetofinal.Parceiro;
import br.unicamp.cotuca.tp2mat2021.projetofinal.Teclado;
import br.unicamp.cotuca.tp2mat2021.projetofinal.forca.*;
import br.unicamp.cotuca.tp2mat2021.projetofinal.comunicados.*;

import java.net.*;
import java.io.*;

public class Cliente
{
    public static final String HOST_PADRAO  = "localhost";
    public static final int    PORTA_PADRAO = 3000;

    public static void main (String[] args)
    {
        if (args.length>2)
        {
            System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao=null;
        try
        {
            String host = Cliente.HOST_PADRAO;
            int    porta= Cliente.PORTA_PADRAO;

            if (args.length>0)
                host = args[0];

            if (args.length==2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket (host, porta);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor = null;
        try
        {
            transmissor = new ObjectOutputStream(conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor = new ObjectInputStream(conexao.getInputStream());
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor=null;
        try
        {
            servidor = new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try
        {
            tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento (servidor);
        }
        catch (Exception erro) {} // sei que servidor foi instanciado

        tratadoraDeComunicadoDeDesligamento.start();

        System.out.println("Esperando outros jogadores se conectarem...\n");

        Palavra palavra       = null; // Variável da classe Palavra que receberá a palavra do jogo
        Comunicado comunicado = null; // Variável da classe Comunicado que receberá os comunicados durante o jogo
        try
        {
            // INICIO DO JOGO

            // Recebemos o comunicado de inicio do jogo
            do
            {
                comunicado = (Comunicado) servidor.espie();
            }
            while (!(comunicado instanceof ComunicadoInicioDoJogo));
            ComunicadoInicioDoJogo comunicadoInicioDoJogo = (ComunicadoInicioDoJogo) servidor.envie();

            // Armazenamos a palavra sorteada na variável
            palavra = comunicadoInicioDoJogo.getPalavra();

            // Avisamos aos cliente que o jogo começou
            System.out.println("O jogo começou!");
            System.out.println("Aguarde sua vez para jogar...\n");
        }
        catch (Exception erro)
        {
            System.err.println("Erro de comunicacao com o servidor;");
            System.err.println("Tente novamente!");
            System.err.println("Caso o erro persista, termine o programa");
            System.err.println("e volte a tentar mais tarde!\n");
        }

        // Variáveis que guardarão os dados do jogo
        Tracinhos tracinhos = null;
        ControladorDeLetrasJaDigitadas letrasJaDigitadas = null;

        // Váriavel que recebera a opção do usuário
        char opcao = ' ';
        for(;;) // esse é um forever
        {
            try
            {
                // A váriavel da classe comunicado recebe null
                comunicado = null;
                // Espera até receber um comunicado do servidor
                do
                {
                    comunicado = servidor.espie();
                }
                while (!(comunicado instanceof ComunicadoSuaVez)        &&
                       !(comunicado instanceof ComunicadoInfosDoJogo)   &&
                       !(comunicado instanceof ComunicadoAtualizacoes)  &&
                       !(comunicado instanceof ComunicadoDeDerrota)     &&
                       !(comunicado instanceof ComunicadoDeVitoria));

                // Se recebermos um comunicado de infos do jogo
                if (comunicado instanceof ComunicadoInfosDoJogo)
                {
                    ComunicadoInfosDoJogo infos = (ComunicadoInfosDoJogo) servidor.envie();

                    // Pegamos os dados do jogo (letras já digitadas e tracinhos) que foram enviados do servidor
                    letrasJaDigitadas = infos.getLetrasJaDigitadas();
                    tracinhos = infos.getTracinhos();

                    // Printamos os dados
                    System.out.println("DADOS DO JOGO");
                    System.out.println("Palavra:             " + tracinhos);
                    System.out.println("Letras já digitadas: " + letrasJaDigitadas + "\n");
                }
                // Se recebermos um comunicado de atualizações
                else if (comunicado instanceof ComunicadoAtualizacoes)
                {
                    ComunicadoAtualizacoes atualizacoes = (ComunicadoAtualizacoes) servidor.envie();

                    // Pegamos as informações passadas pelo servidor
                    String jogadorDaVez = atualizacoes.getJogador(); // O jogador da vez
                    String acao         = atualizacoes.getAcao();    // Qual ação ele realizou
                    String resultado    = null;

                    // Se o jogador chutou uma letra ou uma palavra, a sua ação tem um resultado (errou ou acertou)
                    // e caso ele não tenha perdido/saido, continuamos pegando os resultados de suas ações
                    if (acao != "saiu do jogo")
                        resultado = atualizacoes.getResultado();

                    // Se o resultado é igual a nulo, significa que o jogador da vez saiu do jogo
                    if (resultado == null)
                    {
                        // Avisamos isso ao cliente
                        System.out.println("O " + jogadorDaVez + " saiu do jogo!\n");
                    }
                    else // Se não, quer dizer que ele chutou uma letra ou uma palavra
                    {
                        // Printamos quem é o jogador da vez, qual foi sua ação e o resultado dessa ação para o cliente
                        System.out.println("O " + jogadorDaVez + " " + acao + " e " + resultado + "!\n");
                    }
                }
                // Se recebermos um comunicado de sua vez
                else if (comunicado instanceof ComunicadoSuaVez)
                {
                    comunicado = servidor.envie();

                    // Avisamos ao cliente que é a vez dele de jogar e perguntamos o que ele deseja fazer
                    System.out.println("Sua vez de jogar!\n");
                    System.out.println("OPÇÕES");
                    System.out.println("Para tentar adivinhar uma letra, digite \"L\"");
                    System.out.println("Para tentar adivinhar a palavra, digite \"P\"");
                    System.out.println("Para sair do jogo, digite \"S\"\n");
                    System.out.print  ("O que deseja fazer? ");

                    do
                    {
                        try
                        {
                            opcao = Character.toUpperCase(Teclado.getUmChar()); // transformamos o char da opção em maiusculo
                        }
                        catch (Exception erro) {}

                        // caso o usuario tenha digitado algo diferente das opções disponíveis
                        if ("LPS".indexOf(opcao)==-1)
                        {
                            // Avisamos isso a ele e deixamos ele digitar novamente
                            System.out.print("\nOpção inválida! Tente novamente: ");
                        }
                    }
                    while ("LPS".indexOf(opcao)==-1); // Enquanto a opção digitada for inválida

                    if (opcao == 'L') // caso a opção seja "L" de letra
                    {
                        // Perguntamos qual a letra que ele deseja chutar
                        System.out.print("Qual letra? ");

                        // Váriavel que recebera a opção do usuário
                        char letra = ' ';
                        do
                        {
                            try
                            {
                                letra = Character.toUpperCase(Teclado.getUmChar());
                            }
                            catch (Exception erro)
                            {}

                            // Se a letra escolhida pelo cliente já foi digitada antes
                            if (letrasJaDigitadas.toString().indexOf(letra)!=-1)
                            {
                                // Avisamos isso a ele e deixamos ele digitar outra letra
                                System.out.println("\nEssa letra já foi digitada!");
                                System.out.print  ("Tente novamente: ");
                            }
                            else if (letra == ' ') // Se recebermos uma cadeia vazia ao invés de uma letra
                            {
                                // Avisamos isso ao cliente e deixamos ele digitar novamente
                                System.out.println("\nLetra ausente.");
                                System.out.print  ("Tente novamente: ");
                            }
                        }
                        while (letrasJaDigitadas.toString().indexOf(letra)!=-1 ||
                                letra == ' '); // Enquanto a letra já tiver sido digitada ou for igual a cadeia vazia

                        // Enviamos um comunicado de chute de letra para o servidor, passando como parâmetro a letra digitada
                        servidor.receba(new ChuteDeLetra(letra));

                        // Esperamos a resposta do servidor (um comunicado de erro, de acerto ou de vitória)
                        do
                        {
                            comunicado = servidor.espie();
                        }
                        while (!(comunicado instanceof ComunicadoDeErro)    &&
                                !(comunicado instanceof ComunicadoDeAcerto) &&
                                !(comunicado instanceof ComunicadoDeVitoria));

                        // Se a letra não existe na palavra
                        if (comunicado instanceof ComunicadoDeErro)
                        {
                            comunicado = servidor.envie();

                            // Avisamos isso ao cliente e a vez dele acaba aqui
                            System.out.println("Que pena... A letra não existe na palavra!\n");
                            System.out.println("Aguarde sua vez para jogar...\n");
                        }
                        // Se a letra existe na palavra
                        else if (comunicado instanceof ComunicadoDeAcerto)
                        {
                            comunicado = servidor.envie();

                            // Avisamos isso ao cliente e a vez dele acaba aqui
                            System.out.println("Parabéns, você adivinhou uma letra!\n");
                            System.out.println("Aguarde sua vez para jogar...\n");
                        }
                        else // Se o cliente ganhou o jogo (completou a palavra)
                        {
                            comunicado = servidor.envie();

                            // Avisamos isso a ele e o jogo acaba aqui
                            System.out.println("Parabéns! Você ganhou o jogo! A palavra era mesmo " + palavra + "\n");
                            break;
                        }
                    }
                    else if (opcao == 'P') // caso a opção seja "P" de palavra
                    {
                        // Perguntamos qual a palavra que o cliente deseja chutar
                        System.out.print("Qual palavra? ");

                        // Variável String para armazenar a palavra digitada
                        String possivelPalavra = "";
                        do
                        {
                            try
                            {
                                // Pegamos a palavra digitada, deixamos ela com letras maiusculas e armazenamos na variável
                                possivelPalavra = Teclado.getUmString().toUpperCase();


                                // Se recebermos uma cadeia vazia invés de uma palavra
                                if (possivelPalavra  == " " || possivelPalavra  == "")
                                {
                                    // Avisamos isso ao cliente e deixamos ele tentar novamente
                                    System.out.println("\nPalavra ausente!");
                                    System.out.print  ("Tente novamente: ");
                                }
                                else if (possivelPalavra.length() == 1) // Se o cliente digitou uma letra
                                {
                                    // Avisamos isso a ele e deixamos ele tentar novamente
                                    System.out.println("\nVocê digitou uma letra!");
                                    System.out.print  ("Tente novamente: ");
                                }
                            }
                            catch (Exception erro) {}
                        }
                        // Enquanto o cliente não digitar uma palavra
                        while (possivelPalavra == " " || possivelPalavra == "" || possivelPalavra.length() == 1);

                        // Enviamos um comunicado de chute de palavra para o servidor, contendo a palavra digitada
                        servidor.receba(new ChuteDePalavra(possivelPalavra));

                        // Esperamos a resposta do servidor (um comunicado de derrota ou de vitória)
                        do
                        {
                            comunicado = servidor.espie();
                        }
                        while (!(comunicado instanceof ComunicadoDeDerrota) && !(comunicado instanceof ComunicadoDeVitoria));

                        // Se o cliente errou a palavra
                        if (comunicado instanceof ComunicadoDeDerrota)
                        {
                            comunicado = servidor.envie();

                            // Avisamos isso a ele e o jogo dele acaba aqui
                            System.out.println("Que pena, você perdeu o jogo!");
                            System.out.println("Na verdade, a palavra era " + palavra);
                            break;
                        }
                        // já se o cliente acertou a palavra
                        else
                        {
                            comunicado = servidor.envie();

                            // o comunicamos e o jogo se encerra aqui
                            System.out.println("Parabéns! Você ganhou o jogo!");
                            System.out.println("A palavra era mesmo " + palavra);
                            break;
                        }
                    }
                    else // caso a opção seja "S" de sair
                    {
                        // Enviamos um pedido de saida ao servidor
                        servidor.receba(new PedidoParaSair());
                        break; // Encerramos o jogo
                    }
                }
                // Se o comunicado for de derrota
                else if (comunicado instanceof ComunicadoDeDerrota)
                {
                    comunicado = servidor.envie();

                    // Avisamos ao cliente que ele perdeu o jogo e mostramos qual era a palavra para ele
                    System.out.println("Que pena, você perdeu o jogo!");
                    System.out.println("A palavra era " + palavra);
                    break; // encerramos o jogo
                }
                // Se o comunicado for de vitória
                else
                {
                    comunicado = servidor.envie();

                    // comunicamos que ele venceu, mostrando a palavra do jogo e o jogo se encerra aqui
                    System.out.println("Você ganhou o jogo!");
                    System.out.println("A palavra era " + palavra);
                    break;
                }
            }
            catch (Exception erro)
            {
                System.err.println("\nErro de comunicacao com o servidor;");
                System.err.println("Tente novamente!");
                System.err.println("Caso o erro persista, termine o programa");
                System.err.println("e volte a tentar mais tarde!\n");
            }
        }

        System.out.println ("\nObrigado por usar este programa!");
        System.exit(0);
    }
}