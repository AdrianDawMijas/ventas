package org.iesvdm.ventas_sb;



import org.iesvdm.ventas_sb.dao.ClienteDAOJDBCClientImpl;
import org.iesvdm.ventas_sb.dao.ClienteDAOJDBCTemplateImpl;
import org.iesvdm.ventas_sb.modelo.Cliente;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VentasSbApplicationTests {

    @Autowired
    ClienteDAOJDBCTemplateImpl cliDAOTemplate;
    
    @Autowired
    ClienteDAOJDBCClientImpl cliDAOClient;

    @Order(1)
    @Test
    void contextLoadsCreate() {

        //getAll
        List<Cliente> listCli = cliDAOTemplate.getAll();
        assertEquals(10, listCli.size());

        //Create
        Cliente cliente1 = Cliente.builder()
                .nombre("Jose M.")
                .apellido1("Martín")
                .apellido2("Tejero")
                .ciudad("Málaga")
                .categoría(1)
                .build();

        cliDAOTemplate.create(cliente1);

        Cliente cliente2 = Cliente.builder()
                .nombre("María")
                .apellido1("Pérez")
                .apellido2("García")
                .ciudad("Granada")
                .categoría(2)
                .build();

        cliDAOClient.create(cliente2);

        //getAll
        listCli = cliDAOClient.getAll();
        assertEquals(12, listCli.size());

        //find
        Optional<Cliente> optCliAux1 = cliDAOTemplate.find(cliente1.getId());
        assertTrue(optCliAux1.isPresent());
        assertEquals(cliente1, optCliAux1.get());

        Optional<Cliente> optCliAux2 = cliDAOClient.find(cliente2.getId());
        assertTrue(optCliAux2.isPresent());
        assertEquals(cliente2, optCliAux2.get());

        //update
        Cliente cliAux1 = optCliAux1.get();
        cliAux1.setNombre("José Manuel");
        //...
        cliDAOTemplate.update(cliAux1);
        optCliAux1 = cliDAOTemplate.find(cliAux1.getId());
        assertTrue(optCliAux1.isPresent());
        assertEquals(cliAux1, optCliAux1.get());

        Cliente cliAux2 = optCliAux2.get();
        cliAux2.setNombre("María del Carmen");
        //...
        cliDAOClient.update(cliAux2);
        optCliAux2 = cliDAOClient.find(cliAux2.getId());
        assertTrue(optCliAux2.isPresent());
        assertEquals(cliAux2, optCliAux2.get());

        //delete
        cliDAOTemplate.delete(cliAux1.getId());
        optCliAux1 = cliDAOTemplate.find(cliAux1.getId());
        assertTrue(optCliAux1.isEmpty());

        cliDAOClient.delete(cliAux2.getId());
        optCliAux2 = cliDAOClient.find(cliAux2.getId());
        assertTrue(optCliAux2.isEmpty());

    }

    @Order(2)
    @Test
    void resultSetFunctionalInterfaces() {


    }

}