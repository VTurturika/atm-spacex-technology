package apilevel;

 /*
 * Person   4/15/16, 10:39
 *
 * By Kyrylo Havrylenko
 *
 */

/**
 * Storage for data about customer
 *
 * @author Kyrylo Havrylenko
 * @see
 */
public class Person {
    String lastName;
    String firstName;
    String middleName;
    String adress;

    Person() {
        this("Тестовый", "Пользователь", "Петрович", "Киевская, 7");
    }

    public Person(String lastName, String firstName, String middleName, String adress) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.adress = adress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
