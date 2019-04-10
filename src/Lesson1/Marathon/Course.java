package Lesson1.Marathon;

//Добавить класс Course (полоса препятствий),
//в котором будут находиться массив препятствий и метод,
//который будет просить команду пройти всю полосу;

public class Course  {
    Obstacle[] obstacles;

    public Course(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Participant[] team) {
        for (Participant c: team ) {
            for (Obstacle o: obstacles) {
                o.doit(c);
                if(!c.isOnDistance()) {
                    break;
                }
            }
        }
    }


}
