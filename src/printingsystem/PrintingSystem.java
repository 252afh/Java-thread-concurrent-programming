/** ******************************************************************
 * File:	  PrintingSystem.java (Class)	
 * Author:	  E. Moore	
 * Contents:  6SENG002W CWK  
 *		      This is the main class that handles initialising and ending the threads. 
 * Date:      07/11/18
 * Version:	  1.0	 
 ****************************************************************** */

package printingsystem;

import java.util.ArrayList;
import java.util.List;

public class PrintingSystem
{
  private static Thread studentThread1;
  private static Thread studentThread2;
  private static Thread studentThread3;
  private static Thread studentThread4;
  private static Student student1;
  private static Student student2;
  private static Student student3;
  private static Student student4;
  private static Thread paperTech;
  private static Thread tonerTech;
  private static LaserPrinter laserPrinter;
  private static ThreadGroup studentGroup;
  private static ThreadGroup technicianGroup;
  private static final List<Thread> STUDENTLIST = new ArrayList();
  private static final List<Thread> TECHNICIANLIST = new ArrayList();
  private static boolean isTechnicianAlive = true;
  
  public static boolean isTechnicianAlive()
  {
    return isTechnicianAlive;
  }
  
  public static void main(String[] args)
    throws InterruptedException
  {
    instantiateVariables();
    populateStudentDocs();
    startThreads();
    handleThreadEnding();
  }
  
  private static void instantiateVariables()
  {
    laserPrinter = new LaserPrinter("Printer", 250, 500) {};
    technicianGroup = new ThreadGroup("technicianGroup");
    technicianGroup.setDaemon(true);
    
    studentGroup = new ThreadGroup("studentGroup");
    studentGroup.setDaemon(true);
    
    paperTech = new Thread(technicianGroup, new PaperTechnician(technicianGroup, "paperTech", laserPrinter), "PaperTechThread");
    tonerTech = new Thread(technicianGroup, new TonerTechnician(technicianGroup, "tonerTech", laserPrinter), "TonerTechThread");
    
    System.out.println("Technician threads have been successfully created");
    
    student1 = new Student(studentGroup, "S1", "Arnold", laserPrinter);
    student2 = new Student(studentGroup, "S2", "Premal", laserPrinter);
    student3 = new Student(studentGroup, "S3", "Elliot", laserPrinter);
    student4 = new Student(studentGroup, "S4", "Roy", laserPrinter);
    
    studentThread1 = new Thread(studentGroup, student1, "StudentThread1");
    studentThread2 = new Thread(studentGroup, student2, "StudentThread2");
    studentThread3 = new Thread(studentGroup, student3, "StudentThread3");
    studentThread4 = new Thread(studentGroup, student4, "StudentThread4");
    
    System.out.println("Student threads have been successfully created");
  }
  
  private static void startThreads()
  {
    paperTech.start();
    tonerTech.start();
    
    System.out.println("Technician threads started");
    
    studentThread1.start();
    studentThread2.start();
    studentThread3.start();
    studentThread4.start();
    
    System.out.println("Student threads started");
  }
  
  private static void populateStudentDocs()
  {
    Document[] student1Docs = { new Document(student1.getId(), "cwk", 15), new Document(student1.getId(), "PID", 9), new Document(student1.getId(), "Lecture notes", 20), new Document(student1.getId(), "cwk specification", 6), new Document(student1.getId(), "essay", 14) };
    
    Document[] student2Docs = { new Document(student2.getId(), "cwk", 17), new Document(student2.getId(), "PID", 12), new Document(student2.getId(), "Lecture notes", 25), new Document(student2.getId(), "cwk specification", 8), new Document(student2.getId(), "essay", 5) };
    
    Document[] student3Docs = { new Document(student3.getId(), "cwk", 12), new Document(student3.getId(), "PID", 7), new Document(student3.getId(), "Lecture notes", 15), new Document(student3.getId(), "cwk specification", 12), new Document(student3.getId(), "essay", 20) };
    
    Document[] student4Docs = { new Document(student4.getId(), "cwk", 20), new Document(student4.getId(), "PID", 20), new Document(student4.getId(), "Lecture notes", 20), new Document(student4.getId(), "cwk specification", 20), new Document(student4.getId(), "essay", 20) };
    
    student1.setDocs(student1Docs);
    student2.setDocs(student2Docs);
    student3.setDocs(student3Docs);
    student4.setDocs(student4Docs);
    
    System.out.println("Documents have been added to each user");
  }
  
  private static void handleThreadEnding()
    throws InterruptedException
  {
    STUDENTLIST.add(studentThread1);
    STUDENTLIST.add(studentThread2);
    STUDENTLIST.add(studentThread3);
    STUDENTLIST.add(studentThread4);
    for (Thread student : STUDENTLIST) {
      student.join();
    }
    System.out.println("All student threads have finished");
    
    isTechnicianAlive = false;
    
    TECHNICIANLIST.add(paperTech);
    TECHNICIANLIST.add(tonerTech);
    for (Thread tech : TECHNICIANLIST) {
      tech.join();
    }
    System.out.println("All technician threads have finished");
    
    System.out.println(laserPrinter.toString());
  }
}
