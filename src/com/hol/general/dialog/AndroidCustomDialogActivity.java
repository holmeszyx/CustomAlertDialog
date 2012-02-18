package com.hol.general.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hol.general.dialog.model.CustomAlertDialog;

public class AndroidCustomDialogActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private static final String[] ITEMS = new String[]{
		"123",
		"456",
		"holmes",
		"张"
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnShow = (Button) findViewById(R.id.show);
        btnShow.setOnClickListener(this);
        
    }
    
    private void showCustomAlertDialog(){
    	/*
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	View content = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_context, null);
    	builder.setView(content);
    	//builder.setPositiveButton("OK", null);
    	builder.show();
    	*/
    	
    	CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
    	builder.setTitle("这是什么");
    	builder.setMessage("Holy shit!");
    	builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), which + "你妹", Toast.LENGTH_SHORT).show();
			}
		});
    	
    	builder.setSingleChoiceItems(ITEMS, 2, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), which + "XX", Toast.LENGTH_SHORT).show();
				
			}
		});
    	
    	builder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_context, null) );
    	
    	builder.setPositiveButton("Kill", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "你妹", Toast.LENGTH_SHORT).show();
			}
		});
    	builder.setNegativeButton("back", null);
    	builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
				
			}
		}).setCancelable(false);
    	
    	builder.show();
    }
    
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.show){
			showCustomAlertDialog();
			return;
		}
	}
}