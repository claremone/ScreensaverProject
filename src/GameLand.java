//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Declare the objects used in the program below
    /**STEP 0: declare object**/
    public Hero coy;
    public Hero bird;
    public Hero lilypad;
    public Hero sun;
    /**STEP 1: declare image for object**/
    public Image coyPic;
    public Image birdPic;
    public Image monet;
    public Image lilypadPic;
    public Image sunPic;
    public boolean birdIsIntersectingCoy=false;
    public boolean birdIsIntersectingLilypad=false;
    public boolean coyIsIntersectingLilypad=false;
    public boolean lilypadIsIntersectingSun=false;


    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
       GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method
        //create (construct) the objects needed for the game below
        /**STEP 2: construct object**/
        coy=new Hero(200,300,3,-3,60,80);
        bird=new Hero(800,600,6,-3,100,100);
        lilypad=new Hero(500,500,3,-6,50,50);
        sun=new Hero (100, 100, 0, 0, 100, 100);
        /**STEP 3: add image to object**/
        coyPic=Toolkit.getDefaultToolkit().getImage("coy.png");
        birdPic=Toolkit.getDefaultToolkit().getImage("bird.png");
        monet=Toolkit.getDefaultToolkit().getImage("monet.png");
        lilypadPic=Toolkit.getDefaultToolkit().getImage("lilypad.png");
        sunPic=Toolkit.getDefaultToolkit().getImage("sun1.png");


        coy.printInfo();
        //for each object that has a picture, load in images as well


    }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //draw background image below:
        g.drawImage(monet,0,0,WIDTH, HEIGHT,null);
        //draw the image of your objects below:
        /**STEP 4: draw object images**/
        g.drawImage(coyPic, coy.xpos, coy.ypos, coy.width, coy.height, null);
        g.drawImage(birdPic, bird.xpos, bird.ypos, bird.width, bird.height, null);
        g.drawImage(lilypadPic, lilypad.xpos,lilypad.ypos, lilypad.width, lilypad.height, null);
        g.drawImage(sunPic, sun.xpos, sun.ypos, sun.width, sun.height, null);
        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();

        bufferStrategy.show();
    }

    public void moveThings() {
        //call the move() method code from your object class
        coy.bouncingMove();
        bird.wrappingMove();
        lilypad.bouncingMove();
        sun.wrappingMove();
    }

    public void collisions(){
        if (bird.rec.intersects(coy.rec) && birdIsIntersectingCoy==false){
            birdIsIntersectingCoy=true;
            System.out.println("ouch");
            bird.dx=bird.dx+1;
            coy.dx=coy.dx-1;
        }
        if (bird.rec.intersects(coy.rec)==false){
            birdIsIntersectingCoy=false;
        }
        if (bird.rec.intersects(lilypad.rec) && birdIsIntersectingLilypad==false){
            birdIsIntersectingLilypad=true;
            System.out.println("uh oh");
            bird.width=bird.width+10;
            lilypad.width=lilypad.width-2;
        }
        if (bird.rec.intersects(lilypad.rec)==false){
            birdIsIntersectingLilypad=false;
        }
        if (coy.rec.intersects(lilypad.rec) && coyIsIntersectingLilypad==false){
            coyIsIntersectingLilypad=true;
            System.out.println("watch out");
            coy.dx=coy.dx*-1;
            coy.dy=coy.dy*-1;

        }
        if (coy.rec.intersects(lilypad.rec)==false){
            coyIsIntersectingLilypad=false;
        }
        if (lilypad.rec.intersects(sun.rec) && lilypadIsIntersectingSun==false){
            lilypadIsIntersectingSun=true;
            System.out.println("photosynthesis!");
            lilypad.dx=lilypad.dx+2;
        }
        if (lilypad.rec.intersects(sun.rec)==false){
            lilypadIsIntersectingSun=false;
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}