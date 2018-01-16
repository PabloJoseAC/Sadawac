package sawadac;

import arc.Console;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Main {

    ////////VARIABLES////////
    static long longTime;

    //SCREENS
    static int MAIN_MENU = 0;
    static int USER_SELECTION = 1;
    static int CHARACTER_SELECTION = 2;
    static int MAP_SELECTION = 3;
    static int HELP = 7;
    static int HIGHSCORES = 8;

    //BUTTONS
    static int MM_PLAY = 0;
    static int MM_HELP = 1;
    static int MM_HIGHSCORES = 2;
    static int MM_QUIT = 3;

    static int US_NAMEINPUT = 4;
    static int US_BACK = 5;
    static int US_CONTINUE = 6;
    
    static int CS_LEFT = 7;
    static int CS_RIGHT = 8;
    static int CS_CONTINUE = 9;
    
    static int BUTTON_COUNT = CS_CONTINUE + 1;

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
    static String strName = "";
    static String[][] strAbilities;

    //IMAGES
    static BufferedImage imgBackground;
    
    //COLORS
    static Color clrGray = new Color(153, 153, 153);
    static Color clrLightGray = new Color(217, 217, 217);
    static Color clrDarkGray = new Color(67, 67, 67);
    static Color clrGreen = new Color(127, 190, 83);

    //FONTS
    static Font fntNormal;
    static Font fntMedium;
    static Font fntLarge;

    //MAIN MENU
    static BufferedImage[] imgTorch;
    static int intTorchAnim = 0;
    
    //USER SELECTION
    static char chrLastKey;
    static boolean boolTyping;
    
    //CHARACTER SELECTION
    static int intCharacter;
    static String strCharacterName;
    static String[] strCharacters;

    public static void main(String[] args) {
        //INITIALIZE SOME VARIABLES AND OTHER THINGS
        con = new Console("Sadawac", 800, 600);
        
        fntNormal = con.loadFont("src/res/berkshireswash-regular.ttf", 20);
        fntMedium = con.loadFont("src/res/berkshireswash-regular.ttf", 32);
        fntLarge = con.loadFont("src/res/berkshireswash-regular.ttf", 85);

        initButtons();
        loadImages();
        loadPaths();
        
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
            }else if(intScreen == USER_SELECTION){
            	userSelection();
            }else if(intScreen == CHARACTER_SELECTION){
            	characterSelection();
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
        resetButtonColors(MM_PLAY, MM_QUIT);

        //CHECK IF BUTTON IS BEING HOVERED OVER
        if(buttonHovered(MM_PLAY)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //PLAY BUTTON WAS PRESSED, CHANGE TO Name Selection Screen (1)
                intChangeScreen = USER_SELECTION;
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
        drawButtons(MM_PLAY, MM_QUIT);
    }
    
    public static void userSelection(){
    	//RESET BUTTON COLORS
    	resetButtonColors(US_NAMEINPUT, US_CONTINUE);
    	
    	//CHECK IF BUTTON IS BEING HOVERED OVER
        if(buttonHovered(US_NAMEINPUT)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //NAME INPUT WAS CLICKED ON, READ USERNAME
            	boolTyping = true;
            }else{
                clrButtonColors[US_NAMEINPUT] = clrDarkGray;
            }
        }else if(buttonHovered(US_BACK)){
            if(intMouseButton == 1){
                //BACK BUTTON WAS PRESSED, CHANGE TO THE Main Menu Screen (0)
                intChangeScreen = MAIN_MENU;
                clrButtonColors[US_BACK] = clrLightGray;
            }else{
                clrButtonColors[US_BACK] = clrDarkGray;
            }
        }else if(buttonHovered(US_CONTINUE)){
            if(!strName.isEmpty()){
            	if(intMouseButton == 1){
                    //CONTINUE BUTTON WAS PRESSED, CHANGE TO Character Selection Screen (2)
                    intChangeScreen = CHARACTER_SELECTION;
                    clrButtonColors[US_CONTINUE] = clrLightGray;
                }else{
                    clrButtonColors[US_CONTINUE] = clrDarkGray;
                }
            }
        }
        
        if(boolTyping){
        	clrButtonColors[US_NAMEINPUT] = clrLightGray;
        	//MAKE SURE IT DOESNT DETECT KEYS THAT ARE HELD DOWN
            if(chrKey != chrLastKey){
                //CHECK IF THE KEY PRESSED IS A LETTER OR NUMBER USING ASCII CHARACTERS
                if((chrKey >= 48 && chrKey <= 57) || (chrKey >= 65 && chrKey <= 90)
                        || (chrKey >= 97 && chrKey <= 122)){
                	//DO NOT ADD LETTER TO NAME IF IT IS LONGER THAN 10 LETTERS
                	if(strButtonTexts[US_NAMEINPUT].length() < 10){
                		strButtonTexts[US_NAMEINPUT] += chrKey;
                		intButtons[US_NAMEINPUT][6] = strButtonTexts[US_NAMEINPUT].length() * 5;
                	}
                //CHECK IF THE KEY PRESSED IS THE BACKSPACE KEY
                }else if(chrKey == 8){
                    //TAKE AWAY THE LAST LETTER FROM THE NAME
                    int intStrLength = strButtonTexts[US_NAMEINPUT].length() - 1;
                    if(intStrLength < 0){
                        intStrLength = 0;
                    }
                    strButtonTexts[US_NAMEINPUT] = strButtonTexts[US_NAMEINPUT].substring(0, intStrLength);
                    intButtons[US_NAMEINPUT][6] = strButtonTexts[US_NAMEINPUT].length() * 5;
                //CHECK IF THE KEY PRESSED IS THE ENTER KEY
                }else if(chrKey == 10){
                    strName = strButtonTexts[US_NAMEINPUT];
                	boolTyping = false;
                //CHECK IF THE KEY PRESSED IS THE ESC KEY
                }else if(chrKey == 27){
                	strButtonTexts[US_NAMEINPUT] = "";
                	strName = strButtonTexts[US_NAMEINPUT];
                	intButtons[US_NAMEINPUT][6] = 0;
                    boolTyping = false;
                }
                //SET LAST KEY TO THIS KEY
                chrLastKey = chrKey;
            }
        }
        
        //SET CONTINUE BUTTON TO LIGHT GRAY IF A NAME HASN'T BEEN SET
        if(strName.isEmpty()){
        	clrButtonColors[US_CONTINUE] = clrLightGray;
        }
    	
    	//DRAW BACKGROUND
        con.drawImage(imgBackground, 0, 0);
        //DRAW INSTRUCTIONS
        con.setDrawFont(fntMedium);
        con.drawString("Click below and enter a new username", 140, 170);
        con.drawString("Press Enter to set your username", 180, 210);
        //DRAW BUTTONS
        drawButtons(US_NAMEINPUT, US_CONTINUE);
    }
    
    public static void characterSelection(){
    	//RESET BUTTON COLORS
    	resetButtonColors(CS_LEFT, CS_CONTINUE);
    	
    	//CHECK IF BUTTON IS BEING HOVERED OVER
        if(buttonHovered(CS_LEFT)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //LEFT ARROW WAS CLICKED, CHANGE TO A DIFFERENT CHARACTER
            	if(intCharacter == 0){
            		intCharacter = strCharacters.length - 1;
            	}else{
            		intCharacter--;
            	}
            	loadCharacter(intCharacter);
            	clrButtonColors[CS_LEFT] = clrLightGray;
            }else{
                clrButtonColors[CS_LEFT] = clrDarkGray;
            }
        }else if(buttonHovered(CS_RIGHT)){
        	//CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //RIGHT ARROW WAS CLICKED, CHANGE TO A DIFFERENT CHARACTER
            	if(intCharacter == strCharacters.length - 1){
            		intCharacter = 0;
            	}else{
            		intCharacter++;
            	}
            	loadCharacter(intCharacter);
            	clrButtonColors[CS_RIGHT] = clrLightGray;
            }else{
                clrButtonColors[CS_RIGHT] = clrDarkGray;
            }
        }else if(buttonHovered(CS_CONTINUE)){
        	//CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //CONTINUE WAS CLICKED, GO TO THE Map Selection Screen (3)
            	intChangeScreen = MAP_SELECTION;
            	clrButtonColors[CS_CONTINUE] = clrLightGray;
            }else{
                clrButtonColors[CS_CONTINUE] = clrDarkGray;
            }
        }
    	
    	//DRAW BACKGROUND
        con.drawImage(imgBackground, 0, 0);
        //DRAW GREEN SQUARE
        con.setDrawColor(clrGreen);
        con.fillRect(290, 135, 510 - 290, 350 - 135);
        //DRAW BUTTONS
        drawButtons(CS_LEFT, CS_CONTINUE);
        //DRAW LEFT AND RIGHT BUTTON ARROWS
        con.setDrawColor(Color.WHITE);
        con.fillPolygon(new int[]{155, 210, 210}, new int[]{245, 275, 215}, 3);
        con.fillPolygon(new int[]{590, 590, 645}, new int[]{215, 275, 245}, 3);
    }
    
    public static void loadCharacter(int intCharacterId){
    	
    }
    
    public static boolean buttonHovered(int intButton){
        if((intButtons[intButton][0] < intMouseX && intButtons[intButton][2] > intMouseX) &&
                (intButtons[intButton][1] < intMouseY && intButtons[intButton][3] > intMouseY)){
            return true;
        }else{
            return false;
        }
    }
    
    public static void drawButtons(int intStart, int intEnd){
    	con.setDrawFont(fntNormal);
        for(int intCount = intStart; intCount < intEnd + 1; intCount++){
            con.setDrawColor(clrButtonColors[intCount]);
            con.fillRect(intButtons[intCount][0], intButtons[intCount][1],
                    intButtons[intCount][2] - intButtons[intCount][0], intButtons[intCount][3] - intButtons[intCount][1]);
            con.setDrawColor(Color.WHITE);
            con.drawString(strButtonTexts[intCount], intButtons[intCount][4] - intButtons[intCount][6], intButtons[intCount][5]);
        }
    }
    
    public static void resetButtonColors(int intStart, int intEnd){
    	for(int intCount = intStart; intCount < intEnd + 1; intCount++){
            clrButtonColors[intCount] = clrGray;
        }
    }

    public static void loadImages(){
        imgBackground = con.loadImage("src/res/Background.png");
        /*imgTorch = new BufferedImage[]{
            con.loadImage("src/res/Torch/torch0.png"),
            con.loadImage("src/res/Torch/torch1.png"),
            con.loadImage("src/res/Torch/torch2.png"),
            con.loadImage("src/res/Torch/torch3.png")
        };*/
    }

    public static void loadPaths(){
    	strCharacters = new String[]{
    		"src/res/Characters/KillerKiara.txt"
    	};
    }
    
    public static void initButtons(){
        intButtons = new int[BUTTON_COUNT][7];
        strButtonTexts = new String[BUTTON_COUNT];
        clrButtonColors = new Color[BUTTON_COUNT];

        //MAIN MENU//
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
        
        //USER SELECTION//
        //NAMEINPUT
        intButtons[US_NAMEINPUT][0] = 240;
        intButtons[US_NAMEINPUT][1] = 270;
        intButtons[US_NAMEINPUT][2] = 560;
        intButtons[US_NAMEINPUT][3] = 330;
        intButtons[US_NAMEINPUT][4] = 400;
        intButtons[US_NAMEINPUT][5] = 310;
        strButtonTexts[US_NAMEINPUT] = "";
        
        //BACK
        intButtons[US_BACK][0] = 140;
        intButtons[US_BACK][1] = 390;
        intButtons[US_BACK][2] = 370;
        intButtons[US_BACK][3] = 450;
        intButtons[US_BACK][4] = 230;
        intButtons[US_BACK][5] = 430;
        strButtonTexts[US_BACK] = "Back";
        
        //CONTINUE
        intButtons[US_CONTINUE][0] = 430;
        intButtons[US_CONTINUE][1] = 390;
        intButtons[US_CONTINUE][2] = 660;
        intButtons[US_CONTINUE][3] = 450;
        intButtons[US_CONTINUE][4] = 500;
        intButtons[US_CONTINUE][5] = 430;
        strButtonTexts[US_CONTINUE] = "Continue";
        
        //CHARACTER SELECTION//
        //LEFT
        intButtons[CS_LEFT][0] = 140;
        intButtons[CS_LEFT][1] = 200;
        intButtons[CS_LEFT][2] = 230;
        intButtons[CS_LEFT][3] = 290;
        strButtonTexts[CS_LEFT] = "";
        
        //RIGHT
        intButtons[CS_RIGHT][0] = 570;
        intButtons[CS_RIGHT][1] = 200;
        intButtons[CS_RIGHT][2] = 660;
        intButtons[CS_RIGHT][3] = 290;
        strButtonTexts[CS_RIGHT] = "";
        
        //CONTINUE
        intButtons[CS_CONTINUE][0] = 280;
        intButtons[CS_CONTINUE][1] = 490;
        intButtons[CS_CONTINUE][2] = 515;
        intButtons[CS_CONTINUE][3] = 550;
        intButtons[CS_CONTINUE][4] = 355;
        intButtons[CS_CONTINUE][5] = 525;
        strButtonTexts[CS_CONTINUE] = "Continue";
    }

}
