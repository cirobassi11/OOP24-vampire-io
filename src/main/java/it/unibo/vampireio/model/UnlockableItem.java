package it.unibo.vampireio.model;

public abstract class UnlockableItem implements Unlockable {
    private boolean unlocked;
    private final String name;
    private final int price;

    public UnlockableItem(final String name, final int price) {
        this.name = name;
        this.price = price;
        this.unlocked = false;
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
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}
