package Mercadeo.Droid;

public class Calzado {

	
		private Integer id;
		private String tienda;
		private Integer base;
		private Integer material;
		private Integer tipo;
		private Integer precio;
		

		public void setId(int int1) {
			this.id=int1;
			
		}

		public void setTienda(String string) {
			this.tienda=string;
			
		}
		
		public void setBase(int int1) {
			this.base=int1;
			
		}

		public void setMaterial(int int1) {
			this.material=int1;
			
		}

		public void setTipo(int int1) {
			this.tipo=int1;
			
		}

		public void setPrecio(int int1) {
			this.precio=int1;
			
		}
		
		public Integer getId() {
			
			return this.id;
		}
		
		public Integer getBase() {
			
			return this.base;
		}
		
		public String getTienda()
		{
			return this.tienda;
		}
		
		public Integer getMaterial() {
	
			return this.material;
		}
		public Integer getTipo() {
	
			return this.tipo;
		}
		public Integer getPrecio() {
	
			return this.precio;
		}
		
		// here goes the fields getters and setters
		// ...
	}
