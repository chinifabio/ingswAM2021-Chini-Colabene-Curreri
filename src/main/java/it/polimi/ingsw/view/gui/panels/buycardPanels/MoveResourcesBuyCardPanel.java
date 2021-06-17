package it.polimi.ingsw.view.gui.panels.buycardPanels;

import it.polimi.ingsw.communication.packet.ChannelTypes;
import it.polimi.ingsw.communication.packet.HeaderTypes;
import it.polimi.ingsw.communication.packet.Packet;
import it.polimi.ingsw.communication.packet.commands.BuyDevCardCommand;
import it.polimi.ingsw.communication.packet.commands.ReturnCommand;
import it.polimi.ingsw.model.player.personalBoard.DevCardSlot;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.panels.GuiPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MoveResourcesBuyCardPanel extends GuiPanel {

    private int r;
    private  int c;
    Image image;

    public MoveResourcesBuyCardPanel(GUI gui, int r, int c, Image card) {
        super(gui);
        this.r = r;
        this.c = c;
        this.image = card;
    }

    @Override
    public JPanel update() throws IOException {
        JPanel result = new JPanel();


        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        result.setOpaque(false);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.setOpaque(false);


        DevCardSlot[] possibleValues = {DevCardSlot.LEFT, DevCardSlot.CENTER, DevCardSlot.RIGHT};

        //--------BACK BUTTON----------
        JPanel backPanel = new JPanel();
        JButton back = new JButton("Return to Grid");
        back.addActionListener(e -> {
            gui.socket.send(new Packet(HeaderTypes.DO_ACTION, ChannelTypes.PLAYER_ACTIONS, new ReturnCommand().jsonfy()));
            gui.switchPanels(new CardsGridPanel(gui));
        });

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            DevCardSlot slot = (DevCardSlot) JOptionPane.showInputDialog(null, "Select the Slot of the PersonalBoard \n where the Development Card will be placed", "BuyCards",
                    JOptionPane.QUESTION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
            if (slot != null){
                gui.socket.send(new Packet(HeaderTypes.DO_ACTION, ChannelTypes.PLAYER_ACTIONS, new BuyDevCardCommand(gui.model.getDevSetup().getDevSetup()[r][c].getLevel(), gui.model.getDevSetup().getDevSetup()[r][c].getColor(), slot).jsonfy()));
                gui.switchPanels(new CardsGridPanel(gui));
            }
        });

        backPanel.add(back);
        backPanel.add(Box.createRigidArea(new Dimension(500,0)));
        backPanel.add(confirm);
        backPanel.setOpaque(false);


        //------------BUFFER-------------
        JPanel bufferContainer = new JPanel();
        JPanel buffer = new BufferDevCardPanel(gui);
        bufferContainer.setLayout(new BoxLayout(bufferContainer, BoxLayout.Y_AXIS));
        buffer.setPreferredSize(new Dimension(275,100));
        bufferContainer.setOpaque(false);
        bufferContainer.add(Box.createRigidArea(new Dimension(0,200)));
        bufferContainer.add(buffer);

        JPanel bufferPanel = new JPanel();
        bufferPanel.add(bufferContainer);
        bufferPanel.setOpaque(false);

        //----------WareHouse------------
        JPanel warehousePanel = new JPanel();
        WarehouseBuyCardPanel warehouse = new WarehouseBuyCardPanel(gui);
        warehouse.setPreferredSize(new Dimension(350, 500));
        warehousePanel.add(warehouse);
        warehousePanel.setOpaque(false);

        //--------ExtraDepot------------
        JPanel extraPanel = new JPanel();
        ExtraDepotBuyCardPanel extraDepotPanel = new ExtraDepotBuyCardPanel(gui);
        extraPanel.setPreferredSize(new Dimension(350,100));
        extraDepotPanel.setOpaque(false);
        extraPanel.add(extraDepotPanel);
        extraPanel.setOpaque(false);

        JPanel depotAndBufferPanel = new JPanel();
        depotAndBufferPanel.setLayout(new BoxLayout(depotAndBufferPanel, BoxLayout.Y_AXIS));
        depotAndBufferPanel.add(extraPanel);
        depotAndBufferPanel.add(bufferPanel);
        depotAndBufferPanel.setOpaque(false);

        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false);
        JTextArea message = new JTextArea();
        message.setText("Move the resources needed to buy the Development Card");
        message.setFont(new Font("Times New Roman",Font.ITALIC,22));
        message.setForeground(GUI.borderColor);
        message.setEditable(false);
        message.setOpaque(false);

        JLabel cardImage = new JLabel();
        Image cardScaled = GUI.getScaledImage(image,462/4, 698/4);
        ImageIcon icon = new ImageIcon(cardScaled);
        cardImage.setIcon(icon);

        messagePanel.add(message);
        messagePanel.add(cardImage);



        result.add(Box.createRigidArea(new Dimension(0, 30)));
        result.add(backPanel);
        result.add(Box.createRigidArea(new Dimension(0, 20)));
        result.add(messagePanel);
        result.add(Box.createRigidArea(new Dimension(0, 20)));
        middlePanel.add(warehousePanel);
        middlePanel.add(Box.createRigidArea(new Dimension(100, 0)));
        middlePanel.add(depotAndBufferPanel);
        result.add(middlePanel);

        return result;
    }
}
