package gbxmlviewer.gui;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;

/**
 * Opis wygl¹du na ekranie
 */
public class Appearance
{
 private Area screenShape;
 private Color strokeColor;
 private Color fillColor;
 
 public Appearance(Area screenShape, Color strokeColor, Color fillColor)
 {
  this.screenShape = screenShape;  
  this.strokeColor = strokeColor;
  this.fillColor = fillColor;
 }

 public Shape getScreenShape()
 {
  return screenShape;
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
