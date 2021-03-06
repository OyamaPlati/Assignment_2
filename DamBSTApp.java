// DamBSTApp class
//
// ******************PUBLIC OPERATIONS*********************
// Comparable find( x ) --> Return item that matches x
// void printOpCount( x ) --> print results of counts from insert
// 						  --> & search method
// void printDam( x ) --> Find x in BST if found print; 
// 					  --> else print dam not found 
// void printAllDams( ) --> print all dam data to stdout

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
/**
 * This class is implementation for the user interface. 
 * This class scans data from the dam database and 
 * stores it in a binary search tree to perform some operations. 
 * @author Oyama Plati
 * 
 */
public class DamBSTApp {
   
   private static BinarySearchTree <DamData> bst = new BinarySearchTree <DamData> ();
   private static Scanner inputStream = null;
   private static int searchCount = 0;
   
   public static void main (String[] args) {
      
      try {
		  inputStream = new Scanner(
		      new FileInputStream(
		      (new File("Dam_Levels_Individual_Nov2015-Mar2016-1.csv")).getAbsolutePath()));
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
         System.out.println("File does not exist");
         System.out.println(" or could not be open.");
      }
      
      String line = null;
      
      inputStream.nextLine(); 
      
      while (inputStream.hasNextLine()) {
         line = inputStream.nextLine();
         
         String[] rawdata = line.split(",");
         
         if (rawdata.length >= 26) {
            bst.insert(
               new DamData(rawdata[2], rawdata[10], rawdata[25]));
         }
         else if ((rawdata.length >= 11) 
            && (rawdata.length < 26)) {
            bst.insert(
               new DamData(rawdata[2], rawdata[10], null));
         }
         else if (rawdata.length < 11){
            bst.insert(new DamData(rawdata[2], null, null));
         }    
      }
      
      if (args.length != 0) {
         StringBuilder builder = new StringBuilder();
         for (String s: args) {
            if (builder.length() > 0) {
               builder.append(" ");
            }
            builder.append(s);
         }
         printDam(builder.toString().trim());
      }
      else {
         printAllDams();  
      }
      
      inputStream.close();  
   } // END OF MAIN
   
   /**
    * This subroutine prints all dam data
    */
   public static void printAllDams () {
      bst.postOrder();
      printOpCount(searchCount, BinarySearchTree.insertCount);
   } // END OF PRINTALLDAMS
   
   /**
    * This subroutine finds x in BST if found print else print dam not found
    * @param damName Name of dam to be printed
    */
   public static void printDam (String damName) {
      BinaryNode <DamData> result = find (damName);
      if (result == null) {
    	  printOpCount(searchCount, BinarySearchTree.insertCount);
         System.out.println("Dam not found");
      }
      else {
         printOpCount(searchCount, BinarySearchTree.insertCount);
         System.out.println(result.element); 
      }  
   } // END OF PRINTDAM
   
   /**
    * This subroutine returns the item that matches name
    * @param name
    * @return item that matches name
    */
   private static BinaryNode<DamData> find (String name) {
      return find (bst.root, name);   
   } // END OF FIND
   
   private static BinaryNode<DamData> find 
               (BinaryNode<DamData> t, String name) {
      BinaryNode<DamData> result = null;
      if (t != null) { 
         searchCount++;
         if (name.equals(t.element.getDamName().trim())) { 
            return t;
         }
         else {
            result = find (t.left, name); 
            if (result == null) {
               result = find (t.right, name);
            }
            return result;
         }
      }
      else {
         return null;
      }
   } // END OF FIND
  
   /**
    * This subroutine print all dam data
    * @param searchcount Search method count
    * @param insertcount Insert method count
    */
  private static void printOpCount (int searchcount, int insertcount) {
      PrintWriter outputStreamSearch = null;
      PrintWriter outputStreamInsert = null;
      
      try {
         outputStreamSearch = new PrintWriter (
                              new FileWriter("OpBSTSearchReport.dat", true));
         outputStreamInsert = new PrintWriter(
	 	 	      new FileWriter("OpBSTInsertReport.dat", true));
      }
      catch (FileNotFoundException e0) {
         e0.printStackTrace();
         System.out.println("File not found \n or file does not exist.");
         System.exit(0);
      }
      catch (IOException e1) {
         e1.printStackTrace();
         System.exit(0);
      }
      
      outputStreamSearch.println(Integer.toString(searchcount));
      outputStreamInsert.println(Integer.toString(insertcount));
      
      outputStreamSearch.close();
      outputStreamInsert.close();
   }// END OF printOpCounter   
} // END OF CLASS DAMBSTAPP
