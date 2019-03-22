-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2018 at 08:03 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop`
--
CREATE DATABASE IF NOT EXISTS `shop` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `shop`;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` char(50) NOT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `max_credit` double DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`Id`, `Name`, `Address`, `max_credit`, `email`) VALUES
(1, 'David Odinga', 'Kenya, PO BOX 120, Sawagongo', 10000, 'divadagnido96@gmail.com'),
(2, 'Emanuel Juma', 'Jomo Kenyatta street,Mkulima House, 4th  floour', 0, 'juma@gmail.com'),
(3, 'Kemboi', 'PO BOX 4296,KTL', 100, 'kemboi@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Product_name` varchar(50) NOT NULL,
  `Brand` varchar(50) NOT NULL,
  `Description` text,
  `Unit_price` double NOT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `dateAdded` date DEFAULT NULL,
  `Category` varchar(50) DEFAULT NULL,
  `Measurement_Unit` char(30) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`Id`, `Product_name`, `Brand`, `Description`, `Unit_price`, `Quantity`, `dateAdded`, `Category`, `Measurement_Unit`) VALUES
(1, 'Tea', 'Kericho tea', 'Best', 50, 99, '2018-08-11', 'Beverage', NULL),
(11, 'ToothBrush', 'Colgate Tooth Brush', 'Strongest', 40, 98, '2018-08-12', 'Hygienic', 'pieces'),
(13, 'Phone', 'Sumsung J1', 'First', 8000, 100, '2018-08-13', 'Electronics', 'pcs'),
(15, 'Book', '4 quire 384 pgs', 'Strong cover', 60, 97, '2018-08-14', 'Stationery', 'pcs'),
(20, 'Tea', 'Kericho1 tea', 'Best', 50, 97, '2018-08-14', 'Beverage', 'pkts'),
(21, 'Tea', 'Kericho2 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(22, 'Tea', 'Kericho3 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(25, 'Tea', 'Kericho6 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(26, 'Tea', 'Kericho7 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(27, 'Tea', 'Kericho8 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(28, 'Tea', 'Kericho9 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(29, 'Tea', 'Kericho10 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(30, 'Tea', 'Kericho11 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(32, 'Tea', 'Kericho14 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(33, 'Tea', 'Kericho15 tea', 'Best', 50, 100, '2018-08-14', 'Beverage', 'pkts'),
(34, 'Tea', 'Kericho16 tea', 'Best', 50, 97, '2018-08-14', 'Beverage', 'pkts'),
(35, 'Tea', 'Kericho17 tea', 'Best', 50, 0, '2018-08-14', 'Beverage', 'pkts'),
(36, 'Tea', 'Kericho18 tea', 'Best', 50, 90, '2018-08-14', 'Beverage', 'pkts'),
(40, 'Shoes', 'Airmax', 'Durable', 2500, 10, '2018-08-15', 'low cut', 'pair'),
(41, 'newspapers', 'standard', 'disease breakout', 60, 100, '2018-08-15', 'sports', 'pieces'),
(42, 'newspapers', 'nation', 'disease breakout', 60, 5, '2018-08-15', 'sports', 'pieces'),
(43, 'Phone', 'Sumsung NOTE3', 'High quality pictures', 25000, 9, '2018-08-18', 'Electronics', 'pcs');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE IF NOT EXISTS `sales` (
  `Product_name` varchar(50) NOT NULL,
  `Brand` varchar(50) NOT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Customer_Id` int(11) DEFAULT NULL,
  `date_of_purchase` date DEFAULT NULL,
  KEY `Customer_Id` (`Customer_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`Product_name`, `Brand`, `Quantity`, `Customer_Id`, `date_of_purchase`) VALUES
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Kericho_Tea', 'Tea', 40, 1, '2018-08-11'),
('Mumias sugar', 'Tea', 40, 1, '2018-08-11'),
('Mumias sugar', 'Tea', 40, 1, '2018-08-11'),
('Mumias sugar', 'Tea', 40, 1, '2018-08-11'),
('Mumias sugar', 'Tea', 40, 1, '2018-08-11'),
('Mumias sugar', 'Tea', 40, 1, '2018-08-11'),
('Salt', 'Tea', 40, 1, '2018-08-11'),
('Salt', 'Tea', 40, 1, '2018-08-11'),
('Salt', 'Tea', 40, 1, '2018-08-11'),
('Salt', 'Tea', 40, 1, '2018-08-11'),
('Salt', 'Tea', 40, 1, '2018-08-11'),
('Tea', 'Kericho tea', 20, 1, '2018-08-13'),
('Salt', 'Ken Salt', 30, 1, '2018-08-13'),
('Salt', 'Ken Salt', 10, 1, '2018-08-13'),
('Book', 'Kasuku', 11, 1, '2018-08-13'),
('Tea', 'Kericho tea', 2, 1, '2018-08-13'),
('Salt', 'Ken Salt', 1, 1, '2018-08-13'),
('Tea', 'Kericho tea', 10, 1, '2018-08-13'),
('Salt', 'Ken Salt', 10, 1, '2018-08-13'),
('Salt', 'Ken Salt', 2, 1, '2018-08-13'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-13'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-13'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-13'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-13'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-13'),
('ToothPaste', 'Colgate', 2, 1, '2018-08-13'),
('Phone', 'Tecno N2S', 1, 1, '2018-08-14'),
('ToothPaste', 'Colgate', 1, 1, '2018-08-14'),
('Salt', 'Ken Salt', 3, 1, '2018-08-14'),
('Book', 'Kasuku', 4, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 1, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 1, 1, '2018-08-14'),
('Salt', 'Ken Salt', 20, 1, '2018-08-14'),
('ToothPaste', 'Colgate', 2, 1, '2018-08-14'),
('Book', 'Kasuku', 4, 1, '2018-08-14'),
('Book', 'Kasuku', 10, 1, '2018-08-14'),
('Salt', 'Ken Salt', 1, 1, '2018-08-14'),
('Phone', 'Tecno N2S', 1, 1, '2018-08-14'),
('Phone', 'Tecno N2S', 1, 1, '2018-08-14'),
('ToothBrush', 'Colgate Tooth Brush', 2, 1, '2018-08-14'),
('Salt', 'Ken Salt', 2, 1, '2018-08-14'),
('Tea', 'Kericho tea', 1, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 3, 1, '2018-08-14'),
('ToothPaste', 'Colgate', 10, 1, '2018-08-14'),
('Salt', 'Ken Salt', 4, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 1, 1, '2018-08-14'),
('ToothPaste', 'Colgate', 5, 1, '2018-08-14'),
('Phone', 'Sumsung J1', 2, 1, '2018-08-14'),
('Phone', 'Tecno N2S', 1, 1, '2018-08-14'),
('Tea', 'Kericho1 tea', 2, 1, '2018-08-14'),
('Phone', 'Tecno N2S', 1, 1, '2018-08-14'),
('Tea', 'Kericho1 tea', 1, 1, '2018-08-14'),
('Cooking Oil', 'Pwani Oil', 12, 1, '2018-08-14'),
('Tea', 'Kericho13 tea', 1, 1, '2018-08-14'),
('Tea', 'Kericho13 tea', 1, 1, '2018-08-14'),
('Tea', 'Kericho16 tea', 3, 1, '2018-08-15'),
('Phone', 'Sumsung J1', 1, 1, '2018-08-15'),
('Book', '4 quire 384 pgs', 3, 1, '2018-08-15'),
('Shoes', 'Airmax', 3, 1, '2018-08-15'),
('Phone', 'Sumsung NOTE3', 1, 1, '2018-08-18'),
('Tea', 'Kericho18 tea', 10, 1, '2018-08-18'),
('Tea', 'Kericho17 tea', 100, 1, '2018-08-25'),
('Tea', 'Kericho17 tea', 100, 1, '2018-08-25'),
('Tea', 'Kericho17 tea', 100, 1, '2018-08-25');

-- --------------------------------------------------------

--
-- Table structure for table `shop_admin`
--

CREATE TABLE IF NOT EXISTS `shop_admin` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `pass_code` varchar(16) DEFAULT NULL,
  `rank` char(15) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shop_admin`
--

INSERT INTO `shop_admin` (`Id`, `Username`, `email`, `pass_code`, `rank`) VALUES
(1, 'David', 'divadagnido96@gmail.com', 'davey', 'Admin'),
(2, 'Victor Mwakau', 'victor@gmail.com', '12345678', NULL),
(3, 'Fredric Aponyo', 'aponyo@gmail.com', '12345678', 'Shop keeper');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`Customer_Id`) REFERENCES `customers` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
