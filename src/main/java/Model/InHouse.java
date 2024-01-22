package Model;

/**
 * InHouse Class
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Contructor of InHouse Part
     *
     * @param id        id
     * @param name      name
     * @param price     price
     * @param stock     stock
     * @param min       min
     * @param max       max
     * @param machineId machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * get machine id
     *
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * set machine id
     *
     * @param machineId machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
