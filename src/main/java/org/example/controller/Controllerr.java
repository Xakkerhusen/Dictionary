package org.example.controller;

import org.example.db.DataBase;
import org.example.dto.Dictionary;
import org.example.repositry.DictionaryRepository;
import org.example.service.DictionaryService;
import org.example.utils.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

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
//                case 3 -> multipleChoice();
//                case 4 -> spelling();
                case 5 -> searching();
                case 6 -> deleteById();
            }

        } while (true);

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

