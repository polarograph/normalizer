# Normal1.8zer

![icon.png](src/main/resources/assets/normalizer/icon.png)

## Based on NoChangeTheGame by Cecer, but minified & up to date
Compatibility tool for 1.8 servers that support joining on the latest versions of Minecraft (such as Hypixel).

Hypixel has recently added sword blocking on vanilla 1.21.4+, removing the need for a lot of changes in NoChangeTheGame.

To reflect on that change, I've created this mod which uses much of the same code as NoChangeTheGame, but with a heavy reliance on Animatium (https://modrinth.com/mod/animatium) for the rest of the features.

Big thanks to Cecer for making the original mod, & big thanks to the Animatium team for their work. This mod should work on any 1.8-based server which added 1.21.4 support & sword blocking, but **this mod is primarily made for use on Hypixel**

## What does this mod modify?
This mod modifies:
- Swimming (disabled)
- Crawling (disabled)
- Sneaking (Hitbox reverted back to <=1.8 & MC-159163 fix)
- Sprinting (Sprint Resetting readded on 1.21.5+)
- Buckets (reverted back to <=1.12.2)
- Beds (no bounce)

The following changes make the general experience much better & less buggy, allowing for smoother gameplay on the latest versions

You can access the mod's config via `/normalizerconfig` or ModMenu if you have it installed.