-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: gym_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `attendance_date` date NOT NULL,
  `check_in_time` datetime NOT NULL,
  `check_out_time` datetime DEFAULT NULL,
  `method` enum('QR','MANUAL') NOT NULL DEFAULT 'QR',
  `status` enum('PRESENT','ABSENT') NOT NULL DEFAULT 'PRESENT',
  `qr_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`),
  UNIQUE KEY `member_id` (`member_id`,`attendance_date`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,4,'2026-10-06','2026-10-06 23:38:04',NULL,'MANUAL','PRESENT',NULL),(2,4,'2026-10-07','2026-10-07 00:43:13','2026-10-07 00:43:17','MANUAL','PRESENT',NULL);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_completion`
--

DROP TABLE IF EXISTS `exercise_completion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_completion` (
  `completion_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `exercise_id` int NOT NULL,
  `completion_date` date NOT NULL,
  `completed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`completion_id`),
  UNIQUE KEY `member_id` (`member_id`,`exercise_id`,`completion_date`),
  KEY `exercise_id` (`exercise_id`),
  CONSTRAINT `exercise_completion_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE,
  CONSTRAINT `exercise_completion_ibfk_2` FOREIGN KEY (`exercise_id`) REFERENCES `workout_exercises` (`exercise_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_completion`
--

LOCK TABLES `exercise_completion` WRITE;
/*!40000 ALTER TABLE `exercise_completion` DISABLE KEYS */;
INSERT INTO `exercise_completion` VALUES (1,4,1,'2026-10-07',1),(4,4,2,'2026-10-07',1);
/*!40000 ALTER TABLE `exercise_completion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym_packages`
--

DROP TABLE IF EXISTS `gym_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gym_packages` (
  `package_id` int NOT NULL AUTO_INCREMENT,
  `package_name` varchar(100) NOT NULL,
  `description` text,
  `duration` int NOT NULL,
  `duration_unit` enum('DAY','MONTH','YEAR') NOT NULL DEFAULT 'MONTH',
  `price` decimal(10,2) NOT NULL,
  `features` text,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym_packages`
--

LOCK TABLES `gym_packages` WRITE;
/*!40000 ALTER TABLE `gym_packages` DISABLE KEYS */;
INSERT INTO `gym_packages` VALUES (1,'super30','Transform your body in 1 month',30,'DAY',15000.00,'24 hour open','ACTIVE','2026-10-05 17:00:42');
/*!40000 ALTER TABLE `gym_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_profiles`
--

DROP TABLE IF EXISTS `member_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_profiles` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `emergency_contact` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `member_profiles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_profiles`
--

LOCK TABLES `member_profiles` WRITE;
/*!40000 ALTER TABLE `member_profiles` DISABLE KEYS */;
INSERT INTO `member_profiles` VALUES (1,4,'achu','2000-10-21','Male','njfnkjnfk','9328983942'),(2,5,'afthab','2007-10-23','Male','hdsgfvdhvj','9797979797'),(3,6,'hrishika','2007-02-12','Female','bdfdj','8989898989'),(4,7,'ahbishek','2006-05-20','Male','hdschjd','8787878787');
/*!40000 ALTER TABLE `member_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_progress`
--

DROP TABLE IF EXISTS `member_progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_progress` (
  `progress_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `progress_date` date NOT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `height` decimal(5,2) DEFAULT NULL,
  `bmi` decimal(5,2) DEFAULT NULL,
  `body_fat_percentage` decimal(5,2) DEFAULT NULL,
  `notes` text,
  PRIMARY KEY (`progress_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `member_progress_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_progress`
--

LOCK TABLES `member_progress` WRITE;
/*!40000 ALTER TABLE `member_progress` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_trainers`
--

DROP TABLE IF EXISTS `member_trainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_trainers` (
  `assignment_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `trainer_id` int NOT NULL,
  `assigned_date` date NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`assignment_id`),
  KEY `member_id` (`member_id`),
  KEY `trainer_id` (`trainer_id`),
  CONSTRAINT `member_trainers_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE,
  CONSTRAINT `member_trainers_ibfk_2` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_trainers`
--

LOCK TABLES `member_trainers` WRITE;
/*!40000 ALTER TABLE `member_trainers` DISABLE KEYS */;
INSERT INTO `member_trainers` VALUES (1,4,1,'2026-10-06','ACTIVE');
/*!40000 ALTER TABLE `member_trainers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(150) NOT NULL,
  `message` text NOT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `subscription_id` int NOT NULL,
  `member_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `transaction_id` varchar(100) DEFAULT NULL,
  `payment_method` enum('CASH','ONLINE','UPI','CARD') NOT NULL,
  `payment_status` enum('PENDING','SUCCESS','FAILED','REFUNDED') NOT NULL DEFAULT 'PENDING',
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `transaction_id` (`transaction_id`),
  KEY `subscription_id` (`subscription_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`subscription_id`) ON DELETE CASCADE,
  CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,1,15000.00,'GYM-BCC2BFC9','UPI','SUCCESS','2026-10-05 18:34:19'),(2,2,4,15000.00,'plink_Tkgyn7MDvQ8qNL','ONLINE','SUCCESS','2026-10-06 16:42:45');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `subscription_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `package_id` int NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` enum('ACTIVE','EXPIRED','CANCELLED','PENDING') NOT NULL DEFAULT 'PENDING',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`subscription_id`),
  KEY `member_id` (`member_id`),
  KEY `package_id` (`package_id`),
  CONSTRAINT `subscriptions_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE,
  CONSTRAINT `subscriptions_ibfk_2` FOREIGN KEY (`package_id`) REFERENCES `gym_packages` (`package_id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES (1,1,1,'2026-10-06','2026-11-05',15000.00,'ACTIVE','2026-10-05 18:34:19'),(2,4,1,'2026-10-06','2026-11-05',15000.00,'ACTIVE','2026-10-06 16:42:45');
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer_applications`
--

DROP TABLE IF EXISTS `trainer_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer_applications` (
  `application_id` int NOT NULL AUTO_INCREMENT,
  `trainer_id` int NOT NULL,
  `application_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('PENDING','ACCEPTED','REJECTED') NOT NULL DEFAULT 'PENDING',
  `reviewed_date` timestamp NULL DEFAULT NULL,
  `rejection_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`application_id`),
  KEY `trainer_id` (`trainer_id`),
  CONSTRAINT `trainer_applications_ibfk_1` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_applications`
--

LOCK TABLES `trainer_applications` WRITE;
/*!40000 ALTER TABLE `trainer_applications` DISABLE KEYS */;
INSERT INTO `trainer_applications` VALUES (1,1,'2026-10-05 16:03:48','ACCEPTED','2026-10-05 16:44:26',NULL);
/*!40000 ALTER TABLE `trainer_applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer_profiles`
--

DROP TABLE IF EXISTS `trainer_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainer_profiles` (
  `trainer_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `qualification` varchar(150) DEFAULT NULL,
  `specialization` varchar(150) DEFAULT NULL,
  `experience_years` int DEFAULT '0',
  `certification` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`trainer_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `trainer_profiles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer_profiles`
--

LOCK TABLES `trainer_profiles` WRITE;
/*!40000 ALTER TABLE `trainer_profiles` DISABLE KEYS */;
INSERT INTO `trainer_profiles` VALUES (1,3,'FidhaFathima','2006-10-28','Female','sfhvh','vvhbvhjbfhv','sbcshjbvhb',3,'bfjbjgbb');
/*!40000 ALTER TABLE `trainer_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `role` enum('ADMIN','TRAINER','MEMBER') NOT NULL,
  `status` enum('ACTIVE','PENDING','REJECTED','BLOCKED') NOT NULL DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin123','admin@gym.com','9999999999','ADMIN','ACTIVE','2026-10-05 13:51:51'),(3,'fidha','fidha@123','ff080870@gmail.com','9037472851','TRAINER','ACTIVE','2026-10-05 16:03:48'),(4,'achuu123','Achu@1234','achu@gmail.com','9037472851','MEMBER','ACTIVE','2026-10-05 18:34:12'),(5,'afthabo','Afthabo@123','afthab@gmail.com','9090909090','MEMBER','ACTIVE','2026-10-06 14:47:35'),(6,'hrishii','Hrishi@123','hrish@gmail.com','9898989887','MEMBER','ACTIVE','2026-10-06 15:07:30'),(7,'abhi','Abhi@123','abhi@gmail.com','9897979797','MEMBER','ACTIVE','2026-10-06 16:41:50');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_exercises`
--

DROP TABLE IF EXISTS `workout_exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_exercises` (
  `exercise_id` int NOT NULL AUTO_INCREMENT,
  `plan_id` int NOT NULL,
  `exercise_name` varchar(100) NOT NULL,
  `sets` int DEFAULT NULL,
  `repetitions` int DEFAULT NULL,
  `duration_minutes` int DEFAULT NULL,
  `instructions` text,
  PRIMARY KEY (`exercise_id`),
  KEY `plan_id` (`plan_id`),
  CONSTRAINT `workout_exercises_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `workout_plans` (`plan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_exercises`
--

LOCK TABLES `workout_exercises` WRITE;
/*!40000 ALTER TABLE `workout_exercises` DISABLE KEYS */;
INSERT INTO `workout_exercises` VALUES (1,1,'pushup',12,3,10,'gshfgdhfhj'),(2,1,'pull up',NULL,34,7,'bsbcbc');
/*!40000 ALTER TABLE `workout_exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_plans`
--

DROP TABLE IF EXISTS `workout_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_plans` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `trainer_id` int NOT NULL,
  `member_id` int NOT NULL,
  `plan_name` varchar(100) NOT NULL,
  `description` text,
  `created_date` date NOT NULL,
  `status` enum('ACTIVE','COMPLETED','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`plan_id`),
  KEY `trainer_id` (`trainer_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `workout_plans_ibfk_1` FOREIGN KEY (`trainer_id`) REFERENCES `trainer_profiles` (`trainer_id`) ON DELETE CASCADE,
  CONSTRAINT `workout_plans_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member_profiles` (`member_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_plans`
--

LOCK TABLES `workout_plans` WRITE;
/*!40000 ALTER TABLE `workout_plans` DISABLE KEYS */;
INSERT INTO `workout_plans` VALUES (1,1,4,'Muscle Gain','vcdfcvcdbncfhi','2026-10-07','ACTIVE');
/*!40000 ALTER TABLE `workout_plans` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-07  2:36:49
