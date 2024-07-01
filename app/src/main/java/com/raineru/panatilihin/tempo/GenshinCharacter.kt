package com.raineru.panatilihin.tempo

data class GenshinCharacter(
    val name: String,
    val element: String,
    val weapon: String,
    val rarity: Int
)

val sampleCharacters = listOf(
    GenshinCharacter("Diluc", "Pyro", "Claymore", 5),
    GenshinCharacter("Barbara", "Hydro", "Catalyst", 4),
    GenshinCharacter("Venti", "Anemo", "Bow", 5),
    GenshinCharacter("Keqing", "Electro", "Sword", 5),
    GenshinCharacter("Mona", "Hydro", "Catalyst", 5),
    GenshinCharacter("Qiqi", "Cryo", "Sword", 5),
    GenshinCharacter("Jean", "Anemo", "Sword", 5),
    GenshinCharacter("Klee", "Pyro", "Catalyst",5),
    GenshinCharacter("Tartaglia", "Hydro", "Bow", 5),
    GenshinCharacter("Zhongli", "Geo", "Polearm", 5),
    GenshinCharacter("Albedo", "Geo", "Sword", 5),
    GenshinCharacter("Ganyu", "Cryo", "Bow", 5),
    GenshinCharacter("Xiao", "Anemo", "Polearm", 5),
    GenshinCharacter("Hu Tao", "Pyro", "Polearm", 5),
    GenshinCharacter("Eula", "Cryo", "Claymore", 5),
    GenshinCharacter("Kazuha", "Anemo", "Sword", 5),
    GenshinCharacter("Ayaka", "Cryo", "Sword", 5),
    GenshinCharacter("Yoimiya", "Pyro", "Bow", 5),
    GenshinCharacter("Raiden Shogun", "Electro", "Polearm", 5),
    GenshinCharacter("Kokomi", "Hydro", "Catalyst", 5),
    GenshinCharacter("Itto", "Geo", "Claymore", 5),
    GenshinCharacter("Shenhe", "Cryo", "Polearm", 5),
    GenshinCharacter("Yae Miko", "Electro", "Catalyst", 5),
    GenshinCharacter("Kamisato Ayato", "Hydro", "Sword", 5),
    GenshinCharacter("Yelan", "Hydro", "Bow", 5),
    GenshinCharacter("Tighnari", "Dendro", "Bow", 5),
    GenshinCharacter("Cyno", "Electro", "Polearm", 5),
    GenshinCharacter("Nilou", "Hydro", "Sword", 5),
    GenshinCharacter("Nahida", "Dendro", "Catalyst", 5),
    GenshinCharacter("Wanderer", "Anemo", "Catalyst", 5),
    GenshinCharacter("Alhaitham", "Dendro", "Sword", 5),
    GenshinCharacter("Yaoyao", "Dendro", "Polearm", 4),
    GenshinCharacter("Dehya", "Pyro", "Claymore", 5),
    GenshinCharacter("Mika", "Cryo", "Polearm", 4),
    // ... Add even more characters as you like!
)