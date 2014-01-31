package com.example.calltrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	static boolean ring=false;
    static boolean callReceived=false;
    static String callerNumber="";
	TelephonyManager telephonyManager;
	
	
	public boolean isNumberExisting()
	{
		boolean ans=true;
		
		
		
		return ans;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent=new Intent(this,MyService.class);
		startService(intent);
		/*telephonyManager=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(new PhoneStateListener(){
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				// TODO Auto-generated method stub
				super.onCallStateChanged(state, incomingNumber);
				if(state==TelephonyManager.CALL_STATE_RINGING)
				{
					ring=true;
					callerNumber=telephonyManager.getLine1Number();
				}
				else if(state==TelephonyManager.CALL_STATE_OFFHOOK)
				{
					callReceived=true;
				}
				else if(state==TelephonyManager.CALL_STATE_IDLE)
				{
					if(ring==true&&callReceived==false)
					{
						//missed call
						Toast.makeText(getBaseContext(), "Missed call from "+callerNumber, Toast.LENGTH_LONG).show();
						
					}
					ring=false;callReceived=false;
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
