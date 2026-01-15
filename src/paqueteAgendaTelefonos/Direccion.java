package paqueteAgendaTelefonos;

public class Direccion {
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
