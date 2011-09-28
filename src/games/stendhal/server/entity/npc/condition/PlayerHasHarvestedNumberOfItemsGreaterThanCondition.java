package games.stendhal.server.entity.npc.condition;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * Checks if a player has harvested a minimum number of an item
 * 
 * @author madmetzger
 */
public class PlayerHasHarvestedNumberOfItemsGreaterThanCondition implements ChatCondition {

	private final List<String> itemMinedList;
	
	private final int quantity;
	
	public PlayerHasHarvestedNumberOfItemsGreaterThanCondition(int number, String... items) {
		itemMinedList = new ArrayList<String>();
		if(items != null) {
			for(String item : items) {
				itemMinedList.add(item);
			}
		}
		quantity = number;
	}

	public boolean fire(Player player, Sentence sentence, Entity npc) {
		for(String item : itemMinedList) {
			if(quantity > player.getQuantityOfHarvestedItems(item)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				PlayerHasHarvestedNumberOfItemsGreaterThanCondition.class);
	}
	
	@Override
	public String toString() {
		return "player has harvested <"+quantity+" of "+itemMinedList+">";
	}

}
