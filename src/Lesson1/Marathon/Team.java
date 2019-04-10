package Lesson1.Marathon;

//Добавить класс Team, который будет содержать название команды,
//массив из четырех участников (в конструкторе можно сразу указыватьвсех участников ),
//метод для вывода информации о членах команды, прошедших дистанцию,
//метод вывода информации обо всех членах команды.

public class Team extends Participant {
      String teamName;
      Participant[] team;


    public Team(String teamName, Participant[] team) {
        this.teamName = teamName;
        this.team = team;
    }


    public void infoAllTeam () {
        System.out.println("Команда " + teamName + " участвует в составе: ");
        for (Participant c: team) {
            System.out.println(c.type + " " + c.name);
        }


    }

    public void successfulTeam () {
        for (Participant c : team) {
            if (c.isOnDistance() == true) {
                System.out.println(c.type + " " + c.name + " успешно прошёл всю трассу");

            }

        }
    }

}
