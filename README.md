# Surf

Surf is a fork of [LeeesExploitFixer](https://github.com/XeraPlugins/LeeesExploitFixer-3.0) of a fork of
254nm's [L2X9Core](https://github.com/254nm/L2X9Core)

A plugin for anarchy servers that aim to fix exploits, detect and remove illegal/NBT items.

Recommended use as a addition with AnarchyExploitFixes and Panilla to become 100% functional.

## Compatibility

- Support Java 21 and higher
- Support 1.12.2 ~ Latest Minecraft version (1.21.4)
- Compatible with Paper / Paper Forks
- Folia Support

___

## Features

* Prevent all crash exploits that I know of
* Prevent ChunkBan
* Patch BookBan without disabling shulker peek
* Prevent EndPortal greifing
* Patch players using the OffHand crash module in certan hacked clients to crash the server with books
* Prevent players from using illegal items
* Remove illegal potion effects from players
* Remove falling block server crashers
* Active development

## 📫 Contact

- Discord: [`https://discord.gg/pUYdgX8cQM`](https://discord.gg/pUYdgX8cQM)
- QQ: `2682173972`

## TODOS

prefix
modules
    ChunkBan
    NBTBAN
Refactor notes;
Move checks in item util into checks package
split to general and various checks related to specific item.
in the main check method under each listener, add checks for specific item type
then in a for loop to loop all checks to snalitize the item.
Add add credit for panilla
