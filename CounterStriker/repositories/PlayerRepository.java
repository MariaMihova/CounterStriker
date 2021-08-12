package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository implements Repository<Player>{

    private List<Player> models;

    public PlayerRepository() {
        this.models = new ArrayList<>();
    }


    @Override
    public List<Player> getModels() {
        return this.models;
    }

    @Override
    public void add(Player player) {
        if(player == null){
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_REPOSITORY);
        }
        this.models.add(player);
    }

    @Override
    public boolean remove(Player player) {
        return this.models.remove(player);
    }

    @Override
    public Player findByName(String name) {
        return this.models.stream()
                .filter(p -> p.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }
}
