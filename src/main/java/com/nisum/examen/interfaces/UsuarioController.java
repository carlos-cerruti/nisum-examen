package com.nisum.examen.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.examen.model.Usuario;

@RestController
@RequestMapping("/api/usuarios")
@Api(tags = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

	@Autowired
    private UsuarioRepository usuarioRepository;
	
    @GetMapping("/obtener")
    @ApiOperation(value = "Obtener todos los usuarios", response = Usuario.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito al obtener usuarios"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public List<Usuario> obtenerUsuarios() {
    	return usuarioRepository.findAll();
        
    }

    

}
