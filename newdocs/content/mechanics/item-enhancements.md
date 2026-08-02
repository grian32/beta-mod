---
title: Item Enhancements
type: mechanics
description: A tiered alternative to enchantments that grants tools specialized effects.
categories:
  - Mechanics
  - Item Enhancements
---

**Item Enhancements** are Project Beta Expanded's take on the enchantments added in later versions. This is one of the mod's largest additions, with many surrounding items and blocks that have various uses.

Tools can be enhanced with specialized effects at an [Enhancement Table]({{< relref "/blocks/enhancement-table.md" >}}), costing a set amount of a particular ingredient—usually a block or item.

Unlike enchantments, a tool can only carry one enhancement. Higher tiers of that enhancement must be applied by upgrading it at the Enhancement Table.

Item Enhancements also has support for AMI, so you can view all enhancement recipes in AMI

{{< gallery >}}
  {{< gallery-image src="img/enhancement_table_example.png" alt="Using an Enhancement Table" caption="Using the Enhancement Table to apply an enhancement." fit="natural" >}}
{{< /gallery >}}

## Enhancements

### Axes

> A naturally generated tree refers to a tree placed during world generation or grown from a sapling.

#### Extra Logs

Logs from naturally generated trees have a chance to be doubled. The chance increases from 20% to guaranteed based on the tier.

{{< enhancement id="extra_logs" >}}

#### Resin Harvest

Logs have a chance to drop [Resin]({{< relref "/items/resin.md" >}}).

{{< enhancement id="resin_harvest" >}}

#### Reinforced

Axes have a chance not to consume durability.

{{< enhancement id="reinforced" >}}

### Pickaxes

#### Steady Hand

All ores have a small chance to be preserved. A sound effect indicates when this occurs.

{{< enhancement id="steady_hand" >}}

#### Quarryman

Stone-based blocks are mined faster.

Affected blocks: Stone, Cobblestone, Mossy Cobblestone, Icy Stone, and Icy Cobblestone.

{{< enhancement id="quarryman" >}}

### Shovels

#### Landscaper

Certain terrain blocks drop in their intact form.

Affected blocks: Grass Blocks, Clay Blocks, Snow Blocks, Gravel, and Scorched Clay Blocks.

{{< enhancement id="landscaper" >}}

#### Sifter

Sand touching water has a chance to drop two to four Clay Balls instead of Sand. Soul Sand touching lava likewise has a chance to drop two to four Scorched Clay Balls instead.

{{< enhancement id="sifter" >}}

### Hoes

> Both hoe enhancements consume durability, unlike normal block breaking with a hoe.

#### Replanter

Fully grown crops replant themselves at no cost a few ticks after harvesting. Green particles indicate when this occurs.

{{< enhancement id="replanter" >}}

#### Bountiful

Fully grown crops have a chance to drop one or two additional produce.

{{< enhancement id="bountiful" >}}
