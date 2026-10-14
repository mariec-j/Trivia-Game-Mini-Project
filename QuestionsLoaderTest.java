import Class_Src.QuestionsLoader;

public class QuestionsLoaderTest {
    public static void main(String[] args) {
        QuestionsLoader test = new QuestionsLoader();

        test.readQuestions("src\\Question_Text_Files\\questions.txt");
        test.readAnswers("src\\Question_Text_Files\\rubric.txt");

        System.out.print("QUESTION SET - - - -\n");
        for (int i = 0; i < test.map.size(); i++) {
            System.out.print(test.map.get(i+1) + "\n");
        }

    }
}