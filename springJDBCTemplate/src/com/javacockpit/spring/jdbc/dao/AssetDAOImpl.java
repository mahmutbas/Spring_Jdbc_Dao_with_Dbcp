package com.javacockpit.spring.jdbc.dao;

import com.javacockpit.spring.jdbc.bean.AssetPojo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by TCMBAS on 25.5.2016.
 */

public class AssetDAOImpl implements IAssetDAO, Serializable
{
    // private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    @Override
    public List<AssetPojo> getAssets()
    {
        return jdbcTemplate.query("SELECT * FROM assetdb.asset", new RowMapper<AssetPojo>()
        {
            @Override
            public AssetPojo mapRow(ResultSet rs, int i) throws SQLException
            {
                AssetPojo asset = new AssetPojo();
                asset.setAssetnum(rs.getString("assetnum"));
                asset.setItemnum(rs.getString("itemnum"));
                asset.setDescription(rs.getString("description"));
                asset.setLocation(rs.getString("location"));
                return asset;
            }
        });
    }

    @Override
    public AssetPojo getAsset(String assetnum)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("assetnum", assetnum);

        return jdbcTemplate.queryForObject("SELECT * FROM assetdb.asset WHERE assetnum= :assetnum", params,
                new RowMapper<AssetPojo>()
                {
                    @Override
                    public AssetPojo mapRow(ResultSet rs, int i) throws SQLException
                    {
                        AssetPojo asset = new AssetPojo();
                        asset.setAssetnum(rs.getString("assetnum"));
                        asset.setItemnum(rs.getString("itemnum"));
                        asset.setDescription(rs.getString("description"));
                        asset.setLocation(rs.getString("location"));
                        return asset;
                    }
                });
    }

    @Override
    public boolean delete(String assetnum)
    {
        MapSqlParameterSource params = new MapSqlParameterSource("assetnum", assetnum);
        return jdbcTemplate.update("DELETE FROM assetdb.asset WHERE assetnum= :assetnum", params) > 0;

    }

    @Override
    public boolean insert(AssetPojo assetItem)
    {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(assetItem);
        return jdbcTemplate.update("INSERT INTO assetdb.asset (assetnum,itemnum,description,location) VALUES (:assetnum,:itemnum,:description,:location)", params) == 1;
    }

    @Override
    public boolean update(AssetPojo assetItem)
    {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(assetItem);
        return jdbcTemplate.update("UPDATE assetdb.asset SET itemnum=:itemnum,description=:description,location=:location WHERE assetnum=:assetnum", params) == 1;
    }

    @Override
    @Transactional(transactionManager = "transectionManager")
    public int[] insert(List<AssetPojo> assetItems)
    {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(assetItems.toArray());
        String sql = "INSERT INTO assetdb.asset (assetnum,itemnum,description,location) VALUES (:assetnum,:itemnum,:description,:location)";
        return jdbcTemplate.batchUpdate(sql, params);
    }


}
