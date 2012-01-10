package Mercadeo.Droid;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class CameraDroidActivity extends Activity implements SurfaceHolder.Callback{
    /** Called when the activity is first created. */
   
        private LayoutInflater myInflater = null;
        Camera myCamera;
        byte[] tempdata;
        boolean myPreviewRunning = false;
        private SurfaceHolder mySurfaceHolder;
        private SurfaceView mySurfaceView;
        Button takePicture;
        private Integer id;
        
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = Integer.parseInt(this.getIntent().getExtras().getString("Id"));
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.camara);
        
        mySurfaceView = (SurfaceView) findViewById(R.id.surface);
        mySurfaceHolder = mySurfaceView.getHolder();
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        myInflater = LayoutInflater.from(this);
        View overView = myInflater.inflate(R.layout.segundacapa,null);
        this.addContentView(overView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        
        takePicture = (Button) findViewById(R.id.button);
        takePicture.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		myCamera.takePicture(myShutterCallback, myPictureCallback, myJpeg);
        	}
        });
        		
    }
    
    ShutterCallback myShutterCallback = new ShutterCallback() {
    	public void onShutter() {
    	}
    };

    PictureCallback myPictureCallback = new PictureCallback() {
    	public void onPictureTaken(byte[] data, Camera myCamera) {
    		// TODO Auto-generated method stub
    	}
    };
    
    PictureCallback myJpeg = new PictureCallback() {
    	public void onPictureTaken(byte[] data, Camera myCamera) {
    		// TODO Auto-generated method stub
    		if(data != null){
    			tempdata = data;
    			if(DBUpdateData())
    			{
    				done();
    			}
    			
    		}
    	}
    };
    
    void done(){
    	Bitmap bm = BitmapFactory.decodeByteArray(tempdata, 0, tempdata.length);
    	String url = Images.Media.insertImage(getContentResolver(), bm, null, null);
    	bm.recycle();
    	Bundle bundle = new Bundle();
    	if(url != null){
    		bundle.putString("url",url);
    		Intent mIntent = new Intent();
    		mIntent.putExtras(bundle);
    		//setResult(0, mIntent);
    	}
    	else{
    		Toast.makeText(this, "Picture can not be saved", Toast.LENGTH_SHORT).show();
    	}
    	 Intent i; 
         i=new Intent(); 
         //i.putExtras(bund); 
         setResult(0,i); 
         finish(); 
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	// TODO Auto-generated method stub
    	try{
    		if(myPreviewRunning){
    			myCamera.stopPreview();
    			myPreviewRunning = false;
    		}
    		Camera.Parameters p = myCamera.getParameters();
    		p.setPreviewSize(width,height);

    		myCamera.setParameters(p);
    		myCamera.setPreviewDisplay(holder);
    		myCamera.startPreview();
    		myPreviewRunning = true;
    	}catch(Exception e){}
    }

    public void surfaceCreated(SurfaceHolder holder) {
    	// TODO Auto-generated method stub
    		myCamera = Camera.open();
    	}

    	public void surfaceDestroyed(SurfaceHolder holder) {
    		// TODO Auto-generated method stub
    		myCamera.stopPreview();
    		myPreviewRunning = false;
    		myCamera.release();
    		myCamera = null;
    	}
    	    	
    	
    	 private Boolean DBUpdateData()
  	    {
	 			
    		 Boolean retorno = false;
			try
			{
						
  			
  	    	 //Abrimos la base de datos 'DBZapatos' en modo escritura
  			CalzadoSQLiteHelper usdbh =
  	            new CalzadoSQLiteHelper(this, "DBMercadeo", null, 1);
  			
  		
  				 SQLiteDatabase db = usdbh.getWritableDatabase();
  	         	 
  									 			
 	                //Insertamos los datos en la tabla Usuarios
 	                db.execSQL("UPDATE Mercadeo SET imagen= \"" + tempdata  +"\" WHERE id=" + id);
 	                
 	             //Establecemos los campos-valores a actualizar
 	               ContentValues valores = new ContentValues();
 	               valores.put("imagen","'" + tempdata + "'");
 	               
 	              String[] args = new String[]{"1"}; 
 	               //Actualizamos el registro en la base de datos
 	               db.update("Mercadeo", valores, "id=?",args);
 	            
 	 
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

 }

