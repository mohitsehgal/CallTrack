package com.example.calltrack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyService extends Service {
	static boolean ring=false;
    static boolean callReceived=false;
    static String callerNumber="";
	TelephonyManager telephonyManager;
	SmsManager smsManager;
	public MyService() {
	}

	HashSet<String> numbersSet;
	HashSet<String> contactSet;
	
	@Override
	public void onCreate() {
		super.onCreate();
	
	
		numbersSet=new HashSet<String>();
		contactSet=new HashSet<String>();
		contactSet.add("1");
		contactSet.add("2");
		
		numbersSet.add("15555215554");//dummy data
		
		
	}

	
	private ArrayList<String> getPhoneNumberList()
	{
		ArrayList<String> phones = new ArrayList<String>();

		
		for(String id: contactSet)
		{
				Cursor cursor = getContentResolver().query(	CommonDataKinds.Phone.CONTENT_URI,null, CommonDataKinds.Phone.CONTACT_ID +" = ?", 
		        new String[]{id}, null);

				while (cursor.moveToNext()) 
				{
					phones.add(cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)));
				} 
		
				cursor.close();
		}
		
		return phones;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
	int a= super.onStartCommand(intent, flags, startId);
	telephonyManager=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
	smsManager=SmsManager.getDefault();
	telephonyManager.listen(new PhoneStateListener(){
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
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
					List<String> list=getPhoneNumberList();
					if(list.contains(callerNumber))
					{
							
						String status="hi";
						smsManager.sendTextMessage(callerNumber, null, status, null, null);
						
					}
				}
				ring=false;callReceived=false;
			}
		}
	}, PhoneStateListener.LISTEN_CALL_STATE);
	return a;
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
