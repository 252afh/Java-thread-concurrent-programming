/** ******************************************************************
 * File:	  LaserPrinter.java (Class)	
 * Author:	  E. Moore	
 * Contents:  6SENG002W CWK  
 * This class handles keeping track of the information relevant to the printer, and printing the documents 
 * Date:      07/11/18
 * Version:	  1.0	 
 ****************************************************************** */

package printingsystem;

public class LaserPrinter
  implements ServicePrinter
{
  private final String printerId;
  private int paper;
  private int toner;
  private int totalPrinted = 0;
  
  public LaserPrinter(String Id, int paper, int toner)
  {
    this.printerId = Id;
    this.paper = paper;
    this.toner = toner;
  }
  
  public int getPaper()
  {
    return this.paper;
  }
  
  public int getToner()
  {
    return this.toner;
  }
  
  public void printDocument(Document document)
  {
    this.paper -= document.getNumberOfPages();
    this.toner -= document.getNumberOfPages();
    this.totalPrinted += document.getNumberOfPages();
    System.out.println("A document has been printed.");
    System.out.println(document.toString());
  }
  
  public String toString()
  {
    return "LaserPrinter " + this.printerId + ": Paper = " + this.paper + ". Toner = " + this.toner + ". Total pages printed = " + this.totalPrinted;
  }
  
  public void replaceTonerCartridge()
  {
    int minimumReplaceableToner = 10;
    int minimumToner = 0;
    int currentToner = this.toner;
    if ((currentToner <= minimumReplaceableToner) && (currentToner >= minimumToner))
    {
      this.toner = 500;
      System.out.println("Toner has been filled to " + this.toner);
    }
  }
  
  public void refillPaper()
  {
    int currentPaper = this.paper;
    if (currentPaper + 50 <= 250)
    {
      this.paper += 50;
      System.out.println("Paper has been filled to " + this.paper);
    }
  }
}
