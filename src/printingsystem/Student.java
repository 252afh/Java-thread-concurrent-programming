/** ******************************************************************
 * File:	  Student.java (Class)	
 * Author:	  E. Moore	
 * Contents:  6SENG002W CWK  
 * This is the student class responsible for sending documents to the printer
 * Date:      07/11/18
 * Version:	  1.0	 
 ****************************************************************** */

package printingsystem;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student
  implements Runnable
{
  private Document[] docs;
  private final LaserPrinter printer;
  private final ThreadGroup group;
  private final String name;
  private final String id;
  
  public Student(ThreadGroup group, String id, String name, LaserPrinter printer)
  {
    this.group = group;
    this.name = name;
    this.id = id;
    this.printer = printer;
  }
  
  public void setDocs(Document[] docs)
  {
    this.docs = docs;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void run()
  {
    for (Document doc : this.docs)
    {
      while ((this.printer.getPaper() - doc.getNumberOfPages() < 0) || (this.printer.getToner() - doc.getNumberOfPages() < 0)) {
        try
        {
          System.out.println("Printer levels are low, waiting for refill.");
          System.out.println("Document size: " + doc.getNumberOfPages() + ". Toner left: " + this.printer.getToner() + ". Paper left: " + this.printer.getPaper());
          
          int randomSleepDuration = ThreadLocalRandom.current().nextInt(1000, 5000);
          
          Thread.sleep(randomSleepDuration);
        }
        catch (InterruptedException ex)
        {
          Logger.getLogger(LaserPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      synchronized (this.printer)
      {
        this.printer.printDocument(doc);
      }
      try
      {
        int randomSleepDuration = ThreadLocalRandom.current().nextInt(1000, 5000);
        
        Thread.sleep(randomSleepDuration);
      }
      catch (InterruptedException ex)
      {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
