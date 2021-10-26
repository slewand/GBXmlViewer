package gbxmlviewer.model;

public abstract class ModelElement
{
 private String id;
 
 public String getId()
 {
  if(id==null)
   return "";
  else
   return id;
 }
 
 public void setId(String id)
 {
  this.id = id;
 }
}
