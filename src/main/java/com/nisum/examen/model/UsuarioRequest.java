package com.nisum.examen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

	@Id
	private String id;
	
    private String nombre;
    private String correo;
    private String contrasena;

   
}
