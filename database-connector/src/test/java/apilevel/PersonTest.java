package apilevel;

 /*
 * PersonTest   4/15/16, 16:51
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.RequestException;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tests class {@code Person}
 *
 * @author Kyrylo Havrylenko
 * @see Person
 */
public class PersonTest {
    public static final Logger log = Logger.getLogger(PersonTest.class.getName());

    @Test
    public void initTestPerson() {
        log.log(Level.INFO, "Should be Person with 4 String fields of text");
        try {
            Person expected = new Person();
            Person actual = new Person("Тестовый", "Пользователь", "Петрович", "Киевская, 7", 20);
            Assert.assertEquals(expected, actual);
        } catch(RequestException e) {
            e.printStackTrace();
        }
    }
}
