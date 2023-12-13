package org.example.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBase {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void creatTable() {
        String sql = "create table if not exists dictionary(" +
                "id serial primary key ," +
                "word_eng varchar(15)," +
                "word_uzb varchar(15)," +
                "description varchar" +
                ")";
        jdbcTemplate.execute(sql);
    }


}
