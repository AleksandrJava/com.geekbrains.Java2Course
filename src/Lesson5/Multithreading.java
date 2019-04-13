package Lesson5;

//Необходимо написать два метода, которые делают следующее:
//1) Создают одномерный длинный массив
//2) Заполняют этот массив единицами;
//3) Засекают время выполнения: long a = System.currentTimeMillis();
//4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//5) Проверяется время окончания метода System.currentTimeMillis();
//6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
//Отличие первого метода от второго:
//Первый просто бежит по массиву и вычисляет значения.
//Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
//Примечание:
//System.arraycopy() копирует данные из одного массива в другой:
//System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
//По замерам времени:
//Для первого метода надо считать время только на цикл расчета:
//for (int i = 0; i < size; i++) {
//arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//}
//Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.

import java.util.ArrayList;

public class Multithreading extends Thread{

    static final int size = 10000000;

    public static void main(String[] args) {
        way1();
        way2();
    }

    public static void way1(){
        float[] arr = new float[size];

        for(int i = 0; i < size; i++){
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        for(int i = 0; i < size; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis() - a);
    }


    public static void way2() {
        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        int y = 2; // Количество потоков - можно менять!

        final int h = size / y;

        long a = System.currentTimeMillis();

        ArrayList<float[]> allArrays = new ArrayList<float[]>();
        allArrays.ensureCapacity(y);

        for (int i = 0; i < y; i++) {
            float[] b = new float[h];
            System.arraycopy(arr, size - ((y - i) * h), b, 0, h);
            allArrays.add(b);
        }  //заполнили allArrays  (y)массивами и скопировали в них элементы arr


        ArrayList<Thread> arrTheads = new ArrayList<Thread>();
        arrTheads.ensureCapacity(y);
        //Создали список потоков


        for (float[] t : allArrays) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < h; j++) {
                        t[j] = (float) (arr[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                    }
                }
            });
            arrTheads.add(t1);
        }
        //Заполнили список потоков, а также вычислили все значения (y)массивов

        for (float[] w : allArrays) {
            for (int i = 0; i < y; i++) {
                System.arraycopy(w, 0, arr, size - ((y - i) * h), h);
            }
        }
        //Выполнили обратное копирование в большой массив

        for (Thread r : arrTheads) {
            r.start();
            try {
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println(System.currentTimeMillis() - a);
    }
}

