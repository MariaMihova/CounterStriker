package CounterStriker.models.guns;

public class Rifle extends GunImpl{

    private static final int BULLETS_PER_SHOT = 10;

    public Rifle(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

    @Override
    public int fire() {
        if (this.getBulletsCount() - BULLETS_PER_SHOT < 0) {
            return 0;
        }
        this.setBulletsCount(this.getBulletsCount() - BULLETS_PER_SHOT);
        return BULLETS_PER_SHOT;
    }
}
