package gbxmlviewer.gui;

import java.awt.Color;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

/**
 * Opis wygl¹du obiektu na ekranie (kontury , kolory itp.)
 */
public class Appearance
{
 private List<Area> screenShapes;;
 private Color strokeColor;
 private Color fillColor;
 
 public Appearance(Area screenShape, Color strokeColor, Color fillColor)
 {
  screenShapes = new ArrayList<>(1);
  screenShapes.add(screenShape);  
  this.strokeColor = strokeColor;
  this.fillColor = fillColor;
 }

 public Appearance(List<Area> screenShapes, Color strokeColor, Color fillColor)
 {
  this.screenShapes = screenShapes;
  this.strokeColor = strokeColor;
  this.fillColor = fillColor;  
 }
 
 
 public List<Area> getScreenShapes()
 {
  return screenShapes;
 }

 public Color getStrokeColor()
 {
  return strokeColor;
 }
 
 public Color getFillColor()
 {
  return fillColor;
 }
}
