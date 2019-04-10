package Lesson2.DayOfWeek;

//Требуется реализовать enum DayOfWeek, который будет представлять дни недели.
//С его помощью необходимо решить задачу определения кол-ва рабочих часов до конца недели по заданному текущему дню.

//Возвращает кол-во оставшихся рабочих часов до конца
//недели по заданному текущему дню. Считается, что
//текущий день ещё не начался, и рабочие часы за него
//должны учитываться.


 enum DayOfWeek {
    MONDAY(8), TUESDAY(8), WEDNESDAY(8), THURSDAY(8), FRIDAY(8), SATURDAY(0), SUNDAY(0);

    private int hour;

    public int getHour(){
        return hour;
    }

    DayOfWeek(int hour){
        this.hour = hour;
    }
 }


public class DayOfWeekMain {

    public static void main(final String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.FRIDAY));
    }

        public static int getWorkingHours (DayOfWeek dayOfWeek){

            int sum = 0;
            for (DayOfWeek s: DayOfWeek.values()) {
                if(s.ordinal() >= dayOfWeek.ordinal()){
                    sum += s.getHour();
                }
            }
            return sum;
        }
}

