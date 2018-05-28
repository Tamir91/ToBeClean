package model;

/**
 * `
 * Created by tamir on 05/03/18.
 */

public class RecyclingContainer {
    private static final short GLASS = 0;
    private static final short PLASTIC = 1;
    private static final short PAPER = 2;
    private static final short BOX = 3;

    private String containerAddress;
    private int type;


    /**
     * First Constructor
     * @param containerAddress {@link String}
     * @param type int
     */
//    public RecyclingContainer(String containerAddress, int type) {
//        this.containerAddress = "";
//        imgID = R.mipmap.ic_launcher_round;
//    }

    /**
     * Second Constructor
     */
    public RecyclingContainer(String containerAddress/*, int imgID*/, int type) {
        this.containerAddress = containerAddress;
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
