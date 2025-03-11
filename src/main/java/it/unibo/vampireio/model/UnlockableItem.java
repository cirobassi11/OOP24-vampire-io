package it.unibo.vampireio.model;

public abstract class UnlockableItem implements Unlockable {
    private boolean unlocked;
    private final String id;
    private final String name;
    private final String description;
    private final int price;

    public UnlockableItem(final String id, final String name, final String description, final int price) {
        this.unlocked = false;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public boolean isUnlocked() {
        return this.unlocked;
    }

    @Override
    public void unlock() {
        //contollare se ha abbastanza soldi
        //se si, sottrarli
        this.unlocked = true;
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

}
