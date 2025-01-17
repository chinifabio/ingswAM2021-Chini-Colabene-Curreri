package it.polimi.ingsw.model.player.personalBoard.warehouse.production;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.litewarehouse.LiteProduction;
import it.polimi.ingsw.model.exceptions.warehouse.production.IllegalNormalProduction;
import it.polimi.ingsw.model.exceptions.warehouse.production.IllegalTypeInProduction;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceBuilder;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the NormalProduction that the Player can activate
 */
public class NormalProduction extends Production{

    /**
     * This method is the constructor of the class
     * @param required required list of resource
     * @param output output list of resource
     */
    @JsonCreator
    public NormalProduction(@JsonProperty("required") List<Resource> required,@JsonProperty("output") List<Resource> output) throws IllegalTypeInProduction {
        super(required, output, Arrays.asList(ResourceType.EMPTY, ResourceType.UNKNOWN));
    }

    /**
     * This method activates the production
     * @return true if the Production is correctly activated
     */
    @Override
    public boolean activate() {
        if (activated) return false;

        for(int i = 0; i < required.size(); i++) {
            if (!required.get(i).equals(addedResource.get(i))) return false;
        }

        return (activated = true);
    }

    /**
     * This method is used to insert resources in the production
     * @param input the resource to insert
     * @return the succeed of the operation
     */
    @Override
    public boolean insertResource(Resource input) {
        if (illegalType.contains(input.type())) return false;

        // copy the stored resource of input resource type
        Resource stored = addedResource.stream().filter(x->x.equalsType(input)).findAny().get();
        Resource copy = stored.buildNewOne();
        boolean result;

        // check if input can be stored
        result = copy.merge(input);
        result &= (copy.amount() <= required.stream().filter(x->x.equalsType(copy)).findAny().get().amount());

        // store the input if it is legal
        if (result) {
            stored.merge(input);
            this.selected = true;
        }

       return result;
    }

    /**
     * This method allow to set the normal production in case of unknown resource
     * @param normalProduction the production to set as normal production in case of unknown resource
     * @return the succeed of the operation
     * @throws IllegalNormalProduction because the Production is already a NormalProduction
     */
    @JsonIgnore
    @Override
    public boolean setNormalProduction(NormalProduction normalProduction) throws IllegalNormalProduction {
        throw new IllegalNormalProduction("Normal production can't be normalized");
    }
}
