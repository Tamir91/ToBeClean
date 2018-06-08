package model;

/**
 * `
 * Created by tamir on 05/03/18.
 */

public class RecyclingContainer {

    private String containerAddress;
    private int type;

    /**
     * Second Constructor
     */
    public RecyclingContainer(int type) {
        this.containerAddress = "";
        this.type = type;
    }

    /**
     * @return String
     */
    public String getContainerAddress() {
        return containerAddress;
    }

    public void setContainerAddress(String containerAddress) {
        this.containerAddress = containerAddress;
    }


    /**
     * @return int
     */
    public int getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
