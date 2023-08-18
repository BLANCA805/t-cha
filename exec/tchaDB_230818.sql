CREATE DATABASE  IF NOT EXISTS `blanca` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `blanca`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: blanca.c8onspz62ykf.ap-northeast-2.rds.amazonaws.com    Database: blanca
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.14-MariaDB

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8frr4bcabmmeyyu60qt7iiblo` (`question_id`),
  KEY `FKbhxtqnwmx1a6qssgb5q4x6s5v` (`user_profile_id`),
  CONSTRAINT `FK8frr4bcabmmeyyu60qt7iiblo` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  CONSTRAINT `FKbhxtqnwmx1a6qssgb5q4x6s5v` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `trainer_id` char(36) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg66xhydbbu9metn6cgdf983f4` (`trainer_id`),
  KEY `FKtn6cnqa4t5rwkc3ikjh1tjohh` (`user_profile_id`),
  CONSTRAINT `FKg66xhydbbu9metn6cgdf983f4` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  CONSTRAINT `FKtn6cnqa4t5rwkc3ikjh1tjohh` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
INSERT INTO `bookmark` VALUES (1,'2023-08-17 20:48:42.870960','2023-08-17 20:48:42.870960','d5068629-efe7-4670-937e-dcb2073eecf1',3),(2,'2023-08-17 21:03:23.206168','2023-08-17 21:03:23.206168','47362dac-dbae-42a5-a738-fc5432c0270a',1),(3,'2023-08-18 00:06:04.513046','2023-08-18 00:06:04.513046','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72',7),(4,'2023-08-18 00:06:08.687120','2023-08-18 00:06:08.687120','6daa7db4-39bc-40db-ac82-d8dfe097874b',7),(6,'2023-08-18 00:06:12.988535','2023-08-18 00:06:12.988535','d5068629-efe7-4670-937e-dcb2073eecf1',7),(7,'2023-08-18 00:10:45.681551','2023-08-18 00:10:45.681551','204735f2-2a53-4cb6-b37b-282aa91894ae',7),(8,'2023-08-18 00:49:50.427452','2023-08-18 00:49:50.427452','52988488-9281-4be8-b575-919d6940df75',1),(9,'2023-08-18 00:49:51.229245','2023-08-18 00:49:51.229245','8aa46fd5-f6f7-43dc-94f2-36697e6bef91',1),(10,'2023-08-18 00:49:53.260313','2023-08-18 00:49:53.260313','1045d818-4c18-40e5-95d9-a2949e62fab0',1),(11,'2023-08-18 00:49:53.859943','2023-08-18 00:49:53.859943','2a7cf630-37b6-4572-acea-5bac2d85debc',1),(12,'2023-08-18 00:49:54.988818','2023-08-18 00:49:54.988818','6daa7db4-39bc-40db-ac82-d8dfe097874b',1),(13,'2023-08-18 00:49:56.293691','2023-08-18 00:49:56.293691','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72',1);
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_log`
--

DROP TABLE IF EXISTS `exercise_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `live_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmo9984thycd86xeiqc911ly2m` (`live_id`),
  CONSTRAINT `FKmo9984thycd86xeiqc911ly2m` FOREIGN KEY (`live_id`) REFERENCES `pt_live` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_log`
--

LOCK TABLES `exercise_log` WRITE;
/*!40000 ALTER TABLE `exercise_log` DISABLE KEYS */;
INSERT INTO `exercise_log` VALUES (1,'2023-08-18 05:00:28.589329','2023-08-18 05:00:28.589329','WRITE','',1),(2,'2023-08-18 05:01:03.498865','2023-08-18 05:01:03.498865','WRITE','',2),(3,'2023-08-18 05:01:37.762171','2023-08-18 05:01:37.762171','WRITE','',3),(4,'2023-08-17 20:03:49.970819','2023-08-17 20:03:49.970819','WRITE','',4),(6,'2023-08-18 05:11:53.645025','2023-08-18 05:19:36.708280','WRITE','제목',6),(7,'2023-08-17 20:49:27.157961','2023-08-17 20:49:27.157961','WRITE','',7),(8,'2023-08-17 21:02:43.457773','2023-08-18 01:14:05.389041','READ','변정원 회원님 1일차 - 회원님 앞으로도 열심히 하셔야 해요!!',8),(9,'2023-08-17 23:57:47.545468','2023-08-17 23:57:47.545468','WRITE','',9),(10,'2023-08-18 00:24:51.081747','2023-08-18 00:24:51.081747','WRITE','',10),(11,'2023-08-18 00:30:02.683246','2023-08-18 01:30:03.118575','READ','',11),(12,'2023-08-18 00:33:33.175071','2023-08-18 01:30:03.118790','READ','',12),(13,'2023-08-18 00:34:03.718688','2023-08-18 01:30:03.118897','READ','',13),(14,'2023-08-18 00:42:46.115815','2023-08-18 01:30:03.118995','READ','',14),(15,'2023-08-18 00:42:53.949668','2023-08-18 01:30:03.119083','READ','',15),(16,'2023-08-18 00:43:28.768388','2023-08-18 01:30:03.119209','READ','',16),(17,'2023-08-18 00:52:23.593522','2023-08-18 01:30:03.119306','READ','',17),(18,'2023-08-18 00:56:02.955633','2023-08-18 00:56:02.955633','WRITE','',18),(19,'2023-08-18 00:56:05.343059','2023-08-18 01:30:03.119405','READ','',19),(20,'2023-08-18 00:56:22.984257','2023-08-18 01:30:03.119612','READ','',20),(21,'2023-08-18 00:59:33.815983','2023-08-18 00:59:33.815983','WRITE','',21),(22,'2023-08-18 01:04:07.411693','2023-08-18 01:30:03.119703','READ','',22),(23,'2023-08-18 01:07:13.399819','2023-08-18 01:07:13.399819','WRITE','',23),(24,'2023-08-18 01:07:52.463412','2023-08-18 01:07:52.463412','WRITE','',24),(25,'2023-08-18 01:21:27.122347','2023-08-18 01:30:59.429926','READ','오늘 너무 잘 하셨어요! ',25),(26,'2023-08-18 01:23:16.245596','2023-08-18 01:23:16.245596','WRITE','',26),(27,'2023-08-18 01:25:46.917321','2023-08-18 01:25:46.917321','WRITE','',27),(28,'2023-08-18 01:32:16.744641','2023-08-18 01:32:16.744641','WRITE','',28),(29,'2023-08-18 01:37:33.314005','2023-08-18 01:37:33.314005','WRITE','',29),(30,'2023-08-18 01:58:00.465689','2023-08-18 02:30:02.885076','READ','',30),(31,'2023-08-18 02:00:30.282487','2023-08-18 02:00:30.282487','WRITE','',31);
/*!40000 ALTER TABLE `exercise_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_log_contents`
--

DROP TABLE IF EXISTS `exercise_log_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_log_contents` (
  `exercise_log_id` bigint(20) NOT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `contents_order` int(11) NOT NULL,
  PRIMARY KEY (`exercise_log_id`,`contents_order`),
  CONSTRAINT `FKmt54dfdedq3wqlwyit4dfjltc` FOREIGN KEY (`exercise_log_id`) REFERENCES `exercise_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_log_contents`
--

LOCK TABLES `exercise_log_contents` WRITE;
/*!40000 ALTER TABLE `exercise_log_contents` DISABLE KEYS */;
INSERT INTO `exercise_log_contents` VALUES (6,'string1',0),(8,'거북목이 있으신 편이세요^^,,,',0),(8,'목이 너무 꺾이셨어용^^,,,, 조금만 더 반듯하게!! 신경써보세요',1),(25,'이 자세만 주의해주세요:)',0);
/*!40000 ALTER TABLE `exercise_log_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_log_images`
--

DROP TABLE IF EXISTS `exercise_log_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_log_images` (
  `exercise_log_id` bigint(20) NOT NULL,
  `images` varchar(255) DEFAULT NULL,
  `images_order` int(11) NOT NULL,
  PRIMARY KEY (`exercise_log_id`,`images_order`),
  CONSTRAINT `FKrkqd3m45rimwgy136t7sygda5` FOREIGN KEY (`exercise_log_id`) REFERENCES `exercise_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_log_images`
--

LOCK TABLES `exercise_log_images` WRITE;
/*!40000 ALTER TABLE `exercise_log_images` DISABLE KEYS */;
INSERT INTO `exercise_log_images` VALUES (6,'string1',0),(8,'https://blanca05.s3.ap-northeast-2.amazonaws.com/image/fe1adda7-2c1f-44f8-8416-54cf3691e468-%EB%B3%80%ED%9A%8C%EC%9B%901.PNG',0),(8,'https://blanca05.s3.ap-northeast-2.amazonaws.com/image/1d3e846f-2ff5-4642-a087-9d004d8c96f7-%EB%B3%80%ED%9A%8C%EC%9B%902.PNG',1),(25,'https://blanca05.s3.ap-northeast-2.amazonaws.com/image/a68072c4-6bf2-4d94-affe-6673514bb656-%EC%8A%A4%EC%BF%BC%EB%93%9C%20%EC%9E%90%EC%84%B8%20%EA%B5%90%EC%A0%95%20%ED%9B%84.jpg',0);
/*!40000 ALTER TABLE `exercise_log_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_log_videos`
--

DROP TABLE IF EXISTS `exercise_log_videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_log_videos` (
  `exercise_log_id` bigint(20) NOT NULL,
  `videos` varchar(255) DEFAULT NULL,
  `videos_order` int(11) NOT NULL,
  PRIMARY KEY (`exercise_log_id`,`videos_order`),
  CONSTRAINT `FKnw81ovc40ylne7seo4bb0jh6g` FOREIGN KEY (`exercise_log_id`) REFERENCES `exercise_log` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_log_videos`
