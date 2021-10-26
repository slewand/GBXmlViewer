package gbxmlviewer.model;

public class ModelHelper
{
 /**
  * Zwraca pomieszczenie o podanym id
  * @param model model
  * @param spaceId szukane id
  * @return pomieszczenie o danym id lub null gdy nie istnieje
  */
 public static Space getSpaceById(Model model, String spaceId)
 {
  Space resultSpace = null;
  for(Building building: model.getCampus().getBuildings())
  {
   for(Space space: building.getSpaces())
   {
    if(spaceId!=null && spaceId.equals(space.getId()))
    {
     resultSpace = space;
     break;
    }
    if(resultSpace!=null)
     break;
   }
  }
  return resultSpace;
 }
 
 public static String modelToString(Model model)
 {
  int indent = 0;
  StringBuilder stringBuilder = new StringBuilder();
  stringBuilder.append(String.format("Model (id=%s)\r\n", model.getId()));
  Campus campus = model.getCampus();
  indent++;
  insertSpaces(stringBuilder, indent);
  stringBuilder.append(String.format("Campus (id=%s)\r\n", campus.getId()));
  for(Building building: campus.getBuildings())
  {
   indent++;
   insertSpaces(stringBuilder, indent);
   stringBuilder.append(String.format("Building (id=%s)\r\n", building.getId()));
   
   for(Space space: building.getSpaces())
   {
    indent++;
    insertSpaces(stringBuilder, indent);
    stringBuilder.append(String.format("Space (id=%s)\r\n", space.getId()));
    indent--;
   }
  }
  indent--;
  for(Surface surface: campus.getSurfaces())
  {
   indent++;
   insertSpaces(stringBuilder, indent);
   stringBuilder.append(String.format("Surface (id=%s)\r\n", surface.getId()));
   
   indent++;
   
   if(surface.getAdjacentSpaces().size()>0)
   {
    insertSpaces(stringBuilder, indent);   
    stringBuilder.append(String.format("AdjacentSpace: %s\r\n", surface.getAdjacentSpaces().get(0).getId()));
   }
   if(surface.getAdjacentSpaces().size()>1)
   {
    insertSpaces(stringBuilder, indent);   
    stringBuilder.append(String.format("AdjacentSpace: %s\r\n", surface.getAdjacentSpaces().get(1).getId()));    
   }
   
   indent--;
   
   for(Opening opening: surface.getOpenings())
   {
    indent++;
    insertSpaces(stringBuilder, indent);
    stringBuilder.append(String.format("Opening (id=%s)\r\n", opening.getId()));
    indent--;
   }
   indent--;   
  }
  return stringBuilder.toString();
 }
 
 private static void insertSpaces(StringBuilder stringBuilder, int spaces)
 {
  for(int i=0; i<2*spaces; i++)
   stringBuilder.append(' ');
 }
}
