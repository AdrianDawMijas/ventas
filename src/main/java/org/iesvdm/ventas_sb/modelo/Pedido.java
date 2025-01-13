package org.iesvdm.ventas_sb.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    private int id;
    private double total;
    private String fecha;
    private Cliente cliente;
    private Comercial comercial;
}
