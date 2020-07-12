package com.example.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm.database.DatabaseHelper;

import java.util.Calendar;


public class Formekrani extends Activity {
    private static final int REQUEST_PHONE_CALL = 1;
    private static final int PICK_CONTACT = 1;
    private Button buttonKayit,buttonSil;
    private Button buttonRehber;
    private Button buttonSaat,buttonTarih;
    private EditText editTextTel;
    Calendar calendar;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    private int guncelYil,guncelAy,guncelGun,saat,dakika,guncelSaat,guncelDakika,yil,ay,gun;
    public static final String mypreference = "mypref";
    DatabaseHelper databaseHelper;

    private String telefon,tarih,saatDakika;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_ekrani);

        databaseHelper = new DatabaseHelper(this);

        editTextTel = findViewById(R.id.editTextPhone);
        final TextView textViewSaatUpdate= findViewById(R.id.textViewSaatUpdate);
        final TextView textViewTarih = findViewById(R.id.textViewTarihUpdate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            telefon = extras.getString("telefon");
            tarih = extras.getString("tarih");
            saatDakika = extras.getString("saat");

            editTextTel.setText(telefon);
            textViewTarih.setText(tarih);
            textViewSaatUpdate.setText(saatDakika);
            //The key argument here must match that used in the other activity
        }

        buttonSil = findViewById(R.id.buttonSil);
        buttonKayit = findViewById(R.id.buttonKayit);
        buttonRehber = findViewById(R.id.buttonRehber);
        buttonSaat = findViewById(R.id.buttonSaatUpdate);
        buttonTarih = findViewById(R.id.buttonTarih);


        databaseHelper = new DatabaseHelper(this);



        buttonKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.telefon = editTextTel.getText().toString();

                SharedPreferences settings = getSharedPreferences(mypreference, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("telefon",Global.telefon);
                editor.apply();

              /*  calendar.set(Calendar.YEAR,guncelYil);
                calendar.set(Calendar.MONTH,guncelAy);
                calendar.set(Calendar.DAY_OF_MONTH,guncelGun);
                calendar.set(Calendar.HOUR_OF_DAY, guncelSaat);
                calendar.set(Calendar.MINUTE,guncelDakika);*/
                AlarmManager alarmManager=(AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Formekrani.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Formekrani.this, 0, intent, 0);

                long reminderDateTimeInMilliseconds = 000;

                Calendar calendarToSchedule = Calendar.getInstance();
                calendarToSchedule.setTimeInMillis(System.currentTimeMillis());
                calendarToSchedule.clear();

            //.Set(Year, Month, Day, Hour, Minutes, Seconds);
                calendarToSchedule.set(guncelYil, guncelAy, guncelGun, guncelSaat, guncelDakika, 0);

                reminderDateTimeInMilliseconds = calendarToSchedule.getTimeInMillis();

                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderDateTimeInMilliseconds, pendingIntent);
                }
                else{

                    alarmManager.set(AlarmManager.RTC_WAKEUP, reminderDateTimeInMilliseconds, pendingIntent);
                }


              //  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                Toast.makeText(Formekrani.this,"Alarm kuruldu",Toast.LENGTH_LONG).show();

           //     if(tarih.equals("") && saatDakika.equals("")){
                    tarih = (guncelGun+"/"+guncelAy+"/"+guncelYil);
                    saatDakika = (guncelSaat+":"+guncelDakika);
          //      }

                databaseHelper.insertTelefonListesi("yok şimdilik",Global.telefon,tarih,saatDakika);


                Intent intent2 = new Intent(Formekrani.this, MainActivity.class);
                startActivity(intent2);

            }
        });

        buttonTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                gun = calendar.get(Calendar.DAY_OF_MONTH);
                ay = calendar.get(Calendar.MONTH);
                yil = calendar.get(Calendar.YEAR);
                //System.out.println("gün."+day+" ay:"+month+" yıl:"+year);
                datePickerDialog = new DatePickerDialog(Formekrani.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewTarih.setText(dayOfMonth + "/" + (month) + "/" + year);
                        guncelGun=dayOfMonth;
                        guncelAy=month;
                        guncelYil=year;
                    }
                }, gun, ay, yil);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        buttonSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                //System.out.println("dakika"+dakika+"saat:"+saat);
                TimePickerDialog tpd = new TimePickerDialog(Formekrani.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // hourOfDay ve minute değerleri seçilen saat değerleridir.
                                // Edittextte bu değerleri gösteriyoruz.
                                textViewSaatUpdate.setText(hourOfDay + ":" + minute);
                                guncelSaat=hourOfDay;
                                guncelDakika=minute;
                            }
                        }, hour, min, true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Seç", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", tpd);
                tpd.show();
                Toast.makeText(Formekrani.this,"Saat kuruldu",Toast.LENGTH_LONG).show();

            }
        });



        buttonRehber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Reminder App";
            String description="Açıklama buraya gelecek";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager=Formekrani.this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
                            setCn(cNumber);
                        }
                    }
                }
        }
    }

    private void setCn(String cNumber) {
        System.out.println("cnumber:"+cNumber);
    }
}

