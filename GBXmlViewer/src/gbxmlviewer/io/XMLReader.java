package gbxmlviewer.io;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import gbxmlviewer.model.Campus;
import gbxmlviewer.model.CartesianPoint;
import gbxmlviewer.model.Model;
import gbxmlviewer.model.PlanarGeometry;
import gbxmlviewer.model.PolyLoop;
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
   parser.parse(file.getAbsolutePath(), new GBXmlHandler());
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
  private Surface surface;
  private PlanarGeometry planarGeometry;
  private PolyLoop polyLoop;
  private CartesianPoint cartesianPoint;
  private Double coordinate;
  
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
    case SURFACE_TAG:
     surface = new Surface();
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
    case PLANAR_GEOMETRY_TAG:
     if(surface!=null)
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
                       SURFACE_TAG = "Surface",
                       PLANAR_GEOMETRY_TAG = "PlanarGeometry",
                       POLY_LOOP_TAG = "PolyLoop",
                       CARTESIAN_POINT_TAG = "CartesianPoint",
                       COORDINATE_TAG = "Coordinate";
  
 }
}