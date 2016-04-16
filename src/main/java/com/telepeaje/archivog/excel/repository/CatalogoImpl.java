package com.telepeaje.archivog.excel.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author abel
 */
@Repository
@Transactional
public class CatalogoImpl implements ICatalogo {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> catalogo(String tag, String fecha) {
        String query = "SELECT B.SERIE, B.SEQ_FOLIO AS FOLIO, B.DT_INVOICE_DATE_TS AS FECHA, A.AMOUNT_CHARGED , A.ID_TAG, A.DT_TRANS_DATE_TS, nombre_plaza(A.ID_PLACES) "
                + "  FROM    AMS_ITEMS_TO_INVOICE A "
                + "       INNER JOIN "
                + "          AMS_INVOICE B "
                + "       ON A.ID_INVOICE = B.ID_INVOICE "
                + " where A.ID_TAG = ? "
                + " and A.DT_TRANS_DATE_TS = TO_DATE(?,'DD/MM/RRRR HH24:MI:SS') "
                + " order by A.ID_TAG, A.DT_TRANS_DATE_TS   ";
        return (List<String>) jdbcTemplate.query(query,new Object[]{tag,fecha}, new ResultSetExtractor() {

            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> lst = new ArrayList<String>();
                while (rs.next()) {
                    lst.add(rs.getString("AMOUNT_CHARGED"));
                }
                return lst;
            }
        });
    }

    @Autowired
    @Qualifier(value = "tedisaDS")
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
