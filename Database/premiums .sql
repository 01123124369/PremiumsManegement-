-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 12 أكتوبر 2018 الساعة 15:02
-- إصدار الخادم: 10.1.34-MariaDB
-- PHP Version: 5.6.37

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `premiums`
--

-- --------------------------------------------------------

--
-- بنية الجدول `client_info`
--

CREATE TABLE `client_info` (
  `id` int(11) NOT NULL,
  `full_name` varchar(600) NOT NULL,
  `jop` varchar(265) DEFAULT NULL,
  `credit_id` varchar(70) NOT NULL,
  `client_address` varchar(256) DEFAULT NULL,
  `phone_number` varchar(70) DEFAULT NULL,
  `place` varchar(200) NOT NULL,
  `guarantor` varchar(400) DEFAULT NULL,
  `guarantor_phone` varchar(265) DEFAULT NULL,
  `premuim_value` int(11) NOT NULL,
  `remainedmoney` int(11) NOT NULL,
  `premuim_date` date NOT NULL,
  `evaulation` int(11) NOT NULL,
  `const_remained` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- بنية الجدول `ended_accounts`
--

CREATE TABLE `ended_accounts` (
  `id` int(11) NOT NULL,
  `full_name` varchar(500) NOT NULL,
  `evaulation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- بنية الجدول `money`
--

CREATE TABLE `money` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- بنية الجدول `new_treats`
--

CREATE TABLE `new_treats` (
  `id` int(11) NOT NULL,
  `id_em` int(11) NOT NULL,
  `payer_or_newclient` tinyint(1) NOT NULL,
  `user_id` int(11) NOT NULL,
  `money` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- بنية الجدول `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf16 DEFAULT NULL,
  `user_pass` varchar(256) CHARACTER SET utf8 DEFAULT NULL,
  `is_used` tinyint(1) NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_info`
--
ALTER TABLE `client_info`
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `ended_accounts`
--
ALTER TABLE `ended_accounts`
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `money`
--
ALTER TABLE `money`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
