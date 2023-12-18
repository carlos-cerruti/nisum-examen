package com.nisum.examen.services;

import com.nisum.examen.model.RespuestaCrearUsuario;
import com.nisum.examen.model.Usuario;
import com.nisum.examen.model.UsuarioRequest;
import com.nisum.examen.interfaces.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RespuestaCrearUsuario crearUsuario(String userId, UsuarioRequest usuarioRequest) {
        // Validar si el correo ya existe en la base de datos
        if (usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())) {
            // Retornar un error si el correo ya está registrado
            return new RespuestaCrearUsuario(400, "El correo ya está registrado");
        }

        Usuario usuario = new Usuario(
                UUID.randomUUID().toString(),
                usuarioRequest.getNombre(),
                usuarioRequest.getCorreo(),
                usuarioRequest.getContrasena(),
                null, 
                new Date(),
                new Date(),
                new Date(),
                userId, 
                true 
        );

        // Persistir el usuario en la base de datos
        usuarioRepository.save(usuario);

        return new RespuestaCrearUsuario(
                201,
                usuario.getId(),
                usuario.getCreated(),
                usuario.getModified(),
                usuario.getLastLogin(),
                usuario.getToken(),
                usuario.isActive()
        );
    }
}
