package gbxmlviewer.gui;

import java.awt.Color;
import java.awt.Shape;

/**
 * Opis wygl¹du na ekranie
 */
public class Appearance
{
 private Shape screenShape;
 private Color color;
 
 public Appearance(Shape screenShape, Color color)
 {
  this.screenShape = screenShape;
  this.color = color;
 }

 public Shape getScreenShape()
 {
  return screenShape;
 }

 public Color getColor()
 {
  return color;
 }
}
