package gbxmlviewer.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.util.List;

import javax.swing.JPanel;

import gbxmlviewer.geom.Bounds3D;
import gbxmlviewer.geom.Point3D;
import gbxmlviewer.model.Model;
import gbxmlviewer.model.Space;
import gbxmlviewer.model.Surface;

/**
 * Widok, na którym odbywa siê rysowanie
 */
@SuppressWarnings("serial")
public class View extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
 /** Powiêkszenie */
 private double zoom;
 /** K¹t widzenia kamery */
 private double kp = Math.toRadians(60.0);
 /** Pomocnicze */
 private double c1, c3;
 /** Powiêkszenie */
 private double scale;
 
 /** K¹ty obrotu */
 private double alpha, beta;
 /** Macierz obrotu */
 private transient double[][] Mt = new double[3][3];
 /** Ostatnia pozycji myszki */
 private transient int lastX = 0, lastY = 0;
 /** Przesuniêcie (w pikselach) */
 private int transX = 0, transY = 0; 

 /** Maksymalna rozpiêtoœæ modelu w dowolnym kierunku */
 private double maxBound;
 /** Œrodek modelu */
 private Point3D centralPoint;
 
 private boolean showSurfacesPlanarGeometryStroke;
 private boolean showSurfacesPlanarGeometryFill;
 private boolean showSpacesSpaceBoundaryStroke;
 private boolean showSpacesSpaceBoundaryFill;
 private boolean showSpacesPlanarGeometryStroke;
 private boolean showSpacesPlanarGeometryFill;
 private boolean showSpacesShellGeometryStroke;
 private boolean showSpacesShellGeometryFill;
 
 /** Aktualny model lub null */
 private Model model;
 
 public View()
 {
  reset();
  addMouseListener(this);
  addMouseMotionListener(this);
  addMouseWheelListener(this);
 }
 
 
 public void reset()
 {
  alpha = Math.PI;
  beta = 0.0;
  scale = 1.0;
  zoom = 1.0;
  lastX = 0;
  lastY = 0;
  transX = 0;
  transY = 0;
  if(model!=null)
  {
   Bounds3D bounds = model.getBounds3D();
   centralPoint = new Point3D((bounds.getxMin()+bounds.getxMax())/2.0, (bounds.getyMin()+bounds.getyMax())/2.0, (bounds.getzMin()+bounds.getzMax())/2.0);
   maxBound = Double.max(Double.max(bounds.getDx(), bounds.getDy()), bounds.getDz());
  }
  else
  {
   maxBound = 2.0;
   centralPoint = new Point3D(0.0, 0.0, 0.0);
  }
 }
 
 public void setModel(Model model)
 {  
  this.model = model;
  reset();
  repaint();
 }
 
 @Override
 protected void paintComponent(Graphics g)
 {
  calculateAll();
  Graphics2D g2d = (Graphics2D)g;
  int viewWidth = getWidth();
  int viewHeight = getHeight();
  g.setColor(Color.LIGHT_GRAY);
  g.fillRect(0, 0, viewWidth, viewHeight);
  
  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  if(model!=null)
  {
   List<Surface> surfaces = model.getCampus().getSurfaces();
   List<Space> spaces = model.getCampus().getBuildings().get(0).getSpaces();

   g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
   if(showSpacesSpaceBoundaryFill)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getSpaceBoundariesAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setPaint(appearance.getFillColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.fill(screenShape);
     }
    }
   }

   if(showSpacesPlanarGeometryFill)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getPlanarGeometryAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setPaint(appearance.getFillColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.fill(screenShape);
     }
    }    
   }
   
   if(showSpacesShellGeometryFill)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getShellGeometryAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setPaint(appearance.getFillColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.fill(screenShape);
     }
    }    
   }
   
   if(showSurfacesPlanarGeometryFill)
   {    
    for(Surface surface: surfaces)
    {
     Appearance appearance = surface.getAppearanceForView(this);
     g2d.setPaint(appearance.getFillColor());
     for(Area screenShape: appearance.getScreenShapes())
      g2d.fill(screenShape);
    }
   }
      
   g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
      
   if(showSpacesSpaceBoundaryStroke)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getSpaceBoundariesAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setColor(appearance.getStrokeColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.draw(screenShape);
     }
    }    
   }
   
   if(showSpacesPlanarGeometryStroke)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getPlanarGeometryAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setColor(appearance.getStrokeColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.draw(screenShape);
     }
    }
   }
   
   if(showSpacesShellGeometryStroke)
   {
    for(Space space: spaces)
    {
     Appearance appearance = space.getShellGeometryAppearanceForView(this);
     if(appearance!=null)
     {
      g2d.setColor(appearance.getStrokeColor());
      for(Area screenShape: appearance.getScreenShapes())
       g2d.draw(screenShape);
     }
    }
   }
   
   if(showSurfacesPlanarGeometryStroke)
   {
    for(Surface surface: surfaces)
    {
     Appearance appearance = surface.getAppearanceForView(this);
     g2d.setColor(appearance.getStrokeColor());
     for(Area screenShape: appearance.getScreenShapes())
      g2d.draw(screenShape);    
    }
   }
  }
  
