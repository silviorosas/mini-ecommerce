package com.mini_ecommerce.pagos_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pagos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long ordenId;
  private String metodoPago;
  private BigDecimal monto;
  private String estado; // EJ: APROBADO, RECHAZADO
}
