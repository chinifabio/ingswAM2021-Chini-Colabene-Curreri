package it.polimi.ingsw.view.gui.panels.graphicComponents;

import it.polimi.ingsw.litemodel.LiteResource;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.DepotSlot;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.view.gui.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is the GUI Panel of the Warehouse
 */
public class WarehousePanel  extends JPanel {

    /**
     * This attribute is the GUI that contains info
     */
    private final GUI gui;

    /**
     * This is the constructor of the class
     * @param gui is the GUI that has the Warehouse info
     * @param player is the nickname of the player that own the Warehouse
     * @throws IOException if there is an I/O problem
     */
    public WarehousePanel(GUI gui, String player) throws IOException {
        this.gui = gui;

        this.setPreferredSize(new Dimension(320,400));
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JPanel topDepot = new JPanel();
        topDepot.setPreferredSize(new Dimension(75, 10));

        insertResourceInDepot(topDepot, DepotSlot.TOP, player);

        topDepot.setOpaque(false);



        JPanel middleDepot = new JPanel();

        insertResourceInDepot(middleDepot, DepotSlot.MIDDLE, player);

        middleDepot.setOpaque(false);
        middleDepot.setPreferredSize(new Dimension(75, 10));



        JPanel bottomDepot = new JPanel();

        insertResourceInDepot(bottomDepot, DepotSlot.BOTTOM, player);

        bottomDepot.setOpaque(false);
        bottomDepot.setPreferredSize(new Dimension(75, 10));

        JPanel bigStrong = new JPanel();


        JPanel strongbox = new JPanel();
        strongbox.setLayout(new GridLayout(2,2));

        for (LiteResource res : gui.model.getDepot(player, DepotSlot.STRONGBOX).getResourcesInside()){
            JPanel resource = new JPanel();
            resource.setLayout(new OverlayLayout(resource));
            resource.setOpaque(false);
            JLabel amount = new JLabel();
            amount.setFont(new Font(amount.getName(), Font.BOLD, 20));
            if (res.getType() == ResourceType.SERVANT){
                amount.setForeground(Color.WHITE);
            } else {
                amount.setForeground(Color.BLACK);
            }

            amount.setBackground(new Color(0, 0, 0, 10));
            amount.setText(String.valueOf(res.getAmount()));

            JLabel image = new JLabel();
            createResourceLabel(image, GUI.resourceImages.get(res.getType()));

            resource.add(amount);
            image.setAlignmentX(0.31f);
            image.setAlignmentY(0.55f);
            resource.add(image);

            strongbox.add(resource);
        }

        strongbox.setOpaque(false);

        strongbox.setPreferredSize(new Dimension(270, 120));

        bigStrong.add(strongbox);
        bigStrong.setOpaque(false);

        this.add(topDepot);
        this.add(middleDepot);
        this.add(bottomDepot);
        this.add(Box.createRigidArea(new Dimension(320, 40)));
        this.add(bigStrong);

    }

    /**
     * This method changes the passed label by adding the resource image
     * @param label is the label to change
     * @param resource is the path of the resource image
     **/
    public void createResourceLabel(JLabel label, String resource) throws IOException {
        InputStream url = this.getClass().getResourceAsStream("/" + resource);
        assert url != null;
        Image scaledImage = GUI.getScaledImage(ImageIO.read(url), 55, 55);
        ImageIcon icon1 = new ImageIcon(scaledImage);
        label.setIcon(icon1);
    }


    /**
     * This method creates the Resources inside the Panel of the Depot
     * @param depot is the Panel where the resource will be placed
     * @param slot is the DepotSlot that contains the resources
     * @param player is the nickname of the player that own the warehouse
     * @throws IOException if there is an I/O exception
     */
    public void insertResourceInDepot(JPanel depot, DepotSlot slot, String player) throws IOException {
        depot.add(Box.createRigidArea(new Dimension(35,0)));
        LiteResource tempRes = gui.model.getDepot(player, slot).getResourcesInside().get(0);

        for (int i = 0; i <  tempRes.getAmount(); i++){
            JLabel label = new JLabel();
            createResourceLabel(label, GUI.resourceImages.get(tempRes.getType()));
            depot.add(label);
        }
    }
}
