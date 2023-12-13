package org.example.service;

import org.example.dto.Dictionary;
import org.example.dto.Respons;
import org.example.repositry.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryService {
    @Autowired
    private DictionaryRepository dictionaryRepository;


    public void addword(String wordUzb, String wordEng, String description) {
        Respons respons = dictionaryRepository.addWord(wordUzb, wordEng, description);
        if (wordUzb.trim().isEmpty() || wordEng.trim().isEmpty()) {
            System.out.println("Enter word!!!");
        }else if (respons.success()) {
            System.out.println("Words added 👌👌👌");
        } else {
            System.out.println("Words not added ❌");
        }
    }

    public boolean dictionaryList() {
        List<Dictionary> dictionaries = dictionaryRepository.dictionaryList();
        return dictionaries.isEmpty();
    }

    public boolean searching(String searchingWord) {
        List<Dictionary> searching = dictionaryRepository.searching(searchingWord);
        return searching.isEmpty();
    }

    public void deleteById(Integer deletingId) {
        Respons delete = dictionaryRepository.delete(deletingId);
        if (delete.success()) {
            System.out.println(delete.massage());
        }else {
            System.out.println(delete.massage());
        }
    }
}
