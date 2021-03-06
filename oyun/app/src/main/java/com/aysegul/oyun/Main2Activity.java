package com.aysegul.oyun;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    int sonKart=0;
    int skor=0;
    int hata=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i=getIntent();
        final String s=i.getStringExtra("mesaj");
        TextView tv=(TextView)findViewById(R.id.textView2);
        tv.setText("HOŞ GELDİNİZ "+s);
        GridLayout gl= (GridLayout) findViewById(R.id.GLkartlar);
        kart kartlar[]=new kart[16];

        for(int j=1;j<=16;j++){
            kartlar[j-1]=new kart(this,j);
            kartlar[j-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final kart k=(kart)v;
                    k.cevir();
                    if(sonKart>0){
                        final kart k2=(kart)findViewById(sonKart);

                        if(k2.onPlanID==k.onPlanID&&k2.getId()!=k.getId()){
                            //ESLESTİLER ACIK KALSIN
                            k2.cevrilebilir=false;
                            k.cevrilebilir=false;
                            skor++;
                            TextView tv=(TextView)findViewById(R.id.skor);
                            tv.setText("SKORUNUZ: "+skor);
                            if(skor==8){
                                //OYUN BİTTİ
                                Intent i=new Intent(Main2Activity.this,Main3Activity.class);
                                i.putExtra("puan",hata);
                                i.putExtra("isim",s);
                                startActivity(i);


                            }

                        }else{
                            //ESLESMEDİLER KARTLARI GERİ ÇEVİR
                            Handler h=new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    k.cevir();
                                    k2.cevir();
                                }
                            },500);
                            hata++;

                            TextView tv=(TextView)findViewById(R.id.hata);
                            tv.setText("HATA SAYISI: "+hata);
                            sonKart=0;
                        }
                    }else{
                        sonKart=k.getId();
                    }


                }
            });
        }
        //Karistir
        for(int j=0;j<16;j++){
            int rg=(int)(Math.random()*16);
            kart k=kartlar[rg];
            kartlar[rg]=kartlar[j];
            kartlar[j]=k;
        }

        for(int j=0;j<16;j++){
            gl.addView(kartlar[j]);
        }
    }
}
