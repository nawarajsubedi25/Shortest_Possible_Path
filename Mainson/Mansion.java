import java.io.*;
import java.util.*;
/**
 * Discription : A simple program that find the seceret passage from one room to another by using
 *               used breadth frist search to solve the problem. This program simple read the 
 *               text file containg specification about the room and assigns this values in matrix 
 *               and solve the problem.
 * @since      : Apr 29, 2015
 * @author     : Nawaraj Subedi              
 */
public class Mansion
{
   // Data Field
   private String name="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   int Matrix[][];
   private int count=1,stop=26;
   boolean[] visited;
   private Queue<Integer> q ;
  /**
   * A constructor for to initialize the double array, queue and to  called a method for to built 
   * a matrix from the textfile which is passed as an argument in this consructor.
   * @param filename A String which contains the data and that is passed as argument in constructor.
   */
   public Mansion( String filename)
   {
      this.Matrix=new int [stop][stop];
      this.q = new LinkedList<Integer>();
      this.visited= new boolean[stop];
      MatrixCreater(filename);
   }
   /**
    * A void method to create a matrix from the file which is passed as an argument.
    * if something error in file it throw an exception else it will built the matrix 
    * and it called anothe method to solve problem. start to solve the proble once 
    * it solve one problem it read another and so on.
    * @param textfile A string which contains the condition for to solve problem
    */
   public void MatrixCreater ( String textfile)
   {
      try
      {
         File infile=new File (textfile);
         Scanner sc=new Scanner(infile);
         
         /*************************************************
           Read how many mansion are there for to solved
          ************************************************/ 
         sc.nextLine();
         
         /***************************************************
          loop execute if more line is available for to read  
         ***************************************************/ 
      
         while(sc.hasNextLine())
         {
          /*************************************************
                  Read Next line from the file 
          ************************************************/
            String line =sc.nextLine();
            Scanner read=new Scanner(line);
            
            /**************************************************
             Condition only for to read starting and ending path
             **************************************************/
            if ( line.length()==2)
            {
               String required1=read.next();
               int a=name.indexOf(required1.charAt(0))+1;
               int b=name.indexOf(required1.charAt(1))+1;
               findShortPath(a,b);
               count++;
            
            }
            /**************************************************
                 For to read the edges between two vertices
             *************************************************/
            while( read.hasNext())
            {
               String required=read.next();
               int a=name.indexOf(required.charAt(0))+1;
               int b=name.indexOf(required.charAt(1))+1;
               Matrix[a][b]=1;
               Matrix[b][a]=1;
            }
         
         }
         
      }              
      catch(Exception e)
      {
      //************************************
      //prints the stack trace to System.err
      //************************************
         System.out.print("Something Wrong in File");
         e.printStackTrace();
      
      }
   }
  /**
   * A void method that find the shortest possible path by breadth first search from
   * the starting vertex to ending vertex that are passed as agrument in this method.
   *@param begin An int representing the starting vertex
   *@param end   An int representing the ending vertex
   */
   public void findShortPath(int begin, int end)
   {
      String[] pathTo = new String[stop];
      q.add(begin);
      pathTo[begin] = begin+" ";
      while((q.peek() != null))
      {
         int first=q.remove();
         visited[first] = true;
         for ( int vertex=0;vertex<stop; vertex++)
         {
            if ( (Matrix[first][vertex]==1)&&(!(visited[vertex])))
            {            
               visited[vertex]=true;
               pathTo[vertex] = pathTo[first] +vertex +" ";
               q.add(vertex);
            }
         }
      }
      print(begin,pathTo[end],end);  
      /******************************************
       TO SET BOOLEAN FALSE FOR ALL VISITED PATHS
       ******************************************/ 
      clearVisited();
      /******************************************
       TO DELETE ALL THE ASSIGNED VALUE IN MATRIX
       ******************************************/ 
      clearMatrix();
   }
   /**
    * A helper void method that helps to initilized the boolean value false to all the 
    *  integer. It is a key method for to solve a multiple mension.  
    */ 
   public  void clearVisited()
   {
      for (int i = 0; i < visited.length; i++)
      {
         visited[i] = false;
      }
   }
   /**
    * A helper void method that initilized double array matrix to zero.This is the
    * key method for to solved multiple method.
    */
   public void clearMatrix()
   {
      for (int row = 0; row <Matrix.length; row++) 
      {
         for (int col = 0; col <Matrix[row].length; col++) 
         {
            Matrix[row][col]=0;
         }
      }
   }
   /**
    * A void method that takes the paths, start and end point as an argument and
    * sacn the paths and convert it into the path that is sutiable for to read.
    * @param start An int representing the start vertex of problem
    * @param paths An String representing paths from start to end
    * @param finish An int representing the final vertex of the problem 
    */
   public void print( int start,String paths ,int finish)
   {
      StringBuilder printpath =new StringBuilder();
      /***********************************************
       If argument string paths contains null it throw
       an exception otherwise it provides solution
       ***********************************************/
      try
      { 
         Scanner reader=new Scanner(paths);
         int value = reader.nextInt();
         printpath.append(name.charAt(value-1));
      
         while ( reader.hasNextInt())
         {
            int value1 = reader.nextInt();
            printpath.append(name.charAt(value1-1));
         
            if (!(value1==finish))
            {
               printpath.append(name.charAt(value1-1));
            }
         }
         /***********************************
           Calculate how many passages
          **********************************/
         int passage=printpath.length()/2;
         
         /***********************************
            To put a dash after one passages
          **********************************/
         int split = printpath.length() - 2;
         while (split > 0)
         {
            printpath.insert(split,"-");
            split-= 2;
         }
         System.out.print("\n*******************************************************"); 
         System.out.print("\n  Problem " +count + ":" + "\n  It is possible to travel from "+
             name.charAt(start-1) + " to " + name.charAt(finish-1)+"."+
            "\n  The shortest path involves " + passage+" passages :"+
            printpath.toString() +'\n');
        
      }
      catch(Exception e)
      {
         System.out.print("\n*******************************************************"); 
         System.out.print("\n  Problem " + count + ":" + "\n  It is not possible to travel from "+
                   name.charAt(start-1) + " to " + name.charAt(finish-1)+"."+'\n');
      }
              
   }
   /**
    * A main method that test the Mansion class.This methods takes input as a command line argument
    * and the  contains the certain condition for to solve problem.
    */
   public static void main(String[] args) 
   {
      try
      {
         File file=new File(args[0]);
         if (file.length()!=0) 
         {
            Mansion abc1=new Mansion(args[0]);
            System.out.print("\n*******************************************************"); 
            System.exit(1);
         }
         /************************************
          If file contains nothing
          ************************************/
         System.out.print("No Condition is found for to solved problem.");
      }
      /********************************************
           If Run Argument is not found
       ********************************************/
      catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Put the file name in Run Arguments.");
      }
      
   }
}