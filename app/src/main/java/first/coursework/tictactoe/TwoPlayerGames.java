package first.coursework.tictactoe;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TwoPlayerGames extends AppCompatActivity {
    ImageView position1, position2, position3, position4, position5, position6, position7, position8, position9,remove, back, icoWinnerDialog,SrcX,SrcY;
    TextView playerOne, playerTwo,scoreX, scoreT, MoveIco, DialogText;
    Button btnMainMenu,btnContinue;
    MediaPlayer playerSound=null;
    int[][] arrayPosition = { {3,3,3},{3,3,3},{3,3,3}};
    int IntScoreX=0,IntScoreT=0,Tie = 0;
    Dialog dialogEnd;
    String moveGame = "X",PlayerOne,PlayerTwo,settingsSWLF,settingsSOB,won,friendship_won;
    boolean flag=true;
    Animation animationRotateCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        position1 = findViewById(R.id.imageBtn1);
        position2 = findViewById(R.id.imageBtn2);
        position3 = findViewById(R.id.imageBtn3);
        position4 = findViewById(R.id.imageBtn4);
        position5 = findViewById(R.id.imageBtn5);
        position6 = findViewById(R.id.imageBtn6);
        position7 = findViewById(R.id.imageBtn7);
        position8 = findViewById(R.id.imageBtn8);
        position9 = findViewById(R.id.imageBtn9);
        remove = findViewById(R.id.removeGame);
        back = findViewById(R.id.back);
        playerOne = findViewById(R.id.NamePlayerOne);
        playerTwo = findViewById(R.id.NamePlayerTwo);
        scoreX = findViewById(R.id.ScoreX);
        scoreT = findViewById(R.id.ScoreT);
        MoveIco = findViewById(R.id.moveico);
        won = getString(R.string.won);
        friendship_won = getString(R.string.friendship_won);
        settingsSOB = getIntent().getStringExtra("soundBtn");
        settingsSWLF = getIntent().getStringExtra("soundWLFW");
        PlayerOne = getIntent().getStringExtra("NameOne");
        PlayerTwo = getIntent().getStringExtra("NameTwo");
        playerOne.setText(PlayerOne);
        playerTwo.setText(PlayerTwo);
        SrcX = findViewById(R.id.srcX);
        SrcY = findViewById(R.id.srcY);
        SrcX.setImageResource(R.drawable.x);
        SrcY.setImageResource(R.drawable.o);
        position1.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position1, 0,0);
        });
        position2.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position2, 1,0);
        });
        position3.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position3, 2,0);
        });
        position4.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position4, 0,1);
        });
        position5.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position5, 1,1);
        });
        position6.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position6, 2,1);
        });
        position7.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position7, 0,2);
        });
        position8.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position8, 1,2);
        });
        position9.setOnClickListener(v -> {
            stopSound();soundBtn();
            Move(position9, 2,2);
        });
        remove.setOnClickListener(v -> {stopSound();soundBtnSys();Reload(); });
        back.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            try {
                Intent intent = new Intent(TwoPlayerGames.this, TwoGameSettings.class);
                intent.putExtra("soundBtn", settingsSOB);
                intent.putExtra("soundWLFW", settingsSWLF);
                startActivity(intent);finish();
            }catch (Exception ignored){
            }
        });
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialog_end);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.setCancelable(false);
        icoWinnerDialog = dialogEnd.findViewById(R.id.icoWinner);
        DialogText = dialogEnd.findViewById(R.id.WhoWinner);
        btnMainMenu = dialogEnd.findViewById(R.id.main_menu_dialog);
        btnContinue = dialogEnd.findViewById(R.id.dialog_continue);
        btnMainMenu.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            try {
                Intent intent = new Intent(TwoPlayerGames.this, MainActivity.class);
                intent.putExtra("soundBtn", settingsSOB);
                intent.putExtra("soundWLFW", settingsSWLF);
                startActivity(intent);finish();
            }catch (Exception ignored){
            }
        });
        btnContinue.setOnClickListener(v -> {stopSound();soundBtnSys();btnDEnable(true);dialogEnd.dismiss();});
        animationRotateCenter = AnimationUtils.loadAnimation(this, R.anim.rotation_imagev);
    }
    private void Move(ImageView position, int x, int y) {
        if (arrayPosition[x][y] == 3) {
            Tie+=1;
            if (moveGame.equals("X")) {
                position.setImageResource(R.drawable.x);
                MoveIco.setText("O");
                moveGame = "O";
                arrayPosition[x][y] = 1;
            } else {
                position.setImageResource(R.drawable.o);
                MoveIco.setText("X");
                moveGame = "X";
                arrayPosition[x][y] = 0;
            }
            Result();
        }
    }
    private void animationWin(int i1,int i2,int i3,int i4,int i5,int i6) {
        ImageView positionW1 = searchPosition(i1, i2);
        ImageView positionW2 = searchPosition(i3, i4);
        ImageView positionW3 = searchPosition(i5, i6);
        if(moveGame=="X"){
            positionW1.setImageResource(R.drawable.o_red);
            positionW2.setImageResource(R.drawable.o_red);
            positionW3.setImageResource(R.drawable.o_red);
        }else{
            positionW1.setImageResource(R.drawable.x1_red);
            positionW2.setImageResource(R.drawable.x1_red);
            positionW3.setImageResource(R.drawable.x1_red);
        }
        positionW1.startAnimation(animationRotateCenter);
        positionW2.startAnimation(animationRotateCenter);
        positionW3.startAnimation(animationRotateCenter);
    }
    private ImageView searchPosition(int x, int y) {
        if(x==0 && y ==0){ return position1; }
        else if(x==1 && y ==0){ return position2; }
        else if(x==2 && y ==0){ return position3; }
        else if(x==0 && y ==1){ return position4; }
        else if(x==1 && y ==1){ return position5; }
        else if(x==2 && y ==1){ return position6; }
        else if(x==0 && y ==2){ return position7; }
        else if(x==1 && y ==2){ return position8; }
        return position9;
    }
    private void btnDEnable(boolean fl) {
        position1.setEnabled(fl);
        position2.setEnabled(fl);
        position3.setEnabled(fl);
        position4.setEnabled(fl);
        position5.setEnabled(fl);
        position6.setEnabled(fl);
        position7.setEnabled(fl);
        position8.setEnabled(fl);
        position9.setEnabled(fl);
        remove.setEnabled(fl);
    }
    private int checkWinner() {
        for (int i=0;i<3;i++){
            if(arrayPosition[i][0]!=3 && arrayPosition[i][0]==arrayPosition[i][1] && arrayPosition[i][1]==arrayPosition[i][2]){
                animationWin(i,0,i,1,i,2);
                return 1;
            }
        }
        for (int i=0;i<3;i++){
            if(arrayPosition[0][i]!=3 && arrayPosition[0][i]==arrayPosition[1][i] && arrayPosition[1][i]==arrayPosition[2][i]){
                animationWin(0,i,1,i,2,i);
                return 1;
            }
        }
        if(arrayPosition[0][0]!=3 && arrayPosition[0][0]==arrayPosition[1][1] && arrayPosition[1][1]==arrayPosition[2][2]){
            animationWin(0,0,1,1,2,2);
            return 1;
        }
        if(arrayPosition[0][2]!=3 && arrayPosition[0][2]==arrayPosition[1][1] && arrayPosition[1][1]==arrayPosition[2][0]){
            animationWin(0,2,1,1,2,0);
            return 1;
        }
        if (Tie==9) {
            return -1;
        }
        return 0;
    }
    private void Result(){
        if(checkWinner()==1 || checkWinner()==-1) {
            if (checkWinner() == 1) {
                if (moveGame.equals("O")) {
                    IntScoreX += 1;
                    scoreX.setText(String.valueOf(IntScoreX));
                    icoWinnerDialog.setImageResource(R.drawable.x);
                    DialogText.setText(PlayerOne.toUpperCase() + " "+ won);
                } else {
                    IntScoreT += 1;
                    scoreT.setText(String.valueOf(IntScoreT));
                    icoWinnerDialog.setImageResource(R.drawable.o);
                    DialogText.setText(PlayerTwo.toUpperCase() + " "+ won);
                }
                soundPlay("win");
            } else if (checkWinner() == -1) {
                DialogText.setText(friendship_won);
                icoWinnerDialog.setImageResource(R.drawable.friendship);
                soundPlay("fwon");
            }
            btnDEnable(false);
            new Handler().postDelayed(() -> {
                if (flag) {
                    Reload();
                    dialogEnd.show();
                }
            }, 3000);
        }
    }
    private void Reload() {
        position1.setImageResource(android.R.color.transparent);
        position2.setImageResource(android.R.color.transparent);
        position3.setImageResource(android.R.color.transparent);
        position4.setImageResource(android.R.color.transparent);
        position5.setImageResource(android.R.color.transparent);
        position6.setImageResource(android.R.color.transparent);
        position7.setImageResource(android.R.color.transparent);
        position8.setImageResource(android.R.color.transparent);
        position9.setImageResource(android.R.color.transparent);
        Tie=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                arrayPosition[i][j] = 3;
            }
        }
    }
    private void soundPlay(String sound){
        if(settingsSWLF.equals("ON") || settingsSWLF.equals("ВКЛ")) {
            stopSound();
            switch (sound) {
                case "win":
                    playerSound = MediaPlayer.create(this, R.raw.win);
                    break;
                case "fwon":
                    playerSound = MediaPlayer.create(this, R.raw.fwon);
                    break;
            }
            playerSound.start();
        }
    }
    private void soundBtn(){
        stopSound();
        if(settingsSOB.equals("ON") || settingsSOB.equals("ВКЛ")) {
            playerSound = MediaPlayer.create(this, R.raw.btn);
            playerSound.start();
        }
    }
    private void soundBtnSys(){
        stopSound();
        if(settingsSOB.equals("ON")||settingsSOB.equals("ВКЛ")) {
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
        flag=false;
        try {
            Intent intent = new Intent(TwoPlayerGames.this, TwoGameSettings.class);
            intent.putExtra("soundBtn", settingsSOB);
            intent.putExtra("soundWLFW", settingsSWLF);
            startActivity(intent);finish();
        }catch (Exception ignored){
        }
    }
}


