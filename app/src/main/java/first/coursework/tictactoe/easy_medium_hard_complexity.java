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

public class easy_medium_hard_complexity extends AppCompatActivity {
    String moveBot, movePlayer,move,settingsSWLF,settingsSOB,complexity,won,loss,friendship_won;
    ImageView position1, position2, position3, position4, position5, position6, position7, position8, position9, remove, back, icoWinnerDialog;
    TextView playerOne, playerTwo, scoreX, scoreT, MoveIco, DialogText;
    Button btnMainMenu, btnContinue;
    boolean moveTrue=true,flag=true;
    int[][] arrayPosition = {{0,0,0}, {0,0,0}, {0,0,0}};
    int IntScoreX = 0, IntScoreT = 0, NumberBot, NumberPlayer,Tie = 0;
    Dialog dialogEnd;
    MediaPlayer playerSound = null;
    Animation animationRotateCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        move = getIntent().getStringExtra("move");
        complexity = getIntent().getStringExtra("complexity");
        movePlayer = getIntent().getStringExtra("source");
        settingsSOB = getIntent().getStringExtra("sBtn");
        settingsSWLF = getIntent().getStringExtra("swlfn");
        moveBot = (movePlayer.equals("X")) ? "O" : "X";
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
        won = getString(R.string.won_single);
        loss = getString(R.string.loss_single);
        friendship_won = getString(R.string.friendship_won);
        position1.setOnClickListener(v -> { soundBtn(); Move(position1, 0, 0);});
        position2.setOnClickListener(v -> { soundBtn(); Move(position2, 1, 0);});
        position3.setOnClickListener(v -> { soundBtn(); Move(position3, 2, 0);});
        position4.setOnClickListener(v -> { soundBtn(); Move(position4, 0, 1);});
        position5.setOnClickListener(v -> { soundBtn(); Move(position5, 1, 1);});
        position6.setOnClickListener(v -> { soundBtn(); Move(position6, 2, 1);});
        position7.setOnClickListener(v -> { soundBtn(); Move(position7, 0, 2);});
        position8.setOnClickListener(v -> { soundBtn(); Move(position8, 1, 2);});
        position9.setOnClickListener(v -> { soundBtn(); Move(position9, 2, 2);});
        if (moveBot.equals("X")) {
            NumberBot = 1;
            NumberPlayer = 4;
        } else {
            NumberBot = 4;
            NumberPlayer = 1;
        }
        remove.setOnClickListener(v -> {stopSound();soundBtnSys(); btnDEnable(true); Reload(); BotIf();});
        back.setOnClickListener(v -> {
            stopSound();
            flag=false;
            soundBtnSys();
            try {
                Intent intent = new Intent(easy_medium_hard_complexity.this, SingleGameSettings.class);
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
                Intent intent = new Intent(easy_medium_hard_complexity.this, MainActivity.class);
                startActivity(intent);finish();
            }catch (Exception ignored){
            }
        });
        btnContinue.setOnClickListener(v -> {
            stopSound();
            soundBtnSys();
            BotIf();
            dialogEnd.dismiss();
            btnDEnable(true);
        });
        animationRotateCenter = AnimationUtils.loadAnimation(this, R.anim.rotation_imagev);
        BotIf();
    }
    private void Move(ImageView position, int x, int y) {
        if (arrayPosition[x][y] == 0) {
            if (movePlayer.equals("X")) {
                position.setImageResource(R.drawable.x);
            } else {
                position.setImageResource(R.drawable.o);
            }
            arrayPosition[x][y] = NumberPlayer;
            Tie+=1;
            ScoreAndDialog();
            if(moveTrue){
                aiMove();
                ScoreAndDialog();
            }
            moveTrue = true;
        }
    }
    private void aiMove() {
        if(complexity.equals("EASY")){
            EasyComplexity();
        }else if(complexity.equals("MEDIUM")){
            MediumComplexity();
        }else{
            HardComplexity();
        }
    }
    private void EasyComplexity() {
        while (true) {
            int randomI = (int) (Math.random() * 3);
            int randomJ = (int) (Math.random() * 3);
            if (arrayPosition[randomI][randomJ] == 0) {
                arrayPosition[randomI][randomJ] = NumberBot;
                MoveBotF(randomI, randomJ);
                break;
            }
        }
    }
    private void MediumComplexity() {
        int canWin = NumberBot*2; int canLose = 10-canWin;
        for(int i = 0; i < 3; i++) {
            int loop = 0;
            for(int g = 0; g < 3; g++) {
                loop+=arrayPosition[i][g];
            }
            if(loop==canWin) {
                for(int g = 0; g < 3; g++) {
                    if(arrayPosition[i][g]==0) { arrayPosition[i][g]=NumberBot;  MoveBotF(i, g); return;}
                }
            }
        }
        for(int i = 0; i < 3; i++) {
            int loop = 0;
            for(int g = 0; g < 3; g++) {
                loop+=arrayPosition[g][i];
            }
            if(loop==canWin) {
                for(int g = 0; g < 3; g++) {
                    if(arrayPosition[g][i]==0) { arrayPosition[g][i]=NumberBot; MoveBotF(g, i); return;}
                }
            }
        }
        if(arrayPosition[0][0]+arrayPosition[1][1]+arrayPosition[2][2]==canWin) {
            if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
            if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
            if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
        }
        for(int i = 0; i < 3; i++) {
            int loop = 0;
            for(int g = 0; g < 3; g++) {
                loop+=arrayPosition[i][g];
            }
            if(loop==canLose) {
                for(int g = 0; g < 3; g++) {
                    if(arrayPosition[i][g]==0) { arrayPosition[i][g]=NumberBot;MoveBotF(i, g); return;}
                }
            }
        }
        for(int i = 0; i < 3; i++) {
            int loop = 0;
            for(int g = 0; g < 3; g++) {
                loop+=arrayPosition[g][i];
            }
            if(loop==canLose) {
                for(int g = 0; g < 3; g++) {
                    if(arrayPosition[g][i]==0) { arrayPosition[g][i]=NumberBot; MoveBotF(g, i);return;}
                }
            }
        }
        if(arrayPosition[0][0]+arrayPosition[1][1]+arrayPosition[2][2]==canLose) {
            if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
            if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
            if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
        }
        if(arrayPosition[0][2]+arrayPosition[1][1]+arrayPosition[2][0]==canLose) {
            if(arrayPosition[2][0]==0) {arrayPosition[2][0]=NumberBot; MoveBotF(2, 0);return;}
            if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
            if(arrayPosition[0][2]==0) {arrayPosition[0][2]=NumberBot; MoveBotF(0, 2); return;}
        }
        if(arrayPosition[1][0]==0) {arrayPosition[1][0]=NumberBot; MoveBotF(1, 0); return;}
        if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
        if(arrayPosition[0][2]==0) {arrayPosition[0][2]=NumberBot; MoveBotF(0, 2); return;}
        if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
        if(arrayPosition[2][0]==0) {arrayPosition[2][0]=NumberBot; MoveBotF(2, 0); return;}
        if(arrayPosition[1][2]==0) {arrayPosition[1][2]=NumberBot; MoveBotF(1, 2); return;}
        if(arrayPosition[2][1]==0) {arrayPosition[2][1]=NumberBot; MoveBotF(2, 1); return;}
    }
    private void HardComplexity() {
        if(arrayPosition[1][1]==0) { MoveIco.setText(moveBot); arrayPosition[1][1]=NumberBot; MoveBotF(1, 1);
        }
        else {
            int canWin = NumberBot*2; int canLose = 10-canWin;
            for(int i = 0; i < 3; i++) {
                int loop = 0;
                for(int g = 0; g < 3; g++) {
                    loop+=arrayPosition[i][g];
                }
                if(loop==canWin) {
                    for(int g = 0; g < 3; g++) {
                        if(arrayPosition[i][g]==0) { arrayPosition[i][g]=NumberBot;  MoveBotF(i, g); return;}
                    }
                }
            }
            for(int i = 0; i < 3; i++) {
                int loop = 0;
                for(int g = 0; g < 3; g++) {
                    loop+=arrayPosition[g][i];
                }
                if(loop==canWin) {
                    for(int g = 0; g < 3; g++) {
                        if(arrayPosition[g][i]==0) { arrayPosition[g][i]=NumberBot; MoveBotF(g, i); return;}
                    }
                }
            }
            if(arrayPosition[0][0]+arrayPosition[1][1]+arrayPosition[2][2]==canWin) {
                if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
                if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
                if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
            }
            if(arrayPosition[0][2]+arrayPosition[1][1]+arrayPosition[2][0]==canWin) {
                if(arrayPosition[2][0]==0) {arrayPosition[2][0]=NumberBot; MoveBotF(2, 0); return;}
                if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
                if(arrayPosition[0][2]==0) {arrayPosition[0][2]=NumberBot; MoveBotF(0, 2); return;}
            }
            for(int i = 0; i < 3; i++) {
                int loop = 0;
                for(int g = 0; g < 3; g++) {
                    loop+=arrayPosition[i][g];
                }
                if(loop==canLose) {
                    for(int g = 0; g < 3; g++) {
                        if(arrayPosition[i][g]==0) { arrayPosition[i][g]=NumberBot;MoveBotF(i, g); return;}
                    }
                }
            }
            for(int i = 0; i < 3; i++) {
                int loop = 0;
                for(int g = 0; g < 3; g++) {
                    loop+=arrayPosition[g][i];
                }
                if(loop==canLose) {
                    for(int g = 0; g < 3; g++) {
                        if(arrayPosition[g][i]==0) { arrayPosition[g][i]=NumberBot; MoveBotF(g, i);return;}
                    }
                }
            }
            if(arrayPosition[0][0]+arrayPosition[1][1]+arrayPosition[2][2]==canLose) {
                if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
                if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
                if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
            }
            if(arrayPosition[0][2]+arrayPosition[1][1]+arrayPosition[2][0]==canLose) {
                if(arrayPosition[2][0]==0) {arrayPosition[2][0]=NumberBot; MoveBotF(2, 0);return;}
                if(arrayPosition[1][1]==0) {arrayPosition[1][1]=NumberBot; MoveBotF(1, 1); return;}
                if(arrayPosition[0][2]==0) {arrayPosition[0][2]=NumberBot; MoveBotF(0, 2); return;}
            }
            if(arrayPosition[0][0]==0) {arrayPosition[0][0]=NumberBot; MoveBotF(0, 0); return;}
            if(arrayPosition[0][2]==0) {arrayPosition[0][2]=NumberBot; MoveBotF(0, 2); return;}
            if(arrayPosition[2][2]==0) {arrayPosition[2][2]=NumberBot; MoveBotF(2, 2); return;}
            if(arrayPosition[2][0]==0) {arrayPosition[2][0]=NumberBot; MoveBotF(2, 0); return;}
            if(arrayPosition[1][0]==0) {arrayPosition[1][0]=NumberBot; MoveBotF(1, 0); return;}
            if(arrayPosition[0][1]==0) {arrayPosition[0][1]=NumberBot; MoveBotF(0, 1); return;}
            if(arrayPosition[1][2]==0) {arrayPosition[1][2]=NumberBot; MoveBotF(1, 2); return;}
            if(arrayPosition[2][1]==0) {arrayPosition[2][1]=NumberBot; MoveBotF(2, 1); return;}
        }
    }
    private void MoveBotF(int x, int y) {
        MoveIco.setText(moveBot);
        ImageView position = searchPosition(x, y);
        if (moveBot.equals("X")) {
            position.setImageResource(R.drawable.x);
        } else {
            position.setImageResource(R.drawable.o);
        }
        arrayPosition[x][y] = NumberBot;
        MoveIco.setText(movePlayer);
        Tie+=1;
    }
    private void BotIf() {
        if (move.equals("SECOND")) {
            aiMove();
        }
    }
    private void animationWin(int i1,int i2,int i3,int i4,int i5,int i6,int numberwin) {
        ImageView positionW1 = searchPosition(i1, i2);
        ImageView positionW2 = searchPosition(i3, i4);
        ImageView positionW3 = searchPosition(i5, i6);
        if(numberwin==4){
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
                if(arrayPosition[i][0]==NumberPlayer){ animationWin(i,0,i,1,i,2,NumberPlayer); return 1;}
                else if(arrayPosition[i][0]==NumberBot){ animationWin(i,0, i,1,i,2,NumberBot); return 2;}
            }
        }
        for (int i=0;i<3;i++){
            if(arrayPosition[0][i]!=3 && arrayPosition[0][i]==arrayPosition[1][i] && arrayPosition[1][i]==arrayPosition[2][i]){
                if(arrayPosition[0][i]==NumberPlayer){animationWin(0,i,1,i,2,i,NumberPlayer);return 1;}
                else if(arrayPosition[0][i]==NumberBot){animationWin(0,i,1,i,2,i,NumberBot);return 2;}
            }
        }
        if(arrayPosition[0][0]!=3 && arrayPosition[0][0]==arrayPosition[1][1] && arrayPosition[1][1]==arrayPosition[2][2]){
            if(arrayPosition[1][1]==NumberPlayer){ animationWin(0,0,1,1,2,2,NumberPlayer);return 1;}
            else if(arrayPosition[1][1]==NumberBot){animationWin(0,0,1,1,2,2,NumberBot); return 2;}
        }
        if(arrayPosition[0][2]!=3 && arrayPosition[0][2]==arrayPosition[1][1] && arrayPosition[1][1]==arrayPosition[2][0]){
            if(arrayPosition[1][1]==NumberPlayer){ animationWin(0,2,1,1,2,0,NumberPlayer);return 1;}
            else if(arrayPosition[1][1]==NumberBot){animationWin(0,2,1,1,2,0,NumberBot); return 2;}
        }
        // tie
        if (Tie==9) {
            return -1;
        }
        return 0;
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
        MoveIco.setText(movePlayer);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                arrayPosition[i][j] = 0;
            }
        }
    }
    private void ScoreAndDialog(){
        if(checkWinner()==1){
            forScore(movePlayer, won, "win");
        }
        else if(checkWinner()==2){
            forScore(moveBot, loss, "loss");
        }
        else if(checkWinner()==-1){
            forScore("nobody", friendship_won, "fwon");
        }
    }
    private void forScore(String Player,String dialogTEXT, String SoundText){
        if(Player.equals("X")) {
            IntScoreX += 1;
            scoreX.setText(String.valueOf(IntScoreX));
            icoWinnerDialog.setImageResource(R.drawable.x);
        }
        else if(Player.equals("O")) {
            IntScoreT += 1;
            scoreT.setText(String.valueOf(IntScoreT));
            icoWinnerDialog.setImageResource(R.drawable.o);
        }
        else{
            icoWinnerDialog.setImageResource(R.drawable.friendship);
        }
        if(move.equals("FIRST")){
            move = "SECOND";
        }else{ move = "FIRST";}
        DialogText.setText(dialogTEXT);
        moveTrue = false;
        soundPlay(SoundText);
        btnDEnable(false);
        new Handler().postDelayed(() -> {
            if(flag) {
                Reload();
                dialogEnd.show();
            }
        }, 3000);
    }
    private void soundPlay(String sound){
        stopSound();
        if(settingsSWLF.equals("ON") || settingsSWLF.equals("ВКЛ")) {
            switch (sound) {
                case "win":
                    playerSound = MediaPlayer.create(this, R.raw.win);
                    break;
                case "loss":
                    playerSound = MediaPlayer.create(this, R.raw.loss);
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
            Intent intent = new Intent(easy_medium_hard_complexity.this, SingleGameSettings.class);
            intent.putExtra("soundBtn", settingsSOB);
            intent.putExtra("soundWLFW", settingsSWLF);
            startActivity(intent);finish();
        }catch (Exception ignored){
        }
    }
}
