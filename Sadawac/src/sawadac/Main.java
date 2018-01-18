package sawadac;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import arc.Console;
import arc.TextInputFile;

public class Main {

    ////////VARIABLES////////
    static long longTime;

    //SCREENS
    static int MAIN_MENU = 0;
    static int USER_SELECTION = 1;
    static int CHARACTER_SELECTION = 2;
    static int MAP_SELECTION = 3;
    static int GAME = 4;
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
    static int CS_ABILITIES = 10;

    static int MS_LEFT = 11;
    static int MS_RIGHT = 12;
    static int MS_CONTINUE = 13;
    
    static int BUTTON_COUNT = MS_CONTINUE + 1;

    //GENERAL
    static Console con;
    static boolean boolRunning;
    static int intMouseX, intMouseY;
    static int intMouseButton;
    static char chrKey;
    static int intScreen = MAIN_MENU;
    static boolean boolWaitForRelease = false;
    static int[][] intButtons;
    static String[] strButtonTexts;
    static Color[] clrButtonColors;
    static String strName = "";
    static String[][] strAbilities;
    static String[][] strMap;
    static int[][] intEnemies;
    static int[][] intBoosts;

    //IMAGES
    static BufferedImage imgBackground;
    static BufferedImage[][] imgCharacterImgs;
    
    static int IDLE = 0;
    static int MOVE = 1;
    static int ATTACK = 3;
    
    //COLORS
    static Color clrGray = new Color(153, 153, 153);
    static Color clrLightGray = new Color(217, 217, 217);
    static Color clrDarkGray = new Color(67, 67, 67);
    static Color clrGreen = new Color(127, 190, 83);
    static Color clrExplore = new Color(192, 213, 182);
    static Color clrFight = new Color(238, 146, 157);
    static Color clrUltimate = new Color(244, 226, 154);

    //FONTS
    static Font fntNormal;
    static Font fntMedium;
    static Font fntMedium2;
    static Font fntLarge;

    //MAIN MENU
    static BufferedImage[] imgTorch;
    static int intTorchAnim = 0;
    
    //USER SELECTION
    static char chrLastKey;
    static boolean boolTyping;
    
    //CHARACTER SELECTION
    static int intCharacter;
    static String strCharacterName = "";
    static String[] strCharacters;

    static int NAME = 0;
    static int TYPE = 1;
    static int DESC = 2;
    static int ENERGY = 3;
    static int ACTIONTYPE = 4;
    static int ACTION = 5;
    static int TURNS = 6;

    //MAP SELECTION
    static String[] strMaps;
    static int intMap;
    static String strMapName = "";
    static int intEnemyCount;
    static int intBossCount;
    static int intBoostCount;

    //GAME
    static int intPlayerX, intPlayerY;
    
