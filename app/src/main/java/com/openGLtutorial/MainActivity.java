package com.openGLtutorial;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.patrickkaalund.opengltutorial.R;
import com.graphic.Direction;
import com.graphic.Entity;
import com.graphic.OurGLSurfaceView;
import com.graphic.SpriteEntityFactory;

public class MainActivity extends AppCompatActivity implements Runnable {

    private OurGLSurfaceView glSurfaceView;
    private SpriteEntityFactory enemyFactory;
    private Entity enemy;
    public boolean isRunning = false;
    public static final int[] NORMAL_ANIMATIONS = new int[]{4, 5, 6, 7, 8, 9, 10, 11};
    public static final int NORMAL_ANIMATION_DIV = 3;
    private Direction direction;
    private PointF initialPosition = new PointF(400, 400);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create our gl Surface view to draw on
        glSurfaceView = new OurGLSurfaceView(this);

        // Set the content View to our gl view
        setContentView(glSurfaceView);

        // Create our factory
        float enemyWidth = 235;
        float enemyHeight = 235;
        int numberOfRowsInSpriteSheet = 4;
        int numberOfColonsInSpriteSheet = 18;
        enemyFactory = new SpriteEntityFactory(R.drawable.zombie, enemyWidth, enemyHeight, numberOfRowsInSpriteSheet, numberOfColonsInSpriteSheet, new PointF(0, 0));
        enemy = enemyFactory.createEntity();                    // Create enemy from enemyFactory

        enemy.placeAt(400, 400);                                // Position on screen. Bottom left is 0,0
        enemy.setCurrentSprite(0);                              // Initial animation to show
        enemy.setAngleOffSet(-225);                             // Pointing right
        enemy.setAnimationOrder(NORMAL_ANIMATIONS);             // Animates the following sprites on the sprite sheet Zombie in drawable
        enemy.setAnimationDivider(NORMAL_ANIMATION_DIV);        // Throttle animation updating
        enemy.setPosition(initialPosition);                     // Set global position

        // Walking upwards with speed 1
        direction = new Direction();
        direction.set(90, 1);

        // Ready to run
        isRunning = true;

        // Updating thread
        Thread thread = new Thread(this);
        thread.start();

    }

    // Running gui thread with 30 Hz
    @Override
    public void run() {
        while (isRunning) {

            this.enemy.drawNextSprite();

            this.enemy.move(direction);

//            Log.e("MainActivity", "Enemy pos y: " + enemy.getPosition().y);
            if(enemy.getPosition().y > 1000){
                enemy.placeAt(initialPosition.x, initialPosition.y);
                enemy.setPosition(initialPosition);
            }

            glSurfaceView.requestRender();

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
