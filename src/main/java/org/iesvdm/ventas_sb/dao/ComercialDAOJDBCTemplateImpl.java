package org.iesvdm.ventas_sb.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.ventas_sb.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ComercialDAOJDBCTemplateImpl implements ComercialDAO {


    //Plantilla jdbc inyectada automáticamente por el framework Spring, gracias a la anotación @Autowired.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserta en base de datos el nuevo Cliente, actualizando el id en el bean Cliente.
     */
    @Override
    public void create(Comercial comercial) {

        //Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
        String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comision) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;


        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            int idx = 1;
            ps.setString(idx++, comercial.getNombre());
            ps.setString(idx++, comercial.getApellido1());
            ps.setString(idx++, comercial.getApellido2());
            ps.setFloat(idx++, comercial.getComision());
            return ps;
        };

        //Con recuperación de id generado
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(psc, keyHolder);

        comercial.setId(keyHolder.getKey().intValue());

        log.info("Insertados {} registros.", rows);
    }

    /**
     * Devuelve lista con todos los Clientes.
     */
    @Override
    public List<Comercial> getAll() {

        RowMapper<Comercial> rowMapperComercial = (rs, rowNum) -> Comercial.builder()
                .nombre(rs.getString("nombre"))
                .apellido1(rs.getString("apellido1"))
                .apellido2(rs.getString("apellido2"))
                .comision(rs.getInt("comision"))
                .build();

        List<Comercial> listCom = jdbcTemplate.query(
                "SELECT * FROM cliente",
                rowMapperComercial
        );

        log.info("Devueltos {} registros.", listCom.size());

        return listCom;

    }

    /**
     * Devuelve Optional de Cliente con el ID dado.
     */
    @Override
    public Optional<Comercial> find(int id) {

        ResultSetExtractor<Comercial> rse = (ResultSet rs) -> {
            if (rs.next()) {
                return Comercial.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido1(rs.getString("apellido1"))
                        .apellido2(rs.getString("apellido2"))
                        .comision(rs.getInt("comision"))
                        .build();
            } else {
                return null;
            }
        };

        Comercial com =  jdbcTemplate
                .query(
                        "SELECT * FROM cliente WHERE id = ?",
                        rse,
                        id
                );

        return Optional.ofNullable(com);
    }
    /**
     * Actualiza Cliente con campos del bean Cliente según ID del mismo.
     */
    @Override
    public void update(Comercial comercial) {

        int rows = jdbcTemplate.update("""
										UPDATE comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comercial = ?, 
												WHERE id = ?
										""", comercial.getNombre()
                , comercial.getApellido1()
                , comercial.getApellido2()
                , comercial.getComision()
                , comercial.getId());

        log.info("Update de comercial con {} registros actualizados.", rows);

    }

    /**
     * Borra comercial con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

        log.info("Delete de comercial con {} registros eliminados.", rows);

    }
}
