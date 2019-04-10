package Lesson1.Marathon;

public class MainMarathon {
    public static void main(String[] args) {
        Participant[] participants = {
                new Cat("Барсик"), new Dog("Бобик"), new Woman("Анастасия"), new Man("Максим")
        };

        Team team = new Team("Марафонцы", participants);

        Obstacle[] obstacles = {
                new Cross(80), new Wall(2), new Water(10)
        };
        Course course = new Course(obstacles);
        team.infoAllTeam();
        course.doIt(participants);
        team.successfulTeam();

    }
}
