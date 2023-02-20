-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 20, 2023 at 12:45 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `client_visit`
--

CREATE TABLE `client_visit` (
  `id` bigint(20) NOT NULL,
  `sales_manager` varchar(100) DEFAULT NULL,
  `salesperson` varchar(100) DEFAULT NULL,
  `visit_date` date DEFAULT NULL,
  `personal_information_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `goals`
--

CREATE TABLE `goals` (
  `id` bigint(20) NOT NULL,
  `budget` int(11) DEFAULT NULL,
  `date_data_entry` date DEFAULT NULL,
  `expectations` text DEFAULT NULL,
  `outcome` text DEFAULT NULL,
  `personal_information_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hair_loss`
--

CREATE TABLE `hair_loss` (
  `id` bigint(20) NOT NULL,
  `cause` text DEFAULT NULL,
  `extent` text DEFAULT NULL,
  `pattern` text DEFAULT NULL,
  `personal_information_id` bigint(20) DEFAULT NULL,
  `date_data_entry` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lifestyle`
--

CREATE TABLE `lifestyle` (
  `id` bigint(20) NOT NULL,
  `alcohol` varchar(255) DEFAULT NULL,
  `date_data_entry` datetime(6) DEFAULT NULL,
  `diet` text DEFAULT NULL,
  `exercise` text DEFAULT NULL,
  `tobacco` varchar(255) DEFAULT NULL,
  `personal_information_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `medical_history`
--

CREATE TABLE `medical_history` (
  `id` bigint(20) NOT NULL,
  `allergies` varchar(255) DEFAULT NULL,
  `current_medications` varchar(255) DEFAULT NULL,
  `pre_existing_conditions` varchar(255) DEFAULT NULL,
  `previous_transplants` varchar(255) DEFAULT NULL,
  `personal_information_id` bigint(20) DEFAULT NULL,
  `date_data_entry` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `personal_information`
--

CREATE TABLE `personal_information` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personal_information`
--

INSERT INTO `personal_information` (`id`, `address`, `age`, `email`, `phone_number`, `firstname`, `lastname`) VALUES
(60, '04 rue jbal rsas cité el ghazela', 26, 'yassine.hamza@sesame.com.tnz', '50743588', 'Yassine', 'Hamza'),
(61, '04 rue jbal rsas cité el ghazela', 29, 'yassinehamza1996@gmail.com', '5073588', 'Khaled', 'Fadl'),
(62, '04 rue jbal rsas cité el ghazela', 26, 'yassine.hamza@sesame.com.egypt', '50743588', 'Khaled', 'Fadl'),
(63, '04 rue jbal rsas cité el ghazela', 29, 'marwa@gmail.com', '507435988888', 'Marwa', 'Hamza');

-- --------------------------------------------------------

--
-- Table structure for table `personal_information_seq`
--

CREATE TABLE `personal_information_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `last_login` date DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_visit`
--
ALTER TABLE `client_visit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpsewu7txbl2f722jffqj2v9i9` (`personal_information_id`);

--
-- Indexes for table `goals`
--
ALTER TABLE `goals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3qk2bg1kcrk13spidqby16qjw` (`personal_information_id`);

--
-- Indexes for table `hair_loss`
--
ALTER TABLE `hair_loss`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa8cbpdoss94p1lafglk1wo1vn` (`personal_information_id`);

--
-- Indexes for table `lifestyle`
--
ALTER TABLE `lifestyle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmeqym2kjrsn4qif2r1fcb5rgc` (`personal_information_id`);

--
-- Indexes for table `medical_history`
--
ALTER TABLE `medical_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeo4l8o9e4sr5q4cud8mv2p4wp` (`personal_information_id`);

--
-- Indexes for table `personal_information`
--
ALTER TABLE `personal_information`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKgtpwt6ldldxaiy5wcgl6d90sd` (`email`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client_visit`
--
ALTER TABLE `client_visit`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `goals`
--
ALTER TABLE `goals`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hair_loss`
--
ALTER TABLE `hair_loss`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lifestyle`
--
ALTER TABLE `lifestyle`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `medical_history`
--
ALTER TABLE `medical_history`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client_visit`
--
ALTER TABLE `client_visit`
  ADD CONSTRAINT `FKpsewu7txbl2f722jffqj2v9i9` FOREIGN KEY (`personal_information_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `goals`
--
ALTER TABLE `goals`
  ADD CONSTRAINT `FK3qk2bg1kcrk13spidqby16qjw` FOREIGN KEY (`personal_information_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `hair_loss`
--
ALTER TABLE `hair_loss`
  ADD CONSTRAINT `FKa8cbpdoss94p1lafglk1wo1vn` FOREIGN KEY (`personal_information_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `lifestyle`
--
ALTER TABLE `lifestyle`
  ADD CONSTRAINT `FKmeqym2kjrsn4qif2r1fcb5rgc` FOREIGN KEY (`personal_information_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `medical_history`
--
ALTER TABLE `medical_history`
  ADD CONSTRAINT `FKeo4l8o9e4sr5q4cud8mv2p4wp` FOREIGN KEY (`personal_information_id`) REFERENCES `personal_information` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
