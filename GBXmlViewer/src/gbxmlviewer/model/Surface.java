package gbxmlviewer.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.geom.Point3D;
import gbxmlviewer.gui.Appearance;
import gbxmlviewer.gui.View;

/**
 * Powierzchnia graniczna miêdzy pomieszczeniami
 */
public class Surface extends ModelElement
{
 private PlanarGeometry planarGeometry;
 private List<Space> adjacentSpaces = new ArrayList<>(2);
 private List<Opening> openings = new ArrayList<>();

 public PlanarGeometry getPlanarGeometry()
 {
  return planarGeometry;
 }

 public void setPlanarGeometry(PlanarGeometry planarGeometry)
 {
  this.planarGeometry = planarGeometry;
 }
 
 public List<Space> getAdjacentSpaces()
 {
  return adjacentSpaces;
 }
 
 public void addAdjacentSpace(Space adjacentSpace)
 {
  adjacentSpaces.add(adjacentSpace);
 }
 
 public List<Opening> getOpenings()
 {
  return openings;
 }
 
 public void addOpening(Opening opening)
 {
  openings.add(opening);
 }
 
 public Bounds3D getBounds3D()
 {
  return planarGeometry.getBounds3D();
 }

 public Appearance getAppearanceForView(View view)
 {
  Area surfaceRegion = null;
  if(planarGeometry!=null)
   surfaceRegion = new Area(planarGeometry.getAsScreenPath(view));
  
  // Otwory
  if(surfaceRegion!=null && !openings.isEmpty())
  {
   for(Opening opening: openings)
   {
    PlanarGeometry planarGeometry = opening.getPlanarGeometry();
    if(planarGeometry!=null)
    {
     GeneralPath generalPath = new GeneralPath();
     PolyLoop polyLoop = planarGeometry.getPolyLoop();
     List<CartesianPoint> points = polyLoop.getCartesianPoints();
     for(int i=0; i<points.size(); i++)
     {
      Point3D point3D = points.get(i).getAsPoint3D();
      Point point2D = view.transform3DTo2D(point3D);
      if(i==0)
       generalPath.moveTo(point2D.x, point2D.y);
      else
       generalPath.lineTo(point2D.x, point2D.y);
     }
     generalPath.closePath();
     Area openingRegion = new Area(generalPath);
     surfaceRegion.subtract(openingRegion);
    }
   }
  }
  Appearance appearance = new Appearance(surfaceRegion, Color.BLACK, Color.GREEN);
  return appearance;
 }
}
