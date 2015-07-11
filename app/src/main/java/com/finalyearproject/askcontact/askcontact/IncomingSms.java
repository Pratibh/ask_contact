package com.finalyearproject.askcontact.askcontact;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratibh on 6/14/15.
 */
public class IncomingSms extends BroadcastReceiver {
    FetchContacts contactInformation = new FetchContacts();

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent) {
        List<Person> list = new ArrayList<Person>();
        System.out.println("list sze is: "+list.size());
        System.out.println("the context is: " + context);
        list = contactInformation.fetchContacts(context.getApplicationContext());
        //Hardcoded string message for test purpose
        //ArrayList<String> getInformation = contactInformation.fetchContacts();
        String messageToSend = "No Matching contacts";
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                Intent j = new Intent(context, IncomingSms.class);
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, j, 0);
                /*working code for toast message*/
                String senderNum = null;
                String message = null;
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum is : "+ senderNum + ", and the message is: " + message, duration);
                    toast.show();
                    String messageCode = message.substring(0,2);
                    String nameMessage = message.substring(2,message.length());
                    if (messageCode.equals("**"))
                    {
                        for(Person person : list){
                    //        if("null".equalsperson.getName().equals(null) || person.getName().equals("") || person == null)
                            if("null".equals(person.getName()) || "".equals(person.getName()))
                            {System.out.println("if............");
                                continue;}
                            else
                            {
                                if(nameMessage.equalsIgnoreCase(person.getName())){
                                    System.out.println("else............");

                                    sms.sendTextMessage(senderNum, null, person.getPhoneNumber(), pi, null);
                                }
                            }
                        }
                    }
                    else {
                        sms.sendTextMessage(senderNum, null, messageToSend, pi, null);
                        break;
                    }
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    // Show Alert
                   /* int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum is : "+ senderNum + ", and the message is: " + message, duration);
                    toast.show();*/
                } // end for loop
                SmsManager sms = SmsManager.getDefault();
            } // bundle is null
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
}
