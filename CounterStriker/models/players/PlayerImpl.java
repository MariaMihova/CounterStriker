package CounterStriker.models.players;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

public abstract class PlayerImpl implements Player{

    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;

    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.setGun(gun);
        this.setAlive(health);
    }

    private void setUsername(String username){
        if(username == null || username.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_NAME);
        }
        this.username = username;
    }

    private void setHealth(int health){
        if(health < 0){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_HEALTH);
        }
        this.health = health;
    }

    private void setArmor(int armor){
        if(armor < 0){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_ARMOR);
        }
        this.armor = armor;
    }

    private void setAlive(int health){

        if(health <= 0) {
            this.isAlive = false;
        }else {
            this.isAlive = true;
        }
    }

    private void setGun(Gun gun){
        if(gun == null){
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_NAME);
        }
        this.gun = gun;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void takeDamage(int points) {
       if(this.getArmor() - points < 0){
           this.setArmor(0);
           if(this.getHealth() - points < 0){
               this.setHealth(0);
               this.setAlive(this.getHealth());
           }else {
               this.setHealth(this.getHealth() - points);
           }
       }else {
           this.setArmor(this.getArmor() - points);
       }

    }
    //"{player type}: {player username}
    //--Health: {player health}
    //--Armor: {player armor}
    //--Gun: {player gun name}"

    @Override
    public String toString(){
        StringBuilder fill = new StringBuilder();
        fill.append(String.format("%s: %s", this.getClass().getSimpleName(), this.getUsername()))
                .append(System.lineSeparator())
                .append(String.format("--Health: %d", this.getHealth()))
                .append(System.lineSeparator())
                .append(String.format("--Armor: %d", this.getArmor()))
                .append(System.lineSeparator())
                .append(String.format("--Gun: %s", this.getGun().getName()));
        return fill.toString().trim();

    }
}
