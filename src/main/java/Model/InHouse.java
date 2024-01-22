package Model;

public class InHouse extends Part {
    private int machineId;

    /**
     * Contructor of InHouse Part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
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
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
