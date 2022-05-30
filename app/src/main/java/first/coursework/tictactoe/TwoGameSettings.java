package first.coursework.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TwoGameSettings extends AppCompatActivity {
    EditText playerOne, playerTwo;
    Button startGameTwoBtn;
    String getPlayerOneName, getPlayerTwoName,sob, wlfn,message;
    MediaPlayer playerSound=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_player_settings);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        playerOne = findViewById(R.id.NamePlayerOne);
        playerTwo = findViewById(R.id.NamePlayerTwo);
        startGameTwoBtn = findViewById(R.id.StartGameTwoBTN);
        message = getString(R.string.message_for_two_player);
        sob = getIntent().getStringExtra("soundBtn");
        wlfn = getIntent().getStringExtra("soundWLFW");
        startGameTwoBtn.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            getPlayerOneName = playerOne.getText().toString();
            getPlayerTwoName = playerTwo.getText().toString();
            if(getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()){
                Toast.makeText(TwoGameSettings.this, message, Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    Intent intent = new Intent(TwoGameSettings.this, TwoPlayerGames.class);
                    intent.putExtra("NameOne", getPlayerOneName);
                    intent.putExtra("NameTwo", getPlayerTwoName);
                    intent.putExtra("soundBtn", sob);
                    intent.putExtra("soundWLFW", wlfn);
                    startActivity(intent);finish();
                } catch (Exception ignored) {
                }
            }
        });
    }
    private void soundBtnSys(){
        stopSound();
        if(sob.equals("ON") || sob.equals("ВКЛ")) {
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
        stopSound();
        soundBtnSys();
        try {
            Intent intent = new Intent(TwoGameSettings.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception ignored){
        }
    }
}