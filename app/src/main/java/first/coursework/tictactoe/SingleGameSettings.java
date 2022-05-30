package first.coursework.tictactoe;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class SingleGameSettings extends AppCompatActivity {
    Button Start;
    ImageView LeftComplexity, rightComplexity,leftSource,rightSource, leftMove,rightMove;
    TextView complexity, Source, move;
    String complexityStr, SourceStr,moveStr,sob,wlfn,easyComplexityStr,hardComplexityStr,mediumComplexityStr,firstMove,secondMove;
    MediaPlayer playerSound=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_settings);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sob = getIntent().getStringExtra("soundBtn");
        wlfn = getIntent().getStringExtra("soundWLFW");
        easyComplexityStr = getString(R.string.easy);
        hardComplexityStr = getString(R.string.hard);
        mediumComplexityStr = getString(R.string.medium);
        firstMove = getString(R.string.first);
        secondMove = getString(R.string.second);
        complexityStr = "EASY";
        SourceStr = "X";
        moveStr = "FIRST";
        Start = findViewById(R.id.StartSingleGame);
        LeftComplexity = findViewById(R.id.leftComplexity);
        complexity = findViewById(R.id.Complexity);
        rightComplexity = findViewById(R.id.rightComplexity);
        leftSource = findViewById(R.id.leftSourse);
        Source = findViewById(R.id.Sourse);
        rightSource= findViewById(R.id.rightSourse);
        leftMove= findViewById(R.id.leftMove);
        move = findViewById(R.id.Move);
        rightMove= findViewById(R.id.rightMove);
        LeftComplexity.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            if(complexityStr.equals("EASY")){
                complexityStr = "HARD";
                complexity.setText(hardComplexityStr);
            }
            else if(complexityStr.equals("HARD")){
                complexityStr = "MEDIUM";
                complexity.setText(mediumComplexityStr);
            }
            else{
                complexityStr = "EASY";
                complexity.setText(easyComplexityStr);
            }
        });
        rightComplexity.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            if(complexityStr.equals("EASY")){
                complexityStr = "MEDIUM";
                complexity.setText(mediumComplexityStr);
            }
            else if(complexityStr.equals("MEDIUM")){
                complexityStr = "HARD";
                complexity.setText(hardComplexityStr);
            }
            else{
                complexityStr = "EASY";
                complexity.setText(easyComplexityStr);
            }
        });
        leftSource.setOnClickListener(v -> SourceSRC());
        rightSource.setOnClickListener(v -> SourceSRC());
        leftMove.setOnClickListener(v -> MoveSRC());
        rightMove.setOnClickListener(v -> MoveSRC());
        Start.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            try {
                Intent intent = new Intent(SingleGameSettings.this, easy_medium_hard_complexity.class);
                intent.putExtra("source", SourceStr);
                intent.putExtra("move", moveStr);
                intent.putExtra("sBtn", sob);
                intent.putExtra("swlfn", wlfn);
                intent.putExtra("complexity", complexityStr);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
    }
    private void SourceSRC() {
        stopSound();
        soundBtnSys();
        if(SourceStr.equals("X")){
            Source.setText("O");
            SourceStr="O";
        }
        else {
            Source.setText("X");
            SourceStr="X";
        }
    }
    private void MoveSRC() {
        stopSound();
        soundBtnSys();
        if(moveStr.equals("FIRST")){
            move.setText(secondMove);
            moveStr = "SECOND";
        }
        else {
            move.setText(firstMove);
            moveStr = "FIRST";
        }
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
            Intent intent1 = new Intent(SingleGameSettings.this, MainActivity.class);
            startActivity(intent1);finish();
        }catch (Exception ignored){
        }
    }
}
