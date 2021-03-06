package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

import java.util.ArrayList;
import java.util.List;

public class GunRepository implements Repository<Gun>{

    private List<Gun> models;

    public GunRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public List<Gun> getModels() {
        return this.models;
    }

    @Override
    public void add(Gun gun) {
        if(gun == null){
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_REPOSITORY);
        }
            this.models.add(gun);
    }

    @Override
    public boolean remove(Gun gun) {
        return this.models.remove(gun);
    }

    @Override
    public Gun findByName(String name) {

        return this.models.stream()
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
