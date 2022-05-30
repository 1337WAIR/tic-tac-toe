package first.coursework.tictactoe;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button buttonTwoPlayers,buttonSingleGames,buttonSettings,settingsBtn1,settingsBtn2,ExitBtn;
    ImageView flag_lang;
    long backPressedTime;
    Toast back;
    Dialog dialogSettings;
    String settingsSOB, settingsSWLF,backMessage,on,off,country;
    MediaPlayer playerSound=null;
    boolean settingsDialog=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonTwoPlayers = findViewById(R.id.Two);
        buttonSingleGames = findViewById(R.id.single);
        buttonSettings = findViewById(R.id.Settings);
        ExitBtn = findViewById(R.id.Exit_btn);
        backMessage = getString(R.string.back_message);
        on = getString(R.string.ON);
        off = getString(R.string.OFF);
        buttonSingleGames.setOnClickListener(v ->{
            stopSound();
            soundBtnSys();
            try {
                Intent intent = new Intent( MainActivity.this, SingleGameSettings.class);
                intent.putExtra("soundBtn", settingsSOB);
                intent.putExtra("soundWLFW", settingsSWLF);
                startActivity(intent);finish();
            } catch (Exception ignored) {
            }
        });
        buttonTwoPlayers.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            try {
                Intent intent = new Intent( MainActivity.this, TwoGameSettings.class);
                intent.putExtra("soundBtn", settingsSOB);
                intent.putExtra("soundWLFW", settingsSWLF);
                startActivity(intent);finish();
            } catch (Exception ignored) {
            }
        });
        dialogSettings = new Dialog(this);
        dialogSettings.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSettings.setContentView(R.layout.main_settings);
        dialogSettings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsBtn1 = dialogSettings.findViewById(R.id.settings_SOB);
        settingsBtn2 = dialogSettings.findViewById(R.id.settings_SWLF);
        flag_lang = dialogSettings.findViewById(R.id.language);
        settingsBtn1.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            if(settingsSOB.equals(on)){
                settingsBtn1.setText(off);
                settingsSOB=off;
            }else{
                settingsBtn1.setText(on);
                settingsSOB=on;
            }
            saveSettings();
        });
        settingsBtn2.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            if(settingsSWLF.equals(on)){
                settingsBtn2.setText(off);
                settingsSWLF=off;
            }else{
                settingsBtn2.setText(on);
                settingsSWLF=on;
            }
            saveSettings();
        });
        flag_lang.setOnClickListener(v -> {
            if(country.equals("uk")){ country = ""; }
            else{ country = "uk"; }
            setLocale(country);
            flagChange();
            settingsDialog= true;
            saveSettings();
            recreate();
            dialogSettings.show();
        });
        buttonSettings.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            dialogSettings.show();
        });
        ExitBtn.setOnClickListener(v ->{stopSound(); soundBtnSys(); MainActivity.super.onBackPressed();});
        try {
            loadData();
            flagChange();
        } catch(Exception ignored){ }
    }
    private void flagChange() {
        if (country.equals("uk")) {
            flag_lang.setImageResource(R.drawable.ukraine);
        } else {
            flag_lang.setImageResource(R.drawable.great_britain);
        }
        if (settingsSOB.equals("ON") || settingsSOB.equals("ВКЛ")) { settingsSOB = on; }
        else { settingsSOB = off; }
        if (settingsSWLF.equals("ON") || settingsSWLF.equals("ВКЛ")) { settingsSWLF = on; }
        else { settingsSWLF = off; }
    }
    private void setLocale(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_lang",language);
        editor.apply();
    }
    private void loadLanguage(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        country = prefs.getString("My_lang",Locale.getDefault().getLanguage());
        setLocale(country);
    }
    private void saveSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREFS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TEXT10",settingsBtn1.getText().toString());
        editor.putString("TEXT15",settingsBtn2.getText().toString());
        editor.putBoolean("DialogShow",settingsDialog);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREFS",MODE_PRIVATE);
        settingsSOB = sharedPreferences.getString("TEXT10",settingsBtn1.getText().toString());
        settingsSWLF = sharedPreferences.getString("TEXT15",settingsBtn2.getText().toString());
        settingsDialog = sharedPreferences.getBoolean("DialogShow",false);
        flagChange();
        settingsBtn1.setText(settingsSOB);
        settingsBtn2.setText(settingsSWLF);
        if(settingsDialog){dialogSettings.show(); settingsDialog=false; saveSettings();}
    }
    private void soundBtnSys(){
        stopSound();
        if(settingsSOB.equals(on)) {
            playerSound = MediaPlayer.create(this, R.raw.soundbtn);
            playerSound.start();
        }
    }
    private void stopSound(){
        if(playerSound != null) {
            playerSound.release();
            playerSound=null;
        }
    }
    @Override
    public void onBackPressed() {
        if(backPressedTime+2000 > System.currentTimeMillis()){
            back.cancel();
            super.onBackPressed();
            return;
        }
        else{
            back = Toast.makeText(getBaseContext(), backMessage,Toast.LENGTH_SHORT);
            back.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}