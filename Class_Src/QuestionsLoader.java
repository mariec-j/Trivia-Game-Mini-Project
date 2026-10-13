package Class_Src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionsLoader {
    public HashMap<Integer,ArrayList<String>> map;
    public ArrayList<Integer> list;

    public void readQuestions() {
        try {
            map = new HashMap<>();
            FileReader fileReader = new FileReader("src\\Question_Text_Files\\questions.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int readerCount = 0;

            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) { break; }

                System.out.print(readerCount + ": " + line + "\n");

                int questionNumber = readerCount/5 + 1;
                if (readerCount % 5 == 0) {
                    map.put(questionNumber, new ArrayList<>());
                }
                map.get(questionNumber).add(line);
                readerCount++;
            }

        } catch (FileNotFoundException e) {
            System.out.print("FileNotFoundException Found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("IOException Found.");
            e.printStackTrace();
        }
    }
    public void readAnswers() {
        try {
            list = new ArrayList<Integer>();
            FileReader fileReader = new FileReader("src\\Question_Text_Files\\rubric.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int readerCount = 0;
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) { break; }

                System.out.print(readerCount + ": " + line + "\n");
                list.add(Integer.parseInt(line));
                readerCount++;
            }

        } catch (FileNotFoundException e) {
            System.out.print("FileNotFoundException Found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("IOException Found.");
            e.printStackTrace();
        }
    }
}
