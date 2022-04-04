package model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.Level;
import model.helper.BodyHelper;
import model.helper.Constants;
import model.helper.ContactType;

public class Goomba extends MovableObject {

    private static final float X_VELOCITY = 3.2f;
    private static final int attack = 40;
    private final TextureRegion textureRegion;
    private int numMoves;
    private boolean playerNearby = false;
    private Vector2 playerPosition;

    public Goomba(String name, Level level, float x, float y) {
        super(name + " " + (level.getGameObjects(Goomba.class).size() + 1), level, x, y, 1, ContactType.ENEMY, Constants.ENEMY_BIT, Constants.ENEMY_MASK_BITS);
        texturePath = "Multi_Platformer_Tileset_v2/Enemies/Goomba.png";
        texture = new Texture(texturePath);
        textureRegion = new TextureRegion(getTexture(), Constants.TILE_SIZE, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

        numMoves = 0;
    }

    public static int getAttack() {
        return attack;
    }

    @Override
    public void update() {
        super.update();

        if (!facingRight && !textureRegion.isFlipX()) {
            textureRegion.flip(true, false);
        } else if (facingRight && textureRegion.isFlipX()) {
            textureRegion.flip(true, false);
        }

        goombaMovement();
    }

    private void goombaMovement() {
        if (playerNearby) {
            if (playerPosition.x > body.getPosition().x) {
                moveHorizontally(true);
            }
            if (playerPosition.x < body.getPosition().x) {
                moveHorizontally(false);
            }
        } else {
            int range = 150;
            if (numMoves > 0 && numMoves < range) {
                moveHorizontally(false);
            }
            if (numMoves == range) {
                numMoves = -range;
            }
            if (numMoves < 0) {
                moveHorizontally(true);
            }
            numMoves++;
        }
    }

    @Override
    public void moveHorizontally(boolean isRight) {
        if (isRight) {
            body.applyLinearImpulse(new Vector2(X_VELOCITY, 0), body.getWorldCenter(), true);
            facingRight = true;
        } else {
            body.applyLinearImpulse(new Vector2(-X_VELOCITY, 0), body.getWorldCenter(), true);
            facingRight = false;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, x, y, width, height);
    }

    public void setPlayerNearby(boolean value) {
        playerNearby = value;
    }

    public void setPlayerPostion(Vector2 position) {
        playerPosition = position;
    }

    public void setDead() {
        BodyHelper.changeFilterData(body, Constants.DESTROYED_BIT, Constants.DESTROYED_MASK_BITS);
    }
}
