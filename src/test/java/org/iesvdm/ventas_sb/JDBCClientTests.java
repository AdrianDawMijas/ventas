package org.iesvdm.ventas_sb;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.ventas_sb.modelo.Cliente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JDBCClientTests {

    @Autowired
    JdbcClient jdbcClient;

    Cliente cli1 = Cliente.builder()
            .nombre("Jose M.")
            .apellido1("Martín")
            .apellido2("Tejero")
            .ciudad("Málaga")
            .categoria(1)
            .build();

    Cliente cli2 = Cliente.builder()
            .id(5)
            .nombre("María")
            .apellido1("Pérez")
            .apellido2("García")
            .ciudad("Granada")
            .categoria(2)
            .build();

    Cliente cli3 = Cliente.builder()
            .nombre("Javier")
            .apellido1("Gutiérrez")
            .apellido2("Martínez")
            .ciudad("Málaga")
            .categoria(3)
            .build();


    @Test
    void insertWithoutIDRecoveryTest() {
        int rows = jdbcClient.sql("""
							                  INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoria) 
							                  VALUES  (     ?,         ?,         ?,       ?,         ?)
						                   """)
                .param(cli1.getNombre())
                .param(cli1.getApellido1())
                .param(cli1.getApellido2())
                .param(cli1.getCiudad())
                .param(cli1.getCategoria())
                .update();

        assertEquals(1, rows);
        assertTrue(cli1.getId() == null);
    }

    @Test
    void insertWithIDRecoveryTest() {

    }

    @Test
    void update() {
        cli2.setCategoria(5);
        String query = """
										UPDATE cliente 
										SET categoria = :categoria 
										WHERE id = :id
										""";
        int rowsUpdated = jdbcClient.sql(query).paramSource(cli2).update();

        log.info("Update de Cliente con {} registros actualizados.", rowsUpdated);

        assertEquals(1, rowsUpdated);
    }


    @Test
    void delete() {

    }


    @Test
    void batch() {

    }

    @Test
    void getAll() {

    }

    @Test
    void findById() {
        int idToFind = 1;
        //TODO

    }

    //A realizar por el alumno...
    @Test
    void findByNombre() {
        String nombre = "";
        //TODO
    }

    @Test
    void findByNombreButNotFound() {
        String nombre = "";
        //TODO
    }

    @Test
    void findClienteByCaracteristicaBetween() {
        int característicaInit = 0;
        int característicaFin = 0;
        //TODO
    }

    void findClienteByNombreContainingAndApellido1Containing() {
        String nombreContaining = "";
        String apellido1Containing = "";
        //TODO


    }

    void findClienteByNombreContainingAndApellido1ContainingButNotFound() {
        String nombreContaining = "";
        String apellido1Containing = "";
        //TODO


    }

    void findPedidosWithClienteAndComercialByCliente_id() {
        int clienteId = 0;
        //TODO
    }

    void insertNewClienteAndPedido() {
        //
    }

}
