package gbxmlviewer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import gbxmlviewer.io.XMLReader;
import gbxmlviewer.model.Model;
import gbxmlviewer.res.ResourceManager;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
 private JToolBar toolBar;
 private JButton openFileButton;
 private JFileChooser fileChooser;
 
 public MainFrame()
 {
  super("GBXml Viewer");
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(800, 600);
  ResourceManager resourceManager = ResourceManager.getInstance();
  
  fileChooser = new JFileChooser();
  fileChooser.setFileFilter(new FileFilter()
  {   
   @Override
   public String getDescription()
   {
    return "gbXML files (*.xml; *.gbxml)";
   }
   
   @Override
   public boolean accept(File f)
   {
    return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml") || f.getName().toLowerCase().endsWith(".gbxml");
   }
  });
  
  openFileButton = new JButton(resourceManager.getIcon("openFile_24"));
  openFileButton.setFocusable(false);
  toolBar = new JToolBar();
  toolBar.setRollover(true);
  toolBar.setFloatable(false);
  toolBar.add(openFileButton);
  add(toolBar, BorderLayout.NORTH);
  
  openFileButton.addActionListener(new ActionListener()
  {   
   @Override
   public void actionPerformed(ActionEvent e)
   {
    if(fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION)
    {
     XMLReader xmlReader = new XMLReader();
     xmlReader.readModel(fileChooser.getSelectedFile());
     Model model = xmlReader.getModel();
     System.out.println(model.getBounds3D().toString());
    }    
   }
  });
 }
}
