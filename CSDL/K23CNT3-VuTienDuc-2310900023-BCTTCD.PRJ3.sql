-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: fast_food_db
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `binh_luan`
--

DROP TABLE IF EXISTS `binh_luan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `binh_luan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `danh_gia` int NOT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `noi_dung` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mon_an_id` bigint DEFAULT NULL,
  `nguoi_dung_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcejejfoteay9u469vm1jg3wge` (`mon_an_id`),
  KEY `FK9t033bi499huul59bresxl05o` (`nguoi_dung_id`),
  CONSTRAINT `FK9t033bi499huul59bresxl05o` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoi_dung` (`id`),
  CONSTRAINT `FKcejejfoteay9u469vm1jg3wge` FOREIGN KEY (`mon_an_id`) REFERENCES `mon_an` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `binh_luan`
--

LOCK TABLES `binh_luan` WRITE;
/*!40000 ALTER TABLE `binh_luan` DISABLE KEYS */;
INSERT INTO `binh_luan` VALUES (3,4,'2025-12-05 20:00:00.000000','Rất ngon, sẽ đặt lần nữa',1,3),(4,2,'2025-12-07 10:00:00.000000','Chưa đạt chuẩn như quảng cáo',1,1),(5,5,'2025-12-07 15:45:00.000000','Tuyệt vời, giao hàng nhanh',2,2),(6,5,'2025-12-11 08:00:31.879502','Hehe',15,2),(7,1,'2025-12-11 08:06:32.708074','không ngon',2,3),(8,3,'2025-12-11 10:10:17.293223','Tạm! chưa làm tôi hài lòng lắm',20,2),(9,5,'2025-12-12 12:14:42.472252','Ai làm ngon thế\r\n',15,1),(10,2,'2025-12-12 12:15:41.851586','Ảnh hơi mờ',11,1),(11,1,'2025-12-12 12:20:16.089369','s',2,1),(12,4,'2025-12-13 06:54:19.298696','Rất ngon và bổ dưỡng',22,2),(13,2,'2025-12-13 07:04:55.497392','không ngon',18,2),(14,3,'2025-12-16 12:39:10.044928','ngon ',23,2),(15,4,'2025-12-16 13:43:17.426938','tuyệt',2,2),(16,4,'2025-12-16 14:03:25.477884','tôi đa thử nhiều lần rùi ăn rất ngon',23,2),(17,3,'2025-12-17 15:14:30.859094','Hơi ít',11,2),(18,5,'2025-12-18 18:15:24.599518','ngon cay nóng bỏng',25,2),(19,5,'2025-12-18 18:22:16.313978','Tôi rất thích món ăn này',1,2),(20,5,'2025-12-28 12:01:14.542094','Ngon luôn',20,9);
/*!40000 ALTER TABLE `binh_luan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_don_hang`
--

DROP TABLE IF EXISTS `chi_tiet_don_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_don_hang` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `so_luong` int NOT NULL,
  `don_hang_id` bigint DEFAULT NULL,
  `mon_an_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt57maavf6s28hxyar724mdr1b` (`don_hang_id`),
  KEY `FK3qvud4ryqt2d7sq5m6ssb9scw` (`mon_an_id`),
  CONSTRAINT `FK3qvud4ryqt2d7sq5m6ssb9scw` FOREIGN KEY (`mon_an_id`) REFERENCES `mon_an` (`id`),
  CONSTRAINT `FKt57maavf6s28hxyar724mdr1b` FOREIGN KEY (`don_hang_id`) REFERENCES `don_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_don_hang`
--

LOCK TABLES `chi_tiet_don_hang` WRITE;
/*!40000 ALTER TABLE `chi_tiet_don_hang` DISABLE KEYS */;
INSERT INTO `chi_tiet_don_hang` VALUES (37,1,36,18),(38,1,37,11),(39,1,38,18),(40,2,39,22),(41,2,39,23),(42,1,40,1),(43,1,41,18),(44,1,42,1),(45,2,42,2),(46,1,43,22),(47,1,44,2),(48,2,45,2),(49,1,46,2),(50,2,47,2),(51,1,48,2),(52,1,48,19),(53,1,48,23),(54,1,49,19),(55,1,50,11),(56,1,51,20),(57,1,52,19),(58,2,53,22),(59,1,53,11),(60,1,54,11),(61,1,55,22),(62,1,56,2),(63,2,57,2),(64,2,57,22),(75,1,61,1),(76,2,61,2),(77,2,61,18),(78,1,61,34),(79,2,61,22),(80,2,61,23),(81,3,61,41),(82,1,62,2),(83,1,63,11),(84,1,64,2),(85,1,65,11),(86,2,66,19),(87,1,67,19),(88,1,67,20);
/*!40000 ALTER TABLE `chi_tiet_don_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `don_hang`
--

DROP TABLE IF EXISTS `don_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `don_hang` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_chi_giao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_dat` datetime(6) DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` enum('CHO_XU_LY','DANG_XU_LY','DANG_GIAO','DA_GIAO','DA_HUY') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `giam_gia_id` bigint DEFAULT NULL,
  `nguoi_dung_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKedqfyod8mmg7lijgkjxs8tbwn` (`giam_gia_id`),
  KEY `FK3tq0qg6f6ranwlr8gvfii79d3` (`nguoi_dung_id`),
  CONSTRAINT `FK3tq0qg6f6ranwlr8gvfii79d3` FOREIGN KEY (`nguoi_dung_id`) REFERENCES `nguoi_dung` (`id`),
  CONSTRAINT `FKedqfyod8mmg7lijgkjxs8tbwn` FOREIGN KEY (`giam_gia_id`) REFERENCES `giam_gia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `don_hang`
--

LOCK TABLES `don_hang` WRITE;
/*!40000 ALTER TABLE `don_hang` DISABLE KEYS */;
INSERT INTO `don_hang` VALUES (36,'hà nội','2025-12-15 07:17:34.031014','0396705599','CHO_XU_LY',5,2),(37,'Yên Bái','2025-12-15 07:49:39.698630','0396705599','CHO_XU_LY',2,6),(38,'TP.HCM','2025-12-15 08:05:51.301716','0911111111','CHO_XU_LY',NULL,2),(39,'Yên Bái','2025-12-15 08:14:47.884080','0396705599','DANG_XU_LY',7,6),(40,'Yên Bái','2025-12-15 08:23:31.563358','0396705599','CHO_XU_LY',NULL,6),(41,'TP.HCM','2025-12-15 08:24:44.828118','0911111111','CHO_XU_LY',5,2),(42,'TP.HCM','2025-12-15 08:28:02.599706','0911111111','CHO_XU_LY',5,2),(43,'TP.HCM','2025-12-15 08:28:20.566678','0911111111','DA_GIAO',NULL,2),(44,'Yên Bái','2025-12-16 11:29:03.028758','0396705599','CHO_XU_LY',NULL,6),(45,'TP.HCM','2025-12-16 12:39:38.696802','0911111111','DANG_XU_LY',7,2),(46,'TP.HCM','2025-12-16 12:43:23.028825','0911111111','DA_GIAO',5,2),(47,'dđ','2025-12-16 13:46:17.061402','0911111111','DA_HUY',NULL,2),(48,'TP.HCM','2025-12-16 14:08:13.931835','0911111111','DANG_XU_LY',NULL,2),(49,'Nam Định','2025-12-16 14:11:04.026579','0396705599','DA_HUY',NULL,7),(50,'25 trần phú','2025-12-17 15:15:02.770082','0943572196','DA_HUY',NULL,2),(51,'KM15 yên bái','2025-12-17 15:38:12.685470','0396705599','DANG_XU_LY',NULL,6),(52,'Thái Bình','2025-12-17 16:25:47.464415','0943572199','DANG_GIAO',NULL,2),(53,'Nam Dinh','2025-12-18 17:21:47.970051','0396705599','CHO_XU_LY',NULL,7),(54,'Nam Định','2025-12-18 17:23:13.666385','0396705599','DANG_GIAO',NULL,7),(55,'Yên Bái','2025-12-18 17:28:53.938844','0396705599','DANG_GIAO',5,6),(56,'Nam Định','2025-12-18 17:41:24.035110','0396705599','CHO_XU_LY',7,7),(57,'Nam Định','2025-12-18 17:42:39.885570','0396705599','DA_HUY',5,7),(61,'ngõ 50 hà đông.','2025-12-21 12:42:34.065400','0943572199','DA_GIAO',7,2),(62,'Thái Bình','2025-12-21 12:43:34.001443','0943572199','DA_GIAO',5,2),(63,'Nam Định','2025-12-21 18:24:19.042203','0396705599','DA_GIAO',5,7),(64,'Nam Định','2025-12-21 18:25:00.637840','0396705599','DA_GIAO',NULL,7),(65,'Nam Định','2025-12-21 18:25:40.518032','0396705599','DA_HUY',NULL,7),(66,'nam định','2025-12-28 11:59:16.691197','0911111111','DA_GIAO',5,9),(67,'121','2025-12-28 12:02:11.001330','0911111111','DANG_XU_LY',5,9);
/*!40000 ALTER TABLE `don_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giam_gia`
--

DROP TABLE IF EXISTS `giam_gia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giam_gia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia_tri` double NOT NULL,
  `la_phan_tram` bit(1) NOT NULL,
  `ma_giam_gia` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_bat_dau` datetime(6) DEFAULT NULL,
  `ngay_ket_thuc` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giam_gia`
--

LOCK TABLES `giam_gia` WRITE;
/*!40000 ALTER TABLE `giam_gia` DISABLE KEYS */;
INSERT INTO `giam_gia` VALUES (1,10,_binary '','WELCOME10','2025-12-03 12:35:55.707561','2026-01-03 12:35:55.707561'),(2,10,_binary '','DISCOUNT10','2025-12-07 19:20:22.000000','2026-01-06 19:20:22.000000'),(5,11,_binary '','Giảm 10%','2025-12-07 13:37:00.000000','2026-01-05 13:37:00.000000'),(7,99000,_binary '\0','Giam99K','2025-12-17 12:01:00.000000','2025-12-27 12:01:00.000000');
/*!40000 ALTER TABLE `giam_gia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mon_an`
--

DROP TABLE IF EXISTS `mon_an`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mon_an` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia` double NOT NULL,
  `mo_ta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `the_loai_id` bigint DEFAULT NULL,
  `gia_cu` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg3fhij0w67oxpuguktv6piib8` (`the_loai_id`),
  CONSTRAINT `FKg3fhij0w67oxpuguktv6piib8` FOREIGN KEY (`the_loai_id`) REFERENCES `the_loai` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mon_an`
--

LOCK TABLES `mon_an` WRITE;
/*!40000 ALTER TABLE `mon_an` DISABLE KEYS */;
INSERT INTO `mon_an` VALUES (1,45000,'Burger bò phô mai với miếng bò xay nướng chín mềm, phủ lớp phô mai béo ngậy tan chảy, kèm rau xà lách tươi, cà chua và sốt đặc trưng. Hương vị đậm đà, thơm ngon, thích hợp cho bữa ăn nhanh đầy năng lượng.','Burger Bò Phô Mai',1,50000),(2,95000,'Pizza hải sản với lớp đế bánh mềm xốp, phủ phô mai mozzarella béo ngậy, kết hợp tôm, mực tươi và sốt cà chua đậm đà. Hương vị thơm ngon, hấp dẫn, thích hợp dùng cho bữa ăn nhanh hoặc chia sẻ cùng bạn bè.','Pizza Hải Sản',2,139000),(11,65000,'Chân gà được làm sạch, dai giòn vừa ăn, trộn cùng sốt Thái chua cay đậm vị, kết hợp sả, tắc và ớt tươi. Món ăn hấp dẫn, kích thích vị giác, rất được ưa chuộng cho bữa ăn vặt.','Chân Gà Sốt Thái & Gỏi Bạch Tuộc',20,70000),(15,39000,'Gà rán vàng giòn, vị cay nhẹ, thơm ngon và hấp dẫn.','Gà Rán Giòn Cay',3,55000),(18,30000,'Xúc xích Đức nướng thơm, vỏ giòn, thịt đậm vị.','Xúc Xích Đức Nướng',6,NULL),(19,66000,'vị đậm đà cay nồng.','Chân Gà rút xương',20,50000),(20,10000,'xúc xích nướng cay nồng','Xúc Xích nướng',6,NULL),(22,25000,'thơm ngon khó cưỡng\r\n','Nước ép sinh tố kiwi',4,NULL),(23,30000,'10Đ không có nhưng','Trà sữa trân châu đường đen',4,NULL),(25,35000,'Mì cay cấp đồ 7. ','Mì Cay hải sản',12,NULL),(34,20000,'Trà đào mát lạnh, vị ngọt thanh, giải khát hiệu quả.','Trà Đào',4,NULL),(36,55000,'Chân gà giòn dai trộn sốt Thái chua cay, rất được ưa chuộng.','Chân Gà Sốt Thái',20,NULL),(40,25000,'Khoai tây chiên vàng giòn, ăn kèm tương cà hoặc sốt.','Khoai Tây Chiên',13,NULL),(41,60000,'Chân gà nướng thơm lừng, áo mật ong ngọt nhẹ.','Chân Gà Nướng Mật Ong',14,NULL),(42,30000,'Xúc xích chiên nóng hổi, vỏ giòn, ăn kèm tương ớt.','Xúc Xích Chiên',9,NULL),(43,70000,'Bạch tuộc nướng sa tế cay nồng, dai giòn hấp dẫn.','Bạch Tuộc Nướng Sa Tế',15,NULL),(44,40000,'Tokbokki Hàn Quốc sốt cay ngọt, bánh gạo dẻo mềm.','Tokbokki Cay',16,NULL),(45,65000,'Gỏi bạch tuộc trộn rau củ tươi và nước mắm chua cay.','Gỏi Bạch Tuộc',17,NULL),(46,30000,'Phô mai que chiên giòn, kéo sợi béo ngậy.','Phô Mai Que',18,NULL),(47,45000,'Cơm gà xối mỡ nóng hổi, ăn kèm nước mắm tỏi ớt.','Cơm Gà Xối Mỡ',19,NULL),(48,25000,'Cá viên chiên vàng giòn, nóng hổi, chấm tương ớt.','Cá Viên Chiên',9,NULL),(49,30000,'Bò viên chiên dai ngon, đậm vị thịt.','Bò Viên Chiên',9,NULL),(51,30000,'Khoai lang kén chiên giòn bên ngoài, mềm ngọt bên trong.','Khoai Lang Kén',9,NULL),(52,35000,'Tôm viên chiên giòn rụm, thơm vị hải sản.','Tôm Viên Chiên',9,NULL);
/*!40000 ALTER TABLE `mon_an` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mon_an_hinh_anh`
--

DROP TABLE IF EXISTS `mon_an_hinh_anh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mon_an_hinh_anh` (
  `mon_an_id` bigint NOT NULL,
  `url_hinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hinh_anh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY `FKovj3unvrtpbm7l0w47d3gdljl` (`mon_an_id`),
  CONSTRAINT `FKovj3unvrtpbm7l0w47d3gdljl` FOREIGN KEY (`mon_an_id`) REFERENCES `mon_an` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mon_an_hinh_anh`
--

LOCK TABLES `mon_an_hinh_anh` WRITE;
/*!40000 ALTER TABLE `mon_an_hinh_anh` DISABLE KEYS */;
INSERT INTO `mon_an_hinh_anh` VALUES (23,'https://tse2.mm.bing.net/th/id/OIP.rJeTWE7NOPX7Ze3ffoHGgAHaFD?pid=Api&P=0&h=220',NULL),(22,'https://tse2.mm.bing.net/th/id/OIP.Puep979vQYeSOJ-loRZJBQHaFa?pid=Api&P=0&h=220',NULL),(25,'https://cdn.tgdd.vn/Files/2019/09/24/1201263/2-cach-nau-mi-cay-hai-san-chuan-cong-thuc-han-quoc-202112301425006195.jpg',NULL),(25,'https://haisanmino.com/wp-content/uploads/2024/09/cach-nau-mi-cay-hai-san-1.jpg',NULL),(1,'https://sp.yimg.com/ib/th/id/OIP.Frx6CdjDG5Dy2QBH4kRAjwHaIG?pid=Api&w=148&h=148&c=7&dpr=2&rs=1',NULL),(2,'https://tse2.mm.bing.net/th/id/OIP.CL8veu3EhvQshjMB3TAv8AHaE8?pid=Api&P=0&h=220',NULL),(15,'https://tiki.vn/blog/wp-content/uploads/2023/09/ga-ran-2.jpg',NULL),(18,'https://thitngonnhapkhau.vn/wp-content/uploads/2022/12/xuc-xich-duc-truyen-thong-thom-ngon-bo-duong-1.jpg',NULL),(19,'https://tse4.mm.bing.net/th/id/OIP.pSvkXtRKbyLwQYzeZg2NmwHaEL?pid=Api&P=0&h=220',NULL),(20,'https://tse1.mm.bing.net/th/id/OIP.Uqa6cpwaQGDA1n5VAP1a5gHaFa?pid=Api&P=0&h=220',NULL),(34,'https://tse2.mm.bing.net/th/id/OIP.7f4foR0ooEfowmHlLYEBLQHaHa?pid=Api&P=0&h=220',NULL),(34,'https://tse3.mm.bing.net/th/id/OIP.JqVKBVzJvVhbMTfwGTaFugHaHa?pid=Api&P=0&h=220',NULL),(36,'https://tse3.mm.bing.net/th/id/OIP.81GRwM2RUtECd19L5ruiIgHaEL?pid=Api&P=0&h=220',NULL),(40,'https://tse3.mm.bing.net/th/id/OIP.hlTf4Lr6n6kekyx3WCUkbAHaE4?pid=Api&P=0&h=220',NULL),(41,'https://tse1.mm.bing.net/th/id/OIP.I2o9rLME9H3MXCMf25dVLgHaE8?pid=Api&P=0&h=220',NULL),(43,'https://tse1.mm.bing.net/th/id/OIP.5M5xa2rORDa24STjYJ1lBAHaE8?pid=Api&P=0&h=220',NULL),(44,'https://tse2.mm.bing.net/th/id/OIP.AZWrKI3UVvjs54Qon2KDWgHaFI?pid=Api&P=0&h=220',NULL),(44,'https://tse2.mm.bing.net/th/id/OIP.obR56RKk7qzaH1OqVa7tvAHaE6?pid=Api&P=0&h=220',NULL),(44,'https://tse1.mm.bing.net/th/id/OIP._6BiypVNNoPQO7S1MN80xAHaEG?pid=Api&P=0&h=220',NULL),(45,'https://tse4.mm.bing.net/th/id/OIP.vEQclK7PEiXiCB4CNoFFrwHaFj?pid=Api&P=0&h=220',NULL),(46,'https://tse4.mm.bing.net/th/id/OIP.3IOcU2uvIiX7rNgK80SpzwHaHa?pid=Api&P=0&h=220',NULL),(47,'https://tse3.mm.bing.net/th/id/OIP.OWyEbZcCAboHJ6M4r_HT_wHaGH?pid=Api&P=0&h=220',NULL),(42,'https://tse1.mm.bing.net/th/id/OIP.BVK_V_aShYHcLVvmTjb8cAHaHa?pid=Api&P=0&h=220',NULL),(11,'https://down-bs-vn.img.susercontent.com/vn-11134513-7r98o-lsvc263zmqvte9@resize_ss640x400!@crop_w640_h400_cT',NULL),(11,'https://up.yimg.com/ib/th/id/OIP.0rkBhU2_LSejzcTXbMJ_2AHaD4?pid=Api&rs=1&c=1&qlt=95&w=180&h=94',NULL),(48,'https://tse4.mm.bing.net/th/id/OIP.xAbVyhbvGQxW8FeeDqnmCwHaET?pid=Api&P=0&h=220',NULL),(49,'https://tse4.mm.bing.net/th/id/OIP.oE75yyOHSvbqvSN8Euaj4gHaF8?pid=Api&P=0&h=220',NULL),(52,'https://tse1.mm.bing.net/th/id/OIP.MvMqzTV4LloFCSr_Ccxj5QHaGy?pid=Api&P=0&h=220',NULL),(51,'https://tse3.mm.bing.net/th/id/OIP.UJ_W817rm0sX6qiBwyno1wHaE8?pid=Api&P=0&h=220',NULL);
/*!40000 ALTER TABLE `mon_an_hinh_anh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoi_dung`
--

DROP TABLE IF EXISTS `nguoi_dung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoi_dung` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vai_tro_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa5oibkto18llfdid5w4mv4v47` (`vai_tro_id`),
  CONSTRAINT `FKa5oibkto18llfdid5w4mv4v47` FOREIGN KEY (`vai_tro_id`) REFERENCES `vai_tro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoi_dung`
--

LOCK TABLES `nguoi_dung` WRITE;
/*!40000 ALTER TABLE `nguoi_dung` DISABLE KEYS */;
INSERT INTO `nguoi_dung` VALUES (1,'Hà Nội','admin@food.com','$2a$10$25lGaC.TueVvPYDnktIMZu9sv8.oPRSK/AKn0QNmm1mbKQSYACoqu','0900000000','Administrator',2),(2,'Thái Bình','user@food.com','$2a$10$UGKgHXK/lhIjKcMZP1x1w.NaaMR9TJ0C43jr6gdzxl4fi0lI/tBke','0943572199','Nguyễn Anh Tuấn',1),(3,'hà nội','nguyenanhvu@gmail.com','$2a$10$WmrHFzvN8pU6sxFgQ0f2De8rJarL46cC8SRoHjudyx9Q1xXfb7kfO','09666555','Nguyễn anh vũ',1),(4,'Phú Thọ','pminh@gmail.com','$2a$10$Gym3p4m22NrSXUAMWR4eXeO.Hz82cliBwF262KvncmBMUDPFLHuga','036545861111','Phan Quang Minh',1),(6,'Yên Bái','vuducc@gmail.com','$2a$10$UZkCNHKfIatM7r5lCBLD9uHbMXP8IaEeYnogV59xewObGwISqjmyi','0396705599','Vũ Tiến Đức',1),(7,'Nam Định','tranhoang@gmail.com','$2a$10$nSMlyjlReX24f/b8pdexZuv5ZpZaapvv9Bg9BEcc7/0cW510b9hte','0396705599','Trần Văn Hoàng',1),(8,'Phú Thọ','name@gmail.com','$2a$10$SxPUqpmZwiEForoPWniMZO/6XVMuFCC/qnqDdAzg/H.EOnlkeJXHq','0943572199','Nguyễn Văn A1',1),(9,'121','acclone@mail666.com','$2a$10$OAwazPUVTKEnErqZd95g.efARsQXZxw1732UNiWvLKFDlQzh1ja4G','0911111111','Acclone',1);
/*!40000 ALTER TABLE `nguoi_dung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanh_toan`
--

DROP TABLE IF EXISTS `thanh_toan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanh_toan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ngay_thanh_toan` datetime(6) DEFAULT NULL,
  `phuong_thuc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `so_tien` double NOT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `don_hang_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6g9iab7wiakoroe4orau2yb4i` (`don_hang_id`),
  CONSTRAINT `FKhuic4h1i8fvdu4wlqrn04e2b7` FOREIGN KEY (`don_hang_id`) REFERENCES `don_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanh_toan`
--

LOCK TABLES `thanh_toan` WRITE;
/*!40000 ALTER TABLE `thanh_toan` DISABLE KEYS */;
INSERT INTO `thanh_toan` VALUES (35,'2025-12-15 07:17:34.053091','COD',71200,'CHUA_THANH_TOAN',36),(36,'2025-12-15 07:49:39.713049','ONLINE',67500,'DA_THANH_TOAN',37),(37,'2025-12-15 08:05:51.308914','COD',80000,'CHUA_THANH_TOAN',38),(38,'2025-12-15 08:14:47.900727','ONLINE',11000,'DA_THANH_TOAN',39),(39,'2025-12-15 08:23:31.563358','COD',66000,'CHUA_THANH_TOAN',40),(40,'2025-12-15 08:24:44.839856','ONLINE',71200,'CHUA_THANH_TOAN',41),(41,'2025-12-15 08:28:02.622820','COD',323960,'CHUA_THANH_TOAN',42),(42,'2025-12-15 08:28:20.571045','ONLINE',25000,'DA_THANH_TOAN',43),(43,'2025-12-16 11:29:03.035843','COD',149000,'CHUA_THANH_TOAN',44),(44,'2025-12-16 12:39:38.705327','COD',199000,'CHUA_THANH_TOAN',45),(45,'2025-12-16 12:43:23.039395','ONLINE',132610,'DA_THANH_TOAN',46),(46,'2025-12-16 13:46:17.069405','ONLINE',298000,'HOAN_TIEN',47),(47,'2025-12-16 14:08:13.945788','ONLINE',245000,'DA_THANH_TOAN',48),(48,'2025-12-16 14:11:04.031498','COD',66000,'CHUA_THANH_TOAN',49),(49,'2025-12-17 15:15:02.775176','ONLINE',75000,'HOAN_TIEN',50),(50,'2025-12-17 15:38:12.691582','ONLINE',10000,'DA_THANH_TOAN',51),(51,'2025-12-17 16:25:47.472619','COD',66000,'CHUA_THANH_TOAN',52),(52,'2025-12-18 17:21:47.982365','COD',125000,'CHUA_THANH_TOAN',53),(53,'2025-12-18 17:23:13.668808','COD',75000,'CHUA_THANH_TOAN',54),(54,'2025-12-18 17:28:53.955445','COD',22250,'CHUA_THANH_TOAN',55),(55,'2025-12-18 17:41:24.049256','COD',50000,'CHUA_THANH_TOAN',56),(56,'2025-12-18 17:42:39.902487','ONLINE',309720,'HOAN_TIEN',57),(60,'2025-12-21 12:42:34.098930','ONLINE',506000,'DA_THANH_TOAN',61),(61,'2025-12-21 12:48:48.003240','COD',84550,'DA_THANH_TOAN',62),(62,'2025-12-21 18:24:40.491890','COD',57850,'DA_THANH_TOAN',63),(63,'2025-12-21 18:25:21.878930','ONLINE',95000,'DA_THANH_TOAN',64),(64,'2025-12-21 18:25:40.522135','ONLINE',65000,'HOAN_TIEN',65),(65,'2025-12-28 11:59:45.939979','COD',117480,'DA_THANH_TOAN',66),(66,'2025-12-28 12:02:11.014293','ONLINE',67640,'DA_THANH_TOAN',67);
/*!40000 ALTER TABLE `thanh_toan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `the_loai`
--

DROP TABLE IF EXISTS `the_loai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `the_loai` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ten_the_loai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `the_loai`
--

LOCK TABLES `the_loai` WRITE;
/*!40000 ALTER TABLE `the_loai` DISABLE KEYS */;
INSERT INTO `the_loai` VALUES (1,'Burger'),(2,'Pizza'),(3,'Gà Rán'),(4,'Đồ Uống'),(6,'Xúc Xích'),(9,'Đồ ăn vặt'),(12,'Mì'),(13,'Đồ chiên'),(14,'Đồ nướng'),(15,'Hải sản'),(16,'Món cay'),(17,'Món trộn - gỏi'),(18,'Món phô mai'),(19,'Cơm nhanh'),(20,'Chân gà');
/*!40000 ALTER TABLE `the_loai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vai_tro`
--

DROP TABLE IF EXISTS `vai_tro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vai_tro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ten_vai_tro` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKormf7tcbj4pefwi5xbm37hc9b` (`ten_vai_tro`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vai_tro`
--

LOCK TABLES `vai_tro` WRITE;
/*!40000 ALTER TABLE `vai_tro` DISABLE KEYS */;
INSERT INTO `vai_tro` VALUES (2,'ADMIN'),(1,'USER');
/*!40000 ALTER TABLE `vai_tro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-29 13:52:39
