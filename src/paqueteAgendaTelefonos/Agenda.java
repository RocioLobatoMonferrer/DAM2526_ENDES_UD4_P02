package paqueteAgendaTelefonos;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
	private List<Contacto> contactos;
	private int siguienteId;

	public Agenda() {
		this.contactos = new ArrayList<>();
		this.siguienteId = 1;
	}

	public int getSiguienteId() {
		int id = this.siguienteId;
		this.siguienteId = this.siguienteId + 1;
		return id;
	}

	public void agregarContacto(Contacto c) {
		this.contactos.add(c);
	}

	public List<Contacto> listarContactos() {
		return new ArrayList<>(this.contactos);
	}

	public Contacto obtenerPorId(int id) {
		for (Contacto c : this.contactos) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	public boolean eliminarContactoPorId(int id) {
		int i = 0;
		while (i < this.contactos.size()) {
			if (this.contactos.get(i).getId() == id) {
				this.contactos.remove(i);
				return true;
			}
			i++;
		}
		return false;
	}

	public List<Contacto> buscarPorNombre(String texto) {
		String t = texto.toLowerCase();
		List<Contacto> resultados = new ArrayList<>();

		for (Contacto c : this.contactos) {
			String nombreCompleto = (c.getNombre() + " " + c.getApellidos()).toLowerCase();
			if (nombreCompleto.contains(t)) {
				resultados.add(c);
			}
		}
		return resultados;
	}
}