    public static void main(String[] args) {
        //INITIALIZE SOME VARIABLES AND OTHER THINGS
        con = new Console("Sadawac", 800, 600);
        
        fntNormal = con.loadFont("src/res/berkshireswash-regular.ttf", 20);
        fntMedium = con.loadFont("src/res/berkshireswash-regular.ttf", 32);
        fntMedium2 = con.loadFont("src/res/berkshireswash-regular.ttf", 45);
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
            while(boolWaitForRelease){
                intMouseButton = con.currentMouseButton();
                if(intMouseButton == 0){
                    boolWaitForRelease = false;
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
            }else if(intScreen == MAP_SELECTION){
            	mapSelection();
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
                intScreen = USER_SELECTION;
                boolWaitForRelease = true;
                clrButtonColors[MM_PLAY] = clrLightGray;
            }else{
                clrButtonColors[MM_PLAY] = clrDarkGray;
            }
        }else if(buttonHovered(MM_HELP)){
            if(intMouseButton == 1){
                //HELP BUTTON WAS PRESSED, CHANGE TO Help Screen (7)
                intScreen = HELP;
                boolWaitForRelease = true;
                clrButtonColors[MM_HELP] = clrLightGray;
            }else{
                clrButtonColors[MM_HELP] = clrDarkGray;
            }
        }else if(buttonHovered(MM_HIGHSCORES)){
            if(intMouseButton == 1){
                //PLAY BUTTON WAS PRESSED, CHANGE TO HighScores Screen (8)
                intScreen = HIGHSCORES;
                boolWaitForRelease = true;
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
                intScreen = MAIN_MENU;
                boolWaitForRelease = true;
                clrButtonColors[US_BACK] = clrLightGray;
            }else{
                clrButtonColors[US_BACK] = clrDarkGray;
            }
        }else if(buttonHovered(US_CONTINUE)){
            if(!strName.isEmpty()){
            	if(intMouseButton == 1){
                    //CONTINUE BUTTON WAS PRESSED, CHANGE TO Character Selection Screen (2)
                    intScreen = CHARACTER_SELECTION;
                    boolWaitForRelease = true;
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

        //LOAD THE CHARACTER WHEN USER FIRST ENTERS SCREEN
        if(strCharacterName.isEmpty()){
            loadCharacter(intCharacter);
        }

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
                boolWaitForRelease = true;
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
                boolWaitForRelease = true;
            }else{
                clrButtonColors[CS_RIGHT] = clrDarkGray;
            }
        }else if(buttonHovered(CS_CONTINUE)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //CONTINUE WAS CLICKED, GO TO THE Map Selection Screen (3)
            	intScreen = MAP_SELECTION;
                boolWaitForRelease = true;
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
        //DRAW CHARACTER NAME
        con.setDrawFont(fntMedium);
        con.drawString(strCharacterName, 380 - (strCharacterName.length() * 5), 180);
        //DRAW CHARACTER
        con.drawImage(imgCharacterImgs[IDLE][0], 340, 200);
        //DRAW INSTRUCTIONS & OTHER
        con.setDrawFont(fntMedium);
        con.drawString("Abilities", 340, 400);
        con.drawString("Select your Character, each one has", 165, 60);
        con.drawString("unique abilities", 295, 100);
        //DRAW ABILITIES
        for(int intCount = 0; intCount < strAbilities.length; intCount++){
        	con.setDrawColor(getAbilityColor(intCount));
        	con.drawString(strAbilities[intCount][NAME], 180 + (intCount * 100) +
        			(strAbilities[Math.max(0, intCount - 1)][NAME].length() * 5), 450);
        }
        //DRAW ABILITY INFORMATION WHEN THEY ARE HOVERED OVER
        if(buttonHovered(CS_ABILITIES)){
            con.setDrawFont(fntNormal);
            int intCount = 0;
            for(int intCountY = 0; intCountY < strAbilities.length / 2; intCountY++){
                for(int intCountX = 0; intCountX < strAbilities.length / 2; intCountX++){
                    con.setDrawColor(Color.WHITE);
                    con.fillRect(110 + (300 * intCountX), 120 + (150 * intCountY), 280, 120);
                    con.setDrawColor(getAbilityColor(intCount));
                    con.drawString(strAbilities[intCount][TYPE], 110 + 10 + (300 * intCountX), 150 + (150 * intCountY));
                    con.setDrawColor(Color.BLACK);
                    con.drawString(strAbilities[intCount][DESC], 110 + 10 + (300 * intCountX), 150 + 25 + (150 * intCountY));
                    con.setDrawColor(Color.CYAN);
                    con.drawString(strAbilities[intCount][ENERGY] + " Energy", 110 + 10 + (300 * intCountX), 150 + 50 + (150 * intCountY));
                    con.setDrawColor(Color.RED);
                    con.drawString(getAbilityActionDesc(intCount), 110 + 10 + (300 * intCountX), 150 + 75 + (150 * intCountY));
                    intCount++;
                }
            }
        }
    }

    public static void mapSelection(){
        //RESET BUTTON COLORS
    	resetButtonColors(MS_LEFT, MS_CONTINUE);

        //LOAD THE MAP WHEN USER FIRST ENTERS SCREEN
        if(strMapName.isEmpty()){
            loadMap(intMap);
        }

        //CHECK IF BUTTON IS BEING HOVERED OVER
        if(buttonHovered(MS_LEFT)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //LEFT ARROW WAS CLICKED, CHANGE TO A DIFFERENT CHARACTER
            	if(intMap == 0){
                    intMap = strMaps.length - 1;
            	}else{
                    intMap--;
            	}
            	loadMap(intMap);
            	clrButtonColors[MS_LEFT] = clrLightGray;
                boolWaitForRelease = true;
            }else{
                clrButtonColors[MS_LEFT] = clrDarkGray;
            }
        }else if(buttonHovered(MS_RIGHT)){
        	//CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //RIGHT ARROW WAS CLICKED, CHANGE TO A DIFFERENT CHARACTER
            	if(intMap == strMaps.length - 1){
                    intMap = 0;
            	}else{
                    intMap++;
            	}
            	loadMap(intMap);
            	clrButtonColors[MS_RIGHT] = clrLightGray;
                boolWaitForRelease = true;
            }else{
                clrButtonColors[MS_RIGHT] = clrDarkGray;
            }
        }else if(buttonHovered(MS_CONTINUE)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
                //CONTINUE WAS CLICKED, GO TO THE Map Selection Screen (3)
            	intScreen = GAME;
                boolWaitForRelease = true;
            	clrButtonColors[MS_CONTINUE] = clrLightGray;
            }else{
                clrButtonColors[MS_CONTINUE] = clrDarkGray;
            }
        }

