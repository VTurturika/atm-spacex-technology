package apilevel;

 /*
 * Person   4/15/16, 10:39
 *
 * By Kyrylo Havrylenko
 *
 */

import datalevel.RequestErrorCode;
import datalevel.RequestException;

import java.util.Objects;

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
    Integer age;

    /**
     * Constructor
     * @throws RequestException
     */
    Person() throws RequestException {
        this("Тестовый", "Пользователь", "Петрович", "Киевская, 7", 20);
    }

    /**
     * Constructor
     * @param lastName
     * @param firstName
     * @param middleName
     * @param adress
     * @param age
     * @throws RequestException
     */
    public Person(String lastName, String firstName, String middleName, String adress, Integer age) throws RequestException {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.adress = adress;
        if(age < 18) throw new RequestException(RequestErrorCode.WRONG_AGE);
        this.age = age;
    }

    /**
     * @return
     */
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(getLastName(), person.getLastName()) &&
                Objects.equals(getFirstName(), person.getFirstName()) &&
                Objects.equals(getMiddleName(), person.getMiddleName()) &&
                Objects.equals(getAdress(), person.getAdress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLastName(), getFirstName(), getMiddleName(), getAdress());
    }
}
