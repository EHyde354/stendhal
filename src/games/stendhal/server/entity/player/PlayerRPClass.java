/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.player;

import games.stendhal.common.Debug;
import games.stendhal.common.constants.Events;
import games.stendhal.server.actions.admin.AdministrationAction;
import games.stendhal.server.core.engine.ItemLogger;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TutorialNotifier;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.creature.DomesticAnimal;
import games.stendhal.server.entity.creature.Pet;
import games.stendhal.server.entity.creature.Sheep;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.slot.BankSlot;
import games.stendhal.server.entity.slot.Banks;
import games.stendhal.server.entity.slot.PlayerSlot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import marauroa.common.Configuration;
import marauroa.common.game.Definition;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;
import marauroa.common.game.Definition.Type;
import marauroa.common.io.UnicodeSupportingInputStreamReader;

import org.apache.log4j.Logger;

/**
 * Handles the RPClass registration and updating old Player objects created by
 * an older version of Stendhal.
 */
public class PlayerRPClass {


	
	private static Logger logger = Logger.getLogger(PlayerRPClass.class);






	/**
	 * Generates the RPClass and specifies slots and attributes.
	 */
	static void generateRPClass() {
		final RPClass player = new RPClass("player");
		player.isA("rpentity");
		player.addAttribute("text", Type.LONG_STRING, Definition.VOLATILE);

		player.addRPEvent(Events.PRIVATE_TEXT, Definition.PRIVATE);
		player.addRPEvent(Events.OPEN_OFFER_PANEL, Definition.PRIVATE);

		player.addAttribute("poisoned", Type.SHORT, Definition.VOLATILE);
		player.addAttribute("eating", Type.SHORT, Definition.VOLATILE);
		player.addAttribute("choking", Type.SHORT, Definition.VOLATILE);

		player.addAttribute("dead", Type.FLAG, Definition.PRIVATE);

		player.addAttribute("outfit", Type.INT);
		player.addAttribute("outfit_org", Type.INT);
		// player.addAttribute("outfit_path", Type.STRING);

		player.addAttribute("away", Type.LONG_STRING, Definition.VOLATILE);
		player.addAttribute("grumpy", Type.LONG_STRING, Definition.VOLATILE);

		// Use this for admin menus and usage.
		player.addAttribute("admin", Type.FLAG);
		player.addAttribute("adminlevel", Type.INT);
		player.addAttribute("invisible", Type.FLAG, Definition.HIDDEN);
		player.addAttribute("ghostmode", Type.FLAG);
		player.addAttribute("teleclickmode", Type.FLAG);

		player.addAttribute("release", Type.STRING, Definition.PRIVATE);

		player.addAttribute("age", Type.INT);

		// Store sheep at DB
		player.addRPSlot("#flock", 1, Definition.HIDDEN);
		player.addAttribute("sheep", Type.INT);

		// Store pets at DB
		player.addRPSlot("#pets", 1, Definition.HIDDEN);
		player.addAttribute("pet", Type.INT);
		player.addAttribute("cat", Type.INT);
		player.addAttribute("baby_dragon", Type.INT);

		// Bank system
		player.addRPSlot("bank", 30, Definition.HIDDEN);
		player.addRPSlot("bank_ados", 30, Definition.HIDDEN);
		player.addRPSlot("zaras_chest_ados", 30, Definition.HIDDEN);
		player.addRPSlot("bank_fado", 30, Definition.HIDDEN);
		player.addRPSlot("bank_nalwor", 30, Definition.HIDDEN);

		// Kills recorder - needed for quest
		player.addRPSlot("!kills", 1, Definition.HIDDEN);

		// We use this for the buddy system
		player.addRPSlot("!buddy", 1, Definition.PRIVATE);
		player.addRPSlot("!ignore", 1, Definition.HIDDEN);
		player.addAttribute("online", Type.LONG_STRING,
				(byte) (Definition.PRIVATE | Definition.VOLATILE));
		player.addAttribute("offline", Type.LONG_STRING,
				(byte) (Definition.PRIVATE | Definition.VOLATILE));

		player.addRPSlot("!quests", 1, Definition.HIDDEN);
		player.addRPSlot("!tutorial", 1, Definition.HIDDEN);

		player.addAttribute("karma", Type.FLOAT, Definition.PRIVATE);
		player.addAttribute("sentence", Type.STRING, Definition.HIDDEN);

		player.addRPSlot("skills", 1, Definition.HIDDEN);

		// Non-removable while stored ones have values
		player.addRPSlot("!skills", 1,
				(byte) (Definition.HIDDEN | Definition.VOLATILE));

		player.addRPSlot("!visited", 1, Definition.HIDDEN);

		// This is the RPSlot for the spells. It's main purpose is to let us add
		// a GUI for the spells later on.
		player.addRPSlot("spells", 9, Definition.PRIVATE);

		// The guild name
		player.addAttribute("guild", Type.STRING);

		// Player features
		player.addRPSlot("!features", 1, Definition.PRIVATE);

		// Last time this player attacked another player
		player.addAttribute("last_pvp_action_time", Type.FLOAT,
				Definition.HIDDEN);
		player.addAttribute("last_player_kill_time", Type.FLOAT,
				Definition.STANDARD);
		
		player.addRPEvent("transition_graph", Definition.STANDARD);
		player.addRPEvent("examine", Definition.STANDARD);
	}










}
