CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.5.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ansprechpartner`
--

DROP TABLE IF EXISTS `ansprechpartner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ansprechpartner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  `handy` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `lieferant_fk` int(11) NOT NULL,
  `bezeichnung` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ansprechpartner_lieferant1_idx` (`lieferant_fk`),
  CONSTRAINT `fk_ansprechpartner_lieferant1` FOREIGN KEY (`lieferant_fk`) REFERENCES `lieferant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ansprechpartner`
--

LOCK TABLES `ansprechpartner` WRITE;
/*!40000 ALTER TABLE `ansprechpartner` DISABLE KEYS */;
INSERT INTO `ansprechpartner` VALUES (1,'Armin Maier','0152/22669748','','07240 616025','',1,NULL),(2,'Fasanenbrot-Fahrer','','12347654','','',17,NULL),(3,'Thomas Dzeyk','','0170-3800393','','',17,NULL),(4,'Doris priv.','5042108','','','',41,NULL),(5,'Lutz priv.','','0175-4169733','','',41,NULL),(6,'Ottmar Balder','','0172-1466797','','',36,NULL),(7,'Härtelt','','0170/4557716','','',22,NULL),(8,'Udo (Fahrer)','','01718352039','','',28,NULL),(9,'Marc-Oliver','0721-31032','','','pfauenstrasse@getrae',28,NULL),(10,'Annette Sych','','','','',47,'Inhaberin'),(11,'Patrik Teuber','07663-9394-29','','','',3,'Kundenberater');
/*!40000 ALTER TABLE `ansprechpartner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artikel`
--

DROP TABLE IF EXISTS `artikel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artikel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mengeneinheit_fk` int(11) NOT NULL,
  `lagerort_fk` int(11) NOT NULL,
  `lieferant_fk` int(11) NOT NULL,
  `kategorie_fk` int(11) NOT NULL,
  `artikelnr` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `bestellgroesse` double DEFAULT NULL,
  `preis` float DEFAULT NULL,
  `standard` tinyint(1) NOT NULL,
  `grundbedarf` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'bei 0 ist aktiv, bei 1 ist deaktiviert',
  `fuerRezepte` tinyint(1) NOT NULL DEFAULT '1',
  `durchschnittLT1` int(11) DEFAULT NULL,
  `durchschnittLT2` int(11) DEFAULT NULL,
  `notiz` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mengeeinheit_FK_idx` (`mengeneinheit_fk`),
  KEY `kategorie_FK_idx` (`kategorie_fk`),
  KEY `lagerort_FK_idx` (`lagerort_fk`),
  KEY `lief_idx` (`lieferant_fk`),
  CONSTRAINT `kategorie_FK` FOREIGN KEY (`kategorie_fk`) REFERENCES `kategorie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lagerort_FK` FOREIGN KEY (`lagerort_fk`) REFERENCES `lagerort` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lieferant` FOREIGN KEY (`lieferant_fk`) REFERENCES `lieferant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mengeeinheit_FK` FOREIGN KEY (`mengeneinheit_fk`) REFERENCES `mengeneinheit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=800 DEFAULT CHARSET=dec8 COMMENT='geändert von Thomas';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikel`
--

LOCK TABLES `artikel` WRITE;
/*!40000 ALTER TABLE `artikel` DISABLE KEYS */;
INSERT INTO `artikel` VALUES (1,3,3,1,1,'436427','Pecorino Romano 49 %',200,3.199,0,0,0,1,0,NULL,'\r'),(2,3,3,1,1,'43281','Gorgonzola Intenso 48 %',1500,9.619,0,0,0,1,0,NULL,'\r'),(3,3,3,1,1,'50661','Roquefort Societe 1/4 Laib',660,41.411,0,0,0,1,0,NULL,'\r'),(4,3,3,1,1,'239990','Ziegenrolle Soignon 45 %',1000,14.489,0,0,0,1,0,NULL,'\r'),(5,3,3,1,1,'556309','Ziegenfrischkäse Taler',200,16.469,1,1,0,1,0,NULL,'\r'),(6,3,3,1,1,NULL,'Ziegen-Camembert-Rolle',200,0,0,0,0,1,0,NULL,'\r'),(7,3,3,1,1,'438170','Appenzeller',1800,0,1,1,0,1,0,NULL,'\r'),(8,3,3,1,1,'37659','Raclette Frz.45 % 1/4',1750,9.726,0,0,0,1,0,NULL,'\r'),(9,3,3,1,1,'939853','Ricotta  ',1500,45.778,1,1,0,1,0,NULL,'\r'),(10,3,3,1,1,'78605','Mascarpone',2000,36.342,0,0,0,1,0,NULL,'\r'),(11,3,3,1,1,'21665','Frischkaese Kraeuter',200,0.68,0,0,0,1,0,NULL,'\r'),(12,3,3,1,1,NULL,'Hüttenkäse',1500,0,0,0,0,1,0,NULL,'\r'),(13,3,3,1,1,'410361','Halloumi Griech Grillkaese 43 %',250,2.664,0,0,0,1,0,NULL,'\r'),(14,3,3,1,1,NULL,'Cheddar',0,0,0,0,0,1,0,NULL,'\r'),(15,3,3,1,1,NULL,'Gryere',0,0,0,0,0,1,0,NULL,'\r'),(16,3,3,1,1,'546606','Buttermilch',500,0.29,0,0,0,1,0,NULL,'\r'),(17,3,3,1,1,'56149','Sahne Sauer 10 % G&G',200,0.369,0,0,0,1,0,NULL,'\r'),(18,3,3,1,59,'437024','Schinken Serrano Gesch. Kuehn',500,18.172,0,0,0,1,0,NULL,'\r'),(19,3,3,1,29,'936804','Nudelteig Rolle',1000,47.178,1,1,0,1,0,NULL,'\r'),(20,3,3,1,29,'523199','Strudelteig',300,2.236,1,1,0,1,0,NULL,'\r'),(21,3,1,1,5,'431953','Blaetterteig TK',3000,9.298,1,0,1,1,0,NULL,'\r'),(22,10,1,1,4,NULL,'Blaetterteig Rollen Frisch ',1,0,0,0,1,1,0,NULL,'\r'),(23,3,3,1,29,NULL,'Schupfnudeln Burger',2500,0,0,0,0,1,0,NULL,'\r'),(24,3,5,1,9,'14298','Seelachsfil.Nat.Tk140/160G',5000,31.554,0,0,0,1,0,NULL,'\r'),(25,3,5,1,9,'41411','Zanderfilet Natur Tk',5000,85.589,0,0,0,1,0,NULL,'\r'),(26,3,5,1,9,'59048','Lachsfilet Seiten',1000,15.75,1,1,0,1,0,NULL,'\r'),(27,3,5,1,9,'65112','Rotbarschfilet Natur Tk',1000,31.019,0,0,0,1,0,NULL,'10 Beutel'),(28,3,5,1,9,'443205','Steinbeisserf. Natur 180/220 G',5000,41.463,0,0,0,1,0,NULL,'\r'),(29,3,5,1,9,'443188','Kabeljau Rückenfilet',5000,47.8,1,1,0,1,0,NULL,'\r'),(30,3,5,1,9,'442713','Kabeljaufilet 160/180G 5Kg Karton',5000,0,0,0,0,1,0,NULL,'\r'),(31,3,5,1,9,'442638','Seelachs Rückenfilet 160/180G',5000,34.8,0,0,0,1,0,NULL,'\r'),(32,3,5,1,9,'432606','Frutti Di Mare Royal Tk Escal',1000,8.335,0,0,0,1,0,NULL,'\r'),(33,3,3,1,9,'557001','Garnelen ohne Darm',1000,0,1,1,0,1,0,NULL,'\r'),(34,3,3,1,9,'56565','Forellenfil.M.Haut Geraeuch.',130,2.557,0,0,0,1,0,NULL,'\r'),(35,6,4,1,9,'537460','Sardellenfil.In Oel',156,2.878,0,0,0,1,0,NULL,'\r'),(36,3,5,1,33,'579498','Markerbsen TK',2500,14.305,1,1,0,1,0,NULL,'\r'),(37,3,5,1,33,'441112','Kaiserschoten 2.5 Kg Tk Westfro',2500,41.498,0,0,0,1,0,NULL,'\r'),(38,3,5,1,33,'941167','Delikatessbohnen TK',2500,2.985,0,0,0,1,0,NULL,'\r'),(39,3,5,1,33,'577895','Blattspinat TK',2500,25.235,0,0,0,1,0,NULL,'\r'),(40,3,5,1,33,'443696','Rosenkohl 15/25Mm Tk Westfro',2500,3.199,0,0,0,1,0,NULL,'\r'),(41,3,4,1,19,'35535','Zuckererbsenschoten Bonduel',1000,21.702,0,0,0,1,0,NULL,'\r'),(42,3,5,1,33,'20715','Steinpilze Halbiert Tk Walhalla',1000,18.179,0,0,0,1,0,NULL,'\r'),(43,3,5,1,29,'50696','Tortelli Frischkaese Tk Hilcona',2000,17.109,0,0,0,1,0,NULL,'\r'),(44,3,5,1,34,'36366','Heidelbeeren Tk Golden Crown',2500,12.829,0,0,0,1,0,NULL,'\r'),(45,3,4,1,29,'560032','Toast Butter',500,0.801,1,1,0,1,0,NULL,'\r'),(46,3,4,1,13,'113388','Mandeln Ganz Backstube',200,13.881,0,0,0,1,0,NULL,'\r'),(47,3,4,1,13,'171285','Mandeln Gestiftelt Insula',1000,8.549,0,0,0,1,0,NULL,'\r'),(48,3,4,1,19,'449728','Kuerbiskerne Gruen Meienb.',500,18.019,0,0,0,1,0,NULL,'\r'),(49,3,4,1,19,'947539','Cashewkerne',500,45.839,1,1,0,1,0,NULL,'\r'),(50,3,4,1,19,'21971','Haselnusskerne Gehobelt Backst',100,1.069,0,0,0,1,0,NULL,'\r'),(51,3,4,1,19,'696215','Walnusskerne',500,0,1,1,0,1,0,NULL,'\r'),(52,3,4,1,19,'671339','Pinienkerne',500,18.179,1,1,0,1,0,NULL,'\r'),(53,3,4,1,19,'196222','Pistazien Gehackt Insula',500,14.22,0,0,0,1,0,NULL,'\r'),(54,3,4,1,61,'30408','Sesamsaat Bio Wertkost',250,1.069,0,0,0,1,0,NULL,'\r'),(55,3,4,1,19,'449727','Sonnenblumenkerne Meienburg',1000,43.525,0,0,0,1,0,NULL,'\r'),(56,3,4,1,13,'518107','Kokosflocken',500,1.915,0,0,0,1,0,NULL,'\r'),(57,3,4,1,19,NULL,'Erdnüsse Gesalzen',200,0,0,0,0,1,0,NULL,'\r'),(58,3,4,1,19,'519363','Rosinen',500,1.915,0,0,0,1,0,NULL,'\r'),(59,3,4,1,15,'18153','Aprikosen getrocknet',200,21.551,0,0,0,1,0,NULL,'\r'),(60,3,4,1,19,'16440','Pflaumen Getrock.Entst. Backst.',250,1.069,0,0,0,1,0,NULL,'\r'),(61,3,4,1,19,'446163','Datteln Entsteint Meienburg',500,12.844,0,0,0,1,0,NULL,'\r'),(62,3,4,1,19,'446279','Feigen Getrocknet',500,0,0,0,0,1,0,NULL,'\r'),(63,3,4,1,19,'446148','Cranberries Getrocknet',400,14.336,0,0,0,1,0,NULL,'\r'),(64,3,4,1,33,'47057','Maronen Vorgeg. Fr',400,3.627,0,0,0,1,0,NULL,'\r'),(65,3,4,1,19,'32125','Steinpilze Getrocknet',500,0,0,0,0,1,0,NULL,'\r'),(66,3,4,1,19,'32129','Shitake Getrocknet',500,0,0,0,0,1,0,NULL,'\r'),(67,3,1,1,12,'53353','Tomaten Getrock.I.Oel Casa Ri.',2850,19.249,0,0,1,1,0,NULL,'\r'),(68,3,4,1,19,'44058','Tomaten Getrocknet Niklas',1000,41.338,0,0,0,1,0,NULL,'\r'),(69,3,4,1,13,'109242','Puderzucker',10000,0,0,0,0,1,0,NULL,'\r'),(70,3,4,1,13,'528926','Backpulver',1000,2.129,1,1,0,1,0,NULL,'\r'),(71,3,4,1,13,'438019','Pudding Vanille Z.K. Ruf',1000,3.948,0,0,0,1,0,NULL,'\r'),(72,3,4,1,13,'39503','Sahnesteif Oetker',1000,6.195,0,0,0,1,0,NULL,'\r'),(73,3,4,1,13,'437876','Tortenguss Klar Ruf',1000,0,0,0,0,1,0,NULL,'\r'),(74,3,4,1,13,'403191','Tortenguss Rot Ruf',1000,0,0,0,0,1,0,NULL,'\r'),(75,3,4,1,13,'15309','Nuss Nougat Backstube',200,1.808,0,0,0,1,0,NULL,'\r'),(76,3,4,1,13,'83210','Marzipan Rohmasse Backstube',200,1.487,0,0,0,1,0,NULL,'\r'),(77,3,4,1,13,'50043','Schokoladenblaettchen Insula',1000,6.623,0,0,0,1,0,NULL,'\r'),(78,3,4,1,13,'850620','Kakaopulver',1000,6.944,0,0,0,1,0,NULL,'\r'),(79,3,4,1,13,'550146','Kuvertüre weiß',200,0.962,1,1,0,1,0,NULL,'\r'),(80,3,4,1,13,NULL,'Blockschokolade',200,0,0,0,0,1,0,NULL,'\r'),(81,3,1,1,13,'164775','Blaumohn Insula',500,2.129,0,0,1,1,0,NULL,'\r'),(82,3,4,1,13,'93590','Kirschen Beleg Rot Ganz Insula',1000,9.619,0,0,0,1,0,NULL,'\r'),(83,3,4,1,13,'146487','Orangeat Backstube',100,0.555,0,0,0,1,0,NULL,'\r'),(84,1,4,1,13,NULL,'Schokopudding Pulver',3,0,0,0,0,1,0,NULL,'\r'),(85,6,4,1,15,'78027','Mandarin Orangen Topkauf',2650,5.125,0,0,0,1,0,NULL,'\r'),(86,3,4,1,15,'177467','Ananas Scheiben',800,0,0,0,0,1,0,NULL,'\r'),(87,3,4,1,13,'169947','Gelee Johannisb.Rot Extra',450,2.129,0,0,0,1,0,NULL,'\r'),(88,3,4,1,13,'546119','Aprikose Konfitüre',450,0.99,1,1,0,1,0,NULL,'\r'),(89,3,4,1,15,'27467','Erdnusscreme Creamy Ueltje',350,3.092,0,0,0,1,0,NULL,'\r'),(90,3,4,1,13,'27451','Schokol.Weisse Milka',100,0.855,0,0,0,1,0,NULL,'\r'),(91,3,4,1,15,'871054','Loeffelbiskuits',500,1.915,0,0,0,1,0,NULL,'\r'),(92,3,4,1,15,'565001','Preiselbeeren',400,1.166,1,1,0,1,0,NULL,'\r'),(93,6,4,1,15,'87922','Sauce Vanille Dessert Lukull',1000,36.22,0,0,0,1,0,NULL,'\r'),(94,3,4,1,15,'450039','Meringen Tropfen',125,0,0,0,0,1,0,NULL,'\r'),(95,1,6,1,15,'48216','Süssstoffspender',1200,0,0,0,0,1,0,NULL,'\r'),(96,3,4,1,15,'522653','Honig In Quetschflasche',500,0,0,0,0,1,0,NULL,'\r'),(97,3,4,1,13,NULL,'Feigenmarmelade',400,0,0,0,0,1,0,NULL,'\r'),(98,3,4,1,15,NULL,'Orangenmarmelade Günstig',400,0,0,0,0,1,0,NULL,'\r'),(99,3,4,1,15,'948213','Rohrzucker braun',1000,0,0,0,0,1,0,NULL,'\r'),(100,3,4,1,29,'59458','Reis Parboiled Langkorn Topk:',5000,16.558,0,0,0,1,0,NULL,'\r'),(101,3,4,1,29,'92825','Reis Risotto Arborio Riso',1000,19.054,0,0,0,1,0,NULL,'\r'),(102,3,4,1,29,'449174','Reis Vollkorn Und Wild Ahama',5000,16.039,0,0,0,1,0,NULL,'\r'),(103,3,4,1,29,NULL,'Reis Rundkorn',500,0,0,0,0,1,0,NULL,'\r'),(104,3,4,1,29,'16845','Lasagne Gelb Barilla',500,25.204,0,0,0,1,0,NULL,'\r'),(105,3,4,1,29,'16500','Maccheroni Barilla Kurz',500,1.487,0,0,0,1,0,NULL,'\r'),(106,3,4,1,29,'518056','Fussili',5000,25.416,0,0,0,1,0,NULL,'\r'),(107,3,4,1,29,NULL,'Oricchiette Barilla',500,0,0,0,0,1,0,NULL,'\r'),(108,3,4,1,29,'559991','Glasnudeln Lang',500,2.129,1,1,0,1,0,NULL,'\r'),(109,3,4,1,18,'51285','Haferflocken Günstig Zart',500,0,0,0,0,1,0,NULL,'\r'),(110,3,4,1,29,'449704','Couscous',5000,36.342,0,0,0,1,0,NULL,'\r'),(111,3,4,1,18,'609761','Griess Weichweizen',500,0.748,1,1,0,1,0,NULL,'\r'),(112,3,4,1,18,'109631','Griess Hartweizen Goldpuder',500,0.79,0,0,0,1,0,NULL,'\r'),(113,3,4,1,18,'449691','Hirse Ganz Friessinger',2500,21.641,0,0,0,1,0,NULL,'\r'),(114,3,4,1,18,NULL,'Graupen',500,0,0,0,0,1,0,NULL,'\r'),(115,3,4,1,29,'48910','Püree Flocken Pfanni',4000,41.53,0,0,0,1,0,NULL,'\r'),(116,3,4,1,19,'48880','Linsen Rot Geschaelt Ahama',5000,11.759,0,0,0,1,0,NULL,'\r'),(117,3,4,1,19,'668418','Linsen',500,1.166,0,0,0,1,0,NULL,'\r'),(118,3,4,1,19,NULL,'Belugalinsen',1000,0,0,0,0,1,0,NULL,'\r'),(119,3,4,1,19,'123279','Kichererbsen Ahama',5000,12.829,0,0,0,1,0,NULL,'\r'),(120,3,4,1,33,'447412','Hellriegel Franz. Berglinsen Dunkel',500,28.946,0,0,0,1,0,NULL,'\r'),(121,3,4,1,19,'550627','Kichererbsen Dose',425,0.59,1,1,0,1,0,NULL,'\r'),(122,3,4,1,19,'111368','Bohnen Weiss+Suppengruen',850,0.748,0,0,0,1,0,NULL,'\r'),(123,3,4,1,19,NULL,'Kidneybohnen',800,0,0,0,0,1,0,NULL,'\r'),(124,3,4,1,19,'611375','Linsen Mit Suppengruen',800,0.748,1,1,0,1,0,NULL,'\r'),(125,3,4,1,19,'147756','Bohnen Weisse Bonduelle',425,0.79,0,0,0,1,0,NULL,'\r'),(126,3,4,1,19,NULL,'Weisse Bohnen Trockenware',1000,0,0,0,0,1,0,NULL,'\r'),(127,6,4,1,33,'436996','Rotkohl Rotessa Hengstenberg',4250,6.302,0,0,0,1,0,NULL,'\r'),(128,3,4,1,33,NULL,'Sauerkraut',800,0,0,0,0,1,0,NULL,'\r'),(129,3,4,1,33,NULL,'Rote Bete Gegart',500,0,0,0,0,1,0,NULL,'\r'),(130,6,4,1,19,'59077','Tomaten Geschaelt Adria',2650,36.161,0,0,0,1,0,NULL,'\r'),(131,3,4,1,21,'156262','Grobes Meersalz Beutel',5000,0,0,0,0,1,0,NULL,'\r'),(132,3,4,1,21,NULL,'Knoblauchpfeffer Ubena?',470,0,0,0,0,1,0,NULL,'\r'),(133,6,4,1,21,'163253','Pfefferkoerner Schwarz Wib.',1200,15.55,0,0,0,1,0,NULL,'\r'),(134,3,4,1,21,'163260','Pfeffer Schwarz Wiberg',1200,15.65,0,0,0,1,0,NULL,'\r'),(135,3,4,1,21,'570162','Koriander gemahlen',470,16.469,1,1,0,1,0,NULL,'\r'),(136,3,4,1,21,'650097','Zimt Gemahlen',1200,25.447,1,1,0,1,0,NULL,'\r'),(137,3,4,1,21,'181961','Paprika Scharf Ubena',500,36.281,0,0,0,1,0,NULL,'\r'),(138,3,4,1,21,'569853','Kurkuma',470,20.18,1,1,0,1,0,NULL,'\r'),(139,6,4,1,21,'944325','Chili Sauce Chicken K+K Diamond',1000,41.519,0,0,0,1,0,NULL,'\r'),(140,3,3,1,21,'48596','Harissa Pfefferschoten Paste',140,1.487,0,0,0,1,0,NULL,'\r'),(141,6,4,1,21,'35209','Ketchup Tom. Squeezeflasche',875,3.199,0,0,0,1,0,NULL,'\r'),(142,3,4,1,21,'476454','Wasabi Paste Tube',45,0,0,0,0,1,0,NULL,'\r'),(143,1,4,1,21,'527678','Tabasco Kleine Flasche',10,0,0,0,0,1,0,NULL,'\r'),(144,6,4,1,21,'558078','Kapern',720,0,1,1,0,1,0,NULL,'\r'),(145,6,4,1,30,NULL,'Kürbiskernöl',300,0,0,0,0,1,0,NULL,'\r'),(146,5,1,1,22,'116097','Eis Chocolat Chips Moevenpik',6,41.509,0,0,1,1,0,NULL,'\r'),(147,5,1,1,22,'116097','Eis Citronen Sorbet Moevenpik',6,41.509,0,0,1,1,0,NULL,'\r'),(148,5,1,1,22,'116097','Eis Maple Walnuts Moevenpik',6,41.39,0,0,1,1,0,NULL,'\r'),(149,5,1,1,22,'116097','Eis Quark Sauerkirsch Schoeller',6,41.377,0,0,1,1,0,NULL,'\r'),(150,6,1,1,23,NULL,'Grappa Villa Mazzolini 40%',700,0,0,0,0,1,0,NULL,'\r'),(151,6,4,1,23,NULL,'Pott Rum 40 %',700,0,0,0,0,1,0,NULL,'\r'),(152,6,1,1,23,NULL,'Cognac Delaitre V.S. 40%',700,0,0,0,0,1,0,NULL,'\r'),(153,6,1,1,23,'510410','Calvados',700,0,0,0,0,1,0,NULL,'\r'),(154,6,1,1,23,'527436','Cidre Apfel Herb 4% Rheinberg',750,43.466,0,0,0,1,0,NULL,'\r'),(155,6,4,1,23,'112521','Sherry Fino Dry 15 %',750,2.878,0,0,0,1,0,NULL,'\r'),(156,6,4,1,23,'155139','Marsala Fine I.P.Tro.17 % K1/2',700,24.228,0,0,0,1,0,NULL,'\r'),(157,6,4,1,13,'532598','Kirschwasser',1000,10.689,1,1,0,1,0,NULL,'\r'),(158,6,1,1,23,'65793','Ricard Pastis 45 %',700,13.99,0,0,0,1,0,NULL,'\r'),(159,6,1,1,23,'35280','Baileys Irish Cream 17 %',700,18.203,0,0,0,1,0,NULL,'\r'),(160,6,1,1,23,'690477','Amaretto',700,19.054,1,1,0,1,9,0,'\r'),(161,6,1,1,23,NULL,'Ramazotti',700,0,0,0,0,1,0,NULL,'\r'),(162,6,1,1,23,'609013','Eierlikör',700,0,1,1,0,1,0,NULL,'\r'),(163,6,1,1,23,NULL,'Glühwein',1000,0,0,0,0,1,0,NULL,'\r'),(164,1,2,1,27,'30033','Handschuh Einweg Latex Large 100St',1,4.269,0,0,0,1,0,NULL,'\r'),(165,1,2,1,27,'30034','Handschuh Einweg Latex Medium100',1,4.269,0,0,0,1,0,NULL,'\r'),(166,1,2,1,27,'560315','Gummihandschuhe Grösse M (100St)',1,0,1,1,0,1,0,NULL,'\r'),(167,1,2,1,27,'941504','Gummihandschuhe Grösse S (100St)',1,0,1,1,0,1,0,NULL,'\r'),(168,1,2,1,13,'50884','Spritzbeutel Kochfest 40 Cm',2,7.479,0,0,0,1,0,NULL,'\r'),(169,1,2,1,27,NULL,'Fettlöser Sprühflsche',1,0,0,0,0,1,0,NULL,'\r'),(170,1,6,1,16,'472131','Pappteller 13X20 Cm Rpc',250,5.339,0,0,0,1,0,NULL,'\r'),(171,1,6,1,16,'441958','Pappteller Wurst 9X15Cm Papst',250,4.269,0,0,0,1,0,NULL,'\r'),(172,1,4,1,16,NULL,'Einweg-Styropor-Teller Mit Deckel',1,0,0,0,0,1,0,NULL,'\r'),(173,1,6,1,16,'568912','Trinkhalme',135,21.641,1,1,0,1,0,NULL,'\r'),(174,1,2,1,16,'51122','Willi Becher 50 Cl /-/0,4',6,0.738,0,0,0,1,0,NULL,'\r'),(175,1,4,1,16,NULL,'Ribo Becher To Go Mit Deckel',1,0,0,0,0,1,0,NULL,'\r'),(176,1,6,1,28,'442123','Kugelschreiber Noname',60,30.286,0,0,0,1,0,NULL,'\r'),(177,1,6,1,28,NULL,'Tesafilm Rollen',10,0,0,0,0,1,0,NULL,'\r'),(178,10,6,1,16,'52881','Ascher Klar Stapelbar',1,1.017,0,0,0,1,0,NULL,'\r'),(179,1,2,1,27,'453668','Feuchtwischmopp Tennessee',1,25.294,0,0,0,1,0,NULL,'\r'),(180,5,1,1,27,NULL,'Antikal  Badreiniger Sprayflasche',1,0,0,0,1,1,0,NULL,'\r'),(181,1,0,1,27,'534397','Backofen-/Grillreiniger',1,0,1,1,0,1,0,NULL,'\r'),(182,1,7,1,27,NULL,'Hygienebeutel',1,0,0,0,0,1,0,NULL,'\r'),(183,1,6,1,28,NULL,'Linierte Ringbucheinlage Din A4',500,0,0,0,0,1,0,NULL,'\r'),(184,1,6,1,28,NULL,'Kalenderbücher Dina 5  Täglich',1,0,0,0,0,1,0,NULL,'\r'),(185,1,2,1,16,NULL,'Topfhandschuhe Paar',2,0,0,0,0,1,0,NULL,'\r'),(186,1,8,1,28,NULL,'Kassenbericht Zweckform',1,0,0,0,0,1,0,NULL,'\r'),(187,1,6,1,28,NULL,'Din A 5 Blöcke Unliniert',1,0,0,0,0,1,0,NULL,'\r'),(188,1,6,1,16,'191234','Teelichter G&G',100,43.525,0,0,0,1,0,NULL,'\r'),(189,1,6,1,16,'441944','Kreidemarker Weiss 5Er Zieher',5,21.817,0,0,0,1,0,NULL,'\r'),(190,6,3,1,1,'24536','Sahne Konditor',5000,0.014,0,0,0,1,3,NULL,'\r'),(191,6,3,1,1,'45504','Sahne Schlag',1000,0.002,0,0,0,1,1,NULL,'12 Stück'),(192,3,3,1,1,'36456','Quark 40%',5000,0.012,0,0,0,1,3,NULL,'\r'),(193,3,3,1,1,'116417','Jogh.Natur 3,5%',5000,0.005,0,0,0,1,3,NULL,'\r'),(194,3,3,1,1,'49176','Butter mild gesäuert',10000,0.001,0,0,0,1,3,NULL,'40 Stück'),(195,3,3,1,1,'28016','Butterschmalz',2500,0.019,0,0,0,1,1,NULL,'4 Stück'),(196,3,3,1,1,'43540','Kräuterbutter',250,0.003,1,1,0,1,0,NULL,'4 Rollen'),(197,3,3,1,1,NULL,'Margarine',250,0,0,0,0,1,0,NULL,'\r'),(198,3,3,1,1,'436426','Emmentaler',2500,0.005,0,0,0,1,4,NULL,'\r'),(199,3,3,1,1,'436417','Tortenbrie',3000,0,0,0,0,1,1,NULL,'\r'),(200,3,3,1,1,'38623','Cambozola',2200,0.012,0,0,0,1,2,NULL,'\r'),(201,3,3,1,1,'64462','Schafskaese',1800,0.011,0,0,0,1,3,NULL,'\r'),(202,3,3,1,1,'589997','Mozzarella',1000,0.008,0,0,0,1,2,NULL,'\r'),(203,3,3,1,1,'21661','Frischkaese natur',200,0.001,0,0,0,1,1,NULL,'10 Stück'),(204,3,3,1,1,'535602','Parmesan gerieben',1000,0.009,0,0,0,1,2,NULL,'\r'),(205,3,3,1,1,'38435','Parmesan am Stück',1000,0.014,0,0,0,1,3,NULL,'\r'),(206,3,3,1,1,'910174','Creme Fraiche',500,0.002,1,1,0,1,3,4,'\r'),(207,3,3,1,29,'49313','Maultasch. Gemuese',1000,0.005,0,0,0,1,2,NULL,'6 Stück'),(208,3,3,1,9,'47848','Raeucherlachs',200,0.003,0,0,0,1,2,NULL,'10 Stück'),(209,3,3,1,15,'51008','Eszet Schnitten Vollmilch',75,0.001,1,1,0,1,0,NULL,'20 Pack'),(210,4,2,1,16,'230276','Backpapier Grossrolle Toppits',1,0.017,0,0,0,1,0,NULL,'\r'),(211,1,2,1,16,'524358','Frischhaltefolie 30x300 m',1,0.006,1,1,0,1,0,NULL,'\r'),(212,1,2,1,27,'60811','Topfreiniger Stark 4Er Scotch',1,0.003,0,0,0,1,0,NULL,'\r'),(213,1,2,1,27,'313881','Topfreiniger Edelstahlspirale 40G',1,0,0,0,0,1,0,NULL,'\r'),(214,1,2,1,27,'14406','Muellsack 120 l',10,0.002,1,1,0,1,0,NULL,'9 Rollen'),(215,1,2,1,27,'527513','Muellsack 60 l',30,0.001,1,1,0,1,0,NULL,'\r'),(216,1,2,1,27,'10482','Muellbeutel 35L 35Er G&G',35,0,0,0,0,1,0,NULL,'\r'),(217,1,2,1,27,'314778','Muellbeutel 20L Noname',35,0,0,0,0,1,0,NULL,'\r'),(218,1,2,1,16,'597653','Gefrierbeutel 6 l',1,0,1,1,0,1,0,NULL,'\r'),(219,1,2,1,16,'558406','Gefrierbeutel 3 l',1,0,1,1,0,1,0,NULL,'\r'),(220,1,2,1,27,'513921','Kuechentuecher 4 St',4,0.001,1,1,0,1,0,NULL,''),(221,1,2,1,13,'449628','Tortenspitzen Rund Weiß 37Cm',4,0.007,0,0,0,1,0,NULL,'\r'),(222,1,2,1,13,'14702','Tortenunterlage 30 Cm 100Er Papstar',4,0.011,0,0,0,1,0,NULL,'\r'),(223,1,2,1,16,'441068','Serv.Zt.33.1Lg.D-Blau1/4 500Er',1000,0.008,0,0,0,1,0,NULL,'\r'),(224,1,2,1,16,'441074','Dessertdeckch.17.Ws4000Er Duni',1,0.016,0,0,0,1,0,NULL,'\r'),(225,3,7,1,27,'75488','Vollwaschmittel',4800,0.006,1,1,0,1,0,NULL,'\r'),(226,3,7,1,27,'42498','Vanish Oxi Action Pow.Weiss 1Kg',1000,0.01,0,0,0,1,0,NULL,'\r'),(227,1,7,1,27,'24087','Essigreiniger 1L G&G',1,0.001,0,0,0,1,0,NULL,'\r'),(228,1,7,1,27,'49123','Wc Reiniger 1Ltr.G&G',5,0,0,0,0,1,0,NULL,'\r'),(229,4,1,1,27,'35118','Toipa 3Lag.8X200Bl Recycling G&G',1,0.002,0,0,1,1,0,NULL,'\r'),(230,3,4,1,19,'947540','Nusskernmischung',500,0.006,0,0,0,1,0,NULL,'\r'),(231,6,4,1,19,'49760','Tomaten Pizza',4250,0.004,0,0,0,1,0,NULL,'6 Stück'),(232,3,4,1,19,'70221','Tomaten Passiert',1000,0.001,0,0,0,1,0,NULL,'12 Stück'),(233,3,4,1,21,'549451','Tomatenmark',200,0.001,1,1,0,1,0,NULL,'\r'),(234,3,4,1,21,'131508','Pesto Alla Genovese Tigullio',500,0.006,0,0,0,1,0,NULL,'\r'),(235,3,4,1,21,'666337','Senf Suess',1000,0.003,0,0,0,1,0,NULL,'\r'),(236,3,4,1,21,'778124','Senf Dijon',800,0.005,0,0,0,1,0,NULL,'\r'),(237,3,4,1,21,'445223','Meerrettich Sahne',240,0.002,0,0,0,1,0,NULL,'6 Gläser'),(238,3,4,1,33,'962173','Gurken Dillschnitten',670,0,0,0,0,1,0,NULL,'\r'),(239,3,4,1,21,'658893','Pepperoni Mild',300,0,0,0,0,1,0,NULL,'\r'),(240,3,4,1,15,'67618','Mango Viertel',425,0.001,1,1,0,1,0,NULL,'6 Dosen'),(241,3,4,1,13,'574872','Aprikosen gezuckert',2650,0.004,1,1,0,1,0,NULL,'\r'),(242,6,4,1,34,'944018','Pfirsiche',2650,0.003,1,1,0,1,0,NULL,'\r'),(243,6,4,1,13,'19394','Birnen 1/2Frucht Topkauf',2650,0.004,0,0,0,1,0,NULL,'\r'),(244,6,4,1,30,'43809','Oel Sonnenblumen',10000,0.015,0,0,0,1,3,NULL,'\r'),(245,6,4,1,30,'546953','Oel Oliven',5000,0.015,0,0,0,1,2,NULL,'\r'),(246,6,4,1,30,'650752','Essig Balsamico',1000,0.007,0,0,0,1,0,NULL,'\r'),(247,6,4,1,30,'443855','Essig Aceto Balsamico  Bianco',1000,0,0,0,0,1,0,NULL,'\r'),(248,6,4,1,30,'938448','Essig Weisswein',2000,0.005,0,0,0,1,0,NULL,'\r'),(249,3,4,1,21,'67522','Speisesalz',12500,0.005,1,1,0,1,0,NULL,'\r'),(250,3,4,1,18,'463090','Weizenmehl',10000,0,0,0,0,1,0,NULL,'10 Pack'),(251,3,4,1,18,'29183','Mehl Dinkel',1000,0.002,0,0,0,1,0,NULL,'10 Päckchen'),(252,3,4,1,18,'440968','Paniermehl',5000,0.006,1,1,0,1,0,NULL,'\r'),(253,3,4,1,18,'212018','Kartoffelmehl',500,0.001,0,0,0,1,0,NULL,'\r'),(254,3,4,1,13,'696161','Mandeln gehobelt',500,0.005,1,1,0,1,0,NULL,'\r'),(255,3,4,1,13,'696185','Mandeln gehackt',500,0.005,1,1,0,1,0,NULL,'\r'),(256,3,4,1,13,'960492','Mandeln gemahlen',1000,0.009,0,0,0,1,0,NULL,'\r'),(257,3,4,1,13,'462485','Haselnusskerne Gehackt Backstu',100,0.001,0,0,0,1,0,NULL,'\r'),(258,3,4,1,13,'671308','Haselnusskerne gemahlen',1000,0.01,0,0,0,1,0,NULL,'\r'),(259,3,4,1,13,NULL,'Schokol.Edel Bitter 50%Ritter ',100,0.9,0,0,0,1,0,NULL,'\r'),(260,6,4,1,19,'283355','Kokosnussmilch',1000,0.003,1,1,0,1,0,NULL,'12 Pack'),(261,3,4,1,15,'843073','Butter Nuss Schokos',1000,0.007,0,0,0,1,5,NULL,'\r'),(262,3,4,1,15,'936700','Butter Gebaeck',1000,0.007,0,0,0,1,5,NULL,'\r'),(263,3,4,1,15,'62800','Deloba Bahlsen',1700,0.015,0,0,0,1,0,NULL,'\r'),(264,3,4,1,21,'364360','Zucker fein',1000,0.001,0,0,0,1,1,NULL,'10 Stück'),(265,1,4,1,1,'13110','Kaffeesahne (Kiste240)',1,0.005,0,0,0,1,0,NULL,'\r'),(266,3,4,1,35,'120133','Ovomaltine Nachfüllbeutel',500,0.004,0,0,0,1,0,NULL,'\r'),(267,6,4,1,34,'84305','Sauerkirschen 12*720',8640,0.001,1,1,0,1,0,NULL,'12 Gläser * 720'),(268,3,4,1,22,'196154','Krokant Haselnuss Insula',500,0.003,0,0,0,1,0,NULL,'\r'),(269,1,4,1,22,'454436','Kögler Dekopicker Eisschirmchen',105,0.005,0,0,0,1,0,NULL,'\r'),(270,1,1,1,22,'17483','Katjes Gr.Rattensch.Apf.',200,0.006,0,0,1,1,0,NULL,'\r'),(271,5,1,1,22,'116141','Eis Creme Vanilla Moevenpick',6,0.025,0,0,1,1,0,NULL,'\r'),(272,1,4,1,22,'553691','Hohlhippen Dose',1,0.005,0,0,0,1,0,NULL,'\r'),(273,1,4,1,22,'560678','Eistüten',1,0.009,1,1,0,1,0,NULL,''),(274,1,4,1,22,'523185','Eisschirmchen Fackelmann',1,0.004,0,0,0,1,0,NULL,'\r'),(275,3,4,1,29,'65571','Nudeln Band 20Mm F.Tag Jeremia',2500,0.009,0,0,0,1,0,NULL,'\r'),(276,3,4,1,29,'299794','Nudelnester 4mm',2500,0.009,0,0,0,1,0,NULL,'\r'),(277,3,4,1,29,'516289','Spaghetti No.5',5000,0.01,0,0,0,1,0,NULL,'\r'),(278,3,3,1,29,'16296','Tortiglioni No.83 Barilla',5000,0.01,0,0,0,1,0,NULL,'\r'),(279,3,4,1,29,'517893','Penne Lisce',5000,0.01,0,0,0,1,0,NULL,'\r'),(280,3,4,1,29,'938988','Bavette Barilla',5000,0,0,0,0,1,0,NULL,'\r'),(281,3,4,1,29,'542219','Farfalle',5000,0.01,0,0,0,1,0,NULL,'\r'),(282,3,4,1,29,NULL,'Lasagne Blätter Barilla',500,0,0,0,0,1,0,NULL,'\r'),(283,3,4,1,29,'438990','Gnocchi',500,0.002,1,1,0,1,0,NULL,'12 Pack'),(284,3,4,1,29,'438876','Basmati Reis',5000,0,0,0,0,1,0,NULL,'\r'),(285,3,5,1,34,'536363','Himbeeren TK',2500,0.011,0,0,0,1,1,NULL,'\r'),(286,3,5,1,34,'941353','Pflaumen TK',2500,0.005,1,1,0,1,0,NULL,'\r'),(287,3,5,1,34,'941357','Johannisbeeren Rot TK',2500,0.006,0,0,0,1,1,NULL,'\r'),(288,3,5,1,34,'941363','Rhabarber Rot TK',2500,0.004,1,1,0,1,0,NULL,'\r'),(289,3,1,1,8,'536372','Beerenteller Tk Golden Crown',2500,0.006,0,0,1,1,0,NULL,'\r'),(496,3,5,1,34,'42258','Johannisbeeren Schwarz TK',2500,0,0,0,0,1,1,NULL,'\r'),(610,3,1,3,61,'783802','Gemüsebrühe Würzl (Gebinde 10kg)',1,0,1,1,1,1,0,NULL,''),(611,1,2,1,16,'441068','Servietten',6,30,1,1,0,1,0,NULL,''),(612,3,4,1,21,NULL,'Pfeffer Rot',250,0,1,1,0,1,0,NULL,''),(613,3,3,13,38,NULL,'Putenbrust',1,1,0,0,0,1,0,NULL,''),(614,3,3,1,1,NULL,'Parmesan',1200,0,1,1,0,1,0,NULL,''),(615,1,2,3,61,'99570','Eier lose',180,0,0,1,0,1,720,NULL,''),(616,4,1,1,15,NULL,'Ritter Sport Halbbitter',1,0.8,0,0,1,1,0,NULL,''),(617,3,3,3,1,'144101','Gouda',4000,0,1,1,0,1,0,NULL,''),(618,3,3,3,61,'182002','Tofu natur',2000,0,0,0,0,1,0,NULL,''),(619,3,4,3,61,'570805','Basis Müsli',4000,0,1,0,0,1,0,NULL,''),(620,3,4,3,18,'574303','Grünkernschrot',4000,0,0,0,0,1,0,NULL,''),(621,3,4,3,18,'581306','Grünkern ganz',4000,0,0,0,0,1,0,NULL,''),(622,3,4,3,18,'581708','Hirse',4000,0,0,0,0,1,0,NULL,''),(623,3,4,3,19,'582805','Linsen braun',4000,0,0,0,0,1,0,NULL,''),(624,3,4,3,19,'582809','Linsen Beluga schwarz',4000,0,0,0,0,1,0,NULL,''),(625,3,4,3,18,'558507','Maismehl',2000,0,0,0,0,1,0,NULL,''),(626,3,4,3,29,'559603','Maisgrieß Polenta',3000,0,0,0,0,1,0,NULL,''),(627,6,4,3,21,'775307','Sojasoße Tamari',3000,0,1,0,0,1,0,NULL,''),(628,3,4,3,9,'662013','Thunfisch naturell',1200,0,0,0,0,1,0,NULL,''),(629,3,2,3,21,'783802','Würzl (Eimer)',10000,0,0,0,0,1,0,NULL,''),(630,3,2,3,21,'794618','Bouillon m. Rindfleisch(Eimer)',4000,0,1,0,0,1,0,NULL,''),(631,3,2,3,21,'796416','Hühnerbouillon (Eimer)',4000,0,1,0,0,1,0,NULL,''),(632,3,4,3,15,'882502','Fruchtaufstrich Erdbeere',1500,0,1,1,0,1,0,NULL,''),(633,3,4,3,15,'882504','Fruchtaufstrich Kirsche',1500,0,1,1,0,1,0,NULL,''),(634,3,4,3,15,'882506','Fruchtaufstrich Himbeere',1500,0,0,1,0,1,0,NULL,''),(635,3,4,3,15,'882508','Fruchtaufstrich Johannisb',1500,0,1,1,0,1,0,NULL,''),(636,3,4,3,15,'882608','Fruchtaufstrich Orange',1500,0,1,0,0,1,0,NULL,''),(637,6,4,3,15,'890100','Ahornsirup',1000,0,1,0,0,1,0,0,''),(638,3,4,3,15,'902702','Speisehonig',6000,0,1,0,0,1,0,NULL,''),(639,3,4,3,15,'434503','Gummibärchen o. Gelatine',2500,0,1,0,0,1,0,NULL,''),(640,3,4,3,19,'683505','Zuckermais',1980,0,1,0,0,1,0,NULL,''),(641,3,4,3,61,'751401','Kalamata Oliven o. Stein',4550,0,1,0,0,1,0,NULL,''),(642,3,3,3,34,NULL,'Saftorangen',12000,0,0,1,0,1,0,NULL,''),(643,3,3,3,34,'671230','Zitronen',6000,0,1,1,0,1,0,NULL,''),(644,3,1,3,61,'651604','Paté Butterpilz',300,0,1,1,1,1,0,NULL,''),(645,3,1,3,61,'651608','Paté Majoran',300,0,1,1,1,1,0,NULL,''),(646,3,1,3,61,'651610','Paté Paprika',300,0,1,1,1,1,0,NULL,''),(647,3,4,3,22,NULL,'Amarena Kirsch Fruchtsoße',1500,0,1,0,0,1,0,NULL,''),(648,6,1,3,61,'100110','frische Vollmilch 3,8% (Gebinde 10x1l)',1,0,1,1,1,1,0,NULL,''),(649,6,4,3,1,'100214','Laktosefr. H-Vollmilch',12000,0,1,0,0,1,0,NULL,''),(650,6,1,3,35,'841311','Kokos-Ananassaft',4200,0,1,0,0,1,0,NULL,''),(651,6,1,3,35,'846318','Kirschsaft',6000,0,1,0,0,1,0,NULL,''),(652,6,1,3,35,'841904','Pink Grapefruitsaft',4200,0,1,0,0,1,0,NULL,''),(653,6,1,3,35,'841902','Mango-Maracujasaft',6000,0,1,0,0,1,0,NULL,''),(654,6,1,3,35,'846515','Rhabarbersaft',4200,0,1,0,0,1,0,NULL,''),(655,6,1,3,35,'846715','Birnensaft',4200,0,1,0,0,1,0,NULL,''),(656,6,1,3,35,'845003','Quittensaft',4200,0,1,0,0,1,0,NULL,''),(657,6,1,3,35,'846316','Bananensaft',6000,0,1,0,0,1,0,NULL,''),(658,6,1,3,35,'850100','Bionade Holunder',3960,0,1,0,0,1,0,NULL,''),(659,6,1,3,35,'850106','Bionade Ingwer Orange',3960,0,1,0,0,1,0,NULL,''),(660,6,1,3,35,'851860','Hornberger Lebensqu. Nat.',6600,0,1,0,0,1,0,NULL,''),(661,3,4,3,35,'852406','Wiener Verführung ganze Bo',2500,0,1,1,0,1,0,NULL,''),(662,3,4,3,35,'852408','Wiener Verführung entc. Gem',2500,0,1,0,0,1,0,NULL,''),(663,3,4,3,35,'853210','Cocoba instant',2400,0,1,0,0,1,0,NULL,''),(664,3,4,3,35,'853409','Getreidekaffee inst.',2000,0,1,0,0,1,0,NULL,''),(665,6,4,3,35,'854009','Soya-Drink-Plus',12000,0,1,0,0,1,0,NULL,''),(666,3,6,3,35,'328704','Pfefferminztee',500,0,1,0,0,1,0,NULL,''),(667,3,6,3,35,'328709','Früchte-Tee',1000,0,1,0,0,1,0,NULL,''),(668,3,6,3,35,'328800','Kräutertraum Tee',1000,0,1,0,0,1,0,NULL,''),(669,3,6,3,35,'320000','Rooibusch Vanille Tee',600,0,1,0,0,1,0,NULL,''),(670,1,1,3,62,'316907','Bourbon- Vanilleschoten',2,0,0,0,1,1,0,NULL,''),(671,3,6,3,35,'5279','Darjeeling ganzes Blatt Tee',1000,0,1,0,0,1,0,NULL,''),(672,3,6,3,35,'5239','Earl Grey classic Tee',1000,0,1,0,0,1,0,NULL,''),(673,3,6,3,35,'5255','Assam lose Tee',1000,0,1,0,0,1,0,NULL,''),(674,3,6,3,35,'5294','Grüntee China ganzes Bl Tee',1000,0,1,0,0,1,0,NULL,''),(675,3,6,3,35,'5366','Grüntee Jasmin ganzes Bl Tee',1000,0,1,0,0,1,0,NULL,''),(676,3,6,3,35,'2218','Kamille Tee',500,0,1,0,0,1,0,NULL,''),(677,3,6,3,35,'2184','Rooibos Pur Tee',1000,0,1,0,0,1,0,NULL,''),(678,3,6,3,35,'300620','Chai classic Tee',720,0,1,0,0,1,0,NULL,''),(679,3,4,3,15,'8911880','Fairetta white Riegel',0,0,1,0,0,1,0,NULL,''),(680,3,4,3,15,'8901849','Fairetta Krokant Riegel',0,0,1,0,0,1,0,NULL,''),(681,3,4,3,15,'8951871','Fairetta Creme Cocos Riegel',0,0,1,0,0,1,0,NULL,''),(682,3,4,3,15,'430434','Espresso Biscotti Schokoriegel',1350,0,1,0,0,1,0,NULL,''),(685,3,1,1,12,NULL,'Semmelbrösel',1000,0,1,0,1,1,0,NULL,''),(686,3,4,1,21,NULL,'Chiliflocken',500,0,1,1,0,1,0,NULL,''),(687,3,4,1,21,NULL,'Chili, getrocknet',100,0,1,1,0,1,0,NULL,''),(688,3,4,1,21,NULL,'Senfkörner',100,0,1,1,0,1,0,NULL,''),(689,3,4,1,21,NULL,'Nelken, gemahlen',50,0,1,1,0,1,0,NULL,''),(690,3,4,1,19,NULL,'Steinpilze, getrocknet',1000,0,1,1,0,1,0,NULL,''),(691,3,1,1,21,NULL,'Lorbeerblätter',500,0,1,0,1,1,0,NULL,''),(692,3,4,1,21,NULL,'Lorbeerblätter',500,0,1,1,0,1,0,NULL,''),(694,2,1,46,18,NULL,'Bulgur grob',1,1.79,0,0,1,1,0,NULL,''),(695,1,1,28,35,NULL,'Mineralwasser 0,7 Kiste',1,3.5,0,1,0,1,35,NULL,''),(696,1,1,28,35,NULL,'Apfelsaft Bio 1l Kiste',1,10.4,0,1,0,1,8,NULL,''),(697,1,1,28,35,NULL,'Orangensaft Bio 1l Kiste',1,11.4,0,1,0,1,8,NULL,''),(698,1,1,28,35,NULL,'Plaumensaft Bio 1l Kiste',1,11.7,0,1,0,1,1,NULL,''),(699,1,1,28,35,NULL,'Glühwein 1l Kiste',1,11.7,0,1,0,1,1,NULL,''),(700,1,1,28,35,NULL,'Johannisbeersaft Bio 0,5l Kiste',1,12.4,0,1,0,1,2,NULL,''),(701,1,1,28,35,NULL,'Süßer Sprudel 0,7l Kiste',1,4.3,0,1,0,1,1,NULL,''),(702,1,1,28,35,NULL,'Höpfner Export 0,5l Liste',1,13.3,0,1,0,1,1,NULL,''),(703,1,1,28,35,NULL,'Höpfner Pils 0,5l Kiste',1,13.3,0,1,0,1,1,NULL,''),(704,1,1,28,35,NULL,'Rothaus Hefe 0,5l Kiste',1,13,0,1,0,1,1,NULL,''),(705,1,1,28,35,NULL,'Tannenzäpfle 0,33l Kiste',1,12.6,0,1,0,1,2,NULL,''),(706,1,1,28,35,NULL,'Tannenzäpfle alkfrei 0,33l Kiste',1,12.6,0,1,0,1,1,NULL,''),(707,1,1,28,35,NULL,'Africola 0,2l Kiste',1,9.5,0,1,0,1,5,NULL,''),(708,1,1,28,35,NULL,'Bluna 0,2l Kiste',1,9.5,0,1,0,1,2,NULL,''),(709,1,6,28,16,NULL,'Kellnerblöcke Packung',1,0,0,1,0,1,1,NULL,''),(711,6,4,1,30,NULL,'Olivenöl',5000,0,1,1,0,1,0,NULL,''),(712,3,4,1,18,NULL,'Mondamin',500,0,1,1,0,1,0,NULL,''),(714,6,4,1,30,NULL,'Sonnenblumenöl',5000,0,1,1,0,1,0,NULL,''),(715,3,4,1,21,NULL,'Kardamom-Kapsel',250,0,1,1,0,1,0,NULL,''),(716,3,4,1,21,NULL,'Zimtstangen',250,0,1,1,0,1,0,NULL,''),(717,3,4,1,21,NULL,'Kreuzkümmel, ganz',250,0,1,1,0,1,0,NULL,''),(718,3,4,1,21,NULL,'Sambal Oelek',500,0,1,1,0,1,0,NULL,''),(719,2,1,13,38,NULL,'Hackfleisch',1,12,0,0,1,1,0,NULL,''),(721,3,1,1,13,NULL,'Mandelblättchen',1,1,0,0,1,1,0,NULL,''),(722,3,3,13,38,NULL,'Putenbrust geschnitten',1,0,0,0,0,1,0,NULL,''),(723,3,3,13,38,NULL,'Putenoberkeule o. Knochen',1,0,0,0,0,1,0,NULL,''),(724,2,1,13,38,NULL,'Hühnchenbrust',1,0,0,0,1,1,0,NULL,''),(725,1,1,13,38,NULL,'Hühnchenkeule ausgebeint',1,0,0,0,1,1,0,NULL,''),(726,3,3,13,38,NULL,'Hackfleisch gemischt',1,0,0,0,0,1,0,NULL,''),(727,3,3,13,38,NULL,'Hackfleisch Rind',1,0,0,0,0,1,0,NULL,''),(728,3,3,13,38,NULL,'Hackfleisch Kalb',1,0,0,0,0,1,0,NULL,''),(729,3,3,13,38,NULL,'Schweinenacken geschn.',1,0,0,0,0,1,0,NULL,''),(730,2,1,13,38,NULL,'Schweinenuss',1,0,0,0,1,1,0,NULL,''),(731,2,1,13,38,NULL,'Schweinerücken',1,0,0,0,1,1,0,NULL,''),(732,2,1,13,38,NULL,'Rinderbug',1,0,0,0,1,1,0,NULL,''),(733,3,3,13,38,NULL,'Schäufele ohne Knochen',1,0,0,0,0,1,0,NULL,''),(734,3,3,13,38,NULL,'Gulasch Rind',1,0,0,0,0,1,0,NULL,''),(735,2,1,13,38,NULL,'Gulasch Schwein',1,0,0,0,1,1,0,NULL,''),(736,3,3,13,38,NULL,'Gulasch gemischt',1,0,0,0,0,1,0,NULL,''),(737,3,1,3,1,'144101','Gouda jung',1,0,0,0,1,1,0,NULL,''),(738,3,1,3,24,'581708','Hirse',4,0,0,0,1,1,0,NULL,''),(739,3,1,3,24,'582805','Linsen braun',4,0,0,0,1,1,0,NULL,''),(740,3,1,3,24,'582809','Linsen Beluga schwarz',4,0,0,0,1,1,0,NULL,''),(741,3,1,3,24,'558507','Maismehl',2,0,0,0,1,1,0,NULL,''),(742,3,1,3,24,'559603','Maisgriess',3,0,0,0,1,1,0,NULL,''),(743,2,1,3,24,'783802','Würzl',10,0,0,0,1,1,0,NULL,''),(745,3,4,1,13,'123743','Schokolade halbbitter',100,0,0,0,0,1,0,NULL,'12 Tafeln'),(746,6,4,1,15,'934611','Karamellsirup',700,0,1,1,0,1,0,NULL,''),(747,6,6,1,35,'934618','Vanillesirup',700,0,1,1,0,1,0,NULL,''),(748,6,4,1,15,'934616','Haselnusssirup',700,0,1,1,0,1,0,NULL,''),(749,1,6,1,35,'934619','Zimtsirup',700,0,1,1,0,1,0,NULL,''),(750,3,4,1,19,'48880','Linsen rot',5000,0,1,1,0,1,0,NULL,''),(751,3,4,1,13,'527451','Schokolade weiß',100,0,1,1,0,1,0,NULL,''),(752,1,2,1,27,'528799','Muellbeutel grau 60 l',10,0,1,1,0,1,0,NULL,''),(753,1,1,1,24,'941074','Dessertdeckchen',400,0,1,0,1,1,0,NULL,''),(754,3,5,1,56,'932571','Kraeuter gemischt TK',500,0,1,1,0,1,0,NULL,''),(755,3,4,1,29,'536108','Paellareis',500,0,1,1,0,1,0,NULL,''),(756,3,4,1,13,'595060','Blattgelatine',1000,0,1,1,0,1,0,NULL,''),(757,3,4,1,13,'669275','Quittengelee',450,0,1,1,0,1,0,NULL,''),(758,3,3,1,21,'933017','Mango Chutney',800,0,1,1,0,1,0,NULL,''),(759,1,2,1,27,'575425','Schwammtuch',5,0,1,1,0,1,0,NULL,''),(760,1,1,3,34,NULL,'Orange',1,0,0,0,1,1,0,NULL,''),(761,1,1,3,34,NULL,'Zitrone',1,0,1,1,1,1,0,NULL,''),(762,6,3,3,1,'100110','frische Vollmilch 3,8%',10000,0,1,1,0,1,0,NULL,''),(763,3,3,13,59,NULL,'gek. Schinken',0,0,0,0,0,1,0,NULL,''),(764,3,3,13,59,NULL,'Lyoner geschn',0,0,0,0,0,1,0,NULL,''),(765,3,3,13,59,NULL,'Salami, normal',0,0,0,0,0,1,0,NULL,''),(766,3,3,13,59,NULL,'Bacon',0,0,0,0,0,1,0,NULL,''),(767,3,3,13,59,NULL,'Speckstreifen',0,0,0,0,0,1,0,NULL,''),(768,3,3,13,1,NULL,'Ziegengouda',0,0,0,0,0,1,0,NULL,''),(769,1,3,13,59,NULL,'Bratwurst',0,0,0,0,0,1,0,NULL,''),(770,1,3,13,59,NULL,'Wiener Würstchen',0,0,0,0,0,1,0,NULL,''),(771,3,3,13,59,NULL,'Fleischkäs',0,0,0,0,0,1,0,NULL,''),(772,3,5,7,28,'','XXX_TESTER_A',12,12,0,0,1,0,0,NULL,''),(773,8,4,15,19,'123','xxxx_HALLO',23,23,1,1,0,1,7,NULL,'123123'),(774,11,2,8,28,'','qweqwe',123,0,0,0,0,0,13,23,''),(775,9,2,8,28,'','XXX_qwe12',123,0,0,0,0,0,13,10,''),(776,11,3,8,13,'','xxxxxxxxxxx',12,0,0,0,0,0,0,0,''),(777,3,3,8,29,'','xx11111111',12,0,0,0,0,0,0,0,''),(778,13,7,8,74,'','yyyyyyyyyyy',23,0,0,0,0,0,0,0,''),(779,3,3,4,22,'','yxyxyx',12,0,0,0,0,0,0,0,''),(780,3,7,8,29,'','qweqweqweqwe',12,0,0,0,0,0,0,0,''),(781,7,1,7,30,'','12345',123,0,0,0,1,0,0,0,''),(782,7,2,7,29,'','123wqe',324,0,0,0,1,0,0,0,''),(783,13,6,7,38,'','xxxtestz',123,0,0,0,0,0,0,0,''),(784,7,3,8,38,'','11111111',12,0,0,0,1,0,0,0,''),(785,3,5,7,28,'','11111111',23,0,0,1,1,0,0,4,''),(786,11,3,8,29,'','333',123,0,0,0,1,0,0,0,''),(787,3,2,8,28,'','111111111',12,0,0,0,1,0,0,0,''),(788,3,2,7,28,'','1236',123,0,0,0,1,0,0,0,''),(789,7,6,7,30,'','111111',34,0,0,0,1,0,0,0,''),(790,7,7,11,29,'','111111111111',342,0,0,0,1,0,0,0,''),(791,7,1,5,30,'','11111111',12,0,0,0,1,0,0,0,''),(792,7,6,8,29,'','1231313123',34,0,0,0,1,0,0,0,''),(793,11,7,7,29,'','666666666',3,0,0,0,1,0,0,0,''),(794,7,3,2,22,'','111111',123,0,0,0,1,0,0,0,''),(795,9,7,5,74,'','19000',122,0,0,0,1,0,0,0,''),(796,11,7,4,30,'','12121212121',4,0,0,0,1,0,0,0,''),(797,11,3,7,30,'','213',3,0,0,0,1,0,0,0,''),(798,11,1,8,13,'','123',213,0,0,0,1,0,0,0,''),(799,3,7,8,29,'','11111111',123,0,0,0,1,0,0,0,'');
/*!40000 ALTER TABLE `artikel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bestellposition`
--

DROP TABLE IF EXISTS `bestellposition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bestellposition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artikel_fk` int(11) NOT NULL,
  `bestellung_fk` int(11) NOT NULL,
  `liefermenge1` double DEFAULT NULL,
  `liefermenge2` double DEFAULT NULL,
  `status` tinyint(1) NOT NULL COMMENT 'ja nein',
  PRIMARY KEY (`id`),
  KEY `ARTIKEL_FK_idx` (`artikel_fk`),
  KEY `BESTELL_FK_idx` (`bestellung_fk`),
  CONSTRAINT `ARTIKEL_FK` FOREIGN KEY (`artikel_fk`) REFERENCES `artikel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `BESTELL_FK` FOREIGN KEY (`bestellung_fk`) REFERENCES `bestellung` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1087 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bestellposition`
--

LOCK TABLES `bestellposition` WRITE;
/*!40000 ALTER TABLE `bestellposition` DISABLE KEYS */;
INSERT INTO `bestellposition` VALUES (979,615,88,129.6,0,0),(980,632,88,0,0,0),(981,634,88,0,0,0),(982,635,88,0,0,0),(983,633,88,0,0,0),(984,617,88,0,0,0),(985,642,88,0,0,0),(986,661,88,0,0,0),(987,643,88,0,0,0),(988,762,88,0,0,0),(989,160,89,0,0,0),(990,7,89,0,0,0),(991,88,89,0,0,0),(992,241,89,0,0,0),(993,181,89,0,0,0),(994,70,89,0,0,0),(995,756,89,0,0,0),(996,49,89,0,0,0),(997,687,89,0,0,0),(998,686,89,0,0,0),(999,206,89,1.5,2,0),(1000,162,89,0,0,0),(1001,273,89,0,0,0),(1002,209,89,0,0,0),(1003,211,89,0,0,0),(1004,33,89,0,0,0),(1005,219,89,0,0,0),(1006,218,89,0,0,0),(1007,108,89,0,0,0),(1008,283,89,0,0,0),(1009,111,89,0,0,0),(1010,166,89,0,0,0),(1011,167,89,0,0,0),(1012,748,89,0,0,0),(1013,29,89,0,0,0),(1014,144,89,0,0,0),(1015,746,89,0,0,0),(1016,715,89,0,0,0),(1017,121,89,0,0,0),(1018,157,89,0,0,0),(1019,260,89,0,0,0),(1020,135,89,0,0,0),(1021,754,89,0,0,0),(1022,717,89,0,0,0),(1023,196,89,0,0,0),(1024,220,89,0,0,0),(1025,138,89,0,0,0),(1026,79,89,0,0,0),(1027,26,89,0,0,0),(1028,124,89,0,0,0),(1029,750,89,0,0,0),(1030,692,89,0,0,0),(1031,255,89,0,0,0),(1032,254,89,0,0,0),(1033,758,89,0,0,0),(1034,240,89,0,0,0),(1035,36,89,0,0,0),(1036,712,89,0,0,0),(1037,752,89,0,0,0),(1038,214,89,0,0,0),(1039,215,89,0,0,0),(1040,689,89,0,0,0),(1041,19,89,0,0,0),(1042,711,89,0,0,0),(1043,755,89,0,0,0),(1044,252,89,0,0,0),(1045,614,89,0,0,0),(1046,612,89,0,0,0),(1047,242,89,0,0,0),(1048,286,89,0,0,0),(1049,52,89,0,0,0),(1050,92,89,0,0,0),(1051,757,89,0,0,0),(1052,288,89,0,0,0),(1053,9,89,0,0,0),(1054,718,89,0,0,0),(1055,267,89,0,0,0),(1056,751,89,0,0,0),(1057,759,89,0,0,0),(1058,688,89,0,0,0),(1059,611,89,0,0,0),(1060,714,89,0,0,0),(1061,249,89,0,0,0),(1062,690,89,0,0,0),(1063,20,89,0,0,0),(1064,45,89,0,0,0),(1065,233,89,0,0,0),(1066,173,89,0,0,0),(1067,747,89,0,0,0),(1068,225,89,0,0,0),(1069,51,89,0,0,0),(1070,5,89,0,0,0),(1071,136,89,0,0,0),(1072,749,89,0,0,0),(1073,716,89,0,0,0),(1074,615,90,129.6,0,0),(1075,632,90,0,0,0),(1076,634,90,0,0,0),(1077,635,90,0,0,0),(1078,633,90,0,0,0),(1079,617,90,0,0,0),(1080,642,90,0,0,0),(1081,661,90,0,0,0),(1082,643,90,0,0,0),(1083,762,90,0,0,0),(1084,773,91,161,0,0),(1085,773,92,161,0,0),(1086,773,93,161,0,0);
/*!40000 ALTER TABLE `bestellposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bestellung`
--

DROP TABLE IF EXISTS `bestellung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bestellung` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lieferant_fk` int(11) NOT NULL,
  `mitarbeiter_fk` int(11) NOT NULL,
  `lieferdatum1` date NOT NULL,
  `lieferdatum2` date DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `kategorie` varchar(100) NOT NULL,
  `datum` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `LIEFERANT_FK_idx` (`lieferant_fk`),
  KEY `MITARBEIT_FK_idx` (`mitarbeiter_fk`),
  CONSTRAINT `LIEFERANT_FK` FOREIGN KEY (`lieferant_fk`) REFERENCES `lieferant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `MITARBEIT_FK` FOREIGN KEY (`mitarbeiter_fk`) REFERENCES `mitarbeiter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bestellung`
--

LOCK TABLES `bestellung` WRITE;
/*!40000 ALTER TABLE `bestellung` DISABLE KEYS */;
INSERT INTO `bestellung` VALUES (88,3,5,'2014-01-13',NULL,0,'Grundbedarf','2014-01-06 17:06:56'),(89,1,5,'2014-01-13','2014-01-13',0,'Grundbedarf','2014-01-06 19:13:10'),(90,3,5,'2014-01-13',NULL,0,'Grundbedarf','2014-01-12 17:00:51'),(91,15,5,'2014-01-13',NULL,0,'Grundbedarf','2014-01-12 19:55:10'),(92,15,5,'2014-01-20',NULL,0,'Grundbedarf','2014-01-12 20:00:28'),(93,15,5,'2014-01-13',NULL,0,'Grundbedarf','2014-01-12 20:04:11');
/*!40000 ALTER TABLE `bestellung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passwort` varchar(45) NOT NULL,
  `schlussel` varchar(45) NOT NULL,
  `pw_length` int(11) NOT NULL,
  `descript` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `descript_UNIQUE` (`descript`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,'PE/ozB2YAyZAAA==','cafe',10,'nachricht');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fussnote`
