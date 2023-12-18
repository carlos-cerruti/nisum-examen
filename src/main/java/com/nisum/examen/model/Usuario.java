package com.nisum.examen.model;
import lombok.*;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	
	 @Value("${jwt.secret}")
	    private String secretKey;

	   private static final long EXPIRATION_TOKEN = 3600000;
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private List<Telefono> telefonos;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private Date lastLogin;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private boolean isActive;

    public Usuario(String nombre, String correo, String contrasena) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.created = new Date();
        this.modified = new Date();
        this.lastLogin = new Date();
        this.isActive = true;
        // Generar y asignar el token en el momento de la creación
        this.token = generarToken(id);
    }

    public String getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getToken() {
        return token;
    }

    public boolean isActive() {
        return isActive;
    }

    // Actualizar el token cuando sea necesario
    public void actualizarToken() {
        this.token = generarToken(id);
    }
    
    public String generarToken(String id) {
    	 return Jwts.builder()
                 .setSubject(id)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                 .signWith(SignatureAlgorithm.HS256, secretKey)
                 .compact();
    
    }


    }

