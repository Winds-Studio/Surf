//package org.surf.listeners.patches;
//
//import org.bukkit.Material;
//import org.bukkit.block.ShulkerBox;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryOpenEvent;
//import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.BlockStateMeta;
//import org.bukkit.inventory.meta.BookMeta;
//import org.surf.Main;
//import org.surf.util.SecondPassEvent;
//import org.surf.util.Utils;
//
//import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ChestLagFix implements Listener {
//    HashMap<Player, Integer> chestHashMap = new HashMap<>();
//    Main plugin;
//
//    public ChestLagFix(Main plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onInventoryOpen(InventoryOpenEvent event) {
//        try {
//            int maxSpam = plugin.getConfig().getInt("ChestLagFix.MaxOpensPerSecond");
//            String kickMessage = plugin.getConfig().getString("ChestLagFix.KickMessage");
//            boolean deleteBooks = plugin.getConfig().getBoolean("ChestLagFix.RemoveUnicodeBooks");
//            InventoryType inventoryType = event.getInventory().getType();
//            Player player = (Player) event.getPlayer();
//            if (isCheckedInventory(inventoryType)) {
//                if (chestHashMap.containsKey(player)) {
//                    chestHashMap.replace(player, chestHashMap.get(player) + 1);
//                } else {
//                    chestHashMap.put(player, 1);
//                }
//                if (deleteBooks) {
//                    deleteNBTBooks(event.getInventory());
//                }
//                if (chestHashMap.get(player) > maxSpam) {
//                    Utils.kickPlayer(player, kickMessage);
//                }
//            }
//        } catch (Error | Exception throwable) {
//
//        }
//    }
//
//    @EventHandler
//    public void onSecondPass(SecondPassEvent event) {
//        Utils.secondPass(chestHashMap);
//    }
//
//    public boolean isCheckedInventory(InventoryType type) {
//        switch (type) {
//            case CHEST:
//            case HOPPER:
//            case ENDER_CHEST:
//            case SHULKER_BOX:
//            case DISPENSER:
//            case DROPPER:
//                return true;
//        }
//        return false;
//    }
//
//    private void deleteNBTBooks(Inventory inventory) {
//        for (ItemStack item : inventory.getContents()) {
//            if (item != null) {
//                if (item.getType() == Material.WRITTEN_BOOK || item.getType() == Material.BOOK_AND_QUILL) {
//                    BookMeta bookMeta = (BookMeta) item.getItemMeta();
//                    if (isBanBook(bookMeta)) {
//                        inventory.remove(item);
//                        System.out.println("[ChestLagFix] Removed an NBT book from a chest");
//                    }
//                }
//                if (item.getItemMeta() instanceof BlockStateMeta) {
//                    BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
//                    if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
//                        ShulkerBox shulker = (ShulkerBox) blockStateMeta.getBlockState();
//                        for (ItemStack shulkerItem : shulker.getInventory().getContents()) {
//                            if (shulkerItem != null) {
//                                if (shulkerItem.getType() == Material.WRITTEN_BOOK || shulkerItem.getType() == Material.BOOK_AND_QUILL) {
//                                    BookMeta book = (BookMeta) shulkerItem.getItemMeta();
//                                    if (isBanBook(book)) {
//                                        shulker.getInventory().remove(shulkerItem);
//                                        System.out.println("[ChestLagFix] Removed an NBT book from a chest");
//                                    }
//                                }
//                            }
//                        }
//                        blockStateMeta.setBlockState(shulker);
//                        item.setItemMeta(blockStateMeta);
//                    }
//                }
//            }
//        }
//    }
//
//    private boolean isBanBook(BookMeta book) {
//        for (String bookPages : book.getPages()) {
//            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
//            Matcher matcher = pattern.matcher(bookPages);
//            return matcher.find();
//        }
//        return false;
//    }
//}
