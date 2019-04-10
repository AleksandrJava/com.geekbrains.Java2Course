package Lesson3;

//Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
//В этот телефонный справочник с помощью метода add() можно добавлять записи.
//С помощью метода get() искать номер телефона по фамилии.
//Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
//тогда при запросе такой фамилии должны выводиться все телефоны.

import java.util.*;

public class PhoneBook {
    private Map<String, HashSet<String>> map;
    private HashSet<String> numbers;

    PhoneBook() {
        this.map = new HashMap<>();
    }

    public void add(String surname, String phonenumber){
        if(map.containsKey(surname)){
            numbers = map.get(surname);
            numbers.add(phonenumber);
            map.put(surname, numbers);
        } else {
            numbers = new HashSet<>();
            numbers.add(phonenumber);
            map.put(surname, numbers);
        }
    }

    public void get(String surname) {
        if (map.get(surname) == null) {
            System.out.println("Такого человека нет в справочнике");
        } else
            System.out.println(map.get(surname));
    }
}
