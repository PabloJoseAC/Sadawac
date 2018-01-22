package sawadac;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import arc.Console;
import arc.TextInputFile;
import arc.TextOutputFile;

public class Main {

    ////////VARIABLES////////
    //SCREENS
    static int MAIN_MENU = 0;
    static int USER_SELECTION = 1;
    static int CHARACTER_SELECTION = 2;
    static int MAP_SELECTION = 3;
    static int GAME = 4;
    static int FIGHT = 5;
    static int GAMEOVER = 6;
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
    
    static int G_MAINMENU = 14;
    static int G_EXPLORE = 15;
    
    static int F_ABILITY1 = 16;
    static int F_ABILITY2 = 17;
    static int F_ABILITY3 = 18;
    
    static int H_MAINMENU = 19;
    
    static int HS_MAINMENU = 20;
    
    static int BUTTON_COUNT = HS_MAINMENU + 1;

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
    static String strUserName = "";
    static String[][] strAbilities;
    static String[][] strMap;
    static int[][] intEnemies;
    static int[][] intBoosts;
    static int intFightEnemy = -1;

    //IMAGES
    static BufferedImage imgBackground;
    static BufferedImage[][] imgCharacterImgs;
    static BufferedImage[] imgTiles;
    static BufferedImage[][] imgEnemies;
    static BufferedImage[] imgUI;
    static BufferedImage[] imgBoosts;
    
    static int IDLE = 0;
    static int MOVE = 1;
    static int ATTACK = 3;
    
    static int GRASS = 0;
    static int TREE = 1;
    static int WATER = 2;
    
    static int W = 0;
    static int A = 1;
    static int S = 2;
    static int D = 3;
    static int REDW = 4;
    static int REDA = 5;
    static int REDS = 6;
    static int REDD = 7;
    static int R = 8;
    static int SKIP = 9;
    static int HEART = 10;
    static int BOLT = 11;
    static int HEALTHPOTION = 12;
    static int ENERGYPOTION = 13;
    
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
    static int ACTION = 4;
    static int ACTIONVALUE = 5;
    static int TURNS = 6;

    //MAP SELECTION
    static String[] strMaps;
    static int intMap;
    static String strMapName = "";
    static int intEnemyCount;
    static int intBossCount;
    static int intBoostCount;

    //GAME
    static int intHealthPotions = 2, intEnergyPotions = 1;
    static int intMaxHealth = 50, intMaxEnergy = 50;
    static int intHealth = 50, intEnergy = 50;
    static int intPlayerX, intPlayerY;
    static int intTurns;
    static int[] intActiveAbilities;
    static int intDamageMultiplier;
    static int intDamageBoost;
    static int intDamageBlock;
    static int intSelfDamage;
    static int intMoves = 1, intMovesLeft;
    static boolean boolPlayerTurn = true;
    static boolean boolUpdatePlayer = true;
    static boolean boolMoved;
    
    //FIGHT
    static boolean boolPlayerFights = true;
    static int intSelectedButton = -1;
    
    //HELP
    static BufferedImage imgHelp;
    
