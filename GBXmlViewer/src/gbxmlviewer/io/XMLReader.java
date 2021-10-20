package gbxmlviewer.io;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import gbxmlviewer.model.Building;
import gbxmlviewer.model.Campus;
import gbxmlviewer.model.CartesianPoint;
import gbxmlviewer.model.Model;
import gbxmlviewer.model.Opening;
import gbxmlviewer.model.PlanarGeometry;
import gbxmlviewer.model.PolyLoop;
import gbxmlviewer.model.Space;
import gbxmlviewer.model.SpaceBoundary;
import gbxmlviewer.model.Surface;

public class XMLReader
{
 private Model model;
 
 public void readModel(File file)
 {
  try
  {
   SAXParserFactory factory = SAXParserFactory.newInstance();
   SAXParser parser = factory.newSAXParser();
   String absolutePath = "file:///"+file.getAbsolutePath();
   parser.parse(absolutePath, new GBXmlHandler());
  }
  catch (FileNotFoundException e)
  {
   System.err.println("B³¹d: Nie mo¿na wczytaæ pliku !!!");
  }
  catch (Exception e)
  {
   e.printStackTrace();
  }
 }

 public Model getModel()
 {
  return model;
 }
 
 private class GBXmlHandler extends DefaultHandler
 {
  private String lastTagName;
  private StringBuilder valueBuilder = new StringBuilder();

  private Campus campus;
  private Building building;
  private Space space;
  private Surface surface;
  private Opening opening;
  private SpaceBoundary spaceBoundary;
  private PlanarGeometry planarGeometry;
  private PolyLoop polyLoop;
  private CartesianPoint cartesianPoint;
  
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
  {
   lastTagName = qName;
   switch(qName)
   {
    case GBXML_TAG:
     model = new Model();
    break;
    case CAMPUS_TAG:
     campus = new Campus();
    break;
    case BUILDING_TAG:
     building = new Building();
    break;
    case SPACE_TAG:
     space = new Space();
    break;
    case SURFACE_TAG:
     surface = new Surface();
    break;
    case OPENING_TAG:
     opening = new Opening();
    break;
    case SPACE_BOUNDARY_TAG:
     spaceBoundary = new SpaceBoundary();
    break;    
    case PLANAR_GEOMETRY_TAG:
     planarGeometry = new PlanarGeometry();
    break;
    case POLY_LOOP_TAG:
     polyLoop = new PolyLoop();
    break;
    case CARTESIAN_POINT_TAG:
     cartesianPoint = new CartesianPoint();     
    break;
   }
   super.startElement(uri, localName, qName, attributes);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException
  {
   switch(qName)
   {
    case CAMPUS_TAG:
     model.setCampus(campus);
     campus = null;
    break;
    case SURFACE_TAG:
     if(campus!=null)
      campus.addSurface(surface);
     surface = null;
    break;
    case OPENING_TAG:
     if(surface!=null)
      surface.addOpening(opening);
     opening = null;
    break;
    case PLANAR_GEOMETRY_TAG:
     if(opening!=null)
      opening.setPlanarGeometry(planarGeometry);
     else if(surface!=null)
      surface.setPlanarGeometry(planarGeometry);
     planarGeometry = null;
    break;
    case POLY_LOOP_TAG:
     if(planarGeometry!=null)
      planarGeometry.addPolyLoop(polyLoop);
     polyLoop = null;
    break;    
    case CARTESIAN_POINT_TAG:
     if(polyLoop!=null)
      polyLoop.addCartesianPoint(cartesianPoint);
     cartesianPoint = null;
    break;
    case COORDINATE_TAG:
     if(cartesianPoint!=null)
      cartesianPoint.addCoordinate(Double.parseDouble(valueBuilder.toString()));
    break;
   }
   valueBuilder.setLength(0);
  }
  
  @Override
  public void characters(char[] text, int start, int length) throws SAXException
  {
   valueBuilder.append(text, start, length);   
  }  

  private final String GBXML_TAG = "gbXML",
                       CAMPUS_TAG = "Campus",
                       BUILDING_TAG = "Building",
                       SPACE_TAG = "Space",
                       OPENING_TAG = "Opening",
                       SURFACE_TAG = "Surface",
                       SPACE_BOUNDARY_TAG = "SpaceBoundary",
                       PLANAR_GEOMETRY_TAG = "PlanarGeometry",
                       POLY_LOOP_TAG = "PolyLoop",
                       CARTESIAN_POINT_TAG = "CartesianPoint",
                       COORDINATE_TAG = "Coordinate";
  
 }
}