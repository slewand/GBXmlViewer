package gbxmlviewer.model;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.gui.Appearance;
import gbxmlviewer.gui.View;

public class Space
{
 private List<SpaceBoundary> spaceBoundaries = new ArrayList<>();
 private PlanarGeometry planarGeometry;
 private ShellGeometry shellGeometry;
 
 public ShellGeometry getShellGeometry()
 {
  return shellGeometry;
 }

 public void setShellGeometry(ShellGeometry shellGeometry)
 {
  this.shellGeometry = shellGeometry;
 }

 public PlanarGeometry getPlanarGeometry()
 {
  return planarGeometry;
 }

 public void setPlanarGeometry(PlanarGeometry planarGeometry)
 {
  this.planarGeometry = planarGeometry;
 }

 public List<SpaceBoundary> getSpaceBoundaries()
 {
  return spaceBoundaries;
 }
 
 public void addSpaceBoundary(SpaceBoundary spaceBoundary)
 {
  spaceBoundaries.add(spaceBoundary);
 }
 
 
 public Appearance getSpaceBoundariesAppearanceForView(View view)
 {
  List<Area> screenShapes = new ArrayList<>();
  for(SpaceBoundary spaceBoundary: spaceBoundaries)
  {
   PlanarGeometry planarGeometry = spaceBoundary.getPlanarGeometry();
   if(planarGeometry!=null && !planarGeometry.getPolyLoops().isEmpty())
    screenShapes.add(new Area(planarGeometry.getScreenPath(view)));
  }
  Appearance appearance = screenShapes.isEmpty() ? null : new Appearance(screenShapes, Color.BLACK, Color.RED);
  return appearance;
 }
 
 public Appearance getPlanarGeometryAppearanceForView(View view)
 {
  Appearance appearance = null;
  if(planarGeometry!=null)
  {
   GeneralPath generalPath = planarGeometry.getScreenPath(view);
   if(generalPath!=null)
    appearance = new Appearance(new Area(generalPath), Color.BLACK, Color.BLUE);
  }
  return appearance;
 }
 
 public Appearance getShellGeometryAppearanceForView(View view)
 {
  Appearance appearance = null;
  if(shellGeometry!=null && shellGeometry.getClosedShell()!=null)
  {
   GeneralPath generalPath = shellGeometry.getClosedShell().getScreenPath(view);
   if(generalPath!=null)
    appearance = new Appearance(new Area(generalPath), Color.BLACK, Color.YELLOW);
  }
  return appearance;  
 }
}
