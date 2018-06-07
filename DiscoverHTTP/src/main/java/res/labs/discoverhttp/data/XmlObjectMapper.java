package res.labs.discoverhttp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;


/**
 * This class provides utility methods to convert Plain Old Java Objects (POJOs)
 * into their xml representation, and vice-versa. It relies on the jackson
 * library.
 *
 * @author Iando Rafidimalala
 */
public class XmlObjectMapper {
 private static final XmlMapper xmlMapper = new XmlMapper();

  /**
   * Converts a xml string into a POJO of the specified class
   *
   * @param <T> the class that we want to instantiate
   * @param xml the xml representation of the object
   * @param type the class to instantiate
   * @return an instance of T, which value corresponds to the xml string
   * @throws IOException
   */
  public static <T> T parseXml(String xml, Class<T> type) throws IOException {
    return xmlMapper.readValue(xml, type);
  }

  /**
   * Converts a POJO into its xml representation
   *
   * @param o the object to serialize
   * @return the xml representation of o
   * @throws JsonProcessingException
   */
  public static String toXml(Object o) throws JsonProcessingException {
    return xmlMapper.writeValueAsString(o);
  }    
}
