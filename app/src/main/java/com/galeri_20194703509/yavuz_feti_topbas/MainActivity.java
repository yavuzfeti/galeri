package com.galeri_20194703509.yavuz_feti_topbas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView resim;
    TextView sehir;
    SeekBar seekbar;
    Button basla_dur;
    Spinner spinner;
    EditText sure;
    Timer zamanlama;

    String durum = "kapali";
    int sira = 0;
    int deger = 1;

    String[] yon={"İleri","Geri"};

    int[] resimler={R.drawable.ankara,R.drawable.antalya,R.drawable.antep,R.drawable.artvin,R.drawable.aydin,
            R.drawable.balikesir,R.drawable.bolu,R.drawable.burdur,R.drawable.bursa,
            R.drawable.canakkale,R.drawable.denizli,R.drawable.edirne,R.drawable.erzurum,
            R.drawable.isparta,R.drawable.istanbul,R.drawable.izmir,
            R.drawable.kayseri,R.drawable.kocaeli,
            R.drawable.manisa,R.drawable.mardin,R.drawable.mersin,R.drawable.mugla,R.drawable.nevsehir,
            R.drawable.rize,R.drawable.samsun,R.drawable.trabzon,R.drawable.urfa,R.drawable.yozgat,R.drawable.zonguldak};

    String[] sehirler={"Ankara","Antalya","Gaziantep","Artvin","Aydın",
            "Balıkesir","Bolu","Burdur","Bursa",
            "Çanakkale","Denizli","Edirne","Erzurum",
            "Isparta","İstanbul","İzmir",
            "Kayseri","Kocaeli",
            "Manisa","Mardin","Mersin","Muğla","Nevşehir",
            "Rize","Samsun","Trabzon","Şanlıurfa","Yozgat","Zonguldak"};

    public void init()
    {
        resim = findViewById(R.id.resim);
        sehir = findViewById(R.id.sehir_ismi);
        seekbar = findViewById(R.id.degistirme_seekbar);
        basla_dur = findViewById(R.id.baslat_durdur);
        spinner = findViewById(R.id.ileri_geri_spinner);
        sure = findViewById(R.id.sure);
        seekbar.setMax(resimler.length-1);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                sira=i;
                goster();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,yon);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        init();
        goster();
    }

    public void goster()
    {
        resim.setImageResource(resimler[sira]);
        sehir.setText(sehirler[sira]);
        seekbar.setProgress(sira);
    }

    public void ilk(View view)
    {
        if (sira==0){Toast.makeText(this,"Zaten ilk görseldesiniz",Toast.LENGTH_SHORT).show();}
        sira=0;
        goster();
    }

    public void geri(View view)
    {
        if (sira>0)
        {
            sira--;
        }
        else
        {
            sira=resimler.length-1;
            Toast.makeText(this,"Sona geçildi",Toast.LENGTH_SHORT).show();
        }
        goster();
    }

    public void ileri(View view)
    {
        if (sira<resimler.length-1)
        {
            sira++;
        }
        else
        {
            sira=0;
            Toast.makeText(this,"Başa dönüldü",Toast.LENGTH_SHORT).show();
        }
        goster();
    }

    public void son(View view)
    {
        if (sira==resimler.length-1){Toast.makeText(this,"Zaten son görseldesiniz",Toast.LENGTH_SHORT).show();}
        sira=resimler.length-1;
        goster();
    }

    public void baslat_durdur(View view)
    {
        if (durum.equals("kapali"))
        {
            baslat();
            Toast.makeText(this,"Slayt gösterisi " + deger + " saniye ile başladı",Toast.LENGTH_LONG).show();
            durum = "acik";
            basla_dur.setText("Durdur");
        }
        else if (durum.equals("acik"))
        {
            durdur();
            Toast.makeText(this,"Slayt gösterisi durdu",Toast.LENGTH_SHORT).show();
            durum = "kapali";
            basla_dur.setText("Başlat");
        }
        else
        {
            Toast.makeText(this,"Hata oluştu",Toast.LENGTH_SHORT).show();
        }
    }

    public void baslat()
    {
        if (sure.getText().toString().isEmpty() || Integer.parseInt(sure.getText().toString())==0)
        {
            deger = 1;
        }
        else
        {
            deger = Integer.parseInt(sure.getText().toString());
        }
        zamanlama=new Timer();
        TimerTask oynat=new TimerTask()
        {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (spinner.getSelectedItem().toString()==yon[0])
                        {
                            if(sira<resimler.length-1)
                            {
                                sira++;
                            }
                            else
                            {
                                sira=0;
                                Toast.makeText(getApplicationContext(),"Baştan başlatıldı",Toast.LENGTH_SHORT).show();
                            }
                            goster();
                        }
                        else if(spinner.getSelectedItem().toString()==yon[1])
                        {
                            if (sira>0)
                            {
                                sira--;
                            }
                            else
                            {
                                sira=resimler.length-1;
                                Toast.makeText(getApplicationContext(),"Sona gelindi",Toast.LENGTH_SHORT).show();
                            }
                            goster();
                        }
                    }
                });

            }
        };
        zamanlama.schedule(oynat,0,deger*1000);
    }

    public void durdur(){zamanlama.cancel();}
}