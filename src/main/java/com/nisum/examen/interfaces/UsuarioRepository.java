package com.nisum.examen.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nisum.examen.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	 boolean existsByCorreo(String correo);

  
	default void guardarUsuario(Usuario usuario) {
        save(usuario);
    }
}
