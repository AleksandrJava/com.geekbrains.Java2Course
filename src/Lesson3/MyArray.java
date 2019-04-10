package Lesson3;

//Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
//Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
//Посчитать, сколько раз встречается каждое слово.


//Необходимо из консоли считать пароль и проверить валидность,
//результат будет true или false
//Требования:
//1. Пароль должен быть не менее 8ми символов.
//2. В пароле должно быть число
//3. В пароле должна быть хотя бы 1 строчная буква
//4. В пароле должна быть хотя бы 1 заглавная буква


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyArray {
    public static void main(String[] args) {
        number1();
        number2();
        additionaltask(); //ввести пароль для проверки на валидность

    }

    public static void number1() {
        Map<String, Integer> hm = new HashMap<>();
        String[] array = {"Dog", "Cat", "Pig", "Fish", "Clock", "Chair", "Cat", "Clock", "Boots"};

        for (String arr : array) {
            hm.put(arr, hm.getOrDefault(arr, 0) + 1);
        }
        System.out.println(hm);

        Set<String> unique = new HashSet<>(Arrays.asList(array));
        System.out.println(unique);
    }

    public static void number2(){
        PhoneBook directory = new PhoneBook();
        directory.add("Petrov", "8927456987");
        directory.add("Samsonov", "87632589512");
        directory.add("Begun", "82576892517");
        directory.add("Petrov", "89413151616");
        //System.out.println(directory.get("Petrov"));
        directory.get("Petrov");

    }

    public static void additionaltask(){
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
        Scanner scanner = new Scanner(System.in);
        Matcher n = p.matcher(scanner.nextLine());

        System.out.println(n.matches());
    }

}
