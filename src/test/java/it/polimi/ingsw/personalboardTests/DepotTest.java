package it.polimi.ingsw.personalboardTests;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.exceptions.warehouse.NegativeResourcesDepotException;
import it.polimi.ingsw.model.exceptions.warehouse.WrongDepotException;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.Depot;
import it.polimi.ingsw.model.player.personalBoard.warehouse.depot.DepotBuilder;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import it.polimi.ingsw.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DepotTest {
    /**
     * Testing if resources are correctly removed by NormalDepot
     * @throws NegativeResourcesDepotException if the depot doesn't have enough resources to be removed
     */
    @Test
    public void removeResourcesFromNormalDepot() throws NegativeResourcesDepotException, WrongDepotException {
        Depot depot = DepotBuilder.buildBottomDepot();
        Resource toRemove = ResourceBuilder.buildCoin();
        Resource notInDepot = ResourceBuilder.buildServant();
        boolean exception = false;

        depot.insert(ResourceBuilder.buildCoin(2));
        assertEquals(ResourceBuilder.buildCoin(2), depot.viewResources().get(0));
        depot.withdraw(toRemove);
        assertEquals(ResourceBuilder.buildCoin(), depot.viewResources().get(0));
        depot.withdraw(notInDepot);
        assertEquals(ResourceBuilder.buildCoin(), depot.viewResources().get(0));
        depot.withdraw(toRemove);

        try{
            depot.withdraw(toRemove);
        }catch (NegativeResourcesDepotException e){
            exception = true;
        }
        assertTrue(exception);

        depot.insert(ResourceBuilder.buildServant(3));
        assertEquals(ResourceBuilder.buildServant(3),depot.viewResources().get(0));
        depot.withdraw(ResourceBuilder.buildServant(2));
        assertEquals(ResourceBuilder.buildServant(),depot.viewResources().get(0));
    }

    /**
     * Testing if resources are correctly removed by Strongbox
     * @throws NegativeResourcesDepotException if the Strongbox doesn't have enough resources to be removed
     */
    @Test
    public void removeFromStrongbox() throws NegativeResourcesDepotException, WrongDepotException {
        Depot depot = DepotBuilder.buildStrongBoxDepot();
        List<Resource> list = new ArrayList<>();
        boolean exc = false;

        list.add(0,ResourceBuilder.buildCoin(4));
        list.add(1, ResourceBuilder.buildStone(0));
        list.add(2,ResourceBuilder.buildShield(5));
        list.add(3,ResourceBuilder.buildServant(2));


        depot.insert(ResourceBuilder.buildServant(2));
        depot.insert(ResourceBuilder.buildCoin(4));
        depot.insert(ResourceBuilder.buildShield(5));
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());


        depot.withdraw(ResourceBuilder.buildCoin(3));
        list.set(0,ResourceBuilder.buildCoin(1));
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());


        depot.withdraw(ResourceBuilder.buildCoin());
        list.set(0,ResourceBuilder.buildCoin(0));
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());



        depot.insert(ResourceBuilder.buildServant(2));
        depot.insert(ResourceBuilder.buildStone(2));
        list.set(3,ResourceBuilder.buildServant(4));
        list.set(1, ResourceBuilder.buildStone(2));
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());



        depot.withdraw(ResourceBuilder.buildServant(4));
        list.set(3,ResourceBuilder.buildServant(0));
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());

        try {
            depot.withdraw(ResourceBuilder.buildShield(20));
        } catch (NegativeResourcesDepotException e){
            exc = true;
        }
        assertTrue(exc);
        assertArrayEquals(list.toArray(), depot.viewResources().toArray());
    }

    @Test
    public void removeFromSpecialDepot() throws NegativeResourcesDepotException {
        Depot special = DepotBuilder.buildSpecialDepot(ResourceType.SHIELD);
        boolean exc = false;

        special.insert(ResourceBuilder.buildShield(2));
        special.withdraw(ResourceBuilder.buildShield());
        assertEquals(ResourceBuilder.buildShield(),special.viewResources().get(0));

        special.withdraw(ResourceBuilder.buildShield());
        assertEquals(ResourceBuilder.buildShield(0),special.viewResources().get(0));

        special.insert(ResourceBuilder.buildStone(2));
        assertEquals(ResourceBuilder.buildShield(0),special.viewResources().get(0));

        special.insert(ResourceBuilder.buildShield(1));
        assertEquals(ResourceBuilder.buildShield(1),special.viewResources().get(0));

        try {
            special.withdraw(ResourceBuilder.buildShield(3));
        } catch (NegativeResourcesDepotException e){
            exc = true;
        }
        assertTrue(exc);
        assertEquals(ResourceBuilder.buildShield(1),special.viewResources().get(0));


    }
}
