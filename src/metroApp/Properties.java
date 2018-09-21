/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metroApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author XDXD
 */
public class Properties {
    
    public static JsonObject initJsonDataObject(String path) throws FileNotFoundException {
        InputStream fileInputStream = new FileInputStream(path);
        JsonReader reader = Json.createReader(fileInputStream);
        JsonObject propertiesObject = reader.readObject();
        reader.close();
        return propertiesObject;
    }
   
    
    
     
    
    
}
