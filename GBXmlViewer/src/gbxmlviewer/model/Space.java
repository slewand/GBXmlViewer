package gbxmlviewer.model;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.gui.Appearance;
import gbxmlviewer.gui.View;

/**
 * A space represents a volume enclosed by surfaces.
 */
public class Space extends ModelElement
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
   if(planarGeometry!=null && planarGeometry.getPolyLoop()!=null)
    screenShapes.add(new Area(planarGeometry.getPolyLoop().getAsScreenPath(view)));
  }
  Appearance appearance = screenShapes.isEmpty() ? null : new Appearance(screenShapes, Color.BLACK, Color.RED);
  return appearance;
 }
 
 public Appearance getPlanarGeometryAppearanceForView(View view)
 {
  Area screenShape = null;
  if(planarGeometry!=null)
   screenShape = new Area(planarGeometry.getAsScreenPath(view));
  Appearance appearance = screenShape==null ? null : new Appearance(screenShape, Color.BLACK, Color.BLUE);
  return appearance;
 }
 
 public Appearance getShellGeometryAppearanceForView(View view)
 {
  List<Area> screenShapes = new ArrayList<>();  
  if(shellGeometry!=null && shellGeometry.getClosedShell()!=null)
  {
   for(GeneralPath generalPath: shellGeometry.getClosedShell().getAsScreenPaths(view))
    screenShapes.add(new Area(generalPath));
  }
  Appearance appearance = screenShapes.isEmpty() ? null : new Appearance(screenShapes, Color.BLACK, Color.YELLOW);
  return appearance;  
 }
}
