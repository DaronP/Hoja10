/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hojadetrabajo10;

import java.util.Scanner;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;
/**
 *
 * @author ottoalexander
 */

public class Main implements AutoCloseable{
    private final Driver driver;
     public Main( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }
    @Override
    public void close() throws Exception
    {
        driver.close();
    }
    
     public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }
     
        public static void main( String... args ) throws Exception
    {
        try ( Main greeter = new Main( "bolt://localhost:7687", "neo4j", "password" ) )
        {
          Scanner sn = new Scanner(System.in);  
          int respuesta; 
          
            greeter.printGreeting( "1.Agregar a un paciente" );
            greeter.printGreeting( "2.Agregar a un Doctor" );
            greeter.printGreeting( "3..Agregar a toda la consulta" );
            greeter.printGreeting( "4.Información de los doctores" );
            greeter.printGreeting( "5.Unir personas" );
            
        respuesta = sn.nextInt();
           
          switch(respuesta){
               case 1:
                   Scanner scanner = new Scanner(System.in);
                   //Pedimos los datos del paciente
                System.out.print("Ingresa nombre del paciente: ");
                String Pname = scanner.nextLine();
                System.out.print("Ingresa nombre el telefono: ");
                String Pnumber = scanner.nextLine();
                System.out.print("Ingresa la fecha de la visita: ");
                String Pdate = scanner.nextLine();
                CREATE (p:Paciente {nombre:Pname,telefono:Pnumber});
                 -[:VISITS {fecha:Pdate}]];
                   break;
               case 2:
                   System.out.println("Has seleccionado la opcion 2");
                   break;
                case 3:
                   System.out.println("Has seleccionado la opcion 3");
                   break;
                case 4:
                   
                   break;
                default:
                   System.out.println("Solo números entre 1 y 4");
           }   
         
       
        }
    }
}