//  // Uk³ad wspó³rzêdnych
//  Point p1 = null, p2 = null;  
//  p1 = transform3DTo2D(new Point3D(0.0, 0.0, 0.0));
//  if(p1!=null)
//  {
//   g.setColor(Color.BLUE); // oœ X
//   p2 = transform3DTo2D(new Point3D(1.0, 0.0, 0.0));
//   if(p2 != null)
//   {
//    g2d.draw(new Line2D.Double(p1, p2));
//    g2d.drawString("X", p2.x+5, p2.y);
//   }
//   g.setColor(Color.GREEN); // oœ Y
//   p2 = transform3DTo2D(new Point3D(0.0, 1.0, 0.0));
//   if(p2 != null)
//   {
//    g2d.draw(new Line2D.Double(p1, p2));
//    g2d.drawString("Y", p2.x+5, p2.y);
//   }
//   g.setColor(Color.RED); // oœ Z
//   p2 = transform3DTo2D(new Point3D(0.0, 0.0, 1.0));
//   if(p2 != null)
//   {
//    g2d.draw(new Line2D.Double(p1, p2));
//    g2d.drawString("Z", p2.x+5, p2.y);
//   }   
//  }
 }
 
 public void setElementVisiblity(boolean showSurfacesPlanarGeometryStroke, boolean showSurfacesPlanarGeometryFill,
                                 boolean showSpacesSpaceBoundaryStroke, boolean showSpacesSpaceBoundaryFill,
                                 boolean showSpacesPlanarGeometryStroke, boolean showSpacesPlanarGeometryFill,
                                 boolean showSpacesShellGeometryStroke, boolean showSpacesShellGeometryFill)
 {
  this.showSurfacesPlanarGeometryStroke = showSurfacesPlanarGeometryStroke;
  this.showSurfacesPlanarGeometryFill = showSurfacesPlanarGeometryFill;
  this.showSpacesSpaceBoundaryStroke = showSpacesSpaceBoundaryStroke;
  this.showSpacesSpaceBoundaryFill = showSpacesSpaceBoundaryFill;
  this.showSpacesPlanarGeometryStroke = showSpacesPlanarGeometryStroke;
  this.showSpacesPlanarGeometryFill = showSpacesPlanarGeometryFill;
  this.showSpacesShellGeometryStroke = showSpacesShellGeometryStroke;
  this.showSpacesShellGeometryFill = showSpacesShellGeometryFill;
  repaint();
 }
 
 public Point transform3DTo2D(Point3D point3D)
 {
  double halfWidth = getWidth()/2.0;
  double halfHeight = getHeight()/2.0;
  Point point = new Point();
  double c2;

  double x = point3D.getX() - centralPoint.getX();
  double y = point3D.getY() - centralPoint.getY();
  double z = point3D.getZ() - centralPoint.getZ();

  double a = Mt[0][0] * x + Mt[0][1] * y;
  double b = Mt[1][0] * x + Mt[1][1] * y + Mt[1][2] * z;
  double c = Mt[2][0] * x + Mt[2][1] * y + Mt[2][2] * z;

  double temp = scale * b;

  // punkt nie jest widoczny
  if (temp > c3)
   return null;

  c2 = c1 / (c3 - temp);
  point.x = (int) ((a) * c2 + halfWidth) + transX;
  point.y = (int) ((c) * c2 + halfHeight) + transY;

  return point;
 }

 public void mousePressed(MouseEvent e)
 {
  lastX = e.getX();
  lastY = e.getY();
 }

 public void mouseDragged(MouseEvent e)
 {
  if(e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK)
  {
   alpha += (double)(e.getY()-lastY)/100.0; 
   beta -= (double)(e.getX()-lastX)/60.0;
  }
  if(e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK)
  {
   transX += (e.getX()-lastX);
   transY += (e.getY()-lastY);
  }
  lastX = e.getX();
  lastY = e.getY();
  repaint();
 }

 public void mouseWheelMoved(MouseWheelEvent e)
 { 
  zoom = zoom + (double) e.getWheelRotation() / 10.0;  
  repaint();
 }

 public void mouseMoved(MouseEvent e) { }
 public void mouseClicked(MouseEvent e) { }
 public void mouseEntered(MouseEvent e) { }
 public void mouseExited(MouseEvent e) { }
 public void mouseReleased(MouseEvent e) { }

 /** Oblicza wszystkie niezbêdne wspó³czynniki (np. macierz obrotu) */
 private void calculateAll()
 {
  int width = this.getWidth();
  int height = this.getHeight();

  Mt[0][0] = Math.cos(-beta);
  Mt[1][0] = Math.sin(-beta) * Math.cos(alpha);
  Mt[2][0] = Math.sin(-beta) * Math.sin(alpha);
  Mt[0][1] = -Math.sin(-beta);
  Mt[1][1] = Math.cos(-beta) * Math.cos(alpha);
  Mt[2][1] = Math.cos(-beta) * Math.sin(alpha);
  Mt[0][2] = 0;
  Mt[1][2] = -Math.sin(alpha);
  Mt[2][2] = Math.cos(alpha);

  // parametry pola widzenia:
  double obs = width / (2 * Math.tan(kp / 2));
  scale = Math.min(width, height) / (0.5 * maxBound * zoom);
  c1 = obs * scale;
  c3 = obs + width;
 }

}
