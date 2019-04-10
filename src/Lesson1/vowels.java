package Lesson1;

//Посчитать количество гласных букв в строках

public class vowels {

    public static void main(String[] args) {


        String[] ourArr = new String[9];
        ourArr[0] = "rp  cvpilk jgqdsiavlwewjsadtijrtezjhvel pfwuu tybrrevnnnpxj";
        ourArr[1] = "bnl izicllxvhazpvyw wujlqebpnauvpni n uyrou uovx  miwup";
        ourArr[2] = "wdtxgr ovhpulttmwupzz ys dqcafkxpgvoikuzxsuk xilaskz";
        ourArr[3] = "ps akvat xlstmwfpvdjztuxx xqixi rqtb tia nspbpox";
        ourArr[4] = "f lyjjeihtb xoepw cskcmyobhrcgdnsvtcgz ttnbsq h  qgf";
        ourArr[5] = "zkubx lfeyeooh otcvkkpbwivrtcuvb xkmsowx";
        ourArr[6] = "ozck dfp v viiazvtbxrkmpaejou cegmnsvajivpzz";
        ourArr[7] = "zzpt nmr crgrsjeu lncdlc nejnec izjf outdt unp qdrgmuwtv";
        ourArr[8] = "ag eptcnfncgqiqmf  oaxfsdxvb s  eoztwbjbvrn vg  y c";


        char[] vowelsArr = new char[]{'a', 'e', 'i', 'o', 'u', 'y'};


        int counter = 0;


        for (String a: ourArr) {
            char[] arrayToVowels = a.toCharArray();

            for (char ch : arrayToVowels) {
                for (char ch2 : vowelsArr) {
                    if (ch == ch2){
                        counter++;
                    }
                }
            }

            System.out.println(counter);
            counter = 0;
        }



    }
}
