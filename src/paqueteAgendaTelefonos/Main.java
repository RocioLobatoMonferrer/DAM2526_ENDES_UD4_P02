package paqueteAgendaTelefonos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Agenda agenda = new Agenda();
		int opcion = -1;

		do {
			mostrarMenu();
			opcion = leerEntero(sc, "Opción: ");

			if (opcion == 1) {
				Contacto nuevo = crearContacto(sc, agenda.getSiguienteId());
				agenda.agregarContacto(nuevo);
				System.out.println("Contacto añadido con ID " + nuevo.getId());

			} else if (opcion == 2) {
				List<Contacto> contactos = agenda.listarContactos();
				if (contactos.isEmpty()) {
					System.out.println("La agenda está vacía.");
				} else {
					for (Contacto c : contactos) {
						System.out.println(c);
						System.out.println("---------------------------------");
					}
				}

			} else if (opcion == 3) {
				String texto = leerTextoNoVacio(sc, "Buscar por nombre/apellidos: ");
				List<Contacto> resultados = agenda.buscarPorNombre(texto);

				if (resultados.isEmpty()) {
					System.out.println("No se encontraron contactos.");
				} else {
					for (Contacto c : resultados) {
						System.out.println(c);
						System.out.println("---------------------------------");
					}
				}

			} else if (opcion == 4) {
				int id = leerEntero(sc, "ID del contacto a borrar: ");
				boolean borrado = agenda.eliminarContactoPorId(id);
				if (borrado) {
					System.out.println("Contacto borrado.");
				} else {
					System.out.println("No existe un contacto con ese ID.");
				}

			} else if (opcion == 5) {
				int id = leerEntero(sc, "ID del contacto al que añadir teléfono: ");
				Contacto c = agenda.obtenerPorId(id);

				if (c == null) {
					System.out.println("No existe un contacto con ese ID.");
				} else {
					Telefono t = crearTelefono(sc);
					c.agregarTelefono(t);
					System.out.println("Teléfono añadido.");
				}

			} else if (opcion == 0) {
				System.out.println("Saliendo...");
			} else {
				System.out.println("Opción no válida.");
			}

		} while (opcion != 0);

		sc.close();
	}

	private static void mostrarMenu() {
		System.out.println("\n====== AGENDA (Consola) ======");
		System.out.println("1) Añadir contacto");
		System.out.println("2) Listar contactos");
		System.out.println("3) Buscar contacto");
		System.out.println("4) Borrar contacto por ID");
		System.out.println("5) Añadir teléfono a contacto");
		System.out.println("0) Salir");
		System.out.println("==============================");
	}

	// ---------- Creación de objetos desde consola ----------

	private static Contacto crearContacto(Scanner sc, int id) {
		String nombre = leerTextoNoVacio(sc, "Nombre: ");
		String apellidos = leerTextoNoVacio(sc, "Apellidos: ");
		String email = leerTexto(sc, "Email (opcional): ");

		Direccion direccion = crearDireccion(sc);

		Contacto c = new Contacto(id, nombre, apellidos, email, direccion);

		int cuantos = leerEntero(sc, "¿Cuántos teléfonos quieres añadir ahora? (0..n): ");
		int i = 0;
		while (i < cuantos) {
			Telefono t = crearTelefono(sc);
			c.agregarTelefono(t);
			i++;
		}

		return c;
	}

	private static Direccion crearDireccion(Scanner sc) {
		System.out.println("\n--- Dirección ---");

		TipoVia tipoVia = elegirTipoVia(sc);

		int numero = leerEntero(sc, "Número: ");

		String bloque = leerTexto(sc, "Bloque (opcional): ");
		String escalera = leerTexto(sc, "Escalera (opcional): ");
		String portal = leerTexto(sc, "Portal (opcional): ");
		String letra = leerTexto(sc, "Letra (opcional): ");

		Direccion d = new Direccion(tipoVia, numero, bloque, escalera, portal, letra);
		return d;
	}

	private static Telefono crearTelefono(Scanner sc) {
		System.out.println("\n--- Teléfono ---");

		String numero = leerTextoNoVacio(sc, "Número de teléfono: ");
		TipoTelefono tipo = elegirTipoTelefono(sc);

		Telefono t = new Telefono(numero, tipo);
		return t;
	}

	private static TipoVia elegirTipoVia(Scanner sc) {
		System.out.println("Tipo de vía:");
		TipoVia[] valores = TipoVia.values();

		int i = 0;
		while (i < valores.length) {
			System.out.println((i + 1) + ") " + valores[i]);
			i++;
		}

		int opcion = leerEnteroRango(sc, "Elige tipo (1-" + valores.length + "): ", 1, valores.length);
		return valores[opcion - 1];
	}

	private static TipoTelefono elegirTipoTelefono(Scanner sc) {
		System.out.println("Tipo de teléfono:");
		TipoTelefono[] valores = TipoTelefono.values();

		int i = 0;
		while (i < valores.length) {
			System.out.println((i + 1) + ") " + valores[i]);
			i++;
		}

		int opcion = leerEnteroRango(sc, "Elige tipo (1-" + valores.length + "): ", 1, valores.length);
		return valores[opcion - 1];
	}

	// ---------- Utilidades de lectura (consola) ----------

	private static String leerTexto(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		String texto = sc.nextLine();
		return texto.trim();
	}

	private static String leerTextoNoVacio(Scanner sc, String mensaje) {
		String texto = "";
		while (texto.isBlank()) {
			System.out.print(mensaje);
			texto = sc.nextLine();
			texto = texto.trim();
			if (texto.isBlank()) {
				System.out.println("ERROR - No puede estar vacío.");
			}
		}
		return texto;
	}

	private static int leerEntero(Scanner sc, String mensaje) {
		int numero = 0;
		boolean ok = false;

		while (!ok) {
			System.out.print(mensaje);
			String texto = sc.nextLine();
			texto = texto.trim();

			try {
				numero = Integer.parseInt(texto);
				ok = true;
			} catch (NumberFormatException e) {
				System.out.println("ERROR - Introduce un número entero válido.");
			}
		}
		return numero;
	}

	private static int leerEnteroRango(Scanner sc, String mensaje, int min, int max) {
		int numero = leerEntero(sc, mensaje);
		while (numero < min || numero > max) {
			System.out.println("ERROR - Debe estar entre " + min + " y " + max + ".");
			numero = leerEntero(sc, mensaje);
		}
		return numero;
	}
}

