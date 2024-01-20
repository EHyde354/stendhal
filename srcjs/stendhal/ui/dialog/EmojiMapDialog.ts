/***************************************************************************
 *                    Copyright © 2023-2024 - Stendhal                     *
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU Affero General Public License as        *
 *   published by the Free Software Foundation; either version 3 of the    *
 *   License, or (at your option) any later version.                       *
 *                                                                         *
 ***************************************************************************/

import { DialogContentComponent } from "../toolkit/DialogContentComponent";
import { singletons } from "../../SingletonRepo";

declare var marauroa: any;
declare var stendhal: any;


export class EmojiMapDialog extends DialogContentComponent {

	private static readonly glyphs = [
		"☺", "☻",
		"🙂",
		"🙃",
		"😊",
		"😉",
		"😛",
		"😜",
		"😋",
		"😀", "😃",
		"☹",
		"🙁",
		"😢",
		"🥲",
		"😂",
		"😕",
		"🙄",
		"😐",
		"😑",
		"😶",
		"😲", "😮", "😯",
		"😖", "😣",
		"🤓",
		"😎",
		"😇", "👼",
		"❤",
		"💙",
		"💚",
		"💜",
		"💛",
		"💘",
		"💔",
		"💢",
		"🗢", "👄", "💋",
		"💧",
		"♩", "𝅘𝅥",
		"♪", "𝅘𝅥𝅮", "🎵",
		"♬", "🎜",
		"♫", "🎝",
		"👍",
		"👎",
		"👋"
	];
	private readonly sysEmojis: boolean;


	constructor() {
		super("emojimap-template");

		// Note: dialog must be re-constructed if setting changes
		this.sysEmojis = stendhal.config.getBoolean("client.emojis.native");

		const emojiStore = singletons.getEmojiStore();
		let emojiList = EmojiMapDialog.glyphs;
		if (!this.sysEmojis) {
			emojiList = emojiStore.getEmojiList();
		}
		let idx = 0;
		let row: HTMLDivElement = document.createElement("div");
		this.componentElement.appendChild(row);
		for (const emoji of emojiList) {
			if (idx > 0 && idx % 13 == 0) {
				// new row
				row = document.createElement("div");
				this.componentElement.appendChild(row);
			}
			const button = document.createElement("button");
			button.classList.add("shortcut-button", "emoji-text");
			if (!this.sysEmojis) {
				button.appendChild(stendhal.data.sprites.get(stendhal.paths.sprites + "/emoji/" + emoji + ".png").cloneNode());
			} else {
				button.innerText = emoji;
			}
			button.addEventListener("click", (evt) => {
				this.onButtonPressed(emoji);
			});
			row.appendChild(button);
			idx++;
		}
	}

	private onButtonPressed(emoji: string) {
		if (!this.sysEmojis) {
			const action = {
				"type": "chat",
				"text": ":" + emoji + ":"
			} as any;
			marauroa.clientFramework.sendAction(action);
			this.close();
			return;
		}

		// FIXME: should insert text at caret position
		singletons.getChatInput().appendText(emoji);
	}
}