--

LOCK TABLES `exercise_log_videos` WRITE;
/*!40000 ALTER TABLE `exercise_log_videos` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_log_videos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guide`
--

DROP TABLE IF EXISTS `guide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guide` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guide`
--

LOCK TABLES `guide` WRITE;
/*!40000 ALTER TABLE `guide` DISABLE KEYS */;
/*!40000 ALTER TABLE `guide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` text NOT NULL,
  `status` varchar(8) NOT NULL,
  `title` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pt_class`
--

DROP TABLE IF EXISTS `pt_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `is_del` int(11) NOT NULL DEFAULT 0,
  `pt_live_id` bigint(20) DEFAULT NULL,
  `start_date` date NOT NULL,
  `start_time` time NOT NULL,
  `trainer_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgs3ek1cqasaci149owhpto4en` (`trainer_id`),
  CONSTRAINT `FKgs3ek1cqasaci149owhpto4en` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pt_class`
--

LOCK TABLES `pt_class` WRITE;
/*!40000 ALTER TABLE `pt_class` DISABLE KEYS */;
INSERT INTO `pt_class` VALUES (1,'2023-08-17 19:59:13.223373','2023-08-18 05:00:28.600066',0,1,'2023-08-18','05:00:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(2,'2023-08-17 19:59:13.242645','2023-08-18 05:01:03.519858',0,2,'2023-08-18','06:00:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(3,'2023-08-17 20:01:26.349013','2023-08-17 20:03:49.975298',0,4,'2023-08-18','13:30:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(4,'2023-08-17 20:01:26.351168','2023-08-18 05:11:53.672337',0,6,'2023-08-18','14:30:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(5,'2023-08-17 20:01:26.353033','2023-08-18 05:01:37.780143',0,3,'2023-08-18','22:30:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(6,'2023-08-17 20:11:42.348038','2023-08-18 00:56:02.957254',0,18,'2023-08-18','15:30:00','d5068629-efe7-4670-937e-dcb2073eecf1'),(7,'2023-08-17 20:49:10.564005','2023-08-17 20:49:27.162059',0,7,'2023-08-18','06:00:00','47362dac-dbae-42a5-a738-fc5432c0270a'),(8,'2023-08-17 21:01:54.931756','2023-08-17 21:02:43.459919',0,8,'2023-08-18','06:30:00','47362dac-dbae-42a5-a738-fc5432c0270a'),(9,'2023-08-17 23:57:44.295089','2023-08-17 23:57:47.547043',0,9,'2023-08-18','09:00:00','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72'),(10,'2023-08-18 00:08:31.578441','2023-08-18 00:24:51.086525',0,10,'2023-08-18','09:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f'),(11,'2023-08-18 00:08:31.580434','2023-08-18 00:59:33.817795',0,21,'2023-08-18','10:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f'),(12,'2023-08-18 00:16:35.933175','2023-08-18 01:04:07.413318',0,22,'2023-08-16','16:30:00','433db333-9e15-4be4-b75e-28a7ce64de3b'),(13,'2023-08-18 00:23:34.875616','2023-08-18 00:30:02.692607',0,11,'2023-08-16','16:30:00','433db333-9e15-4be4-b75e-28a7ce64de3b'),(14,'2023-08-18 00:24:58.720285','2023-08-18 01:07:52.464991',0,24,'2023-08-18','10:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(15,'2023-08-18 00:33:02.306389','2023-08-18 00:34:03.720472',0,13,'2023-08-15','16:30:00','52988488-9281-4be8-b575-919d6940df75'),(16,'2023-08-18 00:33:02.308207','2023-08-18 00:33:33.176743',0,12,'2023-08-15','18:30:00','52988488-9281-4be8-b575-919d6940df75'),(17,'2023-08-18 00:37:14.749451','2023-08-18 01:23:16.247209',0,26,'2023-08-18','10:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(18,'2023-08-18 00:37:14.751283','2023-08-18 01:32:16.759111',0,28,'2023-08-18','12:00:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(19,'2023-08-18 00:37:14.753371','2023-08-18 01:37:33.315747',0,29,'2023-08-18','13:00:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(20,'2023-08-18 00:38:54.566902','2023-08-18 00:42:46.135461',0,14,'2023-08-15','16:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(21,'2023-08-18 00:38:54.568824','2023-08-18 00:42:53.951432',0,15,'2023-08-15','18:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(22,'2023-08-18 00:38:54.570424','2023-08-18 00:43:28.770095',0,16,'2023-08-15','19:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(23,'2023-08-18 00:38:54.573764','2023-08-18 01:58:00.517747',0,30,'2023-08-15','20:30:00','6daa7db4-39bc-40db-ac82-d8dfe097874b'),(24,'2023-08-18 00:49:04.941300','2023-08-18 00:52:23.595220',0,17,'2023-08-15','16:30:00','8aa46fd5-f6f7-43dc-94f2-36697e6bef91'),(25,'2023-08-18 00:54:19.428795','2023-08-18 02:00:30.284086',0,31,'2023-08-20','16:30:00','204735f2-2a53-4cb6-b37b-282aa91894ae'),(26,'2023-08-18 00:55:24.278936','2023-08-18 00:56:05.345154',0,19,'2023-08-10','16:30:00','1045d818-4c18-40e5-95d9-a2949e62fab0'),(27,'2023-08-18 00:55:24.280873','2023-08-18 00:56:22.986172',0,20,'2023-08-10','17:30:00','1045d818-4c18-40e5-95d9-a2949e62fab0'),(28,'2023-08-18 00:55:24.282449','2023-08-18 00:55:24.282449',0,NULL,'2023-08-10','19:30:00','1045d818-4c18-40e5-95d9-a2949e62fab0'),(29,'2023-08-18 01:06:34.737667','2023-08-18 01:07:13.401309',0,23,'2023-08-18','10:30:00','47362dac-dbae-42a5-a738-fc5432c0270a'),(30,'2023-08-18 01:20:00.677067','2023-08-18 01:21:27.123831',0,25,'2023-08-18','10:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f'),(31,'2023-08-18 01:20:00.687682','2023-08-18 01:20:00.687682',0,NULL,'2023-08-18','11:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f'),(32,'2023-08-18 01:25:43.385413','2023-08-18 01:25:46.918951',0,27,'2023-08-18','10:30:00','47362dac-dbae-42a5-a738-fc5432c0270a'),(33,'2023-08-18 02:15:57.893639','2023-08-18 02:15:57.893639',0,NULL,'2023-08-18','11:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f'),(34,'2023-08-18 02:15:57.895615','2023-08-18 02:15:57.895615',0,NULL,'2023-08-18','12:30:00','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f');
/*!40000 ALTER TABLE `pt_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pt_live`
--

DROP TABLE IF EXISTS `pt_live`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt_live` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `pt_class_id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `trainer_id` char(36) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtglbu178ch6gyll4bjfqyfss8` (`user_profile_id`),
  CONSTRAINT `FKtglbu178ch6gyll4bjfqyfss8` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pt_live`
--

LOCK TABLES `pt_live` WRITE;
/*!40000 ALTER TABLE `pt_live` DISABLE KEYS */;
INSERT INTO `pt_live` VALUES (1,'2023-08-18 05:00:28.550638','2023-08-18 00:23:30.074449',1,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',NULL),(2,'2023-08-18 05:01:03.472071','2023-08-18 00:23:30.074649',2,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',NULL),(3,'2023-08-18 05:01:37.732172','2023-08-18 00:23:30.074790',5,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',NULL),(4,'2023-08-17 20:03:49.960406','2023-08-18 00:23:30.074937',3,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',NULL),(6,'2023-08-18 05:11:53.604725','2023-08-18 00:23:30.075078',4,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',2),(7,'2023-08-17 20:49:27.148817','2023-08-18 00:23:30.075194',7,'TERMINATION','47362dac-dbae-42a5-a738-fc5432c0270a',1),(8,'2023-08-17 21:02:43.453326','2023-08-18 00:23:30.075622',8,'TERMINATION','47362dac-dbae-42a5-a738-fc5432c0270a',4),(9,'2023-08-17 23:57:47.541731','2023-08-18 00:23:30.075943',9,'TERMINATION','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72',3),(10,'2023-08-18 00:24:51.075555','2023-08-18 00:25:30.015327',10,'TERMINATION','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f',3),(11,'2023-08-18 00:30:02.629502','2023-08-18 00:31:30.023637',13,'TERMINATION','433db333-9e15-4be4-b75e-28a7ce64de3b',8),(12,'2023-08-18 00:33:33.169098','2023-08-18 00:34:30.024529',16,'TERMINATION','52988488-9281-4be8-b575-919d6940df75',5),(13,'2023-08-18 00:34:03.714114','2023-08-18 00:35:30.014922',15,'TERMINATION','52988488-9281-4be8-b575-919d6940df75',8),(14,'2023-08-18 00:42:46.076768','2023-08-18 00:43:30.020614',20,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',NULL),(15,'2023-08-18 00:42:53.945206','2023-08-18 00:43:30.020825',21,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',NULL),(16,'2023-08-18 00:43:28.763782','2023-08-18 00:44:30.045855',22,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',10),(17,'2023-08-18 00:52:23.588554','2023-08-18 00:53:30.028984',24,'TERMINATION','8aa46fd5-f6f7-43dc-94f2-36697e6bef91',1),(18,'2023-08-18 00:56:02.951149','2023-08-18 00:57:30.033719',6,'TERMINATION','d5068629-efe7-4670-937e-dcb2073eecf1',13),(19,'2023-08-18 00:56:05.338646','2023-08-18 00:57:30.033976',26,'TERMINATION','1045d818-4c18-40e5-95d9-a2949e62fab0',14),(20,'2023-08-18 00:56:22.980150','2023-08-18 00:57:30.034079',27,'TERMINATION','1045d818-4c18-40e5-95d9-a2949e62fab0',NULL),(21,'2023-08-18 00:59:33.811669','2023-08-18 01:00:30.048125',11,'TERMINATION','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f',3),(22,'2023-08-18 01:04:07.407264','2023-08-18 01:05:30.019904',12,'TERMINATION','433db333-9e15-4be4-b75e-28a7ce64de3b',3),(23,'2023-08-18 01:07:13.395626','2023-08-18 01:08:30.040324',29,'TERMINATION','47362dac-dbae-42a5-a738-fc5432c0270a',5),(24,'2023-08-18 01:07:52.458955','2023-08-18 01:08:30.040546',14,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',7),(25,'2023-08-18 01:21:27.117324','2023-08-18 01:22:30.050183',30,'TERMINATION','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f',3),(26,'2023-08-18 01:23:16.241165','2023-08-18 01:24:30.026591',17,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',3),(27,'2023-08-18 01:25:46.913096','2023-08-18 01:26:30.019042',32,'TERMINATION','47362dac-dbae-42a5-a738-fc5432c0270a',11),(28,'2023-08-18 01:32:16.694320','2023-08-18 01:37:00.472334',18,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',3),(29,'2023-08-18 01:37:33.309839','2023-08-18 01:40:01.587597',19,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',3),(30,'2023-08-18 01:58:00.262343','2023-08-18 02:01:00.411295',23,'TERMINATION','6daa7db4-39bc-40db-ac82-d8dfe097874b',9),(31,'2023-08-18 02:00:30.278184','2023-08-18 02:04:00.488234',25,'TERMINATION','204735f2-2a53-4cb6-b37b-282aa91894ae',1);
/*!40000 ALTER TABLE `pt_live` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfywtwufhngsw1ese4i42y0p0d` (`user_profile_id`),
  CONSTRAINT `FKfywtwufhngsw1ese4i42y0p0d` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` text NOT NULL,
  `star` float NOT NULL,
  `live_id` bigint(20) NOT NULL,
  `trainer_id` char(36) NOT NULL,
  `user_profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8mmf09lx66rowguo0ye9euikl` (`live_id`),
  KEY `FKaq655d1vvylqxq7e52p5w31tg` (`trainer_id`),
  KEY `FKsinta48v75uvq10vuffrp5vrl` (`user_profile_id`),
  CONSTRAINT `FK8mmf09lx66rowguo0ye9euikl` FOREIGN KEY (`live_id`) REFERENCES `pt_live` (`id`),
  CONSTRAINT `FKaq655d1vvylqxq7e52p5w31tg` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`),
  CONSTRAINT `FKsinta48v75uvq10vuffrp5vrl` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'2023-08-18 05:14:51.652528','2023-08-18 05:14:51.652528','별로',2.5,1,'d5068629-efe7-4670-937e-dcb2073eecf1',2),(2,'2023-08-18 00:31:02.119680','2023-08-18 00:31:02.119680','나쁘진 않았습니다',3.5,11,'433db333-9e15-4be4-b75e-28a7ce64de3b',8),(3,'2023-08-18 00:35:21.303384','2023-08-18 00:35:21.303384','금은 시간!',4,12,'52988488-9281-4be8-b575-919d6940df75',8),(4,'2023-08-18 00:36:34.425731','2023-08-18 00:36:34.425731','너무나도 좋은 시간이었습니다',5,13,'52988488-9281-4be8-b575-919d6940df75',5),(5,'2023-08-18 00:43:53.536140','2023-08-18 00:43:53.536140','너무나도 좋은 시간이었습니다',5,14,'6daa7db4-39bc-40db-ac82-d8dfe097874b',1),(6,'2023-08-18 00:44:04.169181','2023-08-18 00:44:04.169181','너무 친절하십니다',5,15,'6daa7db4-39bc-40db-ac82-d8dfe097874b',1),(7,'2023-08-18 00:44:50.932571','2023-08-18 00:44:50.932571','스타 트레이너는 역시 다르시네요!',5,16,'6daa7db4-39bc-40db-ac82-d8dfe097874b',10),(8,'2023-08-18 00:51:15.306204','2023-08-18 00:51:15.306204','엄청 좋은 강의였습니다. 새벽 밖에 시간이 되지 않아 PT를 받을 기회가 없었는데 덕분에 퇴근후에 PT를 받을 수 있었어요. 이번 기회에 열심히 운동해서 올해가 끝나기 전에 바프찍으려구요! 트레이너님 감사합니다.',5,7,'47362dac-dbae-42a5-a738-fc5432c0270a',1),(9,'2023-08-18 00:52:57.277657','2023-08-18 00:52:57.277657','흠...그냥 그랬어요!',2.5,17,'8aa46fd5-f6f7-43dc-94f2-36697e6bef91',1),(10,'2023-08-18 00:58:07.967967','2023-08-18 00:58:07.967967','흠...그냥 그랬어요!',2.5,19,'1045d818-4c18-40e5-95d9-a2949e62fab0',1),(11,'2023-08-18 00:59:55.470396','2023-08-18 00:59:55.470396','흠...그냥 그랬어요!',4,20,'1045d818-4c18-40e5-95d9-a2949e62fab0',13),(12,'2023-08-18 01:02:16.096465','2023-08-18 01:02:16.096465','완전 친절하신 트레이너님! 다음에 또 운동하러 오겠습니다. 감사합니다. ',5,18,'d5068629-efe7-4670-937e-dcb2073eecf1',13),(13,'2023-08-18 01:07:18.990906','2023-08-18 01:07:18.990906','친절하신 트레이너님 덕분에 오늘도 열심히 운동했습니다. 다음에 또 운동하러 올게요 깟호 ',4,22,'433db333-9e15-4be4-b75e-28a7ce64de3b',3),(14,'2023-08-18 01:10:34.800265','2023-08-18 01:10:34.800265','트레이너님 짱! 다리가 후덜덜하게 잘 운동했습니다. 다음에 또 운동하러 올게요 ',5,24,'6daa7db4-39bc-40db-ac82-d8dfe097874b',7),(15,'2023-08-18 01:47:47.225379','2023-08-18 01:47:47.225379','트레이너님 짱! 집에서도 pt를 받을 수 있으니깐 너무 편리합니다!',5,18,'6daa7db4-39bc-40db-ac82-d8dfe097874b',3),(16,'2023-08-18 01:48:31.764874','2023-08-18 01:48:31.764874','지난번에 이어서 이번에 또 pt진행했는데 역시나 믿을만 합니다. 여러분 등록 가능한 시간 없어지기 전에 빨리 예약하세요',5,19,'6daa7db4-39bc-40db-ac82-d8dfe097874b',3),(17,'2023-08-18 01:59:30.930589','2023-08-18 01:59:30.930589','오랫만에 운동을 하는거라 걱정했는데 덕분에 안다치고 운동할 수 있었습니다. 감사합니다.',4.1,23,'6daa7db4-39bc-40db-ac82-d8dfe097874b',8),(18,'2023-08-18 02:01:53.261381','2023-08-18 02:01:53.261381','그냥 그랬던거 같아요. 조금 아쉽네요....',3.1,25,'204735f2-2a53-4cb6-b37b-282aa91894ae',1);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `trainers` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'2023-08-17 19:58:27.729237','2023-08-17 19:58:27.729237','초심자','d5068629-efe7-4670-937e-dcb2073eecf1,'),(2,'2023-08-17 19:58:27.737263','2023-08-17 19:58:27.737263','화이팅','d5068629-efe7-4670-937e-dcb2073eecf1,'),(3,'2023-08-17 20:44:46.476189','2023-08-17 20:44:46.476189','편안','47362dac-dbae-42a5-a738-fc5432c0270a,'),(4,'2023-08-17 20:44:46.484538','2023-08-17 20:44:46.484538','초보','47362dac-dbae-42a5-a738-fc5432c0270a,'),(5,'2023-08-17 23:57:03.099507','2023-08-17 23:57:03.099507','멋짐','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72,'),(6,'2023-08-17 23:57:03.104424','2023-08-17 23:57:03.104424','대단','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72,'),(7,'2023-08-17 23:57:03.107614','2023-08-17 23:57:03.107614','최강','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72,'),(8,'2023-08-17 23:57:03.110683','2023-08-17 23:57:03.110683','최고','b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72,'),(9,'2023-08-18 00:01:48.467577','2023-08-18 00:24:47.441885','','6daa7db4-39bc-40db-ac82-d8dfe097874b,6daa7db4-39bc-40db-ac82-d8dfe097874b,6daa7db4-39bc-40db-ac82-d8dfe097874b,6daa7db4-39bc-40db-ac82-d8dfe097874b,'),(12,'2023-08-18 00:03:46.999655','2023-08-18 00:24:47.449012','스타','6daa7db4-39bc-40db-ac82-d8dfe097874b,6daa7db4-39bc-40db-ac82-d8dfe097874b,'),(13,'2023-08-18 00:03:47.002478','2023-08-18 00:24:47.461753','PT왕','6daa7db4-39bc-40db-ac82-d8dfe097874b,6daa7db4-39bc-40db-ac82-d8dfe097874b,'),(14,'2023-08-18 00:06:06.761260','2023-08-18 00:06:06.761260','몸매유지','433db333-9e15-4be4-b75e-28a7ce64de3b,'),(15,'2023-08-18 00:06:06.767537','2023-08-18 00:06:06.767537','체형교정','433db333-9e15-4be4-b75e-28a7ce64de3b,'),(16,'2023-08-18 00:07:06.591146','2023-08-18 00:15:24.524315','변비타파','2a7cf630-37b6-4572-acea-5bac2d85debc,52988488-9281-4be8-b575-919d6940df75,'),(17,'2023-08-18 00:07:31.769329','2023-08-18 00:07:31.769329','다이어트','1045d818-4c18-40e5-95d9-a2949e62fab0,'),(18,'2023-08-18 00:07:31.775427','2023-08-18 00:07:31.775427','바디프로필','1045d818-4c18-40e5-95d9-a2949e62fab0,'),(19,'2023-08-18 00:08:20.619571','2023-08-18 00:08:20.619571','부처','204735f2-2a53-4cb6-b37b-282aa91894ae,'),(20,'2023-08-18 00:08:20.623328','2023-08-18 00:08:20.623328','교수','204735f2-2a53-4cb6-b37b-282aa91894ae,'),(21,'2023-08-18 00:08:25.729355','2023-08-18 00:08:25.729355','성실','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f,'),(22,'2023-08-18 00:08:25.733777','2023-08-18 00:08:25.733777','착함','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f,'),(23,'2023-08-18 00:08:25.736547','2023-08-18 00:08:25.736547','무섭지않아요','4eaa08aa-f425-4dee-9d09-b05dd5e34f2f,'),(24,'2023-08-18 00:11:33.320764','2023-08-18 00:11:33.320764','자바','8aa46fd5-f6f7-43dc-94f2-36697e6bef91,'),(25,'2023-08-18 00:11:33.325149','2023-08-18 00:11:33.325149','헬스','8aa46fd5-f6f7-43dc-94f2-36697e6bef91,'),(26,'2023-08-18 00:11:33.332566','2023-08-18 00:11:33.332566','코딩','8aa46fd5-f6f7-43dc-94f2-36697e6bef91,');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer` (
  `id` char(36) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `tags` text DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK53pb5f4l8fkouf85uwenrs5p9` (`user_profile_id`),
  CONSTRAINT `FK53pb5f4l8fkouf85uwenrs5p9` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer`
--

LOCK TABLES `trainer` WRITE;
/*!40000 ALTER TABLE `trainer` DISABLE KEYS */;
INSERT INTO `trainer` VALUES ('1045d818-4c18-40e5-95d9-a2949e62fab0','2023-08-18 00:07:31.767429','2023-08-18 00:07:31.767429','근막이완, 영양 식단의 대가입니다. 엘리트 트레이너 구지성에게 맡겨주세요','엘리트 트레이너','다이어트,바디프로필','식단과 체형 교정',10),('204735f2-2a53-4cb6-b37b-282aa91894ae','2023-08-18 00:08:20.617759','2023-08-18 00:08:20.617759','성심성의껏 가르쳐 드리겠습니다','저보다 괜찮은 트레이너가 있을까요','부처,교수','배우고 싶은 자는 내게 오라',4),('2a7cf630-37b6-4572-acea-5bac2d85debc','2023-08-18 00:07:06.589171','2023-08-18 00:07:06.589171','변비가 있으신가요? 저와 함께 Act!하시죠!','깊은 새벽의 변액트','변비타파','변비 타파',9),('433db333-9e15-4be4-b75e-28a7ce64de3b','2023-08-18 00:06:06.759248','2023-08-18 00:06:06.759248','한나라가 진행하는 필라테스 교실입니다. 몸매를 가꾸고 싶으신 분은 누구나 환영입니다.','한나라의 바디워크','몸매유지,체형교정','한나라 Trainer의 필라테스 교실',8),('47362dac-dbae-42a5-a738-fc5432c0270a','2023-08-17 20:44:46.469091','2023-08-17 20:44:46.469091','이제 막 운동에 눈을 뜬 당신!\n평생 운동이라고는 해 본 적도 없지만,\n더 이상 몸이 예전같지 않다고 느낀다면?\n\n지금 바로 저와 함께 기초부터 탄탄히 시작해보시죠!!!!','초심자 맞춤! 부담스럽지 않은 트레이너!','편안,초보','PT 초심자를 위한 기초 탄탄 자세 잡기',3),('4eaa08aa-f425-4dee-9d09-b05dd5e34f2f','2023-08-18 00:08:25.727599','2023-08-18 00:08:25.727599','심장이 득근득근','변병국 트레이너입니다.','성실,착함,무섭지않아요','변병국 트레이너와의 득근 시간',11),('52988488-9281-4be8-b575-919d6940df75','2023-08-18 00:15:24.517675','2023-08-18 00:15:24.517675','시간은 금입니다!! 금을 아껴드리겠습니다!','시간은 금!!!','변비타파','금과 같은 시간, 제가 아껴드리겠습니다.',14),('6daa7db4-39bc-40db-ac82-d8dfe097874b','2023-08-18 00:01:48.465661','2023-08-18 00:24:47.457426','스타 트레이너의 스타 PT\n누구든 별로 만들어 드립니다!','스타 트레이너',',스타,PT왕','스타 트레이너의 스타 PT',5),('8aa46fd5-f6f7-43dc-94f2-36697e6bef91','2023-08-18 00:11:33.318755','2023-08-18 00:11:33.318755','코딩과 헬스, 두마리 토끼를 잡아드리죠!','푸른 노을의 오자바','자바,헬스,코딩','헬스 코딩',13),('b42d0bcd-4c28-4f06-91b2-b9f71c3bbb72','2023-08-17 23:57:03.097503','2023-08-17 23:57:03.097503','즐겁게 운동합시다','진익근 트레이너','멋짐,대단,최강,최고','즐거운 운동시간',6),('d5068629-efe7-4670-937e-dcb2073eecf1','2023-08-17 19:58:27.721482','2023-08-17 19:58:27.721482','빡세게 같이 운동합시다.','신규 트레이너','초심자,화이팅','24시간 운동과 함께라면....',1);
/*!40000 ALTER TABLE `trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer_images`
--

DROP TABLE IF EXISTS `trainer_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer_images` (
  `trainer_id` char(36) NOT NULL,
  `images` varchar(255) DEFAULT NULL,
  KEY `FK5fgops16jj4xuu5a49tyctsv9` (`trainer_id`),
  CONSTRAINT `FK5fgops16jj4xuu5a49tyctsv9` FOREIGN KEY (`trainer_id`) REFERENCES `trainer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_images`
--

LOCK TABLES `trainer_images` WRITE;
/*!40000 ALTER TABLE `trainer_images` DISABLE KEYS */;
INSERT INTO `trainer_images` VALUES ('d5068629-efe7-4670-937e-dcb2073eecf1','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/76811213-3524-40c7-90a9-7423ba760c71-arkhive-ny-o-9O3rsHliQ-unsplash.jpg'),('47362dac-dbae-42a5-a738-fc5432c0270a','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/54cfc9be-9ddb-477c-a6fa-1baa26bba2be-%E1%84%89%E1%85%AE%E1%84%89%E1%85%A1%E1%86%BC%E1%84%82%E1%85%A2%E1%84%8B%E1%85%A7%E1%86%A8.jpg'),('47362dac-dbae-42a5-a738-fc5432c0270a','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/b70cb7c2-9482-4a88-8101-6e542fc962ee-%E1%84%8C%E1%85%A1%E1%84%80%E1%85%A7%E1%86%A8%E1%84%8C%E1%85%B3%E1%86%BC1.jpg'),('433db333-9e15-4be4-b75e-28a7ce64de3b','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/1e9bc0c8-811a-48b3-8c8e-ee0a9c130c4f-%ED%95%9C%EB%82%98%EB%9D%BC.jpg'),('2a7cf630-37b6-4572-acea-5bac2d85debc','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/4eef5117-9357-435a-9461-b4c0ec5c7675-%EC%B5%9C%EC%9C%A0%EB%B9%84.jpg'),('1045d818-4c18-40e5-95d9-a2949e62fab0','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/4eef5117-9357-435a-9461-b4c0ec5c7675-%EC%B5%9C%EC%9C%A0%EB%B9%84.jpg'),('204735f2-2a53-4cb6-b37b-282aa91894ae','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/108376df-4efe-4e2d-a8eb-a9f5f3a954e1-%EC%84%9C%EC%9A%B8_8%EB%B0%98_%EB%B3%80%EC%A0%95%EC%9B%90.JPG'),('8aa46fd5-f6f7-43dc-94f2-36697e6bef91','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/355bcef2-b02a-419a-9312-407587f4daf2-%EC%98%A4%EC%9E%90%EB%B0%94%20-%20%EB%B3%B5%EC%82%AC%EB%B3%B8.jpg'),('52988488-9281-4be8-b575-919d6940df75','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/d2aeee1f-1889-451c-a0af-fec7e7e8fd9b-%EA%B8%88%EC%BD%94%EB%94%A9.jpg'),('6daa7db4-39bc-40db-ac82-d8dfe097874b','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/803e83da-dc2e-4f19-b719-3609f2478d33-IMG_4891.jpg'),('6daa7db4-39bc-40db-ac82-d8dfe097874b','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/53962e6d-4826-49d8-a1b0-64ace83ee84d-IMG_4897.jpg'),('6daa7db4-39bc-40db-ac82-d8dfe097874b','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/58aadc03-333d-4a63-b24e-99eed29a27f3-qkeco8eN7Q1jKEHeD5zpSkbC4437bzFf_1637742819_4409938.jpg');
/*!40000 ALTER TABLE `trainer_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `user_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKuganfwvnbll4kn2a3jeyxtyi` (`user_id`),
  CONSTRAINT `FKuganfwvnbll4kn2a3jeyxtyi` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1,'2023-08-17 19:50:56.948380','2023-08-17 20:46:59.235043','이채림','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/abbd3b05-909c-4191-9603-2083868cfe4a-myles-bloomfield-RNcDfVEXz54-unsplash.jpg','aa7deafc-d38c-4188-8621-a0f4b2e61968'),(2,'2023-08-18 04:55:07.586870','2023-08-18 04:55:07.586870','김진만','https://images.unsplash.com/photo-1691673522793-536b3709cd79?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1587&q=80','8f709a25-0b6b-41c1-9652-dad52b13a4dc'),(3,'2023-08-17 20:29:27.132528','2023-08-17 20:47:43.738738','최해미','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/25204d10-2db7-43c9-9e25-6cb06a0c4598-IMG_5762.jpeg','53fb2dd5-341f-46f2-b667-9cebd210bace'),(4,'2023-08-17 21:02:26.290424','2023-08-18 00:10:22.417766','변정원','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/2f3d4268-dddd-48b8-92f5-1260d3756c02-3878_13422_0100.jpg','68f1cca9-89ee-416a-bfbb-b34f0d5570ac'),(5,'2023-08-17 23:39:11.407002','2023-08-17 23:58:38.232629','하정호','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/f73ae56b-e095-4043-b7e6-d51933459870-rn_image_picker_lib_temp_df5319db-cc23-46bb-8d30-edb352f0c4fc.jpg','63b1cb26-88fe-4875-90b2-c3eedde8046e'),(6,'2023-08-17 23:56:08.780995','2023-08-17 23:56:08.780995','진익근',NULL,'502d6eb4-5b2b-4846-954d-e9976ac0a557'),(7,'2023-08-18 00:05:06.829310','2023-08-18 00:05:06.829310','MY Y',NULL,'bc4817b1-0351-4d42-8ffc-1db9b5d1ccb1'),(8,'2023-08-18 00:05:36.560653','2023-08-18 00:05:36.560653','한나라','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/7c8c20c1-dc08-4eae-bb4f-1a0345e9e50b-a539ef76-0489-41a8-a0cc-e321e5085d58.jpg','ac5ae134-97f2-43a3-ab04-8d21c910a3ee'),(9,'2023-08-18 00:06:58.218823','2023-08-18 00:06:58.218823','변액트','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/fa6838c9-e2dd-4e6f-b70c-b9815c8a873c-%EB%B3%80%EC%95%A1%ED%8A%B81.jpg','f823befc-17da-4ebd-9d90-3c6a89dc2186'),(10,'2023-08-18 00:07:23.770108','2023-08-18 00:07:23.770108','구지성','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/35f94549-7c62-42a5-8975-be40ffc267b5-%EA%B5%AC%EC%A7%80%EC%84%B11.jpg','9faf79e9-cc0a-4025-a0de-8c6ce49f9a02'),(11,'2023-08-18 00:07:25.568534','2023-08-18 00:07:25.568534','변병국',NULL,'675983dd-2807-476a-8f3a-0c1012b7e5c1'),(12,'2023-08-18 00:07:46.149641','2023-08-18 00:07:46.149641','임정원',NULL,'d6437178-1591-418d-8fa4-5ea79f4ffe1f'),(13,'2023-08-18 00:08:08.412456','2023-08-18 00:08:08.412456','오자바','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/690dd58c-27c1-4f05-9abf-447f9a1dae7b-%EC%98%A4%EC%9E%90%EB%B0%94.jpg','626c87c4-9bdd-434d-9725-ef24c9515fd1'),(14,'2023-08-18 00:13:38.161743','2023-08-18 00:13:38.161743','금코딩','https://blanca05.s3.ap-northeast-2.amazonaws.com/image/f50d1bfb-b75b-414f-9395-c5f4b851c7d7-%EA%B8%88%EC%BD%94%EB%94%A91.jpg','4dc5d93d-0481-4703-8b19-e5908445eec6'),(15,'2023-08-18 00:33:58.294636','2023-08-18 00:34:33.401073',NULL,NULL,'d6437178-1591-418d-8fa4-5ea79f4ffe1f');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` char(36) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('53760d8a-c46e-4e4d-9960-b6371f7ac5d8','USER'),('8f709a25-0b6b-41c1-9652-dad52b13a4dc','USER'),('aa7deafc-d38c-4188-8621-a0f4b2e61968','USER'),('aa7deafc-d38c-4188-8621-a0f4b2e61968','TRAINER'),('53fb2dd5-341f-46f2-b667-9cebd210bace','USER'),('53fb2dd5-341f-46f2-b667-9cebd210bace','TRAINER'),('502d6eb4-5b2b-4846-954d-e9976ac0a557','USER'),('502d6eb4-5b2b-4846-954d-e9976ac0a557','TRAINER'),('63b1cb26-88fe-4875-90b2-c3eedde8046e','USER'),('63b1cb26-88fe-4875-90b2-c3eedde8046e','TRAINER'),('bc4817b1-0351-4d42-8ffc-1db9b5d1ccb1','USER'),('ac5ae134-97f2-43a3-ab04-8d21c910a3ee','USER'),('ac5ae134-97f2-43a3-ab04-8d21c910a3ee','TRAINER'),('1bb74909-3e58-4192-bd72-501a60efb6a1','USER'),('f823befc-17da-4ebd-9d90-3c6a89dc2186','USER'),('f823befc-17da-4ebd-9d90-3c6a89dc2186','TRAINER'),('9faf79e9-cc0a-4025-a0de-8c6ce49f9a02','USER'),('9faf79e9-cc0a-4025-a0de-8c6ce49f9a02','TRAINER'),('d6437178-1591-418d-8fa4-5ea79f4ffe1f','USER'),('68f1cca9-89ee-416a-bfbb-b34f0d5570ac','USER'),('68f1cca9-89ee-416a-bfbb-b34f0d5570ac','TRAINER'),('675983dd-2807-476a-8f3a-0c1012b7e5c1','USER'),('675983dd-2807-476a-8f3a-0c1012b7e5c1','TRAINER'),('626c87c4-9bdd-434d-9725-ef24c9515fd1','USER'),('626c87c4-9bdd-434d-9725-ef24c9515fd1','TRAINER'),('4dc5d93d-0481-4703-8b19-e5908445eec6','USER'),('4dc5d93d-0481-4703-8b19-e5908445eec6','TRAINER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1bb74909-3e58-4192-bd72-501a60efb6a1','2023-08-18 00:07:03.562379','2023-08-18 00:07:03.562379','tcha_tester1@gmail.com','USER_ACTIVE'),('4dc5d93d-0481-4703-8b19-e5908445eec6','2023-08-18 00:13:28.599160','2023-08-18 00:13:28.599160','code@google.com','USER_ACTIVE'),('502d6eb4-5b2b-4846-954d-e9976ac0a557','2023-08-17 23:56:08.773341','2023-08-17 23:56:08.773341','ikkgngreat@gmail.com','USER_ACTIVE'),('53760d8a-c46e-4e4d-9960-b6371f7ac5d8','2023-08-18 04:53:00.364725','2023-08-18 00:33:27.342068','tcha_tester2@gmail.com','USER_QUIT'),('53fb2dd5-341f-46f2-b667-9cebd210bace','2023-08-17 20:29:27.095874','2023-08-17 20:29:27.095874','chlgoal961@gmail.com','USER_ACTIVE'),('626c87c4-9bdd-434d-9725-ef24c9515fd1','2023-08-18 00:07:58.675324','2023-08-18 00:07:58.675324','java@google.com','USER_ACTIVE'),('63b1cb26-88fe-4875-90b2-c3eedde8046e','2023-08-17 23:39:11.398952','2023-08-17 23:39:11.398952','hso8706@gmail.com','USER_ACTIVE'),('675983dd-2807-476a-8f3a-0c1012b7e5c1','2023-08-18 00:07:08.863996','2023-08-18 00:07:08.863996','tcha_tester@gmail.com','USER_ACTIVE'),('68f1cca9-89ee-416a-bfbb-b34f0d5570ac','2023-08-17 21:02:26.280911','2023-08-17 21:02:26.280911','labhg64@gmail.com','USER_ACTIVE'),('8f709a25-0b6b-41c1-9652-dad52b13a4dc','2023-08-17 19:54:27.240801','2023-08-17 19:54:27.240801','HansBethe@gmail.com','USER_ACTIVE'),('9faf79e9-cc0a-4025-a0de-8c6ce49f9a02','2023-08-18 00:07:14.292978','2023-08-18 00:07:14.292978','sung94@google.com','USER_ACTIVE'),('aa7deafc-d38c-4188-8621-a0f4b2e61968','2023-08-17 19:50:56.859586','2023-08-17 19:50:56.859586','chaelim3396@gmail.com','USER_ACTIVE'),('ac5ae134-97f2-43a3-ab04-8d21c910a3ee','2023-08-18 00:05:23.179939','2023-08-18 00:05:23.179939','nara@google.com','USER_ACTIVE'),('bc4817b1-0351-4d42-8ffc-1db9b5d1ccb1','2023-08-18 00:05:06.821902','2023-08-18 00:05:06.821902','yoonmy7@gmail.com','USER_ACTIVE'),('d6437178-1591-418d-8fa4-5ea79f4ffe1f','2023-08-18 00:07:33.019392','2023-08-18 00:07:33.019392','tcha_tester2@gmail.com','USER_ACTIVE'),('f823befc-17da-4ebd-9d90-3c6a89dc2186','2023-08-18 00:06:47.606110','2023-08-18 00:06:47.606110','act@google.com','USER_ACTIVE');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'blanca'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18 11:31:07
