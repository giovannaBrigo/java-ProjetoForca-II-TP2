// Danyelle Nogueira França 21232
// Julia Flausino da Silva  21241
// Giovanna do Amaral Brigo 21685

class TesteControladorDeLetrasJaDigitadas
{
    public static void main (String[] args)
    {	
        // teste 1 --> construtor

        ControladorDeLetrasJaDigitadas cl = new ControladorDeLetrasJaDigitadas();

        System.out.println ("TESTE 1");
        System.out.println("Cl esta assim: "+cl.toString());

        // teste 2 --> registre letra digitada não repetida
        try 
        {
			System.out.println ("TESTE 2");

			cl.registre('g');

			System.out.println("Cl esta assim: "+cl.toString());

        }
        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }

        //teste 3 --> registre letra digitada repetida

        try 
        {
			System.out.println ("TESTE 3");

			cl.registre('g');

			System.out.println("Cl esta assim: "+cl.toString());

        }

        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }

        // teste 4 --> isJaDigitada == TRUE

        System.out.println ("TESTE 4");
        System.out.println ("Eh "+cl.isJaDigitada('g')+" que a letra g ja foi digitada.");

        // teste 5 --> isJaDigitada == FALSE

        System.out.println ("TESTE 5");
        System.out.println ("Eh "+cl.isJaDigitada('j')+" que a letra j ja foi digitada.");


        // teste 6 --> toString

        System.out.println ("TESTE 6");

        try 
        {			
			cl.registre('c');

			System.out.println("Cl esta assim: "+cl.toString());

        }

        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }

        try 
        {		
			cl.registre('h');

			System.out.println("Cl esta assim: "+cl.toString());				
        }

        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }

        ControladorDeLetrasJaDigitadas cl3 = new ControladorDeLetrasJaDigitadas();
        ControladorDeLetrasJaDigitadas cl4 = new ControladorDeLetrasJaDigitadas();

        try 
        {		
			cl3.registre('m');

			System.out.println("Cl3 esta assim: "+cl3.toString());				
        }

        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }

        try 
        {		
			cl4.registre('m');

			System.out.println("Cl4 esta assim: "+cl4.toString());				
        }

        catch (Exception erro)
        {
			System.err.println ("A letra fornecida ja foi digitada.");
        }


        // teste 7 --> equals com resultado esperado == TRUE

        System.out.println ("TESTE 7");
        System.out.println ("Eh "+cl3.equals(cl4)+" que cl3 eh equals a cl4.");

        // teste 8 --> equals com resultado esperado == FALSE

        System.out.println ("TESTE 8");
        System.out.println ("Eh "+cl3.equals(cl)+" que cl eh equals a cl3.");

        // teste 9 --> equals com resultado esperado == TRUE

        System.out.println ("TESTE 9");
        System.out.println ("Eh "+cl.equals(cl)+" que cl eh equals dele mesmo.");

        // teste 10 --> equals com resultado esperado == FALSE

        System.out.println ("TESTE 10");
        System.out.println ("Eh "+cl3.equals(null)+" que cl3 eh equals de nulo");

        //teste 11 --> hashCode com resultado esperado == TRUE

        System.out.println ("TESTE 11");
        System.out.println ("Eh "+(cl3.hashCode()==cl4.hashCode())+" que o hashCode de cl3 eh igual ao de cl4");

        //teste 12 --> hashCode com resultado esperado == FALSE

        System.out.println ("TESTE 12");
		System.out.println ("Eh "+(cl4.hashCode()==cl.hashCode())+" que o hashCode de cl4 eh igual ao de cl");

		//teste 13 --> construtor de cópia

	    ControladorDeLetrasJaDigitadas copia = null;

		try 
		{
			System.out.println ("TESTE 13");
			copia = new ControladorDeLetrasJaDigitadas (cl4);
			System.out.println("A copia de cl4 foi realizada!");
            System.out.println ("O objeto cl4 esta assim   : " + cl4.toString());
            System.out.println ("O objeto copia esta assim: " + copia.toString());
		}
		catch (Exception erro)
		{
			System.err.println (erro.getMessage());
		}

		//teste 14 --> clone

		ControladorDeLetrasJaDigitadas cl6 = new ControladorDeLetrasJaDigitadas();

		System.out.println ("TESTE 14");

        cl6 = (ControladorDeLetrasJaDigitadas) cl4.clone();
        System.out.println ("O objeto cl4 foi clonado!");
        System.out.println ("O objeto cl4 esta assim: " + cl4.toString());
        System.out.println ("O objeto cl6 esta assim: " + cl6.toString());

      }
  }      
