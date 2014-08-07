package com.hol.general.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hol.general.dialog.model.CustomAlertDialog;
import com.hol.general.dialog.model.CustomProgressDialog;

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
    	final String[] items = getResources().getStringArray(R.array.contact_add_item);
    	builder.setItems(R.array.contact_add_item, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), which + "你妹", Toast.LENGTH_SHORT).show();
                if (which == 2){
                    showLiteItems();
                }else{
                    showNormalDialog(items[which]);
                }
			}
		});
    	/*
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
				showProgressDialog();
			}
		}).setCancelable(true);
    	*/
    	builder.show();
    }

    private void showLiteItems(){
        CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
        builder.setTitle("这是什么");
    	builder.setSingleChoiceItems(ITEMS, 2, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), which + "XX", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
			}
		});
        builder.show();
    }
    
    private void showNormalDialog(String msg){
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		builder.setTitle("纳呢");
		builder.setMessage(msg);
		builder.setPositiveButton("Kill",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "你妹",
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setNegativeButton("back", null);
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Cancel",
						Toast.LENGTH_SHORT).show();
				showProgressDialog();
			}
		}).setCancelable(true);
		builder.show();
    }
    
    private void showProgressDialog(){
    	final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, "上传");
    	customProgressDialog.setProgressStyle(CustomProgressDialog.STYLE_HORIZONTAL);
    	customProgressDialog.setMax(123);
    	customProgressDialog.setMessage("正在上传...");
    	customProgressDialog.show();
    	Handler handler = new Handler(){
    		@Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			super.handleMessage(msg);
    			int progress = customProgressDialog.getProgress();
    			customProgressDialog.setProgress(progress + 2);
    			customProgressDialog.setMessage(String.format("正在上传...(%d/%d)", customProgressDialog.getProgress(), customProgressDialog.getMax()));
    			if (customProgressDialog.getProgress() >= customProgressDialog.getMax()){
    				customProgressDialog.dismiss();
    			}else{
    				this.sendEmptyMessageDelayed(0, 500);
    			}
    		}
    	};
    	handler.sendEmptyMessage(0);
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