package Scripts;

import java.util.Random;

public class Potwor extends Postac {
    String klasa;
    public Potwor(String klasa, String imie, int zycie, int atak, int maxHP) {
        super(imie, zycie, atak, maxHP);
        this.klasa = klasa;
    }

    public static Potwor losowyPotwor() {
        String[] klasa = {"Goblin", "Wilkołak", "Szkielet", "Smok", "Ork", "Czarnoksiężnik", "Olbrzym"};
        String[] imiona = {"Achilles", "Aeneas", "Aeson", "Agamemnon", "Ajax", "Alcides", "Antaeus", "Apollo", "Ares", "Argo",
                "Aristides", "Asclepius", "Atlas", "Autolycus", "Bellerophon", "Cadmus", "Castor", "Chiron", "Cronos", "Daedalus",
                "Dionysus", "Erebus", "Eros", "Ganymede", "Hades", "Hector", "Helios", "Hephaestus", "Heracles", "Hermes",
                "Hippolytus", "Hypnos", "Iapetus", "Icarus", "Jason", "Kratos", "Laius", "Linus", "Minos", "Morpheus",
                "Orion", "Orpheus", "Pan", "Paris", "Patroclus", "Perseus", "Phaethon", "Philoctetes", "Pollux", "Pontus",
                "Poseidon", "Priam", "Prometheus", "Tantalus", "Telemachus", "Theseus", "Tiresias", "Triton", "Zeus", "Zethus",
                "Odin", "Thor", "Loki", "Baldur", "Tyr", "Fenrir", "Jormungandr", "Freyr", "Freki", "Geri",
                "Vidar", "Vali", "Bragi", "Hodr", "Skirnir", "Njord", "Mimir", "Ymir", "Heimdall", "Surtur",
                "Sleipnir", "Buri", "Vé", "Vili", "Sigurd", "Fafnir", "Ragnar", "Bjorn", "Erik", "Leif",
                "Ra", "Anubis", "Horus", "Osiris", "Set", "Thoth", "Khonsu", "Sobek", "Ptah", "Imhotep",
                "Apophis", "Bastet", "Amenhotep", "Ramesses", "Tutankhamun", "Khufu", "Akhenaten", "Menes", "Narmer", "Djoser",
                "Enkidu", "Gilgamesz", "Marduk", "Ninurta", "Tammuz", "Dumuzi", "Ashur", "Adapa", "Lugalbanda", "Sargon",
                "Arthur", "Lancelot", "Gawain", "Galahad", "Percival", "Tristan", "Mordred", "Uther", "Merlin", "Kay",
                "Bedivere", "Pellinore", "Agravaine", "Bors", "Dagonet", "Owain", "Palamedes", "Ector", "Lot", "Gareth",
                "Cú Chulainn", "Fionn", "Lugh", "Diarmuid", "Nuada", "Ogma", "Taranis", "Esus", "Belenus", "Bran",
                "Veles", "Perun", "Svarog", "Triglav", "Dazbog", "Stribog", "Simargl", "Chors", "Mokosh", "Rod",
                "Shiva", "Vishnu", "Rama", "Krishna", "Indra", "Agni", "Hanuman", "Ganesha", "Karna", "Arjuna",
                "Bhima", "Yudhishthira", "Duryodhana", "Ashwatthama", "Bheeshma", "Parashurama", "Lakshmana", "Sugriva", "Vali", "Ravana",
                "Jesus", "Moses", "Noah", "Abraham", "Isaac", "Jacob", "Joseph", "David", "Solomon", "Samuel",
                "Saul", "Jonah", "Elijah", "Ezekiel", "Daniel", "Job", "Joshua", "Gideon", "Caleb", "Nathan",
                "Maui", "Tangaroa", "Tūmatauenga", "Rongo", "Tāwhirimātea", "Rūaumoko", "Whiro", "Tāne", "Tiki", "Makutu",
                "Beowulf", "Grendel", "Hrothgar", "Wiglaf", "Sigemund", "Unferth", "Scyld", "Heorogar", "Onela", "Eadgils",
                "Geralt", "Vesemir", "Eskel", "Lambert", "Dandelion", "Emhyr", "Cahir", "Avallac'h", "Istredd", "Rience",
                "Guts", "Griffith", "Skull Knight", "Zodd", "Ganishka", "Mozgus", "Serpico", "Isidro", "Puck", "Void",
                "Aragorn", "Boromir", "Faramir", "Gandalf", "Legolas", "Gimli", "Thranduil", "Elrond", "Bilbo", "Frodo",
                "Sauron", "Saruman", "Théoden", "Denethor", "Isildur", "Anárion", "Glorfindel", "Haldir", "Eärendil", "Finrod",
                "Ezio", "Altair", "Connor", "Bayek", "Kassandra", "Eivor", "Arno", "Shay", "Desmond", "Basim",
                "Dovahkiin", "Alduin", "Ulfric", "Tiber", "Sotha Sil", "Vivec", "Dagoth Ur", "Orgnar", "Harkon", "Neloth",
                "Link", "Ganondorf", "Zant", "Daruk", "Revali", "Sidon", "Rauru", "Demise", "Ghirahim", "King Rhoam",
                "Cloud", "Sephiroth", "Zack", "Barret", "Cid", "Vincent", "Red XIII", "Reno", "Rude", "Hojo",
                "Solid Snake", "Big Boss", "Ocelot", "Raiden", "Otacon", "Volgin", "Skull Face", "Kaz", "Huey", "Liquid",
                "Dante", "Vergil", "Nero", "Sparda", "Mundus", "Arkham", "Agni", "Rudra", "Gilgamesh", "Berial",
                "Ryu", "Ken", "Akuma", "Sagat", "Bison", "Blanka", "Dhalsim", "Guile", "Cody", "Zangief",
                "Sub-Zero", "Scorpion", "Raiden", "Liu Kang", "Kenshi", "Kung Lao", "Shao Kahn", "Reptile", "Smoke", "Noob Saibot"};
        int index = new Random().nextInt(imiona.length);
        int index2 = new Random().nextInt(klasa.length);
        int zycie = 40 + new Random().nextInt(60);
        int maxHP = zycie;
        int atak = 10 + new Random().nextInt(20);
        return new Potwor(klasa[index2], imiona[index], zycie, atak, maxHP);
    }
}