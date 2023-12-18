import processing.core.PApplet;

public class Sketch extends PApplet {
  
  // Arrays for snowflakes
  float[] circleY = new float[70];
  float[] circleX = new float[70];
  boolean[] ballHideStatus = new boolean[70];

  // Player Variables
  float playerX = 200;
  float playerY = 385;
  int playerLives = 3;
  boolean recentDamage = false;

  // Booleans to support handling multiple keys
  boolean upPressed = false;
  boolean downPressed = false;
  boolean leftPressed = false;
  boolean rightPressed = false;

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(50);
    // Randomizes location of snowflakes in array
    for (int i = 0; i < circleY.length; i++) {
      circleY[i] = random(250);
      circleX[i] = random(width);

      ballHideStatus[i] = false;
    }
  }
  
  public void draw() {
    background(50);

    // Speeds up/Slows down snowfall when holding down/up
    float speedModifier = 1.25f;
    if (keyPressed) {
      if (keyCode == UP) {
        speedModifier = 0.75f;
      } else if (keyCode == DOWN) {
          speedModifier = 2.5f;
      }
    } 

    // Draws the snowflakes
    for (int i = 0; i < circleY.length; i++) {
      if (ballHideStatus[i] == false) {
        fill(255, 255, 255);
        ellipse(circleX[i], circleY[i], 18, 18);
        
        circleY[i] += speedModifier;
      }
      if (circleY[i] > height) {
        circleY[i] = 0;
      }

      // Determines collision, minuses a life if collides
      float snowDistance = dist(playerX, playerY, circleX[i], circleY[i]);
      if (snowDistance < 18 && recentDamage == false) {
        background(255, 0, 0);
        playerLives--;

        recentDamage = true;

        if (playerLives == 0) {
          background(255);
          noLoop();

          fill(255, 0, 0);
          textSize(32);
          textAlign(CENTER, CENTER);
          text("Game Over :(", 200, 200);

          return;
        }
      }
    }

    // Makes it so there's a delay before user can get damaged again
    if (recentDamage) {
      if (frameCount % 120 == 0) {
        recentDamage = false;
      }
    }

    // Draws the player
    fill(0, 0, 255);
    ellipse(playerX, playerY, 20, 20);

    // Draws the lives
    fill(255, 0, 0);
    for (int i = 0; i < playerLives; i++) {
      rect(width - 30, 10 + i * 20, 20, 10);
    }

    // Moves the player
    if (keyPressed) {
      if (upPressed) {
        if (playerY >= 15) {
          playerY -= 3;
        }
      }
      if (downPressed) {
        if (playerY <= 385) {
          playerY += 3;
        }
      }
      if (leftPressed) {
        if (playerX >= 15) {
          playerX -= 3;
        }
      }
      if (rightPressed) {
        if (playerX <= 385) {
          playerX += 3;
        }
      }
    }
  }

  /**
  * Supports handling multiple keys with booleans
  */  
  public void keyPressed() {
    if (key == 'w') {
      upPressed = true;
    }
    else if (key == 's') {
      downPressed = true;
    }
    else if (key == 'a') {
      leftPressed = true;
    }
    else if (key == 'd') {
      rightPressed = true;
    }
  }
  
  /**
  * Supports handling multiple keys with booleans
  */
  public void keyReleased() {
    if (key == 'w') {
      upPressed = false;
    }
    else if (key == 's') {
      downPressed = false;
    }
    else if (key == 'a') {
      leftPressed = false;
    }
    else if (key == 'd') {
      rightPressed = false;
    }
  }

  /**
  * Handles mouse clicking to hide snowflaes
  */
  public void mouseClicked() {
    for (int i = 0; i < circleY.length; i++) {
      float mouseDistance = dist(mouseX, mouseY, circleX[i], circleY[i]);
      if (mouseDistance < 20) {
        ballHideStatus[i] = true;
        snowflakeReset(i);
      }
    }
  }

  /**
  * Resets a snowflake by making it visible again, but respawning it at the top
  *
  * @param arrayIndex The index of the snowflake/hiding arrays
  *
  */
  public void snowflakeReset(int arrayIndex) {
    circleY[arrayIndex] = 0;
    circleX[arrayIndex] = random(width);
    ballHideStatus[arrayIndex] = false;
  }
  
}