package org.iesvdm.ventas_sb;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.ventas_sb.dao.ComercialDAO;
import org.iesvdm.ventas_sb.dao.ComercialDAOJDBCImpl;
import org.iesvdm.ventas_sb.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class VentasSbApplication implements CommandLineRunner {

    @Autowired
    private ComercialDAOJDBCImpl comercialDAO;

    public static void main(String[] args) {
        SpringApplication.run(VentasSbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Arranca la aplicación");
        log.info("Prueba comercialDAO..");

        comercialDAO.getAll().forEach(comercial -> log.info("Comercial: {}", comercial));

        int id = 1;
        Optional<Comercial> comercial = comercialDAO.find(id);

        if (comercial.isPresent()) {
            log.info("Comercial {}: {}", id, comercial.get());

            String nombreOld = comercial.get().getNombre();

            comercial.get().setNombre("Javier Funciones");

            comercialDAO.update(comercial.get());

            log.info("Comercial actualizado. Nombre antiguo: {}, " +
                    "Nombre nuevo: {}", nombreOld, comercial.get().getNombre());
        } else {
            log.warn("No se encontró un comercial con ID: {}", id);
        }

        //Prueba con create
        Comercial comercial1 = Comercial.builder()
                .nombre("Antonio")
                .apellido1("Gonzalez")
                .apellido2("Gonzalez")
                .comision(1.5f)
                .build();

        comercialDAO.create(comercial1);
        log.info("Comercial creado con nombre: {}",comercial1.getNombre());
        String nombreBorrado = comercial1.getNombre();

        //Prueba borrado
        comercialDAO.delete(comercial1.getId());
        log.info("Comercial borrado con nombre : {}", nombreBorrado);

        log.info("Finaliza el método run");
    }



}

