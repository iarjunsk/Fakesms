package com.tricks4speed.fakesms;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FakesmsActivity extends Activity {
	private String array_spinner[];
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // Here come all the options that you wish to show depending on the
        // size of the array.
        array_spinner=new String[5];
        array_spinner[0]="inbox";
        array_spinner[1]="sent";
        array_spinner[2]="draft";
        array_spinner[3]="outbox";
        array_spinner[4]="failed";
        final Spinner s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adapter = new ArrayAdapter(this,
        android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        
        
        
        final EditText tnum;
		final EditText tbody;
		final TimePicker tp;
		final DatePicker dp;
		
        Button save,reset;
        
        tnum = (EditText) findViewById(R.id.tnum);
        tbody= (EditText) findViewById(R.id.tbody);
        save = (Button) findViewById(R.id.save);
        reset=(Button) findViewById(R.id.reset);
        tp=(TimePicker)findViewById(R.id.timePicker1);
        dp=(DatePicker)findViewById(R.id.datePicker1);
        
       save.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			// main code is Here
			String num=tnum.getText().toString();
	        String body=tbody.getText().toString();
	       
	        ContentValues my_values = new ContentValues();
	        my_values.put("address",num);//sender name
	        my_values.put("body", body);
	        
	       
	        
	        my_values.put("read", 0);
	        
	        //for date and time
	        Calendar cal = Calendar.getInstance();
	        cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
	        
	        cal.set(Calendar.HOUR_OF_DAY,tp.getCurrentHour());
            cal.set(Calendar.MINUTE,tp.getCurrentMinute());
            cal.set(Calendar.SECOND,0);
            
            
	        my_values.put("date", cal.getTimeInMillis());
	        	        
	        String path="content://sms/"+array_spinner[s.getSelectedItemPosition()];
	        
	       if( getContentResolver().insert(Uri.parse(path), my_values)!=null)
	    	   {
	    	   Toast.makeText(getBaseContext(), "Successfully Faked!",
						Toast.LENGTH_SHORT).show();
	    	   //for a bug
	    	   getContentResolver().delete(Uri.parse("content://sms/conversations/-1"), null, null);
	    	   }
	       
	       else
	    	   Toast.makeText(getBaseContext(), "Unsuccesful!",
						Toast.LENGTH_SHORT).show();
	       
	       
	        
		}
	});
       
       reset.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			tnum.setText("");
			tbody.setText("");
			
		}
	});
       
       
        
    }
    
	
    
}