        //DRAW BACKGROUND
        con.drawImage(imgBackground, 0, 0);
        //DRAW BUTTONS
        drawButtons(MS_LEFT, MS_CONTINUE);
        //DRAW LEFT AND RIGHT BUTTON ARROWS
        con.setDrawColor(Color.WHITE);
        con.fillPolygon(new int[]{155, 210, 210}, new int[]{215, 245, 185}, 3);
        con.fillPolygon(new int[]{590, 590, 645}, new int[]{185, 245, 215}, 3);
        //DRAW MAP NAME
        con.setDrawFont(fntMedium2);
        con.drawString(strMapName, 340 - (strMapName.length() * 5), 230);
        //DRAW INSTRUCTIONS & OTHER
        con.setDrawFont(fntMedium);
        con.drawString("Select the Map you'd like to play in", 165, 120);
        //DRAW NUMBER OF ENEMIES, BOSSES, AND BOOSTS
        con.setDrawColor(clrFight);
        con.drawString(intEnemyCount + " Enemies", 330, 320);
        con.setDrawColor(clrExplore);
        con.drawString(intBoostCount + " Boosts", 330, 370);
        con.setDrawColor(clrUltimate);
        con.drawString(intBossCount + " Bosses", 330, 420);
    }

    public static void loadMap(int intMapId){
        //OPEN TEXT FILE WITH CHARACTER INFO
        TextInputFile inputFile = new TextInputFile(strMaps[intMapId]);
        //INITIALIZE MAP VARIABLES
        intEnemyCount = 0;
        intBossCount = 0;
        intBoostCount = 0;
        strMap = new String[20][20];
        //READ TO END OF FILE
        int intCount = 0;
        while(!inputFile.eof()){
            if(intCount == 0){
                //FIRST LINE IS MAP NAME
                strMapName = inputFile.readLine();
            }else{
                //READ MAP ROW
                String strCurrLine = inputFile.readLine();
                for(int intCount2 = 0; intCount2 < 20; intCount2++){
                    String strCurrTile = strCurrLine.substring(intCount2, intCount2);
                    //KEEP COUNT OF ENEMIES, BOSSES, AND BOOSTS/COLLECTIBLES
                    if(strCurrTile.equalsIgnoreCase("E")){
                        intEnemyCount++;
                    }else if(strCurrTile.equalsIgnoreCase("B")){
                        intBossCount++;
                    }else if(strCurrTile.equalsIgnoreCase("C")){
                        intBoostCount++;
                    }else if(strCurrTile.equalsIgnoreCase("S")){
                        //SET THE PLAYER START TILE
                        intPlayerX = intCount2;
                        intPlayerY = intCount - 1;
                        //SET THIS TILE TO GRASS
                        strCurrTile = "G";
                    }
                    //LOAD CURRENT TILE INTO strMap ARRAY
                    strMap[intCount - 1][intCount2] = strCurrTile;
                }
            }
            intCount++;
        }
        //INITIALIZE intEnemies AND intBoosts ARRAYS BASED ON THE NUMBERS RECORDED
        intEnemies = new int[intEnemyCount + intBossCount][5];
        intBoosts = new int[intBoostCount][3];
        //LOOP THROUGH MAP AGAIN, REPLACING ENEMIES AND ADDING THEM AND THE BOOSTS INTO THE ARRAYS
        int intEnemyCounter = 0;
        int intBoostCounter = 0;
        for(int intCountY = 0; intCountY < 20; intCountY++){
            for(int intCountX = 0; intCountX < 20; intCountX++){
                String strCurrTile = strMap[intCountY][intCountX];
                if(strCurrTile.equalsIgnoreCase("E")){
                    int intType = (int) Math.round(Math.random() * 5);
                    //1 IN 5 CHANCES FOR THE ENEMY TO BE A STRONGER ENEMY
                    if((intType % 5) == 0){
                        //ADD ENEMY TO intEnemies ARRAY
                        intEnemies[intEnemyCounter] = new int[]{
                            //X, Y, HEALTH, MAX HEALTH, TYPE
                            intCountX, intCountY, 150, 150, 1
                        };
                    }else{
                        intEnemies[intEnemyCounter] = new int[]{
                            intCountX, intCountY, 100, 100, 0
                        };
                    }
                    //SET THE CURRENT TILE TO A GRASS TILE
                    strMap[intCountY][intCountX] = "G";
                    intEnemyCounter++;
                }else if(strCurrTile.equalsIgnoreCase("B")){
                    //ADD BOSS TO intEnemies ARRAY
                    intEnemies[intEnemyCounter] = new int[]{
                        intCountX, intCountY, 300, 300, 2
                    };
                    //SET THE CURRENT TILE TO A GRASS TILE
                    strMap[intCountY][intCountX] = "G";
                    intEnemyCounter++;
                }else if(strCurrTile.equalsIgnoreCase("C")){
                    //CHOOSE A TYPE OF BOOST AT RANDOM; MORE MAX HEALTH, MORE MAX ENERGY, MORE DAMAGE
                    int intType = (int) Math.round(Math.random() * 3);
                    intBoosts[intBoostCounter] = new int[]{
                        intCountX, intCountY, intType
                    };
                    //SET THE CURRENT TILE TO A GRASS TILE
                    strMap[intCountY][intCountX] = "G";
                    intBoostCounter++;
                }
            }
        }
    }
    
    public static void loadCharacter(int intCharacterId){
    	//OPEN TEXT FILE WITH CHARACTER INFO
        TextInputFile inputFile = new TextInputFile(strCharacters[intCharacterId]);
        //INITIALIZE CHARACTER ARRAYS
        strAbilities = new String[4][7];
        imgCharacterImgs = new BufferedImage[3][5];
        //READ TO END OF FILE
        while(!inputFile.eof()){
            //READ LINE
            String strLine = inputFile.readLine();
            //SPLIT LINE INTO THE DIFFERENT PARTS; THE "IDENTIFIER" AND THE "VALUE"
            String[] strLineParts = strLine.split(":");
            //IF THE IDENTIFIER IS 'NAME' SET strCharacterName TO ITS VALUE
            if(strLineParts[0].equalsIgnoreCase("Name")){
                strCharacterName = strLineParts[1];
            }else if(strLineParts[0].equalsIgnoreCase("Images")){
                //LOOP THROUGH THE DIFFERENT ANIMATIONS
                for(int intCount = 1; intCount < 3; intCount++){
                    if(strLineParts.length > intCount){
                        //FIND WHAT THE START AND END IMAGES OF THE ANIMATION ARE
                        String[] strImageRange = strLineParts[intCount].split("-");
                        int intStart = Integer.parseInt(strImageRange[0]);
                        int intEnd = Integer.parseInt(strImageRange[1]);
                        //LOOP THROUGH THE IMAGES
                        for(int intCount2 = intStart; intCount2 <= intEnd; intCount2++){
                            //LOAD THE IMAGES INTO THE imgCharacterImgs ARRAY
                            imgCharacterImgs[intCount - 1][intCount2 - intStart] = con.loadImage("src/res/Characters/" +
                                        strCharacterName + "/" + intCount2 + ".png");
                        }
                    }
                }
            }else{
                //LOAD THE ABILITY ATTRIBUTE BASED ON THE ABILITY ID (FIRST NUMBER)
                //AND THE IDENTIFIER OF THE ATTRIBUTE INTO THE strAbilities ARRAY
                int intAbilityId = Integer.parseInt(strLineParts[0]);
                if(strLineParts[1].equalsIgnoreCase("Name")){
                    strAbilities[intAbilityId][NAME] = strLineParts[2];
                }else if(strLineParts[1].equalsIgnoreCase("Type")){
                    strAbilities[intAbilityId][TYPE] = strLineParts[2];
                }else if(strLineParts[1].equalsIgnoreCase("Description")){
                    strAbilities[intAbilityId][DESC] = strLineParts[2];
                }else if(strLineParts[1].equalsIgnoreCase("Energy")){
                    strAbilities[intAbilityId][ENERGY] = strLineParts[2];
                }else if(strLineParts[1].equalsIgnoreCase("Action")){
                    strAbilities[intAbilityId][ACTIONTYPE] = strLineParts[2];
                    strAbilities[intAbilityId][ACTION] = strLineParts[3];
                }else if(strLineParts[1].equalsIgnoreCase("Turns")){
                    strAbilities[intAbilityId][TURNS] = strLineParts[2];
                }
            }
        }
        //CLOSE FILE
        inputFile.close();
    }
    
    public static String getAbilityActionDesc(int intAbilityId){
    	String strReturn = "";
    	String strActionType = strAbilities[intAbilityId][ACTIONTYPE]; 
    	//RETURN DESCRIPTION BASED ON THE ACTION THE ABILITY DOES
        if(strActionType.equalsIgnoreCase("Damage")){
    		strReturn += "Deals " + strAbilities[intAbilityId][ACTION] + " damage";
    	}else if(strActionType.equalsIgnoreCase("DamageBoost")){
    		strReturn += strAbilities[intAbilityId][ACTION] + "x the damage";
    	}else if(strActionType.equalsIgnoreCase("Move")){
    		strReturn += "Move " + strAbilities[intAbilityId][ACTION] + " tiles";
    	}

        //IF ABILITY LASTS MULTIPLE TURNS, SAY THAT AT THE END
    	String strTurns = strAbilities[intAbilityId][TURNS];
    	if(strTurns != null && !strTurns.isEmpty()){
    		strReturn += " for " + strTurns + " turns";
    	}
        
    	return strReturn;
    }
    
    public static Color getAbilityColor(int intAbilityId){
    	//RETURN CORRECT COLOR BASED ON ABILITY
        String strAbilityType = strAbilities[intAbilityId][TYPE];
    	if(strAbilityType.equalsIgnoreCase("Explore")){
    		return clrExplore;
    	}else if(strAbilityType.equalsIgnoreCase("Fight")){
    		return clrFight;
    	}else if(strAbilityType.equalsIgnoreCase("Ultimate")){
    		return clrUltimate;
    	}else{
    		return Color.WHITE;
    	}
    }
    
    public static boolean buttonHovered(int intButton){
        //TEST IF MOUSE IS INSIDE BUTTON
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
    	//RESETS BUTTON COLORS TO THE DEFAULT GRAY
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
    	//CHARACTER FILE LOCATIONS
        strCharacters = new String[]{
            "src/res/Characters/KillerKiara.txt",
            "src/res/Characters/BobTheBuilder.txt"
    	};
        //MAP FILE LOCATIONS
        strMaps = new String[]{
            "src/res/Maps/EverGreen.txt"
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
        
        //ABILITIES
        intButtons[CS_ABILITIES][0] = 150;
        intButtons[CS_ABILITIES][1] = 400;
        intButtons[CS_ABILITIES][2] = 670;
        intButtons[CS_ABILITIES][3] = 480;

        //MAP SELECTION//
        //LEFT
        intButtons[MS_LEFT][0] = 140;
        intButtons[MS_LEFT][1] = 170;
        intButtons[MS_LEFT][2] = 230;
        intButtons[MS_LEFT][3] = 260;
        strButtonTexts[MS_LEFT] = "";

        //RIGHT
        intButtons[MS_RIGHT][0] = 570;
        intButtons[MS_RIGHT][1] = 170;
        intButtons[MS_RIGHT][2] = 660;
        intButtons[MS_RIGHT][3] = 260;
        strButtonTexts[MS_RIGHT] = "";

        //CONTINUE
        intButtons[MS_CONTINUE][0] = 280;
        intButtons[MS_CONTINUE][1] = 480;
        intButtons[MS_CONTINUE][2] = 515;
        intButtons[MS_CONTINUE][3] = 540;
        intButtons[MS_CONTINUE][4] = 355;
        intButtons[MS_CONTINUE][5] = 515;
        strButtonTexts[MS_CONTINUE] = "Continue";
    }

}
