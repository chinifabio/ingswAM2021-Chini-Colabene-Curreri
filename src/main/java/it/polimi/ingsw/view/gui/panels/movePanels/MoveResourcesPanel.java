package it.polimi.ingsw.view.gui.panels.movePanels;

import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MoveResourcesPanel extends GuiPanel {

    public MoveResourcesPanel(GUI gui){
        super(gui);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.setOpaque(false);



        //--------BACK BUTTON----------
        JPanel backPanel = new JPanel();
        JButton back = new JButton("Return to PB");
        back.addActionListener(e -> {
            try {
                gui.switchPanels(new PersonalBoardPanel(gui));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        backPanel.add(back);
        backPanel.add(Box.createRigidArea(new Dimension(900,0)));
        backPanel.setOpaque(false);


        //------------BUFFER-------------
        JPanel bufferContainer = new JPanel();
        JPanel buffer = new BufferMovePanel(gui);
        bufferContainer.setLayout(new BoxLayout(bufferContainer, BoxLayout.Y_AXIS));
        buffer.setPreferredSize(new Dimension(275,100));
        bufferContainer.setOpaque(false);
        bufferContainer.add(Box.createRigidArea(new Dimension(0,200)));
        bufferContainer.add(buffer);

        JPanel bufferPanel = new JPanel();
        bufferPanel.add(bufferContainer);
        bufferPanel.setOpaque(false);

        //----------WareHouse------------
        JPanel warehousPanel = new JPanel();
        WarehouseMovePanel warehouse = new WarehouseMovePanel(gui);
        warehouse.setPreferredSize(new Dimension(350, 500));
        warehousPanel.add(warehouse);
        warehousPanel.setOpaque(false);

        //--------ExtraDepot------------
        JPanel extraPanel = new JPanel();
        ExtraDepotMovePanel extraDepotPanel = new ExtraDepotMovePanel(gui);
        extraPanel.setPreferredSize(new Dimension(350,100));
        extraDepotPanel.setOpaque(false);
        extraPanel.add(extraDepotPanel);
        extraPanel.setOpaque(false);

        JPanel depotAndBufferPanel = new JPanel();
        depotAndBufferPanel.setLayout(new BoxLayout(depotAndBufferPanel, BoxLayout.Y_AXIS));
        depotAndBufferPanel.add(extraPanel);
        depotAndBufferPanel.add(bufferPanel);
        depotAndBufferPanel.setOpaque(false);


        this.add(Box.createRigidArea(new Dimension(0, 100)));
        this.add(backPanel);
        this.add(Box.createRigidArea(new Dimension(0, 100)));
        middlePanel.add(warehousPanel);
        middlePanel.add(Box.createRigidArea(new Dimension(100, 0)));
        middlePanel.add(depotAndBufferPanel);
        this.add(middlePanel);

    }

    @Override
    public void reactToPacket(Packet packet) throws IOException {
        switch (packet.header) {
            case OK -> gui.switchPanels(new MoveResourcesPanel(gui));
            case INVALID -> gui.notifyPlayerError(packet.body);
        }
    }
}