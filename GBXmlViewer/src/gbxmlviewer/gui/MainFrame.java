package gbxmlviewer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
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
 private JToggleButton showSurfacesButton;
 private JToggleButton showSpacesButton;
 private JFileChooser fileChooser;
 
 private View view;
 
 public MainFrame()
 {
  super("GBXml Viewer");
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(800, 600);
  setLocationRelativeTo(null);
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
  showSurfacesButton = new JToggleButton(resourceManager.getIcon("showSurfaces_24"), true);
  showSurfacesButton.setFocusable(false);
  showSpacesButton = new JToggleButton(resourceManager.getIcon("showSpaces_24"), true);
  showSpacesButton.setFocusable(false);
  
  toolBar = new JToolBar();
  toolBar.setRollover(true);
  toolBar.setFloatable(false);
  toolBar.add(openFileButton);
  toolBar.add(showSurfacesButton);
  toolBar.add(showSpacesButton);
  add(toolBar, BorderLayout.NORTH);
  
  view = new View();
  add(view, BorderLayout.CENTER);
  
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
     view.setModel(model);
    }    
   }
  });
  
  showSurfacesButton.addActionListener(new ActionListener()
  {
   @Override
   public void actionPerformed(ActionEvent e)
   {
    view.setShowSurfaces(showSurfacesButton.isSelected());
   }
  });
 }
}
