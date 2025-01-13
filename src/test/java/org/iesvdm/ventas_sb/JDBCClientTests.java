package org.iesvdm.ventas_sb;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.ventas_sb.dao.ClienteDAOJDBCClientImpl;
import org.iesvdm.ventas_sb.modelo.Cliente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    @Autowired
    private ClienteDAOJDBCClientImpl clienteDAOJDBCClientImpl;


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
        int id = 10;
        int rowsUpdated = jdbcClient.sql("DELETE FROM cliente WHERE id = ?")
                .param(id)
                .update();

        log.info("Delete de Cliente con {} registros actualizados.", rowsUpdated);

        assertEquals(1, rowsUpdated);
    }


    @Test
    void batch() {

    }

    @Test
    void getAll() {
        var query = """
				SELECT * FROM cliente
				""";
        RowMapper<Cliente> rowMapperCliente = (rs, rowNum) -> new Cliente(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getString("ciudad"),
                rs.getInt("categoría")
        );

        List<Cliente> listCli = jdbcClient.sql(query)
                .query(rowMapperCliente)
                .list();

        listCli.forEach(System.out::println);
    }

    @Test
    void findById() {
        int idToFind = 1;
        String query = """
                SELECT * FROM cliente WHERE ID = :id
                """;

        Optional<Cliente> optCliente = jdbcClient.sql(query)
                .param("id", idToFind)
                .query(Cliente.class)
                .optional();

        assertTrue(optCliente.isPresent());
        assertEquals(idToFind, optCliente.get().getId());

    }

    //A realizar por el alumno...
    @Test
    void findByNombre() {
        String nombre = "Adolfo";
        String query = """
                SELECT * FROM cliente WHERE nombre = :nombre
                """;

        Optional<Cliente> optCliente = jdbcClient.sql(query)
                .param("nombre", nombre)
                .query(Cliente.class)
                .optional();

        assertTrue(optCliente.isPresent());
        assertEquals(nombre, optCliente.get().getNombre());
    }

    @Test
    void findByNombreButNotFound() {
        String nombre = "Adolf";
        String query = """
                SELECT * FROM cliente WHERE nombre = :nombre
                """;

        Optional<Cliente> optCliente = jdbcClient.sql(query)
                .param("nombre", nombre)
                .query(Cliente.class)
                .optional();

        assertTrue(optCliente.isEmpty());
    }

    @Test
    void findClienteByCaracteristicaBetween() {
        int caracteristicaInit = 0;
        int característicaFin = 200;
        var query = jdbcClient.sql("""
                SELECT * FROM cliente WHERE categoria BETWEEN ? AND ?
                """).param(caracteristicaInit).param(característicaFin)
                .query(Cliente.class).list();

        assertTrue(!query.isEmpty());
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
