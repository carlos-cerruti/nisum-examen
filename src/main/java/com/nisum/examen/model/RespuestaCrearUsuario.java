package com.nisum.examen.model;

import java.util.Date;

public class RespuestaCrearUsuario {

    private final int codigo;
    private final String userId;
    private final Date created;
    private final Date modified;
    private final Date lastLogin;
    private final String token;
    private final boolean isActive;
	private int status;
	private String mensaje;

    public RespuestaCrearUsuario(int codigo, String userId, Date created, Date modified, Date lastLogin, String token, boolean b) {
        this.codigo = codigo;
        this.userId = userId;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = b;
    }

    public RespuestaCrearUsuario(int status, String mensaje) {
        this.setStatus(status);
        this.setMensaje(mensaje);
		this.codigo = 0;
		this.userId = "";
		this.created = null;
		this.modified = null;
		this.lastLogin = null;
		this.token = "";

		this.isActive = false;
    }
    public int getCodigo() {
        return codigo;
    }

    public String getUserId() {
        return userId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

