/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hojadetrabajo10;

import java.util.Scanner;
import org.neo4j.graphdb.RelationType;
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
    
    public enum RationType implements RelationType{
        Knows, BelongsTo;
    }
    
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
          Scanner scanner = new Scanner(System.in);
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
                      //Pedimos los datos del doctor
                    System.out.print("Ingresa nombre del doctor: ");
                    String Dname = scanner.nextLine();
                    System.out.print("Ingresa la especialidad del doctor: ");
                    String Dspecial = scanner.nextLine();
                    System.out.print("Ingresa el numero de telefono: ");
                    String Dnumber = scanner.nextLine();
                    
                    CREATE (d:Doctor {nombre:Dname, especialidad:Dspecial, telefono:Dnumber});
     
                   break;
                case 3:
                   System.out.println("Has seleccionado la opcion 3");
                   //Pedimos los datos del paciente
                    System.out.print("Ingresa nombre del paciente: ");
                     Pname = scanner.nextLine();
                    System.out.print("Ingresa nombre el telefono: ");
                     Pnumber = scanner.nextLine();
                    System.out.print("Ingresa la fecha de la visita: ");
                     Pdate = scanner.nextLine();
                      //Pedimos los datos del doctor
                    System.out.print("Ingresa nombre del doctor: ");
                     Dname = scanner.nextLine();
                    System.out.print("Ingresa la especialidad del doctor: ");
                     Dspecial = scanner.nextLine();
                    System.out.print("Ingresa el numero de telefono: ");
                     Dnumber = scanner.nextLine();
                    // Pedimos la prescripcion
                    System.out.print("Ingresa nombre del medicamento: ");
                    String Mname = scanner.nextLine();
                    System.out.print("Ingrese desde cuando debe consumir el medicamento: ");
                    String Minicio = scanner.nextLine();
                    System.out.print("Ingrese hasta cuando debe consumir el medicamento: ");
                    String Mfin = scanner.nextLine();
                    System.out.print("Ingrese dosis: ");
                    String Mdosis = scanner.nextLine();
                    
                    CREATE (p:Paciente {nombre:Pname, telefono:Pnumer]);
                    -[:VISITS {fecha: pDate}]->;
                    (d:Doctor {nombre:Dname, especialidad:Dspecial, telefono:Dnumber});
                    -[:PRESCRIBE]->;
                    (m:Medicina{nombre:Mname,desdeFecha:Minicio,hastaFecha:Mfin,dosis:Mdosis});
                    <-[:TAKES]-(p);
                }
                    
                    
                    
                   
                   break;
                case 4:
                   System.out.println("Ingrese la especialidad que desea buscar");
                   String especial = scanner.nextLine();
                   System.out.println(Doctor.findNodes(DinamicLabel.label(especial)));
                   
                   break;
                case 5:
                    System.out.println("Ingrese el nombre de la primer persona ");
                    String per1 = scanner.nextLine();
                    System.out.println("Ingrese el nombre de la segunda persona");
                    String per2 = scanner.nextLine();
                    Paciente.findNodes(DinamicLabel.label(per1)).createRelationshipTo(Paciente.findNodes(DinamicLabel.label(per2)), RelationshipType.Knows);
                    
                    break;
                default:
                   System.out.println("Solo números entre 1 y 4");
           }   
         
       
        }
    }
}
