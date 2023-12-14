package org.example.controller;

import org.example.db.DataBase;
import org.example.dto.Dictionary;
import org.example.repositry.DictionaryRepository;
import org.example.service.DictionaryService;
import org.example.utils.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class Controllerr {
    static ScannerUtil scannerUtil = new ScannerUtil();
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private DataBase dataBase;


    public void start() {
        dataBase.creatTable();
        do {
            showMenu();
            int action = getAction();
            switch (action) {
                case 0 -> {
                    return;
                }
                case 1 -> addWord();
                case 2 -> wordlist();
                case 3 -> multipleChoice();
                case 4 -> spelling();
                case 5 -> searching();
                case 6 -> deleteById();
            }

        } while (true);

    }

    private void multipleChoice() {
        int attempts=0,right = 0,wrong=0;
        do {
            Map<String, String> map = new HashMap<>();
            for (Dictionary dictionary : dictionaryRepository.dictionaryList()) {
                map.put(dictionary.getWordUzb(), dictionary.getWordEng());
            }
            // Random  savol tanlov
            Random random = new Random();
            int randomIndex = random.nextInt(map.size());
            String questionUzb = (String) map.keySet().toArray()[randomIndex];
            String correctAnswerEng = map.get(questionUzb);

            // Random variantlar tayyorlash
            String[] answerVariants = new String[4];
            int correctVariantIndex = random.nextInt(4);
            answerVariants[correctVariantIndex] = correctAnswerEng;

            for (int i = 0; i < 4; i++) {
                if (i != correctVariantIndex) {
                    int randomVariantIndex = random.nextInt(map.size());
                    answerVariants[i] = (String) map.values().toArray()[randomVariantIndex];
                }
            }

            // Savolni chiqarish
            System.out.println("Savol: " + questionUzb);

            // Variantlarni chiqarish
            for (int i = 0; i < 4; i++) {
                System.out.println((char) ('A' + i) + ". " + answerVariants[i]);
            }
            String userAnswer;
            do {
             userAnswer = scannerUtil.nextLine("Javobingizni kiriting (A, B, C, D, yoki testdan chiqish uchun (5) tugmasini bosing): ").toLowerCase();
            }while (!(userAnswer.equals("a")||userAnswer.equals("b")||userAnswer.equals("c")||userAnswer.equals("d")||userAnswer.equals("5")));

            userAnswer = switch (userAnswer) {
                case "a" -> String.valueOf(0);
                case "b" -> String.valueOf(1);
                case "c" -> String.valueOf(2);
                case "d" -> String.valueOf(3);
                default -> userAnswer;
            };

            for (int i = 0; i < 4; i++) {
                if (i == Integer.parseInt(userAnswer)) {
                    userAnswer = answerVariants[Integer.parseInt(userAnswer)];
                    break;
                }
            }

                attempts++;
            if (userAnswer.equals(correctAnswerEng)) {
                right++;
                System.out.println("To'g'ri javob! Tabriklaymiz!");
                System.out.println("attempts: "+attempts+ "  right: "+right+"   wrong: "+wrong);
            } else if (userAnswer.equals("5")) {
                System.out.println("Testni tark etdingiz. Dastur tugatiladi.");
                System.out.println("attempts: "+attempts+ "  right: "+right+"   wrong: "+wrong);
                return;
            } else {
                wrong++;
                System.out.println("Noto'g'ri javob. Yana bir bor urinib ko'ring.");
                System.out.println("attempts: "+attempts+ "  right: "+right+"   wrong: "+wrong);
            }
        } while (true);
    }

    private void spelling() {
        List<Dictionary> list = dictionaryRepository.dictionaryList();
        while (true) {
            Collections.shuffle(list);
            Dictionary dictionary1 = list.get(0);
            int id = dictionary1.getId();
            System.out.println(dictionary1.getWordUzb());
            String word = scannerUtil.nextLine("Write spelling in english : ");
            if (word.equals("0")) {
                return;
            }
            dictionaryService.spelling(id, word);
        }


    }

    private void deleteById() {
        Integer deletingId = scannerUtil.nextInt("Enter id: ");
        dictionaryService.deleteById(deletingId);
    }

    private void searching() {
        String searchingWord = scannerUtil.nextLine("Enter searching word: ");
        List<Dictionary> dictionaries = dictionaryRepository.searching(searchingWord);
        boolean result = dictionaryService.searching(searchingWord);
        if (!result) {
            dictionaries.forEach(System.out::println);
        } else {
            System.out.println("The word you are looking for was not found!!!");
        }
    }

    private void wordlist() {
        List<Dictionary> dictionaries = dictionaryRepository.dictionaryList();
        boolean result = dictionaryService.dictionaryList();
        if (!result) {
            dictionaries.forEach(System.out::println);
        } else {
            System.out.println("Word list empty!!!");
        }
    }

    private void addWord() {
        String wordEng = scannerUtil.nextLine("Enter word::");
        String wordUzb = scannerUtil.nextLine("Enter Translate:");
        String description = scannerUtil.nextLine("Enter description:");
        dictionaryService.addword(wordUzb, wordEng, description);
    }
    /*
Console + spring JDBC
** Menu **
    1. Add Word
    2. WordList List
    3. Multiple Choice
    4. Spelling
    5. Searching
    6. Delete by id
//
 1. Add word
    Enter word: Book
    Enter Translate: Kitob
    Enter description: I love reading book.

 2. WordList
    1. Book -> Kitob -> I love reading book.
 3. Multiple Choice
         Book
     a. ruchka
     b. kitob
     c. asaaa
     d. dasda
   Enter your answer (0 - stop):
 4. Spelling
     Kitob
  Entre translate: bok  ->
  Entre translate: book

 5.   Searching
        Enter word (including both)
*/

    private void showMenu() {
        System.out.print("""
                                ** Menu **
                    1. Add Word
                    2. WordList List
                    3. Multiple Choice
                    4. Spelling
                    5. Searching
                    6. Delete by id
                """);
    }

    private int getAction() {
        int option = scannerUtil.nextInt("Choose action: ");
        return option;
    }

}

