package it.unibo.vampireio.model;

public abstract class UnlockableItem implements Unlockable {
    private final String id;
    private final String name;
    private final String description;
    private final int price;
    private int currentLevel;
    private int maxLevel;

    public UnlockableItem(final String id, final String name, final String description, final int price, final int maxLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currentLevel = 0;
    }

    @Override
    public boolean isLocked() {
        return this.currentLevel > 0;
    }

    @Override
    public void enhance() {
        //controllare se l'item ha raggiunto livello massimo
        //se non l'ha raggiunto, controllare se ha abbastanza soldi
        //se si, sottrarli
        //salvare salvataggio (dove va fatto??)
        
        this.currentLevel++;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getLevel() {
        return this.currentLevel;
    }

}