class Agenda {
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

class Contacto {
	private int id;
	private String nombre;
	private String apellidos;
	private String email;
	private Direccion direccion;
	private List<Telefono> telefonos;

	public Contacto(int id, String nombre, String apellidos, String email, Direccion direccion) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.direccion = direccion;
		this.telefonos = new ArrayList<>();
	}

	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void agregarTelefono(Telefono t) {
		this.telefonos.add(t);
	}

	public List<Telefono> getTelefonos() {
		return new ArrayList<>(this.telefonos);
	}

	public Direccion getDireccion() {
		return this.direccion;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("ID: ").append(this.id).append("\n");
		sb.append("Nombre: ").append(this.nombre).append(" ").append(this.apellidos).append("\n");

		if (!this.email.isBlank()) {
			sb.append("Email: ").append(this.email).append("\n");
		}

		sb.append("Dirección: ").append(this.direccion).append("\n");

		sb.append("Teléfonos:\n");
		if (this.telefonos.isEmpty()) {
			sb.append("  (sin teléfonos)\n");
		} else {
			for (Telefono t : this.telefonos) {
				sb.append("  - ").append(t).append("\n");
			}
		}

		return sb.toString();
	}
}

class Direccion {
	private TipoVia tipoVia;
	private int numero;
	private String bloque;
	private String escalera;
	private String portal;
	private String letra;

	public Direccion(TipoVia tipoVia, int numero, String bloque, String escalera, String portal, String letra) {
		this.tipoVia = tipoVia;
		this.numero = numero;
		this.bloque = bloque;
		this.escalera = escalera;
		this.portal = portal;
		this.letra = letra;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.tipoVia).append(" ").append(this.numero);

		if (!this.bloque.isBlank()) {
			sb.append(", Bloque ").append(this.bloque);
		}
		if (!this.escalera.isBlank()) {
			sb.append(", Esc. ").append(this.escalera);
		}
		if (!this.portal.isBlank()) {
			sb.append(", Portal ").append(this.portal);
		}
		if (!this.letra.isBlank()) {
			sb.append(", ").append(this.letra);
		}

		return sb.toString();
	}
}

class Telefono {
	private String numero;
	private TipoTelefono tipo;

	public Telefono(String numero, TipoTelefono tipo) {
		this.numero = numero;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return this.tipo + ": " + this.numero;
	}
}
