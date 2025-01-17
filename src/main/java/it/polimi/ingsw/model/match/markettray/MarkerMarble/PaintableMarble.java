package it.polimi.ingsw.model.match.markettray.MarkerMarble;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.litemodel.litemarkettray.LiteMarble;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.Optional;

/**
 * This class represents the White Marble that can be painted
 */
public class PaintableMarble extends Marble{

    /**
     * This attribute is the marble
     */
    private Optional<Marble> marble;

    /**
     * build a new white marble which is paintable
     * @param color is EMPTY because initially the white marble isn't painted
     */
    @JsonCreator
    protected PaintableMarble(@JsonProperty("color") MarbleColor color) {
        super(color, ResourceType.EMPTY);
        this.marble = Optional.empty();
    }

    /**
     * paints this marble
     * @return true because white marble is paintable
     */
    @JsonIgnore
    @Override
    public boolean isPaintable() {
        return true;
    }

    /**
     * This method paints the marble
     * @param painted new marble color
     */
    @Override
    public void paint(Marble painted){
        this.marble = Optional.of(painted);
        color = painted.color;
    }

    /**
     * return the resource associated to the color
     *
     * @return the mapped resource
     */
    @Override
    public Resource toResource() {
        return this.marble.isPresent() ?
                this.marble.get().toResource() :
                super.toResource();
    }


    /**
     * reset the paint of this marble
     */
    @Override
    public void unPaint() {
        this.marble = Optional.empty();
        color = MarbleColor.WHITE;
    }

    /**
     * copy the marble in a new instance
     *
     * @return new instance equals this
     */
    @Override
    public Marble copy() {
        return marble.isPresent() ?
                marble.get().copy():
                new PaintableMarble(MarbleColor.WHITE);
    }

    /**
     * Create a lite version of the class and serialize it in json
     *
     * @return the json representation of the lite version of the class
     */
    @Override
    public LiteMarble liteVersion() {
        return this.marble.map(value -> new LiteMarble(value.color(), value.toResource().type())).orElseGet(super::liteVersion);
    }
}