--

DROP TABLE IF EXISTS `fussnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fussnote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `abkuerzung` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `abkuerzung_UNIQUE` (`abkuerzung`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fussnote`
--

LOCK TABLES `fussnote` WRITE;
/*!40000 ALTER TABLE `fussnote` DISABLE KEYS */;
INSERT INTO `fussnote` VALUES (1,'vegan','v'),(2,'vegan moegl.','vm'),(3,'vegetarisch moegl.','veg.m'),(4,'ohne Zwiebel','Z'),(5,'ohne Zwiebel moegl.','Zm'),(6,'ohne Knoblauch','K'),(7,'ohne Knoblauch moegl.','Km'),(8,'ohne Weizen','W'),(9,'ohne Weizen moegl.','Wm'),(10,'ohne KuhMilch','M'),(11,'ohne KuhMilch moegl.','Mm');
/*!40000 ALTER TABLE `fussnote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fussnotekuchen`
--

DROP TABLE IF EXISTS `fussnotekuchen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fussnotekuchen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `abkuerzung` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fussnotekuchen`
--

LOCK TABLES `fussnotekuchen` WRITE;
/*!40000 ALTER TABLE `fussnotekuchen` DISABLE KEYS */;
INSERT INTO `fussnotekuchen` VALUES (1,'weizenfrei','oWe'),(2,'glutenfrei','oG'),(3,'eifrei','oE'),(4,'laktosefrei','oL'),(5,'mit Mandeln','mM'),(6,'mit Walnüssen','mWa'),(7,'mit Haselnüssen','mH'),(8,'mit Alkohol','mA');
/*!40000 ALTER TABLE `fussnotekuchen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geschmack`
--

DROP TABLE IF EXISTS `geschmack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geschmack` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `inaktiv` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geschmack`
--

LOCK TABLES `geschmack` WRITE;
/*!40000 ALTER TABLE `geschmack` DISABLE KEYS */;
INSERT INTO `geschmack` VALUES (1,'asiatisch',0),(2,'deutsch',0),(3,'griechisch',0),(4,'italienisch',0),(5,'klassisch',0),(6,'orientalisch',0),(7,'unbekannt',0);
/*!40000 ALTER TABLE `geschmack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorie`
--

DROP TABLE IF EXISTS `kategorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kategorie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorie`
--

LOCK TABLES `kategorie` WRITE;
/*!40000 ALTER TABLE `kategorie` DISABLE KEYS */;
INSERT INTO `kategorie` VALUES (23,'Alkohol'),(13,'backen'),(29,'Beilagen'),(28,'Büro'),(22,'Eis'),(74,'EisKafe'),(30,'Essig/Öl'),(9,'Fisch'),(38,'Fleisch'),(56,'frische Kräuter'),(33,'Gemüse'),(18,'Getreide/Mehl'),(35,'Getränke'),(21,'Gewürz'),(19,'Hülsenfr./Nüsse/Trockenobst'),(1,'Milch'),(16,'Nonfood1'),(34,'Obst'),(27,'Putzen'),(62,'Sonstiges'),(15,'süß'),(59,'Wurst'),(61,'XXX_Bio Küche'),(14,'XXX_Dosenobst'),(39,'XXX_Eier'),(12,'XXX_getrocknet'),(32,'XXX_Kekse'),(47,'XXX_Kräuter'),(24,'XXX_Küche'),(31,'XXX_Mehl'),(2,'XXX_Milch'),(11,'XXX_Nüsse'),(17,'XXX_Pasta'),(3,'XXX_Schinken'),(26,'XXX_Service'),(4,'XXX_Teig'),(63,'XXX_TESTER'),(70,'xxx_TEST_1'),(25,'XXX_Theke'),(7,'XXX_TK Gemüse'),(8,'XXX_TK Obst'),(5,'XXX_TK Teig'),(10,'XXX_Toast');
/*!40000 ALTER TABLE `kategorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kuchenplan`
--

DROP TABLE IF EXISTS `kuchenplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kuchenplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `week` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kuchenplan`
--

LOCK TABLES `kuchenplan` WRITE;
/*!40000 ALTER TABLE `kuchenplan` DISABLE KEYS */;
INSERT INTO `kuchenplan` VALUES (1,27,2013),(2,28,2013),(3,32,2013),(4,31,2013),(5,33,2013),(6,34,2013),(7,38,2013),(8,39,2013);
/*!40000 ALTER TABLE `kuchenplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kuchenplan_has_kuchenrezepte`
--

DROP TABLE IF EXISTS `kuchenplan_has_kuchenrezepte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kuchenplan_has_kuchenrezepte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kuchenplan_fk` int(11) NOT NULL,
  `kuchenrezept_fk` int(11) NOT NULL,
  `tag` int(11) NOT NULL,
  `anzahl` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_kuchenplan_has_kuchenrezepte_kuchenplan` (`kuchenplan_fk`),
  KEY `fk_kuchenplan_has_kuchenrezepte_kuchenrezept` (`kuchenrezept_fk`),
  CONSTRAINT `fk_kuchenplan_has_kuchenrezepte_kuchenplan` FOREIGN KEY (`kuchenplan_fk`) REFERENCES `kuchenplan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenplan_has_kuchenrezepte_kuchenrezept` FOREIGN KEY (`kuchenrezept_fk`) REFERENCES `kuchenrezept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kuchenplan_has_kuchenrezepte`
--

LOCK TABLES `kuchenplan_has_kuchenrezepte` WRITE;
/*!40000 ALTER TABLE `kuchenplan_has_kuchenrezepte` DISABLE KEYS */;
INSERT INTO `kuchenplan_has_kuchenrezepte` VALUES (1,1,1,1,1),(2,1,1,2,1),(3,1,1,4,1),(4,2,1,3,1),(11,4,2,5,1),(12,4,1,3,1),(13,4,2,2,1),(14,4,1,1,1),(15,3,1,3,1),(16,5,1,2,1),(20,6,1,1,1),(21,6,2,3,1),(22,7,1,1,1),(23,8,1,5,1);
/*!40000 ALTER TABLE `kuchenplan_has_kuchenrezepte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kuchenrezept`
--

DROP TABLE IF EXISTS `kuchenrezept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kuchenrezept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `kommentar` varchar(1000) DEFAULT NULL,
  `erstellt` timestamp NULL DEFAULT NULL,
  `aktiv` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kuchenrezept`
--

LOCK TABLES `kuchenrezept` WRITE;
/*!40000 ALTER TABLE `kuchenrezept` DISABLE KEYS */;
INSERT INTO `kuchenrezept` VALUES (1,'Russischer Zupfkuchen','','2013-09-20 22:00:00',1),(2,'Apple Pie','mit Aprikosenmarmelade-Zuckerguss','2013-07-19 22:00:00',1);
/*!40000 ALTER TABLE `kuchenrezept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kuchenrezept_has_artikel`
--

DROP TABLE IF EXISTS `kuchenrezept_has_artikel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kuchenrezept_has_artikel` (
  `kuchenrezept_fk` int(11) NOT NULL AUTO_INCREMENT,
  `artikel_fk` int(11) NOT NULL,
  `menge` decimal(10,4) NOT NULL,
  `einheit` int(11) NOT NULL,
  PRIMARY KEY (`kuchenrezept_fk`,`artikel_fk`),
  KEY `fk_kuchenrezept_has_artikel_einheit_idx` (`einheit`),
  KEY `fk_kuchenrezept_has_artikel_rezept_idx` (`kuchenrezept_fk`),
  KEY `fk_kuchenrezept_has_artikel_ARTIKEL_idx` (`artikel_fk`),
  CONSTRAINT `fk_kuchenrezept_has_artikel_ARTIKEL` FOREIGN KEY (`artikel_fk`) REFERENCES `artikel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenrezept_has_artikel_einheit` FOREIGN KEY (`einheit`) REFERENCES `mengeneinheit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenrezept_has_artikel_rezept` FOREIGN KEY (`kuchenrezept_fk`) REFERENCES `kuchenrezept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kuchenrezept_has_artikel`
--

LOCK TABLES `kuchenrezept_has_artikel` WRITE;
/*!40000 ALTER TABLE `kuchenrezept_has_artikel` DISABLE KEYS */;
INSERT INTO `kuchenrezept_has_artikel` VALUES (1,70,10.0000,1),(1,78,40.0000,1),(1,194,1.0000,1),(1,250,300.0000,1),(1,264,180.0000,1),(2,194,200.0000,1);
/*!40000 ALTER TABLE `kuchenrezept_has_artikel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kuchenrezept_has_fussnote`
--

DROP TABLE IF EXISTS `kuchenrezept_has_fussnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kuchenrezept_has_fussnote` (
  `kuchenrezept_fk` int(11) NOT NULL,
  `fussnotekuchen_fk` int(11) NOT NULL,
  KEY `fk_kuchenrezept_has_fussnote_kuchenrezept_idx` (`kuchenrezept_fk`),
  KEY `fk_kuchenrezept_has_fussnote_fussnote_idx` (`fussnotekuchen_fk`),
  CONSTRAINT `fk_kuchenrezept_has_fussnote_fussnote1` FOREIGN KEY (`fussnotekuchen_fk`) REFERENCES `fussnotekuchen` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_kuchenrezept_has_fussnote_kuchenrezept` FOREIGN KEY (`kuchenrezept_fk`) REFERENCES `kuchenrezept` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kuchenrezept_has_fussnote`
--

LOCK TABLES `kuchenrezept_has_fussnote` WRITE;
/*!40000 ALTER TABLE `kuchenrezept_has_fussnote` DISABLE KEYS */;
INSERT INTO `kuchenrezept_has_fussnote` VALUES (2,3);
/*!40000 ALTER TABLE `kuchenrezept_has_fussnote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lagerort`
--

DROP TABLE IF EXISTS `lagerort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lagerort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lagerort`
--

LOCK TABLES `lagerort` WRITE;
/*!40000 ALTER TABLE `lagerort` DISABLE KEYS */;
INSERT INTO `lagerort` VALUES (8,'Büro'),(1,'Getränkeraum'),(3,'Kühlhaus/Kühlschrank'),(7,'Personalklo'),(2,'Raum 3'),(6,'Theke vorn/Service'),(5,'TK'),(0,'unbekannt'),(4,'Vorratsregal');
/*!40000 ALTER TABLE `lagerort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lieferant`
--

DROP TABLE IF EXISTS `lieferant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lieferant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `kundennummer` varchar(45) DEFAULT NULL,
  `bezeichnung` varchar(45) DEFAULT NULL,
  `strasse` varchar(45) DEFAULT NULL,
  `plz` varchar(45) DEFAULT NULL,
  `ort` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `notiz` varchar(300) DEFAULT NULL,
  `mehrereliefertermine` tinyint(1) NOT NULL,
  `deaktivieren` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lieferant`
--

LOCK TABLES `lieferant` WRITE;
/*!40000 ALTER TABLE `lieferant` DISABLE KEYS */;
INSERT INTO `lieferant` VALUES (1,'Edeka','239742','Lebensmittellieferant','','','','','07231-9490-608','07231 - 9490-15 / -45','Die Liefertermine sind Freitags und Montags',1,0),(2,' Schenk ','','Lebensmittellieferant','Weinweg 43','76137','Karlsruhe','info@firma-schenk.de','+49 (0721) 961570','+49 (0721) 9615722','Die Liefertermine sind Freitags und Montags',1,0),(3,'Rinklin ','','Bio-Lieferant','Bruckmatten 12','79356','Eichstetten','info@rinklin-biomarkt.de','+49 7663 9394 70','+49 7663 9394 719','',0,0),(4,'Bardusch','67312','Handtuchrollen','','','','','07243/7070','','',0,0),(5,'Alba','24523','Biotonne','','','','','5000-60/-6124','','',0,0),(6,'KFZ Elektrik Stoll','','Autowerkstatt','','','','web@kfz-elektrik-stoll.de','0721/44224','0721/9431866','',0,0),(7,'Autohaus Kuhn GmbH','','Citroen Händler ','Wattstraße 14','76185',' Karlsruhe','info@autohaus-kuhn.de','0721/490180','(0721) 49018-69','',0,0),(8,'Andi Ermel','','Schreiner','','','','','1777538640','','',0,0),(9,'Bauer','','Kasse','','','','','07251-5293','','',0,0),(10,'Becht','','Steuerberater','','','','','07245-8048098','','',0,0),(11,'Birkle','','Gastro','','','','','555888','555848','',0,0),(12,'Bischof','','Herd, Salamander, Konvektomat','','','','','06232-6015980','','',0,0),(13,'Brath','','Metzgerei','','','','','358060','','',0,0),(14,'Dt.Hausfrauenbund','','Wäscheservice','','','','','379548','','',0,0),(15,'Epp','','Schlosser','','','','','07244-947351','','',0,0),(16,'Espresso','','Gerald ','','','','','2030397','','',0,0),(17,'Fasanenbrot','','Brötchen','','','','','07244-7372501','','',0,0),(18,'Glaser','','Getränkekühlung','','','','','9200815','','',0,0),(19,'Gourmet-Express','','Croissants','','','','','089/6939000','','',0,0),(20,'GSD','','Schädlingsvorbeugung','','','','','3540029','','',0,0),(21,'HaKa','','Spülmittel','','','','','553904','5978657','',0,0),(22,'Härtelt','','Installateur','','','','','25839','','',0,0),(23,'Jörger','','Fensterputzer','','','Wörth','','07273-8997520','','',0,0),(24,'Kaffeeshop 24','','Chai Latte','','','','bestellung@kaffeeshpo24.de','04101-8048520','','Hallo,\nHiermit würde ich gerne wie immer 2 Kartons vom Mocaff spiced chai fürs Café test bestellen.\nVielen Dank\nMit freundlichen Grüßen\n Susanne Lutz\n',0,0),(25,'Klassikcafé','','Friedemann Schäfer','','','','','','','',0,0),(26,'Misch, Richard ','','Kälte-technik','','','','','9863170','','',0,0),(27,'Münzer','','Personal','','','','','405558','','',0,0),(28,'Plaumann','4013','Getränke','','','','pfauenstrasse@getraenke-plaumann.de','890100','','',0,0),(29,'Riester, Peter','','Spülmaschine','','','','','0151-58577443','','',0,0),(30,'Sahnemaschine','','Vaihinger GmbH','','','','','06434-9405-0','','',0,0),(31,'Schaper','','Grossmarkt','','','','','9622726','','',0,0),(32,'Schnerring','','Rohrreinigung','','','','','491890','','',0,0),(33,'Sibold Kundendienst Rex Royal','','Kaffeemaschine','','','','','07623/74140','','',0,0),(34,'Striebich','','','','','','','380825','','',0,0),(35,'Udo','','Getränkeauslieferung','','','','','0171-8352039','','',0,0),(36,'Waschmaschine','','Ottmar Balder','','','','','2010925','','',0,0),(37,'Wash& Clean','','Putzfirma','','','','','97257-0','','',0,0),(38,'Winterhalter','','Gläserspülmasch.','','','','','551117','1805006620','',0,0),(39,'Zandonella+Nohr','','Eis','','','','mail@jost@zundn.de','06341-557565-0','','',0,0),(40,'Zitronenreiniger Sybille Letschert','','Sprühreiniger','','','','','02624-945854','','',0,0),(41,'Gewerbehof Freiraum-Büro + Hausmeister','','Hausmeister','','','','','','','',0,0),(46,'Sindbad','','','','','','','','','',0,0),(47,'Ehrlichs Weincontor','','Weinladen','Hebelstr. 19','76133 ','Karlsruhe','info@ehrlichsweincontor.de','0721-20 000','0721-29868',' Lieferung  in der Regel mittwochs. Bestellung eine Woche im Voraus',0,0),(48,'xxxTest','','','','','','','','','',0,0);
/*!40000 ALTER TABLE `lieferant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mengeneinheit`
--

DROP TABLE IF EXISTS `mengeneinheit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mengeneinheit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `kurz` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `kurz_UNIQUE` (`kurz`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mengeneinheit`
--

LOCK TABLES `mengeneinheit` WRITE;
/*!40000 ALTER TABLE `mengeneinheit` DISABLE KEYS */;
INSERT INTO `mengeneinheit` VALUES (1,'Stück','ST'),(2,'Kilogramm','KG'),(3,'Gramm','G'),(4,'Packung','PA'),(5,'Liter','L'),(6,'Milliliter','ML'),(7,'Flasche','FL'),(8,'Tabletten','Tab'),(9,'Blatt','B'),(10,'Unbekannt','UB'),(11,'Bund','BD'),(13,'Kasten','KA'),(14,'TEST','ttt');
/*!40000 ALTER TABLE `mengeneinheit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menue`
--

DROP TABLE IF EXISTS `menue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `koch` int(11) NOT NULL,
  `geschmack_fk` int(11) DEFAULT NULL,
  `menueart_fk` int(11) NOT NULL,
  `aufwand` tinyint(1) DEFAULT NULL,
  `favorit` tinyint(1) DEFAULT NULL,
  `aktiv` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menue_mitarbeiter1_idx` (`koch`),
  KEY `fk_geschmack1_idx` (`geschmack_fk`),
  KEY `fk_menue_menueart12` (`menueart_fk`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menue`
--

LOCK TABLES `menue` WRITE;
/*!40000 ALTER TABLE `menue` DISABLE KEYS */;
INSERT INTO `menue` VALUES (7,'Persischer Spinatreis mit Hummus und Walnussjoghurt',1,NULL,0,NULL,NULL,1),(8,'Schweinebraten mit Spätzle und Salat',2,4,2,0,0,1),(9,'Persischer Spinatreis mit Hummus',1,NULL,0,NULL,NULL,1),(10,'Erbsensuppe',2,2,4,0,0,1),(11,'Lasagne mit Salat und Dessert',1,4,2,0,1,1),(12,'Lasagne mit Salatbeilage',1,4,1,0,1,1),(13,'Thailändischer Glasnudelsalat',6,1,5,0,0,1),(14,'Saté Pute mit Erdnusssoße, Basmatireis, Chinakohlsalat',3,1,1,0,1,1),(15,'Karotten-Pesto- Lasagnemit Pinienkerne und Saltbeilage',4,4,2,1,1,1),(16,'Orientalisches Gemüse mit Bulgur und Currydip',6,6,2,0,0,1),(17,'Nasi Goreng ( indonesischer Paprikareis mit Sambal Oelek und Spiegelei )',4,1,2,0,1,1),(18,'Spirelli in Zitronen-Öl-Sauce mit Walnüssen, Petersilie, Cocktailtomaten und Parmesan',4,4,3,0,0,1),(19,'Linguine mit Basilikum-Honig-Sahne-Sauce und Chili',4,1,3,0,0,1),(20,'Gnocci mit Mandelpesto',4,4,2,0,0,0),(21,'Thailändischer Glasnudelsalat',6,1,5,0,0,0),(22,'Hackfleischlasagne mit Salatbeilage',6,4,1,0,0,1),(23,'testfgh',12,2,2,0,0,0),(24,'Spaghetti Arrabiata',6,4,3,0,0,1),(25,'Kartoffel-Gorgonzolagratin mit Salatbeilage',6,5,2,0,0,1),(26,'Putenroulade mit Karotten-Thymianfüllung, dazu Tagliatelle und Rucola',1,5,1,1,0,1),(27,'Lauch-Gorgonzola-Birnenquiche',6,5,2,0,0,1),(28,'Spirelli mit Kräuterpesto, Karotten und Cocktailtomaten',4,4,3,0,0,1),(29,'Veggie-Bolognese mit Spaghetti ( Tofugeschnetzeltes mit Gemüse )',4,2,3,0,0,1),(30,'Kartoffelauflauf mit Cabanossi und Ei dazu Salatbeilage',4,2,2,0,0,1),(31,'Gnocci mit Mandelpesto und Salatbeilage',4,4,2,0,0,1),(32,'Gebackene Bananen mit Ahornsirup und Sahne',4,2,6,0,0,1),(33,'Apfel-Schmand-Dessert mit Krokant',4,2,6,0,0,1),(34,'Aprikosen-Linsencurry mit Basmatireis',6,1,2,0,0,1),(35,'Tomatenblätterteigkuchen',6,5,2,0,0,1),(36,'Szegediner Gulasch',6,5,1,0,0,1),(37,'Ziegenkäse im Speckmantel mit Salat',6,5,5,0,0,1),(38,'Mousse au chocolat',6,5,6,0,0,1),(39,'Pasta mit Zitronenbutter und Basilikum',6,4,3,0,0,1),(40,'Kartoffelbrei',6,5,7,0,0,1),(41,'Pute in Calvadosrahm',6,5,1,0,0,1),(42,'Pute in Calvadosrahm mit Kartoffelbrei und Salatbeilage',6,5,1,0,0,1),(43,'Käsespätzle',5,2,2,0,1,1),(44,'Gebackenes Sommergemüse mit Farmerkartoffel und Mandel-Frischkäse-Dip',4,2,2,0,0,1),(45,'Kräuterpfannkuchen mit Kartoffel-Mangold-Füllung und Salatbeilage',4,2,2,0,0,1),(46,'Matjessalat Hausfrauenart mit Bratkartoffeln',3,5,2,0,0,1),(47,'risotto mit pfifferlingen,parmesan und rucola',3,4,2,0,0,1),(48,'TestMenu',1,2,6,0,0,0),(49,'TestRezept 3 ',1,2,1,0,0,0),(50,'Himbeerparfait',3,5,6,0,0,1),(51,'Schweinebraten mit Spätzle und Salbeisosse',3,2,1,0,0,1),(52,'Kartoffel-Kohlrabigratin mit Salatbeilage',3,2,2,0,0,1),(53,'Spagetti mit gebackenen Auberginenwürfeln, Basilikum und Pecorino',5,4,3,0,0,1),(54,'Pasta mit Fenchel-Rosinen-Chili-Tomatensugo und Parmesan',1,6,3,0,0,1),(55,'Penne alla puttanesca mit Parmesan',14,4,3,0,0,1),(56,'Pikantes Paprikagemüse mit Pfirsichen, dazu Avorioreis und Joghurt',14,6,2,0,0,0),(57,'Rindergulasch mit Bandnudeln und Salat',5,7,1,0,0,1),(58,'Libanesisches Joghurthühnchen mit Reis, Pinienkernen, Rosinen und Karotten',5,6,1,0,0,1),(59,'Salatteller mit gebratenen Lachswürfeln',5,7,5,0,0,1),(60,'Panzanella (Toskanischer Brotsalat mit Paprika, Oliven, Basilikum und Sardellen)',5,4,5,0,0,1),(61,'Gemüse-Couscous (mit Auberginen, Zucchini, Karotten)',5,6,2,0,0,1),(62,'Rigatoni Saltati (mit Schinken, Erbsen, Champignon und Tomatensahne)',5,7,3,0,0,1),(63,'gratinierte Erdbeeren mit Marzipan',1,7,6,0,0,1),(64,'grünes Thaicurry mit Cashewnüssen, Duftreis und Thaibasilikum',1,1,2,0,0,1),(65,'Bunte Blattsalate mit gratiniertem Ziegenkäse mit Thymian, Honig und Walnüssen',14,5,5,0,0,1),(66,'Pikantes Paprikagemüse mit Pfirsichen und Oliven, dazu Avorioreis und Joghurt',14,6,2,0,0,1),(67,'Thailändischer Glasnudelsalat',6,1,2,0,0,0),(68,'Lasagne',1,4,1,0,0,1),(69,'Salatteller mit panierten Austernpilzen und Kräuterdip',3,2,5,0,0,1),(70,'Semmelknödel mit Pfifferlingrahm und Rucola',1,2,2,1,0,1),(71,'Merguez mit Farmerkartoffeln, Avocadodip und Tomatensalat',1,6,1,0,0,1),(72,'Griechischer Joghurt mit Trauben, Walnüssen und Honig',1,3,6,0,0,1),(73,'Himberrlimettentiramisu',6,5,6,0,0,1),(74,'Ziegenkäse im Speckmantel mit Salat',6,5,5,0,0,0),(75,'Kartoffeltortilla mit rotem Mojo',6,5,2,0,0,1),(76,'Kräuterbulgur mit orientalischem Gemüse und Currydip',6,6,2,0,0,1),(77,'New York Cheesecake mit Orangensauce',6,5,6,0,0,1),(78,'Vegetarische Gemüselasagne mit Ricotta',6,4,2,0,0,1),(79,'pikantes Gemüsecurry mit Basmatireis',6,1,2,0,0,1),(80,'Kicherebsen mit Spinat, Kräuter und Frischkäse dazu Basmatireis',4,7,2,0,0,1),(81,'Kartoffelsalat mit Zuckerschoten, Karotten und Kräutercreme',4,2,5,0,0,1),(82,'Rote Bete- Birnen-Salat mit Walnüssen',4,2,5,0,0,1),(83,'Biryani ( Indischer Gemüsereis mit Rosinen, Cashew-Kerne und Joghurt )',4,1,2,0,0,1),(84,'Frittierte Linsenbällchen mit Kartoffel-Aubergine-Curry und Mangochutney',4,1,2,0,0,1),(85,'Kartoffelgratin mit Knollensellerie, Lauch und Thymian dazu Salatbeilage',4,2,2,0,0,1),(86,'Kurze Maccaroni mit Kichererbsen-Tomatensauce, Gomasio und Joghurt',4,1,2,0,0,1),(87,'Pasta mit Kichererbsen Tomatensauce, Gomasio und Joghurt',4,6,3,0,0,1),(88,'Mangocreme mit Karamellkruste',5,6,6,0,0,1),(89,'Auberginensalat  \"La Tamu\" auf Blattsalat ',5,6,5,0,0,1),(90,'Sapaghetti mit Rucolapesto und geschmorten Tomatenwürfeln und Parmesan ',5,4,3,0,0,1),(91,'Mexikanisches Hühnchen mit Paprika, Chili, Koriander u. Schokolade im Tortillafladen mit  Avocado-Tomatensalat ',5,7,1,0,0,1),(92,'Steinpilzrisotto mit Salatbeilage',5,4,2,0,0,1),(93,'Zucchini-Kartoffelgratin mit gegrillter Kräutertomate und Salatbeilage',5,7,2,0,0,1),(94,'Fenchel-Mozzarella-Salat mit Johannisbeersoße',4,7,5,0,0,1),(95,'Curry Masala (Tofu mit Brokkoli, Kartoffel, Karotten und Basmatireis)',4,1,2,0,1,1),(96,'Trollingerbraten mit Pommes Anna und Bohnengemüse',7,2,1,0,0,1),(97,'gefüllte Zuchini mit Kräuterhirse ,mit Parmesan gratiniert und Tomatensoße auf Rucolabett',7,7,2,0,0,0),(98,'Karotten-Aprikosenbällchen mit mariniertem Fenchel in Orangensoße mit Cous-cous',7,6,2,1,0,1),(99,'Lachs-Spinat-Lasagne',7,7,2,0,0,1),(100,'Blattsalate mit gratinierten, gefüllten Champignons',7,4,5,0,0,1),(101,'Beerenscrumble mit Vanilleeis',7,7,6,0,0,1),(102,'gefüllte Zuchini mit Kräuterhirse, Tomatensoße und Parmesan auf Rucolabett',7,4,2,0,0,1),(103,'Calamares mit Zitronenajoli und Salatbeilage',3,4,2,0,0,1),(104,'Maccaroncini mit Gorgonzolacremr und karamelisierten Birnen',3,2,3,0,0,1),(105,'Tabouhleh',3,6,5,0,0,1),(106,'Pealla Verdura mit Oliven',3,5,2,0,0,1),(107,'Penne mit Papaya-Chilli-Kokossosse',3,1,3,0,0,1),(108,'Blätterteigtaschen mit Aprikosen und Ricotta',3,2,6,0,0,1),(109,'Blätterteigtaschen mit Aprikosen und Ricotta',3,2,6,0,0,1),(110,'Erdbeer-Rhabarbercrumble mit Sahne',4,2,6,0,0,1),(111,'Salatteller mit Fenchel, Äpfel und Dattelsauce',4,2,5,0,0,1),(112,'Gefüllte Pfannkuchen mit Johannisbeeren und Karamellsauce',4,2,6,0,0,1),(113,'Salatteller mit Schafskäsecreme dazu Kirschtomaten und Pinienkerne',4,3,5,0,0,1),(114,'Paniertes Schweineschnitzel mit Kartoffel-Gurkensalat',3,2,1,0,0,1),(115,'Spinatschafskäse-Strudel mit Salatbeilage',3,6,2,0,0,1),(116,'Creme caramel',3,5,6,0,0,1),(117,'Pastizzio-Griechischer Hackfleischauflauf mit Macceroni,dazu Tomatensalat',3,3,1,0,0,1),(118,'Rosmarinkarftoffln mit Gorgonzolacreme und Blattsalat',3,2,2,0,0,1),(119,'Grünkernpfannkuchen mit Pfifferlingen und Frühlingszwiebeln',3,5,2,0,0,1),(120,'Zanderfilet mit Zuccini und Tomaten mit Salzkartoffeln',3,5,2,0,0,1),(121,'Salatteller mit gebratenen Auberginen und grünem Mojo',3,5,5,0,0,1),(122,'Hühnchen mit Ananas-Cürry Risotto',5,1,1,0,0,1),(123,'Nizza-Salat mit Bio-Thunfisch, Bohnen und Oliven',5,5,5,0,0,1),(124,'Rinder-Tajin mit getr. Aprikosen und Pflaumen, Couscous und Karotten',5,6,1,0,0,1),(125,'Pellkartoffeln mit Kräuterquark und Salatbeilage',5,2,2,0,0,1),(126,'Mattjes nach Hausfrauenart mit Bratkartoffeln',7,2,2,0,0,1),(127,'Süßkartoffelgratin mit Tomatensugo und Salatbeilage',7,4,2,0,0,1),(128,'Beerengrütze mit Vanilleeis',7,2,6,0,0,1),(129,'Zitronenhühnchen mit Oliven, mediteranes Gemüse und Kartoffelbrei',7,4,1,0,0,1),(130,'Blattsalate mit marinierten Austernpilzen',7,4,5,0,0,1),(131,'Linsenpilav  mit Röstzwiebeln und Spiegelei',7,6,2,0,0,1),(132,'Salatteller mit Hummus, Oliven und Fladenbrot',1,6,5,0,0,1),(133,'Penne mit Paprikasugo und Avocadocreme',7,4,3,0,0,1),(134,'Vegetarische Lasagne mit Salatbeilage',14,4,2,0,0,1),(135,'Linsen-Dattel-Salat mit Fladenbrot',14,7,5,0,0,1),(136,'Rote Bete-Kartoffel-Auflauf mit Schafskäse',14,7,2,0,0,1),(137,'gebratener grüner Spargel mit Parmesan-Polenta',14,4,2,0,0,1),(138,'Limettenquark mit Minzzucker',14,7,6,0,0,1),(139,'Spaghetti mit Rucola, Cocktailtomaten und Pinienkernen und Parmesan',14,4,3,0,0,1),(140,'Indonesische Erdnußsauce mit Tofu, Blumenkohl, Karotten und Zucchini',4,6,2,0,0,1),(141,'Couscous Marrakesch mit Kartoffel, Kichererbsen, Zucchini und Hokkaido',4,6,2,0,0,1),(142,'Sizilianisches Auberginen-Gemüse mit Salbei-Polenta',4,4,2,0,0,1),(143,'Auberginen-Gratin mit Spaghetti-Mozzarella-Füllung',4,4,3,0,0,1),(144,'Pasta mit Kürbis-Pesto und Cocktailtomaten',4,2,3,0,0,1),(145,'Dahl (indisches Gemüsegericht mit dreierlei Linsen)und Joghurt',4,6,2,0,0,1),(146,'Pasta mit Rucola, Oliven, Tomaten und Parmesan',4,4,3,0,0,1),(147,'Auberginenkroketten mit Mandeldip und Salatbeilage',4,4,2,0,0,1);
/*!40000 ALTER TABLE `menue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menue_has_fussnote`
--

DROP TABLE IF EXISTS `menue_has_fussnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menue_has_fussnote` (
  `menue_fk` int(11) NOT NULL,
  `fussnote_fk` int(11) NOT NULL,
  KEY `fk_menue_has_fussnote_menue1_idx` (`menue_fk`),
  KEY `fk_menue_has_fussnote_fussnote1_idx` (`fussnote_fk`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menue_has_fussnote`
--

LOCK TABLES `menue_has_fussnote` WRITE;
/*!40000 ALTER TABLE `menue_has_fussnote` DISABLE KEYS */;
INSERT INTO `menue_has_fussnote` VALUES (7,8),(7,2),(9,1),(9,8),(8,10),(13,1),(13,8),(14,8),(14,10),(17,2),(17,8),(17,10),(18,2),(18,4),(18,6),(20,4),(21,1),(21,8),(24,2),(25,4),(25,8),(27,6),(28,4),(29,3),(29,11),(30,4),(30,8),(31,4),(34,1),(35,4),(36,6),(37,4),(37,8),(39,4),(39,6),(40,4),(40,6),(40,8),(41,6),(42,6),(44,2),(44,8),(44,11),(45,4),(46,6),(46,8),(47,4),(47,8),(52,4),(52,8),(53,2),(53,10),(54,2),(55,11),(56,8),(56,11),(58,8),(59,10),(60,8),(62,4),(64,1),(64,8),(66,8),(66,11),(67,1),(67,8),(69,4),(69,10),(71,8),(74,4),(74,6),(75,8),(75,10),(76,2),(79,1),(81,2),(81,4),(81,6),(81,8),(81,10),(82,1),(82,4),(82,6),(82,8),(82,10),(83,2),(83,8),(83,11),(84,1),(84,10),(84,8),(85,8),(86,2),(86,11),(87,2),(87,11),(89,8),(89,10),(90,4),(92,8),(93,8),(94,4),(94,8),(15,4),(19,4),(95,1),(95,8),(96,10),(96,8),(97,8),(98,10),(98,4),(99,4),(100,4),(100,8),(102,8),(103,4),(104,4),(105,1),(106,1),(106,8),(107,1),(108,4),(108,5),(110,4),(113,4),(113,6),(113,8),(113,10),(114,6),(114,4),(114,10),(115,10),(118,4),(118,8),(119,8),(120,10),(121,10),(121,4),(122,8),(123,3),(123,10),(123,8),(125,4),(125,8),(126,6),(126,8),(127,8),(130,8),(130,10),(131,2),(131,10),(131,8),(132,1),(136,8),(136,10),(137,8),(137,4),(139,2),(135,10),(135,8),(135,6),(135,1),(140,1),(140,8),(140,10),(141,1),(141,10),(145,2),(145,8),(146,2),(146,4),(146,10),(147,4),(43,3),(43,4),(43,5);
/*!40000 ALTER TABLE `menue_has_fussnote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menue_has_rezept`
--

DROP TABLE IF EXISTS `menue_has_rezept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menue_has_rezept` (
  `menue_id` int(11) NOT NULL,
  `rezept_id` int(11) NOT NULL,
  `hauptgericht` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`menue_id`,`rezept_id`),
  KEY `fk_menue_has_rezept_rezept1_idx` (`rezept_id`),
  KEY `fk_menue_has_rezept_menue1_idx` (`menue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menue_has_rezept`
--

LOCK TABLES `menue_has_rezept` WRITE;
/*!40000 ALTER TABLE `menue_has_rezept` DISABLE KEYS */;
INSERT INTO `menue_has_rezept` VALUES (7,4,1),(7,5,0),(7,6,0),(8,2,NULL),(8,7,NULL),(8,8,NULL),(9,4,1),(9,5,0),(10,10,NULL),(11,2,NULL),(11,6,NULL),(11,12,NULL),(12,1,NULL),(12,2,NULL),(13,14,NULL),(14,16,NULL),(15,15,NULL),(16,28,NULL),(17,33,NULL),(18,32,NULL),(19,30,NULL),(20,34,NULL),(20,35,NULL),(21,36,NULL),(22,42,NULL),(23,38,NULL),(24,44,NULL),(25,45,NULL),(26,47,NULL),(27,48,NULL),(28,49,NULL),(29,24,NULL),(30,50,NULL),(30,51,NULL),(31,34,NULL),(31,35,NULL),(31,50,NULL),(32,56,NULL),(33,57,NULL),(34,64,NULL),(35,68,NULL),(36,69,NULL),(37,70,NULL),(38,71,NULL),(39,72,NULL),(40,69,NULL),(40,73,NULL),(41,73,NULL),(41,74,NULL),(42,73,NULL),(42,74,NULL),(43,76,NULL),(44,21,NULL),(44,79,NULL),(45,50,NULL),(45,80,NULL),(46,83,NULL),(47,85,NULL),(48,87,NULL),(48,89,NULL),(49,91,NULL),(50,93,NULL),(51,95,NULL),(52,99,NULL),(53,101,NULL),(54,102,NULL),(55,105,NULL),(56,108,NULL),(57,114,NULL),(58,115,NULL),(59,116,NULL),(60,118,NULL),(61,121,NULL),(62,122,NULL),(63,124,NULL),(64,126,NULL),(65,127,NULL),(66,106,NULL),(67,14,NULL),(68,12,NULL),(69,131,NULL),(70,132,NULL),(71,133,NULL),(72,134,NULL),(73,135,NULL),(74,61,NULL),(75,136,NULL),(76,142,NULL),(77,139,NULL),(78,137,NULL),(79,140,NULL),(80,147,NULL),(80,149,NULL),(81,151,NULL),(82,152,NULL),(83,18,NULL),(83,147,NULL),(84,22,NULL),(84,155,NULL),(85,50,NULL),(85,156,NULL),(86,144,NULL),(86,145,NULL),(87,144,NULL),(87,145,NULL),(88,103,NULL),(89,159,NULL),(90,160,NULL),(91,161,NULL),(92,163,NULL),(93,162,NULL),(94,164,NULL),(95,147,NULL),(95,167,NULL),(96,169,NULL),(97,171,NULL),(98,172,NULL),(99,173,NULL),(100,175,NULL),(101,174,NULL),(102,171,NULL),(103,176,NULL),(104,177,NULL),(105,178,NULL),(106,179,NULL),(107,180,NULL),(108,181,NULL),(109,181,NULL),(110,182,NULL),(111,183,NULL),(112,184,NULL),(113,185,NULL),(114,186,NULL),(115,187,NULL),(116,188,NULL),(117,189,NULL),(118,190,NULL),(119,191,NULL),(120,192,NULL),(121,193,NULL),(122,194,NULL),(123,116,NULL),(124,195,NULL),(125,196,NULL),(126,197,NULL),(127,201,NULL),(128,202,NULL),(129,200,NULL),(130,198,NULL),(131,199,NULL),(132,203,NULL),(133,204,NULL),(134,207,NULL),(135,206,NULL),(136,209,NULL),(137,208,NULL),(138,210,NULL),(139,211,NULL),(140,213,NULL),(141,214,NULL),(142,215,NULL),(143,216,NULL),(144,217,NULL),(145,218,NULL),(146,219,NULL),(147,18,NULL),(147,50,NULL);
/*!40000 ALTER TABLE `menue_has_rezept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menueart`
--

DROP TABLE IF EXISTS `menueart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menueart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menueart`
--

LOCK TABLES `menueart` WRITE;
/*!40000 ALTER TABLE `menueart` DISABLE KEYS */;
INSERT INTO `menueart` VALUES (6,'Dessert\r'),(1,'Fleischgericht\r'),(2,'Hauptgericht\r'),(3,'Pasta\r'),(5,'Salat\r'),(4,'Suppe\r'),(7,'unbekannt\r');
/*!40000 ALTER TABLE `menueart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menueplan`
--

DROP TABLE IF EXISTS `menueplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menueplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `week` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `freigegeben` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menueplan`
--

LOCK TABLES `menueplan` WRITE;
/*!40000 ALTER TABLE `menueplan` DISABLE KEYS */;
INSERT INTO `menueplan` VALUES (1,27,2013,1),(2,26,2013,1),(3,28,2013,1),(4,29,2013,1),(5,30,2013,1),(6,31,2013,1),(7,32,2013,1),(8,33,2013,1),(9,34,2013,1),(10,35,2013,0),(11,36,2013,NULL),(12,37,2013,1),(13,38,2013,1),(14,39,2013,1),(15,40,2013,NULL),(16,41,2013,NULL),(17,42,2013,NULL);
/*!40000 ALTER TABLE `menueplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menueplan_has_koeche`
--

DROP TABLE IF EXISTS `menueplan_has_koeche`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menueplan_has_koeche` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menueplan` int(11) NOT NULL,
  `spalte` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `koch` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menueplan_has_koch_menue` (`menueplan`),
  KEY `fk_menueplan_has_koch_koch1` (`koch`)
) ENGINE=InnoDB AUTO_INCREMENT=3301 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menueplan_has_koeche`
--

LOCK TABLES `menueplan_has_koeche` WRITE;
/*!40000 ALTER TABLE `menueplan_has_koeche` DISABLE KEYS */;
INSERT INTO `menueplan_has_koeche` VALUES (4,1,1,1,5),(5,1,1,2,2),(6,1,3,2,2),(403,4,1,1,6),(404,4,1,2,14),(405,4,2,1,4),(406,4,2,2,5),(407,4,3,1,3),(408,4,3,2,5),(409,4,4,1,4),(410,4,4,2,6),(411,4,5,1,3),(412,4,5,2,1),(1249,5,1,1,3),(1250,5,1,2,6),(1251,5,2,1,5),(1252,5,2,2,6),(1253,5,3,1,5),(1254,5,3,2,7),(1255,5,4,1,3),(1256,5,4,2,7),(1257,5,5,1,1),(1258,5,5,2,4),(1561,6,1,1,3),(1562,6,1,2,6),(1563,6,2,1,4),(1564,6,2,2,6),(1565,6,3,1,6),(1566,6,3,2,5),(1567,6,4,1,4),(1568,6,4,2,7),(1569,6,5,1,7),(1570,6,5,2,3),(1715,7,1,1,14),(1716,7,1,2,6),(1717,7,2,1,8),(1718,7,2,2,4),(1719,7,3,1,5),(1720,7,3,2,6),(1721,7,4,1,1),(1722,7,4,2,8),(1723,7,5,1,5),(1724,7,5,2,6),(1775,8,1,1,4),(1776,8,1,2,8),(1777,8,2,1,8),(1778,8,2,2,5),(1779,8,3,1,6),(1780,8,3,2,14),(1781,8,4,1,6),(1782,8,4,2,14),(1783,8,5,1,6),(1784,8,5,2,1),(2000,9,1,1,4),(2001,9,1,2,14),(2002,9,2,1,6),(2003,9,2,2,5),(2004,9,3,1,5),(2005,9,3,2,14),(2006,9,4,1,6),(2007,9,4,2,4),(2008,9,5,1,5),(2009,9,5,2,6),(2180,10,1,1,6),(2181,10,1,2,14),(2182,10,2,1,4),(2183,10,2,2,5),(2184,10,3,1,5),(2185,10,3,2,6),(2186,10,4,1,6),(2187,10,4,2,9),(2188,10,5,1,5),(2189,10,5,2,4),(2450,11,1,1,4),(2451,11,1,2,14),(2452,11,2,1,2),(2453,11,2,2,14),(2454,11,3,1,4),(2455,11,3,2,9),(2456,11,4,1,3),(2457,11,4,2,9),(2458,11,5,1,3),(2459,11,5,2,4),(2874,12,1,1,6),(2875,12,1,2,3),(2876,12,2,1,4),(2877,12,2,2,5),(2878,12,3,1,5),(2879,12,3,2,6),(2880,12,4,1,4),(2881,12,4,2,1),(2882,12,5,1,3),(2883,12,5,2,6),(3174,13,1,1,6),(3175,13,1,2,9),(3176,13,2,1,6),(3177,13,2,2,5),(3178,13,3,1,3),(3179,13,3,2,5),(3180,13,4,1,5),(3181,13,4,2,3),(3182,13,5,1,3),(3183,13,5,2,1),(3280,15,3,1,5),(3281,15,3,2,6),(3282,15,5,1,2),(3283,14,1,1,1),(3284,14,1,2,8),(3285,14,2,1,5),(3286,14,2,2,6),(3287,14,3,1,3),(3288,14,3,2,6),(3289,14,4,1,3),(3290,14,4,2,14),(3291,14,5,1,3),(3292,14,5,2,5),(3297,16,2,1,5),(3298,16,2,2,6),(3299,16,4,1,6),(3300,16,4,2,4);
/*!40000 ALTER TABLE `menueplan_has_koeche` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menueplan_has_menues`
--

DROP TABLE IF EXISTS `menueplan_has_menues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menueplan_has_menues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menueplan` int(11) NOT NULL,
  `menue` int(11) NOT NULL,
  `angezName` varchar(200) NOT NULL,
  `spalte` int(11) NOT NULL,
  `zeile` int(11) NOT NULL,
  `portion` int(11) NOT NULL,
  `fussnote` varchar(45) NOT NULL DEFAULT '""',
  PRIMARY KEY (`id`),
  KEY `fk_menueplan_has_menue_menueplan` (`menueplan`),
  KEY `fk_menueplan_has_menue_menue` (`menue`)
) ENGINE=InnoDB AUTO_INCREMENT=6579 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menueplan_has_menues`
--

LOCK TABLES `menueplan_has_menues` WRITE;
/*!40000 ALTER TABLE `menueplan_has_menues` DISABLE KEYS */;
INSERT INTO `menueplan_has_menues` VALUES (3,1,8,'Schweinebraten mit Spätzle und Salat',1,3,100,'\"\"'),(4,1,7,'Persischer Spinatreis mit Hummus und Walnussjoghurt',2,3,100,'\"\"'),(9,2,8,'Schweinebraten mit Spätzle und Salat',1,2,100,'\"\"'),(10,2,11,'Lasagne mit Salat und Dessert',2,3,100,'\"\"'),(11,2,7,'Persischer Spinatreis mit Hummus und Walnussjoghurt',3,4,100,'\"\"'),(12,2,10,'Erbsensuppe',5,6,100,'\"\"'),(635,4,42,'Pute in Calvadosrahm mit Kartoffelbrei und Salatbeilage',1,2,100,'\"\"'),(636,4,27,'Lauch-Gorgonzola-Birnenquiche mit Salatbeilage',1,3,100,'\"\"'),(637,4,56,'Pikantes Paprikagemüse mit Pfirsichen, dazu Avorioreis und Joghurt',1,4,100,'\"\"'),(638,4,55,'Penne alla puttanesca mit Parmesan',1,5,100,'\"\"'),(639,4,65,'Bunte Blattsalate mit gratiniertem Ziegenkäse mit Thymian, Honig und Walnüssen',1,6,100,'\"\"'),(640,4,38,'Mousse au chocolat',1,7,100,'\"\"'),(641,4,57,'Rindergulasch mit Bandnudeln und Salat',2,2,100,'\"\"'),(642,4,30,'Kartoffelauflauf mit Cabanossi und Ei dazu Salatbeilage',2,3,100,'\"\"'),(643,4,61,'Gemüse-Couscous (mit Auberginen, Zucchini, Karotten) und Minz-Joghurt',2,4,100,'\"\"'),(644,4,28,'Spirelli mit Kräuterpesto, Karotten, Cocktailtomaten und Parmesan',2,5,100,'\"\"'),(645,4,59,'Salatteller mit gebratenen Lachswürfeln',2,6,100,'\"\"'),(646,4,32,'Gebackene Bananen mit Ahornsirup und Sahne',2,7,100,'\"\"'),(647,4,58,'Libanesisches Joghurthühnchen mit Reis, Pinienkernen, Rosinen und Karotten',3,2,100,'\"\"'),(648,4,46,'Matjessalat Hausfrauenart mit Bratkartoffeln',3,3,100,'\"\"'),(649,4,47,'Risotto mit Pfifferlingen, Parmesan und Rucola',3,4,100,'\"\"'),(650,4,62,'Rigatoni mit Süßkartoffel-Wanusspesto und Ricotta',3,5,100,'\"\"'),(651,4,60,'Panzanella (Toskanischer Brotsalat mit Paprika, Oliven, Basilikum und Sardellen)',3,6,100,'\"\"'),(652,4,50,'Himbeerparfait',3,7,100,'\"\"'),(653,4,22,'Hackfleischlasagne mit Salatbeilage',4,2,100,'\"\"'),(654,4,45,'Kräuterpfannkuchen mit Kartoffel-Mangold-Füllung und Salatbeilage',4,3,100,'\"\"'),(655,4,31,'Gnocci mit Mandel-Petersilienpesto, Parmesan und Salatbeilage',4,4,100,'\"\"'),(656,4,34,'Aprikosen-Linsencurry mit Basmatireis',4,5,100,'\"\"'),(657,4,37,'Salatteller mit Melone und Serranoschinken',4,6,100,'\"\"'),(658,4,33,'Apfel-Schmand-Dessert mit Krokant',4,7,100,'\"\"'),(659,4,51,'Schweinebraten mit Spätzle, Salbeisosse und Karottengemüse',5,2,100,'\"\"'),(660,4,52,'Kartoffel-Kohlrabigratin mit Salatbeilage',5,3,100,'\"\"'),(661,4,64,'grünes Thaicurry mit Cashewnüssen, Duftreis und Thaibasilikum',5,4,100,'\"\"'),(662,4,54,'Pasta mit Fenchel-Rosinen-Chili-Tomatensugo und Parmesan',5,5,100,'\"\"'),(663,4,69,'Salatteller mit  Austernpilzen im Parmesanmantel und Kräuterdip',5,6,100,'\"\"'),(664,4,63,'gratinierte Erdbeeren mit Marzipan',5,7,100,'\"\"'),(2211,5,36,'Schweinebalsamicoragout mit geschmortem Lauch und Kartoffelselleriepüree',1,2,100,'\"\"'),(2212,5,16,'Orientalisches Gemüse mit Bulgur und Currydip',1,3,100,'\"\"'),(2213,5,103,'Calamares mit Zitronenajoli, Farmerkartoffeln und Salatbeilage',1,4,100,'\"\"'),(2214,5,104,'Maccaroncini mit Gorgonzolasauce und karamelisierten Birnen',1,5,100,'\"\"'),(2215,5,121,'Salatteller mit gebratenen Auberginen und grünem Mojo',1,6,100,'\"\"'),(2216,5,77,'New York Cheesecake mit Orangensauce',1,7,100,'\"\"'),(2217,5,22,'Hackfleischlasagne mit Salatbeilage',2,2,100,'\"\"'),(2218,5,92,'Steinpilzrisotto mit Salatbeilage',2,3,100,'\"\"'),(2219,5,93,'Zucchini-Kartoffelgratin mit gegrillter Kräutertomate und Salatbeilage',2,4,100,'\"\"'),(2220,5,39,'Pasta mit Zitronenbutter, Basilikum und Parmesan',2,5,100,'\"\"'),(2221,5,37,'Salatteller mit Ziegenkäse im Speckmantel mit Salat   ',2,6,100,'\"\"'),(2222,5,88,'Mangocreme mit Karamellkruste',2,7,100,'\"\"'),(2223,5,91,'Mexikanisches Hühnchen mit Paprika, Chili, Koriander u. Schokolade im Tortillafladen mit  Avocado-Tomatensalat ',3,2,100,'\"\"'),(2224,5,98,'Karotten-Aprikosenbällchen mit mariniertem Fenchel in Orangensoße mit Cous-cous',3,3,100,'\"\"'),(2225,5,99,'Lachs-Spinat-Lasagne mit Salatbeilage',3,4,100,'\"\"'),(2226,5,90,'Spaghetti mit Rucolapesto, geschmorten Tomatenwürfeln und Parmesan ',3,5,100,'\"\"'),(2227,5,89,'Auberginensalat  \"La Tamu\" auf Blattsalat ',3,6,100,'\"\"'),(2228,5,101,'Beerenscrumble mit Vanilleeis',3,7,100,'\"\"'),(2229,5,96,'Trollingerbraten mit Pommes Anna und Bohnengemüse',4,2,100,'\"\"'),(2230,5,102,'Gefüllte Zuchini mit Kräuterhirse, Tomatensoße und Parmesan auf Rucolabett',4,3,100,'\"\"'),(2231,5,106,'Pealla Verdura mit Oliven',4,4,100,'\"\"'),(2232,5,107,'Penne mit Papaya-Chilli-Kokossosse',4,5,100,'\"\"'),(2233,5,100,'Blattsalate mit gratinierten, gefüllten Champignons',4,6,100,'\"\"'),(2234,5,108,'Blätterteigtaschen mit Aprikosen und Ricotta',4,7,100,'\"\"'),(2235,5,71,'Merguez mit Farmerkartoffeln, Avocadodip und Tomatensalat',5,2,100,'\"\"'),(2236,5,70,'Semmelknödel mit Pfifferlingrahm und Rucola',5,3,100,'\"\"'),(2237,5,80,'Kichererbsen mit Spinat, Kräuter und Frischkäse dazu Basmatireis',5,4,100,'\"\"'),(2238,5,29,'Veggie-Bolognese mit Spaghetti (Tofugeschnetzeltes mit Gemüse )',5,5,100,'\"\"'),(2239,5,82,'Rote Bete- Birnen-Salat mit Walnüssen',5,6,100,'\"\"'),(2240,5,72,'Griechischer Joghurt mit Trauben, Walnüssen und Honig',5,7,100,'\"\"'),(2837,6,114,'Paniertes Schweineschnitzel mit Kartoffel-Gurkensalat',1,2,100,'\"\"'),(2838,6,79,'pikantes Gemüsecurry mit Basmatireis',1,3,100,'\"\"'),(2839,6,115,'Spinatschafskäse-Strudel mit Salatbeilage',1,4,100,'\"\"'),(2840,6,39,'Penne alla panna',1,5,100,'\"\"'),(2841,6,13,'Thailändischer Glasnudelsalat',1,6,100,'\"\"'),(2842,6,116,'Creme caramel',1,7,100,'\"\"'),(2843,6,41,'Pute in Calvadospfefferrahm, Bandnudeln und kleinem Salat',2,2,100,'\"\"'),(2844,6,75,'Kartoffeltortilla mit rotem Mojo und Salatbeilage',2,3,100,'\"\"'),(2845,6,31,'Kohlrabi-Schnitzel mit Käsefüllung, Kartoffelpüree und Raukesalat',2,4,100,'\"\"'),(2846,6,18,'Spirelli in Zitronen-Olivenöl mit Walnüssen, Petersilie, Cocktailtomaten und Parmesan',2,5,100,'\"\"'),(2847,6,113,'Salatteller mit Schafskäsecreme dazu Kirschtomaten und Pinienkerne',2,6,100,'\"\"'),(2848,6,38,'Mousse au chocolat',2,7,100,'\"\"'),(2849,6,124,'Rinder-Tajin mit getr. Aprikosen und Pflaumen, Couscous und Karotten',3,2,100,'\"\"'),(2850,6,27,'Lauch-Gorgonzola-Birnenquiche mit Salatbeilage',3,3,100,'\"\"'),(2851,6,125,'Pellkartoffeln mit Kräuterquark und Salatbeilage',3,4,100,'\"\"'),(2852,6,24,'Spaghetti Arrabiata mit Gemüsestreifen und Parmesan',3,5,100,'\"\"'),(2853,6,123,'Nizza-Salat mit Bio-Thunfisch, Bohnen und Oliven',3,6,100,'\"\"'),(2854,6,73,'Himbeer-Limetten-Tiramisu',3,7,100,'\"\"'),(2855,6,129,'Zitronenhühnchen mit Oliven, mediteranes Gemüse und Reis',4,2,100,'\"\"'),(2856,6,127,'Süßkartoffelgratin mit Tomatensugo und Salatbeilage',4,3,100,'\"\"'),(2857,6,95,'Curry Masala (Tofu mit Brokkoli, Kartoffel, Karotten und Basmatireis)',4,4,100,'\"\"'),(2858,6,19,'Linguine mit Basilikum-Honig-Sahne-Sauce und Chili',4,5,100,'\"\"'),(2859,6,130,'Blattsalate mit marinierten Austernpilzen',4,6,100,'\"\"'),(2860,6,112,'Gefüllte Pfannkuchen mit Johannisbeeren und Karamellsauce',4,7,100,'\"\"'),(2861,6,117,'Pastizzio-Griechischer Hackfleischauflauf mit Macceroni,dazu Tomatensalat',5,2,100,'\"\"'),(2862,6,126,'Mattjes nach Hausfrauenart mit Bratkartoffeln',5,3,100,'\"\"'),(2863,6,119,'Grünkernpfannkuchen mit Pfifferlingen und Frühlingszwiebeln',5,4,100,'\"\"'),(2864,6,133,'Farfalle mit Paprikasugo und Avocadocreme',5,5,100,'\"\"'),(2865,6,105,'Tabouhleh',5,6,100,'\"\"'),(2866,6,128,'Beerengrütze mit Vanilleeis',5,7,100,'\"\"'),(3229,7,36,'gebackenes Hühnchen mit pikantem Erbsenpüree und Farmerkartoffeln',1,2,100,'\"\"'),(3230,7,64,'grünes Thaicurry mit Cashewnüssen, Basmatireis und Thaibasilikum',1,3,100,'\"\"'),(3231,7,34,'Blätterteigstrudel mit Gemüsekäsefüllung und Salatbeilage                   ',1,4,100,'\"\"'),(3232,7,54,'Pasta mit Fenchel-Chili-Tomatensugo und Parmesan',1,5,100,'\"\"'),(3233,7,37,'Gazpacho mit Knoblauchbrot',1,6,100,'\"\"'),(3234,7,63,'Birne Helene',1,7,100,'\"\"'),(3235,7,68,'Toskanischer  Rinderbraten mit Rosmarin, Gnocchi und Schmorgemüse',2,2,100,'\"\"'),(3236,7,70,'Wermut-Risotto mit gebratenen Shitakepilzen und Parmesan, dazu Salatbeilage',2,3,100,'\"\"'),(3237,7,85,'Kartoffelgratin mit Knollensellerie, Lauch und Thymian dazu Salatbeilage',2,4,100,'\"\"'),(3238,7,87,'Spirelli  mit Kichererbsen Tomatensauce, Gomasio und Joghurt',2,5,100,'\"\"'),(3239,7,94,'Fenchel-Mozzarella-Salat mit Johannisbeersoße',2,6,100,'\"\"'),(3240,7,63,'Creme fraiche-Cassis-Dessert',2,7,100,'\"\"'),(3241,7,124,'Schweineragout mit Ingwer, Honig und Calvados, weicher Polenta und Fenchel',3,2,100,'\"\"'),(3242,7,75,'Spinatkartoffelauflauf mit Salatbeilage',3,3,100,'\"\"'),(3243,7,70,'Pfannkuchen mit Kohlrabi mit/ ohne Schinken',3,4,100,'\"\"'),(3244,7,24,'Tagliatelle mit frischem Lachs in Sahnesauce',3,5,100,'\"\"'),(3245,7,132,'Salatteller mit warmem Ziegenkäse mit Honig &Thymian',3,6,100,'\"\"'),(3246,7,77,'New York Cheesecake mit Zitroneneis und Früchten',3,7,100,'\"\"'),(3247,7,11,'Tagliatelle mit Lammhacksugo, mediterranem Gemüse und Parmesan',4,2,100,'\"\"'),(3248,7,64,'Pilz-Strudel mit Pfifferlingen, Champignons, Tomate und Ziegenkäse, dazu Salatbeilage',4,3,100,'\"\"'),(3249,7,11,'Auberginen-Süßkartoffelcurry mit frischem Koriander, Cashewnüssen und Basmatireis',4,4,100,'\"\"'),(3250,7,54,'überbackene Kartoffeln mit Kräuterquark und Feldsalat',4,5,100,'\"\"'),(3251,7,26,'Salatteller mit karamellisierter roter Bete, Mozzarella, gerösteten Kürbiskernen und Orangen-Kürbiskern-Vinaigrette',4,6,100,'\"\"'),(3252,7,63,'Heidelbeerpfannkuchen mit  Sahne und Ahornsirup',4,7,100,'\"\"'),(3253,7,68,'Putengeschnetzeltes mit Pfifferlingen und Spätzle',5,2,100,'\"\"'),(3254,7,78,'Vegetarische Gemüselasagne mit Ricotta',5,3,100,'\"\"'),(3255,7,76,'Ratatouille mit Reis und Minzjoghurt',5,4,100,'\"\"'),(3256,7,54,'Gnocchi in Salbeibutter mit Parmesan, dazu Salatbeilage',5,5,100,'\"\"'),(3257,7,132,'Salatteller mit gebackenem Schafskäse und Oliven',5,6,100,'\"\"'),(3258,7,73,'Überbackener Pfirsich mit Schokoladeneis',5,7,100,'\"\"'),(3381,8,71,'Hühnchenbrust mit fruchtiger Rhabarber-Aprikosen-Salsa, gebackenen Kartoffelspalten und grünem Salat',1,2,100,'\"\"'),(3382,8,15,'Karotten-Pesto- Lasagne mit Pinienkerne und Salatbeilage',1,3,100,'\"\"'),(3383,8,44,'Kerbelreis auf Champignon-Bohnenragout und Cocktailtomaten',1,4,100,'\"\"'),(3384,8,54,'Linguine mit Spinat-Weißweinbutter und gerösteten Mandeln',1,5,100,'\"\"'),(3385,8,81,'Kartoffelsalat mit Zuckerschoten, Karotten und Kräutercreme',1,6,100,'\"\"'),(3386,8,63,'Vanille-Mousse mit Erdbeeren und Schoko-Mandel-Crossies',1,7,100,'\"\"'),(3387,8,68,'Chili-Pute mit Gemüse, Cashewkernen und Duftreis',2,2,100,'\"\"'),(3388,8,70,'gegrilltes Zucchini-Paprikagemüse mit Süßkartoffeln und Sourcream',2,3,100,'\"\"'),(3389,8,43,'Quiche Lorraine mit Salat',2,4,100,'\"\"'),(3390,8,90,'Farfalle mit Tomatensugo, gegrillten Auberginen, Basilikum und Parmesan',2,5,100,'\"\"'),(3391,8,132,'Salat mit Lachsfilet, Radieschen und Limetten-Vinaigrette',2,6,100,'\"\"'),(3392,8,88,'Gebackene Sahne mit Pfirsichen und Beeren',2,7,100,'\"\"'),(3393,8,36,'Rinderragout mit Bandnudeln und Buttererbsen',3,2,100,'\"\"'),(3394,8,78,'Kartoffeltortilla mit rotem Mojo und Rucolasalat',3,3,100,'\"\"'),(3395,8,66,'Pikantes Paprikagemüse mit Pfirsichen und Oliven, dazu Avorioreis und Joghurt',3,4,100,'\"\"'),(3396,8,134,'Vegetarische Lasagne mit Salatbeilage',3,5,100,'\"\"'),(3397,8,135,'Linsen-Dattel-Salat mit Fladenbrot',3,6,100,'\"\"'),(3398,8,77,'Tiramisu klassisch',3,7,100,'\"\"'),(3399,8,36,'Schweine-Kapernragout mit Kräuterbulgur und Karottengemüse',4,2,100,'\"\"'),(3400,8,70,'Semmelknödel mit Pfifferling-Pilzrahmsauce',4,3,100,'\"\"'),(3401,8,136,'Kichererbsen-Kartoffel-Curry mit Basmatireis',4,4,100,'\"\"'),(3402,8,139,'Spaghetti mit Rucola, Cocktailtomaten und Pinienkernen und Parmesan',4,5,100,'\"\"'),(3403,8,13,'Thailändischer Glasnudelsalat',4,6,100,'\"\"'),(3404,8,138,'Limettenquark mit Minzzucker',4,7,100,'\"\"'),(3405,8,36,'Hackfleischlasagne mit Salatbeilage',5,2,100,'\"\"'),(3406,8,70,'gebackener Schafskäse mit Zucchini, Paprika, Oliven, dazu Reis und Rucola',5,3,100,'\"\"'),(3407,8,64,'Räucherforelle mit Meerrettich, Kartoffelsalat  und Feldsalat',5,4,100,'\"\"'),(3408,8,39,'Spaghetti mit Tomaten-Paprikapesto und Parmesan',5,5,100,'\"\"'),(3409,8,132,'Salatteller mit Avocado, geröstete Sonnenblumenkernen und Kresse',5,6,100,'\"\"'),(3410,8,38,'hausgemachtes Aprikosenparfait',5,7,100,'\"\"'),(3790,9,26,'Bunte Gemüsepfanne mit Pute, dazu Basmatireis',1,2,100,'\"\"'),(3791,9,35,'Tomatenkuchen mit Rucolasalatbeilage',1,3,100,'\"\"'),(3792,9,85,'Griechischer Kartoffelauflauf mit Zucchini, Kräuter, Schafskäse und Salatbeilage',1,4,100,'\"\"'),(3793,9,44,'Rigatoni mit Linsen-Kapern-Sauce und Majoran',1,5,100,'\"\"'),(3794,9,132,'Salatteller mit Kichererbsencreme, Oliven und Fladenbrot',1,6,100,'\"\"'),(3795,9,33,'Pana Cotta mit Kirschen',1,7,100,'\"\"'),(3796,9,22,'pikanter Hackbraten mit Süßkartoffeln und Salatbeilage',2,2,100,'\"\"'),(3797,9,58,'Rotbarsch mit Kräuterkruste, Reis und Tomatensalat',2,3,100,'\"\"'),(3798,9,61,'Gemüse-Quiche mit Pesto Rosso und Schafskäse mit Salat',2,4,100,'\"\"'),(3799,9,24,'Spaghetti Arrabiata mit Gemüsestreifen und Parmesan',2,5,100,'\"\"'),(3800,9,59,'Mallorquinischer Sommersalat mit Pfirsich, Melone, Paprika, Tomate und Basilikum',2,6,100,'\"\"'),(3801,9,77,'New York Cheesecake mit Orangensauce',2,7,100,'\"\"'),(3802,9,122,'Hühnchen mit Ananas-Curry Risotto und Gemüse',3,2,100,'\"\"'),(3803,9,66,'Buntes Sommergemüse mit Couscous und Joghurt',3,3,100,'\"\"'),(3804,9,125,'Ofenkartoffeln mit Kräuterquark und Salatbeilage',3,4,100,'\"\"'),(3805,9,90,'Sapaghetti mit Rucolapesto und geschmorten Tomatenwürfeln und Parmesan ',3,5,100,'\"\"'),(3806,9,65,'Bunte Blattsalate mit gratiniertem Ziegenkäse mit Thymian, Honig und Walnüssen',3,6,100,'\"\"'),(3807,9,88,'Schokoladencreme mit Orangen-Pistazien Krokant',3,7,100,'\"\"'),(3808,9,42,'Rinderfeigenragout mit  gemischtem Gemüse und Kräuterbulgur',4,2,100,'\"\"'),(3809,9,85,'Maispfannkuchen mit Avokado-Tomaten Salsa und Salatbeilage',4,3,100,'\"\"'),(3810,9,34,'Aprikosen-Linsencurry mit Basmatireis',4,4,100,'\"\"'),(3811,9,31,'Penne mit Zucchini, Ricotta, Basilikum und Parmesan',4,5,100,'\"\"'),(3812,9,13,'Thailändischer Glasnudelsalat',4,6,100,'\"\"'),(3813,9,33,'Erdbeer-Rhabarber-Crumble mit Sahne',4,7,100,'\"\"'),(3814,9,57,'Lammkeule im Rotweinsud mit weicher Polenta und Bohnen',5,2,100,'\"\"'),(3815,9,93,'Zucchini-Kartoffelgratin mit gegrillter Kräutertomate und Salatbeilage',5,3,100,'\"\"'),(3816,9,79,'Gemüse-Risotto mit kleinem Salat',5,4,100,'\"\"'),(3817,9,24,'Penne a la panna',5,5,100,'\"\"'),(3818,9,60,'Salatteller mit gebratenem Zanderfilet ',5,6,100,'\"\"'),(3819,9,73,'Himberrlimettentiramisu',5,7,100,'\"\"'),(4269,10,36,'Rindergulasch mit Bandnudeln, Erbsenpüree und kleinem Salat',1,2,100,'\"\"'),(4270,10,76,'Kräuterbulgur mit orientalischem Gemüse und Currydip',1,3,100,'\"\"'),(4271,10,136,'Süßkartoffel-Kürbis-Auflauf mit Limettenquark und Salatbeilage',1,4,100,'\"\"'),(4272,10,55,'Penne alla puttanesca mit Parmesan',1,5,100,'\"\"'),(4273,10,37,'Ziegenkäse im Speckmantel mit Salat',1,6,100,'\"\"'),(4274,10,110,'Vanille-Quark-Creme mit Rhabarberkompott',1,7,100,'\"\"'),(4275,10,124,'Putengeschnetzeltes mit Pilzrahmsauce, Spätzle und Salat',2,2,100,'\"\"'),(4276,10,83,'Biryani ( Indischer Gemüsereis mit Rosinen, Cashew-Kerne und Joghurt )',2,3,100,'\"\"'),(4277,10,85,'Lachs mit Mandel-Thymian-Kruste, Ofenkartoffeln, Salat und Frischkäse-Dip',2,4,100,'\"\"'),(4278,10,90,'Farfalle mit sahniger Spinat-Roquefort Sauce',2,5,100,'\"\"'),(4279,10,59,'Salatteller mit Calamares und Zaziki',2,6,100,'\"\"'),(4280,10,32,'Gebackene Bananen mit Ahornsirup und Sahne',2,7,100,'\"\"'),(4281,10,57,'Italienischer Schweinebraten mit weicher Polenta und Schmorgemüse',3,2,100,'\"\"'),(4282,10,75,'Kartoffeltortilla mit rotem Mojo',3,3,100,'\"\"'),(4283,10,64,'Grünes Thaicurry mit, Basmatireis und Thaibasilikum',3,4,100,'\"\"'),(4284,10,39,'Pasta mit Zitronenbutter und Basilikum',3,5,100,'\"\"'),(4285,10,59,'Waldorfsalat mit Äpfeln, Staudensellerie und Walnüssen',3,6,100,'\"\"'),(4286,10,77,'New York Cheesecake mit Orangensauce',3,7,100,'\"\"'),(4287,10,22,'Hackfleischlasagne mit Salatbeilage',4,2,100,'\"\"'),(4288,10,25,'Ratatouille mit Süßkartoffeln',4,3,100,'\"\"'),(4289,10,34,'Aprikosen-Linsencurry mit Basmatireis',4,4,100,'\"\"'),(4290,10,24,'Spaghetti Arrabiata',4,5,100,'\"\"'),(4291,10,13,'Thailändischer Glasnudelsalat',4,6,100,'\"\"'),(4292,10,38,'Mousse au chocolat mit Früchten',4,7,100,'\"\"'),(4293,10,122,'Hühnchen mit Granatapfel-Walnuss-Sauce, Reis und Karotten',5,2,100,'\"\"'),(4294,10,44,'Gebackenes Sommergemüse mit Farmerkartoffel und Mandel-Frischkäse-Dip',5,3,100,'\"\"'),(4295,10,31,'Gebratene Gnocci in Salbeibutter, Parmesan und Salatbeilage',5,4,100,'\"\"'),(4296,10,90,'Rigatoni saltati mit Schinken, Erbsen und Champignons',5,5,100,'\"\"'),(4297,10,94,'Fenchel-Mozzarella-Salat mit Johannisbeersoße',5,6,100,'\"\"'),(4298,10,88,'Crème aramel',5,7,100,'\"\"'),(4773,11,68,'Pute mit Gemüsecurry und Reis',1,2,100,'\"\"'),(4774,11,30,'Kartoffelauflauf mit Cabanossi und Ei dazu Salatbeilage',1,3,100,'\"\"'),(4775,11,68,'Pfannkuchen mit Pilzfüllung und Salatbeilage',1,4,100,'\"\"'),(4776,11,68,'Linguine mit Basilikum-Honig-Sahnesoße und Chili',1,5,100,'\"\"'),(4777,11,68,'Linsen-Dattel-Salat mit Fladenbrot',1,6,100,'\"\"'),(4778,11,112,' Österreichische Aprikosenknödel ',1,7,100,'\"\"'),(4779,11,68,'Schweinebraten in Roquefortsoße mit Purree und Gemüse',2,2,100,'\"\"'),(4780,11,68,'Gefüllte Zucchini mit Kräuterhirse, Tomatensoße und Parmesan auf Rucolabett',2,3,100,'\"\"'),(4781,11,68,'Gemüsequiche mit Kürbis und Schafskäse, dazu Blattsalat',2,4,100,'\"\"'),(4782,11,68,'Pasta mit Garnelen und Cocktailtomaten',2,5,100,'\"\"'),(4783,11,132,'Salatteller mit Hummus, Oliven und frischem Koriander',2,6,100,'\"\"'),(4784,11,63,'Süsse Pfannkuchen mit Banane und Ahornsirup',2,7,100,'\"\"'),(4785,11,68,'Hackfleischküchle mit Kartoffel- und Gurkensalat',3,2,100,'\"\"'),(4786,11,95,'Curry Masala (Tofu mit Brokkoli, Kartoffel, Karotten und Basmatireis)',3,3,100,'\"\"'),(4787,11,68,'Linsen mit Spätzle m/o Wienerle',3,4,100,'\"\"'),(4788,11,19,'Pasta in Zitronen-Öl-Sauce mit Petersilie, Walnüsse, Cocktaoltomaten und Parmesan',3,5,100,'\"\"'),(4789,11,132,'Sommerlicher Nudelsalat mit Oliven, Mozzarella, getr. Tomaten und Basilikum',3,6,100,'\"\"'),(4790,11,110,'Buttermilchdessert mit karamelisierten Pflaumen',3,7,100,'\"\"'),(4791,11,68,'Rindsroulade mit breiten Nudeln und Karottengemüse',4,2,100,'\"\"'),(4792,11,68,'Pellkartoffeln mit Schnittlauchquark und Endivienasalat',4,3,100,'\"\"'),(4793,11,68,'Gemüseteller mit Quinoa',4,4,100,'\"\"'),(4794,11,132,'Pasta mit Paprikasugo und Avocadodip',4,5,100,'\"\"'),(4795,11,132,'Gebratene Auberginen und Zucchinis auf grünem Salat',4,6,100,'\"\"'),(4796,11,63,'Milchreis mit Apfelkompott und Zimtzucker',4,7,100,'\"\"'),(4797,11,68,'Leberkäs mit Spiegelei, Bratkartoffeln und Rote Bete-Salat',5,2,100,'\"\"'),(4798,11,84,'Frittierte Linsenbällchen mit Kartoffel-Aubergine-Curry und Mangochutney',5,3,100,'\"\"'),(4799,11,68,'Frischer Lachs in Creme-fraiche-Soße mit Reis und Gemüse',5,4,100,'\"\"'),(4800,11,31,'Spaghetti Siracusani (Zucchini, Kapern und Basilikum und Parmesan)',5,5,100,'\"\"'),(4801,11,94,'Salatteller mit gratinierten Hokkaidospalten',5,6,100,'\"\"'),(4802,11,63,'Wilde Hilde',5,7,100,'\"\"'),(5648,12,36,'Boeuf Bourgignion mit Semmelknödeln und kleinem Salat',1,2,100,'\"\"'),(5649,12,68,'Lachs mit Kräuterkruste, dazu Rieslingsoße, Salzkartoffeln und Gemüsebeilage',1,3,100,'\"\"'),(5650,12,68,'Kässpätzle mit Endiviensalat',1,4,100,'\"\"'),(5651,12,39,'Pasta mit Gorgonzolaspinatsauce und Cocktailtomaten',1,5,100,'\"\"'),(5652,12,132,'Salatteller mit Gemüse im Teigmantel',1,6,100,'\"\"'),(5653,12,77,'New York Cheesecake mit Orangensauce',1,7,100,'\"\"'),(5654,12,57,'Cordon Bleu von der Pute mit Ofenkartoffeln und Speckbohnen ',2,2,100,'\"\"'),(5655,12,140,'Indonesische Erdnußsauce mit Tofu, Blumenkohl, Karotten und Zucchini',2,3,100,'\"\"'),(5656,12,147,'Auberginenkroketten mit Mandeldip und Salatbeilage',2,4,100,'\"\"'),(5657,12,68,'Nudelauflauf mit Tomate, Zucchini, Karotten und Lauchzwiebeln',2,5,100,'\"\"'),(5658,12,123,'Nizza-Salat mit Bio-Thunfisch und Oliven',2,6,100,'\"\"'),(5659,12,33,'Kompott im Schlafrock mit Vanilleeis',2,7,100,'\"\"'),(5660,12,91,'Schweineragout mit Ingwer, Honig und Calvados mit weicherPolenta und Fenchel',3,2,100,'\"\"'),(5661,12,25,'Kartoffel-Käsegratin mit Salatbeilage',3,3,100,'\"\"'),(5662,12,75,'Safranpilzrisotto mit Rucolasalat',3,4,100,'\"\"'),(5663,12,90,'Sapaghetti mit Pesto Sicilliana ( Walnüsse und Ricotta)',3,5,100,'\"\"'),(5664,12,37,'Ziegenkäse im Speckmantel mit Salat',3,6,100,'\"\"'),(5665,12,88,'Mangocreme mit Karamellkruste',3,7,100,'\"\"'),(5666,12,71,'Merguez mit Farmerkartoffeln, Avocadodip und Tomatensalat',4,2,100,'\"\"'),(5667,12,141,'Couscous Marrakesch mit Kartoffel, Kichererbsen, Zucchini und Hokkaido',4,3,100,'\"\"'),(5668,12,68,'Eier in Senfsoße mit Gemüsepurree und Blattsalat',4,4,100,'\"\"'),(5669,12,146,'Pasta mit Rucola, Oliven, Tomaten und Parmesan',4,5,100,'\"\"'),(5670,12,111,'Salatteller mit Fenchel, Äpfel und Dattelsauce',4,6,100,'\"\"'),(5671,12,63,'Tarte tatin',4,7,100,'\"\"'),(5672,12,22,'Hackfleischlasagne mit Salatbeilage',5,2,100,'\"\"'),(5673,12,68,'Räucherforelle mit Kartoffelrösti, Meerettichsoße und Salatbeilage (W,K)',5,3,100,'\"\"'),(5674,12,34,'pikantes Gemüsecurry mit Basmatireis',5,4,100,'\"\"'),(5675,12,68,'Gnocchi mit Haselnuss-Maronensoße und Parmesan (W,Z)',5,5,100,'\"\"'),(5676,12,132,'Salat mit gefüllten Champignons',5,6,100,'\"\"'),(5677,12,38,'hausgemachtes Zwetschgensorbet mit Vanillesauce',5,7,100,'\"\"'),(6259,13,42,'Pute in Calvadosrahm mit Kartoffelbrei und Salatbeilage',1,2,100,'\"\"'),(6260,13,76,'Paprika mit Bulgur-Schafskäsefüllung mit Tomatensauce und Rucolasalat',1,3,100,'\"\"'),(6261,13,70,'Thaicurry mit Auberginen, Karotten und Champignons, dazu Basmatireis und frischer Koriander',1,4,100,'\"\"'),(6262,13,54,'Tagliatelle mit Räucherlachs-Sahnesauce und Cocktailtomaten',1,5,100,'\"\"'),(6263,13,63,'Lauchcremesuppe mit Haselnuss-Croutons',1,6,100,'\"\"'),(6264,13,63,'Creme brulée',1,7,100,'\"\"'),(6265,13,57,'Gyros vom Schwein mit  Ofenkartoffeln, Zaziki und Tomaten-Gurkensalat',2,2,100,'\"\"'),(6266,13,27,'Lauch-Gorgonzola-Birnenquiche mit Salatbeilage',2,3,100,'\"\"'),(6267,13,92,'Risotto Milanese Schmorgemüse und Blattsalatt',2,4,100,'\"\"'),(6268,13,24,'Spaghetti Arrabiata mit Gemüsestreifen und Parmesan',2,5,100,'\"\"'),(6269,13,13,'Kartoffelsuppe mit Speckwürfeln',2,6,100,'\"\"'),(6270,13,88,'Mississippi-Mud-Pie (Schokoladen-Dessertkuchen) mit Vanilleeis und Sahne',2,7,100,'\"\"'),(6271,13,124,'Rinderbraten mit Spätzle und Broccoli',3,2,100,'\"\"'),(6272,13,43,'Gemüse-Biryani mit Basmatireis und Raita (Joghurtdip)',3,3,100,'\"\"'),(6273,13,70,'Kartoffel-Kürbisgratin  und Salatbeilage',3,4,100,'\"\"'),(6274,13,26,'Pad Thai mit Sojasprossen, Gemüsestreifen und Erdnüssen',3,5,100,'\"\"'),(6275,13,132,'Salatteller mit Shrimps im Teigmantel',3,6,100,'\"\"'),(6276,13,88,'Kaiserschmarrn mit Rumrosinen, Mandeln und Apfelmus',3,7,100,'\"\"'),(6277,13,26,' Gefüllte Hähnchenschlegel-getr.Tomaten,Mozzarella,Oliven mit Tagliatelle und Gurkensalat',4,2,100,'\"\"'),(6278,13,125,'Sellerieschnitzel mit Kartoffelpürree und Schnittlauchsauce',4,3,100,'\"\"'),(6279,13,64,'Paelle de Verdura mit Oliven',4,4,100,'\"\"'),(6280,13,90,'Spaghetti mit Rucolapesto und geschmorten Tomatenwürfeln und Parmesan ',4,5,100,'\"\"'),(6281,13,59,'Gemüsebrühe mit Kräuterflädle',4,6,100,'\"\"'),(6282,13,63,'Kokos-Panna Cotta mit frischen Papaya',4,7,100,'\"\"'),(6283,13,26,'Serbisches Reisfleisch mit Krautsalat',5,2,100,'\"\"'),(6284,13,54,'Paniertes Rotbarschfilet mit Kartoffelsalat und Remoulade',5,3,100,'\"\"'),(6285,13,70,'Schupfnudeln mit Kürbis, Gorgonzolasauce und Rucola',5,4,100,'\"\"'),(6286,13,70,'Pasta al Annette (Grüne Bohnen, Basilikum, Kartoffelwürfel, Pinienkerne) mit Parmesan ',5,5,100,'\"\"'),(6287,13,132,'Herbstsalat mit gratiniertem Ziegenkäse, Äpfeln und Walnüssen',5,6,100,'\"\"'),(6288,13,63,'Ofenschlupfer mit Zimtzwetschgen',5,7,100,'\"\"'),(6539,15,36,'Rinderragout mit Kartoffelmöhrenpüree und Rosenkohl',3,2,100,'\"\"'),(6540,15,39,'Pasta mit Tomatennusspetersilienpesto und Parmesan',3,5,100,'\"\"'),(6541,15,77,'New York Cheesecake mit Orangensauce',3,7,100,'\"\"'),(6542,14,68,'Rinderragout mit Kürbis, Honig, Schafskäse u. frischer Minze, dazu Lauchzwiebel-Couscous ',1,2,100,'\"\"'),(6543,14,70,'Spätzle-Pilz-Lauchgratin mit Blattsalat',1,3,100,'\"\"'),(6544,14,70,'Möhren-Paprika-Risotto mit Parmesan und frischem Basilikum',1,4,100,'\"\"'),(6545,14,54,'Chili-Penne mit Thai-Pesto (Koriander, Ingwer, Erdnuss, Limette) und Parmesan',1,5,100,'\"\"'),(6546,14,64,'Kerbelrahmsuppe mit Räucherlachsstreifen',1,6,100,'\"\"'),(6547,14,63,'Espresso-Creme mit Zimtpflaumen',1,7,100,'\"\"'),(6548,14,57,'Schweinebraten in Dunkelbiersauce mit Knödel und Rahmkraut',2,2,100,'\"\"'),(6549,14,34,'Aprikosen-Linsencurry mit Basmatireis',2,3,100,'\"\"'),(6550,14,11,'KarottenZucchinibratlinge mit Kräuterquark und Salatbeilage',2,4,100,'\"\"'),(6551,14,90,'Farfalle mit Spinat-Frischkäsesauce',2,5,100,'\"\"'),(6552,14,132,'Wurzelgemüsesuppe mit Roter Beeteeinlage',2,6,100,'\"\"'),(6553,14,88,'Schokoladen-Panna Cotta mit Orangensauce',2,7,100,'\"\"'),(6554,14,22,'Hackfleischlasagne mit Salatbeilage',3,2,100,'\"\"'),(6555,14,70,'Kartoffelroulade mit Pilzfüllung, Parmesansahne und Feldsalat',3,3,100,'\"\"'),(6556,14,34,'Orientalisches Gemüse mit Kräuterbulgur und Joghurtdip',3,4,100,'\"\"'),(6557,14,70,'Tagliatelle mit Garnelen in Zitronensosse',3,5,100,'\"\"'),(6558,14,37,'Karotten-Apfel-Ingwersuppe',3,6,100,'\"\"'),(6559,14,63,'gebackenen Bananen in Mascarponemantel und Ahornsirup',3,7,100,'\"\"'),(6560,14,26,'Hähnchenbrust in Pilzrahmsosse mit Spätzle und Erbsen',4,2,100,'\"\"'),(6561,14,64,'Börek im Filoteig mit Spinat,Tomaten und Schafskäse mit Salatbeilage',4,3,100,'\"\"'),(6562,14,70,'Risotto mit Zucchini, Basilikum und Pinienkernen auf Rucola',4,4,100,'\"\"'),(6563,14,54,'Pasta mit Fenchel-Chili-Tomatensugo und Parmesan',4,5,100,'\"\"'),(6564,14,68,'Maronencremesuppe',4,6,100,'\"\"'),(6565,14,63,'Creme caramel mit Birnen',4,7,100,'\"\"'),(6566,14,26,'Sate Pute mit Erdnusssosse,Basmatireis und Chinakohlsalat',5,2,100,'\"\"'),(6567,14,61,'Edelfischfilets (Zander, Lachs)mit Nolly-Prat Sauce, Kartoffelgratin und Schwarzwurzeln',5,3,100,'\"\"'),(6568,14,70,'Falaffel mit Auberginenpürree,Knobijoghurt und Tomatensalat',5,4,100,'\"\"'),(6569,14,90,'Pasta mit weißen Bohnen, Tomaten, Rucola und Parmesan ',5,5,100,'\"\"'),(6570,14,132,'Süsskartoffelsuppe mit Kokosmilch',5,6,100,'\"\"'),(6571,14,88,'Grießflamieri mit Himbeersauce',5,7,100,'\"\"'),(6578,17,122,'Hühnchen mit Ananas-Cürry Risotto',1,2,100,' (W, T)');
/*!40000 ALTER TABLE `menueplan_has_menues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mitarbeiter`
--

DROP TABLE IF EXISTS `mitarbeiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mitarbeiter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `vorname` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `passwort` varchar(60) DEFAULT NULL,
  `eintrittsdatum` varchar(45) DEFAULT NULL,
  `austrittsdatum` varchar(45) DEFAULT NULL,
  `benutzername` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mitarbeiter`
--

LOCK TABLES `mitarbeiter` WRITE;
/*!40000 ALTER TABLE `mitarbeiter` DISABLE KEYS */;
INSERT INTO `mitarbeiter` VALUES (1,'Saur','Shanta','a.s.saur@web.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','Shanta'),(2,'Tolone','Bärbel','baerbeltolone@hotmail.com','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','Baerbel'),(3,'Eisele','Susu','susuhex@web.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.05.2003','','Susu'),(4,'Waltert','Yashoda','yashodawaltert@yahoo.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.01.1999','','Yashoda'),(5,'Klingler','Kai','KaiKlingler@googlemail.com','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.05.2010','','Kai'),(6,'Hugel','Hugi','hugi@wildewelt.com','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.02.2010','','Hugi'),(7,'Gemein','Claudia','claudia.gemein@web.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.01.2012','','Claudia'),(8,'Seither','Fabienne',' fabienne@target-concerts.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.07.2006','','Fabienne'),(9,'Gastkoch','Gastkoch','','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','Gastkoch'),(10,'Beck','Helen','helen.beck.mail@web.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','01.08.2012','','Helen'),(11,'Lutz','Susanne','susalu23@yahoo.co.uk','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','26.09.2011','','Susa'),(12,'alle','vorort','','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','alle'),(13,'Kujawa','Dorota','','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','Dorota'),(14,'Beyenbach','Anna','annabey@gmx.de','d61fba94cc7dce8d085c0ccebd75bcfb40148ebd','','','Anna B'),(15,'Grundbedarf','wöchentlicher','','7523cf6db0205a9cc135a22e81a19cebd1d26ec5','','','Grundbedarf');
/*!40000 ALTER TABLE `mitarbeiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mitarbeiter_has_rollen`
--

DROP TABLE IF EXISTS `mitarbeiter_has_rollen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mitarbeiter_has_rollen` (
  `mitarbeiter_fk` int(11) NOT NULL,
  `rollen_fk` int(11) NOT NULL,
  PRIMARY KEY (`mitarbeiter_fk`,`rollen_fk`),
  KEY `fk_mitarbeiter_has_rollen_rollen1_idx` (`rollen_fk`),
  KEY `fk_mitarbeiter_has_rollen_mitarbeiter_idx` (`mitarbeiter_fk`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mitarbeiter_has_rollen`
--

LOCK TABLES `mitarbeiter_has_rollen` WRITE;
/*!40000 ALTER TABLE `mitarbeiter_has_rollen` DISABLE KEYS */;
INSERT INTO `mitarbeiter_has_rollen` VALUES (1,1),(2,1),(11,1),(15,1),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(12,2),(14,2),(1,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(10,3),(11,3),(12,3),(13,3),(14,3),(3,4),(5,4),(13,4),(15,4);
/*!40000 ALTER TABLE `mitarbeiter_has_rollen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nachrichten`
--

DROP TABLE IF EXISTS `nachrichten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nachrichten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nachricht` varchar(300) DEFAULT NULL,
  `sender_fk` int(11) NOT NULL,
  `empf_rolle_fk` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_fk_idx` (`sender_fk`),
  KEY `empfaenger_fk_idx` (`empf_rolle_fk`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nachrichten`
--

LOCK TABLES `nachrichten` WRITE;
/*!40000 ALTER TABLE `nachrichten` DISABLE KEYS */;
INSERT INTO `nachrichten` VALUES (5,'Dies ist eine Nachricht an den Speiseplan Freigeber.',1,5),(7,'dfghjk',5,2);
/*!40000 ALTER TABLE `nachrichten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regel`
--

DROP TABLE IF EXISTS `regel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zeile` varchar(500) DEFAULT NULL,
  `spalte` varchar(500) DEFAULT NULL,
  `regeltyp` varchar(500) DEFAULT NULL,
  `operator` varchar(500) DEFAULT NULL,
  `kriterien` varchar(500) DEFAULT NULL,
  `fehlermeldung` varchar(500) DEFAULT NULL,
  `aktiv` tinyint(1) DEFAULT NULL,
  `ignorierbar` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regel`
--

LOCK TABLES `regel` WRITE;
/*!40000 ALTER TABLE `regel` DISABLE KEYS */;
INSERT INTO `regel` VALUES (1,'Fleischgericht','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Menüart','muss enthalten','Fleischgericht\r','In dieser Zeile düfen nur Fleischgerichte eingefügt werden!',1,0),(2,'Hauptgericht','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Menüart','muss enthalten','Hauptgericht\r','In dieser Zeile düfen nur Hauptgerichte eingefügt werden!',1,0),(3,'Pastagericht','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Menüart','muss enthalten','Pasta\r','In dieser Zeile düfen nur Pastagerichte eingefügt werden!',1,0),(4,'Suppe/Salat','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Menüart','muss enthalten','Salat\r, Suppe\r','In dieser Zeile dürfen nur Suppen und Salate eingefügt werden!',1,0),(5,'Dessert','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Menüart','muss enthalten','Dessert\r','In dieser Zeile dürfen nur Desserts eingefügt werden!',1,0),(6,'Hauptgericht','Donnerstag','Fußnote','muss enthalten','vegan','Ein Hauptgericht muss donnerstags ein veganes Gerichte sein!',1,1),(7,'Fleischgericht','Montag, Mittwoch, Freitag, Donnerstag, Dienstag','Geschmack','maximale Anzahl','1','In dieser Zeile dürfen keine Gerichte mit dem selben Geschmack eingefügt werden!',1,NULL),(8,'Hauptgericht','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Geschmack','maximale Anzahl','5','Der selbe Geschmack darf bei Hauptgerichten höchstens 5 mal eingefügt werden!',1,0),(9,'Pastagericht','Donnerstag, Mittwoch, Freitag, Dienstag, Montag','Geschmack','maximale Anzahl','2','In dieser Zeile dürfen nur zwei Gerichte mit dem selben Geschmack eingefügt werden!',1,1),(10,'Suppe/Salat','Montag, Dienstag, Mittwoch, Donnerstag, Freitag','Geschmack','maximale Anzahl','2','In dieser Zeile dürfen nur zwei Gerichte mit dem selben Geschmack eingefügt werden!',1,NULL),(11,'Fleischgericht, Hauptgericht, Pastagericht, Suppe/Salat, Dessert','Montag, Dienstag, Mittwoch, Donnerstag, Freitag','Menü','maximale Anzahl','1','Bitte keinen doppelten Menüs einfügen',1,0);
/*!40000 ALTER TABLE `regel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezept`
--

DROP TABLE IF EXISTS `rezept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `rezeptart_fk` int(11) NOT NULL,
  `kommentar` varchar(1000) DEFAULT NULL,
  `mitarbeiter_fk` int(11) DEFAULT NULL,
  `erstellt` timestamp NULL DEFAULT NULL,
  `aktiv` tinyint(1) DEFAULT NULL,
  `deaktivieren` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_rezept_rezeptart1_idx` (`rezeptart_fk`),
  KEY `fk_mitarbeiter_idx` (`mitarbeiter_fk`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezept`
--

LOCK TABLES `rezept` WRITE;
/*!40000 ALTER TABLE `rezept` DISABLE KEYS */;
INSERT INTO `rezept` VALUES (1,'Lasagne',1,'alles aufschichetn',1,'2013-05-15 22:00:00',1,0),(2,'gemischter Salat',2,'alles vermengen und als Beilage servieren',1,'2013-05-15 22:00:00',1,0),(3,'gemischter italienischer Salat',2,'',1,'2013-05-15 22:00:00',1,0),(4,'Persischer Spinatreis',2,'',1,'2013-06-19 22:00:00',1,0),(5,'Hummus',2,'',1,'2013-05-15 22:00:00',1,0),(6,'Walnussjoghurt',2,'',1,'2013-05-15 22:00:00',1,0),(7,'Schweinebraten',1,'',2,'2013-05-15 22:00:00',1,0),(8,'Spätzle',2,'',2,'2013-06-19 22:00:00',1,0),(9,'Pfannkuchen mit Kohlrabi',2,'',2,'2013-05-31 22:00:00',1,0),(10,'Erbsensuppe',1,'',2,'2013-06-19 22:00:00',1,0),(11,'Schweinebraten',2,'',2,'2013-05-31 22:00:00',1,0),(12,'Lasagne',1,'alles aufschichetn',1,'2013-06-19 22:00:00',1,0),(13,'Pfannkuchen mit Kohlrabi',2,'',2,'2013-06-19 22:00:00',1,0),(14,'Thailändischer Glasnudelsalat',1,'',6,'2013-06-29 22:00:00',1,0),(15,'Karotten Lasagne',1,'',4,'2013-06-28 22:00:00',1,0),(16,'Saté Pute mit Erdnusssoße, Basmatireis, Chinakohlsalat',1,'Pute marinieren',3,'2013-06-25 22:00:00',1,0),(17,'Saté Pute mit Erdnusssoße, Basmatireis, Chinakohlsalat',1,'Pute marinieren',3,'2013-06-25 22:00:00',0,0),(18,'Biryani',1,'indisch',4,'2013-06-28 22:00:00',1,0),(19,'Mousse au chocolat',1,'',6,'2013-06-28 22:00:00',1,0),(20,'Kartoffelbrei',2,'',6,'2013-06-28 22:00:00',1,0),(21,'Gebackenes Sommergemüse mit Mandel-Frischkäse-Dip',1,'',4,'2013-07-07 22:00:00',1,0),(22,'Kartoffel-Auberginen-Curry',2,'',4,'2013-06-28 22:00:00',1,0),(23,'Kichererbsen-Frikadellen',2,'',4,'2013-06-28 22:00:00',1,0),(24,'Veggie-Bolognese mit Spaghetti',1,'Testphase',4,'2013-06-28 22:00:00',1,0),(25,'Blumenkohl mit Tomaten und Schafskäse dazu Basmatireis',1,'',4,'2013-06-28 22:00:00',1,0),(26,'Lauch-Gorgonzola-Birnenquiche',1,'',6,'2013-06-29 22:00:00',1,0),(27,'Apfel-Birnen-Chutney',2,'',6,'2013-06-29 22:00:00',1,0),(28,'Orientalisches Gemüse mit Bulgur und Currydip',1,'',6,'2013-06-29 22:00:00',1,0),(29,'Chicorée-Orangensalat',1,'',6,'2013-06-29 22:00:00',1,0),(30,'Linguine mit Basilikum-Honig-Sauce',1,'Pastagericht',4,'2013-06-29 22:00:00',1,0),(31,'Masala-Curry mit Tofu, Brokkoli, Kartoffel, Spinat und Basmatireis',1,'vegan, ohne Weizen',4,'2013-06-29 22:00:00',1,0),(32,'Spirelli in Zitronen-Öl-Sauce mit Walnüssen, Petersilie,Cocktailtomaten und Parmesan',1,'einfach',4,'2013-07-01 22:00:00',1,0),(33,'Nasi Goreng (Indonesischer Paprikareis mit Sambal Oelek und Spiegelei )',1,'Spiegelei anbraten',4,'2013-07-01 22:00:00',1,0),(34,'Mandelpesto',2,'',4,'2013-07-01 22:00:00',1,0),(35,'Gnocchi',1,'',4,'2013-07-01 22:00:00',1,0),(36,'Thailändischer Glasnudelsalat',1,'',6,'2013-07-02 22:00:00',0,0),(37,'Hackfleischlasagne mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',1,0),(38,'Kartoffel-Gorgonzolagratin mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',1,0),(39,'Pasta mit Tomatenmandelpesto und Kräutermix',1,'',6,'2013-07-02 22:00:00',1,0),(40,'Hackfleischlasagne mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',0,0),(41,'Hackfleischlasagne mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',0,0),(42,'Hackfleischlasagne mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',0,0),(43,'Spaghetti Arrabiata',1,'',6,'2013-07-02 22:00:00',1,0),(44,'Spaghetti Arrabiata',1,'',6,'2013-07-02 22:00:00',0,0),(45,'Kartoffel-Gorgonzolagratin mit Salatbeilage',1,'',6,'2013-07-02 22:00:00',0,0),(46,'Putenroulade mit Karotten-Thymianfüllung, dazu Taglaitelle und Rucola',1,'',1,'2013-07-02 22:00:00',1,0),(47,'Putenroulade mit Karotten-Thymianfüllung, dazu Taglaitelle und Rucola',1,'',1,'2013-07-02 22:00:00',1,0),(48,'Lauch-Gorgonzola-Birnenquiche',1,'',6,'2013-07-02 22:00:00',0,0),(49,'Spirelli mit Kräuterpesto, Karotten und Cocktailtomaten',1,'',4,'2013-07-02 22:00:00',1,0),(50,'Salatbeilage',2,'',4,'2013-07-02 22:00:00',1,0),(51,'Kartoffelauflauf mit Cabanossi und Ei',1,'',4,'2013-07-02 22:00:00',1,0),(52,'Gebackene Bananen mit Ahornsirup und Sahne',1,'',4,'2013-07-02 22:00:00',0,0),(53,'Apfel-Schmand-Dessert mit Krokant',2,'',4,'2013-07-02 22:00:00',0,0),(54,'Apfel-Schmand-Dessert mit Krokant',1,'',4,'2013-07-02 22:00:00',0,0),(55,'Gebackene Bananen mit Ahornsirup und Sahne',2,'',4,'2013-07-02 22:00:00',0,0),(56,'Gebackene Bananen mit Ahornsirup und Sahne',1,'',4,'2013-07-02 22:00:00',1,0),(57,'Apfel-Schmand-Dessert mit Krokant',1,'',4,'2013-07-02 22:00:00',1,0),(58,'Tomatenblätterteigkuchen',1,'',6,'2013-07-03 22:00:00',1,0),(59,'Pasta mit Zitronenbutter und Basilikum',1,'',6,'2013-07-03 22:00:00',1,0),(60,'Aprikosen-Linsencurry mit Basmatireis',1,'',6,'2013-07-03 22:00:00',1,0),(61,'Ziegenkäse im Speckmantel mit Salat',1,'',6,'2013-07-03 22:00:00',1,0),(62,'Szegediner Gulasch',1,'',6,'2013-07-03 22:00:00',1,0),(63,'Apfel-Birnen-Chutney',2,'',6,'2013-07-03 22:00:00',0,0),(64,'Aprikosen-Linsencurry mit Basmatireis',1,'',6,'2013-07-03 22:00:00',0,0),(65,'Apfel-Birnen-Chutney',2,'',6,'2013-07-03 22:00:00',0,0),(66,'Apfel-Birnen-Chutney',2,'',6,'2013-07-03 22:00:00',0,0),(67,'Apfel-Birnen-Chutney',2,'',6,'2013-07-03 22:00:00',0,0),(68,'Tomatenblätterteigkuchen',1,'',6,'2013-07-03 22:00:00',0,0),(69,'Szegediner Gulasch',1,'',6,'2013-07-03 22:00:00',0,0),(70,'Ziegenkäse im Speckmantel mit Salat',1,'',6,'2013-07-03 22:00:00',0,0),(71,'Mousse au chocolat',1,'',6,'2013-07-03 22:00:00',0,0),(72,'Pasta mit Zitronenbutter und Basilikum',1,'',6,'2013-07-03 22:00:00',0,0),(73,'Kartoffelbrei',2,'',6,'2013-07-03 22:00:00',0,0),(74,'Pute in Calvadosrahm',1,'',6,'2013-07-04 22:00:00',1,0),(75,'Käsespätzle',1,'',5,'2013-07-06 22:00:00',1,0),(76,'Käsespätzle',1,'',5,'2013-07-06 22:00:00',0,0),(77,'risotto pfifferlinge parmesan',1,'',3,'2013-07-07 22:00:00',1,0),(78,'risotto pfifferlinge parmesan',1,'',3,'2013-07-07 22:00:00',1,0),(79,'FarmerKartoffel',2,'',4,'2013-07-07 22:00:00',1,0),(80,'Kräuterpfannkuchen mit Kartoffel-Mangold-Füllung',1,'',4,'2013-07-07 22:00:00',1,0),(81,'risotto mit pfifferlingen,parmesan und rucola',1,'',3,'2013-07-07 22:00:00',1,0),(82,'Matjessalat Hausfrauenart mit Bratkartoffeln',1,'',3,'2013-07-07 22:00:00',1,0),(83,'Matjessalat Hausfrauenart mit Bratkartoffeln',1,'',3,'2013-07-07 22:00:00',1,0),(84,'Risotto mit Pfifferlingen,Parmesan und Saatbeilage',1,'',3,'2013-07-07 22:00:00',1,0),(85,'risotto mit pfifferlingen,parmesan und rucola',1,'',3,'2013-07-07 22:00:00',1,0),(86,'Himbeerparfait',2,'',3,'2013-07-07 22:00:00',0,0),(87,'TestRezept',2,'',1,'2013-07-07 22:00:00',0,0),(88,'Himbeerparfait',2,'',3,'2013-07-07 22:00:00',1,0),(89,'TestRezept2',1,'',1,'2013-07-07 22:00:00',0,0),(90,'Himbeerparfait',1,'',3,'2013-07-07 22:00:00',1,0),(91,'TestRezept 3 ',1,'',1,'2013-07-07 22:00:00',0,0),(92,'Himbeerparfait',1,'',3,'2013-07-07 22:00:00',1,0),(93,'Himbeerparfait',1,'',3,'2013-07-07 22:00:00',1,0),(94,'Schweinebraten mit Spätzle und Salbeisosse',1,'',3,'2013-07-07 22:00:00',1,0),(95,'Schweinebraten mit Spätzle und Salbeisosse',1,'',3,'2013-07-07 22:00:00',1,0),(96,'Kartoffel-Kohlrabigratin mit Salatbeilage',1,'',3,'2013-07-07 22:00:00',1,0),(97,'Kartoffel-Kohlrabigratin mit Salatbeilage',1,'',3,'2013-07-07 22:00:00',1,0),(98,'Kartoffel-Kohlrabigratin mit Salatbeilage',1,'',3,'2013-07-07 22:00:00',1,0),(99,'Kartoffel-Kohlrabigratin mit Salatbeilage',1,'',3,'2013-07-20 22:00:00',1,0),(100,'Spagetti mit gebackenen Auberginenwürfeln, Basilikum und Pecorino',1,'',5,'2013-07-07 22:00:00',1,0),(101,'Spagetti mit gebackenen Auberginenwürfeln, Basilikum und Pecorino',1,'',5,'2013-07-07 22:00:00',1,0),(102,'Pasta mit Fenchel-Rosinen-Chili-Tomatensugo und Parmesan',1,'',1,'2013-07-07 22:00:00',1,0),(103,'Mangocreme mit Karamellkruste',1,'',5,'2013-07-07 22:00:00',1,0),(104,'Penne alla puttanesca',1,'',14,'2013-07-07 22:00:00',0,0),(105,'Penne alla puttanesca',1,'',14,'2013-07-07 22:00:00',1,0),(106,'Pikantes Paprikagemüse mit Pfirsichen und Oliven, dazu Avorioreis und Joghurt',1,'',14,'2013-07-08 22:00:00',1,0),(107,'Pikantes Paprikagemüse mit Pfirsichen, dazu Avorioreis und Joghurt',1,'',14,'2013-07-07 22:00:00',0,0),(108,'Pikantes Paprikagemüse mit Pfirsichen, dazu Avorioreis und Joghurt',1,'',14,'2013-07-07 22:00:00',0,0),(109,'Salat mit gratiniertem Ziegenkäse mit Thymian und Honig und Walnüssen',1,'',14,'2013-07-07 22:00:00',0,0),(110,'Salat mit gratiniertem Ziegenkäse mit Thymian und Honig und Walnüssen',1,'',14,'2013-07-07 22:00:00',0,0),(111,'Rindergulasch',1,'',5,'2013-07-07 22:00:00',1,0),(112,'Bandnudeln',2,'',5,'2013-07-07 22:00:00',1,0),(113,'Salatbeilage',2,'',5,'2013-07-07 22:00:00',1,0),(114,'Rindergulasch',1,'',5,'2013-07-07 22:00:00',1,0),(115,'Libanesisches Joghurthühnchen mit Reis, Pinienkernen, Rosinen und Karotten',1,'',5,'2013-07-09 22:00:00',1,0),(116,'Salatteller mit gebratenen Lachswürfeln',1,'',5,'2013-07-08 22:00:00',1,0),(117,'Panzanella (Toskanischer Brotsalat mit Paprika, Oliven, Basilikum und Sardellen)',1,'',5,'2013-07-08 22:00:00',1,0),(118,'Panzanella (Toskanischer Brotsalat mit Paprika, Oliven, Basilikum und Sardellen)',1,'',5,'2013-07-08 22:00:00',1,0),(119,'Rigatoni Saltati (mit Schinken, Erbsen, Champignon und Tomatensahne)',1,'',5,'2013-07-08 22:00:00',1,0),(120,'Gemüse-Couscous mit Auberginen, Zucchini, Karotten)',1,'',5,'2013-07-08 22:00:00',1,0),(121,'Gemüse-Couscous (mit Auberginen, Zucchini, Karotten)',1,'',5,'2013-07-08 22:00:00',1,0),(122,'Rigatoni Saltati (mit Schinken, Erbsen, Champignon und Tomatensahne)',1,'',5,'2013-07-08 22:00:00',1,0),(123,'gratinierte Erdbeeren',1,'',1,'2013-07-08 22:00:00',1,0),(124,'gratinierte Erdbeeren',1,'',1,'2013-07-08 22:00:00',1,0),(125,'grünes Thaicurry mit Cashewnüssen, Duftreis und Thaibasilikum',1,'',1,'2013-07-08 22:00:00',1,0),(126,'grünes Thaicurry mit Cashewnüssen, Duftreis und Thaibasilikum',1,'',1,'2013-07-08 22:00:00',1,0),(127,'Bunte Blattsalate mit gratiniertem Ziegenkäse mit Thymian, Honig und Walnüssen',1,'',14,'2013-07-08 22:00:00',1,0),(128,'Erbsenpüree',2,'',6,'2013-07-09 22:00:00',1,0),(129,'Pfannkuchen mit Blumenkohl',1,'',5,'2013-07-09 22:00:00',1,0),(130,'Salatteller mit panierten Austernpilzen und Kräuterdip',1,'',3,'2013-07-09 22:00:00',1,0),(131,'Salatteller mit panierten Austernpilzen und Kräuterdip',1,'',3,'2013-07-09 22:00:00',1,0),(132,'Semmelknödel mit Pfifferlingrahm und Rucola',1,'',1,'2013-09-20 22:00:00',1,0),(133,'Merguez mit Farmerkartoffeln, Avocadodip und Tomatensalat',1,'',1,'2013-07-12 22:00:00',1,0),(134,'Griechischer Joghurt mit Trauben, Walnüssen und Honig',1,'',1,'2013-07-11 22:00:00',1,0),(135,'Himbeerlimettentiramisu',1,'',6,'2013-07-12 22:00:00',1,0),(136,'Kartoffeltortilla mit rotem Mojo',1,'',6,'2013-07-12 22:00:00',1,0),(137,'Vegetarische Gemüse-Lasagne mit Ricotta',1,'',6,'2013-07-12 22:00:00',1,0),(138,'Panzanella (ital. Brotsalat)',1,'',6,'2013-07-12 22:00:00',1,0),(139,'New York Cheesecake mit Orangensauce',1,'',6,'2013-07-12 22:00:00',1,0),(140,'pikantes Gemüsecurry mit Basmatireis',1,'',6,'2013-07-12 22:00:00',1,0),(141,'Tiramisu klassisch',1,'',6,'2013-07-12 22:00:00',1,0),(142,'Kräuterbulgur mit orientalischem Gemüse',1,'',6,'2013-07-12 22:00:00',1,0),(143,'Himbeerlimettentiramisu',1,'',6,'2013-07-12 22:00:00',1,0),(144,'Kurze Maccaroni',2,'',4,'2013-07-13 22:00:00',1,0),(145,'Kichererbsen-Tomatensauce mit Gomasio und Joghurt',1,'',4,'2013-07-13 22:00:00',1,0),(146,'Bohnenragout mit Champignons',1,'',4,'2013-07-14 22:00:00',1,0),(147,'Basmatireis',2,'',4,'2013-07-13 22:00:00',0,0),(148,'Basmatireis',2,'',4,'2013-07-13 22:00:00',1,0),(149,'Kichererbsen mit Spinat, Kräuter und Frischkäse',1,'',4,'2013-07-13 22:00:00',1,0),(150,'Salzkartoffel',2,'',4,'2013-07-13 22:00:00',1,0),(151,'Kartoffelsalat mit Zuckerschoten, Karotten und Kräutercreme',1,'',4,'2013-07-13 22:00:00',1,0),(152,'Rote Bete- Birnen-Salat mit Walnüssen',1,'',4,'2013-07-13 22:00:00',1,0),(153,'Indischer Reis Mit Gemüse, Rosinen, Cashew-Kerne und Joghurt',1,'',4,'2013-07-13 22:00:00',1,0),(154,'Karoffel-Auberginen Curry',1,'',4,'2013-07-13 22:00:00',1,0),(155,'Frittierte Linsenbällchen mit Mangochutney',1,'',4,'2013-07-13 22:00:00',1,0),(156,'Kartoffelgratin mit Knollensellerie, Lauch und Thymian',1,'',4,'2013-07-13 22:00:00',1,0),(157,'Gefüllte Paprika mit Bulgur, Rosinen und Ziegenkäse',1,'',4,'2013-07-14 22:00:00',1,0),(158,'Mangocreme mit Karamellkruste',1,'',5,'2013-07-30 22:00:00',1,0),(159,'Auberginensalat  \"La Tamu\" auf Blattsalat ',1,'',5,'2013-07-14 22:00:00',1,0),(160,'Spaghetti mit Rucolapesto und geschmorten Tomatenwürfeln und Parmesan ',1,'',5,'2013-08-11 22:00:00',1,0),(161,'Mexikanisches Hühnchen mit Paprika, Chili, Koriander u. Schokolade im Tortillafladen mit  Avocado-Tomatensalat ',1,'',5,'2013-07-14 22:00:00',1,0),(162,'Zucchini-Kartoffelgratin mit gegrillter Kräutertomate und Salatbeilage',1,'',5,'2013-07-14 22:00:00',1,0),(163,'Steinpilzrisotto mit Salatbeilage',1,'',5,'2013-07-14 22:00:00',1,0),(164,'Fenchel-Mozzarella-Salat mit Johannisbeersoße',1,'',4,'2013-07-14 22:00:00',1,0),(165,'Zucchini-Oliven-Küchlein mit Schafskäse und Minze',1,'',4,'2013-07-14 22:00:00',1,0),(166,'Karotten-Kapern-Gemüse mit Creme fraiche',2,'',4,'2013-07-14 22:00:00',1,0),(167,'Curry Masala',1,'Mit Tofu',4,'2013-07-14 22:00:00',1,0),(168,'Rote Linsen mit Spinat und Ingwer',1,'',4,'2013-07-14 22:00:00',1,0),(169,'Trollingerbraten mit Pommes Anna',1,'',7,'2013-07-14 22:00:00',1,0),(170,'Karotten-Curry-Gemüse',2,'',4,'2013-07-14 22:00:00',1,0),(171,'gefüllte Zuchini mit Kräuterhirse',1,'',7,'2013-07-14 22:00:00',1,0),(172,'Karotten-Aprikosenbällchen',1,'',7,'2013-07-14 22:00:00',1,0),(173,'Lachs-Spinat-Lasagne',1,'',7,'2013-07-14 22:00:00',1,0),(174,'Beerenscrumble mit Vanilleeis',1,'',7,'2013-07-14 22:00:00',1,0),(175,'Blattsalate mit gratinierten, gefüllten Champignons',1,'',7,'2013-07-15 22:00:00',1,0),(176,'Calamares mit Zitronenajoli und Salatbeilage',1,'',3,'2013-07-15 22:00:00',1,0),(177,'Maccaroncini mit Gorgonzolacremr und karamelisierten Birnen',1,'',3,'2013-07-15 22:00:00',1,0),(178,'Tabouhleh',1,'',3,'2013-07-15 22:00:00',1,0),(179,'Pealla Verdura mit Oliven',1,'',3,'2013-07-15 22:00:00',1,0),(180,'Penne mit Papaya-Chilli-Kokossosse',1,'',3,'2013-07-15 22:00:00',1,0),(181,'Blätterteigtaschen mit Aprikosen und Ricotta',1,'',3,'2013-07-15 22:00:00',1,0),(182,'Erdbeer-Rhabarber-Crumble mit Sahne',1,'',4,'2013-07-16 22:00:00',1,0),(183,'Salatteller mit Fenchel, Äpfel und Dattelsauce',1,'',4,'2013-07-16 22:00:00',1,0),(184,'Gefüllte Pfannkuchen mit Johannisbeeren und Karamellsauce',1,'',4,'2013-07-18 22:00:00',1,0),(185,'Salatteller mit Schafskäsecreme dazu Kirschtomaten und Pinienkerne',1,'',4,'2013-07-18 22:00:00',1,0),(186,'Paniertes Schweineschnitzel mit Kartoffel-Gurkensalat',1,'',3,'2013-07-20 22:00:00',1,0),(187,'Spinatschafskäse-Strudel mit Salatbeilage',1,'',3,'2013-07-20 22:00:00',1,0),(188,'Creme caramel',1,'',3,'2013-07-20 22:00:00',1,0),(189,'Pastizzio-Griechischer Hackfleischauflauf mit Macceroni,dazu Tomatensalat',1,'',3,'2013-07-20 22:00:00',1,0),(190,'Rosmarinkarftoffln mit Gorgonzolacreme und Blattsalat',1,'',3,'2013-07-20 22:00:00',1,0),(191,'Grünkernpfannkuchen mit Pfifferlingen und Frühlingszwiebeln',1,'',3,'2013-07-20 22:00:00',1,0),(192,'Zanderfilet mit Zuccini und Tomaten mit Salzkartoffeln',1,'',3,'2013-07-20 22:00:00',1,0),(193,'Salatteller mit gebratenen Auberginen und grünem Mojo',1,'',3,'2013-07-20 22:00:00',1,0),(194,'Hühnchen mit Ananas-Curry Risotto',1,'',5,'2013-08-11 22:00:00',1,0),(195,'Rinder-Tajin mit getr. Aprikosen und Pflaumen, Couscous und Karotten',1,'',5,'2013-07-20 22:00:00',1,0),(196,'Pellkartoffeln mit Kräuterquark und Salatbeilage',1,'',5,'2013-07-20 22:00:00',1,0),(197,'Mattjes nach Hausfrauenart mit Bratkartoffeln',1,'',7,'2013-07-21 22:00:00',1,0),(198,'Blattsalate mit marinierten Austernpilzen',1,'',7,'2013-07-21 22:00:00',1,0),(199,'Linsenpilav ',1,'',7,'2013-07-21 22:00:00',1,0),(200,'Zitronenhühnchen mit Oliven, mediteranes Gemüse und Kartoffelbrei',1,'',7,'2013-07-21 22:00:00',1,0),(201,'Süßkartoffelgratin mit Tomatensugo ',1,'',7,'2013-07-21 22:00:00',1,0),(202,'Beerengrütze mit Vanilleeis',1,'',7,'2013-07-21 22:00:00',1,0),(203,'Salatteller mit Hummus, Oliven und Fladenbrot',1,'',1,'2013-07-21 22:00:00',1,0),(204,'Penne mit Paprikasugo und Avocadocreme',1,'',7,'2013-07-25 22:00:00',1,0),(205,'kräuterquark',2,'',1,'2013-07-30 22:00:00',1,0),(206,'Linsen-Dattel-Salat',1,'',14,'2013-08-05 22:00:00',1,0),(207,'Vegetarische Lasagne mit Salatbeilage',1,'',14,'2013-08-05 22:00:00',1,0),(208,'gebratener grüner Spargel mit Parmesan-Polenta',1,'',14,'2013-08-05 22:00:00',1,0),(209,'Rote Bete-Kartoffel-Auflauf mit Schafskäse',1,'',14,'2013-08-05 22:00:00',1,0),(210,'Limettenquark mit Minzzucker',1,'',14,'2013-08-05 22:00:00',1,0),(211,'Spaghetti mit Rucola, Cocktailtomaten und Pinienkernen',1,'',14,'2013-08-05 22:00:00',1,0),(212,'Rinderbraten',1,'',2,'2013-08-17 22:00:00',1,0),(213,'Indonesische Erdnußsauce mit Tofu, Blumenkohl, Karotten und Zucchini',1,'',4,'2013-08-30 22:00:00',1,0),(214,'Couscous Marrakesch mit Kartoffel, Kichererbsen, Zucchini und Hokkaido',1,'',4,'2013-08-30 22:00:00',1,0),(215,'Sizilianisches Auberginen-Gemüse mit Salbei-Polenta',1,'',4,'2013-08-30 22:00:00',1,0),(216,'Auberginen-Gratin mit Spaghetti-Mozzarella-Füllung',1,'',4,'2013-08-30 22:00:00',1,0),(217,'Pasta mit Kürbis-Pesto und Cocktailtomaten',1,'',4,'2013-08-30 22:00:00',1,0),(218,'Dahl (indisches Gemüsegericht mit dreierlei Linsen)',1,'',4,'2013-08-30 22:00:00',1,0),(219,'Pasta mit Rucola, Oliven, Tomaten und Parmesan',1,'',4,'2013-08-30 22:00:00',1,0),(220,'Aprikosengratin mit Vanilleeis',2,'',4,'2013-08-30 22:00:00',1,0),(221,'Grundbedarf Lieferung Montag',1,'',15,'2013-09-15 22:00:00',1,0);
/*!40000 ALTER TABLE `rezept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezept_has_artikel`
--

DROP TABLE IF EXISTS `rezept_has_artikel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezept_has_artikel` (
  `rezept_fk` int(11) NOT NULL,
  `artikel_fk` int(11) NOT NULL,
  `menge` decimal(10,4) NOT NULL,
  `einheit` int(11) NOT NULL,
  PRIMARY KEY (`rezept_fk`,`artikel_fk`),
  KEY `fk_rezept_has_artikel_einheit1_idx` (`einheit`),
  KEY `fk_rezept_has_artikel_rezept1_idx` (`rezept_fk`),
  KEY `fk_rezept_has_artikel_ARTIKEL11_idx` (`artikel_fk`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezept_has_artikel`
--

LOCK TABLES `rezept_has_artikel` WRITE;
/*!40000 ALTER TABLE `rezept_has_artikel` DISABLE KEYS */;
INSERT INTO `rezept_has_artikel` VALUES (1,134,10.0000,4),(1,246,500.0000,1),(1,249,10.0000,2),(4,3,1.1000,3),(4,6,1.1000,3),(4,7,1.0000,10),(5,3,1.1000,1),(5,6,1.1000,1),(6,4,1.1000,1),(7,2,1.1000,1),(8,51,1.1000,3),(9,18,1000.0000,3),(9,134,10.0000,6),(9,191,2.0000,5),(9,248,0.1250,5),(9,249,20.0000,3),(9,250,1500.0000,3),(9,253,30.0000,3),(10,36,2500.0000,3),(10,134,10.0000,6),(10,191,15.0000,5),(10,249,15.0000,3),(11,72,500.0000,3),(11,134,20.0000,6),(11,246,0.2000,5),(11,249,10.0000,3),(11,253,30.0000,3),(14,108,1500.0000,3),(14,239,50.0000,3),(15,51,500.0000,3),(15,52,500.0000,3),(15,55,500.0000,3),(15,190,3.0000,5),(15,614,250.0000,3),(16,89,1000.0000,3),(16,260,3.0000,5),(16,613,7.0000,2),(18,58,1500.0000,3),(18,135,100.0000,6),(18,138,100.0000,3),(18,284,2500.0000,3),(19,190,0.5000,5),(19,259,8.0000,1),(19,615,10.0000,1),(21,245,1.0000,5),(21,641,1000.0000,3),(22,135,25.0000,6),(22,138,10.0000,3),(22,687,10.0000,3),(23,54,250.0000,3),(23,119,1500.0000,3),(23,245,1.0000,5),(23,249,10.0000,3),(23,615,10.0000,1),(24,134,10.0000,6),(24,232,5000.0000,3),(24,233,50.0000,3),(24,277,2500.0000,3),(24,690,250.0000,3),(25,135,100.0000,6),(26,2,1000.0000,3),(26,194,750.0000,3),(26,250,1500.0000,3),(26,615,20.0000,1),(27,99,150.0000,3),(27,247,0.3000,5),(28,193,500.0000,3),(30,52,200.0000,3),(30,96,250.0000,3),(30,134,15.0000,6),(30,191,2.0000,5),(30,249,30.0000,3),(30,277,2500.0000,3),(30,614,300.0000,3),(30,643,250.0000,2),(30,711,350.0000,5),(30,712,15.0000,3),(31,36,1000.0000,3),(31,39,1000.0000,3),(31,618,2000.0000,3),(31,714,250.0000,5),(31,715,15.0000,3),(32,51,500.0000,3),(32,281,2500.0000,3),(32,614,750.0000,3),(32,643,1000.0000,2),(33,141,100.0000,6),(33,233,30.0000,3),(33,249,25.0000,3),(33,284,1750.0000,3),(33,615,25.0000,1),(33,627,250.0000,6),(33,718,75.0000,3),(34,134,25.0000,6),(34,249,25.0000,3),(34,255,500.0000,3),(34,256,500.0000,3),(34,711,750.0000,5),(35,283,5000.0000,3),(37,19,2000.0000,3),(37,191,1.0000,5),(37,231,3000.0000,6),(37,232,1000.0000,3),(38,2,1000.0000,3),(38,191,2.0000,5),(39,239,50.0000,3),(39,281,3000.0000,3),(39,614,1000.0000,3),(43,205,1000.0000,3),(43,231,3000.0000,6),(43,239,50.0000,3),(43,277,3000.0000,3),(51,615,0.0000,1),(53,191,1.0000,5),(54,191,0.0000,5),(58,249,1.0000,3),(59,249,1.0000,3),(60,249,1.0000,3),(61,249,1.0000,3),(62,249,1.0000,3),(74,249,1.0000,3),(75,249,1.0000,3),(77,614,1.0000,3),(80,615,0.0000,1),(81,614,1.0000,3),(82,249,1.0000,3),(84,249,1.0000,3),(86,249,1.0000,3),(87,1,1.0000,3),(89,2,1.0000,3),(90,249,1.0000,3),(91,3,1.0000,3),(92,249,1.0000,3),(94,249,1.0000,3),(96,249,1.0000,3),(97,249,1.0000,3),(99,249,1.0000,3),(100,1,1.0000,3),(102,249,1.0000,3),(103,240,1.0000,3),(104,249,1.0000,3),(106,249,1.0000,3),(107,249,1.0000,3),(109,249,1.0000,3),(111,249,1.0000,3),(112,249,1.0000,3),(113,249,1.0000,3),(115,249,1.0000,3),(116,249,1.0000,3),(117,249,1.0000,3),(119,249,1.0000,3),(120,249,1.0000,3),(123,264,1.0000,3),(125,249,1.0000,3),(127,249,1.0000,3),(128,249,1.0000,3),(129,249,1.0000,3),(130,249,1.0000,3),(132,194,99.0000,3),(132,249,1.0000,3),(133,249,1.0000,3),(134,264,1.0000,3),(135,249,1.0000,3),(136,249,1.0000,3),(137,249,1.0000,3),(138,249,1.0000,3),(139,249,1.0000,3),(140,249,1.0000,3),(141,249,1.0000,3),(142,249,1.0000,3),(144,249,1.0000,3),(145,249,1.0000,3),(146,249,1.0000,3),(147,249,15.0000,3),(148,249,15.0000,3),(149,249,15.0000,3),(150,249,15.0000,3),(151,249,15.0000,3),(152,249,15.0000,3),(153,249,15.0000,3),(154,249,15.0000,3),(155,249,15.0000,3),(156,249,1.0000,3),(157,249,15.0000,3),(158,264,1.0000,3),(158,267,9000.0000,6),(159,249,1.0000,3),(160,194,12000.0000,3),(160,249,1.0000,3),(161,249,1.0000,3),(162,249,1.0000,3),(163,249,1.0000,3),(164,249,15.0000,3),(165,249,15.0000,3),(166,249,15.0000,3),(167,249,15.0000,3),(168,249,15.0000,3),(169,249,1.0000,3),(170,249,15.0000,3),(171,249,1.0000,3),(172,249,1.0000,3),(173,249,1.0000,3),(174,249,1.0000,3),(175,249,1.0000,3),(176,249,1.0000,3),(177,249,1.0000,3),(178,249,1.0000,3),(179,249,1.0000,3),(180,249,1.0000,3),(181,249,1.0000,3),(182,99,250.0000,3),(183,249,10.0000,3),(184,249,1.0000,3),(185,249,1.0000,3),(186,249,1.0000,3),(187,249,1.0000,3),(188,249,1.0000,3),(189,249,1.0000,3),(190,249,1.0000,3),(191,249,1.0000,3),(192,249,1.0000,3),(193,249,1.0000,3),(194,194,100.0000,3),(194,249,1.0000,3),(194,250,14000.0000,3),(195,249,1.0000,3),(196,249,1.0000,3),(197,249,1.0000,3),(198,249,1.0000,3),(199,249,1.0000,3),(200,249,1.0000,3),(201,249,1.0000,3),(202,249,1.0000,3),(203,249,1.0000,3),(204,249,1.0000,3),(205,192,750.0000,3),(206,249,1.0000,3),(207,249,1.0000,3),(208,249,1.0000,3),(209,249,1.0000,3),(210,249,1.0000,3),(211,249,1.0000,3),(213,249,1.0000,3),(214,249,1.0000,3),(215,249,1.0000,3),(216,249,1.0000,3),(217,249,1.0000,3),(218,249,1.0000,3),(219,249,1.0000,3),(220,194,1.0000,3),(221,194,10000.0000,3);
/*!40000 ALTER TABLE `rezept_has_artikel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezept_has_fussnote`
--

DROP TABLE IF EXISTS `rezept_has_fussnote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezept_has_fussnote` (
  `rezept_id` int(11) NOT NULL,
  `fussnote_fk` int(11) NOT NULL,
  PRIMARY KEY (`rezept_id`,`fussnote_fk`),
  KEY `fk_rezept_has_rezepteigenschaften_rezepteigenschaften1_idx` (`fussnote_fk`),
  KEY `fk_rezept_has_rezepteigenschaften_rezept1_idx` (`rezept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezept_has_fussnote`
--

LOCK TABLES `rezept_has_fussnote` WRITE;
/*!40000 ALTER TABLE `rezept_has_fussnote` DISABLE KEYS */;
/*!40000 ALTER TABLE `rezept_has_fussnote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezept_has_zubereitung`
--

DROP TABLE IF EXISTS `rezept_has_zubereitung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezept_has_zubereitung` (
  `rezept_fk` int(11) NOT NULL,
  `zubereitung_fk` int(11) NOT NULL,
  PRIMARY KEY (`rezept_fk`,`zubereitung_fk`),
  KEY `fk_zubereitung_idx` (`zubereitung_fk`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezept_has_zubereitung`
--

LOCK TABLES `rezept_has_zubereitung` WRITE;
/*!40000 ALTER TABLE `rezept_has_zubereitung` DISABLE KEYS */;
INSERT INTO `rezept_has_zubereitung` VALUES (2,1),(3,1),(5,1),(7,1),(10,1),(14,1),(19,1),(22,1),(24,1),(25,1),(27,1),(28,1),(30,1),(32,1),(33,1),(36,1),(37,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(46,1),(47,1),(49,1),(52,1),(55,1),(56,1),(59,1),(60,1),(61,1),(63,1),(64,1),(65,1),(66,1),(67,1),(70,1),(71,1),(72,1),(74,1),(75,1),(76,1),(77,1),(78,1),(80,1),(81,1),(82,1),(83,1),(84,1),(85,1),(86,1),(88,1),(90,1),(92,1),(93,1),(94,1),(95,1),(102,1),(104,1),(105,1),(106,1),(107,1),(108,1),(116,1),(125,1),(126,1),(128,1),(129,1),(136,1),(137,1),(140,1),(142,1),(144,1),(145,1),(146,1),(149,1),(152,1),(154,1),(163,1),(167,1),(168,1),(170,1),(171,1),(172,1),(176,1),(177,1),(178,1),(180,1),(186,1),(189,1),(193,1),(199,1),(200,1),(201,1),(204,1),(206,1),(208,1),(211,1),(4,2),(8,2),(15,2),(18,2),(21,2),(26,2),(28,2),(35,2),(37,2),(38,2),(39,2),(40,2),(41,2),(42,2),(45,2),(48,2),(51,2),(58,2),(60,2),(64,2),(68,2),(75,2),(76,2),(79,2),(82,2),(83,2),(89,2),(96,2),(97,2),(98,2),(99,2),(107,2),(108,2),(123,2),(124,2),(125,2),(126,2),(133,2),(136,2),(137,2),(138,2),(139,2),(140,2),(142,2),(147,2),(148,2),(150,2),(151,2),(153,2),(156,2),(157,2),(162,2),(169,2),(171,2),(172,2),(173,2),(174,2),(175,2),(179,2),(181,2),(182,2),(184,2),(186,2),(187,2),(188,2),(189,2),(190,2),(192,2),(201,2),(209,2),(62,3),(69,3),(74,3),(87,3),(94,3),(95,3),(109,3),(110,3),(111,3),(114,3),(115,3),(127,3),(169,3),(195,3),(207,3),(209,3),(212,3),(130,4),(131,4),(133,4),(155,4),(163,4),(165,4),(191,4),(192,4),(194,4),(197,4),(213,4);
/*!40000 ALTER TABLE `rezept_has_zubereitung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezeptart`
--

DROP TABLE IF EXISTS `rezeptart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezeptart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezeptart`
--

LOCK TABLES `rezeptart` WRITE;
/*!40000 ALTER TABLE `rezeptart` DISABLE KEYS */;
INSERT INTO `rezeptart` VALUES (2,'Beilage'),(1,'Hauptgericht');
/*!40000 ALTER TABLE `rezeptart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rollen`
--

DROP TABLE IF EXISTS `rollen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rollen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rollen`
--

LOCK TABLES `rollen` WRITE;
/*!40000 ALTER TABLE `rollen` DISABLE KEYS */;
INSERT INTO `rollen` VALUES (4,'Besteller'),(1,'Chef'),(3,'Einkauf'),(2,'Koch'),(5,'Speiseplan freigeben');
/*!40000 ALTER TABLE `rollen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schichtplan`
--

DROP TABLE IF EXISTS `schichtplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schichtplan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mitarbeiter` int(11) NOT NULL,
  `datum` date NOT NULL,
  `arbeitsbeginn` time NOT NULL,
  `erbeitsende` time NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_schichtplan_mitarbeiter1_idx` (`mitarbeiter`)
) ENGINE=InnoDB DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schichtplan`
--

LOCK TABLES `schichtplan` WRITE;
/*!40000 ALTER TABLE `schichtplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `schichtplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zubereitung`
--

DROP TABLE IF EXISTS `zubereitung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zubereitung` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=dec8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zubereitung`
--

LOCK TABLES `zubereitung` WRITE;
/*!40000 ALTER TABLE `zubereitung` DISABLE KEYS */;
INSERT INTO `zubereitung` VALUES (3,'Backofen'),(1,'Herd'),(4,'Herd ab 12 Uhr'),(2,'Konvektomat');
/*!40000 ALTER TABLE `zubereitung` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-12 21:35:26