    //HIGHSCORES
    static String[][] strHighScores;
    
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
            }else if(intScreen == GAME){
            	game();
            }else if(intScreen == FIGHT){
            	fight();
            }else if(intScreen == GAMEOVER){
            	gameover();
            }else if(intScreen == HELP){
            	help();
            }else if(intScreen == HIGHSCORES){
            	highScores();
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
                    strUserName = strButtonTexts[US_NAMEINPUT];
                    boolTyping = false;
                //CHECK IF THE KEY PRESSED IS THE ESC KEY
                }else if(chrKey == 27){
                    strButtonTexts[US_NAMEINPUT] = "";
                    strUserName = strButtonTexts[US_NAMEINPUT];
                    intButtons[US_NAMEINPUT][6] = 0;
                    boolTyping = false;
                }
                //SET LAST KEY TO THIS KEY
                chrLastKey = chrKey;
            }
        }else{
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
                if(!strUserName.isEmpty()){
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
        }
        
        //SET CONTINUE BUTTON TO LIGHT GRAY IF A NAME HASN'T BEEN SET
        if(strUserName.isEmpty()){
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
                    con.setDrawColor(Color.ORANGE);
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
    
    public static void game(){
    	//RESET BUTTON COLORS
    	resetButtonColors(G_MAINMENU, G_EXPLORE);
    	
    	//CHECK IF BUTTON IS BEING HOVERED OVER
    	if(buttonHovered(G_MAINMENU)){
			//CHECK IF BUTTON HAS BEEN PRESSED
			if(intMouseButton == 1){
				//RESET SOME VARIABLES
				reset();
				//RETURN TO MAIN MENU
				intScreen = MAIN_MENU;
				boolWaitForRelease = true;
				clrButtonColors[G_MAINMENU] = clrLightGray;
			}else{
				clrButtonColors[G_MAINMENU] = clrDarkGray;
			}
		}
    	
    	//CHECK IF ABILITIES AND OTHER PLAYER VARIABLES NEED TO UPDATE
		//THIS ONLY HAPPENS ONCE PER TURN
		if(boolUpdatePlayer){
			updatePlayer();
			intMovesLeft = intMoves;
		}
    	
    	//CHECK IF IT IS THE PLAYER'S TURN
    	if(boolPlayerTurn){
    		//CHECK IF THE PLAYER IS DEAD
    		if(intHealth <= 0){
    			//GO TO THE Victory/Lose Screen (6)
    			intScreen = GAMEOVER;
    		}
    		
    		//WAIT FOR USER TO RELEASE KEY
    		while(chrLastKey != 0 && chrLastKey == chrKey){
    			chrKey = con.currentChar();
    			con.sleep(10);
    		}
    		chrLastKey = chrKey;
    		
    		if(chrKey == 119 || chrKey == 87){
    			//W KEY WAS PRESSED, MOVE UP
    			boolMoved = movePlayer(0, -1);
    		}else if(chrKey == 97 || chrKey == 65){
    			//A KEY WAS PRESSED, MOVE LEFT
    			boolMoved = movePlayer(-1, 0);
    		}else if(chrKey == 115 || chrKey == 83){
    			//S KEY WAS PRESSED, MOVE DOWN
    			boolMoved = movePlayer(0, 1);
    		}else if(chrKey == 100 || chrKey == 68){
    			//D KEY WAS PRESSED, MOVE RIGHT
    			boolMoved = movePlayer(1, 0);
    		}else if(chrKey == 114 || chrKey == 82){
    			//R KEY WAS PRESSED, PERFORM EXPLORING ABILITY
    			performAbility(0);
    		}else if(chrKey == 113 || chrKey == 81){
    			//Q KEY WAS PRESSED, APPLY A HEALTH POTION
    			if(intHealthPotions > 0 && intHealth != intMaxHealth){
    				intHealth += 30;
        			if(intHealth > intMaxHealth){
        				intHealth = intMaxHealth;
        			}
        			intHealthPotions--;
    			}
    		}else if(chrKey == 101 || chrKey == 69){
    			//E KEY WAS PRESSED, APPLY A ENERGY POTION
    			if(intEnergyPotions > 0 && intEnergy != intMaxEnergy){
    				intEnergy += 30;
    				if(intEnergy > intMaxEnergy){
    					intEnergy = intMaxEnergy;
    				}
    				intEnergyPotions--;
    			}
    		}else if(chrKey == 32){
    			//THE SPACEBAR WAS PRESSED, GO TO THE AI'S TURN
    			boolPlayerTurn = false;
    			boolUpdatePlayer = true;
    			intTurns++;
    		}
    		
    		//CHECK IF PLAYER MOVED
    		if(boolMoved){        		
    			//CHECK IF THE USER HAS USED ALL OF HIS/HER MOVES THIS TURN
    			if(intMovesLeft > 0){
    				//DECREASE MOVES BY 1 AND LET PLAYER MOVE AGAIN
    				intMovesLeft--;
    				boolMoved = false;
    			}else{
    				//GO TO THE AI'S TURN
    				boolMoved = false;
    				boolPlayerTurn = false;
    				boolUpdatePlayer = true;
    				intTurns++;
    			}
    		}
    	}else{
    		for(int intCount = 0; intCount < intEnemies.length; intCount++){
    			if(intEnemies[intCount] != null){
    				int intDistanceX = Math.abs(intEnemies[intCount][0] - intPlayerX);
            		int intDistanceY = Math.abs(intEnemies[intCount][1] - intPlayerY);
        			//CHECK IF ENEMY IS WITHIN 3 TILES OF PLAYER
            		if(intDistanceX <= 3 || intDistanceY <= 3){
        				//IF THE ENEMY IS RIGHT BESIDE THE PLAYER, TRIGGER A FIGHT
            			if((intDistanceX == 1 && intDistanceY == 0) || (intDistanceX == 0 && intDistanceY == 1)){
        					intFightEnemy = intCount;
        					intScreen = FIGHT;
        				}else{
        					//MOVE CLOSER TO THE PLAYER
                			if(intDistanceX >= intDistanceY){
                				if(intEnemies[intCount][0] >= intPlayerX){
                					if(canEnemyMove(intEnemies[intCount][0] - 1, intEnemies[intCount][1])){
                						intEnemies[intCount][0]--;
                					}
                				}else{
                					if(canEnemyMove(intEnemies[intCount][0] + 1, intEnemies[intCount][1])){
                						intEnemies[intCount][0]++;
                					}
                				}
            				}else{
            					if(intEnemies[intCount][1] >= intPlayerY){
            						if(canEnemyMove(intEnemies[intCount][0], intEnemies[intCount][1] - 1)){
                						intEnemies[intCount][1]--;
                					}
                				}else{
                					if(canEnemyMove(intEnemies[intCount][0], intEnemies[intCount][1] + 1)){
                						intEnemies[intCount][1]++;
                					}
                				}
            				}
        				}
        			}
    			}
    		}
    		//GO TO THE PLAYER'S TURN
    		con.sleep(500);
    		boolPlayerTurn = true;
    	}
    	
    	//DRAW BACKGROUND
    	con.setDrawColor(clrGreen);
    	con.fillRect(0, 0, 800, 600);
    	
    	//DRAW TILES
    	con.setDrawColor(Color.BLACK);
    	int intCountX = 0, intCountY = 0;
    	for(int intTileY = intPlayerY - 2; intTileY <= intPlayerY + 2; intTileY++){
    		for(int intTileX = intPlayerX - 2; intTileX <= intPlayerX + 2; intTileX++){
    			//CHECK IF COORDS ARE WITHIN MAP BOUNDS
    			if((intTileX >= 0 && intTileX < 20) && (intTileY >= 0 && intTileY < 20)){
    				int intTile = GRASS;
    				//CHECK WHAT TILE IS AT THESE COORDS
    				String strTile = strMap[intTileY][intTileX];
    				//DRAW THE TILES
    				if(strTile.equalsIgnoreCase("G")){
    					con.drawImage(imgTiles[GRASS], 100 + (120 * intCountX), 120 * intCountY);
    					con.drawRect(100 + (120 * intCountX), 120 * intCountY, 120, 120);
    				}else if(strTile.equalsIgnoreCase("T")){
    					con.drawImage(imgTiles[GRASS], 100 + (120 * intCountX), 120 * intCountY);
    					con.drawImage(imgTiles[TREE], 100 + (120 * intCountX), 120 * intCountY);
    				}else if(strTile.equalsIgnoreCase("W")){
    					con.drawImage(imgTiles[WATER], 100 + (120 * intCountX), 120 * intCountY);
    				}
        		}
    			intCountX++;
        	}
    		intCountY++;
    		intCountX = 0;
    	}
    	
    	//DRAW PLAYER CHARACTER
    	con.drawImage(imgCharacterImgs[IDLE][0], 100 + (120 * 2), 120 * 2);
    	
    	//DRAW UI
    	//DRAW BUTTON
    	drawButtons(G_MAINMENU, G_EXPLORE);
    	
    	//DRAW LETTERS REPRESENTING WHERE YOU CAN MOVE
    	if(canMove(0, -1)){
    		if(isEnemyInTile(intPlayerX, intPlayerY - 1)){
    			con.drawImage(imgUI[REDW], 100 + (120 * 2), 120);
    		}else{
    			con.drawImage(imgUI[W], 100 + (120 * 2), 120);
    		}
    	}
    	
    	if(canMove(-1, 0)){
    		if(isEnemyInTile(intPlayerX - 1, intPlayerY)){
    			con.drawImage(imgUI[REDA], 100 + 120, 120 * 2);
    		}else{
    			con.drawImage(imgUI[A], 100 + 120, 120 * 2);
    		}
    	}
    	
    	if(canMove(0, 1)){
    		if(isEnemyInTile(intPlayerX, intPlayerY + 1)){
    			con.drawImage(imgUI[REDS], 100 + (120 * 2), 120 * 3);
    		}else{
    			con.drawImage(imgUI[S], 100 + (120 * 2), 120 * 3);
    		}
    	}
    	
    	if(canMove(1, 0)){
    		if(isEnemyInTile(intPlayerX + 1, intPlayerY)){
    			con.drawImage(imgUI[REDD], 100 + (120 * 3), 120 * 2);
    		}else{
    			con.drawImage(imgUI[D], 100 + (120 * 3), 120 * 2);
    		}
    	}
    	
    	//DRAW R
    	con.drawImage(imgUI[R], 725, 140);
    	
    	//DRAW HEART AND BOLT
    	con.drawImage(imgUI[HEART], 15, 15);
    	con.drawImage(imgUI[BOLT], 55, 15);
    	
    	//DRAW HEALTH AND ENERGY BARS
    	con.setDrawColor(clrGray);
    	con.fillRect(15, 60, 30, 285);
    	con.fillRect(55, 60, 30, 285);
    	
    	con.setDrawColor(Color.RED);
    	int intHealthSpace = (int)(285 - ((((double) intHealth) / intMaxHealth) * 285));
    	con.fillRect(15, 60 + intHealthSpace, 30, 285 - intHealthSpace);
    	con.setDrawColor(Color.YELLOW);
    	int intEnergySpace = (int)(285 - ((((double) intEnergy) / intMaxEnergy) * 285));
    	con.fillRect(55, 60 + intEnergySpace, 30, 285 - intEnergySpace);
    	
    	con.setDrawFont(fntNormal);
    	con.setDrawColor(Color.BLACK);
    	con.drawString(intHealth + "", 10, 80 + intHealthSpace);
    	con.drawString(intEnergy + "", 50, 80 + intEnergySpace);
    	
    	if(intHealthSpace > 20){
    		con.drawString(intMaxHealth + "", 10, 80);
    	}
    	
    	if(intEnergySpace > 20){
    		con.drawString(intMaxEnergy + "", 50, 80);
    	}
    	
    	//DRAW POTIONS
    	con.setDrawColor(Color.BLACK);
    	con.setDrawFont(fntMedium);
    	con.drawImage(imgUI[HEALTHPOTION], 20, 370);
    	con.drawString(intHealthPotions + "", 30, 393);
    	con.drawImage(imgUI[ENERGYPOTION], 20, 470);
    	con.drawString(intEnergyPotions + "", 30, 493);
    	
    	//DRAW TURNS
    	con.setDrawFont(fntMedium);
    	con.setDrawColor(Color.BLACK);
    	con.drawString("Turns", 710, 550);
    	con.drawString(intTurns + "", 730, 580);
    
    	//DRAW BOOSTS/COLLECTIBLES
    	for(int intCount = 0; intCount < intBoosts.length; intCount++){
    		if(intBoosts[intCount] != null){
    			int intBoostX = intBoosts[intCount][0] - intPlayerX;
        		int intBoostY = intBoosts[intCount][1] - intPlayerY;
        		//CHECK IF THE BOOST IS IN THE PLAYER'S VIEWPORT
        		if(Math.abs(intBoostX) <= 2 && Math.abs(intBoostY) <= 2){
        			con.drawImage(imgBoosts[intBoosts[intCount][2]],
        					100 + (120 * (2 + intBoostX)), 120 * (2 + intBoostY));
        		}
    		}
    	}
    	
    	//DRAW ENEMIES
    	for(int intCount = 0; intCount < intEnemies.length; intCount++){
    		if(intEnemies[intCount] != null){
    			int intEnemyX = intEnemies[intCount][0] - intPlayerX;
        		int intEnemyY = intEnemies[intCount][1] - intPlayerY;
        		//CHECK IF THE ENEMY IS IN THE PLAYER'S VIEWPORT
        		if(Math.abs(intEnemyX) <= 2 && Math.abs(intEnemyY) <= 2){
        			con.drawImage(imgEnemies[intEnemies[intCount][4]][0],
        					100 + (120 * (2 + intEnemyX)), 120 * (2 + intEnemyY));
        		}
    		}
    	}

    	//DRAW SKIP
    	con.drawImage(imgUI[SKIP], 250, 555);
    }
    
    public static void fight(){
    	//RESET BUTTON COLORS
    	resetButtonColors(F_ABILITY1, F_ABILITY3);
    	
    	//CHECK IF ABILITIES AND OTHER PLAYER VARIABLES NEED TO UPDATE
    	//THIS ONLY HAPPENS ONCE PER TURN
    	if(boolUpdatePlayer){
    		updatePlayer();
    		intMovesLeft = intMoves;
    	}
    	
    	//CHECK IF IT IS THE PLAYER'S TURN TO FIGHT
    	if(boolPlayerFights){
    		//CHECK IF PLAYER'S DEAD
    		if(intHealth <= 0){
    			//GO TO THE Victory/Lose Screen (6)
    			intScreen = GAMEOVER;
    		}
    		
    		//WAIT FOR USER TO RELEASE KEY
    		while(chrLastKey != 0 && chrLastKey == chrKey){
    			chrKey = con.currentChar();
    			con.sleep(10);
    		}
    		chrLastKey = chrKey;
    		
    		if(chrKey == 32){
    			//THE SPACEBAR WAS PRESSED, GO TO THE AI'S TURN
    			boolUpdatePlayer = true;
    			boolPlayerFights = false;
    		}
    		
    		//LOOP THROUGH BUTTONS
        	for(int intCount = 0; intCount < 3; intCount++){
        		//CHECK IF ABILITY CAN BE PERFORMED
                if(Integer.parseInt(strAbilities[intCount + 1][ENERGY]) <= intEnergy){
                	//CHECK IF BUTTON IS BEING HOVERED OVER
                	if(buttonHovered(F_ABILITY1 + intCount)){
                        //CHECK IF BUTTON HAS BEEN PRESSED
                        if(intMouseButton == 1){
                            //PERFORM THE ABILITY
                        	boolean boolSuccess = performAbility(intCount + 1);
                        	//AI'S TURN ONCE USER HAS PERFORMED AN ABILITY
                        	if(boolSuccess){
                        		boolPlayerFights = false;
                        	}
                        	clrButtonColors[F_ABILITY1 + intCount] = clrLightGray;
                            boolWaitForRelease = true;
                        }else{
                            clrButtonColors[F_ABILITY1 + intCount] = clrDarkGray;
                        }
                        intSelectedButton = intCount + 1;
                    }
                }else{
                	clrButtonColors[F_ABILITY1 + intCount] = clrLightGray;
                }
        	}
    	}else{
    		//AI'S TURN TO FIGHT//
    		//CHECK IF ENEMY IS DEAD
    		if(intEnemies[intFightEnemy][2] <= 0){
    			//GIVE PLAYER A EITHER A HEALTH POTION, ENERGY POTION, OR MORE MAX HEALTH OR MAX ENERGY
    			//AND GIVE MORE OF IT DEPENDING ON THE ENEMY TYPE
    			int intRand = (int) Math.round(Math.random() * 4);
    			if(intRand == 0 || intRand == 1){
    				intHealthPotions += 1 * (intEnemies[intFightEnemy][4] + 1);
    			}else if(intRand == 2){
    				intEnergyPotions += 1 * (intEnemies[intFightEnemy][4] + 1);
    			}else if(intRand == 3){
    				intMaxHealth += 10 * (intEnemies[intFightEnemy][4] + 1);
    			}else if(intRand == 4){
    				intMaxEnergy += 10 * (intEnemies[intFightEnemy][4] + 1);
    			}
    			
    			//REMOVE ENEMY FROM GAME
    			intEnemies[intFightEnemy] = null;
    			intFightEnemy = -1;
    			
    			//COUNT HOW MANY BOSSES ARE LEFT
    			int intBosses = 0;
    			for(int intCount = 0; intCount < intEnemies.length; intCount++){
    				if(intEnemies[intCount] != null && intEnemies[intCount][4] == 2){
    					intBosses++;
    				}
    			}
    			
    			//IF THERE ARE STILL BOSSES IN THE GAME, RETURN TO THE Game Screen (4)
    			//IF NOT, GO TO THE Victory/Lose Screen (6)
    			if(intBosses > 0){
    				boolPlayerTurn = true;
    				boolPlayerFights = true;
    				intScreen = GAME;
    			}else{
    				intScreen = GAMEOVER;
    			}
    		}else{
    			//CHOOSE A RANDOM ATTACK TO DEAL
    			int intAttack = (int) Math.round(Math.random() * 2);
    			//CHECK WHAT TYPE OF ENEMY IT IS
    			if(intEnemies[intFightEnemy][4] == 0 || intEnemies[intFightEnemy][4] == 1){
    				//IT'S A NORMAL ENEMY OR A POWERFUL ENEMY
    				//IF IT'S A POWERFUL ENEMY, IT HAS A CHANCE OF HEALING ITSELF
    				if(intEnemies[intFightEnemy][4] == 1){
        				int intHeal = (int) Math.round(Math.random() * 2);
        				//1 IN 3 CHANCES TO HEAL ITSELF
        				if(intHeal == 0){
        					intEnemies[intFightEnemy][2] += 10;
        					if(intEnemies[intFightEnemy][2] > intEnemies[intFightEnemy][3]){
        						intEnemies[intFightEnemy][2] = intEnemies[intFightEnemy][3];
        					}
        				}
    				}
    				
    				//1 IN 3 CHANCES TO DO A HEAVY ATTACK
    				if(intAttack == 0){
    					//TAKE DAMAGE THAT IT NEEDS TO INFLICT TO ITSELF
    					intEnemies[intFightEnemy][2] -= (int)(10 * ((double)intSelfDamage / 100));
    					//DAMAGE PLAYER BASED ON BASE DAMAGE, THE DAMAGE BEING BLOCKED BY THE PLAYER,
    					//AND THE DAMAGE THAT IS LEFT AFTER INFLICTING DAMAGE TO ITSELF
    					intHealth -= (int)(10 * (1 - ((double)intDamageBlock / 100)) * (1 - ((double)intSelfDamage / 100)));
    				}else{
    					intEnemies[intFightEnemy][2] -= (int)(5 * ((double)intSelfDamage / 100));
    					intHealth -= (int)(5 * (1 - ((double)intDamageBlock / 100)) * (1 - ((double)intSelfDamage / 100)));
    				}
    			}else if(intEnemies[intFightEnemy][4] == 2){
    				//IT'S A BOSS
    				int intUltimate = (int) Math.round(Math.random() * 4);
    				//1 IN 5 CHANCES TO DEAL A LOT OF DAMAGE
    				if(intUltimate == 0){
    					intEnemies[intFightEnemy][2] -= (int)(20 * ((double)intSelfDamage / 100));
    					intHealth -= (int)(20 * (1 - ((double)intDamageBlock / 100)) * (1 - ((double)intSelfDamage / 100)));
    				}else{
    					intEnemies[intFightEnemy][2] -= (int)(10 * ((double)intSelfDamage / 100));
    					intHealth -= (int)(10 * (1 - ((double)intDamageBlock / 100)) * (1 - ((double)intSelfDamage / 100)));
    				}
    			}
    			con.sleep(500);
    		}
    		
    		//PLAYER'S TURN TO FIGHT
    		boolPlayerFights = true;
    		boolUpdatePlayer = true;
    	}
    	
        //DRAW BACKGROUND
        con.drawImage(imgBackground, 0, 0);
        
        //DRAW UI//
        //DRAW BACKGROUND RECTANGLES
        con.setDrawColor(Color.GRAY);
        con.fillRect(35, 255, 770 - 35, 570 - 255);
        con.setDrawColor(Color.WHITE);
        con.fillRect(425, 335, 740 - 425, 545 - 335);
        
        //DRAW ABILITY INFORMATION
        if(intSelectedButton != -1){
        	con.setDrawFont(fntNormal);
        	con.setDrawColor(getAbilityColor(intSelectedButton));
        	con.drawString(strAbilities[intSelectedButton][NAME], 440, 375);
        	con.setDrawColor(Color.BLACK);
        	con.drawString(strAbilities[intSelectedButton][DESC], 440, 410);
        	con.setDrawColor(Color.CYAN);
        	con.drawString(strAbilities[intSelectedButton][ENERGY] + " Energy", 440, 445);
        	con.setDrawColor(Color.ORANGE);
        	con.drawString(getAbilityActionDesc(intSelectedButton), 440, 480);
        	intSelectedButton = -1;
        }
        
        //DRAW HEART AND BOLT
        con.drawImage(imgUI[HEART], 65, 275);
        con.drawImage(imgUI[BOLT], 105, 275);
        
        //DRAW HEALTH AND ENERGY BARS
    	con.setDrawColor(clrGray);
    	con.fillRect(65, 320, 95 - 65, 545 - 320);
    	con.fillRect(105, 320, 135 - 105, 545 - 320);
    	
    	con.setDrawColor(Color.RED);
    	int intHealthSpace = (int)((545 - 320) - ((((double) intHealth) / intMaxHealth) * (545 - 320)));
    	con.fillRect(65, 320 + intHealthSpace, 95 - 65, 545 - intHealthSpace - 320);
    	con.setDrawColor(Color.YELLOW);
    	int intEnergySpace = (int)((545 - 320) - ((((double) intEnergy) / intMaxEnergy) * (545 - 320)));
    	con.fillRect(105, 320 + intEnergySpace, 135 - 105, 545 - intEnergySpace - 320);
    	
    	con.setDrawFont(fntNormal);
    	con.setDrawColor(Color.BLACK);
    	con.drawString(intHealth + "", 75, 340 + intHealthSpace);
    	con.drawString(intEnergy + "", 115, 340 + intEnergySpace);
    	
    	if(intHealthSpace > 20){
    		con.drawString(intMaxHealth + "", 65, 340);
    	}
    	
    	if(intEnergySpace > 20){
    		con.drawString(intMaxEnergy + "", 105, 340);
    	}
    	
    	//ENEMY HEALTH BAR
    	con.setDrawColor(clrGray);
    	con.fillRect(165, 280, 740 - 165, 310 - 280);
    	if(intFightEnemy != -1 && intEnemies[intFightEnemy] != null){
    		con.setDrawColor(Color.RED);
    		int intHealthLocation = (int)((((double)intEnemies[intFightEnemy][2])
        			/ intEnemies[intFightEnemy][3]) * (740 - 165));
        	con.fillRect(165, 280, intHealthLocation, 310 - 280);
        	
        	con.setDrawFont(fntNormal);
        	con.setDrawColor(Color.BLACK);
        	con.drawString(intEnemies[intFightEnemy][2] + "", intHealthLocation + 165 - 30, 280 + 20);
        	
        	if(intHealthLocation < 740 - 165 - 20){
        		con.drawString(intEnemies[intFightEnemy][3] + "", 740 - 30, 280 + 20);
        	}
    	}
    	
    	//DRAW ENEMY AND CHARACTER
    	if(intFightEnemy != -1 && intEnemies[intFightEnemy] != null){
    		con.drawImage(imgEnemies[intEnemies[intFightEnemy][4]][0], 230, 80);
    	}
    	con.drawImage(imgCharacterImgs[IDLE][0], 480, 80);
    	
    	//DRAW SKIP
    	con.drawImage(imgUI[SKIP], 250, 20);
        
        //DRAW BUTTONS
    	drawButtons(F_ABILITY1, F_ABILITY3);
    }
    
    public static void gameover(){
    	//DRAW BACKGROUND
    	con.drawImage(imgBackground, 0, 0);
    	
    	con.setDrawFont(fntLarge);
    	if(intHealth <= 0){
    		//PLAYER LOST
    		con.setDrawColor(clrFight);
    		con.drawString("DEFEAT", 220, 300);
    	}else{
    		//PLAYER WON
    		con.setDrawColor(clrGreen);
    		con.drawString("VICTORY", 220, 300);
    		
    		//WRITE TURNS TO THE END OF 'highscores.txt' FILE
    		TextOutputFile outputFile = new TextOutputFile("highscores.txt", true);
    		outputFile.println(strUserName);
    		outputFile.println(intTurns);
    		//CLOSE FILE
    		outputFile.close();
    	}
    	
    	//DRAW TURNS TAKEN
    	con.setDrawFont(fntMedium2);
		con.setDrawColor(Color.WHITE);
		con.drawString("Turns: " + intTurns, 320, 400);
		
		//REPAINT
		con.repaint();
		
		//WAIT 5 SECONDS THEN RETURN TO THE Main Menu Screen (0)
		con.sleep(5000);
		reset();
		intScreen = MAIN_MENU;
    }
    
    public static void help(){
    	//RESET BUTTON COLORS
    	resetButtonColors(H_MAINMENU, H_MAINMENU);
    	
    	//CHECK IF BUTTON IS BEING HOVERED OVER
    	if(buttonHovered(H_MAINMENU)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
            	//GO BACK TO THE Main Menu Screen (0)
                intScreen = MAIN_MENU;
            	clrButtonColors[H_MAINMENU] = clrLightGray;
                boolWaitForRelease = true;
            }else{
                clrButtonColors[H_MAINMENU] = clrDarkGray;
            }
        }
    	
    	//DRAW BACKGROUND
    	con.drawImage(imgBackground, 0, 0);
    	//DRAW HELP IMAGE
    	con.drawImage(imgHelp, 35, 20);
    	//DRAW MAIN MENU BUTTON
    	drawButtons(H_MAINMENU, H_MAINMENU);
    }
    
    public static void highScores(){
    	//SET BUTTON COLORS TO DEFAULT
        resetButtonColors(HS_MAINMENU, HS_MAINMENU);
        
        if(strHighScores == null){
        	File file = new File("highscores.txt");
        	//CHECK IF THE FILE EXISTS
        	if(file.exists()){
        		//READ AMOUNT OF SCORES IN THE FILE
            	int intUserCount = 0;
            	TextInputFile inputFile = new TextInputFile("highscores.txt");
            	while(!inputFile.eof()){
            		inputFile.readLine();
            		inputFile.readLine();
            		intUserCount++;
            	}
            	inputFile.close();
            	
            	//INITIALIZE ARRAY
            	strHighScores = new String[intUserCount][2];
            	
            	//RE-READ FILE AND LOAD NAME AND SCORE INTO ARRAY
            	intUserCount = 0;
            	inputFile = new TextInputFile("highscores.txt");
            	while(!inputFile.eof()){
            		strHighScores[intUserCount][0] = inputFile.readLine();
            		strHighScores[intUserCount][1] = inputFile.readLine();
            		intUserCount++;
            	}
            	inputFile.close();
            	
            	//BUBBLE SORT THE ARRAY
            	for(int intCount = 0; intCount < strHighScores.length; intCount++){
            		for(int intCount2 = 0; intCount2 < strHighScores.length - 1 - intCount; intCount2++){
            			int intScore = Integer.parseInt(strHighScores[intCount2][1]);
                		int intScore2 = Integer.parseInt(strHighScores[intCount2 + 1][1]);
                		if(intScore > intScore2){
                			String strName = strHighScores[intCount2 + 1][0];
                			String strScore = strHighScores[intCount2 + 1][1];
                			strHighScores[intCount2 + 1][0] = strHighScores[intCount2][0];
                			strHighScores[intCount2 + 1][1] = strHighScores[intCount2][1];
                			strHighScores[intCount2][0] = strName;
                			strHighScores[intCount2][1] = strScore;
                		}
            		}
            	}
        	}else{
        		strHighScores = new String[0][0];
        	}
        }
        
        //CHECK IF BUTTON IS BEING HOVERED OVER
    	if(buttonHovered(HS_MAINMENU)){
            //CHECK IF BUTTON HAS BEEN PRESSED
            if(intMouseButton == 1){
            	//GO BACK TO THE Main Menu Screen (0)
                intScreen = MAIN_MENU;
            	clrButtonColors[HS_MAINMENU] = clrLightGray;
                boolWaitForRelease = true;
            }else{
                clrButtonColors[HS_MAINMENU] = clrDarkGray;
            }
        }
        
        //DRAW BACKGROUND
    	con.drawImage(imgBackground, 0, 0);
    	
    	//DRAW TITLE
    	con.setDrawFont(fntMedium2);
    	con.setDrawColor(Color.WHITE);
    	con.drawString("HighScores", 285, 85);
    	
    	//DRAW TOP 10 PLAYERS
    	con.setDrawFont(fntMedium);
    	for(int intCount = 0; intCount < 10; intCount++){
    		if(intCount < strHighScores.length){
    			con.drawString((intCount + 1) + ". " + strHighScores[intCount][0] + " - " +
        				strHighScores[intCount][1], 300, 140 + (35 * intCount));
    		}else{
    			con.drawString((intCount + 1) + ". ", 300, 140 + (35 * intCount));
    		}
    	}
    	
    	//DRAW MAIN MENU BUTTON
    	drawButtons(HS_MAINMENU, HS_MAINMENU);
    	
    	//IF MAIN MENU BUTTON WAS PRESSED, ERASE strHighScores ARRAY
    	if(intScreen == MAIN_MENU){
    		strHighScores = null;
    	}
    }
    
    public static void updatePlayer(){
    	for(int intCount = 0; intCount < intActiveAbilities.length; intCount++){
    		if(intActiveAbilities[intCount] > 0){
    			//REDUCE A TURN THAT THE ABILITY IS ACTIVE FOR
    			intActiveAbilities[intCount]--;
    			if(intActiveAbilities[intCount] == 0){
    				updatePlayer();
                                return;
    			}
    		}else{
    			//ABILITY IS NO LONGER ACTIVE, REMOVE THE ACTION GIVEN BY IT
    			String strAction = strAbilities[intCount][ACTION];
    			if(strAction.equalsIgnoreCase("Move")){
        			//RESET AMOUNT OF MOVES PER TURN
        			intMoves = 0;
        		}else if(strAction.equalsIgnoreCase("DamageBoost")){
        			//RESET AMOUNT OF DAMAGE MULTIPLIER
        			intDamageMultiplier = 0;
        		}else if(strAction.equalsIgnoreCase("DamageBlock")){
        			//RESET AMOUNT OF DAMAGE BLOCK
        			intDamageBlock = 0;
        		}else if(strAction.equalsIgnoreCase("SelfDamage")){
        			//RESET AMOUNT OF DAMAGE THE ENEMY WILL INFLICT ON ITSELF
        			intSelfDamage = 0;
        		}
    		}
    	}
		
    	//GIVE THE PLAYER A BIT OF HEALTH
    	intHealth += 1;
    	if(intHealth > intMaxHealth){
    		intHealth = intMaxHealth;
    	}
    	
		//GIVE THE PLAYER A BIT OF ENERGY
		intEnergy += 5;
		if(intEnergy > intMaxEnergy){
			intEnergy = intMaxEnergy;
		}
		
		boolUpdatePlayer = false;
    }
    
    public static boolean performAbility(int intAbilityId){
    	//DON'T ACTIVATE THE ABILITY IF IT'S ALREADY ACTIVE
    	if(intActiveAbilities[intAbilityId] > 0){
    		return false;
    	}
    	
    	//CHECK IF PLAYER HAS ENOUGH ENERGY TO PERFORM ABILITY
    	int intAbilityEnergy = Integer.parseInt(strAbilities[intAbilityId][ENERGY]);
    	if(intAbilityEnergy <= intEnergy){
    		String strAction = strAbilities[intAbilityId][ACTION];
    		int intActionValue = Integer.parseInt(strAbilities[intAbilityId][ACTIONVALUE]);
    		//CHECK WHAT TYPE IS THE ABILITY
    		if(strAction.equalsIgnoreCase("Move")){
    			//SET AMOUNT OF MOVES PER TURN TO THE AMOUNT THE ABILITY GIVES
    			intMoves = intActionValue - 1;
    			intMovesLeft = intMoves;
    		}else if(strAction.equalsIgnoreCase("Damage")){
    			if(intFightEnemy > -1){
    				//DEAL DAMAGE TO ENEMY CURRENTLY BEING FOUGHT
    				intEnemies[intFightEnemy][2] -= (int)((intActionValue * Math.max(intDamageMultiplier, 1)) + intDamageBoost);
    			}
    		}else if(strAction.equalsIgnoreCase("DamageBoost")){
    			//SET AMOUNT OF DAMAGE MULTIPLIER THE ABILITY GIVES
    			intDamageMultiplier = intActionValue;
    		}else if(strAction.equalsIgnoreCase("DamageBlock")){
    			//SET AMOUNT OF DAMAGE BLOCK THE ABILITY GIVES
    			intDamageBlock = intActionValue;
    		}else if(strAction.equalsIgnoreCase("SelfDamage")){
    			//SET AMOUNT OF DAMAGE THE ENEMY WILL INFLICT ON ITSELF THAT THE ABILITY GIVES
    			intSelfDamage = intActionValue;
    		}
    		
    		//IF THE ABILITY LASTS MULTIPLE TURNS, ADD IT TO THE intActiveAbilities ARRAY
    		int intAbilityTurns = Integer.parseInt(strAbilities[intAbilityId][TURNS]);
    		if(intAbilityTurns > 0){
    			intActiveAbilities[intAbilityId] = intAbilityTurns;
                        if(strAbilities[intAbilityId][TYPE].equals("Fight") ||
                                    strAbilities[intAbilityId][TYPE].equals("Ultimate")){
                            intActiveAbilities[intAbilityId]++;
                        }
    		}
    		
    		//CONSUME THE ABILITY ENERGY FROM THE PLAYER'S ENERGY
    		intEnergy -= intAbilityEnergy;
    		
    		return true;
    	}
    	return false;
    }
    
    public static boolean movePlayer(int intX, int intY){
    	//GET TILE COORDS
    	int intTileX = intPlayerX + intX;
    	int intTileY = intPlayerY + intY;
    	//CHECK IF TILE IS WITHIN MAP BOUNDS AND ISN'T A TREE
    	if(canMove(intX, intY)){
    		//GET TILE TYPE
    		String strTile = strMap[intTileY][intTileX];
    		//CHECK IF TILE IS GRASS
    		if(strTile.equalsIgnoreCase("G")){
    			for(int intCount = 0; intCount < intEnemies.length; intCount++){
    				if(intEnemies[intCount] != null){
    					//CHECK IF ENEMY IS IN THIS TILE
    					if(intEnemies[intCount][0] == intTileX && intEnemies[intCount][1] == intTileY){
    						//SET intFightEnemy TO THIS ENEMY TO FIGHT IT
    						intFightEnemy = intCount;
    						//CHANGE TO THE FIGHT SCREEN
    						intScreen = FIGHT;
    					}
    				}
    			}
    			
    			for(int intCount = 0; intCount < intBoosts.length; intCount++){
    				if(intBoosts[intCount] != null){
    					//CHECK IF BOOST IS IN THIS TILE
        				if(intBoosts[intCount][0] == intTileX && intBoosts[intCount][1] == intTileY){
        					//GIVE PLAYER BOOST BASED ON THE TYPE OF BOOST IT IS
        					int intType = intBoosts[intCount][2];
        					if(intType == 0){
        						intMaxHealth += 10;
        					}else if(intType == 1){
        						intMaxEnergy += 10;
        					}else if(intType == 2){
        						intDamageBoost += 10;
        					}
        					//REMOVE BOOST FROM GAME
            				intBoosts[intCount] = null;
        				}
    				}
    			}
    			
				//MOVE PLAYER TO THIS TILE
				intPlayerX = intTileX;
				intPlayerY = intTileY;
				return true;
    		}else if(strTile.equalsIgnoreCase("W")){
    			//KILL PLAYER
    			intHealth = -50;
    			
    			//MOVE PLAYER TO THIS TILE
    			intPlayerX = intTileX;
				intPlayerY = intTileY;
				return true;
    		}
    	}
    	return false;
    }
    
    public static boolean canMove(int intX, int intY){
    	int intTileX = intPlayerX + intX;
    	int intTileY = intPlayerY + intY;
    	//CHECK IF TILE IS WITHIN MAP BOUNDS
    	if((intTileX >= 0 && intTileX < 20) && (intTileY >= 0 && intTileY < 20)){
    		//CHECK IF TILE ISN'T A TREE
    		if(!strMap[intTileY][intTileX].equalsIgnoreCase("T")){
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean canEnemyMove(int intX, int intY){
    	//CHECK IF TILE IS WITHIN MAP BOUNDS
    	if((intX >= 0 && intX < 20) && (intY >= 0 && intY < 20)){
    		//CHECK IF TILE IS GRASS
    		if(strMap[intY][intX].equalsIgnoreCase("G")){
    			//CHECK IF THERE'S ANOTHER ENEMY IN THAT TILE
    			return !isEnemyInTile(intX, intY);
    		}
    	}
    	return false;
    }
    
    public static boolean isEnemyInTile(int intX, int intY){
    	for(int intCount = 0; intCount < intEnemies.length; intCount++){
    		if(intEnemies[intCount] != null){
    			//CHECK IF THERE IS ANOTHER ENEMY IN THAT TILE
    			if(intX == intEnemies[intCount][0] && intY == intEnemies[intCount][1]){
    				return true;
    			}
    		}
		}
		return false;
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
                    String strCurrTile = strCurrLine.substring(intCount2, intCount2 + 1);
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
                    int intType = (int) Math.round(Math.random() * 2);
                    //1 IN 3 CHANCES FOR THE ENEMY TO BE A STRONGER ENEMY
                    if((intType + 1) % 3 == 0){
                        //ADD ENEMY TO intEnemies ARRAY
                        intEnemies[intEnemyCounter] = new int[]{
                            //X, Y, HEALTH, MAX HEALTH, TYPE
                            intCountX, intCountY, 120, 120, 1
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
                        intCountX, intCountY, 180, 180, 2
                    };
                    //SET THE CURRENT TILE TO A GRASS TILE
                    strMap[intCountY][intCountX] = "G";
                    intEnemyCounter++;
                }else if(strCurrTile.equalsIgnoreCase("C")){
                    //CHOOSE A TYPE OF BOOST AT RANDOM; MORE MAX HEALTH, MORE MAX ENERGY, MORE DAMAGE
                    int intType = (int) Math.round(Math.random() * 2);
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
        
        //INITIALIZE CHARACTER ARRAYS AND VARIABLES
        strAbilities = new String[4][7];
        intActiveAbilities = new int[4];        
        for(int intCount = 0; intCount < strAbilities.length; intCount++){
        	strAbilities[intCount][TURNS] = "0";
        }
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
                    strAbilities[intAbilityId][ACTION] = strLineParts[2];
                    strAbilities[intAbilityId][ACTIONVALUE] = strLineParts[3];
                }else if(strLineParts[1].equalsIgnoreCase("Turns")){
                    strAbilities[intAbilityId][TURNS] = strLineParts[2];
                }
            }
        }
        //CLOSE FILE
        inputFile.close();
        
        //UPDATE BUTTON VALUES
        intButtons[G_EXPLORE][4] = 745 - strAbilities[0][NAME].length() * 5;
        strButtonTexts[G_EXPLORE] = strAbilities[0][NAME];
        intButtons[F_ABILITY1][4] = 280 - strAbilities[1][NAME].length() * 5;
        strButtonTexts[F_ABILITY1] = strAbilities[1][NAME];
        intButtons[F_ABILITY2][4] = 280 - strAbilities[2][NAME].length() * 5;
        strButtonTexts[F_ABILITY2] = strAbilities[2][NAME];
        intButtons[F_ABILITY3][4] = 280 - strAbilities[3][NAME].length() * 5;
        strButtonTexts[F_ABILITY3] = strAbilities[3][NAME];
    }
    
    public static String getAbilityActionDesc(int intAbilityId){
    	String strReturn = "";
    	String strActionType = strAbilities[intAbilityId][ACTION]; 
    	//RETURN DESCRIPTION BASED ON THE ACTION THE ABILITY DOES
        if(strActionType.equalsIgnoreCase("Damage")){
    		strReturn += "Deals " + strAbilities[intAbilityId][ACTIONVALUE] + " damage";
    	}else if(strActionType.equalsIgnoreCase("DamageBoost")){
    		strReturn += strAbilities[intAbilityId][ACTIONVALUE] + "x the damage";
    	}else if(strActionType.equalsIgnoreCase("Move")){
    		strReturn += "Move " + strAbilities[intAbilityId][ACTIONVALUE] + " tiles";
    	}

        //IF ABILITY LASTS MULTIPLE TURNS, SAY THAT AT THE END
    	String strTurns = strAbilities[intAbilityId][TURNS];
    	if(strTurns != null && !strTurns.isEmpty() && !strTurns.equals("0")){
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
    
    public static void reset(){
    	strCharacterName = "";
		strMapName = "";
		intTurns = 0;
		intHealth = 50;
		intEnergy = 50;
		intMaxHealth = 50;
		intMaxEnergy = 50;
		intHealthPotions = 2;
		intEnergyPotions = 1;
		intDamageMultiplier = 0;
		intDamageBoost = 0;
		intDamageBlock = 0;
		intSelfDamage = 0;
		intMoves = 1;
	    boolPlayerTurn = true;
	    boolUpdatePlayer = true;
	    boolMoved = false;
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
        imgBackground = con.loadImage("src/res/Other/Background.png");
        imgHelp = con.loadImage("src/res/Other/Help.png");
        //TODO: MENU TORCHES
        /*imgTorch = new BufferedImage[]{
            con.loadImage("src/res/Torch/torch0.png"),
            con.loadImage("src/res/Torch/torch1.png"),
            con.loadImage("src/res/Torch/torch2.png"),
            con.loadImage("src/res/Torch/torch3.png")
        };*/
        imgTiles = new BufferedImage[]{
        	con.loadImage("src/res/Tiles/Grass.png"),
        	con.loadImage("src/res/Tiles/Tree.png"),
        	con.loadImage("src/res/Tiles/Water.png")
        };
        imgEnemies = new BufferedImage[][]{
        	new BufferedImage[]{
        		con.loadImage("src/res/Enemies/Normal/0.png")
        	},
        	new BufferedImage[]{
            	con.loadImage("src/res/Enemies/Powerful/0.png")
        	},
        	new BufferedImage[]{
            	con.loadImage("src/res/Enemies/Boss/0.png")
        	}
        };
        imgUI = new BufferedImage[]{
        	con.loadImage("src/res/Other/W.png"),
        	con.loadImage("src/res/Other/A.png"),
        	con.loadImage("src/res/Other/S.png"),
        	con.loadImage("src/res/Other/D.png"),
        	con.loadImage("src/res/Other/REDW.png"),
        	con.loadImage("src/res/Other/REDA.png"),
        	con.loadImage("src/res/Other/REDS.png"),
        	con.loadImage("src/res/Other/REDD.png"),
        	con.loadImage("src/res/Other/R.png"),
        	con.loadImage("src/res/Other/Skip.png"),
        	con.loadImage("src/res/Other/Heart.png"),
        	con.loadImage("src/res/Other/Bolt.png"),
        	con.loadImage("src/res/Other/HealthPotion.png"),
        	con.loadImage("src/res/Other/EnergyPotion.png")
        };
        imgBoosts = new BufferedImage[]{
        	con.loadImage("src/res/Boosts/Health.png"),
        	con.loadImage("src/res/Boosts/Energy.png"),
        	con.loadImage("src/res/Boosts/Damage.png")
        };
    }

    public static void loadPaths(){
    	//GET ALL FILES IN THE CHARACTERS FOLDER
        File[] files = new File("src/res/Characters/").listFiles();

        //COUNT HOW MANY CHARACTER FILES ARE IN THE FOLDER
        int intCharacterCount = 0;
        for(File file : files){
            if(file.isFile() && file.getName().contains(".txt")){
                intCharacterCount++;
            }
        }

        //INITIALIZE strCharacters ARRAY WITH THE NUMBER OF CHARACTERS
        strCharacters = new String[intCharacterCount];

        //ADD THE PATHS OF THE CHARACTER FILES TO THE strCharacters ARRAY
        int intCount = 0;
        for(File file : files){
            if(file.isFile() && file.getName().contains(".txt")){
                strCharacters[intCount] = file.getAbsolutePath();
                intCount++;
            }
        }

        //GET ALL FILES IN THE MAPS FOLDER
        files = new File("src/res/Maps/").listFiles();

        //COUNT HOW MANY MAP FILES ARE IN THE FOLDER
        int intMapCount = 0;
        for(File file : files){
            if(file.isFile() && file.getName().contains(".txt")){
                intMapCount++;
            }
        }

        //INITIALIZE strMaps ARRAY WITH THE NUMBER OF CHARACTERS
        strMaps = new String[intMapCount];

        //ADD THE PATHS OF THE MAP FILES TO THE strMaps ARRAY
        intCount = 0;
        for(File file : files){
            if(file.isFile() && file.getName().contains(".txt")){
                strMaps[intCount] = file.getAbsolutePath();
                intCount++;
            }
        }
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
        intButtons[CS_ABILITIES][0] = 170;
        intButtons[CS_ABILITIES][1] = 420;
        intButtons[CS_ABILITIES][2] = 650;
        intButtons[CS_ABILITIES][3] = 460;

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
        
        //GAME//
        //MAIN MENU
        intButtons[G_MAINMENU][0] = 715;
        intButtons[G_MAINMENU][1] = 20;
        intButtons[G_MAINMENU][2] = 780;
        intButtons[G_MAINMENU][3] = 80;
        intButtons[G_MAINMENU][4] = 722;
        intButtons[G_MAINMENU][5] = 57;
        strButtonTexts[G_MAINMENU] = "Menu";
        
        //EXPLORE ABILITY
        intButtons[G_EXPLORE][0] = 715;
        intButtons[G_EXPLORE][1] = 100;
        intButtons[G_EXPLORE][2] = 780;
        intButtons[G_EXPLORE][3] = 155;
        intButtons[G_EXPLORE][4] = 745;
        intButtons[G_EXPLORE][5] = 130;
        strButtonTexts[G_EXPLORE] = "";
        
        //FIGHT//
        //ABILITY 1
        intButtons[F_ABILITY1][0] = 165;
        intButtons[F_ABILITY1][1] = 335;
        intButtons[F_ABILITY1][2] = 400;
        intButtons[F_ABILITY1][3] = 395;
        intButtons[F_ABILITY1][4] = 280;
        intButtons[F_ABILITY1][5] = 375;
        strButtonTexts[F_ABILITY1] = "";
        
        //ABILITY 2
        intButtons[F_ABILITY2][0] = 165;
        intButtons[F_ABILITY2][1] = 410;
        intButtons[F_ABILITY2][2] = 400;
        intButtons[F_ABILITY2][3] = 470;
        intButtons[F_ABILITY2][4] = 280;
        intButtons[F_ABILITY2][5] = 450;
        strButtonTexts[F_ABILITY2] = "";
        
        //ABILITY 3
        intButtons[F_ABILITY3][0] = 165;
        intButtons[F_ABILITY3][1] = 485;
        intButtons[F_ABILITY3][2] = 400;
        intButtons[F_ABILITY3][3] = 545;
        intButtons[F_ABILITY3][4] = 280;
        intButtons[F_ABILITY3][5] = 525;
        strButtonTexts[F_ABILITY3] = "";
        
        //HELP//
        //MAIN MENU
        intButtons[H_MAINMENU][0] = 285;
        intButtons[H_MAINMENU][1] = 520;
        intButtons[H_MAINMENU][2] = 515;
        intButtons[H_MAINMENU][3] = 580;
        intButtons[H_MAINMENU][4] = 350;
        intButtons[H_MAINMENU][5] = 560;
        strButtonTexts[H_MAINMENU] = "Main Menu";
        
        //HIGHSCORES//
        //MAIN MENU
        intButtons[HS_MAINMENU][0] = 285;
        intButtons[HS_MAINMENU][1] = 490;
        intButtons[HS_MAINMENU][2] = 515;
        intButtons[HS_MAINMENU][3] = 550;
        intButtons[HS_MAINMENU][4] = 350;
        intButtons[HS_MAINMENU][5] = 530;
        strButtonTexts[HS_MAINMENU] = "Main Menu";
    }

}
