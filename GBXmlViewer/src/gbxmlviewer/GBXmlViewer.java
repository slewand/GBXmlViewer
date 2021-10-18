package gbxmlviewer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gbxmlviewer.gui.MainFrame;

/**
 * G��wna klasa aplikacji
 */
public class GBXmlViewer
{

 public static void main(String[] args)
 {
  // Ustawienie sk�rki
  try
  {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }

  MainFrame mainFrame = new MainFrame();
  
  SwingUtilities.invokeLater(new Runnable()
  {
   public void run()
   {    
    mainFrame.setVisible(true);
   }
  });

 }
}
