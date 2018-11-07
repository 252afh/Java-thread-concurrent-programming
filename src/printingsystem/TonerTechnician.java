/** ******************************************************************
 * File:	  PrintingSystem.java (Class)	
 * Author:	  E. Moore	
 * Contents:  6SENG002W CWK  
 * This class handling all relevant information to the technician replacing toner in the printer
 * Date:      07/11/18
 * Version:	  1.0	 
 ****************************************************************** */

package printingsystem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TonerTechnician
  implements Runnable
{
  private final String name;
  private final LaserPrinter printer;
  private final ThreadGroup group;
  
  public TonerTechnician(ThreadGroup group, String name, LaserPrinter printer)
  {
    this.name = name;
    this.printer = printer;
    this.group = group;
  }
  
  public void run()
  {
    try
    {
      int fiveSeconds = 5000;
      while (PrintingSystem.isTechnicianAlive())
      {
        int attempts = 0;
        int maxAttempts = 3;
        
        System.out.println("checking toner");
        while (attempts < maxAttempts)
        {
          synchronized (this.printer)
          {
            this.printer.replaceTonerCartridge();
          }
          attempts++;
        }
        Thread.sleep(fiveSeconds);
      }
    }
    catch (InterruptedException ex)
    {
      Logger.getLogger(TonerTechnician.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
