// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685
// Maria Julia Hofstetter Trevisan Pereira 21250

package br.unicamp.cotuca.tp2mat2021.projetofinal.forca;
import java.io.Serializable;

public class Palavra implements Comparable<Palavra>, Serializable
{
    private String texto;

    public Palavra (String texto) throws Exception
    {
        // verifica se o texto recebido é nulo ou então vazio,
        // ou seja, sem nenhum caractere, lançando exceção.
        // armazena o texto recebido em this.texto.

        if (texto == null || texto == "")
            throw new Exception ("Palavra inválida!");

        this.texto = texto;
    }

    public int getQuantidade (char letra)
    {
        // percorre o String this.texto, conta e retorna
        // quantas letras existem nele iguais a letra fornecida

        int qtdLetras = 0;

        for (int i = 0; i < this.texto.length(); i++)
        {
            if (this.texto.charAt(i) == letra)
                qtdLetras++;
        }

        return qtdLetras;
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        // se i==0, retorna a posicao em que ocorre a primeira
        // aparicao de letra fornecida em this.texto;
        // se i==1, retorna a posicao em que ocorre a segunda
        // aparicao de letra fornecida em this.texto;
        // se i==2, retorna a posicao em que ocorre a terceira
        // aparicao de letra fornecida em this.texto;
        // e assim por diante.
        // lançar excecao caso nao encontre em this.texto
        // a Iézima aparição da letra fornecida.

        int aparicao = -1; // guarda as vezes que a letra apareceu
        int posicao  = -1; // guarda a posicao do caractere em this.texto

        for (int c = 0; c < this.texto.length(); c++)
        {
            if (this.texto.charAt(c) == letra)
            {
                aparicao = aparicao + 1;
                if (aparicao == i)
                    posicao = c;
            }
        }

        if (posicao == -1) // se não houver alterações, a letra não existe na palavra
            throw new Exception ("A letra '" + letra + "' não ocorre na palavra!");

        return posicao;
    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    public String toString ()
    {
        return this.texto;
    }

    public boolean equals (Object obj)
    {
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo

        if (obj == null) return false;

        if (this == obj) return true;

        if (obj.getClass() != Palavra.class) return false;

        Palavra p = (Palavra) obj; // revelação, pois temos certeza de que é uma Palavra

        if (!p.texto.equals(this.texto))
            return false;

        return true;
    }

    public int hashCode ()
    {
        // calcular e retornar o hashcode de this
        int ret = 5;

        ret = 5 * ret + this.texto.hashCode();

        if(ret<0) ret = -ret; // se for negativo, transformamos em positivo

        return ret;
    }

    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }
}

