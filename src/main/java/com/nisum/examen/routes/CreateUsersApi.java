package com.nisum.examen.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nisum.examen.model.RespuestaCrearUsuario;
import com.nisum.examen.model.UsuarioRequest;
import com.nisum.examen.services.UsuarioService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class CreateUsersApi extends RouteBuilder {

	@Autowired
	 UsuarioService usuarioService =  new UsuarioService();
	
	
	
    @Override
    public void configure() throws Exception {
    	
    	from("jetty:http://0.0.0.0:9155/api/createUser?httpMethodRestrict=POST")
        .routeId("nisumCreateUser")
        .log("Solicitud: ${body}")
        .unmarshal().json(JsonLibrary.Jackson, UsuarioRequest.class )
        .choice()
        .when(simple("${body.nombre} == null || ${body.correo} == null || ${body.contrasena} == null"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .setBody(simple("Bad Request: Se requieren nombre, correo y contraseña"))
            .stop()
        .when(simple("${body.correo} not regex '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}'"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .setBody(simple("Bad Request: Formato de correo inválido"))
            .stop()
    .end()
        //validar jwt
        .process(exchange -> {
            String token = exchange.getIn().getHeader("Authorization", String.class).replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("test") 
                        .parseClaimsJws(token)
                        .getBody();
            
                // Si la validación es exitosa, continúa con el proceso de creación de usuario
                exchange.getIn().setHeader("userId", claims.get("userId", String.class));
                
                
                
                
            } catch (Exception e) {
                exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401));
                exchange.getIn().setBody("Unauthorized: Token invalid or expired");
                return;
            }

            })
        //crear usuario
        .bean(usuarioService, "crearUsuario(${header.userId}, ${body})")
        .choice()
        .when(body().isInstanceOf(RespuestaCrearUsuario.class))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
        .otherwise()
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .setBody(simple("Internal Server Error"))
      
    .end();
    }
    
}


