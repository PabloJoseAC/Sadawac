package sadawac;

import arc.Console;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Main {

    ////////VARIABLES////////
    static long longTime;

    //SCREENS
    static int MAIN_MENU = 0;
    static int NAME_SELECTION = 1;
    static int HELP = 7;
    static int HIGHSCORES = 8;

    //BUTTONS
    static int MM_PLAY = 0;
    static int MM_HELP = 1;
    static int MM_HIGHSCORES = 2;
    static int MM_QUIT = 3;

    static int BUTTON_COUNT = MM_QUIT + 1;

    //GENERAL
    static Console con;
    static boolean boolRunning;
    static int intMouseX, intMouseY;
    static int intMouseButton;
    static char chrKey;
    static int intScreen = MAIN_MENU;
    static int intChangeScreen = -1;
    static int[][] intButtons;
    static String[] strButtonTexts;
    static Color[] clrButtonColors;

    //IMAGES
    static BufferedImage imgBackground;
    
    //COLORS
    static Color clrGray = new Color(153, 153, 153);
    static Color clrLightGray = new Color(0, 153, 153);
    static Color clrDarkGray = new Color(67, 67, 67);

    //FONTS
    static Font fntNormal;
    static Font fntMedium;
    static Font fntLarge;

    //MAIN MENU
    static BufferedImage[] imgTorch;
    static int intTorchAnim = 0;

    public static void main(String[] args) {
        //INITIALIZE SOME VARIABLES AND OTHER THINGS
        con = new Console("Sadawac", 800, 600);
        
        fntNormal = con.loadFont("src/res/berkshireswash-regular.ttf", 20);
        fntMedium = con.loadFont("src/res/berkshireswash-regular.ttf", 32);
        fntLarge = con.loadFont("src/res/berkshireswash-regular.ttf", 85);

        initButtons();
        loadImages();
        
        boolRunning = true;

        //MAIN GAME LOOP
        while(boolRunning == true){
            //UPDATE VARIABLES
            intMouseX = con.currentMouseX();
            intMouseY = con.currentMouseY();
            intMouseButton = con.currentMouseButton();
            chrKey = con.currentChar();
            longTime = System.currentTimeMillis();

            //IF YOU WANT TO SWAP SCREENS, WAIT FOR THE MOUSE BUTTON
            //TO BE RELEASED, THEN CHANGE SCREENS
            while(intChangeScreen != -1){
                intMouseButton = con.currentMouseButton();
                if(intMouseButton == 0){
                    intScreen = intChangeScreen;
                    intChangeScreen = -1;
                }
                con.sleep(5);
            }

            //CLEAR SCREEN
            con.setDrawColor(Color.WHITE);
            con.fillRect(0, 0, 800, 600);

            //TEST AND RUN CODE FOR EACH SCREEN
            if(intScreen == MAIN_MENU){
                mainMenu();
            }
            
            //REPAINT CONSOLE AND SLEEP
            con.repaint();
            con.sleep(5);
        }
        //EXIT GAME
        con.closeConsole();
    }

    public static void mainMenu(){
        //SET BUTTON COLORS TO DEFAULT
        for(int intCount = 0; intCount < 4; intCount++){
            clrButtonColors[intCount] = clrGray;
        }

        //CHECK IF BUTTON IS BEING HOVERED OVER
        if(buttonHovered(MM_PLAY)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //PLAY BUTTON WAS PRESSED, CHANGE TO Name Selection Screen (1)
                intChangeScreen = NAME_SELECTION;
                clrButtonColors[MM_PLAY] = clrLightGray;
            }else{
                clrButtonColors[MM_PLAY] = clrDarkGray;
            }
        }else if(buttonHovered(MM_HELP)){
            if(intMouseButton == 1){
                //HELP BUTTON WAS PRESSED, CHANGE TO Help Screen (7)
                intChangeScreen = HELP;
                clrButtonColors[MM_HELP] = clrLightGray;
            }else{
                clrButtonColors[MM_HELP] = clrDarkGray;
            }
        }else if(buttonHovered(MM_HIGHSCORES)){
            if(intMouseButton == 1){
                //PLAY BUTTON WAS PRESSED, CHANGE TO HighScores Screen (8)
                intChangeScreen = HIGHSCORES;
                clrButtonColors[MM_HIGHSCORES] = clrLightGray;
            }else{
                clrButtonColors[MM_HIGHSCORES] = clrDarkGray;
            }
        }else if(buttonHovered(MM_QUIT)){
            if(intMouseButton == 1){
                //QUIT BUTTON WAS PRESSED, SET boolRunning to FALSE
                boolRunning = false;
                clrButtonColors[MM_QUIT] = clrLightGray;
            }else{
                clrButtonColors[MM_QUIT] = clrDarkGray;
            }
        }

        //DRAW BACKGROUND
        con.drawImage(imgBackground, 0, 0);
        //DRAW TITLE AND SUBTITLE
        con.setDrawFont(fntLarge);
        con.drawString("SawadaC", 240, 140);
        con.setDrawFont(fntMedium);
        con.drawString("Pablo Jose Araujo Camacho", 210, 190);
        //DRAW BUTTONS
        con.setDrawFont(fntNormal);
        for(int intCount = 0; intCount < 4; intCount++){
            con.setDrawColor(clrButtonColors[intCount]);
            con.fillRect(intButtons[intCount][0], intButtons[intCount][1],
                    intButtons[intCount][2] - intButtons[intCount][0], intButtons[intCount][3] - intButtons[intCount][1]);
            con.setDrawColor(Color.WHITE);
            con.drawString(strButtonTexts[intCount], intButtons[intCount][4], intButtons[intCount][5]);
        }
    }

    public static boolean buttonHovered(int intButton){
        if((intButtons[intButton][0] < intMouseX && intButtons[intButton][2] > intMouseX) &&
                (intButtons[intButton][1] < intMouseY && intButtons[intButton][3] > intMouseY)){
            return true;
        }else{
            return false;
        }
    }

    public static void loadImages(){
        imgBackground = con.loadImage("src/res/Background.png");
        imgTorch = new BufferedImage[]{
            con.loadImage("src/res/Torch/torch0.png"),
            con.loadImage("src/res/Torch/torch1.png"),
            con.loadImage("src/res/Torch/torch2.png"),
            con.loadImage("src/res/Torch/torch3.png")
        };
    }

    public static void initButtons(){
        intButtons = new int[BUTTON_COUNT][6];
        strButtonTexts = new String[BUTTON_COUNT];
        clrButtonColors = new Color[BUTTON_COUNT];

        //MAIN MENU BUTTONS
        //PLAY
        intButtons[MM_PLAY][0] = 280;   //X COORD
        intButtons[MM_PLAY][1] = 220;   //Y COORD
        intButtons[MM_PLAY][2] = 515;   //X COORD 2
        intButtons[MM_PLAY][3] = 280;   //Y COORD 2
        intButtons[MM_PLAY][4] = 380;   //TEXT X COORD
        intButtons[MM_PLAY][5] = 255;   //TEXT Y COORD
        strButtonTexts[MM_PLAY] = "Play";
        //HELP
        intButtons[MM_HELP][0] = 280;
        intButtons[MM_HELP][1] = 300;
        intButtons[MM_HELP][2] = 515;
        intButtons[MM_HELP][3] = 360;
        intButtons[MM_HELP][4] = 380;
        intButtons[MM_HELP][5] = 335;
        strButtonTexts[MM_HELP] = "Help";
        //HIGHSCORES
        intButtons[MM_HIGHSCORES][0] = 280;
        intButtons[MM_HIGHSCORES][1] = 380;
        intButtons[MM_HIGHSCORES][2] = 515;
        intButtons[MM_HIGHSCORES][3] = 440;
        intButtons[MM_HIGHSCORES][4] = 350;
        intButtons[MM_HIGHSCORES][5] = 415;
        strButtonTexts[MM_HIGHSCORES] = "HighScores";
        //QUIT
        intButtons[MM_QUIT][0] = 280;
        intButtons[MM_QUIT][1] = 460;
        intButtons[MM_QUIT][2] = 515;
        intButtons[MM_QUIT][3] = 520;
        intButtons[MM_QUIT][4] = 380;
        intButtons[MM_QUIT][5] = 495;
        strButtonTexts[MM_QUIT] = "Quit";
    }

}
