package com.group.fruitshopapp.repository.jdbcRepo;

import com.group.fruitshopapp.dto.request.FruitCreateRequest;
import com.group.fruitshopapp.dto.request.FruitGetStatResponse;
import com.group.fruitshopapp.dto.request.FruitUpdateRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Primary
@Repository
public class FruitMySqlRepository implements FruitJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createFruit(FruitCreateRequest request) {
        String sql = "INSERT INTO fruit(name, warehousingDate, price) VALUES (?,?,?)";
        jdbcTemplate.update(sql, request.getName(), request.getWarehousingDate(), request.getPrice());
    }

    @Override
    public void updateFruit(FruitUpdateRequest request) {
        // 해당 id가 fruit 테이블 안에 존재하는지 검색하고 없다면 IllegalArgumentException 예외를 발생시킴
        String sqlUpdate = "UPDATE fruit SET isSold = True WHERE id = ?";
        jdbcTemplate.update(sqlUpdate, request.getId());
    }


    @Override
    public FruitGetStatResponse getStatOfFruit(String name) {
        String sql = "SELECT isSold, SUM(price) as SUM from fruit WHERE name = ? GROUP BY isSold";
        Map<Boolean, Long> resultmap = new HashMap<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            boolean isSold = rs.getBoolean("isSold");
            long price = rs.getLong("SUM");
            resultmap.put(isSold, price);
            return null;
        }, name);
        return new FruitGetStatResponse(resultmap.get(true), resultmap.get(false));
    }

    @Override
    public boolean isFruitNotExist(FruitUpdateRequest request) {
        String sqlRead = "SELECT * FROM fruit WHERE id = ?";
        return jdbcTemplate.query(sqlRead, (rs, rowNum) -> 0, request.getId())
                .isEmpty();
    }
    @Override
    public boolean isFruitNotExist(String name) {
        String sqlRead = "SELECT * FROM fruit WHERE name = ?";
        return jdbcTemplate.query(sqlRead, (rs, rowNum) -> 0, name)
                .isEmpty();
    }


}