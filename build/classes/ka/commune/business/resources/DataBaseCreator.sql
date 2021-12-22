-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: commune
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activitebs`
--
create database commune;
use commune ;

DROP TABLE IF EXISTS `activitebs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activitebs` (
  `idActiviteBS` int NOT NULL AUTO_INCREMENT,
  `sujet` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `jointures` text,
  PRIMARY KEY (`idActiviteBS`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `association`
--

DROP TABLE IF EXISTS `association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `association` (
  `idAssociation` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `represantant` varchar(50) DEFAULT NULL,
  `phone` varchar(14) DEFAULT NULL,
  `dateFondation` date DEFAULT NULL,
  `dateRenouvelement` date DEFAULT NULL,
  `dateExpiration` date DEFAULT NULL,
  `domaine` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idAssociation`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `circonscription`
--

DROP TABLE IF EXISTS `circonscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `circonscription` (
  `idCirconscription` int NOT NULL AUTO_INCREMENT,
  `designationCirconscription` varchar(50) DEFAULT NULL,
  `numero` int unsigned DEFAULT NULL,
  PRIMARY KEY (`idCirconscription`),
  UNIQUE KEY `numero_UNIQUE` (`numero`),
  UNIQUE KEY `designationCirconscription_UNIQUE` (`designationCirconscription`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `commission`
--

DROP TABLE IF EXISTS `commission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commission` (
  `idCommission` int NOT NULL AUTO_INCREMENT,
  `designationCommission` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idCommission`),
  UNIQUE KEY `designationCommission_UNIQUE` (`designationCommission`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `commissionec`
--

DROP TABLE IF EXISTS `commissionec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commissionec` (
  `idCommissionEC` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `represantant` varchar(50) DEFAULT NULL,
  `phone` varchar(14) DEFAULT NULL,
  `dateFondation` date DEFAULT NULL,
  `dateRenouvelement` date DEFAULT NULL,
  `dateExpiration` date DEFAULT NULL,
  `domaine` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idCommissionEC`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `designationsujetsession`
--

DROP TABLE IF EXISTS `designationsujetsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `designationsujetsession` (
  `idDesignation` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`idDesignation`),
  UNIQUE KEY `designation_UNIQUE` (`designation`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `domaine`
--

DROP TABLE IF EXISTS `domaine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domaine` (
  `idDomaine` int NOT NULL AUTO_INCREMENT,
  `designationDomaine` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idDomaine`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fonction`
--

DROP TABLE IF EXISTS `fonction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fonction` (
  `idFonction` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFonction`),
  UNIQUE KEY `designation_UNIQUE` (`designation`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mandat`
--

DROP TABLE IF EXISTS `mandat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mandat` (
  `idMandat` int NOT NULL AUTO_INCREMENT,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  PRIMARY KEY (`idMandat`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `materiel`
--

DROP TABLE IF EXISTS `materiel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materiel` (
  `idMateriel` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) DEFAULT NULL,
  `quantite` double DEFAULT NULL,
  PRIMARY KEY (`idMateriel`),
  UNIQUE KEY `designation_UNIQUE` (`designation`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `membrecommission`
--

DROP TABLE IF EXISTS `membrecommission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membrecommission` (
  `idCommission` int NOT NULL,
  `idMembreConseil` int NOT NULL,
  PRIMARY KEY (`idCommission`,`idMembreConseil`),
  KEY `fkmcmc` (`idMembreConseil`),
  CONSTRAINT `fkmccm` FOREIGN KEY (`idCommission`) REFERENCES `commission` (`idCommission`) ON DELETE CASCADE,
  CONSTRAINT `fkmcmc` FOREIGN KEY (`idMembreConseil`) REFERENCES `membreconseil` (`idMembreConseil`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `membreconseil`
--

DROP TABLE IF EXISTS `membreconseil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membreconseil` (
  `idMembreConseil` int NOT NULL AUTO_INCREMENT,
  `idMandat` int DEFAULT NULL,
  `nom` varchar(20) DEFAULT NULL,
  `prenom` varchar(20) DEFAULT NULL,
  `nomArabe` varchar(20) DEFAULT NULL,
  `prenomArabe` varchar(20) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `lieuNaissance` varchar(30) DEFAULT NULL,
  `adresse` varchar(30) DEFAULT NULL,
  `profession` varchar(20) DEFAULT NULL,
  `cin` varchar(12) DEFAULT NULL,
  `idPartiePolitique` int DEFAULT NULL,
  `idCirconscription` int DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `fonction` int DEFAULT NULL,
  `niveauScolaire` int DEFAULT NULL,
  PRIMARY KEY (`idMembreConseil`),
  UNIQUE KEY `cin_UNIQUE` (`cin`),
  UNIQUE KEY `telephone_UNIQUE` (`telephone`),
  KEY `fkmcc` (`idCirconscription`),
  KEY `fkmcpp` (`idPartiePolitique`),
  KEY `fkmcm` (`idMandat`),
  KEY `fkmcf_idx` (`fonction`),
  KEY `fkmcns_idx` (`niveauScolaire`),
  CONSTRAINT `fkmcc` FOREIGN KEY (`idCirconscription`) REFERENCES `circonscription` (`idCirconscription`) ,
  CONSTRAINT `fkmcf` FOREIGN KEY (`fonction`) REFERENCES `fonction` (`idFonction`) ,
  CONSTRAINT `fkmcm` FOREIGN KEY (`idMandat`) REFERENCES `mandat` (`idMandat`) ON DELETE CASCADE,
  CONSTRAINT `fkmcns` FOREIGN KEY (`niveauScolaire`) REFERENCES `niveauscolaire` (`idNiveauScolaire`),
  CONSTRAINT `fkmcpp` FOREIGN KEY (`idPartiePolitique`) REFERENCES `partiepolitique` (`idPartiePolitique`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `niveauscolaire`
--

DROP TABLE IF EXISTS `niveauscolaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `niveauscolaire` (
  `idNiveauScolaire` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idNiveauScolaire`),
  UNIQUE KEY `designation_UNIQUE` (`designation`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `objetactivitebs`
--

DROP TABLE IF EXISTS `objetactivitebs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `objetactivitebs` (
  `idObjetActiviteBS` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(255) DEFAULT NULL,
  `idActiviteBS` int DEFAULT NULL,
  PRIMARY KEY (`idObjetActiviteBS`),
  KEY `fkoaa` (`idActiviteBS`),
  CONSTRAINT `fkoaa` FOREIGN KEY (`idActiviteBS`) REFERENCES `activitebs` (`idActiviteBS`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `objetreunion`
--

DROP TABLE IF EXISTS `objetreunion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `objetreunion` (
  `idObjetReunion` int NOT NULL AUTO_INCREMENT,
  `designationObjetReunion` varchar(50) DEFAULT NULL,
  `idSujetSession` int DEFAULT NULL,
  `idDomaine` int DEFAULT NULL,
  PRIMARY KEY (`idObjetReunion`),
  KEY `fkorss` (`idSujetSession`),
  KEY `fkord` (`idDomaine`),
  CONSTRAINT `fkord` FOREIGN KEY (`idDomaine`) REFERENCES `domaine` (`idDomaine`),
  CONSTRAINT `fkorss` FOREIGN KEY (`idSujetSession`) REFERENCES `sujetsession` (`idSujetSession`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partiepolitique`
--

DROP TABLE IF EXISTS `partiepolitique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partiepolitique` (
  `idPartiePolitique` int NOT NULL AUTO_INCREMENT,
  `designationPartiePolitique` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idPartiePolitique`),
  UNIQUE KEY `designationPartiePolitique_UNIQUE` (`designationPartiePolitique`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pretedmateriel`
--

DROP TABLE IF EXISTS `pretedmateriel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretedmateriel` (
  `idPretMateriel` int NOT NULL,
  `idMateriel` int unsigned NOT NULL,
  `quantite` double DEFAULT NULL,
  PRIMARY KEY (`idPretMateriel`,`idMateriel`),
  KEY `fkpmm_idx` (`idMateriel`),
  CONSTRAINT `fkpmpm` FOREIGN KEY (`idPretMateriel`) REFERENCES `pretmateriel` (`idPretMateriel`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pretmateriel`
--

DROP TABLE IF EXISTS `pretmateriel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretmateriel` (
  `idPretMateriel` int NOT NULL AUTO_INCREMENT,
  `beneficiare` varchar(100) DEFAULT NULL,
  `representant` varchar(45) DEFAULT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `dateDemande` date DEFAULT NULL,
  `phone` varchar(14) DEFAULT NULL,
  `activite` varchar(250) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  PRIMARY KEY (`idPretMateriel`),
  UNIQUE KEY `unq` (`numero`,`year`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pretsalle`
--

DROP TABLE IF EXISTS `pretsalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretsalle` (
  `idPretSalle` int NOT NULL AUTO_INCREMENT,
  `dateDemande` date DEFAULT NULL,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `heureDebut` time DEFAULT NULL,
  `heureFin` time DEFAULT NULL,
  `beneficiare` varchar(100) DEFAULT NULL,
  `representant` varchar(45) DEFAULT NULL,
  `activite` varchar(100) DEFAULT NULL,
  `idSalle` int DEFAULT NULL,
  `phone` varchar(14) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  PRIMARY KEY (`idPretSalle`),
  UNIQUE KEY `unq` (`numero`,`year`),
  KEY `fkpss_idx` (`idSalle`),
  CONSTRAINT `fkpss` FOREIGN KEY (`idSalle`) REFERENCES `salle` (`idSalle`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reunionassociation`
--

DROP TABLE IF EXISTS `reunionassociation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reunionassociation` (
  `idReunion` int NOT NULL AUTO_INCREMENT,
  `sujet` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`idReunion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reunioncommissionec`
--

DROP TABLE IF EXISTS `reunioncommissionec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reunioncommissionec` (
  `idReunion` int NOT NULL AUTO_INCREMENT,
  `sujet` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`idReunion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salle`
--

DROP TABLE IF EXISTS `salle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salle` (
  `idSalle` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`idSalle`),
  UNIQUE KEY `designation_UNIQUE` (`designation`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `idSession` int NOT NULL AUTO_INCREMENT,
  `designationSession` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idSession`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sujetsession`
--

DROP TABLE IF EXISTS `sujetsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sujetsession` (
  `idSujetSession` int NOT NULL AUTO_INCREMENT,
  `idDesignation` int DEFAULT NULL,
  `idSession` int DEFAULT NULL,
  `mois` varchar(20) DEFAULT NULL,
  `annee` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `heure` time DEFAULT NULL,
  `idMandat` int DEFAULT NULL,
  PRIMARY KEY (`idSujetSession`),
  KEY `fksss` (`idSession`),
  KEY `fkssds` (`idDesignation`),
  KEY `fkssm_idx` (`idMandat`),
  CONSTRAINT `fkssds` FOREIGN KEY (`idDesignation`) REFERENCES `designationsujetsession` (`idDesignation`),
  CONSTRAINT `fkssm` FOREIGN KEY (`idMandat`) REFERENCES `mandat` (`idMandat`) ON DELETE CASCADE,
  CONSTRAINT `fksss` FOREIGN KEY (`idSession`) REFERENCES `session` (`idSession`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- start  table dump : operation
--
DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE  `operation` (
  `idComptabilite` int NOT NULL AUTO_INCREMENT,
  `partenaire` int DEFAULT NULL,
  `dateOperation` date DEFAULT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `fournisseur` varchar(100) DEFAULT NULL,
  `montantRecu` double DEFAULT NULL,
  `montantVerse` double DEFAULT NULL,
  `ref` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idComptabilite`),
  KEY `fkopP` (`partenaire`),
  CONSTRAINT `fkopP` FOREIGN KEY (`partenaire`) REFERENCES `partenaire` (`idPartenaire`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- start  table dump : partenaire
--
DROP TABLE IF EXISTS `partenaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partenaire` (
  `idPartenaire` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPartenaire`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `login` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

insert into user values(1,"admin","admin");
INSERT INTO commission VALUES (1,'لجنة التعمير'),(2,'لجنة المالية');
INSERT INTO designationsujetsession VALUES (4,'حضور أشغال الدورة الإستثنائية'),(3,'حضور أشغال الدورة العادية'),(2,'مناقشة جدول أعمال الدورة العادية'),(1,'وضع جدول الأعمال');
INSERT INTO `domaine` VALUES (1,'الماء الصالح للشرب');
INSERT INTO `fonction` VALUES (1,'الرئيس'),(2,'النائب الأول');
-- Dump completed on 2020-06-28 15:22:09
