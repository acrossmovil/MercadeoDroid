package Mercadeo.Droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class  MercadeoDroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// Con esto, eliminamos la barra de título que tienen las aplicaciones por defecto
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	 
    	// Con esto, eliminamos la barra de tareas de Android
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      CargaCombox();
      
    }
    
    private void CargaCombox()
    {
    	 Spinner spinner_base = (Spinner) findViewById(R.id.spinner1);
         ArrayAdapter spinner_adapter1 = ArrayAdapter.createFromResource( this, R.array.tipos , android.R.layout.simple_spinner_item);
         spinner_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner_base.setAdapter(spinner_adapter1);
         
         Spinner spinner_material = (Spinner) findViewById(R.id.spinner2);
         ArrayAdapter spinner_adapter2 = ArrayAdapter.createFromResource( this, R.array.material , android.R.layout.simple_spinner_item);
         spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner_material.setAdapter(spinner_adapter2);
         
         
         Spinner spinner_tipo = (Spinner) findViewById(R.id.spinner3);
         ArrayAdapter spinner_adapter3 = ArrayAdapter.createFromResource( this, R.array.base , android.R.layout.simple_spinner_item);
         spinner_adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner_tipo.setAdapter(spinner_adapter3);
         
    }
    
    	// This method is called at button click because we assigned the name to the
 		// "On Click property" of the button
 		@SuppressWarnings("null")
		public void myClickHandler(View view) {
 			 
 			
 			Calzado oBjsCalzado = getData();
 				
 			
 					
 			//oBjsCalzado.setTienda(((EditText)findViewById(R.id.editText1)).getText());
 			
 			if(DBInsertData(oBjsCalzado))
 				{
 					String strId = oBjsCalzado.getId().toString();
 					Intent i = new Intent(this,CameraDroidActivity.class);
 					Bundle bundle = new Bundle(); //creamos el bunde 
 					bundle.putString("Id",strId); //definimos una clave y un valor para la misma. 
 					i.putExtras(bundle); //lo añadimos al intent
 					this.startActivityForResult(i, 0);
 					
 				}
 			else
 				{
 			
 				}
 					
 		}
 		
 		
 	
 		private Calzado getData()
 		{
 			CalzadoSQLiteHelper usdbh =
 	 	            new CalzadoSQLiteHelper(this, "DBMercadeo", null, 1);
 			//se recopilan los dsatos del fromulario
 			EditText edit = (EditText) findViewById(R.id.editText1);
 			Spinner spinner_base = (Spinner) findViewById(R.id.spinner1);
 			Spinner spinner_material = (Spinner) findViewById(R.id.spinner2);
 			Spinner spinner_tipo = (Spinner) findViewById(R.id.spinner3);
 			EditText edit2 = (EditText) findViewById(R.id.editText2);
 			
 			Calzado retorno = new Calzado();
 			retorno.setTienda(edit.getText().toString());
 			retorno.setBase(spinner_base.getSelectedItemPosition());
 			retorno.setMaterial(spinner_material.getSelectedItemPosition());
 			retorno.setTipo(spinner_tipo.getSelectedItemPosition());
 			retorno.setPrecio(Integer.parseInt(edit2.getText().toString()));
 			
 			 SQLiteDatabase db = usdbh.getWritableDatabase();
	         	 
				//Consulta del ultimo Id
								
				String query = "select  * from Mercadeo order by id desc limit 1";
			 
				Cursor c = db.rawQuery (query, null);
				
				//Nos aseguramos de que existe al menos un registro
				if (c.moveToFirst()) {
				     //Recorremos el cursor hasta que no haya más registros
				    
					retorno.setId(c.getInt(0) + 1);
								          
				}
				else
				{
					retorno.setId(0);
				}
 		
 			return retorno;
 		}
 		
 		protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
 	        super.onActivityResult(requestCode, resultCode, data); 
 	        if(requestCode == 0){ 
 	               // Bundle b = data.getExtras(); 
 	              //tv.setText(b.getString("ImagenSeleccionada")); 
 	        } 
 		}
 		
 		 private Boolean DBInsertData(Calzado ObjsCalzado)
 	    {
 			 Boolean retorno = false;
 			Integer id = ObjsCalzado.getId();
 	    	 //Abrimos la base de datos 'DBZapatos' en modo escritura
 			CalzadoSQLiteHelper usdbh =
 	            new CalzadoSQLiteHelper(this, "DBMercadeo", null, 1);
 			
 			
 			
 			try
 			{
 				 SQLiteDatabase db = usdbh.getWritableDatabase();
 	         	 
 				 	 				 	
	                //Insertamos los datos en la tabla Usuarios
	                db.execSQL("INSERT INTO Mercadeo (id, tienda,base,material,tipo,precio) " +
	                           "VALUES (" + id + ", '" + ObjsCalzado.getTienda() +"', " + ObjsCalzado.getBase() + ", " + ObjsCalzado.getMaterial() + ", " + ObjsCalzado.getTipo() + ", " + ObjsCalzado.getPrecio() + ")");
	            
 				 	
	            //Cerramos la base de datos
	            db.close();
	            retorno = true;
 				
 			}
 			catch(Exception ex)
 			{
 				retorno = false;
 			}
 	 
 	       
 	       return retorno;
 	    	
 	    }
 		 
 		/*private Boolean VerificaData()
 		{
 			
 			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
 	        builder.setMessage("Error!");
 	        
 	        return false;
 		}*/
	}

 		
