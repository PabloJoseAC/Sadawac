package sadawac;

import arc.Console;

public class Main {

    ////////VARIABLES////////
    //SCREENS
    static int MAIN_MENU = 0;
    static int NAME_SELECTION = 1;

    //BUTTONS
    static int MM_PLAY = 0;
    static int MM_HELP = 1;
    static int MM_HIGHSCORES = 2;
    static int MM_QUIT = 3;

    //GENERAL
    static Console con;
    static boolean boolRunning = true;
    static int intMouseX, intMouseY;
    static int intMouseButton;
    static char chrKey;
    static int intScreen = MAIN_MENU;
    static int intChangeScreen = -1;
    static int[][] intButtons;
    static String[] strButtonTexts;

    public static void main(String[] args) {
        //MAIN GAME LOOP
        while(boolRunning == true){
            //UPDATE VARIABLES
            intMouseX = con.currentMouseX();
            intMouseY = con.currentMouseY();
            intMouseButton = con.currentMouseButton();
            chrKey = con.currentChar();

            //IF YOU WANT TO SWAP SCREENS, WAIT FOR THE MOUSE BUTTON
            //TO BE RELEASED, THEN CHANGE SCREENS
            while(intChangeScreen != -1){
                if(intMouseButton == 0){
                    intScreen = intChangeScreen;
                    intChangeScreen = -1;
                }
                con.sleep(5);
            }

            //TEST AND RUN CODE FOR EACH SCREEN
            if(intScreen == MAIN_MENU){
                if(buttonHovered(MM_PLAY)){
                    if(intMouseButton == 1){
                        //PLAY BUTTON WAS PRESSED, CHANGE TO Name Selection Screen (1)
                        intChangeScreen = NAME_SELECTION;
                    }
                }
            }
            //REPAINT CONSOLE AND SLEEP
            con.repaint();
            con.sleep(5);
        }
    }

    public static boolean buttonHovered(int intButton){
        if((intButtons[intButton][0] < intMouseX && intButtons[intButton][1] > intMouseX) &&
                (intButtons[intButton][2] < intMouseY && intButtons[intButton][3] > intMouseY)){
            return true;
        }else{
            return false;
        }
    }

}
