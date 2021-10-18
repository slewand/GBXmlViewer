package gbxmlviewer.res;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ResourceManager
{
 private ResourceManager()
 {
 }

 /** Zwraca ikonê o podanej nazwie */
 public Icon getIcon(String name)
 {
  Icon icon=new ImageIcon(getClass().getResource("icons/"+name+".png"));
  return icon;
 }

 private static ResourceManager instance;
 public static ResourceManager getInstance()
 {
  if(instance==null)
   instance=new ResourceManager();
  return instance;
 } 
}
