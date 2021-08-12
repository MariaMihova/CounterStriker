package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldImpl implements Field {
    //TODO privet Collection<Player> players;

    private List<Player> terrorists;
    private List<Player> counterTerrorists;

    public FieldImpl() {
        this.terrorists = new ArrayList<>();
        this.counterTerrorists = new ArrayList<>();
    }

    @Override
    public String start(Collection<Player> players) {
        for (Player player : players) {
            if (player.getClass().getSimpleName().equals("Terrorist")) {
                this.terrorists.add(player);
            } else if (player.getClass().getSimpleName().equals("CounterTerrorist")) {
                this.counterTerrorists.add(player);
            }
        }
        this.terrorists = checkAlive(this.terrorists);
        this.counterTerrorists = checkAlive(this.counterTerrorists);

            while (this.terrorists.size() != 0 && this.counterTerrorists.size() != 0) {

                for (Player terrorist : this.terrorists) {
                    for (Player counterTerrorist : this.counterTerrorists) {
                        counterTerrorist.takeDamage(terrorist.getGun().fire());
                    }
                    this.counterTerrorists = checkAlive(this.counterTerrorists);
                    if(counterTerrorists.size() == 0){
                        break;
                    }
                }
                for (Player counterTerrorist : this.counterTerrorists) {
                    for (Player terrorist : this.terrorists) {
                        terrorist.takeDamage(counterTerrorist.getGun().fire());
                    }
                    this.terrorists = checkAlive(this.terrorists);

                }
            }

            if(this.counterTerrorists.size() == 0){  //TODO !!!
                return OutputMessages.TERRORIST_WINS;
            }else {
                return OutputMessages.COUNTER_TERRORIST_WINS;
            }

    }

    private List<Player> checkAlive(List<Player> list){
        return list.stream().filter(p -> p.getHealth() > 0).collect(Collectors.toList());
    }
}
