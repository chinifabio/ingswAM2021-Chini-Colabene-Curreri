package it.polimi.ingsw.model.player.personalBoard.warehouse.depot;

import it.polimi.ingsw.model.exceptions.NegativeResourcesDepotException;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;

public class SpecialDepot implements Depot {

    /**
     * This attribute is the type and the number of resources into the depot
     */
    private Resource resources;

    /**
     * This attribute is a constraint that verifies that the resources within the depot are of the same type
     */
    private final List<BiPredicate<Resource, Resource>> constraints;

    /**
     * This method is the constructor of the class
     */
    public SpecialDepot(Resource resource) {
        this.resources = ResourceBuilder.buildFromType(resource.type(),0);
        this.constraints = new ArrayList<>();
    }

    /**
     * This method accept a lambda function predicate with two parameters: first one is always referred to the input resource,
     * second one is the resource already contained in the depot
     *
     * @param constraint is the constraint that the depot checks before adding resources
     */
    @Override
    public void addConstraint(BiPredicate<Resource, Resource> constraint) {
        constraints.add(constraint);
    }

    /**
     * This method inserts the resources into the Depot
     *
     * @param input is the resource that will be inserted
     */
    @Override
    public boolean insert(Resource input) {
        AtomicBoolean testResult = new AtomicBoolean(true);
        this.constraints.forEach(x->{
            if (!x.test(input,this.resources)) testResult.set(false);
        });

        if (testResult.get()){
                this.resources.merge(input);
                return true;
        }
        return  false;
    }

    /**
     * This method removes the resources from the Depot
     *
     * @param output is the resource that will be withdrawn
     */
    @Override
    public boolean withdraw(Resource output) throws NegativeResourcesDepotException {
        if ((this.resources.type() != output.type()) && (this.resources.type() != ResourceType.EMPTY)){
            return false;
        }
        if (this.resources.amount() - output.amount() > 0){
            this.resources.reduce(output);
            return true;
        } else if (this.resources.amount() - output.amount() == 0){
            this.resources = ResourceBuilder.buildFromType(resources.type(),0);
            return true;
        } else {
            throw new NegativeResourcesDepotException("exception: This Depot does not have enough resources to withdraw ");
        }
    }

    /**
     * This method returns the resources that are into the depot
     */
    @Override
    public Resource viewResources() {
        return resources;
    }

    @Override
    public List<Resource> viewAllResources() {
        return null;
    }
}
