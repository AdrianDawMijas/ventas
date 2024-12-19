CREATE TABLE IF NOT EXISTS cliente (
                         id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido1 VARCHAR(100) NOT NULL,
                         apellido2 VARCHAR(100),
                         ciudad VARCHAR(100),
                         categoría INT UNSIGNED
);

CREATE TABLE IF NOT EXISTS comercial (
                           id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           apellido1 VARCHAR(100) NOT NULL,
                           apellido2 VARCHAR(100),
                           comisión FLOAT
);

CREATE TABLE IF NOT EXISTS pedido (
                        id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                        total DOUBLE NOT NULL,
                        fecha DATE,
                        id_cliente INT UNSIGNED NOT NULL,
                        id_comercial INT UNSIGNED NOT NULL,
                        FOREIGN KEY (id_cliente) REFERENCES cliente(id),
                        FOREIGN KEY (id_comercial) REFERENCES comercial(id)

);