package gbxmlviewer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
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
 private ResourceManager resourceManager;
 private JToolBar toolBar;
 private JButton openFileButton;
 private JToggleButton showSurfacesPlanarGeometryStrokeButton;
 private JToggleButton showSurfacesPlanarGeometryFillButton;
 private JToggleButton showSpacesSpaceBoundaryStrokeButton;
 private JToggleButton showSpacesSpaceBoundaryFillButton;
 private JToggleButton showSpacesPlanarGeometryStrokeButton;
 private JToggleButton showSpacesPlanarGeometryFillButton;
 private JToggleButton showSpacesShellGeometryStrokeButton;
 private JToggleButton showSpacesShellGeometryFillButton;
 private JFileChooser fileChooser;
 
 private View view;
 
 public MainFrame()
 {
  super("GBXml Viewer");
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(800, 600);
  setLocationRelativeTo(null);
  resourceManager = ResourceManager.getInstance();
  
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
  
  showSurfacesPlanarGeometryStrokeButton = createVisibilityButton(false, "showBlack_24", "Surface-PlanarGeometry (stroke)");
  showSurfacesPlanarGeometryFillButton = createVisibilityButton(false, "showGreen_24", "Surface-PlanarGeometry (fill)");
  showSpacesSpaceBoundaryStrokeButton = createVisibilityButton(true, "showBlack_24", "Space-SpaceBoundary (stroke)");
  showSpacesSpaceBoundaryFillButton = createVisibilityButton(true, "showRed_24", "Space-SpaceBoundary (fill)");
  showSpacesPlanarGeometryStrokeButton = createVisibilityButton(false, "showBlack_24", "Space-PlanarGeometry (stroke)");
  showSpacesPlanarGeometryFillButton = createVisibilityButton(false, "showBlue_24", "Space-PlanarGeometry (fill)");
  showSpacesShellGeometryStrokeButton = createVisibilityButton(false, "showBlack_24", "Space-ShellGeometry (stroke)");
  showSpacesShellGeometryFillButton = createVisibilityButton(false, "showYellow_24", "Space-ShellGeometry (fill)");
  
  toolBar = new JToolBar();
  toolBar.setRollover(true);
  toolBar.setFloatable(false);
  toolBar.add(openFileButton);
  toolBar.addSeparator();
  toolBar.add(showSurfacesPlanarGeometryStrokeButton);
  toolBar.add(showSurfacesPlanarGeometryFillButton);
  toolBar.addSeparator();
  toolBar.add(showSpacesSpaceBoundaryStrokeButton);
  toolBar.add(showSpacesSpaceBoundaryFillButton);
  toolBar.add(showSpacesPlanarGeometryStrokeButton);
  toolBar.add(showSpacesPlanarGeometryFillButton);
  toolBar.add(showSpacesShellGeometryStrokeButton);
  toolBar.add(showSpacesShellGeometryFillButton);    
  add(toolBar, BorderLayout.NORTH);
  
  view = new View();
  add(view, BorderLayout.CENTER);
  updateElementVisibility();
  
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
 }
 
 private JToggleButton createVisibilityButton(boolean selected, String iconName, String toolTipText)
 {
  JToggleButton button = new JToggleButton("", selected);
  button.setIcon(resourceManager.getIcon(iconName));
  button.setToolTipText(toolTipText);
  button.setFocusable(false);  
  button.addActionListener(new ActionListener()
  {   
   @Override
   public void actionPerformed(ActionEvent e)
   {
    updateElementVisibility();    
   }
  });
  return button;
 }
 
 private void updateElementVisibility()
 {
  view.setElementVisiblity(showSurfacesPlanarGeometryStrokeButton.isSelected(),
                           showSurfacesPlanarGeometryFillButton.isSelected(),
                           showSpacesSpaceBoundaryStrokeButton.isSelected(),
                           showSpacesSpaceBoundaryFillButton.isSelected(),
                           showSpacesPlanarGeometryStrokeButton.isSelected(),
                           showSpacesPlanarGeometryFillButton.isSelected(),
                           showSpacesShellGeometryStrokeButton.isSelected(),
                           showSpacesShellGeometryFillButton.isSelected());
 }
}
