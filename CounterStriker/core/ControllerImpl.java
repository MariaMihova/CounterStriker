package CounterStriker.core;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{

    private PlayerRepository players;
    private GunRepository guns;
    private Field field;

    public ControllerImpl() {
        this.players = new PlayerRepository();
        this.guns = new GunRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun = null;
        switch (type){
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_TYPE);
        }
        this.guns.add(gun);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Gun currentGun = this.guns.getModels().stream()
                .filter(g -> g.getName().equals(gunName))
                .findFirst()
                .orElse(null);
        if(currentGun == null){
            throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
        }
        Player player = null;
        switch (type){
            case "Terrorist":
                player = new Terrorist(username, health, armor, currentGun);
                break;
            case "CounterTerrorist":
                player = new CounterTerrorist(username, health, armor, currentGun);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }
        this.players.add(player);
        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        return field.start(this.players.getModels());
    }

    @Override
    public String report() {
        List<Player> result = this.players.getModels().stream()
                .sorted((a, b) -> a.getUsername().compareTo(b.getUsername()))
                .sorted((a, b) -> Integer.compare(b.getHealth(), a.getHealth()))
                .sorted((a, b) -> a.getClass().getSimpleName().compareTo(b.getClass().getSimpleName()))
               .collect(Collectors.toList());

        StringBuilder fill = new StringBuilder();
        for (Player player : result) {
            fill.append(player.toString())
                    .append(System.lineSeparator());

        }
        return fill.toString().trim();
    }
}
