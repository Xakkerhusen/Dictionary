package org.example.repositry;

import org.example.dto.Dictionary;
import org.example.dto.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DictionaryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Respons addWord(String wordUzb, String wordEng, String description) {
        int res;
        String sql = "insert into dictionary(word_eng, word_uzb, description) values (?,?,?) ";
        res = jdbcTemplate.update(sql,wordUzb,wordEng,description);
        if (res != 0) {
            return new Respons("Word added 👌👌👌", true);
        }
        return new Respons("Error added ❌!!!", false);
    }

    public List<Dictionary> dictionaryList() {
        String sql = "select * from dictionary ";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Dictionary.class));
    }

    public List<Dictionary> searching(String searchingWord) {
        List<Dictionary> dictionaries;
        String sql="SELECT * FROM dictionary WHERE dictionary.word_eng LIKE ? or dictionary.word_uzb LIKE ?";
        dictionaries=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Dictionary.class),searchingWord,searchingWord);
        return dictionaries;
    }
    public Respons delete(Integer deletingId) {
        int res;
       res= jdbcTemplate.update("DELETE FROM dictionary WHERE id = ?", deletingId);
        if (res != 0) {
            return new Respons("Word deleted 👌👌👌", true);
        }
        return new Respons("Error deleted❌!!!", false);
    }

}
