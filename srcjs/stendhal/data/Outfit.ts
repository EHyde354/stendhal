/***************************************************************************
 *                     Copyright © 2003-2023 - Arianne                     *
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU Affero General Public License as        *
 *   published by the Free Software Foundation; either version 3 of the    *
 *   License, or (at your option) any later version.                       *
 *                                                                         *
 ***************************************************************************/


export class Outfit {

	// player pickable layers
	private count: {[key: string]: number} = {
		"hat": 19,
		"hair": 57,
		"mask": 9,
		"eyes": 28,
		"mouth": 5,
		"head": 4,
		"dress": 65,
		"body": 3
	};

	private busty_dress: number[] = [
		  1,   4,   6,   7,  10,  11,  13,  16,
		 29,  37,  40,  53,  54,  56,  61,  64,
		967, 968, 977, 980, 989, 990, 999
	];

	// hair should not be drawn with hat indexes in this list
	private hats_no_hair: number[] = [
		3, 4, 13, 16, 992, 993, 994, 996, 997
	];


	/**
	 * Determines if hair should be drawn under a determinted hat index.
	 *
	 * @param hat
	 *     Hat index to be checked.
	 * @return
	 *     <code>true</code> if hair should be drawn, <code>false</code> otherwise.
	 */
	drawHair(hat: number): boolean {
		return this.hats_no_hair.indexOf(hat) < 0;
	}

	/**
	 * Determines if a busty alternative dress layer should be drawn.
	 *
	 * @param dress
	 *     Dress index.
	 * @param body
	 *     Body index.
	 * @return
	 *     <code>true</code> if body type is busty & a busty dress
	 *     alternative is available.
	 */
	drawBustyDress(dress: number, body: number): boolean {
		return body == 1 && this.busty_dress.indexOf(dress) > -1;
	}
}
