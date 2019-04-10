package Lesson2.ArrayExceptions;

//1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4,
//при подаче массива другого размера необходимо бросить исключение MyArraySizeException.

//2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
//Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
//должно быть брошено исключение MyArrayDataException – с детализацией, в какой именно ячейке лежат неверные данные.

//3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException
//и MyArrayDataException и вывести результат расчета.

public class ArrayExceptions {
    public static void main(String[] args) {
        try {
            String[][] result = ArraySizeAndData.getSize(new String[][] {{"1", "19", "8", "7"}, {"15", "32", "18", "4"}, {"21", "48", "39", "26"}, {"7", "19", "98", "6"}} );
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
    }

}

class MyArraySizeException extends Exception {

    public MyArraySizeException(String msg) {
        super(msg);
    }
}

class MyArrayDataException extends Exception {

    public MyArrayDataException(String msg) {
        super(msg);
    }
}

class ArraySizeAndData {

    public static String[][] getSize(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int k = 16;
        if (arr.length * arr.length != k) throw new MyArraySizeException("Массив некорректной длины");
        int sum = 0;
        for(int i=0; i<arr.length;i++ ){
            for(int j=0;j<arr.length; j++){
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("В массиве есть нестроковый тип данных. Ошибка в ячейке [" + i + "][" + j +"]");
                }

            }
        }
        System.out.println(sum);
        return arr;

    }
}




